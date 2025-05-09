package Objects;

import com.sun.j3d.utils.geometry.Box;
import javax.media.j3d.Material;
import javax.media.j3d.Appearance;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;
public class Desk3D extends BranchGroup {
    public Desk3D() {
        // Create the tabletop of the desk
        Transform3D tabletopTransform = new Transform3D();
        tabletopTransform.setTranslation(new Vector3f(0.0f, 0.2f, 0.0f));
        TransformGroup tabletopTG = new TransformGroup(tabletopTransform);
        tabletopTG.addChild(new Box(0.5f, 0.05f, 0.3f, createMaterial(new Color3f(0.6f, 0.3f, 0.2f))));
        this.addChild(tabletopTG);

        // Create the legs of the desk
        for (int i = 0; i < 4; i++) {
            float x = (i % 2 == 0) ? 0.45f : -0.45f;
            float z = (i < 2) ? 0.25f : -0.25f;
            Transform3D legTransform = new Transform3D();
            legTransform.setTranslation(new Vector3f(x, 0.0f, z));
            TransformGroup legTG = new TransformGroup(legTransform);
            legTG.addChild(new Box(0.05f, 0.2f, 0.05f, createMaterial(new Color3f(0.4f, 0.2f, 0.1f))));
            this.addChild(legTG);
        }

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
