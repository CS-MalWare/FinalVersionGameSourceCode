package gamesource.State.worldState;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.state.BaseAppState;
import com.jme3.bounding.BoundingVolume;
import com.jme3.bullet.BulletAppState;
import com.jme3.collision.CollisionResults;
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
import gamesource.State.CharacterState.secondWorldCharacter.goblinGirlState;
import gamesource.State.CharacterState.secondWorldCharacter.shanmanState;
import gamesource.State.SpecialEffect.SecondWorldLight;
import gamesource.State.SpecialEffect.SecondWorldOtherSpecial;
import gamesource.State.SpecialEffect.makeCross;
import gamesource.State.controlState.InputAppState;
import gamesource.State.controlState.PositionInputState;
import gamesource.State.mapState.chest;
import gamesource.State.mapState.secondWorldMap;
import gamesource.State.mapState.skyBox2;
import gamesource.State.musicState.SecondBackMusic;
import gamesource.battleState.battle.Battle;
import gamesource.uiState.bagstate.BagAppState;
import gamesource.uiState.menustate.MenuAppState;
import gamesource.uiState.shopstate.ShopAppState;

import java.util.ArrayList;

public class SecondState  extends BaseAppState {
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
    // MajorActor major=new MajorActor(new Vector3f(-0.5884632f, -25.645144f, 76.421844f));
    chest c1 = new chest(new Vector3f(9.952984f, -31.962004f, 56.09926f),4f);
    chest c2 = new chest(new Vector3f(-10.413138f, -29.553904f, 28.508766f));
    chest c3 = new chest(new Vector3f(12.185162f, -18.157299f, -74.07405f),4.2f);
    private angryskeletonState skeleton1=new angryskeletonState(new Vector3f(3.0883105f, -31.735968f, 43.255566f),0.5f);

    private shanmanState shanman=new shanmanState(new Vector3f(5.1453485f, -32.197643f, 58.86895f),-1.5f);

    private goblinGirlState girl =new goblinGirlState(new Vector3f(5.3336577f, -31.696009f, 55.903286f),-1.5f);

    private greenSkeletonState skeleton2=new greenSkeletonState(new Vector3f(8.143902f, -32.197643f, 44.735046f),-0.7f);

    private InputAppState input;

    private secondWorldMap world=new secondWorldMap();

    private snowRobotState snowRobot=new snowRobotState(new Vector3f(8.143902f, -32.197643f, 44.735046f));

    private batState bat=new batState();

    private bossKnight knight=new bossKnight();

    BulletAppState bullet=new BulletAppState();         //现在为为世界添加物理引擎的测试情况

    private SecondBackMusic music=new SecondBackMusic();

    private stuxnetState boss=new stuxnetState();

    private skyBox2 sky=new skyBox2();

    private SecondWorldLight light=new SecondWorldLight();

    private SecondWorldOtherSpecial special =new SecondWorldOtherSpecial();

    private StartTalk st=new StartTalk();

    private BoundingVolume maj;

    private float time=0;

    private int chan=0;

    private BagAppState bagState;
    private ShopAppState shopState;
    private MenuAppState menuState;
    private makeCross cross;

    private ArrayList<BaseAppState> states=new ArrayList<BaseAppState>();

    protected void initialize(Application application){
        state=application.getStateManager();
        cam=application.getCamera();
        state.attach(world);
        states.add(world);
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
        //state.attach(new PositionInputState());
        //states.add(wldor);
        state.attach(skeleton1);
        states.add(skeleton1);
        state.attach(shanman);
        states.add(shanman);
        state.attach(girl);
        states.add(girl);
        state.attach(skeleton2);
        states.add(skeleton2);
        state.attach(music);
        states.add(music);
        state.attach(snowRobot);
        states.add(snowRobot);
        state.attach(boss);
        states.add(boss);
        state.attach(c1);
        states.add(c1);
        state.attach(c2);
        states.add(c2);
        state.attach(c3);
        states.add(c3);
        state.attach(sky);
        states.add(sky);
        state.attach(special);
        states.add(special);
        state.attach(light);
        states.add(light);

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
        major.setPlace(new Vector3f(-0.5884632f, -25.645144f, 76.421844f));
    }

    public CollisionResults results1(){
        ray=major.get();
        BoundingVolume sha=shanman.get();
        CollisionResults result=new CollisionResults();
        ray.collideWith(sha,result);
        return result;
    }
    public CollisionResults results2(){
        ray=major.get();
        BoundingVolume gi=girl.get();
        CollisionResults result=new CollisionResults();
        ray.collideWith(gi,result);
        return result;
    }public CollisionResults results3(){
        maj=major.getMajor();
        BoundingVolume ske=skeleton1.get();
        CollisionResults result=new CollisionResults();
        maj.collideWith(ske,result);
        return result;
    }public CollisionResults results4(){
        BoundingVolume ske=skeleton2.get();
        CollisionResults result=new CollisionResults();
        maj.collideWith(ske,result);
        return result;
    }

    class StartTalk implements ActionListener {
        @Override
        public void onAction(String name,boolean isPressed,float tpf){
            int battle1=1;
            CollisionResults results=results1();
            CollisionResults results2=results2();
            CollisionResults results3=results3();
            CollisionResults results4=results4();
            if(talk.equals(name)&&isPressed){
                if(results.size()>0){
                    System.out.println("get");
                    if(canmove==1){
                        state.detach(input);
                        major.mouseChange();
                        canmove=0;
                    }else{
                        state.attach(input);
                        major.mouseChange();
                        canmove=1;
                    }
                }else if (results2.size()>0){
                    if(canmove==1){
                        state.detach(input);
                        major.mouseChange();
                        canmove=0;
                    }else{
                        state.attach(input);
                        major.mouseChange();
                        canmove=1;
                    }
                }
            }
            if(move.equals(name)&&isPressed){
                if(results3.size()>0){
                    battle1=0;
                }else if(results4.size()>0){
                    System.out.println("hhhhh");
                }
            }
            if(change.equals(name)&&isPressed){
                System.out.println("change");
                major.change();
            }
            if(bag.equals(name)&&isPressed){
                if(canmove==1){
                    state.detach(input);
                    major.mouseChange();
                    canmove=0;
                }else{
                    state.attach(input);
                    major.mouseChange();
                    canmove=1;
                }
            }
            if(battle1==0){
                state.detach(input);
                states.remove(input);
                System.out.println("zzzz");
                inputManager.deleteTrigger(talk, TALK);
                inputManager.deleteTrigger(change, CHANGECAMERA);
                inputManager.deleteTrigger(bag, BAG);
                inputManager.deleteTrigger(move,MOVE);
                state.attach(new Battle(states));
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
        /*state.detach(world);
        state.detach(skeleton1);
        state.detach(shanman);
        state.detach(girl);
        state.detach(skeleton2);
        state.detach(music);
        state.detach(snowRobot);
        state.detach(boss);
        state.detach(c1);
        state.detach(c2);
        state.detach(c3);
        state.detach(light);
        state.detach(sky);
        state.detach(special);
        System.out.println("second clean");*/
    }

    @Override
    protected void onEnable() {
        System.out.println("second enable");
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
            if (!(baseAppState instanceof MenuAppState) && !(baseAppState instanceof BagAppState) && !(baseAppState instanceof ShopAppState)&& !(baseAppState instanceof MajorActor)&& !(baseAppState instanceof InputAppState)) {
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
