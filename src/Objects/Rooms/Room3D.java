package Objects.Rooms;


import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

import javax.media.j3d.*;
import javax.vecmath.*;
import java.awt.*;

public class Room3D {
    private Canvas3D canvas3D;
    private SimpleUniverse universe;
    private BranchGroup scene;
    private TransformGroup viewTransformGroup;
    private TransformGroup furnitureGroup;

    public Room3D() {
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        canvas3D = new Canvas3D(config);

        universe = new SimpleUniverse(canvas3D);
        viewTransformGroup = universe.getViewingPlatform().getViewPlatformTransform();

        furnitureGroup = new TransformGroup();
        furnitureGroup.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
        furnitureGroup.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);

//        updateViewTransform();
//
//        // Initialize the scene graph
//        scene = new BranchGroup();
//        scene.setCapability(BranchGroup.ALLOW_DETACH);
//        updateRoom(10.0f, 5.0f, 8.0f); // Call updateRoom to create the initial scene
//
//        setupOrbitControls();

        setupOrbitControls(); // Setup user controls for the canvas
        scene = createInitialScene(); // Create an initial empty scene
        universe.addBranchGraph(scene); // Add the initial scene to the universe
    }
    private void setupOrbitControls() {
        ViewingPlatform viewingPlatform = universe.getViewingPlatform();
        OrbitBehavior orbit = new OrbitBehavior(canvas3D, OrbitBehavior.REVERSE_ALL);
        BoundingSphere bounds = new BoundingSphere(new Point3d(), 1000.0);
        orbit.setSchedulingBounds(bounds);
        viewingPlatform.setViewPlatformBehavior(orbit);
    }

    private BranchGroup createInitialScene() {
        // Create an empty BranchGroup that can later be detached and replaced with updateRoom
        BranchGroup initialScene = new BranchGroup();
        initialScene.setCapability(BranchGroup.ALLOW_DETACH);
        return initialScene;
    }

    public void updateRoom(float width, float height, float depth) {
        // Stop the renderer, update the scene, and start the renderer again
        canvas3D.stopRenderer();

        if (scene != null && scene.isLive()) {
            scene.detach(); // Detach the current scene if it's live
        }

        // Create a new scene graph with the specified dimensions
        scene = createSceneGraph(width, height, depth);
        scene.compile(); // Optimize the scene graph

        // Add the new scene graph to the universe
        universe.addBranchGraph(scene);
        canvas3D.startRenderer();

        // Update the view transform to fit the new room dimensions
        updateViewTransform();
    }

//    private void updateViewTransform() {
//        // Adjust the camera position based on the room dimensions
//        Transform3D viewTransform3D = new Transform3D();
//        viewTransform3D.setTranslation(new Vector3f(0.0f, 5.0f, 20.0f)); // Position the camera
//        viewTransformGroup.setTransform(viewTransform3D);
//    }

    private void updateViewTransform() {
        // Create a new Transform3D object
        Transform3D viewTransform = new Transform3D();

        // Set the position of the camera (eye point)
        Point3d eye = new Point3d(0.0, 5.0, 20.0); // Eye position
        Point3d center = new Point3d(0.0, 0.0, 0.0); // Look at position
        Vector3d up = new Vector3d(0.0, 1.0, 0.0); // Up direction

        // Set the viewing transform
        viewTransform.lookAt(eye, center, up);
        viewTransform.invert();

        // Apply the transform to the viewing platform
        viewTransformGroup.setTransform(viewTransform);
    }

    // Method within the Room3D class to add furniture and position it correctly
    public void addFurniture(Node furniture) {
        BranchGroup furnitureBranch = new BranchGroup(); // This BranchGroup will wrap the furniture Node.
        furnitureBranch.setCapability(BranchGroup.ALLOW_DETACH); // Allow the BranchGroup to be detached if necessary.
        furnitureBranch.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
        furnitureBranch.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);

        Transform3D moveDown = new Transform3D();
        moveDown.setTranslation(new Vector3f(0.0f, -3.0f, 0.0f)); // This moves the furniture down. Adjust as needed.

        TransformGroup tg = new TransformGroup(moveDown);
        tg.addChild(furniture); // Add the actual furniture Node to the TransformGroup.
        furnitureBranch.addChild(tg); // Add the TransformGroup to the BranchGroup.

        furnitureGroup.addChild(furnitureBranch); // Add the BranchGroup to the furnitureGroup.
    }

    private void setupInteractivity() {
        BoundingSphere bounds = new BoundingSphere(new Point3d(), 1000.0);

        // Rotation behavior
        MouseRotate behavior = new MouseRotate();
        behavior.setTransformGroup(furnitureGroup);
        behavior.setSchedulingBounds(bounds);
        scene.addChild(behavior);
    }


    private BranchGroup createSceneGraph(float width, float height, float depth) {
        BranchGroup root = new BranchGroup();

        // Enable the ALLOW_DETACH capability so that the BranchGroup can be detached later
        root.setCapability(BranchGroup.ALLOW_DETACH);

        // Add the room geometry to the scene
        RoomGeometry roomGeometry = new RoomGeometry();
        root.addChild(roomGeometry.createRoom(width, height, depth));
        root.addChild(furnitureGroup);

        // Setup lighting and background
        setupLights(root);

        return root;
    }
    public void resetScene() {
        updateRoom(10.0f, 5.0f, 8.0f); // Reset to default dimensions
    }

    private void setupLights(BranchGroup root) {
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);

        // Ambient light
        Color3f ambientColor = new Color3f(0.5f, 0.5f, 0.5f);
        AmbientLight ambientLight = new AmbientLight(ambientColor);
        ambientLight.setInfluencingBounds(bounds);
        root.addChild(ambientLight);

        // Directional light
        Color3f light1Color = new Color3f(1.0f, 1.0f, 1.0f);
        Vector3f light1Direction = new Vector3f(-1.0f, -1.0f, -1.0f);
        DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
        light1.setInfluencingBounds(bounds);
        root.addChild(light1);
    }

    public Component getCanvas() {
        return canvas3D;
    }
}