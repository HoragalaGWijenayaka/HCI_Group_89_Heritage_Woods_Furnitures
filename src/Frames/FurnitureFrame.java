package Frames;

import Components.Sidebar;
import Objects.Bed3D;
import Objects.Chair3D;
import Objects.Cupboard3D;
import Objects.Desk3D;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import javax.media.j3d.Canvas3D;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

public class FurnitureFrame extends JFrame {

    public FurnitureFrame() {
        setTitle("Select Furniture");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout(10, 10));

        JPanel furniturePanel = createFurniturePanel();
        getContentPane().add(furniturePanel, BorderLayout.CENTER);

        Sidebar sidebar = new Sidebar();
        this.add(sidebar,BorderLayout.WEST);

    }

    private JPanel createFurniturePanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2)); // Assuming 4 types of furniture

        String[] furnitureTypes = {"Bed", "Chair", "Cupboard", "Desk"};
        for (String type : furnitureTypes) {
            String imagePath = "/Resources/" + type.toLowerCase() + ".png"; // Path within the resource folder
            ImageIcon icon = createScaledImageIcon(imagePath, 200, 200);
            JButton button = new JButton(icon);
            button.addActionListener(ActionEvent -> display3DFurniture(type));
            panel.add(button);
        }

        return panel;
    }

    private ImageIcon createScaledImageIcon(String path, int width, int height) {
        URL imageUrl = getClass().getResource(path);
        if (imageUrl != null) {
            try {
                BufferedImage img = ImageIO.read(imageUrl);
                Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImg);
            } catch (IOException e) {
                e.printStackTrace();
                return new ImageIcon(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
            }
        } else {
            System.err.println("Couldn't find file: " + path);
            return new ImageIcon(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
        }
    }

    private void display3DFurniture(String type) {
        BranchGroup bg = null;
        switch (type) {
            case "Bed":
                bg = new Bed3D();
                break;
            case "Chair":
                bg = new Chair3D();
                break;
            case "Cupboard":
                bg = new Cupboard3D();
                break;
            case "Desk":
                bg = new Desk3D();
                break;
        }
        if (bg != null) {
            show3DFurniture(bg);
        }
    }

    private void show3DFurniture(BranchGroup bg) {
        JFrame frame3D = new JFrame("3D View");
        frame3D.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame3D.setSize(800, 600);

        Canvas3D canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        frame3D.add(canvas3D, BorderLayout.CENTER);

        SimpleUniverse universe = new SimpleUniverse(canvas3D);

        // Adjust the viewing platform to a reasonable starting distance
        universe.getViewingPlatform().setNominalViewingTransform();

        // Allow the user to interact with the object using the mouse
        universe.getViewer().getView().setMinimumFrameCycleTime(5);

        // Set up lighting so that the object is well lit
        addLightsToUniverse(universe);

        // Ensure the BranchGroup is capable of interaction
        bg.setCapability(BranchGroup.ALLOW_DETACH);
        bg.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
        bg.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);

        // Set up interactions
        setupInteractions(universe, canvas3D);

        universe.addBranchGraph(bg);

        frame3D.setVisible(true);
    }

    private void setupInteractions(SimpleUniverse universe, Canvas3D canvas3D) {
        OrbitBehavior orbit = new OrbitBehavior(canvas3D);
        orbit.setReverseTranslate(true);
        orbit.setSchedulingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0));
        universe.getViewingPlatform().setViewPlatformBehavior(orbit);
    }

    private void addLightsToUniverse(SimpleUniverse universe) {
        BranchGroup bg = new BranchGroup();

        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        Color3f light1Color = new Color3f(1.0f, 1.0f, 1.0f);
        Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
        DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
        light1.setInfluencingBounds(bounds);
        bg.addChild(light1);

        // Add an ambient light to reveal the details of the surface
        AmbientLight ambientLight = new AmbientLight(new Color3f(0.2f, 0.2f, 0.2f));
        ambientLight.setInfluencingBounds(bounds);
        bg.addChild(ambientLight);

        universe.addBranchGraph(bg);
    }


    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            FurnitureFrame frame = new FurnitureFrame();
            frame.setVisible(true);
        });
    }
}
