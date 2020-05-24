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

public class darkSolidier extends BaseAppState implements ActionListener {
    private BulletAppState bullet;

    private Spatial solidier;
    private CapsuleCollisionShape capsuleShape=new CapsuleCollisionShape();
    private CharacterControl solidControl ;
    private float stepHeight=0.01f;
    private AnimControl animControl;
    private AnimChannel animChannel;


    private PhysicsSpace physics;

    private Node rootNode=new Node("Solidier");

    private SimpleApplication app;

    private Node character;

    private float radius;

    private float height;

    private Node scene;

    private Node bip001;

    private float timer=0;

    private int model=1;

    private float rotateX=1f,rotateY=0f,rotateZ=-1f,modelY=5.4f;

    private Vector3f place=new Vector3f(0,0,0);

    private int type=1;

    private BoundingVolume solid;

    protected void initialize(Application application){
        app=(SimpleApplication)application;

        initModel();
        initPhysics();

        initAnim();

        solidControl.setPhysicsLocation(place);

        //initKeys();
        //initChaseCamera();
    }
    public darkSolidier(int model){
        this.model=model;
    }
    public darkSolidier(int model,float rotateX,float rotateY,float rotateZ){
        //app=(SimpleApplication)application;
        this.model=model;
        this.rotateX=rotateX;
        this.rotateY=rotateY;
        this.rotateZ=rotateZ;

    }

    public darkSolidier(int model,Vector3f place){
        //app=(SimpleApplication)application;
        this.model=model;
        this.place=place;

    }
    public darkSolidier(int model,Vector3f place,float modelY){
        this.place=place;
        this.model=model;
        this.modelY=modelY;
    }
    public darkSolidier(int model,Vector3f place,float modelY,float rotateX,float rotateZ,float rotateY){
        this.model=model;
        this.place=place;
        this.modelY=modelY;
        this.rotateX=rotateX;
        this.rotateY=rotateY;
        this.rotateZ=rotateZ;

    }
    public darkSolidier(int model,int type){
        //app=(SimpleApplication)application;
        this.model=model;
        this.type=type;
    }
    public darkSolidier(int model,float rotateX,float rotateY,float rotateZ,int type){
        //app=(SimpleApplication)application;
        this.model=model;
        this.rotateX=rotateX;
        this.rotateY=rotateY;
        this.rotateZ=rotateZ;
        this.type=type;

        //initModel();
        //initPhysics();

        //initAnim();

        //solidControl.setPhysicsLocation(new Vector3f(75.06459f, -29.602854f, 3.6023405f));

        //model=1;
        //initKeys();
        //initChaseCamera();
    }

    public darkSolidier(int model,Vector3f place,int type){
        //app=(SimpleApplication)application;
        this.model=model;
        this.place=place;
        this.type=type;
        //initModel();
        //initPhysics();

        //initAnim();

        //solidControl.setPhysicsLocation(place);
        //initKeys();
        //initChaseCamera();
    }
    public darkSolidier(int model,Vector3f place,float modelY,int type){
        this.place=place;
        this.model=model;
        this.modelY=modelY;
        this.type=type;
        //initModel();
        //initPhysics();

        //initAnim();

        //solidControl.setPhysicsLocation(new Vector3f(75.06459f, -29.602854f, 3.6023405f));
        //initKeys();
        //initChaseCamera();
    }
    public darkSolidier(int model,Vector3f place,float modelY,float rotateX,float rotateZ,float rotateY,int type){
        this.place=place;
        this.model=model;
        this.modelY=modelY;
        this.rotateX=rotateX;
        this.rotateY=rotateY;
        this.rotateZ=rotateZ;
        this.type=type;
    }


    public void initModel(){
        solidier = app.getAssetManager().loadModel("character/solidier/darksolidier.j3o");
        solidier.setName("Solidier");
        solidier.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        solidier.scale(0.015f);
        solidier.rotate(-1.5f,modelY,-0.03f);
        solid=solidier.getWorldBound();
    }

    public BoundingVolume get(){
        return solid;
    }

    public void initPhysics(){
        bullet=app.getStateManager().getState(BulletAppState.class);
        physics=bullet.getPhysicsSpace();

        radius=0.4f;
        height=0.6f;

        solidier.move(0.06f,-(height/2+radius)+0.03f,0);

        character=new Node("Character");
        rootNode.attachChild(character);
        character.attachChild(solidier);

        capsuleShape=new CapsuleCollisionShape(radius,height,1);

        solidControl=new CharacterControl(capsuleShape,stepHeight);
        character.addControl(solidControl);

        solidControl.setFallSpeed(55);
        solidControl.setPhysicsLocation(new Vector3f(0,height/2+radius,0));
        solidControl.setGravity(new Vector3f(0,-9.81f,0));

        //physics.add(solidControl);


        character.setLocalTranslation(0, height / 2 + radius, 0);
        character.move(0.06f,-(height/2+radius),0);


        character.setLocalTranslation(3.764972f, -4.4763145f, 6.0633626f);

        rootNode.attachChild(character);
    }

    public void initAnim(){

        scene=(Node)solidier;
        bip001=(Node)scene.getChild("Bip001");
        animControl=bip001.getControl(AnimControl.class);
        //System.out.println(animControl.getAnimationNames());

        animChannel=animControl.createChannel();
        animChannel.setAnim("walk");
    }

    @Override
    protected void cleanup(Application application) {

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
                solidControl.setWalkDirection(direction);
                timer = timer - tpf;
                if (timer < 6.1) {
                    timer = 0;
                    solidier.rotate(0, 0f, 3f);
                }
            } else {
                Vector3f direction = new Vector3f(-rotateX, -rotateY,
                        -rotateZ);
                //walkDirection.addLocal(direction);
                direction.y = 0;
                direction.normalizeLocal();
                direction.multLocal(0.01f);
                solidControl.setWalkDirection(direction);
                timer = timer + tpf;
                if (timer >= 6) {
                    timer = 12;
                    solidier.rotate(0, 0, -3f);
                }
            }
        }
        else if(model==2){
            if(timer<3) {
                //solidier.rotate(rotateX, rotateY, rotateZ);
                timer = timer + tpf;
                attack();
                if(timer>2.9){
                    timer=6;
                }
            }
            else{
                //solidier.rotate(rotateX, rotateY, rotateZ);
                timer = timer - tpf;
                takeDamage();
                if(timer<3.1){
                    timer=0;
                }
            }
        }else if(model==3){
            if(timer>3) {
                //solidier.rotate(rotateX, rotateY, rotateZ);
                timer = timer -tpf;
                attack2();
                if(timer<3.1){
                    timer=0;
                }
            }
            else{
                //solidier.rotate(rotateX, rotateY, rotateZ);
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

    @Override
    protected void onEnable() {
        app.getRootNode().attachChild(this.rootNode);
        physics.add(solidControl);
    }

    @Override
    protected void onDisable() {

        this.rootNode.removeFromParent();
        physics.remove(solidControl);
    }

    @Override
    public void onAction(String s, boolean b, float v) {

    }
}
