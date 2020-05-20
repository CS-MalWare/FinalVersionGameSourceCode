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
import gamesource.State.CharacterState.enemies.BatState;
import gamesource.State.CharacterState.enemies.BlueSkeletonState;
import gamesource.State.CharacterState.enemies.RedSkeletonState;
import gamesource.State.CharacterState.enemies.forth.BossKnight;
import gamesource.State.CharacterState.enemies.forth.StoneMan;
import gamesource.State.CharacterState.enemies.forth.boss;
import gamesource.State.SpecialEffect.ThirdWorldLight;
import gamesource.State.SpecialEffect.Water;
import gamesource.State.SpecialEffect.makeCross;
import gamesource.State.controlState.InputAppState;
import gamesource.State.controlState.PositionInputState;
import gamesource.State.mapState.Chest;
import gamesource.State.mapState.ForthWorldMap;
import gamesource.State.mapState.SkyBox;
import gamesource.uiState.bagstate.BagAppState;
import gamesource.uiState.menustate.MenuAppState;
import gamesource.uiState.shopstate.ShopAppState;

import java.util.ArrayList;

public class ForthState extends BaseAppState {
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
    ForthWorldMap world=new ForthWorldMap();
    // MajorActor major=new MajorActor(new Vector3f(-0.5884632f, -25.645144f, 76.421844f));
    Chest c1 = new Chest(new Vector3f(9.952984f, -31.962004f, 56.09926f),4f);
    Chest c2 = new Chest(new Vector3f(-10.413138f, -29.553904f, 28.508766f));
    Chest c3 = new Chest(new Vector3f(12.185162f, -18.157299f, -74.07405f),4.2f);
    private StoneMan man1=new StoneMan(new Vector3f(1.2305703f, -21.918747f, 31.115707f),4.5f,1,0,1);
    private boss bos=new boss(new Vector3f(0.2364038f, -12.214317f, -34.228657f),3.6f);
    private BatState bat1=new BatState(new Vector3f(-8.639356f, -21.22517f, 19.451141f),-0.5f);

    private BatState bat2=new BatState(new Vector3f(-16.774906f, -15.959639f, 18.43346f),1.1f);
    private BatState bat3=new BatState(new Vector3f(-14.036222f, -0.26350462f, -22.96706f),-0.5f);
    private BossKnight knight=new BossKnight(new Vector3f(2.0474744f, -21.482273f, 7.5591955f),-1.2f);
    private StoneMan man2=new StoneMan(new Vector3f(-5.5626683f, -15.747082f, -8.876474f),0.7f,1,0,1,"Attack1");
    private StoneMan man3=new StoneMan(new Vector3f(3.491192f, -22.307375f, 11.724836f),4.5f,1,0,1);
    private BlueSkeletonState ske1=new BlueSkeletonState(new Vector3f(-4.245868f, -11.549194f, -25.822964f),0.2f);
    private RedSkeletonState ske2=new RedSkeletonState(new Vector3f(-1.4215671f, -11.728199f, -24.200905f),-0.7f);
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
        state.attach(light);
        state.attach(bos);
        states.add(bos);
        state.attach(man1);
        states.add(man1);
        state.attach(bat1);
        states.add(bat1);
        state.attach(bat2);
        states.add(bat2);
        state.attach(bat3);
        states.add(bat3);
        state.attach(knight);
        states.add(knight);
        state.attach(man2);
        states.add(man2);
        state.attach(man3);
        states.add(man3);
        state.attach(ske1);
        states.add(ske1);
        state.attach(ske2);
        states.add(ske2);
        //state.attach(water);
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
        major.setPlace(new Vector3f(0f, 10f, 0f));
        major.setPlace(new Vector3f(1.5346308f, -18.545364f, 56.147945f));
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