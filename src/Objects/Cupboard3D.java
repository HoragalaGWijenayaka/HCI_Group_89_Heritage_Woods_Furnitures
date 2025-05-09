package Objects;

import com.sun.j3d.utils.geometry.Box;
import javax.media.j3d.Material;
import javax.media.j3d.Appearance;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

public class Cupboard3D extends BranchGroup {
    public Cupboard3D() {
        // Create the body of the cupboard
        Transform3D bodyTransform = new Transform3D();
        bodyTransform.setTranslation(new Vector3f(0.0f, 0.2f, 0.0f));
        TransformGroup bodyTG = new TransformGroup(bodyTransform);
        bodyTG.addChild(new Box(0.3f, 0.4f, 0.1f, createMaterial(new Color3f(0.5f, 0.35f, 0.2f))));
        this.addChild(bodyTG);

        // Create the doors of the cupboard
        Transform3D doorTransform = new Transform3D();
        doorTransform.setTranslation(new Vector3f(0.0f, 0.2f, 0.101f));
        TransformGroup doorTG = new TransformGroup(doorTransform);
        doorTG.addChild(new Box(0.29f, 0.39f, 0.01f, createMaterial(new Color3f(0.4f, 0.25f, 0.15f))));
        this.addChild(doorTG);

        this.setCapability(BranchGroup.ALLOW_DETACH);
        this.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    }
    public Appearance createMaterial(Color3f color) {
        Appearance app = new Appearance();
        Material mat = new Material();
        mat.setAmbientColor(color);
        mat.setDiffuseColor(color);
        app.setMaterial(mat);
        return app;
    }
}
