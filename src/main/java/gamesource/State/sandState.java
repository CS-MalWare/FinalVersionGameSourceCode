package gamesource.State;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class sandState extends BaseAppState {

    public Spatial Scene;

    public CollisionShape sceneShape;

    public RigidBodyControl landScape;

    public PhysicsSpace physics;

    private Node rootNode=new Node("sandState");

    private SimpleApplication app;

    protected void initialize(Application application){
        app=(SimpleApplication)application;
        BulletAppState bullet=app.getStateManager().getState(BulletAppState.class);

        physics=bullet.getPhysicsSpace();

        Scene=app.getAssetManager().loadModel("Map/scene.j3o");

        Scene.scale(3.5f);
        Scene.move(0, -35, -3);

        sceneShape = CollisionShapeFactory.createMeshShape(Scene);
        landScape=new RigidBodyControl(sceneShape,0);

        Scene.addControl(landScape);

        rootNode.attachChild(Scene);
        physics.add(landScape);
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
        this.rootNode.removeFromParent();
    }
}
