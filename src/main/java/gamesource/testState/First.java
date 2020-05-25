package gamesource.testState;

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
import com.jme3.material.Material;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;
import gamesource.State.CharacterState.MajorActor;
import gamesource.State.CharacterState.Master1;
import gamesource.State.CharacterState.firstWorldCharacter.*;
import gamesource.State.SpecialEffect.*;
import gamesource.State.controlState.InputAppState;
import gamesource.State.controlState.PositionInputState;
import gamesource.State.mapState.*;
import gamesource.State.musicState.FirstBackMusic;
import gamesource.battleState.appState.BattleBackGroundState;
import gamesource.battleState.card.saber.power.DivineDefense;
import gamesource.uiState.SmallMap;
import gamesource.uiState.bagstate.BagAppState;
import gamesource.uiState.menustate.MenuAppState;
import gamesource.uiState.shopstate.ShopAppState;
import gamesource.util.TalkWithOption;

import java.util.ArrayList;

public class First extends BaseAppState {
    public static String canGo = "can";// 这是用于存档的变量,大佬们不要改没了
    public final static String talk = "TALK";
    public final static Trigger TALK = new KeyTrigger(KeyInput.KEY_N);
    public final static String change = "CHANGE";
    public final static Trigger CHANGECAMERA = new KeyTrigger(KeyInput.KEY_C);
    public final static String bag = "BAG";
    public final static Trigger BAG = new KeyTrigger(KeyInput.KEY_B);
    public final static String move = "MOVE";
    public final static Trigger MOVE = new KeyTrigger(KeyInput.KEY_W);
    private InputManager inputManager;

    MajorActor major;
    KingState king = new KingState(new Vector3f(-39.032665f, 4.674482f, -12.373539f), 3.9f);
    InputAppState input;
    soldierState trainSoldier = new soldierState(5, new Vector3f(74.39715f, -27.375854f, -12.961687f), 6f, 2);
    blackBoyState black1 = new blackBoyState(new Vector3f(40.09374f, -15.457153f, -47.101334f), 4.8f);
    girlState girl1 = new girlState(new Vector3f(30.66676f, -17.49788f, -38.046852f), -0.8f);
    KnightState bridgeKnight = new KnightState(5, new Vector3f(13.648198f, -9.379812f, -35.002224f), -2.4f);
    soldierState bridgeSoldier = new soldierState(5, new Vector3f(10.684639f, -9.582897f, -33.982456f), -2.9f);
    lizardState lizard = new lizardState(new Vector3f(-35.907394f, 3.7558994f, -35.212135f), 0.7f);
    KnightState knight2 = new KnightState(2, new Vector3f(-30.269215f, 3.6953368f, -39.55574f), 0.6f, 2);
    KnightState knight3 = new KnightState(3, new Vector3f(-30.080694f, 3.7788515f, -37.448612f), 3.5f, 1);
    KnightState knight4 = new KnightState(5, new Vector3f(-32.802486f, 3.7768545f, -36.82728f), 0.9f, 2);
    soldierState soldier1 = new soldierState(1, new Vector3f(75.06459f, -29.602854f, 3.6023405f));
    soldierState soldier2 = new soldierState(2, new Vector3f(76.39369f, -25.91693f, -11.675956f), 6f);
    soldierState soldier3 = new soldierState(3, new Vector3f(75.281975f, -27.164389f, -10.233985f), 2.6f, 2);
    soldierState soldier4 = new soldierState(4, new Vector3f(62.556515f, -26.660355f, -18.043798f), 2.6f);
    KnightState knight5 = new KnightState(1, new Vector3f(-46.05996f, 6.526695f, -26.613733f), 1.4f, -1, 0, 1);
    KnightState knight6 = new KnightState(1, new Vector3f(-46.07412f, 5.8200717f, -29.27783f), 4.6f, 1, 0, -1, 2);
    girlState girl = new girlState(new Vector3f(70.15531f, -27.27989f, -18.333033f), -0.3f);
    metalKnightState metalKnight = new metalKnightState(new Vector3f(64.4272f, -25.418774f, -20.45753f), -1.45f);
    blackBoyState boy1 = new blackBoyState(new Vector3f(64.8886f, -26.233301f, -9.080749f), 2.4f);
    shovelKnight s1 = new shovelKnight(new Vector3f(-35.924995f, 7.3355093f, -13.190997f), 4.4f);
    shovelKnight s2 = new shovelKnight(new Vector3f(-40.020714f, 5.764501f, -9.103482f), 3.4f);
    FirstWorldState f1 = new FirstWorldState();
    PositionInputState p1 = new PositionInputState();
    BulletAppState bullet = new BulletAppState();         //现在为为世界添加物理引擎的测试情况
    Chest c1 = new Chest(new Vector3f(62.722965f, -26.72887f, 9.268448f));
    Chest c2 = new Chest(new Vector3f(-15.538464f, -2.8196087f, -60.361416f));
    Chest c3 = new Chest(new Vector3f(-26.772413f, 6.3929253f, -8.448748f), 2.9f);
    ThirdWater x1 = new ThirdWater(-34.4f);
    SixthWorldLight light = new SixthWorldLight(1);
    FirstWorldOtherSpecial special = new FirstWorldOtherSpecial();
    SkyBox sky ;
    private Master1 master = new Master1(new Vector3f(-42.829556f, 4.341275f, -10.886024f), -2.9f);
    FirstBackMusic music=new FirstBackMusic();


    private BagAppState bagState;
    private ShopAppState shopState;
    private MenuAppState menuState;
    private makeCross cross;


    private Node rootNode;
    private SimpleApplication app;

    private AppStateManager state;

    private int canmove = 1, chan = 0;
    private int shadow=1024,open=0;
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
        Spatial pic=getPicture(5);
        app.getGuiNode().attachChild(pic);
        //state.attach(new AxisState());
        //state.attach(bullet);
        state.attach(f1);
        states.add(f1);
        //stateManager.attach(new AxisState());
        state.attach(p1);
        states.add(p1);
        light=new SixthWorldLight(1,4096*2);
        state.attach(light);
        state.attach(new skyBox6(pic));
        state.attach(x1);
        state.attach(special);
    }



    @Override
    protected void cleanup(Application application) {
        System.out.println("first clean");

        for (int i = 0; i < states.size(); i++) {
            if (!(states.get(i) instanceof MajorActor) && !(states.get(i) instanceof InputAppState) && !(states.get(i) instanceof MenuAppState) && !(states.get(i) instanceof BagAppState) && !(states.get(i) instanceof ShopAppState) && !(states.get(i) instanceof makeCross)) {
                state.detach(states.get(i));
            }
        }
        //state.cleanup();
    }

    @Override
    protected void onEnable() {

        for (BaseAppState baseAppState : states) {
            if (!(baseAppState instanceof MenuAppState) && !(baseAppState instanceof BagAppState) && !(baseAppState instanceof ShopAppState)) {
                baseAppState.setEnabled(true);
                //state.detach(baseAppState);
            }

        }
    }

    @Override
    protected void onDisable() {
        System.out.println("first disable");

        inputManager.deleteTrigger(talk, TALK);
        inputManager.deleteTrigger(change, CHANGECAMERA);
        inputManager.deleteTrigger(bag, BAG);
        inputManager.deleteTrigger(move, MOVE);
        for (BaseAppState baseAppState : states) {
            if (!(baseAppState instanceof MenuAppState) && !(baseAppState instanceof BagAppState) && !(baseAppState instanceof ShopAppState)) {
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
}
