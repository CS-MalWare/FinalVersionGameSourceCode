package gamesource.State.worldState;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.state.BaseAppState;
import com.jme3.bounding.BoundingVolume;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import gamesource.State.CharacterState.MajorActor;
import gamesource.State.CharacterState.enemies.*;
import gamesource.State.SpecialEffect.*;
import gamesource.State.controlState.InputAppState;
import gamesource.State.controlState.PositionInputState;
import gamesource.State.mapState.*;
import gamesource.uiState.bagstate.BagAppState;
import gamesource.uiState.menustate.MenuAppState;
import gamesource.uiState.shopstate.ShopAppState;

import java.util.ArrayList;

public class ThirdState  extends BaseAppState {
    public final static String talk="TALK";
    public final static Trigger TALK = new KeyTrigger(KeyInput.KEY_N);
    public final static String change="CHANGE";
    public final static Trigger CHANGECAMERA = new KeyTrigger(KeyInput.KEY_C);
    public final static String bag="BAG";
    public final static Trigger BAG = new KeyTrigger(KeyInput.KEY_B);
    public final static String move="MOVE";
    public final static Trigger MOVE=new KeyTrigger(KeyInput.KEY_W);
    private InputManager inputManager;

    private AppStateManager state;

    private int canmove=1;

    Ray ray;

    private Camera cam;

    MajorActor major;
    InputAppState input;
    ThirdWorldMap world=new ThirdWorldMap();
    // MajorActor major=new MajorActor(new Vector3f(-0.5884632f, -25.645144f, 76.421844f));
    Chest c1 = new Chest(new Vector3f(9.952984f, -31.962004f, 56.09926f),4f);
    Chest c2 = new Chest(new Vector3f(-10.413138f, -29.553904f, 28.508766f));
    Chest c3 = new Chest(new Vector3f(12.185162f, -18.157299f, -74.07405f),4.2f);

    //这个地图暂时这5种怪物，想新加new一下加入state里面，构造函数第一个参数是位置，第二个是朝向，进入游戏需要先按c进入第一人称，再按f1来获取玩家坐标

    private DrunkCrab crab1=new DrunkCrab(new Vector3f(86.417725f, 3.0157287f, -5.2350335f),2f);

    private Fish1State fish1_1=new Fish1State(new Vector3f(93.7314f, -3.7759366f, 27.07812f), 5f);
    private Fish1State fish1_2 = new Fish1State(new Vector3f(46.98013f, 0.9620397f, -0.31717157f), -3f);
    private Fish1State fish1_3 = new Fish1State(new Vector3f(56.948456f, 3.351931f, -47.507305f), 3f);
    private Fish1State fish1_4 = new Fish1State(new Vector3f(12.754826f, -3.4226258f, -74.095726f), 1f);
    private Fish1State fish1_5 = new Fish1State(new Vector3f(-50.21668f, -5.0685973f, -71.16844f), 1.5f);
    private Fish2State fish2_1=new Fish2State(new Vector3f(77.26708f, 2.1770868f, 0.21428971f), 2f);
    private Fish2State fish2_2=new Fish2State(new Vector3f(-68.81787f, -7.400928f, -82.652245f), 3.7f);
    private Fish2State fish2_3=new Fish2State(new Vector3f(-104.77109f, -8.663721f, -96.10981f), -3.7f);
    private Fish2State fish2_4=new Fish2State(new Vector3f(-108.34749f, -7.848976f, -66.91676f), -5.7f);
    private Fish2State fish2_5=new Fish2State(new Vector3f(-95.21405f, -2.9428518f, 2.1157131f), -5.7f);
    private Fish3State fish3_1=new Fish3State(new Vector3f(0,35,0),6.4f);
    private Fish3State fish3_2=new Fish3State(new Vector3f(-79.33274f, -1.6759943f, 24.259945f),6.4f);
    private Fish3State fish3_3=new Fish3State(new Vector3f(-58.6319f, -1.6084806f, 50.47643f),-6.4f);
    private Fish3State fish3_4=new Fish3State(new Vector3f(-80.64096f, -6.567422f, 66.516266f),3.4f);

    private MushroomBug bu1=new MushroomBug(new Vector3f(60.75251f, 4.0023937f, -34.16455f),-4f);

    private StartTalk st=new StartTalk();

    private BoundingVolume maj;

    private float time=0;

    private int chan=0;

    private BagAppState bagState;
    private ShopAppState shopState;
    private MenuAppState menuState;
    private makeCross cross;
    private ThirdWorldLight light=new ThirdWorldLight();
    private Water water=new Water(22);

    private ArrayList<BaseAppState> states=new ArrayList<BaseAppState>();

    protected void initialize(Application application){
        state=application.getStateManager();
        cam=application.getCamera();
        state.attach(world);
        states.add(world);
        state.attach(new PositionInputState());
        input=state.getState(InputAppState.class);
        states.add(input);
        major=state.getState(MajorActor.class);
        states.add(major);
        bagState=state.getState(BagAppState.class);
        states.add(bagState);
        shopState=state.getState(ShopAppState.class);
        states.add(shopState);
        menuState=state.getState(MenuAppState.class);
        states.add(menuState);
        cross=state.getState(makeCross.class);
        states.add(cross);
        state.attach(fish1_1);
        state.attach(fish1_2);
        state.attach(fish1_3);
        state.attach(fish1_4);
        state.attach(fish1_5);
        state.attach(fish2_1);
        state.attach(fish2_2);
        state.attach(fish2_3);
        state.attach(fish2_4);
        state.attach(fish2_5);
        state.attach(fish3_1);
        state.attach(fish3_2);
        state.attach(fish3_3);
        state.attach(fish3_4);

        state.attach(bu1);
        state.attach(crab1);

        state.attach(light);
        state.attach(water);
        state.attach(new SkyBox());


        this.inputManager=application.getInputManager();
        inputManager.addMapping(talk,TALK);
        inputManager.addListener(st,talk);

        inputManager.addMapping(change,CHANGECAMERA);
        inputManager.addListener(st,change);

        inputManager.addMapping(bag,BAG);
        inputManager.addListener(st,bag);

        inputManager.addMapping(move,MOVE);
        inputManager.addListener(st,move);

        cam.lookAtDirection(new Vector3f(0,0,-1),new Vector3f(0,1,0));
        major.setPlace(new Vector3f(0f, 30f, 0f));
    }


    class StartTalk implements ActionListener {
        @Override
        public void onAction(String name,boolean isPressed,float tpf){
            if (change.equals(name) && isPressed) {
                System.out.println("change");
                major.change();
            }
        }
    }

    protected void cancel(){

    }

    public void change(){
        if(chan==0) {
            major.mouseChange();
            System.out.println("zzzz");
            major.mouseChange();
            System.out.println("hhhh");
            chan++;
        }
    }

    public void update(float tpf){
        if(chan==0) {
            time = time + tpf;
            if (time < 25 && time > 5) {
                change();
            }
        }
    }

    @Override
    protected void cleanup(Application application) {
    }

    @Override
    protected void onEnable() {
        inputManager.addMapping(talk,TALK);
        inputManager.addMapping(change,CHANGECAMERA);
        inputManager.addMapping(bag,BAG);
        inputManager.addMapping(move,MOVE);

        inputManager.addListener(st,talk);
        inputManager.addListener(st,change);
        inputManager.addListener(st,bag);
        inputManager.addListener(st,move);
    }

    @Override
    protected void onDisable() {

        System.out.println("second disable");
        inputManager.removeListener(st);
        inputManager.deleteTrigger(talk, TALK);
        inputManager.deleteTrigger(change, CHANGECAMERA);
        inputManager.deleteTrigger(bag, BAG);
        inputManager.deleteTrigger(move,MOVE);
        for (BaseAppState baseAppState : states) {
            if (!(baseAppState instanceof MenuAppState) && !(baseAppState instanceof BagAppState) && !(baseAppState instanceof ShopAppState)) {
                baseAppState.setEnabled(false);
                //state.detach(baseAppState);
            }

        }
        /*world.setEnabled(false);
        skeleton1.setEnabled(false);
        shanman.setEnabled(false);
        girl.setEnabled(false);
        skeleton2.setEnabled(false);
        music.setEnabled(false);
        snowRobot.setEnabled(false);
        boss.setEnabled(false);
        c1.setEnabled(false);
        c2.setEnabled(false);
        c3.setEnabled(false);
        light.setEnabled(false);
        sky.setEnabled(false);
        special.setEnabled(false);*/
    }
}