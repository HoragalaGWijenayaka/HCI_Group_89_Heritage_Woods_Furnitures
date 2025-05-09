package Objects;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Cylinder;
import javax.media.j3d.*;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

public class Chair3D extends BranchGroup {
    public Chair3D() {
        // Set capabilities
        this.setCapability(BranchGroup.ALLOW_DETACH);
        this.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        // Create the seat of the chair
        Transform3D seatTransform = new Transform3D();
        seatTransform.setTranslation(new Vector3f(0.0f, 0.2f, 0.0f));
        TransformGroup seatTG = new TransformGroup(seatTransform);
        seatTG.addChild(createBox(0.6f, 0.1f, 0.6f, createMaterial(new Color3f(0.6f, 0.3f, 0.2f))));
        this.addChild(seatTG);

        // Create the back of the chair
        Transform3D backTransform = new Transform3D();
        backTransform.setTranslation(new Vector3f(0.0f, 0.5f, -0.3f));
        TransformGroup backTG = new TransformGroup(backTransform);
        backTG.addChild(createBox(0.6f, 0.5f, 0.1f, createMaterial(new Color3f(0.6f, 0.3f, 0.2f))));
        this.addChild(backTG);

        // Create the legs of the chair
        createLeg(new Vector3f(0.25f, 0.1f, 0.25f));
        createLeg(new Vector3f(-0.25f, 0.1f, 0.25f));
        createLeg(new Vector3f(0.25f, 0.1f, -0.25f));
        createLeg(new Vector3f(-0.25f, 0.1f, -0.25f));
    }

    private void createLeg(Vector3f position) {
        Transform3D legTransform = new Transform3D();
        legTransform.setTranslation(position);
        TransformGroup legTG = new TransformGroup(legTransform);
        legTG.addChild(createCylinder(0.05f, 0.4f, createMaterial(new Color3f(0.5f, 0.25f, 0.1f))));
        this.addChild(legTG);
    }

    private Box createBox(float x, float y, float z, Appearance ap) {
        return new Box(x, y, z, Box.GENERATE_NORMALS, ap);
    }

    private Cylinder createCylinder(float radius, float height, Appearance ap) {
        return new Cylinder(radius, height, Cylinder.GENERATE_NORMALS, ap);
    }

    private Appearance createMaterial(Color3f color) {
        Appearance ap = new Appearance();
        Material mat = new Material();
        mat.setDiffuseColor(color);
        mat.setSpecularColor(new Color3f(1.0f, 1.0f, 1.0f));
        mat.setShininess(50.0f);
        ap.setMaterial(mat);
        return ap;
    }
}
