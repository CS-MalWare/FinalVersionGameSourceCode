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

public class blackBoyState extends BaseAppState implements ActionListener {
    private BulletAppState bullet;
    private Spatial boy;
    private CapsuleCollisionShape capsuleShape=new CapsuleCollisionShape();
    private CharacterControl boyControl ;
    private float stepHeight=0.01f;
    private AnimControl animControl;
    private AnimChannel animChannel;

    private PhysicsSpace physics;

    private Node rootNode=new Node("blackBoy");

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

        boyControl.setPhysicsLocation(place);

        //initKeys();
        //initChaseCamera();
    }
    public blackBoyState(){

    }
    public blackBoyState(Vector3f place){
        this.place=place;
    }
    public blackBoyState(Vector3f place,float modelY){
        this.place=place;
        this.modelY=modelY;
    }
    public void initModel(){
        boy=app.getAssetManager().loadModel("character/blackBoy/scene.j3o");
        boy.setName("boy");
        boy.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        boy.scale(0.14f);
        boy.rotate(0f,modelY,0f);

    }
    public void initPhysics(){
        bullet=app.getStateManager().getState(BulletAppState.class);
        physics=bullet.getPhysicsSpace();

        radius=0.4f;
        height=0.6f;

        boy.move(0.06f,-(height/2+radius)+0.03f,0);

        character=new Node("Character");
        rootNode.attachChild(character);
        character.attachChild(boy);

        capsuleShape=new CapsuleCollisionShape(radius,height,1);

        boyControl=new CharacterControl(capsuleShape,stepHeight);
        character.addControl(boyControl);

        boyControl.setFallSpeed(55);
        boyControl.setPhysicsLocation(new Vector3f(0,height/2+radius,0));
        boyControl.setGravity(new Vector3f(0,-9.81f,0));

        //physics.add(boyControl);


        character.setLocalTranslation(0, height / 2 + radius, 0);
        character.move(0.06f,-(height/2+radius),0);


        character.setLocalTranslation(3.764972f, -4.4763145f, 6.0633626f);

        rootNode.attachChild(character);
    }
    public void initAnim(){


        scene=(Node)boy;

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

        physics.add(boyControl);
    }

    @Override
    protected void onDisable() {

        this.rootNode.removeFromParent();
        physics.remove(boyControl);
    }

    @Override
    public void onAction(String s, boolean b, float v) {

    }
}
