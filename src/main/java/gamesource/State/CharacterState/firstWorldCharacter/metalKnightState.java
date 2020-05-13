package gamesource.State.CharacterState.firstWorldCharacter;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.input.controls.ActionListener;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class metalKnightState extends BaseAppState implements ActionListener {

    private BulletAppState bullet;
    private Spatial knight;
    private CapsuleCollisionShape capsuleShape=new CapsuleCollisionShape();
    private CharacterControl knightControl ;
    private float stepHeight=0.01f;
    private AnimControl animControl;
    private AnimChannel animChannel;

    private PhysicsSpace physics;

    private Node rootNode=new Node("Knight");

    private SimpleApplication app;

    private Node character;

    private float radius;

    private float height;

    private Node scene;

    private Node bip001;


    private float rotateX=0f,rotateY=0f,rotateZ=0f,modelY=0f;

    private Vector3f place=new Vector3f(0,0,0);

    protected void initialize(Application application){
        app=(SimpleApplication)application;

        System.out.println(1);
        initModel();
        initPhysics();

        initAnim();

        knightControl.setPhysicsLocation(place);

        //initKeys();
        //initChaseCamera();
    }
    public metalKnightState(){

    }
    public metalKnightState(Vector3f place){
        this.place=place;
    }
    public metalKnightState(Vector3f place,float modelY){
        this.place=place;
        this.modelY=modelY;
    }
    public void initModel(){
        knight=app.getAssetManager().loadModel("character/metalknight/scene.j3o");
        knight.setName("metalKnight");
        knight.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        knight.scale(2.8f);
        knight.rotate(0f,modelY,0f);

    }
    public void initPhysics(){
        bullet=app.getStateManager().getState(BulletAppState.class);
        physics=bullet.getPhysicsSpace();

        radius=0.4f;
        height=0.6f;

        knight.move(0.06f,-(height/2+radius)+1.1f,0);

        character=new Node("Character");
        rootNode.attachChild(character);
        character.attachChild(knight);

        capsuleShape=new CapsuleCollisionShape(radius,height,1);

        knightControl=new CharacterControl(capsuleShape,stepHeight);
        character.addControl(knightControl);

        knightControl.setFallSpeed(55);
        knightControl.setPhysicsLocation(new Vector3f(0,height/2+radius,0));
        knightControl.setGravity(new Vector3f(0,-9.81f,0));

        //physics.add(knightControl);


        character.setLocalTranslation(0, height / 2 + radius, 0);
        character.move(0.06f,-(height/2+radius),0);


        character.setLocalTranslation(3.764972f, -4.4763145f, 6.0633626f);

        rootNode.attachChild(character);
    }
    public void initAnim(){


        scene=(Node)knight;

        bip001=(Node)scene.getChild("bip001");

        //bip001.getChild("AnimControl");


        animControl=bip001.getControl(AnimControl.class);

        //AnimControl control = (AnimControl)spatial.getControl(0);

        System.out.println(animControl.getAnimationNames()+"zzzzzzz");

        animChannel=animControl.createChannel();

        //spatial.move(0, 1, 0);


        animChannel.setAnim("Take 001");
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

    @Override
    public void onAction(String s, boolean b, float v) {

    }
}
