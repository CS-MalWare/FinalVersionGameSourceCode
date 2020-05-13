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

public class girlState extends BaseAppState implements ActionListener {

    private BulletAppState bullet;
    private Spatial girl;
    private CapsuleCollisionShape capsuleShape=new CapsuleCollisionShape();
    private CharacterControl girlControl ;
    private float stepHeight=0.01f;
    private AnimControl animControl;
    private AnimChannel animChannel;

    private PhysicsSpace physics;

    private Node rootNode=new Node("girl");

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

        girlControl.setPhysicsLocation(place);

        //initKeys();
        //initChaseCamera();
    }
    public girlState(){

    }
    public girlState(Vector3f place){
        this.place=place;
    }
    public girlState(Vector3f place,float modelY){
        this.place=place;
        this.modelY=modelY;
    }
    public void initModel(){
        girl=app.getAssetManager().loadModel("character/girl/girl.j3o");
        girl.setName("girl");
        girl.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        girl.scale(3.3f);
        girl.rotate(0f,modelY,0f);

    }
    public void initPhysics(){
        bullet=app.getStateManager().getState(BulletAppState.class);
        physics=bullet.getPhysicsSpace();

        radius=0.4f;
        height=0.6f;

        girl.move(0.06f,-(height/2+radius)+0.03f,0);

        character=new Node("Character");
        rootNode.attachChild(character);
        character.attachChild(girl);

        capsuleShape=new CapsuleCollisionShape(radius,height,1);

        girlControl=new CharacterControl(capsuleShape,stepHeight);
        character.addControl(girlControl);

        girlControl.setFallSpeed(55);
        girlControl.setPhysicsLocation(new Vector3f(0,height/2+radius,0));
        girlControl.setGravity(new Vector3f(0,-9.81f,0));

        //physics.add(girlControl);


        character.setLocalTranslation(0, height / 2 + radius, 0);
        character.move(0.06f,-(height/2+radius),0);


        character.setLocalTranslation(3.764972f, -4.4763145f, 6.0633626f);

        rootNode.attachChild(character);
    }
    public void initAnim(){


        scene=(Node)girl;

        bip001=(Node)scene.getChild("rootgrp");

        //bip001.getChild("AnimControl");


        animControl=bip001.getControl(AnimControl.class);

        //AnimControl control = (AnimControl)spatial.getControl(0);

        System.out.println(animControl.getAnimationNames()+"zzzzzzz");

        animChannel=animControl.createChannel();

        //spatial.move(0, 1, 0);


        animChannel.setAnim("rootgrp|Take 001|BaseLayer");
    }
    @Override
    protected void cleanup(Application application) {

    }
    @Override
    protected void onEnable() {
        app.getRootNode().attachChild(this.rootNode);
        physics.add(girlControl);
    }

    @Override
    protected void onDisable() {

        this.rootNode.removeFromParent();
        physics.remove(girlControl);
    }

    @Override
    public void onAction(String s, boolean b, float v) {

    }
}
