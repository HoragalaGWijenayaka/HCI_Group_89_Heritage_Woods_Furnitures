package Frames;

import Modals.Furniture2DItem;
import Objects.Bed3D;
import Objects.Chair3D;
import Objects.Cupboard3D;
import Objects.Desk3D;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

import javax.imageio.ImageIO;
import javax.media.j3d.*;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.TransformGroup;
import javax.swing.*;
import javax.vecmath.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class DesignRoom extends JFrame {
    private List<Furniture2DItem> furnitureItems = new ArrayList<>();
    private JPanel designAreaPanel;
    private SimpleUniverse universe;
    private Canvas3D canvas3D;
    private BranchGroup sceneRoot;
    private TransformGroup roomTransformGroup;
    private int roomNumber;
    private int widthTextField;
    private int heightTextField;
    private Color floorColor;
    private Color leftWallColor;
    private Color frontWallColor;
    private Color rightWallColor;
    private int depthTextField;
    private String shape;
    private static final int SCALE_FACTOR = 100;

    public DesignRoom(int width, int height, int depth, int roomNumber, String shape, Color floorColor, Color leftWallColor, Color frontWallColor, Color rightWallColor) {
        super("Designer Room");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        getContentPane().setLayout(new BorderLayout());

        // Set the layout and add panels for 2D design
        JPanel headerPanel = createHeaderPanel(width, height, depth, shape);
        getContentPane().add(headerPanel, BorderLayout.WEST);

        JPanel furniturePanel = createFurniturePanel();
        getContentPane().add(furniturePanel, BorderLayout.NORTH);

        int panelWidth = width * SCALE_FACTOR;
        int panelHeight = depth * SCALE_FACTOR;

        this.roomNumber = roomNumber;
        this.widthTextField = width;
        this.heightTextField = height;
        this.floorColor = floorColor;
        this.leftWallColor =leftWallColor;
        this.rightWallColor = rightWallColor;
        this.frontWallColor = frontWallColor;
        this.shape = shape;


        designAreaPanel = createDesignAreaPanel(panelWidth, panelHeight, shape);
        getContentPane().add(designAreaPanel, BorderLayout.CENTER);

        JButton visualize3DButton = new JButton("Visualize 3D");
        visualize3DButton.setBackground(new Color(4, 29, 255)); // Hex #041DFF
        visualize3DButton.setForeground(Color.WHITE);
        visualize3DButton.addActionListener(e -> init3DView(width, height, depth, floorColor, leftWallColor, frontWallColor, rightWallColor));
        getContentPane().add(visualize3DButton, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void init3DView(int width, int height, int depth, Color floorColor, Color leftWallColor, Color frontWallColor, Color rightWallColor) {
        if (canvas3D == null) {
            setup3DCanvasAndUniverse();

            // Initialize sceneRoot with capabilities set
            sceneRoot = new BranchGroup();
            setInitialCapabilities(sceneRoot);

            BranchGroup roomGroup = createRoom(width, height, depth);
            setInitialCapabilities(roomGroup); // Set initial capabilities immediately after creating

            // Add the roomGroup to the sceneRoot
            sceneRoot.addChild(roomGroup);

            // Set the colors for the room
            setRoomColors(floorColor, leftWallColor, frontWallColor, rightWallColor);

            // Iterate over furniture items and add them to the scene
            for (Furniture2DItem item : furnitureItems) {
                TransformGroup furnitureTG = createFurnitureTransformGroup(item, width, height);
                if (furnitureTG != null) {
                    sceneRoot.addChild(furnitureTG);
                }
            }

            setupOrbitControls(canvas3D);

            // Finalize and add the scene to the universe
            sceneRoot.compile(); // Compile the scene graph before making it live
            universe.addBranchGraph(sceneRoot);

            getContentPane().add(canvas3D, BorderLayout.CENTER);
            designAreaPanel.setVisible(false);
            pack();
        }
    }


    private void setRoomColors(Color floorColor, Color leftWallColor, Color frontWallColor, Color rightWallColor) {
        // Print the colors to verify they are received
        System.out.println("Floor Color: " + floorColor);
        System.out.println("Left Wall Color: " + leftWallColor);
        System.out.println("Front Wall Color: " + frontWallColor);
        System.out.println("Right Wall Color: " + rightWallColor);

        // Set the colors for the walls and floor
        setFloorColor(floorColor);
        setLeftWallColor(leftWallColor);
        setFrontWallColor(frontWallColor);
        setRightWallColor(rightWallColor);
    }

    public void setFurnitureItems(List<Furniture2DItem> furnitureItems) {
        this.furnitureItems = furnitureItems;
        // Update the design area with these furniture items
        updateDesignArea();
    }

    private void updateDesignArea() {
        // Clear the existing furniture in the design area
        designAreaPanel.removeAll();

        // Iterate over furnitureItems and create labels for each item
        for (Furniture2DItem item : furnitureItems) {
            JLabel furnitureLabel = createFurnitureLabel(item.getType(), item.getPosition());
            designAreaPanel.add(furnitureLabel);
            // This assumes that createFurnitureLabel sets the size and position of the label correctly
        }

        // Redraw the panel
        designAreaPanel.revalidate();
        designAreaPanel.repaint();
    }


    private void setFloorColor(Color floorColor) {
        BranchGroup roomGroup = (BranchGroup) sceneRoot.getChild(0);
        TransformGroup floorTG = (TransformGroup) roomGroup.getChild(0);
        Box floor = findBoxInTransformGroup(floorTG);
        if (floor != null) {
            Appearance floorAppearance = new Appearance();
            ColoringAttributes ca = new ColoringAttributes(new Color3f(floorColor), ColoringAttributes.NICEST);
            floorAppearance.setColoringAttributes(ca);
            floor.setAppearance(floorAppearance);
        }
    }
    private Box findBoxInTransformGroup(TransformGroup tg) {
        for (int i = 0; i < tg.numChildren(); i++) {
            Node node = tg.getChild(i);
            if (node instanceof Box) {
                return (Box) node;
            }
        }
        return null;
    }
    private void setLeftWallColor(Color leftWallColor) {
        // Create an appearance with the specified color
        Appearance wallAppearance = new Appearance();
        ColoringAttributes ca = new ColoringAttributes(new Color3f(leftWallColor), ColoringAttributes.NICEST);
        wallAppearance.setColoringAttributes(ca);

        // Find the left wall TransformGroup and update its appearance
        BranchGroup roomGroup = (BranchGroup) sceneRoot.getChild(0);
        TransformGroup leftWallTG = (TransformGroup) roomGroup.getChild(2);
        Box leftWall = (Box) leftWallTG.getChild(0);
        leftWall.setAppearance(wallAppearance);
    }

    private void setFrontWallColor(Color frontWallColor) {
        // Create an appearance with the specified color
        Appearance wallAppearance = new Appearance();
        ColoringAttributes ca = new ColoringAttributes(new Color3f(frontWallColor), ColoringAttributes.NICEST);
        wallAppearance.setColoringAttributes(ca);

        // Find the front wall TransformGroup and update its appearance
        BranchGroup roomGroup = (BranchGroup) sceneRoot.getChild(0);
        TransformGroup frontWallTG = (TransformGroup) roomGroup.getChild(1);
        Box frontWall = (Box) frontWallTG.getChild(0);
        frontWall.setAppearance(wallAppearance);
    }

    private void setRightWallColor(Color rightWallColor) {
        // Create an appearance with the specified color
        Appearance wallAppearance = new Appearance();
        ColoringAttributes ca = new ColoringAttributes(new Color3f(rightWallColor), ColoringAttributes.NICEST);
        wallAppearance.setColoringAttributes(ca);

        // Find the right wall TransformGroup and update its appearance
        BranchGroup roomGroup = (BranchGroup) sceneRoot.getChild(0);
        TransformGroup rightWallTG = (TransformGroup) roomGroup.getChild(3);
        Box rightWall = (Box) rightWallTG.getChild(0);
        rightWall.setAppearance(wallAppearance);
    }


    private JPanel createFurniturePanel() {
        JPanel furniturePanel = new JPanel(new BorderLayout());
        furniturePanel.setBackground(Color.WHITE);

        // Furniture items panel (left aligned, horizontal)
        JPanel itemsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        itemsPanel.setBackground(Color.WHITE);

        String[] furnitureTypes = {"Bed", "Chair", "Cupboard", "Desk"};
        for (String type : furnitureTypes) {
            itemsPanel.add(createDraggableLabel("src/Resources/" + type.toLowerCase() + ".png", type));
        }

        // Buttons panel (right aligned)
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonsPanel.setBackground(Color.WHITE);

        JButton saveButton = new JButton("Save");
        saveButton.setBackground(new Color(0, 0, 255)); // Blue
        saveButton.setForeground(Color.WHITE); // White text
        saveButton.setFocusPainted(false);
        saveButton.addActionListener(e -> saveDesign());

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBackground(new Color(255, 0, 0)); // Red
        deleteButton.setForeground(Color.WHITE); // White text
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(e -> deleteDesign());

        buttonsPanel.add(saveButton);
        buttonsPanel.add(deleteButton);

        // Add both panels to the main furniture panel
        furniturePanel.add(itemsPanel, BorderLayout.WEST);
        furniturePanel.add(buttonsPanel, BorderLayout.EAST);

        return furniturePanel;
    }



    private JPanel createHeaderPanel(int width, int height, int depth, String shape) {
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        headerPanel.setBackground(Color.LIGHT_GRAY);
        headerPanel.add(new JLabel("Width: " + width + ", Height: " + height + ", Depth: " + depth + ", Shape: " + shape));
        return headerPanel;
    }

    private void saveDesign() {
        // Assume that we are saving the room design to a text file
        try (PrintWriter out = new PrintWriter("room_info.txt")) {
            out.println("Room Number: " + this.roomNumber);
            out.println("Room Size (WxHxD): " + this.widthTextField + "x" +
                    this.heightTextField + "x" + this.depthTextField);
            out.println("Room Shape: " + this.shape);

            // Save the colors as RGB values
            out.println("Floor Color: " + colorToRgbString(floorColor));
            out.println("Left Wall Color: " + colorToRgbString(leftWallColor));
            out.println("Front Wall Color: " + colorToRgbString(frontWallColor));
            out.println("Right Wall Color: " + colorToRgbString(rightWallColor));

            // Save furniture items
            for (Furniture2DItem item : furnitureItems) {
                out.println("Furniture type: " + item.getType() + ", Position: " + item.getPosition().toString());
            }

            JOptionPane.showMessageDialog(this, "Design saved successfully.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving design.");
        }
    }

    private String colorToRgbString(Color color) {
        return color.getRed() + "," + color.getGreen() + "," + color.getBlue();
    }


    private void deleteDesign() {
        // Deleting the room design text file
        File file = new File("room_design.txt");
        if (file.delete()) {
            JOptionPane.showMessageDialog(this, "Design deleted successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "Error deleting design.");
        }
    }


    private JLabel createDraggableLabel(String imagePath, String type) {
        JLabel label = new JLabel();
        label.setPreferredSize(new Dimension(100, 100));
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        try {
            Image img = ImageIO.read(new File(imagePath));
            ImageIcon icon = new ImageIcon(img.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
            label.setIcon(icon);
        } catch (IOException ex) {
            ex.printStackTrace();
            label.setText(type);
        }
        label.setTransferHandler(new ValueExportTransferHandler(type));

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JComponent c = (JComponent) e.getSource();
                TransferHandler handler = c.getTransferHandler();
                handler.exportAsDrag(c, e, TransferHandler.COPY);
            }
        });

        return label;
    }

    private JPanel createDesignAreaPanel(int width, int height, String shape) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK); // Set the border color
                // Draw the shape based on the user's selection
                if ("Rectangle".equals(shape)) {
                    g.drawRect(0, 0, width, height); // Rectangles can have different width and height
                } else if ("Square".equals(shape)) {
                    int size = Math.min(width, height); // For a square, width and height are the same
                    g.drawRect(0, 0, size, size);
                }
                // You can add additional shapes if necessary
            }
        };
        panel.setPreferredSize(new Dimension(width, height));
        panel.setLayout(null);
        panel.setBackground(new Color(240, 240, 240));

        // Set up the panel to accept drops
        panel.setDropTarget(new DropTarget() {
            @Override
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    String data = (String) evt.getTransferable().getTransferData(DataFlavor.stringFlavor);
                    Point dropPoint = evt.getLocation();
                    JLabel droppedLabel = createFurnitureLabel(data, dropPoint);
                    panel.add(droppedLabel);

                    // Add the furniture item to the furnitureItems list
                    furnitureItems.add(new Furniture2DItem(data, dropPoint, droppedLabel));
                    System.out.println("Furniture type: " + data + ", Position: " + dropPoint);

                    panel.revalidate();
                    panel.repaint();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        return panel;
    }

    private JLabel createFurnitureLabel(String text, Point location) {
        JLabel label = new JLabel(text);
        label.setSize(100, 100);
        label.setLocation(location);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Add mouse listener for moving the furniture
        MouseAdapter ma = new MouseAdapter() {
            private Point lastLocation;

            @Override
            public void mousePressed(MouseEvent e) {
                lastLocation = e.getPoint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                Point location = label.getLocation();
                int x = location.x - lastLocation.x + e.getX();
                int y = location.y - lastLocation.y + e.getY();
                label.setLocation(x, y);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // After moving the furniture, update its position in the list
                for (Furniture2DItem item : furnitureItems) {
                    if (item.getLabel().equals(label)) {
                        item.setPosition(label.getLocation());
                        break;
                    }
                }
            }
        };
        label.addMouseListener(ma);
        label.addMouseMotionListener(ma);

        return label;
    }


    private void showRoomIn3D(int width, int height, int depth) {
        // Create and set up the 3D canvas
        if (canvas3D == null) {
            setup3DCanvasAndUniverse();
        }

        // Clear the previous scene if needed
        sceneRoot.detach();
        sceneRoot = new BranchGroup();
        sceneRoot.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
        sceneRoot.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);

        // Add lights to the scene
        addLight(sceneRoot);

        // Add the walls to the scene
        BranchGroup roomGroup = createRoom(width, height, depth);
        sceneRoot.addChild(roomGroup);

        // Iterate over the furniture items and add them to the scene
        for (Furniture2DItem item : furnitureItems) {
            TransformGroup furnitureTG = createFurnitureTransformGroup(item, width, height);
            sceneRoot.addChild(furnitureTG);
        }

        // Finalize and add the scene to the universe
        sceneRoot.compile();
        universe.addBranchGraph(sceneRoot);
        setupOrbitControls(canvas3D);

        // Swap the panels
        designAreaPanel.setVisible(false);
        getContentPane().add(canvas3D, BorderLayout.CENTER); // Add the canvas to the center
        pack(); // Adjust the window to account for the new component sizes
    }


    private BranchGroup createRoom(int width, int height, int depth) {
        BranchGroup roomGroup = new BranchGroup();
        setInitialCapabilities(roomGroup);

        TransformGroup floorTG = createWall(width, depth, new Vector3f(0f, -height / 2f, 0f), "floor");
        roomGroup.addChild(floorTG);

        TransformGroup frontWallTG = createWall(width, height, new Vector3f(0f, 0f, -depth / 2f), "frontWall");
        roomGroup.addChild(frontWallTG);

        TransformGroup leftWallTG = createWall(depth, height, new Vector3f(-width / 2f, 0f, 0f), "leftWall");
        roomGroup.addChild(leftWallTG);

        TransformGroup rightWallTG = createWall(depth, height, new Vector3f(width / 2f, 0f, 0f), "rightWall");
        roomGroup.addChild(rightWallTG);

        addLight(roomGroup);

        return roomGroup;
    }


    private void addLight(BranchGroup group) {
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);

        // Ambient light
        AmbientLight ambientLight = new AmbientLight(true, new Color3f(0.2f, 0.2f, 0.2f));
        ambientLight.setInfluencingBounds(bounds);
        group.addChild(ambientLight);

        // Directional light
        Vector3f lightDirection = new Vector3f(-1.0f, -1.0f, -1.0f); // example direction
        DirectionalLight directionalLight = new DirectionalLight(true, new Color3f(1.0f, 1.0f, 1.0f), lightDirection);
        directionalLight.setInfluencingBounds(bounds);
        group.addChild(directionalLight);

        // Point light (example: positioned above the center of the scene)
        PointLight pointLight = new PointLight(new Color3f(1.0f, 0.0f, 0.0f), new Point3f(0.0f, 5.0f, 0.0f), new Point3f(1.0f, 0.0f, 0.0f));
        pointLight.setInfluencingBounds(bounds);
        group.addChild(pointLight);

        // Spot light (example: pointing downwards)
        SpotLight spotLight = new SpotLight(new Color3f(0.0f, 1.0f, 0.0f), new Point3f(0.0f, 5.0f, 0.0f), new Point3f(0.1f, 0.0f, 0.0f), new Vector3f(0.0f, -1.0f, 0.0f), (float) Math.PI / 4, 50);
        spotLight.setInfluencingBounds(bounds);
        group.addChild(spotLight);
    }


    private TransformGroup createWall(float width, float height, Vector3f position, String name) {
        // Set up the appearance for the wall with coloring attributes
        Appearance wallAppearance = new Appearance();
        ColoringAttributes ca = new ColoringAttributes();
        ca.setColor(new Color3f(Color.WHITE)); // Default to white, color is set later
        wallAppearance.setColoringAttributes(ca);

        // Create the wall as a box
        Box wall = new Box(width / 2, height / 2, 0.05f, Primitive.GENERATE_NORMALS, wallAppearance);

        // Set the capabilities for the wall's appearance modifications at runtime
        wallAppearance.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_WRITE);
        wall.setCapability(Box.ENABLE_APPEARANCE_MODIFY);

        // Prepare the transform for the wall
        Transform3D transform = new Transform3D();
        Transform3D rotation = new Transform3D();
        if ("leftWall".equals(name) || "rightWall".equals(name)) {
            rotation.rotY(Math.PI / 2);
        } else if ("floor".equals(name)) {
            rotation.rotX(-Math.PI / 2);
        }
        transform.setTranslation(position);
        transform.mul(rotation);

        // Create a TransformGroup and add the wall to it
        TransformGroup tg = new TransformGroup(transform);
        tg.addChild(wall);

        return tg; // Return the TransformGroup with the wall
    }



    private TransformGroup createFurnitureTransformGroup(Furniture2DItem item, int roomWidth, int roomHeight) {
        // Calculate the 3D position based on the 2D position
        float xRatio = (float) roomWidth / designAreaPanel.getWidth();
        float yRatio = (float) roomHeight / designAreaPanel.getHeight(); // Assuming the panel height maps to 3D y-axis

        // Assuming the center of the design area panel corresponds to the origin (0,0,0) in the 3D space
        float xPosition = (item.getPosition().x - designAreaPanel.getWidth() / 2f) * xRatio;
        float yPosition = (item.getPosition().y - designAreaPanel.getHeight() / 2f) * yRatio;

        // Obtain the correct 3D model based on the type of furniture
        Node furniture3DModel = get3DModelForType(item.getType());
        if (furniture3DModel == null) {
            System.err.println("No 3D model found for type: " + item.getType());
            return null;
        }

        // Set correct scale. Adjust these values based on your model sizes
        float scale = getScaleForFurnitureType(item.getType());

        // The position of the base of the furniture should be half of its height to sit on the floor
        float baseHeight = getBaseHeightForFurnitureType(item.getType());
        float zPosition = -baseHeight / 2;

        // Create the Transform3D for positioning the furniture
        Transform3D transform3D = new Transform3D();
        transform3D.setTranslation(new Vector3f(xPosition, zPosition, -yPosition));

        // Apply scaling if the model is not to scale
        Transform3D scaling = new Transform3D();
        scaling.setScale(new Vector3d(scale, scale, scale)); // Set the scale
        transform3D.mul(scaling);

        // Create the TransformGroup with the transform
        TransformGroup furnitureTG = new TransformGroup(transform3D);

        // Add the furniture model to the TransformGroup
        furnitureTG.addChild(furniture3DModel);

        furnitureTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        furnitureTG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        furnitureTG.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);

        return furnitureTG;
    }

    // Adjust these methods based on the actual dimensions and scales of your furniture models
    private float getScaleForFurnitureType(String type) {
        switch (type.toLowerCase()) {
            case "bed":
                return 1.5f;
            case "chair":
                return 0.5f;
            case "cupboard":
                return 2.5f;
            case "desk":
                return 1.2f;
            default:
                return 1.0f;
        }
    }

    private float getBaseHeightForFurnitureType(String type) {
        switch (type.toLowerCase()) {
            case "bed":
                return 2.2f;
            case "chair":
                return 2.5f;
            case "cupboard":
                return 2.2f;
            case "desk":
                return 2.4f;
            default:
                return 0.3f;
        }
    }


    // Call this method before the scene becomes live, e.g., before universe.addBranchGraph(sceneRoot)
    private void setInitialCapabilities(BranchGroup group) {
        group.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
        group.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
        group.setCapability(BranchGroup.ALLOW_DETACH);
    }

    // In your method where you compile the scene graph and make it live
    private void compileAndAddToUniverse() {
        sceneRoot.compile();
        universe.addBranchGraph(sceneRoot);
    }


    private void enableAppearanceChange(Box wall) {
        // Now setting capabilities before the object is added to the live scene graph
        wall.setCapability(Box.ENABLE_APPEARANCE_MODIFY);
        wall.getAppearance().setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_WRITE);
        for (int i = 0; i < wall.numChildren(); i++) {
            Shape3D shape = (Shape3D) wall.getChild(i);
            shape.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
            shape.getAppearance().setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_WRITE);
        }
    }


    private Node get3DModelForType(String type) {
        // This method should return a 3D model based on the furniture type
        // Here's an example of how it could look
        Node model;
        switch (type.toLowerCase()) {
            case "bed":
                model = new Bed3D(); // Assuming Bed3D extends Node and is your 3D model for the bed
                break;
            case "chair":
                model = new Chair3D(); // Similarly for Chair3D
                break;
            case "cupboard":
                model = new Cupboard3D(); // And so on for each furniture type
                break;
            case "desk":
                model = new Desk3D();
                break;
            default:
                // If we don't recognize the furniture type, we can create a default shape or return null
                model = new Box(0.1f, 0.1f, 0.1f, createAppearance());
                break;
        }
        return model;
    }

    private Appearance createAppearance() {
        // Create a default Appearance object to use for the default shape
        Appearance appearance = new Appearance();
        ColoringAttributes ca = new ColoringAttributes();
        ca.setColor(new Color3f(0.6f, 0.3f, 0.2f)); // Example color
        appearance.setColoringAttributes(ca);
        return appearance;
    }


    private Appearance createDebugAppearance() {
        Appearance debugAppearance = new Appearance();
        ColoringAttributes ca = new ColoringAttributes();
        ca.setColor(new Color3f(1.0f, 0.0f, 0.0f)); // Red color for debugging
        debugAppearance.setColoringAttributes(ca);
        return debugAppearance;
    }


    private void setup3DCanvasAndUniverse() {
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        canvas3D = new Canvas3D(config);

        universe = new SimpleUniverse(canvas3D);
        universe.getViewingPlatform().setNominalViewingTransform();

        sceneRoot = new BranchGroup();
        sceneRoot.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
        sceneRoot.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
        sceneRoot.setCapability(BranchGroup.ALLOW_DETACH);

        universe.addBranchGraph(sceneRoot);
    }

    private void setupOrbitControls(Canvas3D canvas) {
        OrbitBehavior orbit = new OrbitBehavior(canvas, OrbitBehavior.REVERSE_ALL);
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        orbit.setSchedulingBounds(bounds);
        ViewingPlatform vp = universe.getViewingPlatform();
        vp.setViewPlatformBehavior(orbit);
    }
}

class ValueExportTransferHandler extends TransferHandler {
    private final String value;

    public ValueExportTransferHandler(String value) {
        this.value = value;
    }

    protected Transferable createTransferable(JComponent c) {
        return new StringSelection(value);
    }

    public int getSourceActions(JComponent c) {
        return COPY;
    }
}
