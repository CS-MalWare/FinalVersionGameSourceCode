package gamesource.State.CharacterState.enemies.sixth;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.bounding.BoundingVolume;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.input.controls.ActionListener;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class darkKnight extends BaseAppState implements ActionListener {
    private BulletAppState bullet;
    private Spatial Knight;
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

    private float timer=0;

    private int model=1,type=1;

    private float rotateX=1f,rotateY=0f,rotateZ=-1f,modelY=5.4f;

    private Vector3f place=new Vector3f(0,0,0);

    private BoundingVolume knight;

    protected void initialize(Application application){
        app=(SimpleApplication)application;


        initModel();
        initPhysics();

        initAnim();


        knightControl.setPhysicsLocation(place);
    }

    public darkKnight(int model){
        //app=(SimpleApplication)application;
        this.model=model;

    }
    public darkKnight(int model,float rotateX,float rotateY,float rotateZ){
        this.model=model;
        this.rotateX=rotateX;
        this.rotateY=rotateY;
        this.rotateZ=rotateZ;

    }

    public darkKnight(int model,Vector3f place){
        this.model=model;
        this.place=place;

    }
    public darkKnight(int model,Vector3f place,float modelY){
        this.place=place;
        this.model=model;
        this.modelY=modelY;
    }
    public darkKnight(int model,Vector3f place,float modelY,float rotateX,float rotateZ,float rotateY){
        this.modelY=modelY;
        this.model=model;
        this.place=place;
        this.rotateX=rotateX;
        this.rotateY=rotateY;
        this.rotateZ=rotateZ;

    }
    public darkKnight(int model,int type){
        //app=(SimpleApplication)application;
        this.model=model;
        this.type=type;
    }
    public darkKnight(int model,float rotateX,float rotateY,float rotateZ,int type){
        //app=(SimpleApplication)application;
        this.model=model;
        this.rotateX=rotateX;
        this.rotateY=rotateY;
        this.rotateZ=rotateZ;
        this.type=type;

    }

    public darkKnight(int model,Vector3f place,int type){
        //app=(SimpleApplication)application;
        this.model=model;
        this.place=place;
        this.type=type;
    }
    public darkKnight(int model,Vector3f place,float modelY,int type){
        this.place=place;
        this.model=model;
        this.modelY=modelY;
        this.type=type;
    }
    public darkKnight(int model,Vector3f place,float modelY,float rotateX,float rotateZ,float rotateY,int type){
        this.place=place;
        this.model=model;
        this.modelY=modelY;
        this.rotateX=rotateX;
        this.rotateY=rotateY;
        this.rotateZ=rotateZ;
        this.type=type;
    }
    @Override
    protected void cleanup(Application application) {

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

    public void initAnim(){

        scene=(Node)Knight;
        bip001=(Node)scene.getChild("Bip001");
        animControl=bip001.getControl(AnimControl.class);
        System.out.println(animControl.getAnimationNames());

        animChannel=animControl.createChannel();
        animChannel.setAnim("walk");
    }
    @Override
    public void update(float tpf){
        if(model==1) {
            if (timer >= 6) {
                Vector3f direction = new Vector3f(rotateX, rotateY,
                        rotateZ);
                //direction.addLocal(direction);
                direction.y = 0;
                direction.normalizeLocal();
                direction.multLocal(0.01f);
                knightControl.setWalkDirection(direction);
                timer = timer - tpf;
                if (timer < 6.1) {
                    timer = 0;
                    Knight.rotate(0, 0f, 3f);
                }
            } else {
                Vector3f direction = new Vector3f(-rotateX, -rotateY,
                        -rotateZ);
                //walkDirection.addLocal(direction);
                direction.y = 0;
                direction.normalizeLocal();
                direction.multLocal(0.01f);
                knightControl.setWalkDirection(direction);
                timer = timer + tpf;
                if (timer >= 6) {
                    timer = 12;
                    Knight.rotate(0, 0, -3f);
                }
            }
        }
        else if(model==2){
            if(timer<3) {
                //Knight.rotate(rotateX, rotateY, rotateZ);
                timer = timer + tpf;
                attack();
                if(timer>2.9){
                    timer=6;
                }
            }
            else{
                //.rotate(rotateX, rotateY, rotateZ);
                timer = timer - tpf;
                takeDamage();
                if(timer<3.1){
                    timer=0;
                }
            }
        }else if(model==3){
            if(timer>3) {
                //Knight.rotate(rotateX, rotateY, rotateZ);
                timer = timer -tpf;
                attack2();
                if(timer<3.1){
                    timer=0;
                }
            }
            else{
                //Knight.rotate(rotateX, rotateY, rotateZ);
                timer = timer + tpf;
                takeDamage2();
                if(timer>2.9){
                    timer=6;
                }
            }
        }else if(model==4){
            takeDamage2();
        }else if(model==5){
            idle();
        }
        //System.out.println(animChannel.getAnimationName());
    }
    protected void attack(){
        if(animChannel.getAnimationName().equals("attack")==false){
            animChannel.setAnim("attack");
        }
    }
    protected void attack2(){
        if(animChannel.getAnimationName().equals("attack2")==false){
            animChannel.setAnim("attack2");
        }
    }
    protected void takeDamage(){
        if(animChannel.getAnimationName().equals("takeDamage")==false){
            animChannel.setAnim("takeDamage");
        }
    }protected void takeDamage2(){
        if(animChannel.getAnimationName().equals("takeDamage2")==false){
            animChannel.setAnim("takeDamage2");
        }
    }protected void idle(){
        if(animChannel.getAnimationName().equals("idle")==false){
            animChannel.setAnim("idle");
        }
    }

    public void initModel(){
        Knight = app.getAssetManager().loadModel("character/Knight/darkknight.j3o");
        Knight.setName("Knight");
        Knight.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        Knight.scale(0.017f);
        Knight.rotate(-1.5f,modelY,-0.03f);
        knight=Knight.getWorldBound();
    }

    public BoundingVolume get(){
        return knight;
    }
    @Override
    protected void onEnable() {
        app.getRootNode().attachChild(this.rootNode);
        physics.add(knightControl);
        knight=Knight.getWorldBound();
    }

    @Override
    protected void onDisable() {
        this.rootNode.removeFromParent();
        physics.remove(knightControl);
        knight=null;
    }

    @Override
    public void onAction(String s, boolean b, float v) {

    }
}
