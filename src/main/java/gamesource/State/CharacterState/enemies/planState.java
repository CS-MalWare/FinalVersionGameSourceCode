package gamesource.State.CharacterState.enemies;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.bounding.BoundingVolume;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class PlanState extends BaseAppState {
    private BulletAppState bullet;
    private Spatial boy;
    private CapsuleCollisionShape capsuleShape=new CapsuleCollisionShape();
    private CharacterControl boyControl ;
    private float stepHeight=0.01f;
    private PhysicsSpace physics;

    private Node rootNode=new Node("lizard");

    private SimpleApplication app;

    private Node character;

    private float radius;

    private float height;

    private Node scene;

    private Node bip001;

    private Vector3f place=new Vector3f(0,0,0);

    private float modelY;
    private BoundingVolume lizard;

    protected void initialize(Application application){
        app=(SimpleApplication)application;

        initModel();
        initPhysics();


        boyControl.setPhysicsLocation(place);

        //initKeys();
        //initChaseCamera();
    }
    public PlanState(){

    }
    public PlanState(Vector3f place){
        this.place=place;
    }
    public PlanState(Vector3f place, float modelY){
        this.place=place;
        this.modelY=modelY;
    }
    public void initModel(){
        boy=app.getAssetManager().loadModel("character/plan/plan.j3o");
        boy.setName("boy");
        boy.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        boy.scale(0.4f);
        boy.rotate(0f,modelY,0f);
        lizard=boy.getWorldBound();
    }

    public BoundingVolume get(){
        return lizard;
    }

    public void initPhysics(){
        bullet=app.getStateManager().getState(BulletAppState.class);
        physics=bullet.getPhysicsSpace();

        radius=0.4f;
        height=0.6f;

        boy.move(0.06f,-(height/2+radius)-2.0f,0);

        character=new Node("Character");
        rootNode.attachChild(character);
        character.attachChild(boy);

        capsuleShape=new CapsuleCollisionShape(radius,height,1);

        boyControl=new CharacterControl(capsuleShape,stepHeight);
        character.addControl(boyControl);

        boyControl.setFallSpeed(55);
        boyControl.setPhysicsLocation(new Vector3f(0,height/2+radius,0));
        boyControl.setGravity(new Vector3f(0,-9.81f,0));

        physics.add(boyControl);


        character.setLocalTranslation(0, height / 2 + radius, 0);
        character.move(0.06f,-(height/2+radius),0);


        character.setLocalTranslation(3.764972f, -4.4763145f, 6.0633626f);

        rootNode.attachChild(character);
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