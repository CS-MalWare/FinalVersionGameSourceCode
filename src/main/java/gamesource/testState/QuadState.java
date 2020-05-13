package gamesource.testState;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;

public class QuadState extends BaseAppState {

    private BoxCollisionShape boxCollisionShape=new BoxCollisionShape(new Vector3f(9f,0.05f,9f));
    private RigidBodyControl rigidBodyFloor=new RigidBodyControl(0);
    private PhysicsSpace physicsSpace;

    private AssetManager assetManager;

    private Node rootNode=new Node("QuadState");

    private SimpleApplication app;

    @Override
    protected void initialize(Application application) {
        app=(SimpleApplication)application;
        BulletAppState bulletAppState=app.getStateManager().getState(BulletAppState.class);
        physicsSpace= bulletAppState.getPhysicsSpace();

        Geometry geom=new Geometry("平面", new Quad(9,9));
        geom.rotate(-1.57f,0,0);
        geom.move(-1.5f,0,1.5f);
        assetManager=application.getAssetManager();

        Material mat =new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color",ColorRGBA.Red);
        //mat.setFloat("Shininess",16f);
        //mat.setBoolean("UseMaterialColors",true);

        geom.setMaterial(mat);

        rootNode.attachChild(geom);

        rigidBodyFloor.setCollisionShape(boxCollisionShape);
        rigidBodyFloor.setRestitution(0.8f);

        physicsSpace.add(rigidBodyFloor);
    }

    @Override
    protected void cleanup(Application application) {

    }

    @Override
    protected void onEnable() {
    app.getRootNode().attachChild(this.rootNode);
    }

    @Override
    protected void onDisable() {

    }
}
