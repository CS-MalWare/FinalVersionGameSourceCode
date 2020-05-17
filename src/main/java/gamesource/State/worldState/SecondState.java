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
import gamesource.State.mapState.Chest;
import gamesource.State.mapState.SecondWorldMap;
import gamesource.State.mapState.SkyBox2;
import gamesource.State.musicState.SecondBackMusic;
import gamesource.battleState.appState.BattleBackGroundState;
import gamesource.battleState.appState.EnemyState;
import gamesource.battleState.battle.Battle;
import gamesource.battleState.character.enemy.originalForest.*;
import gamesource.uiState.bagstate.BagAppState;
import gamesource.uiState.menustate.MenuAppState;
import gamesource.uiState.shopstate.ShopAppState;

import java.util.ArrayList;

public class SecondState extends BaseAppState {
    public final static String talk = "TALK";
    public final static Trigger TALK = new KeyTrigger(KeyInput.KEY_N);
    public final static String change = "CHANGE";
    public final static Trigger CHANGECAMERA = new KeyTrigger(KeyInput.KEY_C);
    public final static String bag = "BAG";
    public final static Trigger BAG = new KeyTrigger(KeyInput.KEY_B);
    public final static String move = "MOVE";
    public final static Trigger MOVE = new KeyTrigger(KeyInput.KEY_W);
    private InputManager inputManager;

    private AppStateManager state;

    private int canmove = 1;

    Ray ray;

    private Camera cam;

    MajorActor major;
    // MajorActor major=new MajorActor(new Vector3f(-0.5884632f, -25.645144f, 76.421844f));
    Chest c1 = new Chest(new Vector3f(9.952984f, -31.962004f, 56.09926f), 4f);
    Chest c2 = new Chest(new Vector3f(-10.413138f, -29.553904f, 28.508766f));
    Chest c3 = new Chest(new Vector3f(12.185162f, -18.157299f, -74.07405f), 4.2f);
    private AngrySkeletonState skeleton1 = new AngrySkeletonState(new Vector3f(3.0883105f, -31.735968f, 43.255566f), 0.5f);

    private shanmanState shanman = new shanmanState(new Vector3f(5.1453485f, -32.197643f, 58.86895f), -1.5f);

    private goblinGirlState girl = new goblinGirlState(new Vector3f(5.3336577f, -31.696009f, 55.903286f), -1.5f);

    private GreenSkeletonState skeleton2 = new GreenSkeletonState(new Vector3f(8.143902f, -32.197643f, 44.735046f), -0.7f);
    private BlueSkeletonState skeleton3 = new BlueSkeletonState(new Vector3f(-15.3608456f, -21.152367f, 72.28308f), 2f);
    private RedSkeletonState skeleton4 = new RedSkeletonState(new Vector3f(-12.66109f, -26.166546f, 53.731964f), 0.5f);
    private BatState bat1 = new BatState(new Vector3f(-6.6939545f, -29.954361f, 29.743092f), 0.7f);
    private SnowRobotState snowRobot1 = new SnowRobotState(new Vector3f(17.560339f, -32.435986f, 35.696587f), 2f);
    private KingSkeletonState skeleton5 = new KingSkeletonState(new Vector3f(8.058961f, -31.545362f, 17.327284f), -0.7f);
    private GreenSkeletonState skeleton6 = new GreenSkeletonState(new Vector3f(-0.22325397f, -19.929562f, -6.269995f), 0.3f);
    private AngrySkeletonState skeleton7 = new AngrySkeletonState(new Vector3f(15.483921f, -24.226637f, -20.16292f), 0.5f);
    private BlueSkeletonState skeleton8 = new BlueSkeletonState(new Vector3f(7.382364f, -18.829222f, -69.045815f), -0.4f);
    private SnowRobotState snowRobot2 = new SnowRobotState(new Vector3f(-3.889712f, -18.6719f, -73.689575f), -2f);


    private InputAppState input;

    private SecondWorldMap world = new SecondWorldMap();


    private BatState bat = new BatState();

    private BossKnight knight = new BossKnight();

    BulletAppState bullet = new BulletAppState();         //现在为为世界添加物理引擎的测试情况

    private SecondBackMusic music = new SecondBackMusic();

//    private StuxnetState boss = new StuxnetState();

    private SkyBox2 sky = new SkyBox2();

    private SecondWorldLight light = new SecondWorldLight();

    private SecondWorldOtherSpecial special = new SecondWorldOtherSpecial();

    private StartTalk st = new StartTalk();

    private BoundingVolume maj;

    private float time = 0;

    private int chan = 0;

    private BagAppState bagState;
    private ShopAppState shopState;
    private MenuAppState menuState;
    private makeCross cross;

    private ArrayList<BaseAppState> states = new ArrayList<BaseAppState>();

    protected void initialize(Application application) {
        state = application.getStateManager();
        cam = application.getCamera();
        state.attach(world);
        states.add(world);
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
        state.attach(new PositionInputState());
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
        state.attach(skeleton3);
        states.add(skeleton3);
        state.attach(skeleton4);
        states.add(skeleton4);
        state.attach(bat1);
        states.add(bat1);
        state.attach(snowRobot1);
        states.add(snowRobot1);
        state.attach(skeleton5);
        states.add(skeleton5);
        state.attach(skeleton6);
        states.add(skeleton6);
        state.attach(skeleton7);
        states.add(skeleton7);
        state.attach(skeleton8);
        states.add(skeleton8);
        state.attach(snowRobot2);
        states.add(snowRobot2);

        state.attach(music);
        states.add(music);

//        state.attach(boss);
//        states.add(boss);
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
        major.setPlace(new Vector3f(-0.5884632f, -25.645144f, 76.421844f));
        BattleBackGroundState.setBackgroundSrc("Map/second.j3o");
    }

    public CollisionResults results1() {
        ray = major.get();
        BoundingVolume sha = shanman.get();
        CollisionResults result = new CollisionResults();
        ray.collideWith(sha, result);
        return result;
    }

    public CollisionResults results2() {
        ray = major.get();
        BoundingVolume gi = girl.get();
        CollisionResults result = new CollisionResults();
        ray.collideWith(gi, result);
        return result;
    }

    public CollisionResults results3() {
        maj = major.getMajor();
        BoundingVolume ske = skeleton1.get();
        CollisionResults result = new CollisionResults();
        maj.collideWith(ske, result);
        return result;
    }

    public CollisionResults results4() {
        BoundingVolume ske = skeleton2.get();
        CollisionResults result = new CollisionResults();
        maj.collideWith(ske, result);
        return result;
    }


    public CollisionResults results5() {
        BoundingVolume ske = skeleton3.get();
        CollisionResults result = new CollisionResults();
        maj.collideWith(ske, result);
        return result;
    }

    public CollisionResults results6() {
        BoundingVolume ske = skeleton4.get();
        CollisionResults result = new CollisionResults();
        maj.collideWith(ske, result);
        return result;
    }


    public CollisionResults results7() {
        BoundingVolume ske = bat1.get();
        CollisionResults result = new CollisionResults();
        maj.collideWith(ske, result);
        return result;
    }

    public CollisionResults results8() {
        BoundingVolume ske = snowRobot1.get();
        CollisionResults result = new CollisionResults();
        maj.collideWith(ske, result);
        return result;
    }

    public CollisionResults results9() {
        BoundingVolume ske = skeleton5.get();
        CollisionResults result = new CollisionResults();
        maj.collideWith(ske, result);
        return result;
    }

    public CollisionResults results10() {
        BoundingVolume ske = skeleton6.get();
        CollisionResults result = new CollisionResults();
        maj.collideWith(ske, result);
        return result;
    }

    public CollisionResults results11() {
        BoundingVolume ske = skeleton7.get();
        CollisionResults result = new CollisionResults();
        maj.collideWith(ske, result);
        return result;
    }


    public CollisionResults results12() {
        BoundingVolume ske = skeleton8.get();
        CollisionResults result = new CollisionResults();
        maj.collideWith(ske, result);
        return result;
    }

    public CollisionResults results13() {
        BoundingVolume ske = snowRobot2.get();
        CollisionResults result = new CollisionResults();
        maj.collideWith(ske, result);
        return result;
    }


    class StartTalk implements ActionListener {
        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            int battle1 = -1;
            CollisionResults results1 = results1();
            CollisionResults results2 = results2();
            CollisionResults results3 = results3();
            CollisionResults results4 = results4();
            CollisionResults results5 = results5();
            CollisionResults results6 = results6();
            CollisionResults results7 = results7();
            CollisionResults results8 = results8();
            CollisionResults results9 = results9();
            CollisionResults results10 = results10();
            CollisionResults results11 = results11();
            CollisionResults results12 = results12();
            CollisionResults results13 = results13();
            if (talk.equals(name) && isPressed) {
                if (results1.size() > 0) {
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
                } else if (results2.size() > 0) {
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
            if (move.equals(name) && isPressed) {
                if (results3.size() > 0) {
                    battle1 = 0;
                } else if (results4.size() > 0) {
                    battle1 = 1;
                } else if (results5.size() > 0) {
                    battle1 = 2;
                } else if (results6.size() > 0) {
                    battle1 = 3;
                } else if (results7.size() > 0) {
                    battle1 = 4;
                } else if (results8.size() > 0) {
                    battle1 = 5;
                } else if (results9.size() > 0) {
                    battle1 = 6;
                } else if (results10.size() > 0) {
                    battle1 = 7;
                } else if (results11.size() > 0) {
                    battle1 = 8;
                } else if (results12.size() > 0) {
                    battle1 = 9;
                } else if (results13.size() > 0) {
                    battle1 = 10;
                }
            }

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
            switch (battle1) {
                case 0:
                    state.detach(input);
                    states.remove(input);
                    inputManager.deleteTrigger(talk, TALK);
                    inputManager.deleteTrigger(change, CHANGECAMERA);
                    inputManager.deleteTrigger(bag, BAG);
                    inputManager.deleteTrigger(move, MOVE);
                    EnemyState.getInstance().addEnemies(
                            new Slime(20, "Enemies/skeleton/skeleton.j3o", 0, 0, 0, 0, 0, 0, 0, 0),
                            new Slime(15, "Enemies/skeleton/skeleton.j3o", 5, 0, 0, 0, 0, 0, 0, 0)
                    );
                    state.detach(skeleton1);
                    states.remove(skeleton1);
                    state.attach(new Battle(states));
                    cam.setLocation(new Vector3f(0, 0, 10.3f));
                    cam.lookAtDirection(new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));

                    break;
                case 1:
                    state.detach(input);
                    states.remove(input);
                    inputManager.deleteTrigger(talk, TALK);
                    inputManager.deleteTrigger(change, CHANGECAMERA);
                    inputManager.deleteTrigger(bag, BAG);
                    inputManager.deleteTrigger(move, MOVE);
                    EnemyState.getInstance().addEnemies(
                            new Slime(25, "Enemies/skeleton/greenSkeleton/greenSkeleton.j3o", 0, 0, 0, 0, 0, 0, 0, 0),
                            new Slime(20, "Enemies/skeleton/greenSkeleton/greenSkeleton.j3o", 0, 0, 0, 0, 0, 0, 0, 0)
                    );
                    state.detach(skeleton2);
                    states.remove(skeleton2);
                    state.attach(new Battle(states));
                    cam.setLocation(new Vector3f(0, 0, 10.3f));
                    cam.lookAtDirection(new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
                    break;
                case 2:
                    state.detach(input);
                    states.remove(input);
                    inputManager.deleteTrigger(talk, TALK);
                    inputManager.deleteTrigger(change, CHANGECAMERA);
                    inputManager.deleteTrigger(bag, BAG);
                    inputManager.deleteTrigger(move, MOVE);
                    EnemyState.getInstance().addEnemies(
                            new RedSlime(25, "Enemies/skeleton/blueSkeleton/blueSkeleton.j3o", 0, 0, 0, 0, 0, 0, 0, 0),
                            new Slime(27, "Enemies/skeleton/blueSkeleton/blueSkeleton.j3o", 0, 0, 0, 0, 0, 0, 0, 0)
                    );
                    state.detach(skeleton3);
                    states.remove(skeleton3);
                    state.attach(new Battle(states));
                    cam.setLocation(new Vector3f(0, 0, 10.3f));
                    cam.lookAtDirection(new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
                    break;
                case 3:
                    state.detach(input);
                    states.remove(input);
                    inputManager.deleteTrigger(talk, TALK);
                    inputManager.deleteTrigger(change, CHANGECAMERA);
                    inputManager.deleteTrigger(bag, BAG);
                    inputManager.deleteTrigger(move, MOVE);
                    EnemyState.getInstance().addEnemies(
                            new RedSlime(30, "Enemies/skeleton/redSkeleton/redSkeleton.j3o", 5, 0, 0, 0, 0, 0, 0, 0),
                            new RedSlime(25, "Enemies/skeleton/redSkeleton/redSkeleton.j3o", 5, 0, 0, 0, 0, 0, 0, 0)
                    );
                    state.detach(skeleton4);
                    states.remove(skeleton4);
                    state.attach(new Battle(states));
                    cam.setLocation(new Vector3f(0, 0, 10.3f));
                    cam.lookAtDirection(new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
                    break;

                case 4:
                    state.detach(input);
                    states.remove(input);
                    inputManager.deleteTrigger(talk, TALK);
                    inputManager.deleteTrigger(change, CHANGECAMERA);
                    inputManager.deleteTrigger(bag, BAG);
                    inputManager.deleteTrigger(move, MOVE);
                    EnemyState.getInstance().addEnemies(
                            new BlackSlime(33, "Enemies/bat/scene.j3o", 0, 1, 0, 0, 0, 0, 0, 0),
                            new BlackSlime(25, "Enemies/bat/scene.j3o", 0, 3, 0, 0, 0, 0, 0, 0)
                    );
                    state.detach(bat1);
                    states.remove(bat1);
                    state.attach(new Battle(states));
                    cam.setLocation(new Vector3f(0, 0, 10.3f));
                    cam.lookAtDirection(new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
                    break;

                case 5:
                    state.detach(input);
                    states.remove(input);
                    inputManager.deleteTrigger(talk, TALK);
                    inputManager.deleteTrigger(change, CHANGECAMERA);
                    inputManager.deleteTrigger(bag, BAG);
                    inputManager.deleteTrigger(move, MOVE);
                    EnemyState.getInstance().addEnemies(
                            new EliteSlime(70, "Enemies/snowRobot/snowRobot.j3o", 10, 1, 2, 0, 0, 0, 0, 0)
                    );
                    state.detach(snowRobot1);
                    states.remove(snowRobot1);
                    state.attach(new Battle(states));
                    cam.setLocation(new Vector3f(0, 0, 10.3f));
                    cam.lookAtDirection(new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
                    break;

                case 6:
                    state.detach(input);
                    states.remove(input);
                    inputManager.deleteTrigger(talk, TALK);
                    inputManager.deleteTrigger(change, CHANGECAMERA);
                    inputManager.deleteTrigger(bag, BAG);
                    inputManager.deleteTrigger(move, MOVE);
                    EnemyState.getInstance().addEnemies(
                            new KingSlime(150, "Enemies/skeleton/KingSkeleton/kingSkeleton.j3o", 0, 4, 1, 0, 0, 0, 0, 0)
                    );
                    state.detach(skeleton5);
                    states.remove(skeleton5);
                    state.attach(new Battle(states));
                    cam.setLocation(new Vector3f(0, 0, 10.3f));
                    cam.lookAtDirection(new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
                    break;


                case 7:
                    state.detach(input);
                    states.remove(input);
                    inputManager.deleteTrigger(talk, TALK);
                    inputManager.deleteTrigger(change, CHANGECAMERA);
                    inputManager.deleteTrigger(bag, BAG);
                    inputManager.deleteTrigger(move, MOVE);
                    EnemyState.getInstance().addEnemies(
                            new Wolfman(30, "Enemies/skeleton/greenSkeleton/greenSkeleton.j3o", 5, 0, 3, 0, 0, 0, 0, 0),
                            new Wolfman(35, "Enemies/skeleton/greenSkeleton/greenSkeleton.j3o", 5, 0, 2, 0, 0, 0, 0, 0)
                    );
                    state.detach(skeleton6);
                    states.remove(skeleton6);
                    state.attach(new Battle(states));
                    cam.setLocation(new Vector3f(0, 0, 10.3f));
                    cam.lookAtDirection(new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
                    break;
                case 8:
                    state.detach(input);
                    states.remove(input);
                    inputManager.deleteTrigger(talk, TALK);
                    inputManager.deleteTrigger(change, CHANGECAMERA);
                    inputManager.deleteTrigger(bag, BAG);
                    inputManager.deleteTrigger(move, MOVE);
                    EnemyState.getInstance().addEnemies(
                            new OneEyedWolfman(30, "Enemies/skeleton/blueSkeleton/blueSkeleton.j3o", 0, 0, 2, 0, 0, 0, 0, 0),
                            new OneEyedWolfman(45, "Enemies/skeleton/skeleton.j3o", 0, 1, 0, 0, 0, 0, 0, 0)
                    );
                    state.detach(skeleton7);
                    states.remove(skeleton7);
                    state.attach(new Battle(states));
                    cam.setLocation(new Vector3f(0, 0, 10.3f));
                    cam.lookAtDirection(new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
                    break;
                case 9:
                    state.detach(input);
                    states.remove(input);
                    inputManager.deleteTrigger(talk, TALK);
                    inputManager.deleteTrigger(change, CHANGECAMERA);
                    inputManager.deleteTrigger(bag, BAG);
                    inputManager.deleteTrigger(move, MOVE);
                    EnemyState.getInstance().addEnemies(
                            new EliteWolfman(85, "Enemies/skeleton/blueSkeleton/blueSkeleton.j3o", 0, 1, 1, 0, 0, 0, 0, 0)
                    );
                    state.detach(skeleton8);
                    states.remove(skeleton8);
                    state.attach(new Battle(states));
                    cam.setLocation(new Vector3f(0, 0, 10.3f));
                    cam.lookAtDirection(new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
                    break;
                case 10:
                    state.detach(input);
                    states.remove(input);
                    inputManager.deleteTrigger(talk, TALK);
                    inputManager.deleteTrigger(change, CHANGECAMERA);
                    inputManager.deleteTrigger(bag, BAG);
                    inputManager.deleteTrigger(move, MOVE);
                    EnemyState.getInstance().addEnemies(
                            new KingWolfman(180, "Enemies/snowRobot/snowRobot.j3o", 0, 2, 2, 0, 1, 0, 0, 0)
                    );
                    state.detach(snowRobot2);
                    states.remove(snowRobot2);
                    state.attach(new Battle(states));
                    cam.setLocation(new Vector3f(0, 0, 10.3f));
                    cam.lookAtDirection(new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
                    break;

                default:
                    break;

            }


        }
    }

    protected void cancel() {

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
        inputManager.addMapping(talk, TALK);
        inputManager.addMapping(change, CHANGECAMERA);
        inputManager.addMapping(bag, BAG);
        inputManager.addMapping(move, MOVE);

        inputManager.addListener(st, talk);
        inputManager.addListener(st, change);
        inputManager.addListener(st, bag);
        inputManager.addListener(st, move);
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
