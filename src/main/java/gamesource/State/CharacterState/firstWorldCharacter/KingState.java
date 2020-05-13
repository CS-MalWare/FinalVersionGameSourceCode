package gamesource.State.CharacterState.firstWorldCharacter;

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

public class KingState extends BaseAppState {
    private BulletAppState bullet;
    private PhysicsSpace physics;
    private Spatial King;
    private CapsuleCollisionShape capsuleShape=new CapsuleCollisionShape();
    private CharacterControl kingControl ;
    private float stepHeight=0.01f;
    private Node rootNode=new Node("king");
    private SimpleApplication app;
    private Node character;
    private float radius;

    private float height;

    private float modelY=0f;
    private Vector3f place=new Vector3f(0,0,0);

    private BoundingVolume king;

    protected void initialize(Application application){
        app=(SimpleApplication)application;


        initModel();
        initPhysics();



        kingControl.setPhysicsLocation(place);
    }
    public KingState(){

    }

    public KingState(Vector3f place){
        this.place=place;

    }
    public KingState(Vector3f place,float modelY){
        this.place=place;
        this.modelY=modelY;
    }
    @Override
    protected void cleanup(Application application) {

    }

    @Override
    protected void onEnable() {
        app.getRootNode().attachChild(this.rootNode);
        physics.add(kingControl);
    }

    @Override
    protected void onDisable() {

        this.rootNode.removeFromParent();
        physics.remove(kingControl);
    }

    public void initPhysics(){
        bullet=app.getStateManager().getState(BulletAppState.class);
        physics=bullet.getPhysicsSpace();

        radius=0.4f;
        height=0.6f;

        King.move(0.06f,-(height/2+radius)+0f,0);

        character=new Node("Character");
        rootNode.attachChild(character);
        character.attachChild(King);

        capsuleShape=new CapsuleCollisionShape(radius,height,1);

        kingControl=new CharacterControl(capsuleShape,stepHeight);
        character.addControl(kingControl);

        kingControl.setFallSpeed(55);
        kingControl.setPhysicsLocation(new Vector3f(0,height/2+radius,0));
        kingControl.setGravity(new Vector3f(0,-9.81f,0));

        //physics.add(kingControl);


        character.setLocalTranslation(0, height / 2 + radius, 0);
        character.move(0.06f,-(height/2+radius)+3,0);


        rootNode.attachChild(character);
    }
    public void initModel(){
        King = app.getAssetManager().loadModel("character/king/scene.j3o");
        King.setName("King");
        King.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        King.scale(0.11f);
        King.rotate(0,modelY,0f);

        king=King.getWorldBound();
    }

    public BoundingVolume get(){
        return king;
    }
}
