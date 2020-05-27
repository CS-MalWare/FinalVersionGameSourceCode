package gamesource.State.worldState;

import com.jme3.app.Application;
import com.jme3.app.FlyCamAppState;
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
import com.jme3.material.Material;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;
import gamesource.State.CharacterState.MajorActor;
import gamesource.State.CharacterState.Master1;
import gamesource.State.CharacterState.firstWorldCharacter.*;
import gamesource.State.SpecialEffect.FirstWorldLight;
import gamesource.State.SpecialEffect.FirstWorldOtherSpecial;
import gamesource.State.SpecialEffect.Water;
import gamesource.State.SpecialEffect.makeCross;
import gamesource.State.controlState.InputAppState;
import gamesource.State.controlState.PositionInputState;
import gamesource.State.mapState.FirstWorldState;
import gamesource.State.mapState.SkyBox;
import gamesource.State.musicState.FirstBackMusic;
import gamesource.battleState.appState.BattleBackGroundState;
import gamesource.uiState.SmallMap;
import gamesource.uiState.bagstate.BagAppState;
import gamesource.uiState.menustate.MenuAppState;
import gamesource.uiState.shopstate.ShopAppState;
import gamesource.util.TalkWithOption;

import java.util.ArrayList;

public class SeventhState extends BaseAppState {
    public static String canGo = "can";// 这是用于存档的变量,大佬们不要改没了
    public final static String talk = "TALK";
    public final static Trigger TALK = new KeyTrigger(KeyInput.KEY_N);
    public final static String change = "CHANGE";
    public final static Trigger CHANGECAMERA = new KeyTrigger(KeyInput.KEY_C);
    public final static String bag = "BAG";
    public final static Trigger BAG = new KeyTrigger(KeyInput.KEY_B);
    public final static String move = "MOVE";
    public final static Trigger MOVE = new KeyTrigger(KeyInput.KEY_W);

    public final static String menu = "MENU";
    public final static Trigger MENU = new KeyTrigger(KeyInput.KEY_V);
    public final static String map = "MAP";
    public final static Trigger MAP = new KeyTrigger(KeyInput.KEY_M);
    private InputManager inputManager;

    MajorActor major;
    KingState king = new KingState(new Vector3f(-39.032665f, 4.674482f, -12.373539f), 4.3f);
    InputAppState input;
    soldierState trainSoldier = new soldierState(5, new Vector3f(74.39715f, -27.375854f, -12.961687f), 6f, 2);
    girlState girl1 = new girlState(new Vector3f(30.66676f, -17.49788f, -38.046852f), -0.8f);
    KnightState knight2 = new KnightState(2, new Vector3f(-30.269215f, 3.6953368f, -39.55574f), 2.1f, 2);
    KnightState knight3 = new KnightState(3, new Vector3f(-30.080694f, 3.7788515f, -37.448612f), 2.2f, 1);
    KnightState knight4 = new KnightState(5, new Vector3f(-51.185917f, 4.0807734f, -15.632022f), 2.2f, 2);
    KnightState bridgeKnight = new KnightState(5, new Vector3f(-48.703007f, 5.015172f, -10.933982f), 2.3f);
    soldierState bridgeSoldier = new soldierState(5, new Vector3f(-49.20796f, 4.715569f, -13.518127f), 2.1f);
    lizardState lizard = new lizardState(new Vector3f(-46.110508f, 4.7068315f, -12.008309f), 2.9f);
    blackBoyState black1 = new blackBoyState(new Vector3f(-48.370106f, 4.2267914f, -17.703886f), 0.9f);
    soldierState soldier6 = new soldierState(5, new Vector3f(-50.940468f, 4.607802f, -11.950516f), 2.6f, 2);
    soldierState soldier1 = new soldierState(1, new Vector3f(75.06459f, -29.602854f, 3.6023405f));
    soldierState soldier2 = new soldierState(2, new Vector3f(76.39369f, -25.91693f, -11.675956f), 6f);
    soldierState soldier3 = new soldierState(3, new Vector3f(75.281975f, -27.164389f, -10.233985f), 2.6f, 2);
    girlState girl = new girlState(new Vector3f(-44.83667f, 4.0626507f, -16.635683f), -0.3f);

    soldierState soldier4 = new soldierState(4, new Vector3f(62.556515f, -26.660355f, -18.043798f), 2.6f);
    KnightState knight5 = new KnightState(1, new Vector3f(-46.05996f, 6.526695f, -26.613733f), 1.4f, -1, 0, 1);
    KnightState knight6 = new KnightState(1, new Vector3f(-46.07412f, 5.8200717f, -29.27783f), 4.6f, 1, 0, -1, 2);
    metalKnightState metalKnight = new metalKnightState(new Vector3f(64.4272f, -25.418774f, -20.45753f), -1.45f);
    blackBoyState boy1 = new blackBoyState(new Vector3f(64.8886f, -26.233301f, -9.080749f), 2.4f);
    shovelKnight s1 = new shovelKnight(new Vector3f(-35.924995f, 7.3355093f, -13.190997f), 4.4f);
    shovelKnight s2 = new shovelKnight(new Vector3f(-40.020714f, 5.764501f, -9.103482f), 3.4f);
    FirstWorldState f1 = new FirstWorldState();
    PositionInputState p1 = new PositionInputState();
    BulletAppState bullet = new BulletAppState();         //现在为为世界添加物理引擎的测试情况
    //    Chest c1 = new Chest(new Vector3f(62.722965f, -26.72887f, 9.268448f));
//    Chest c2 = new Chest(new Vector3f(-15.538464f, -2.8196087f, -60.361416f));
//    Chest c3 = new Chest(new Vector3f(-26.772413f, 6.3929253f, -8.448748f), 2.9f);
    Water x1 = new Water(-34.4f);
    FirstWorldLight light = new FirstWorldLight(1);
    FirstWorldOtherSpecial special = new FirstWorldOtherSpecial();
    SkyBox sky;
    private Master1 master = new Master1(new Vector3f(-42.829556f, 4.341275f, -10.886024f), 3.9f);
    FirstBackMusic music=new FirstBackMusic();


    private BagAppState bagState;
    private ShopAppState shopState;
    private MenuAppState menuState;
    private makeCross cross;
    private SmallMap smallMap;

    StartTalk st = new StartTalk();

    private Node rootNode;
    private SimpleApplication app;

    private AppStateManager state;

    private int canmove = 1, chan = 0;
    private int shadow=1024,open=0;
    private int Cro=1;
    private int fly=0;
    Ray ray;

    BoundingVolume maj;

    private float time;

    private ArrayList<BaseAppState> states = new ArrayList<BaseAppState>();

    private boolean isTalkShow = false;
    private TalkWithOption talkWithOption;
    private ArrayList<String> content = new ArrayList<>();

    @Override
    protected void initialize(Application application) {
        BattleBackGroundState.setBackgroundSrc("Map/first/ditu.j3o");
        app = (SimpleApplication) application;
        state = application.getStateManager();
        Spatial pic=getPicture(1);
        app.getGuiNode().attachChild(pic);
        major = state.getState(MajorActor.class);
        states.add(major);
        input = state.getState(InputAppState.class);
        states.add(input);
        bagState = state.getState(BagAppState.class);
        states.add(bagState);
        shopState = state.getState(ShopAppState.class);
        states.add(shopState);
        menuState = state.getState(MenuAppState.class);
        states.add(menuState);
        cross = state.getState(makeCross.class);
        states.add(cross);
        cross.setEnabled(true);

        //state.attach(new AxisState());
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
        state.attach(bridgeKnight);
        states.add(bridgeKnight);
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

        state.attach(soldier6);
        states.add(soldier6);
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
//        state.attach(c1);
//        states.add(c1);
//        state.attach(c2);
//        states.add(c2);
//        state.attach(c3);
//        states.add(c3);
        state.attach(x1);
        states.add(x1);
        light = new FirstWorldLight(open,shadow);
        state.attach(light);
        states.add(light);
        state.attach(special);
        states.add(special);
        state.attach(master);
        states.add(master);
        state.attach(music);
        states.add(music);
        sky=new SkyBox(pic);
        state.attach(sky);
        states.add(sky);
        smallMap = new SmallMap(1600, 900, new Vector2f(400, 400), 1);
        state.attach(smallMap);
        states.add(smallMap);

        this.inputManager = application.getInputManager();
        inputManager.addMapping(talk, TALK);
        inputManager.addListener(st, talk);

        inputManager.addMapping(change, CHANGECAMERA);
        inputManager.addListener(st, change);


        inputManager.addMapping(bag, BAG);
        inputManager.addListener(st, bag);


        inputManager.addMapping(move, MOVE);
        inputManager.addListener(st, move);


        inputManager.addMapping(menu, MENU);
        inputManager.addListener(st,menu);


        inputManager.addMapping(map, MAP);
        inputManager.addListener(st, map);

        //major.setPlace(new Vector3f(93.51907f, -31.696218f, 18.607859f));
        major.setPlace(new Vector3f(-46.11014f, 4.358172f, -14.546469f));
        major.height(6);
        major.change2();
        state.detach(state.getState(FlyCamAppState.class));
    }

    public SeventhState(){

    }
    public SeventhState(int shadow,int open){
        this.shadow=shadow;
        this.open=open;
    }
    public void change() {
        if (chan == 0) {
            major.mouseChange();
            System.out.println("zzzz");
            major.mouseChange();
            System.out.println("hhhh");
            chan++;
        }
    }

    public CollisionResults collision() {
        ray = major.get();
        ray.setLimit(3);
        BoundingVolume ki = king.get();
        CollisionResults results = new CollisionResults();
        ray.collideWith(ki, results);
        return results;
    }

    public CollisionResults collision2() {
        BoundingVolume train = trainSoldier.get();
        CollisionResults results = new CollisionResults();
        ray.collideWith(train, results);
        return results;
    }

    public CollisionResults collision3() {
        BoundingVolume liz = lizard.get();
        CollisionResults results = new CollisionResults();
        ray.collideWith(liz, results);
        return results;
    }

    public CollisionResults collision4() {
        BoundingVolume kni = knight4.get();
        CollisionResults results = new CollisionResults();
        ray.collideWith(kni, results);
        return results;
    }

    //    public CollisionResults collision5() {
//        try {
//            maj = major.getMajor();
//            BoundingVolume kni = c1.get();
//            CollisionResults results = new CollisionResults();
//            maj.collideWith(kni, results);
//            return results;
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    public CollisionResults collision6() {
//        try {
//            BoundingVolume kni = c2.get();
//            CollisionResults results = new CollisionResults();
//            maj.collideWith(kni, results);
//            return results;
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    public CollisionResults collision7() {
//        try {
//            BoundingVolume kni = c3.get();
//            CollisionResults results = new CollisionResults();
//            maj.collideWith(kni, results);
//            return results;
//        } catch (Exception e) {
//            return null;
//        }
//    }
    public CollisionResults collision8() {
        BoundingVolume train = bridgeSoldier.get();
        CollisionResults results = new CollisionResults();
        ray.collideWith(train, results);
        return results;
    }
    public CollisionResults collision9() {
        BoundingVolume train = bridgeKnight.get();
        CollisionResults results = new CollisionResults();
        ray.collideWith(train, results);
        return results;
    }
    public CollisionResults collision10() {
        BoundingVolume train =master.get();
        CollisionResults results = new CollisionResults();
        ray.collideWith(train, results);
        return results;
    }
    class StartTalk implements ActionListener {

        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            CollisionResults results = collision();
            CollisionResults results2 = collision2();
            CollisionResults results3 = collision3();
            CollisionResults results4 = collision4();
            CollisionResults results8 = collision8();
            CollisionResults results9 = collision9();
            CollisionResults results10 = collision10();
            if (talk.equals(name) && isPressed) {
                //System.out.println("zzzzzz");
                if (results.size() > 0) {
                    //这个函数里写和女王的对话
                    if(!isTalkShow && !getStateManager().hasState(talkWithOption)){
                        content.clear();
                        content.add("My son, you came back and become more powerful than before.");
                        content.add("I am tired. Maybe this would be a good time for retirement");
                        content.add("In charge of the country is not an easy thing");
                        content.add("It took up most of my tine, and now, I want stay with family.");
                        talkWithOption = new TalkWithOption("Queen", content, TalkWithOption.CallType.CONFIRM, 1, states);
                        state.attach(talkWithOption);
                        isTalkShow = true;
                        SecondState.canGo = "can";
                    }else if(isTalkShow && getStateManager().hasState(talkWithOption)){
                        getStateManager().detach(talkWithOption);
                        app.getFlyByCamera().setDragToRotate(false);
                        isTalkShow = false;
                    }
                    System.out.println("get");
                    if (canmove == 1) {
                        state.detach(input);
                        major.mouseChange();
                        canmove = 0;
                    } else {
                        state.attach(input);
                        major.mouseChange();
                        canmove = 1;
                    }
                } else if (results2.size() > 0) {               //这个是船旁边训练士兵的对话
                    if(!isTalkShow && !getStateManager().hasState(talkWithOption)){
                        content.clear();
                        content.add("Long time to see, my prince, seems like you have finished you trail.");
                        content.add("We all glad to see you again in King city. After training, there has a great change on your body!");
                        content.add("We are drilling recruits. Would you be pleasure to give them a lession?");
                        talkWithOption = new TalkWithOption("Soldier", content, TalkWithOption.CallType.CONFIRM, 1, states);
                        state.attach(talkWithOption);
                        isTalkShow = true;
                    }else if(isTalkShow && getStateManager().hasState(talkWithOption)){
                        getStateManager().detach(talkWithOption);
                        app.getFlyByCamera().setDragToRotate(false);
                        isTalkShow = false;
                    }
                    if (canmove == 1) {
                        state.detach(input);
                        major.mouseChange();
                        canmove = 0;
                    } else {
                        state.attach(input);
                        major.mouseChange();
                        canmove = 1;
                    }
                } else if (results3.size() > 0) {                //这里是购买商店的对话
                    if(!isTalkShow && !getStateManager().hasState(talkWithOption)){
                        content.clear();
                        content.add("Recently, I feel a evil power from supernatural.");
                        content.add("So I returned the king city as soon as possible.");
                        content.add("From the result of division, there will be a prince save this world");
                        content.add("Before save the world, do you needs some new skills?");
                        talkWithOption = new TalkWithOption("Lizard Mage", content, TalkWithOption.CallType.SHOP, 1, states);
                        state.attach(talkWithOption);
                        isTalkShow = true;
                    }else if(isTalkShow && getStateManager().hasState(talkWithOption)){
                        getStateManager().detach(talkWithOption);
                        app.getFlyByCamera().setDragToRotate(false);
                        isTalkShow = true;
                    }
                    if (canmove == 1) {
                        state.detach(input);
                        major.mouseChange();
                        canmove = 0;
                    } else {
                        state.attach(input);
                        major.mouseChange();
                        canmove = 1;
                    }
                } else if (results4.size() > 0) {                //这里是训练骑士的对话
                    if (canmove == 1) {
                        state.detach(input);
                        major.mouseChange();
                        canmove = 0;
                    } else {
                        state.attach(input);
                        major.mouseChange();
                        canmove = 1;
                    }
                }else if(results8.size()>0){                    //这里是桥上士兵
                    if(!isTalkShow && !getStateManager().hasState(talkWithOption)){
                        content.clear();
                        content.add("Hello");
                        content.add("We all glad to see you again in King city. After training, there has a great change on your body!");
                        content.add("We are drilling recruits. Would you be pleasure to give them a lession?");
                        talkWithOption = new TalkWithOption("Soldier", content, TalkWithOption.CallType.FIGHT, 1, states);
                        state.attach(talkWithOption);
                        isTalkShow = true;
                    }else if(isTalkShow && getStateManager().hasState(talkWithOption)){
                        getStateManager().detach(talkWithOption);
                        app.getFlyByCamera().setDragToRotate(false);
                        isTalkShow = false;
                    }
                    if (canmove == 1) {
                        state.detach(input);
                        major.mouseChange();
                        canmove = 0;
                    } else {
                        state.attach(input);
                        major.mouseChange();
                        canmove = 1;
                    }
                }else if(results9.size()>0){                    //这里是桥上的骑士
                    if(!isTalkShow && !getStateManager().hasState(talkWithOption)){
                        content.clear();
                        content.add("Ahead is the most prosperous part of the King city, with the most richest trade marking and the finest order of knights.");
                        content.add("If you want to go there, you need to keep your weapon in here");
                        talkWithOption = new TalkWithOption("Soldier", content, TalkWithOption.CallType.CONFIRM, 1, states);
                        state.attach(talkWithOption);
                        isTalkShow = true;
                    }else if(isTalkShow && getStateManager().hasState(talkWithOption)){
                        getStateManager().detach(talkWithOption);
                        app.getFlyByCamera().setDragToRotate(false);
                        isTalkShow = false;
                    }
                    if (canmove == 1) {
                        state.detach(input);
                        major.mouseChange();
                        canmove = 0;
                    } else {
                        state.attach(input);
                        major.mouseChange();
                        canmove = 1;
                    }
                }else if(results10.size()>0){                    //这里是新加的法师
                    content.clear();
                    if (canmove == 1) {
                        state.detach(input);
                        major.mouseChange();
                        canmove = 0;
                    } else {
                        state.attach(input);
                        major.mouseChange();
                        canmove = 1;
                    }
                }
            }
            if (change.equals(name) && isPressed) {
                if(fly==0){
                    state.attach(new FlyCamAppState());
                    fly++;
                }
                System.out.println("change");
                major.change();
                if(Cro==0){
                    cross.setEnabled(true);
                    Cro=1;
                }else{
                    cross.setEnabled(false);
                    Cro=0;
                }
            }
            if (map.equals(name) && isPressed) {
                if (canmove == 1) {
                    state.detach(input);
                    major.mouseChange();
                    canmove = 0;
                } else {
                    state.attach(input);
                    major.mouseChange();
                    canmove = 1;
                }
            }
            if (menu.equals(name) && isPressed) {
                if (canmove == 1) {
                    state.detach(input);
                    major.mouseChange();
                    canmove = 0;
                } else {
                    state.attach(input);
                    major.mouseChange();
                    canmove = 1;
                }
            }
            if (bag.equals(name) && isPressed) {
                if (canmove == 1) {
                    state.detach(input);
                    major.mouseChange();
                    canmove = 0;
                } else {
                    state.attach(input);
                    major.mouseChange();
                    canmove = 1;
                }
            }
//            if (move.equals(name) && isPressed) {
//                if (results5 != null && results5.size() > 0) {
//                    System.out.println("chest");
//                    c1.open();
//                    getApplication().getStateManager().attach(new GetCardState());
//                } else if (results6 != null && results6.size() > 0) {
//                    System.out.println("chest");
//                    c2.open();
//                    getApplication().getStateManager().attach(new GetCardState());
//                } else if (results7 != null && results7.size() > 0) {
//                    System.out.println("chest");
//                    c3.open();
//                    getApplication().getStateManager().attach(new GetCardState());
//                }
//            }
        }
    }

    public void update(float tpf) {
        /*if (chan == 0) {
            time = time + tpf;
            if (time < 60 && time > 10) {
                change();
                cross.setEnabled(false);
            }
        }*/
       /* if(chan==0) {
        try {
            time = time + tpf;
            if (sky.finish() == 1) {
                if (time < 60 && time > 5) {
                    change();
                    cross.setEnabled(false);
                }
            }
        }catch (Exception e){

        }
        }*/

        if(isTalkShow){
            if(!getStateManager().hasState(talkWithOption)){
                isTalkShow = false;
            }
        }
    }


    @Override
    protected void cleanup(Application application) {
        System.out.println("first clean");

        for (int i = 0; i < states.size(); i++) {
            if (!(states.get(i) instanceof MajorActor) && !(states.get(i) instanceof InputAppState) && !(states.get(i) instanceof MenuAppState) && !(states.get(i) instanceof BagAppState) && !(states.get(i) instanceof ShopAppState) && !(states.get(i) instanceof makeCross) && !(states.get(i) instanceof SmallMap)) {
                state.detach(states.get(i));
            }
        }
        //state.cleanup();
    }

    @Override
    protected void onEnable() {
        BattleBackGroundState.setBackgroundSrc("Map/first/ditu.j3o");

        inputManager.addMapping(talk, TALK);
        inputManager.addListener(st, talk);

        inputManager.addMapping(change, CHANGECAMERA);
        inputManager.addListener(st, change);


        inputManager.addMapping(bag, BAG);
        inputManager.addListener(st, bag);


        inputManager.addMapping(move, MOVE);
        inputManager.addListener(st, move);

        for (BaseAppState baseAppState : states) {
            if (!(baseAppState instanceof MenuAppState) && !(baseAppState instanceof BagAppState) && !(baseAppState instanceof ShopAppState) && !(baseAppState instanceof SmallMap)) {
                baseAppState.setEnabled(true);
                //state.detach(baseAppState);
            }

        }
    }
    private Spatial getPicture(int number) {
        // 创建一个四边形

        int x=app.getCamera().getWidth();
        int y=app.getCamera().getHeight();
        Quad quad = new Quad(x, y);
        Geometry geom = new Geometry("Picture", quad);
        Texture tex;
        // 加载图片
        switch(number){
            case 1:
                tex =  app.getAssetManager().loadTexture("Map/first.png");
                break;
            case 2:
                tex =  app.getAssetManager().loadTexture("Map/second.png");
                break;
            case 3:
                tex = app.getAssetManager().loadTexture("Map/third.png");
                break;
            case 4:
                tex = app.getAssetManager().loadTexture("Map/forth.png");
                break;
            case 5:
                tex =  app.getAssetManager().loadTexture("Map/fifth.png");
                break;
            case 6:
                tex = app.getAssetManager().loadTexture("Map/sixth.png");
                break;
            default:
                tex = app.getAssetManager().loadTexture("Map/first.png");
        }

        Material mat = new Material( app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setTexture("ColorMap", tex);

        // 应用这个材质
        geom.setMaterial(mat);

        return geom;
    }

    @Override
    protected void onDisable() {
        System.out.println("first disable");

        inputManager.removeListener(st);

        inputManager.deleteTrigger(talk, TALK);
        inputManager.deleteTrigger(change, CHANGECAMERA);
        inputManager.deleteTrigger(bag, BAG);
        inputManager.deleteTrigger(move, MOVE);
        for (BaseAppState baseAppState : states) {
            if (!(baseAppState instanceof MenuAppState) && !(baseAppState instanceof BagAppState) && !(baseAppState instanceof ShopAppState) && !(baseAppState instanceof SmallMap)) {
                baseAppState.setEnabled(false);
                //state.detach(baseAppState);
            }

        }
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
