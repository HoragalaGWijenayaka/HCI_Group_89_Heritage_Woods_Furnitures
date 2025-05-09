package Objects;

import com.sun.j3d.utils.geometry.Box;
import javax.media.j3d.Material;
import javax.media.j3d.Appearance;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

public class Bed3D extends BranchGroup {
    public Bed3D() {
        // Create the base of the bed
        Transform3D baseTransform = new Transform3D();
        baseTransform.setTranslation(new Vector3f(0.0f, -0.1f, 0.0f));
        TransformGroup baseTG = new TransformGroup(baseTransform);
        baseTG.addChild(new Box(0.4f, 0.1f, 0.6f, createMaterial(new Color3f(0.3f, 0.2f, 0.1f))));
        this.addChild(baseTG);

        // Create the mattress of the bed
        Transform3D mattressTransform = new Transform3D();
        mattressTransform.setTranslation(new Vector3f(0.0f, 0.1f, 0.0f));
        TransformGroup mattressTG = new TransformGroup(mattressTransform);
        mattressTG.addChild(new Box(0.4f, 0.05f, 0.6f, createMaterial(new Color3f(0.8f, 0.8f, 0.9f))));
        this.addChild(mattressTG);

        // Create the pillows of the bed
        Transform3D pillowTransform = new Transform3D();
        pillowTransform.setTranslation(new Vector3f(0.0f, 0.15f, 0.5f));
        TransformGroup pillowTG = new TransformGroup(pillowTransform);
        pillowTG.addChild(new Box(0.35f, 0.05f, 0.1f, createMaterial(new Color3f(0.9f, 0.9f, 1.0f))));
        this.addChild(pillowTG);

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
