package gamesource.State.CharacterState.enemies.third;

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
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Fish6State  extends BaseAppState {
    private BulletAppState bullet;
    private Spatial robot;
    private CapsuleCollisionShape capsuleShape=new CapsuleCollisionShape();
    private CharacterControl robotControl ;
    private float stepHeight=0.01f;
    private AnimControl animControl;
    private AnimChannel animChannel;

    private PhysicsSpace physics;

    private Node rootNode=new Node("walkRobot");

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

    private BoundingVolume plane;

    protected void initialize (Application application){
        app=(SimpleApplication)application;

        initModel();
        initPhysics();
        initAnim();

        robotControl.setPhysicsLocation(place);
    }

    @Override
    protected void cleanup(Application application) {

    }

    public Fish6State(Vector3f place){
        this.place=place;
    }

    public Fish6State(Vector3f place, int modelY){
        this.place=place;
        this.modelY=modelY;
    }

    public Fish6State(Vector3f place,float modelY,float rotateX,float rotateZ,float rotateY){
        this.modelY=modelY;
        this.place=place;
        this.rotateX=rotateX;
        this.rotateY=rotateY;
        this.rotateZ=rotateZ;
    }
    public void initPhysics(){
        bullet=app.getStateManager().getState(BulletAppState.class);
        physics=bullet.getPhysicsSpace();

        radius=0.4f;
        height=0.6f;

        robot.move(0.06f,-(height/2+radius)+1.2f,0);

        character=new Node("Character");
        rootNode.attachChild(character);
        character.attachChild(robot);

        capsuleShape=new CapsuleCollisionShape(radius,height,1);

        robotControl=new CharacterControl(capsuleShape,stepHeight);
        character.addControl(robotControl);

        robotControl.setFallSpeed(55);
        robotControl.setPhysicsLocation(new Vector3f(0,height/2+radius,0));
        robotControl.setGravity(new Vector3f(0,-9.81f,0));

        //physics.add(knightControl);


        character.setLocalTranslation(0, height / 2 + radius, 0);
        character.move(0.06f,-(height/2+radius)+3,0);

        rootNode.attachChild(character);
    }
    public void initAnim(){

        scene=(Node)robot;
        bip001=(Node)scene.getChild("Armature");
        animControl=bip001.getControl(AnimControl.class);
        System.out.println(animControl.getAnimationNames()+"fish6");

        animChannel=animControl.createChannel();
        animChannel.setAnim("Take 01");
        animChannel.setSpeed(2.0f);
    }
    public void initModel(){
        robot = app.getAssetManager().loadModel("Enemies/underWater/fish6.j3o");
        robot.setName("Knight");
        robot.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        robot.scale(1.2f);
        robot.rotate(0,modelY,0);
        plane=robot.getWorldBound();
    }
    public void update(float tpf){
        if (timer >= 6) {
            Vector3f direction = new Vector3f(rotateX, rotateY,
                    rotateZ);
            //direction.addLocal(direction);
            direction.y = 0;
            direction.normalizeLocal();
            direction.multLocal(0.01f);
            robotControl.setWalkDirection(direction);
            timer = timer - tpf;
            if (timer < 6.1) {
                timer = 0;
                robot.rotate(0, 3f, 0);
            }
        } else {
            Vector3f direction = new Vector3f(-rotateX, -rotateY,
                    -rotateZ);
            //walkDirection.addLocal(direction);
            direction.y = 0;
            direction.normalizeLocal();
            direction.multLocal(0.01f);
            robotControl.setWalkDirection(direction);
            timer = timer + tpf;
            if (timer >= 6) {
                timer = 12;
                robot.rotate(0, -3f, 0);
            }
        }
    }
    public BoundingVolume get(){
        return plane;
    }
    protected void onEnable() {
        app.getRootNode().attachChild(this.rootNode);
        physics.add(robotControl);
        plane=robot.getWorldBound();
    }
    protected void onDisable() {
        this.rootNode.removeFromParent();
        physics.remove(robotControl);
        plane=null;
    }
}