package Frames;

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
import javax.swing.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
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

public class RoomDesigner extends JFrame {
    private List<Furniture2DItem> furnitureItems = new ArrayList<>();
    private JTextField roomNumberField;
    private JTextField widthTextField;
    private JTextField heightTextField;
    private JButton createButton;
    private JButton resetButton;
    private SimpleUniverse universe;
    private javax.media.j3d.Canvas3D canvas3D;

    public RoomDesigner(){
        super("Furniture Room Designer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout(10, 10));

        initializeSwingComponents();
    }
    private void initializeSwingComponents() {
        roomNumberField = new JTextField(10);
        widthTextField = new JTextField(5);
        heightTextField = new JTextField(5);
        createButton = new JButton("Create");
        resetButton = new JButton("Reset");

        JPanel headerPanel = createHeaderPanel();
        JPanel furniturePanel = createFurniturePanel();
        // Main content area where the room is designed
        JPanel designAreaPanel = createDesignAreaPanel();

        getContentPane().add(headerPanel, BorderLayout.NORTH);
        getContentPane().add(furniturePanel, BorderLayout.WEST);
        getContentPane().add(designAreaPanel, BorderLayout.CENTER);
    }
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        headerPanel.setBackground(Color.LIGHT_GRAY);

        headerPanel.add(new JLabel("Room Number"));
        headerPanel.add(roomNumberField); // Use the initialized roomNumberField
        headerPanel.add(new JLabel("Width"));
        headerPanel.add(widthTextField); // Use the initialized widthTextField
        headerPanel.add(new JLabel("Height"));
        headerPanel.add(heightTextField);

        headerPanel.add(createButton);
        headerPanel.add(resetButton);
        createButton.addActionListener(e -> show3DView());

        return headerPanel;
    }
    private JPanel createFurniturePanel() {
        JPanel furniturePanel = new JPanel();
        furniturePanel.setLayout(new BoxLayout(furniturePanel, BoxLayout.Y_AXIS));
        furniturePanel.setBackground(Color.WHITE);

        // Create labels with these icons and align them
        JLabel bedLabel = createDraggableLabel("src/Resources/bed.png", 100, 100, "Bed");
        JLabel chairLabel = createDraggableLabel("src/Resources/chair.png", 100, 100, "Chair");
        JLabel cupboardLabel = createDraggableLabel("src/Resources/cupboard.png", 100, 100, "Cupboard");
        JLabel deskLabel = createDraggableLabel("src/Resources/desk.png", 100, 100, "Desk");

        // Center align labels
        bedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        chairLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cupboardLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        deskLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add to the panel
        furniturePanel.add(bedLabel);
        furniturePanel.add(chairLabel);
        furniturePanel.add(cupboardLabel);
        furniturePanel.add(deskLabel);

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create and style the Save button
        JButton saveButton = new JButton("Save");
        styleButton(saveButton, Color.BLUE);

        // Create and style the Delete button
        JButton deleteButton = new JButton("Delete");
        styleButton(deleteButton, Color.RED);

        // Add buttons to the button panel
        buttonPanel.add(saveButton);
        buttonPanel.add(deleteButton);

        saveButton.addActionListener(e -> saveDesign());
        deleteButton.addActionListener(e-> deleteDesign());

        // Add button panel to furniture panel
        furniturePanel.add(buttonPanel);

        return furniturePanel;
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(100, 35));
    }

    private JLabel createDraggableLabel(String imagePath, int width, int height, String type) {
        try {
            ImageIcon icon = new ImageIcon(ImageIO.read(new File(imagePath)).getScaledInstance(width, height, Image.SCALE_SMOOTH));
            JLabel label = new JLabel(icon);
            label.setTransferHandler(new TransferHandler("icon") {
                @Override
                protected Transferable createTransferable(JComponent c) {
                    ImageIcon icon = (ImageIcon) ((JLabel) c).getIcon();
                    return new FurnitureTransferable(type, icon.getImage());
                }

                @Override
                public int getSourceActions(JComponent c) {
                    return COPY;
                }
            });
            label.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    JComponent jc = (JComponent) e.getSource();
                    TransferHandler th = jc.getTransferHandler();
                    th.exportAsDrag(jc, e, TransferHandler.COPY);
                }
            });
            return label;
        } catch (IOException ex) {
            ex.printStackTrace();
            return new JLabel("Image not found");
        }
    }

    private JPanel createDesignAreaPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawRect(50, 50, 600, 400); // Draw the design canvas
            }
        };
        panel.setLayout(null);
        panel.setDropTarget(new DropTarget() {
            public synchronized void drop(DropTargetDropEvent evt) {
                try{
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    Transferable t = evt.getTransferable();
                    String type = (String) t.getTransferData(DataFlavor.stringFlavor);
                    Image image = (Image) t.getTransferData(DataFlavor.imageFlavor);
                    if (image != null) {
                        Point location = evt.getLocation();
                        int width = image.getWidth(null);
                        int height = image.getHeight(null);

                        JLabel furnitureLabel = new JLabel(new ImageIcon(image));
                        furnitureLabel.setBounds(location.x - width/2, location.y-height/2,width,height);
                        panel.add(furnitureLabel);
                        furnitureItems.add(new Furniture2DItem(type, location));
                        System.out.println("Added "+type+" at: "+location);
                        panel.revalidate();
                        panel.repaint();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        return panel;
    }

    private void show3DView() {
        JFrame frame3D = new JFrame("3D View");
        frame3D.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame3D.setSize(800, 600);

        canvas3D = new javax.media.j3d.Canvas3D(SimpleUniverse.getPreferredConfiguration());
        frame3D.add(canvas3D, BorderLayout.CENTER);

        universe = new SimpleUniverse(canvas3D);
        universe.getViewingPlatform().setNominalViewingTransform();

        BranchGroup scene = createSceneGraph();

        // Set up lighting
        addLights(scene);

        universe.addBranchGraph(scene);

        // Set up interactions
        setupInteractions();

        // Make frame visible
        frame3D.setVisible(true);

        System.out.println("3D view displayed");
    }

    private void addLights(BranchGroup scene) {
        // Create a directional light
        DirectionalLight light = new DirectionalLight(new Color3f(1.0f, 1.0f, 1.0f), new Vector3f(-1.0f, -1.0f, -1.0f));
        light.setInfluencingBounds(new BoundingSphere(new Point3d(), 100.0));
        scene.addChild(light);
    }

    private void setupInteractions() {
        // Set up the initial view platform position and orientation
        TransformGroup viewTransformGroup = universe.getViewingPlatform().getViewPlatformTransform();
        Transform3D viewTransform3D = new Transform3D();

        // Position the camera to match the 3D view in your example
        Point3d eyePosition = new Point3d(0, 6, 12); // Adjust Y for height and Z for distance
        Point3d lookAt = new Point3d(0, 0, 0); // Look at the center of the scene
        Vector3d up = new Vector3d(0, 1, 0); // Up direction

        viewTransform3D.lookAt(eyePosition, lookAt, up);
        viewTransform3D.invert(); // Important: invert the Transform3D to get a correct view matrix
        viewTransformGroup.setTransform(viewTransform3D);

        // Create an OrbitBehavior that allows free movement
        OrbitBehavior orbit = new OrbitBehavior(canvas3D, OrbitBehavior.REVERSE_ALL);
        orbit.setSchedulingBounds(new BoundingSphere(new Point3d(), 1000.0));
        orbit.setRotFactors(1.0, 1.0); // Allow both left/right and up/down rotation

        ViewingPlatform viewingPlatform = universe.getViewingPlatform();
        viewingPlatform.setViewPlatformBehavior(orbit);
    }


    private Node createFloor() {
        // The color and texture for the floor
        Appearance floorAppearance = new Appearance();
        Color3f color = new Color3f(0.7f, 0.7f, 0.7f);
        ColoringAttributes ca = new ColoringAttributes(color, ColoringAttributes.NICEST);
        floorAppearance.setColoringAttributes(ca);

        // Creating a box representing the floor with the correct size
        Box floor = new Box(6f, 0.01f, 6f, Primitive.GENERATE_NORMALS, floorAppearance);

        // Ensure the floor is not pickable
        floor.setPickable(false);

        // Positioning the floor correctly in the scene
        TransformGroup floorTG = new TransformGroup();
        Transform3D transform = new Transform3D();
        Transform3D floorTransform = new Transform3D();
        Transform3D floorTrans = new Transform3D();
        floorTrans.setIdentity(); // Resets the transform to identity
        floorTrans.setTranslation(new Vector3f(0f, -0.01f, 0f)); // Apply translation to lower the floor slightly below the origin along the Y-axis
        floorTG.setTransform(floorTrans);
        floorTG.addChild(floor);

        return floorTG;
    }



    private BranchGroup createSceneGraph() {
        BranchGroup objRoot = new BranchGroup();
        objRoot.addChild(createFloor());
        for (Furniture2DItem item : furnitureItems) {
            System.out.println("Creating 3D object for type: " + item.getType());
            TransformGroup tg = createTransformGroupForItem(item);
            objRoot.addChild(tg);
        }
        return objRoot;
    }

    private TransformGroup createTransformGroupForItem(Furniture2DItem item) {
        String type = item.getType();
        Point position = item.getPosition();

        // Create the transform group and set its capabilities to allow runtime changes
        TransformGroup tg = new TransformGroup();
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

        // Create the transformation object and apply the scale
        Transform3D transform = new Transform3D();
        double scaleFactor = getScaleFactor(type);
        transform.setScale(new Vector3d(scaleFactor, scaleFactor, scaleFactor));

        // Translate the position, adjusting for the furniture's base height
        Vector3f position3D = translate2DPositionTo3D(position);
        position3D.y = getBaseHeight(type) / 2; // Set Y to half the base height
        transform.setTranslation(position3D);

        // Set the transformation to the transform group
        tg.setTransform(transform);

        // Add the actual 3D model of the furniture
        Node furniture3D = get3DModelForType(type);
        tg.addChild(furniture3D);

        return tg;
    }

    private double getScaleFactor(String type) {
        return switch (type) {
            case "Chair" -> 0.8;
            case "Bed" -> 1.2;
            case "Cupboard" -> 1.3;
            case "Desk" -> 1.25;
            default -> 1.0; // Default scale factor if type is unknown
        };
    }

    private Node get3DModelForType(String type) {
        switch (type) {
            case "Chair":
                return new Chair3D();
            case "Bed":
                return new Bed3D();
            case "Cupboard":
                return new Cupboard3D();
            case "Desk":
                return new Desk3D();
            default:
                return new BranchGroup(); // Placeholder for unknown types
        }
    }

    private float getBaseHeight(String type) {
        // You'll need to provide the actual base height for each furniture type
        return switch (type) {
            case "Chair" -> 0.5f;
            case "Bed" -> 0.8f;
            case "Cupboard" -> 1.0f;
            case "Desk" -> 0.7f;
            default -> 0.5f; // Default base height
        };
    }
    private Vector3f translate2DPositionTo3D(Point position) {
        // These ratios convert 2D positions to the 3D world size
        float xRatio = 12f / 600f; // Width of the room in 3D / width of the panel in 2D
        float zRatio = 12f / 400f; // Depth of the room in 3D / height of the panel in 2D

        float x3d = position.x * xRatio - 6f; // Adjust to center
        float z3d = position.y * zRatio - 6f; // Adjust to center
        // The y3d will be set based on the furniture's base height in the method above

        return new Vector3f(x3d, 0f, z3d); // Initially setting y to 0
    }

    private void saveDesign() {
        // Construct the string to save.
        StringBuilder builder = new StringBuilder();
        builder.append("Room Number: ").append(roomNumberField.getText()).append("\n");
        builder.append("Width: ").append(widthTextField.getText()).append("\n");
        builder.append("Height: ").append(heightTextField.getText()).append("\n");
        for (Furniture2DItem item : furnitureItems) {
            builder.append("Furniture Type: ").append(item.getType()).append(", ");
            builder.append("Position: ").append(item.getPosition().toString()).append("\n");
        }

        // Save to a file.
        try (PrintWriter out = new PrintWriter("room_design.txt")) {
            out.println(builder.toString());
            System.out.println("Saved");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void deleteDesign() {
        File file = new File("room_design.txt");
        if (file.delete()) {
            System.out.println("Design deleted successfully.");
        } else {
            System.out.println("Failed to delete the design.");
        }
    }

    private void resetDesign() {
        roomNumberField.setText("");
        widthTextField.setText("");
        heightTextField.setText("");
        furnitureItems.clear();
        canvas3D.repaint();
    }



    class Furniture2DItem {
        private String type;
        private Point position;

        public Furniture2DItem(String type, Point position) {
            this.type = type;
            this.position = position;
        }

        public String getType() {
            return type;
        }

        public Point getPosition() {
            return position;
        }
    }

    class FurnitureTransferable implements Transferable {
        private String type;
        private Image image;

        public FurnitureTransferable(String type, Image image) {
            this.type = type;
            this.image = image;
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{DataFlavor.stringFlavor, DataFlavor.imageFlavor};
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return DataFlavor.stringFlavor.equals(flavor) || DataFlavor.imageFlavor.equals(flavor);
        }

        @Override
        public Object getTransferData(DataFlavor flavor) {
            if (DataFlavor.stringFlavor.equals(flavor)) {
                return type;
            }
            if (DataFlavor.imageFlavor.equals(flavor)) {
                return image;
            }
            return null;
        }
    }


}