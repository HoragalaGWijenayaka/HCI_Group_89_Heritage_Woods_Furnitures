package Objects.Rooms;

import com.sun.j3d.utils.geometry.Box;

import javax.media.j3d.*;
import javax.vecmath.*;

public class RoomGeometry {

    public BranchGroup createRoom(float width, float height, float depth) {
        // Create a branch group for the room
        BranchGroup roomGroup = new BranchGroup();

        Appearance wallAppearance = new Appearance();

        // Define material properties
        Color3f ambientColor = new Color3f(0.7f, 0.7f, 0.7f); // Ambient color of the material
        Color3f emissiveColor = new Color3f(0.0f, 0.0f, 0.0f); // Emissive color of the material
        Color3f diffuseColor = new Color3f(0.7f, 0.7f, 0.7f); // Diffuse color of the material
        Color3f specularColor = new Color3f(1.0f, 1.0f, 1.0f); // Specular color of the material
        float shininess = 80.0f; // Shininess of the material

        Material material = new Material(ambientColor, emissiveColor, diffuseColor, specularColor, shininess);
        material.setLightingEnable(true);
        // Apply the material to the appearance
        wallAppearance.setMaterial(material);


        // Dimensions for the walls, floor, and ceiling
        float wallThickness = 0.1f; // Thin wall

        // Floor
        Box floor = new Box(width / 2, wallThickness / 2, depth / 2, Box.GENERATE_NORMALS, wallAppearance);
        TransformGroup floorTg = new TransformGroup();
        Transform3D floorTrans = new Transform3D();
        floorTrans.setTranslation(new Vector3f(0.0f, -height / 2, 0.0f));
        floorTg.setTransform(floorTrans);
        floorTg.addChild(floor);
        roomGroup.addChild(floorTg);

        // Ceiling
        Box ceiling = new Box(width / 2, wallThickness / 2, depth / 2, Box.GENERATE_NORMALS, wallAppearance);
        TransformGroup ceilingTg = new TransformGroup();
        Transform3D ceilingTrans = new Transform3D();
        ceilingTrans.setTranslation(new Vector3f(0.0f, height / 2, 0.0f));
        ceilingTg.setTransform(ceilingTrans);
        ceilingTg.addChild(ceiling);
        roomGroup.addChild(ceilingTg);

        // Walls
        Box leftWall = new Box(wallThickness / 2, height / 2, depth / 2, Box.GENERATE_NORMALS, wallAppearance);
        TransformGroup leftWallTg = new TransformGroup();
        Transform3D leftWallTrans = new Transform3D();
        leftWallTrans.setTranslation(new Vector3f(-width / 2, 0.0f, 0.0f));
        leftWallTg.setTransform(leftWallTrans);
        leftWallTg.addChild(leftWall);
        roomGroup.addChild(leftWallTg);

        Box rightWall = new Box(wallThickness / 2, height / 2, depth / 2, Box.GENERATE_NORMALS, wallAppearance);
        TransformGroup rightWallTg = new TransformGroup();
        Transform3D rightWallTrans = new Transform3D();
        rightWallTrans.setTranslation(new Vector3f(width / 2, 0.0f, 0.0f));
        rightWallTg.setTransform(rightWallTrans);
        rightWallTg.addChild(rightWall);
        roomGroup.addChild(rightWallTg);

        Box backWall = new Box(width / 2, height / 2, wallThickness / 2, Box.GENERATE_NORMALS, wallAppearance);
        TransformGroup backWallTg = new TransformGroup();
        Transform3D backWallTrans = new Transform3D();
        backWallTrans.setTranslation(new Vector3f(0.0f, 0.0f, -depth / 2));
        backWallTg.setTransform(backWallTrans);
        backWallTg.addChild(backWall);
        roomGroup.addChild(backWallTg);

        // Note: The front wall is omitted to allow viewing inside the room

        return roomGroup;
    }
}
