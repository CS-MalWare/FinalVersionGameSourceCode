package gamesource.State.worldState;

import java.util.ArrayList;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
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
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.style.BaseStyles;

import gamesource.State.CharacterState.*;
import gamesource.State.CharacterState.firstWorldCharacter.*;
import gamesource.State.SpecialEffect.FirstWorldLight;
import gamesource.State.SpecialEffect.FirstWorldOtherSpecial;
import gamesource.State.SpecialEffect.Water;
import gamesource.State.SpecialEffect.makeCross;
import gamesource.State.controlState.InputAppState;
import gamesource.State.controlState.PositionInputState;
import gamesource.State.mapState.FirstWorldState;
import gamesource.State.mapState.chest;
import gamesource.State.mapState.skyBox;
import gamesource.uiState.bagstate.BagAppState;
import gamesource.uiState.menustate.MenuAppState;
import gamesource.uiState.shopstate.ShopAppState;
import gamesource.util.TalkWithOption;
import gamesource.util.TalkWithOption.CallType;

public class FirstState extends BaseAppState {

    public final static String talk="TALK";
    public final static Trigger TALK = new KeyTrigger(KeyInput.KEY_N);
    public final static String change="CHANGE";
    public final static Trigger CHANGECAMERA = new KeyTrigger(KeyInput.KEY_C);
    public final static String bag="BAG";
    public final static Trigger BAG = new KeyTrigger(KeyInput.KEY_B);
    private InputManager inputManager;

    MajorActor major;
    KingState king=new KingState(new Vector3f(-39.032665f, 4.674482f, -12.373539f),3.9f);
    InputAppState input;
    soldierState trainSoldier=new soldierState(5,new Vector3f(74.39715f, -27.375854f, -12.961687f),6f,2);
    blackBoyState black1=new blackBoyState(new Vector3f(40.09374f, -15.457153f, -47.101334f),4.8f);
    girlState girl1=new girlState(new Vector3f(30.66676f, -17.49788f, -38.046852f),-0.8f);
    KnightState kinght1=new KnightState(5,new Vector3f(13.648198f, -9.379812f, -35.002224f),-2.4f);
    soldierState bridgeSoldier=new soldierState(5,new Vector3f(10.684639f, -9.582897f, -33.982456f),-2.9f);
    lizardState lizard =new lizardState(new Vector3f(-35.907394f, 3.7558994f, -35.212135f),0.7f);
    KnightState knight2=new KnightState(2,new Vector3f(-30.269215f, 3.6953368f, -39.55574f),0.6f,2);
    KnightState knight3=new KnightState(3,new Vector3f(-30.080694f, 3.7788515f, -37.448612f),3.5f,1);
    KnightState knight4=new KnightState(5,new Vector3f(-32.802486f, 3.7768545f, -36.82728f),0.9f,2);
    soldierState soldier1=new soldierState(1,new Vector3f(75.06459f, -29.602854f, 3.6023405f));
    soldierState soldier2=new soldierState(2,new Vector3f(76.39369f, -25.91693f, -11.675956f),6f);
    soldierState soldier3=new soldierState(3,new Vector3f(75.281975f, -27.164389f, -10.233985f),2.6f,2);
    soldierState soldier4=new soldierState(4,new Vector3f(62.556515f, -26.660355f, -18.043798f),2.6f);
    KnightState knight5=new KnightState(1,new Vector3f(-46.05996f, 6.526695f, -26.613733f),1.4f,-1,0,1);
    KnightState knight6=new KnightState(1,new Vector3f(-46.07412f, 5.8200717f, -29.27783f),4.6f,1,0,-1,2);
    girlState girl=new girlState(new Vector3f(70.15531f, -27.27989f, -18.333033f),-0.3f);
    metalKnightState metalKnight=new metalKnightState(new Vector3f(64.4272f, -25.418774f, -20.45753f),-1.45f);
    blackBoyState boy1=new blackBoyState(new Vector3f(64.8886f, -26.233301f, -9.080749f),2.4f);
    shovelKnight s1=new shovelKnight(new Vector3f(-35.924995f, 7.3355093f, -13.190997f),4.4f);
    shovelKnight s2=new shovelKnight(new Vector3f(-40.020714f, 5.764501f, -9.103482f),3.4f);
    FirstWorldState f1=new FirstWorldState();
    PositionInputState p1=new PositionInputState();
    BulletAppState bullet=new BulletAppState();         //现在为为世界添加物理引擎的测试情况
    chest c1 = new chest(new Vector3f(62.722965f, -26.72887f, 9.268448f));
    chest c2 = new chest(new Vector3f(-15.538464f, -2.8196087f, -60.361416f));
    chest c3 = new chest(new Vector3f(-26.772413f, 6.3929253f, -8.448748f),2.9f);
    Water x1=new Water(-34.4f);
    FirstWorldLight light=new FirstWorldLight(0);
    FirstWorldOtherSpecial special =new FirstWorldOtherSpecial();
    skyBox sky=new skyBox();


    private BagAppState bagState;
    private ShopAppState shopState;
    private MenuAppState menuState;
    private makeCross cross;

    StartTalk st=new StartTalk();

    private Node rootNode;
    private SimpleApplication app;

    private AppStateManager state;

    private int canmove=1,chan=0;

    Ray ray;

    private float time;

    private ArrayList<BaseAppState> states=new ArrayList<BaseAppState>();

    @Override
    protected void initialize(Application application) {
        app=(SimpleApplication)application;
        state=application.getStateManager();
        major=state.getState(MajorActor.class);
        states.add(major);
        input=state.getState(InputAppState.class);
        states.add(input);
        bagState=state.getState(BagAppState.class);
        states.add(bagState);
        shopState=state.getState(ShopAppState.class);
        states.add(shopState);
        menuState=state.getState(MenuAppState.class);
        states.add(menuState);
        cross=state.getState(makeCross.class);
        states.add(cross);
        //state.attach(bullet);
        state.attach(f1);
        states.add(f1);
        //stateManager.attach(new AxisState());
        state.attach(p1);
        states.add(p1);
        //state.attach(major);
        state.attach(trainSoldier);
        states.add(trainSoldier);
        state.attach(black1);
        states.add(black1);
        state.attach(girl1);
        states.add(girl);
        state.attach(kinght1);
        states.add(kinght1);
        state.attach(bridgeSoldier);
        states.add(bridgeSoldier);
        state.attach(lizard);
        states.add(lizard);
        state.attach(knight2);
        states.add(knight2);
        state.attach(knight3);
        states.add(knight3);
        state.attach(knight4);
        states.add(knight4);
        state.attach(soldier1);
        states.add(soldier1);
        state.attach(soldier2);
        states.add(soldier2);
        state.attach(soldier3);
        states.add(soldier3);
        state.attach(soldier4);
        states.add(soldier4);
        state.attach(knight5);
        states.add(knight5);
        state.attach(knight6);
        states.add(knight6);
        state.attach(girl);
        states.add(girl);
        state.attach(metalKnight);
        states.add(metalKnight);
        state.attach(boy1);
        states.add(boy1);
        //stateManager.attach(new BagAppState());
        state.attach(s1);
        states.add(s1);
        state.attach(s2);
        states.add(s2);
        state.attach(king);
        states.add(king);
        state.attach(c1);
        states.add(c1);
        state.attach(c2);
        states.add(c2);
        state.attach(c3);
        states.add(c3);
        state.attach(x1);
        states.add(x1);
        state.attach(light);
        states.add(light);
        state.attach(special);
        states.add(special);
        state.attach(sky);
        states.add(sky);

        this.inputManager=application.getInputManager();
        inputManager.addMapping(talk,TALK);
        inputManager.addListener(st,talk);

        inputManager.addMapping(change,CHANGECAMERA);
        inputManager.addListener(st,change);


        inputManager.addMapping(bag,BAG);
        inputManager.addListener(st,bag);


        major.setPlace(new Vector3f(-35.907394f, 3.7558994f, -35.212135f));

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

    public CollisionResults collision(){
        ray=major.get();
        ray.setLimit(3);
        BoundingVolume ki=king.get();
        CollisionResults results = new CollisionResults();
        ray.collideWith(ki, results);
        return results;
    }

    public CollisionResults collision2(){
        BoundingVolume train=trainSoldier.get();
        CollisionResults results=new CollisionResults();
        ray.collideWith(train,results);
        return results;
    }

    public CollisionResults collision3(){
        BoundingVolume liz=lizard.get();
        CollisionResults results=new CollisionResults();
        ray.collideWith(liz,results);
        return results;
    }

    public CollisionResults collision4(){
        BoundingVolume kni=knight4.get();
        CollisionResults results=new CollisionResults();
        ray.collideWith(kni,results);
        return results;
    }

    class StartTalk implements ActionListener {
        ArrayList<String> content = new ArrayList<>();

        @Override
        public void onAction(String name,boolean isPressed,float tpf){
            CollisionResults results=collision();
            CollisionResults results2=collision2();
            CollisionResults results3=collision3();
            CollisionResults results4=collision4();
            if(talk.equals(name)&&isPressed){
                //System.out.println("zzzzzz");
                if(results.size()>0){                 
                                                            //这个函数里写和女王的对话
                    content.clear();
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
                }else if (results2.size()>0){               //这个是船旁边训练士兵的对话
                    content.clear();
                    content.add("Long time to see, my prince, seems like you have finished you trail.");
                    content.add("We all glad to see you again in King city. After training, there has a great change on your body!");
                    content.add("We are drilling recruits. Would you be pleasure to give them a lession?");
                    TalkWithOption talkWithOption = new TalkWithOption("Soldier", content, CallType.SHOP);
                    state.attach(talkWithOption);
                    if(canmove==1){
                        state.detach(input);
                        major.mouseChange();
                        canmove=0;
                    }else{
                        state.attach(input);
                        major.mouseChange();
                        canmove=1;
                    }
                }else if(results3.size()>0){                //这里是购买商店的对话
                    
                    if(canmove==1){
                        state.detach(input);
                        major.mouseChange();
                        canmove=0;
                    }else{
                        state.attach(input);
                        major.mouseChange();
                        canmove=1;
                    }
                }else if(results4.size()>0){                //这里是训练骑士的对话
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
        }
    }

public void update(float tpf){
        if(chan==0) {
            time = time + tpf;
            if (time < 19 && time > 5) {
                change();
            }
        }
}


    @Override
    protected void cleanup(Application application) {
        System.out.println("first clean");

        for(int i=0;i<states.size();i++){
            if(!(states.get(i) instanceof MajorActor) && !(states.get(i) instanceof InputAppState)&& !(states.get(i) instanceof MenuAppState)&& !(states.get(i) instanceof BagAppState)&& !(states.get(i) instanceof ShopAppState)&& !(states.get(i) instanceof makeCross))
            {
                state.detach(states.get(i));
            }
        }
        //state.cleanup();
    }

    @Override
    protected void onEnable() {
        inputManager.addMapping(talk,TALK);
        inputManager.addListener(st,talk);

        inputManager.addMapping(change,CHANGECAMERA);
        inputManager.addListener(st,change);


        inputManager.addMapping(bag,BAG);
        inputManager.addListener(st,bag);

        //major.setEnabled(false);
        king.setEnabled(true);
        //input.setEnabled(false);
        trainSoldier.setEnabled(true);
        black1.setEnabled(true);
        girl1.setEnabled(true);
        kinght1.setEnabled(true);
        bridgeSoldier.setEnabled(true);
        lizard.setEnabled(true);
        knight2.setEnabled(true);
        knight3.setEnabled(true);
        knight4.setEnabled(true);
        soldier1.setEnabled(true);
        soldier2.setEnabled(true);
        soldier3.setEnabled(true);
        soldier4.setEnabled(true);
        knight5.setEnabled(true);
        knight6.setEnabled(true);
        girl.setEnabled(true);
        metalKnight.setEnabled(true);
        boy1.setEnabled(true);
        s1.setEnabled(true);
        s2.setEnabled(true);
        f1.setEnabled(true);
        p1.setEnabled(true);
        //bullet.setEnabled(false);
        c1 .setEnabled(true);
        c2 .setEnabled(true);
        c3 .setEnabled(true);
        x1.setEnabled(true);
        light.setEnabled(true);
        special.setEnabled(true);
        sky.setEnabled(true);
    }

    @Override
    protected void onDisable() {
        System.out.println("first disable");

        inputManager.removeListener(st);

        inputManager.deleteTrigger(talk, TALK);
        inputManager.deleteTrigger(change, CHANGECAMERA);
        inputManager.deleteTrigger(bag, BAG);

        /*major.setEnabled(false);
        king.setEnabled(false);
        //input.setEnabled(false);
        trainSoldier.setEnabled(false);
        black1.setEnabled(false);
        girl1.setEnabled(false);
        kinght1.setEnabled(false);
        bridgeSoldier.setEnabled(false);
        lizard.setEnabled(false);
        knight2.setEnabled(false);
        knight3.setEnabled(false);
        knight4.setEnabled(false);
        soldier1.setEnabled(false);
        soldier2.setEnabled(false);
        soldier3.setEnabled(false);
        soldier4.setEnabled(false);
        knight5.setEnabled(false);
        knight6.setEnabled(false);
        girl.setEnabled(false);
        metalKnight.setEnabled(false);
        boy1.setEnabled(false);
        s1.setEnabled(false);
        s2.setEnabled(false);
        f1.setEnabled(false);
        p1.setEnabled(false);
        //bullet.setEnabled(false);
        c1 .setEnabled(false);
        c2 .setEnabled(false);
        c3 .setEnabled(false);
        x1.setEnabled(false);
        light.setEnabled(false);
        special.setEnabled(false);
        sky.setEnabled(false);*/
    }
}