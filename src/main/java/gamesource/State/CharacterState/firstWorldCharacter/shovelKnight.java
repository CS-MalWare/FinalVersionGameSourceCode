package gamesource.State.CharacterState.firstWorldCharacter;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class shovelKnight extends BaseAppState {

    private BulletAppState bullet;
    private PhysicsSpace physics;
    private Spatial Knight;
    private CapsuleCollisionShape capsuleShape=new CapsuleCollisionShape();
    private CharacterControl knightControl ;
    private float stepHeight=0.01f;
    private Node rootNode=new Node("shoveKnight");
    private SimpleApplication app;
    private Node character;
    private float radius;

    private float height;

    private float modelY=0f;
    private Vector3f place=new Vector3f(0,0,0);

    protected void initialize(Application application){
        app=(SimpleApplication)application;


        initModel();
        initPhysics();



        knightControl.setPhysicsLocation(place);
    }


    public shovelKnight(){

    }

    public shovelKnight(Vector3f place){
        this.place=place;

    }
    public shovelKnight(Vector3f place,float modelY){
        this.place=place;
        this.modelY=modelY;
    }

    @Override
    protected void cleanup(Application application) {

    }

    @Override
    protected void onEnable() {
        app.getRootNode().attachChild(this.rootNode);
        physics.add(knightControl);
    }

    @Override
    protected void onDisable() {
        this.rootNode.removeFromParent();
        physics.remove(knightControl);
    }

    public void initPhysics(){
        bullet=app.getStateManager().getState(BulletAppState.class);
        physics=bullet.getPhysicsSpace();

        radius=0.4f;
        height=0.6f;

        Knight.move(0.06f,-(height/2+radius)+0f,0);

        character=new Node("Character");
        rootNode.attachChild(character);
        character.attachChild(Knight);

        capsuleShape=new CapsuleCollisionShape(radius,height,1);

        knightControl=new CharacterControl(capsuleShape,stepHeight);
        character.addControl(knightControl);

        knightControl.setFallSpeed(55);
        knightControl.setPhysicsLocation(new Vector3f(0,height/2+radius,0));
        knightControl.setGravity(new Vector3f(0,-9.81f,0));

        //physics.add(knightControl);


        character.setLocalTranslation(0, height / 2 + radius, 0);
        character.move(0.06f,-(height/2+radius)+3,0);

        rootNode.attachChild(character);
    }
    public void initModel(){
            Knight = app.getAssetManager().loadModel("character/shovelKnight/scene.j3o");
        Knight.setName("Knight");
        Knight.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        Knight.scale(0.27f);
        Knight.rotate(0,modelY,0f);

    }
}
