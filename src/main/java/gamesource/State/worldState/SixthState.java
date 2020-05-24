package gamesource.State.worldState;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.state.BaseAppState;
import com.jme3.bounding.BoundingVolume;
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
import gamesource.State.CharacterState.enemies.StuxnetState;
import gamesource.State.CharacterState.enemies.sixth.Master2;
import gamesource.State.CharacterState.enemies.sixth.darkKnight;
import gamesource.State.CharacterState.enemies.sixth.darkSolidier;
import gamesource.State.SpecialEffect.FirstWorldOtherSpecial;
import gamesource.State.SpecialEffect.ThirdWorldLight;
import gamesource.State.SpecialEffect.Water;
import gamesource.State.SpecialEffect.makeCross;
import gamesource.State.controlState.InputAppState;
import gamesource.State.controlState.PositionInputState;
import gamesource.State.mapState.FirstWorldState;
import gamesource.State.mapState.SkyBox;
import gamesource.State.mapState.skyBox6;
import gamesource.battleState.appState.EnemyState;
import gamesource.battleState.appState.GetCardState;
import gamesource.battleState.battle.Battle;
import gamesource.battleState.character.enemy.originalForest.BlackSlime;
import gamesource.battleState.character.enemy.originalForest.RedSlime;
import gamesource.battleState.character.enemy.originalForest.Slime;
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
    private InputManager inputManager;
    private darkSolidier solidier1=new darkSolidier(5,new Vector3f(49.65925f, -25.159817f, -11.53271f),1.9f);
    private darkKnight knight1=new darkKnight(5,new Vector3f(-46.003223f, 4.131584f, -23.703917f),-2.6f);
    private AppStateManager state;

    private int canmove = 1;

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
    private ThirdWorldLight light = new ThirdWorldLight();
    Water x1 = new Water(-34.4f);
    private FirstWorldOtherSpecial effect = new FirstWorldOtherSpecial();
    private Master2 master = new Master2(new Vector3f(12.150929f, -9.193834f, -33.237625f), -2.6f);
    private StuxnetState boss=new StuxnetState(new Vector3f(-36.57827f, 2.9845061f, -7.4623866f),-2.6f);

    private ArrayList<BaseAppState> states = new ArrayList<BaseAppState>();

    protected void initialize(Application application) {
        state = application.getStateManager();
        cam = application.getCamera();
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
        state.attach(new skyBox6());


        this.inputManager = application.getInputManager();
        inputManager.addMapping(talk, TALK);
        inputManager.addListener(st, talk);

        inputManager.addMapping(change, CHANGECAMERA);
        inputManager.addListener(st, change);

        inputManager.addMapping(bag, BAG);
        inputManager.addListener(st, bag);

        inputManager.addMapping(move, MOVE);
        inputManager.addListener(st, move);

        cam.lookAtDirection(new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
        major.setPlace(new Vector3f(0f, 10f, 0f));
        major.height(6);
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
            BoundingVolume ske = master.get();
            CollisionResults result = new CollisionResults();
            maj.collideWith(ske, result);
            return result;
        } catch (Exception e) {
            return null;
        }
    }
    public CollisionResults results4() {
        try {
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
                System.out.println("change");
                major.change();
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
            if (move.equals(name) && isPressed) {
                if (move.equals(name) && isPressed) {
                    if (results1 != null && results1.size() > 0) {
                        battle1 = 0;
                    } else if (results2 != null && results2.size() > 0) {
                        battle1 = 1;
                    } else if (results3 != null && results3.size() > 0) {
                        battle1 = 2;
                    } else if (results4 != null && results4.size() > 0) {
                        battle1 = 3;
                    }
                }

            }
            switch (battle1) {
                case 0:
                    state.detach(input);
                    states.remove(input);
                    inputManager.deleteTrigger(talk, TALK);
                    inputManager.deleteTrigger(change, CHANGECAMERA);
                    inputManager.deleteTrigger(bag, BAG);
                    inputManager.deleteTrigger(move, MOVE);
                    EnemyState.getInstance().addEnemies(
                            new Slime(100, "character/Solidier/darksolidier.j3o", 0, 0, 0, 0, 0, 0, 0, 0));
                    state.detach(solidier1);
                    states.remove(solidier1);
                    state.attach(new Battle(states));
//                    cam.setLocation(new Vector3f(0, 0, 10.3f));
//                    cam.lookAtDirection(new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
                    GetCardState.setGoldCountAfterThisBattle(25);

                    break;
                case 1:
                    state.detach(input);
                    states.remove(input);
                    inputManager.deleteTrigger(talk, TALK);
                    inputManager.deleteTrigger(change, CHANGECAMERA);
                    inputManager.deleteTrigger(bag, BAG);
                    inputManager.deleteTrigger(move, MOVE);
                    EnemyState.getInstance().addEnemies(
                            new Slime(120, "character/master/Master2.j3o", 0, 0, 0, 0, 0, 0, 0, 0));
                    state.detach(knight1);
                    states.remove(knight1);
                    state.attach(new Battle(states));
//                    cam.setLocation(new Vector3f(0, 0, 10.3f));
//                    cam.lookAtDirection(new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
                    GetCardState.setGoldCountAfterThisBattle(25);

                    break;
                case 2:
                    state.detach(input);
                    states.remove(input);
                    inputManager.deleteTrigger(talk, TALK);
                    inputManager.deleteTrigger(change, CHANGECAMERA);
                    inputManager.deleteTrigger(bag, BAG);
                    inputManager.deleteTrigger(move, MOVE);
                    EnemyState.getInstance().addEnemies(
                            new Slime(150, "character/Knight/darkknight.j3o", 2, 0, 0, 0, 0, 0, 0, 0)
                    );
                    state.detach(master);
                    states.remove(master);
                    state.attach(new Battle(states));
//                    cam.setLocation(new Vector3f(0, 0, 10.3f));
//                    cam.lookAtDirection(new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
                    GetCardState.setGoldCountAfterThisBattle(30);

                    break;
                case 3:
                    state.detach(input);
                    states.remove(input);
                    inputManager.deleteTrigger(talk, TALK);
                    inputManager.deleteTrigger(change, CHANGECAMERA);
                    inputManager.deleteTrigger(bag, BAG);
                    inputManager.deleteTrigger(move, MOVE);
                    EnemyState.getInstance().addEnemies(
                            new RedSlime(200, "Enemies/zhenwang/boss.j3o", 5, 0, 0, 0, 0, 0, 0, 0));
                    state.detach(boss);
                    states.remove(boss);
                    state.attach(new Battle(states));
//                    cam.setLocation(new Vector3f(0, 0, 10.3f));
//                    cam.lookAtDirection(new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
                    GetCardState.setGoldCountAfterThisBattle(30);

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
        if (chan == 0) {
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
        inputManager.addMapping(talk, TALK);
        inputManager.addMapping(change, CHANGECAMERA);
        inputManager.addMapping(bag, BAG);
        inputManager.addMapping(move, MOVE);

        inputManager.addListener(st, talk);
        inputManager.addListener(st, change);
        inputManager.addListener(st, bag);
        inputManager.addListener(st, move);
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