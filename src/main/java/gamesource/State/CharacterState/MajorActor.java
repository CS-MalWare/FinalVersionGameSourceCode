package gamesource.State.CharacterState;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.audio.AudioData;
import com.jme3.audio.AudioNode;
import com.jme3.bounding.BoundingVolume;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.font.BitmapText;
import com.jme3.input.ChaseCamera;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class MajorActor extends BaseAppState implements AnimEventListener {

    public final static String DEBUG = "debug";
    public final static String FORWARD = "forward";
    public final static String BACKWARD = "backward";
    public final static String LEFT = "left";
    public final static String RIGHT = "right";
    public final static String JUMP = "jump";
    private Vector3f walkDirection = new Vector3f();
    private boolean left = false, right = false, up = false, down = false;
    private Vector3f camDir = new Vector3f();
    private Vector3f camLeft = new Vector3f();
    private Camera cam;
    private FlyByCamera flycam;
    private InputManager inputManager;
    private BulletAppState bullet;
    private Vector3f camPlace;
    private Quaternion camRot = new Quaternion();
    private Vector3f walkDir = new Vector3f();

    private Spatial MajorStar;
    private CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape();      //胶囊体作为玩家的碰撞形状
    private CharacterControl player;                                            //玩家动作控制器，用这种控制方法，可以大幅度减少actionlistener
    private float stepHeight = 0.01f;

    private AnimControl animControl;
    private AnimChannel animChannel;

    private PhysicsSpace physics;

    private Node rootNode = new Node("MajorStar");
    private Node camNode = new Node("Camera");

    private SimpleApplication app;

    private Node character;

    private float radius;                                                       //胶囊半径

    private float height;                                                       //胶囊的身高

    private Node scene;

    private Node bip001;                                                        //这个是包含动作的骨骼部分

    private Ray ray;

    private BoundingVolume major;

    private ChaseCamera chaseCam;

    private int CamSituation = 1;
    private float jump = 6;
    private int camSituation2 = 1;
    private Vector3f place;

    private AudioNode run;

    @Override
    protected void initialize(Application application) {
        app = (SimpleApplication) application;
        bullet = app.getStateManager().getState(BulletAppState.class);
        cam = app.getCamera();
        flycam = app.getFlyByCamera();
        inputManager = app.getInputManager();
        physics = bullet.getPhysicsSpace();
        rootNode = app.getRootNode();
        initModel();
        initAnim();
        initPhysics();
        initMusic();

        CamSituation = 0;
        camSituation2 = 0;

        chaseCam.setDragToRotate(false);
        flycam.setDragToRotate(false);
        camNode.removeControl(chaseCam);
    }

    public void height(float jump) {
        this.jump = jump;
    }

    public float getX(){
        return player.getPhysicsLocation().x;
    }
    public float getY(){
        return player.getPhysicsLocation().y;
    }
    public float getZ(){
        return player.getPhysicsLocation().z;
    }
    public void initModel() {
        MajorStar = app.getAssetManager().loadModel("LeadingActor/MajorActor4.j3o");
        MajorStar.setName("MajorStar");
        MajorStar.setShadowMode(RenderQueue.ShadowMode.Cast);
        MajorStar.scale(0.015f);
        MajorStar.rotate(-1.5f, 0f, -0.03f);
    }

    public void initAnim() {
        scene = (Node) MajorStar;
        bip001 = (Node) scene.getChild("bip001");

        animControl = bip001.getControl(AnimControl.class);
        //System.out.println(animControl.getAnimationNames());

        animChannel = animControl.createChannel();
        animControl.addListener(this);
        animChannel.setAnim("idle");
    }

    public void initPhysics() {
        radius = 0.4f;
        height = 0.6f;

        character = new Node("Character");
        MajorStar.move(0.06f, -(height / 2 + radius) + 0.0f, 0);
        character.attachChild(MajorStar);

        capsuleShape = new CapsuleCollisionShape(radius, height, 1);

        player = new CharacterControl(capsuleShape, stepHeight);
        character.addControl(player);

        //player.setJumpSpeed(1);
        player.setFallSpeed(55);
        player.setPhysicsLocation(new Vector3f(0, height / 2 + radius, 0));
        player.setGravity(new Vector3f(0, -9.81f, 0));

        physics.add(player);

        character.setLocalTranslation(0, height / 2 + radius, 0);
        character.move(0.06f, -(height / 2 + radius) + 3, 0);

        character.attachChild(camNode);
        camNode.setLocalTranslation(0, height / 2 + 0.8f, radius);
        //rootNode.attachChild(character);

        ray = new Ray();
        ray.setLimit(0.02f);

        major = MajorStar.getWorldBound();
        //initKeys();
        initChaseCamera();
        player.setPhysicsLocation(place);
    }

    public void initMusic() {
        run = new AudioNode(app.getAssetManager(), "Sound/run/run.wav", AudioData.DataType.Stream);
        run.setLooping(true);
        run.setPositional(false);
        run.setVolume(6);
        run.setPitch(0.7f);
        cam.lookAtDirection(new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
    }


    public MajorActor() {
        place = new Vector3f(91.454414f, -32.017883f, 16.952227f);
    }

    public MajorActor(Vector3f place) {
        this.place = place;
    }

    public Ray get() {
        return ray;
    }

    public BoundingVolume getMajor() {
        return major;
    }

    public void mouseChange() {
        if (camSituation2 == 1) {
            if (CamSituation == 1) {
                chaseCam.setDragToRotate(true);
            } else {
                flycam.setDragToRotate(true);
            }
            camSituation2 = 0;
        } else {
            if (CamSituation == 1) {
                chaseCam.setDragToRotate(false);
            } else {
                flycam.setDragToRotate(false);
            }
            camSituation2 = 1;
        }
    }

    public void update(float tpf) {
        ray.setOrigin(cam.getLocation());
        ray.setDirection(cam.getDirection());
        if (walkDir.lengthSquared() != 0) {

            camDir.set(cam.getDirection());
            camDir.y = 0;
            camDir.normalizeLocal();

            camRot.lookAt(camDir, Vector3f.UNIT_Y);

            camRot.mult(walkDir, camDir);

            player.setViewDirection(camDir);

            camDir.multLocal(0.1f);
        } else {
            camDir.set(0, 0, 0);
        }

        player.setWalkDirection(camDir);
        cam.setLocation(camNode.getWorldTranslation());
        if (player.getPhysicsLocation().y < -50) {
            player.setPhysicsLocation(place);
        }
    }

    /*private void initKeys(){
        inputManager.addMapping(DEBUG, new KeyTrigger(KeyInput.KEY_F1));
        inputManager.addMapping(LEFT, new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping(RIGHT, new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping(FORWARD, new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping(BACKWARD, new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping(JUMP, new KeyTrigger(KeyInput.KEY_SPACE));

        inputManager.addListener(this,DEBUG,LEFT,RIGHT,FORWARD,BACKWARD,JUMP);
    }*/


    @Override
    protected void cleanup(Application application) {
    }

    @Override
    protected void onEnable() {
        rootNode.attachChild(character);
        physics.add(player);
        //run.play();
    }

    @Override
    protected void onDisable() {
        rootNode.detachChild(character);
        physics.remove(player);
        run.stop();
    }

    /* public void onAction(String name, boolean isPressed, float tpf) {
         if (DEBUG.equals(name) && isPressed) {
             boolean debugEnabled = bullet.isDebugEnabled();
             bullet.setDebugEnabled(!debugEnabled);
         } else if (LEFT.equals(name)) {
             left = isPressed;
         } else if (RIGHT.equals(name)) {
             right = isPressed;
         } else if (FORWARD.equals(name)) {
             up = isPressed;
         } else if (BACKWARD.equals(name)) {
             down = isPressed;
         } else if (JUMP.equals(name) && isPressed) {
             player.jump();
         }
     }*/
    private void initChaseCamera() {
        chaseCam = new ChaseCamera(cam, camNode, inputManager);
        chaseCam.setInvertVerticalAxis(true);
        chaseCam.setMinDistance(0.1f);
        chaseCam.setDefaultDistance(10f);
        chaseCam.setDragToRotate(false);
    }

    public void change() {
        if (CamSituation == 1) {
            camNode.removeControl(chaseCam);
            CamSituation = 0;
        } else {
            camNode.addControl(chaseCam);
            CamSituation = 1;
        }

    }

    public void jump() {
        if (player.onGround()) {
            player.jump(new Vector3f(0, jump, 0));

            animChannel.setAnim("jump");
            animChannel.setLoopMode(LoopMode.DontLoop);
            animChannel.setSpeed(1.8f);
        }
    }

    public void idle() {
        walkDir.set(0, 0, 0);
        //if(player.onGround()){
        animChannel.setAnim("idle");
        run.stop();
        //}
    }

    public void walk(Vector3f dir) {
        if (dir != null) {
            if (walkDir.lengthSquared() == 0) {
                animChannel.setAnim("run");
                animChannel.setSpeed(1.4f);
                run.play();
            }

            dir.normalizeLocal();

            walkDir.set(dir);
        }
    }

    @Override
    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
        //System.out.println("cycle done");
        if ("jump".equals(animName)) {

            //System.out.println("end end edn");
            if (walkDir.lengthSquared() != 0) {
                channel.setAnim("run");
                channel.setLoopMode(LoopMode.Loop);
                channel.setSpeed(1f);
                run.play();
            } else {
                channel.setAnim("idle");
                channel.setLoopMode(LoopMode.Loop);
                run.stop();
            }
        }
    }

    public void setPlace(Vector3f place) {
        this.place = place;
        player.setPhysicsLocation(place);
    }

    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {

    }
}
