package gamesource.State.worldState;

import com.jme3.app.Application;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.state.BaseAppState;
import com.jme3.bounding.BoundingVolume;
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
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;
import gamesource.State.CharacterState.MajorActor;
import gamesource.State.CharacterState.enemies.StuxnetState;
import gamesource.State.CharacterState.enemies.sixth.Master2;
import gamesource.State.CharacterState.enemies.sixth.darkKnight;
import gamesource.State.CharacterState.enemies.sixth.darkSolidier;
import gamesource.State.SpecialEffect.*;
import gamesource.State.controlState.InputAppState;
import gamesource.State.controlState.PositionInputState;
import gamesource.State.mapState.FirstWorldState;
import gamesource.State.mapState.skyBox6;
import gamesource.State.musicState.SixthBackMusic;
import gamesource.battleState.appState.BattleBackGroundState;
import gamesource.battleState.appState.EnemyState;
import gamesource.battleState.appState.GetCardState;
import gamesource.battleState.battle.Battle;
import gamesource.battleState.character.enemy.boss.Ace;
import gamesource.battleState.character.enemy.boss.Faker;
import gamesource.battleState.character.enemy.boss.Tuxnet;
import gamesource.battleState.character.enemy.boss.Zac;
import gamesource.uiState.SmallMap;
import gamesource.uiState.bagstate.BagAppState;
import gamesource.uiState.menustate.MenuAppState;
import gamesource.uiState.shopstate.ShopAppState;

import java.util.ArrayList;

public class SixthState extends BaseAppState {
    public static String canGo = "cannot"; // 这是用于存档的变量, 大佬们不要改没了
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
    private darkSolidier solidier1=new darkSolidier(5,new Vector3f(49.65925f, -25.159817f, -11.53271f),1.9f);
    private darkKnight knight1=new darkKnight(5,new Vector3f(-46.003223f, 4.131584f, -23.703917f),-2.6f);
    private AppStateManager state;

    private SimpleApplication app;
    private int canmove = 1;
    private int fly=0;

    private int Cro=1;
    Ray ray;

    private Camera cam;

    MajorActor major;
    InputAppState input;
    FirstWorldState world = new FirstWorldState();

    private BoundingVolume maj;

    private float time = 0;

    private int chan = 0;

    private StartTalk st = new StartTalk();

    private BagAppState bagState;
    private ShopAppState shopState;
    private MenuAppState menuState;
    private makeCross cross;
    private SmallMap smallMap;

    private SixthWorldLight light;
    Water x1 = new Water(-34.4f);
    private FirstWorldOtherSpecial effect = new FirstWorldOtherSpecial();
    private Master2 master = new Master2(new Vector3f(12.150929f, -9.193834f, -33.237625f), -2.6f);
    private StuxnetState boss=new StuxnetState(new Vector3f(-36.57827f, 2.9845061f, -7.4623866f),-2.6f);
    private skyBox6 sky;
    private SixthBackMusic music=new SixthBackMusic();
    private int shadow=1024,open=0;
    private ArrayList<BaseAppState> states = new ArrayList<BaseAppState>();

    protected void initialize(Application application) {
        BattleBackGroundState.setBackgroundSrc("Map/first/ditu.j3o");

        app = (SimpleApplication) application;
        state = application.getStateManager();
        cam = application.getCamera();
        Spatial pic=getPicture(6);
        app.getGuiNode().attachChild(pic);
        state.attach(world);
        states.add(world);
        state.attach(new PositionInputState());
        input = state.getState(InputAppState.class);
        states.add(input);
        major = state.getState(MajorActor.class);
        states.add(major);
        bagState = state.getState(BagAppState.class);
        states.add(bagState);
        shopState = state.getState(ShopAppState.class);
        states.add(shopState);
        menuState = state.getState(MenuAppState.class);
        states.add(menuState);
        cross = state.getState(makeCross.class);
        states.add(cross);
        light = new SixthWorldLight(open,shadow);
        state.attach(light);
        states.add(light);
        state.attach(effect);
        states.add(effect);
        state.attach(x1);
        states.add(x1);
        state.attach(master);
        states.add(master);
        state.attach(solidier1);
        states.add(solidier1);
        state.attach(knight1);
        states.add(knight1);
        state.attach(boss);
        states.add(boss);
        state.attach(music);
        states.add(music);

        smallMap = new SmallMap(1600, 900, new Vector2f(400, 400), 6);
        state.attach(smallMap);
        states.add(smallMap);
        sky=new skyBox6(pic);
        state.attach(sky);
        states.add(sky);

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

        cam.lookAtDirection(new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
        //major.setPlace(new Vector3f(12.901182f, -9.024441f, -25.671635f));
        major.setPlace(new Vector3f(92.35694f, -31.713285f, 17.851564f));
        major.height(6);
        state.detach(state.getState(FlyCamAppState.class));
        major.change2();
    }
    public SixthState(){

    }
    public SixthState(int shadow,int open){
        this.shadow=shadow;
        this.open=open;
    }

    public CollisionResults results1() {
        try {
            maj = major.getMajor();
            BoundingVolume ske = solidier1.get();
            CollisionResults result = new CollisionResults();
            maj.collideWith(ske, result);
            return result;
        } catch (Exception e) {
            return null;
        }
    }
    public CollisionResults results2() {
        try {
            maj = major.getMajor();
            BoundingVolume ske = knight1.get();
            CollisionResults result = new CollisionResults();
            maj.collideWith(ske, result);
            return result;
        } catch (Exception e) {
            return null;
        }
    }
    public CollisionResults results3() {
        try {
            maj = major.getMajor();
            BoundingVolume ske = master.get();
            CollisionResults result = new CollisionResults();
            maj.collideWith(ske, result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public CollisionResults results4() {
        try {
            maj = major.getMajor();
            BoundingVolume ske = boss.get();
            CollisionResults result = new CollisionResults();
            maj.collideWith(ske, result);
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    class StartTalk implements ActionListener {
        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            int battle1 = -1;
            CollisionResults results1 = results1();
            CollisionResults results2 = results2();
            CollisionResults results3 = results3();
            CollisionResults results4 = results4();
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
            }if (map.equals(name) && isPressed) {
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
            if (move.equals(name) && isPressed) {
                if (move.equals(name) && isPressed) {
                    if (results1 != null && results1.size() > 0) {
                        battle1 = 0;
                    } else if (results2 != null && results2.size() > 0) {
                        System.out.println("battle+battle");
                        battle1 = 1;
                    } else if (results3 != null && results3.size() > 0) {
                        battle1 = 2;
                    } else if (results4 != null && results4.size() > 0) {
                        System.out.println("444444444");
                        battle1 = 3;
                    }
                }

            }
            switch (battle1) {
                case 0:
                    state.detach(input);
                    states.remove(input);
                    bagState.onFight();
                    shopState.onFight();
                    inputManager.deleteTrigger(talk, TALK);
                    inputManager.deleteTrigger(change, CHANGECAMERA);
                    inputManager.deleteTrigger(bag, BAG);
                    inputManager.deleteTrigger(move, MOVE);
                    inputManager.deleteTrigger(menu, MENU);
                    inputManager.deleteTrigger(map, MAP);
                    EnemyState.getInstance().addEnemies(
                            new Zac(350, "character/solidier/darksolidier.j3o", 5, 1, 2, 0, 0, 0, 0, 0));
                    state.detach(solidier1);
                    states.remove(solidier1);
                    state.attach(new Battle(states));
//                    cam.setLocation(new Vector3f(0, 0, 10.3f));
//                    cam.lookAtDirection(new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
                    GetCardState.setGoldCountAfterThisBattle(200);

                    break;
                case 1:
                    state.detach(input);
                    states.remove(input);
                    bagState.onFight();
                    shopState.onFight();
                    inputManager.deleteTrigger(talk, TALK);
                    inputManager.deleteTrigger(change, CHANGECAMERA);
                    inputManager.deleteTrigger(bag, BAG);
                    inputManager.deleteTrigger(move, MOVE);
                    inputManager.deleteTrigger(menu, MENU);
                    inputManager.deleteTrigger(map, MAP);
                    EnemyState.getInstance().addEnemies(
                            new Ace(450, "character/Knight/darkknight.j3o", 0, 1, 1, 0, 0, 0, 0, 0));
                    state.detach(knight1);
                    states.remove(knight1);
                    state.attach(new Battle(states));
//                    cam.setLocation(new Vector3f(0, 0, 10.3f));
//                    cam.lookAtDirection(new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
                    GetCardState.setGoldCountAfterThisBattle(200);

                    break;
                case 2:
                    state.detach(input);
                    states.remove(input);
                    bagState.onFight();
                    shopState.onFight();
                    inputManager.deleteTrigger(talk, TALK);
                    inputManager.deleteTrigger(change, CHANGECAMERA);
                    inputManager.deleteTrigger(bag, BAG);
                    inputManager.deleteTrigger(move, MOVE);
                    inputManager.deleteTrigger(menu, MENU);
                    inputManager.deleteTrigger(map, MAP);
                    EnemyState.getInstance().addEnemies(
                            new Faker(400, "character/master/Master2.j3o", 2, 2, 2, 0, 0, 0, 0, 0)
                    );
                    state.detach(master);
                    states.remove(master);
                    state.attach(new Battle(states));
//                    cam.setLocation(new Vector3f(0, 0, 10.3f));
//                    cam.lookAtDirection(new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
                    GetCardState.setGoldCountAfterThisBattle(200);

                    break;
                case 3:
                    state.detach(input);
                    states.remove(input);
                    bagState.onFight();
                    shopState.onFight();
                    inputManager.deleteTrigger(talk, TALK);
                    inputManager.deleteTrigger(change, CHANGECAMERA);
                    inputManager.deleteTrigger(bag, BAG);
                    inputManager.deleteTrigger(move, MOVE);
                    inputManager.deleteTrigger(menu, MENU);
                    inputManager.deleteTrigger(map, MAP);
                    EnemyState.getInstance().addEnemies(
                            new Tuxnet(900, "Enemies/zhenwang/boss.j3o", 0, 3, 5, 3, 5, 5, 0, 0));
                    state.detach(boss);
                    states.remove(boss);
                    state.attach(new Battle(states));
                    SeventhState.canGo = "can";
//                    cam.setLocation(new Vector3f(0, 0, 10.3f));
//                    cam.lookAtDirection(new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
                    GetCardState.setGoldCountAfterThisBattle(300);

                    break;

            }
        }
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

    public void update(float tpf) {
        /*if (chan == 0) {
            time = time + tpf;
            if (time < 60 && time > 10) {
                change();
                cross.setEnabled(false);
            }
        }*/
        /*if(chan==0) {
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
    protected void cleanup(Application application) {
    }

    @Override
    protected void onEnable() {
        BattleBackGroundState.setBackgroundSrc("Map/first/ditu.j3o");

        inputManager.addMapping(talk, TALK);
        inputManager.addMapping(change, CHANGECAMERA);
        inputManager.addMapping(bag, BAG);
        inputManager.addMapping(move, MOVE);

        inputManager.addListener(st, talk);
        inputManager.addListener(st, change);
        inputManager.addListener(st, bag);
        inputManager.addListener(st, move);
        inputManager.addMapping(menu, MENU);
        inputManager.addListener(st,menu);


        inputManager.addMapping(map, MAP);
        inputManager.addListener(st, map);
        for (BaseAppState baseAppState : states) {
            if (!(baseAppState instanceof MenuAppState) && !(baseAppState instanceof BagAppState) && !(baseAppState instanceof ShopAppState)) {
                baseAppState.setEnabled(true);
                //state.detach(baseAppState);
            }

        }
    }

    @Override
    protected void onDisable() {
        System.out.println("second disable");
        inputManager.removeListener(st);
        inputManager.deleteTrigger(talk, TALK);
        inputManager.deleteTrigger(change, CHANGECAMERA);
        inputManager.deleteTrigger(bag, BAG);
        inputManager.deleteTrigger(move, MOVE);
        inputManager.deleteTrigger(menu, MENU);
        inputManager.deleteTrigger(map, MAP);
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