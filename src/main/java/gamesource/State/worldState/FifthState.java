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
import gamesource.State.CharacterState.Master1;
import gamesource.State.CharacterState.enemies.SnowRobotState;
import gamesource.State.CharacterState.enemies.fifth.flyRobot;
import gamesource.State.CharacterState.enemies.fifth.plane;
import gamesource.State.CharacterState.enemies.fifth.walkRobot;
import gamesource.State.CharacterState.firstWorldCharacter.lizardState;
import gamesource.State.SpecialEffect.FirstWorldOtherSpecial;
import gamesource.State.SpecialEffect.ThirdWorldLight;
import gamesource.State.SpecialEffect.Water;
import gamesource.State.SpecialEffect.makeCross;
import gamesource.State.controlState.InputAppState;
import gamesource.State.controlState.PositionInputState;
import gamesource.State.mapState.Chest;
import gamesource.State.mapState.FifthWorldState;
import gamesource.State.mapState.SkyBox;
import gamesource.battleState.appState.BattleBackGroundState;
import gamesource.battleState.appState.EnemyState;
import gamesource.battleState.appState.GetCardState;
import gamesource.battleState.battle.Battle;
import gamesource.battleState.character.enemy.mechanicalEmpire.RampageRobot;
import gamesource.battleState.character.enemy.mechanicalEmpire.Robot;
import gamesource.battleState.character.enemy.mechanicalEmpire.SteamRobot;
import gamesource.battleState.character.enemy.mechanicalEmpire.StrongRobot;
import gamesource.battleState.character.enemy.originalForest.*;
import gamesource.uiState.bagstate.BagAppState;
import gamesource.uiState.menustate.MenuAppState;
import gamesource.uiState.shopstate.ShopAppState;

import java.util.ArrayList;

public class FifthState extends BaseAppState {
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
    FifthWorldState world=new FifthWorldState();
    // MajorActor major=new MajorActor(new Vector3f(-0.5884632f, -25.645144f, 76.421844f));
    Chest c1 = new Chest(new Vector3f(21.895473f, -23.64f, 28.8404f),-1.6f);
    Chest c2 = new Chest(new Vector3f(9.5974655f, 16.679998f, -30.491623f),-1.6f);

    //这个地图暂时这5种怪物，想新加new一下加入state里面，构造函数第一个参数是位置，第二个是朝向，进入游戏需要先按c进入第一人称，再按f1来获取玩家坐标

    private plane p1=new plane(new Vector3f(22.074516f, -10.199999f, -23.742216f),0,0,8,0);
    private plane p2=new plane(new Vector3f(46.47963f, -10.199999f, -7.7142005f),0f,8,0,0);
    private plane p3=new plane(new Vector3f(11.662053f, 1.3200008f, 26.24392f),-1.5f,0,8,0);
    private walkRobot robot1=new walkRobot(new Vector3f(26.040071f, -21.72f, -1.5775526f),3.0f,0,4,0);
    private flyRobot robot2=new flyRobot(new Vector3f(0,0,0),2.2f);
    private flyRobot robot3=new flyRobot(new Vector3f(-12.852558f, -33.24f, -13.446446f),-2.4f,-3f,3,0,2,9);
    private flyRobot robot4=new flyRobot(new Vector3f(-17.62852f, -33.24f, 0.89568424f),-0.9f,3f,3,0,2,11);
    private SnowRobotState robot5=new SnowRobotState(new Vector3f(18.963163f, -33.24f, 0.511641f),1.3f);
    private walkRobot robot6=new walkRobot(new Vector3f(34.28899f, -21.72f, -57.356865f),4.7f,15,0,0,40);
    private walkRobot robot7=new walkRobot(new Vector3f(9.650833f, -10.199997f, -36.951283f),-0.6f,8,-8,0,7);
    private SnowRobotState robot8=new SnowRobotState(new Vector3f(-26.445934f, 1.3200009f, -3.8378994f),-0.3f);
    private lizardState lizard=new lizardState(new Vector3f(6.345871f, 16.74f, -24.815386f),-0.3f);
    private Master1 master=new Master1(new Vector3f(9.348317f, 16.739998f, -24.272078f),-0.3f);
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
    private FirstWorldOtherSpecial effect=new FirstWorldOtherSpecial();

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
        state.attach(master);
        states.add(master);
        states.add(cross);
        //state.attach(p1);
        state.attach(robot1);
        states.add(robot1);
        state.attach(robot2);
        states.add(robot2);
        state.attach(robot3);
        states.add(robot3);
        state.attach(robot4);
        states.add(robot4);
        state.attach(robot5);
        states.add(robot5);
        state.attach(robot6);
        states.add(robot6);
        state.attach(robot7);
        states.add(robot7);
        state.attach(robot8);
        states.add(robot8);
        state.attach(p1);
        states.add(p1);
        state.attach(p2);
        states.add(p2);
        state.attach(p3);
        states.add(p3);
        state.attach(lizard);
        states.add(lizard);
        state.attach(light);
        states.add(light);
        state.attach(effect);
        states.add(effect);
        state.attach(c1);
        states.add(c1);
        state.attach(c2);
        states.add(c2);
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
        major.setPlace(new Vector3f(0f, 0f, 0f));
        major.height(22f);
        BattleBackGroundState.setBackgroundSrc("Map/fifth.j3o");
    }



    public CollisionResults results1() {
        try {
            maj = major.getMajor();
            BoundingVolume ske = p1.get();
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
            BoundingVolume ske = p2.get();
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
            BoundingVolume ske = p3.get();
            CollisionResults result = new CollisionResults();
            maj.collideWith(ske, result);
            return result;
        } catch (Exception e) {
            return null;
        }
    }
    public CollisionResults results4() {
        try {
            maj = major.getMajor();
            BoundingVolume ske = robot1.get();
            CollisionResults result = new CollisionResults();
            maj.collideWith(ske, result);
            return result;
        } catch (Exception e) {
            return null;
        }
    }
    public CollisionResults results5() {
        try {
            maj = major.getMajor();
            BoundingVolume ske = robot2.get();
            CollisionResults result = new CollisionResults();
            maj.collideWith(ske, result);
            return result;
        } catch (Exception e) {
            return null;
        }
    }
    public CollisionResults results6() {
        try {
            maj = major.getMajor();
            BoundingVolume ske = robot3.get();
            CollisionResults result = new CollisionResults();
            maj.collideWith(ske, result);
            return result;
        } catch (Exception e) {
            return null;
        }
    }
    public CollisionResults results7() {
        try {
            maj = major.getMajor();
            BoundingVolume ske = robot4.get();
            CollisionResults result = new CollisionResults();
            maj.collideWith(ske, result);
            return result;
        } catch (Exception e) {
            return null;
        }
    }
    public CollisionResults results8() {
        try {
            maj = major.getMajor();
            BoundingVolume ske = robot5.get();
            CollisionResults result = new CollisionResults();
            maj.collideWith(ske, result);
            return result;
        } catch (Exception e) {
            return null;
        }
    }
    public CollisionResults results9() {
        try {
            maj = major.getMajor();
            BoundingVolume ske = robot6.get();
            CollisionResults result = new CollisionResults();
            maj.collideWith(ske, result);
            return result;
        } catch (Exception e) {
            return null;
        }
    }
    public CollisionResults results10() {
        try {
            maj = major.getMajor();
            BoundingVolume ske = robot7.get();
            CollisionResults result = new CollisionResults();
            maj.collideWith(ske, result);
            return result;
        } catch (Exception e) {
            return null;
        }
    }
    public CollisionResults results11() {
        try {
            maj = major.getMajor();
            BoundingVolume ske = robot8.get();
            CollisionResults result = new CollisionResults();
            maj.collideWith(ske, result);
            return result;
        } catch (Exception e) {
            return null;
        }
    }
    public CollisionResults results12(){
        try {
            maj = major.getMajor();
            BoundingVolume kni = c1.get();
            CollisionResults results = new CollisionResults();
            maj.collideWith(kni, results);
            return results;
        }catch(Exception e){
            return null;
        }
    }
    public CollisionResults results13(){
        try {
            maj = major.getMajor();
            BoundingVolume kni = c2.get();
            CollisionResults results = new CollisionResults();
            maj.collideWith(kni, results);
            return results;
        }catch(Exception e){
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
            CollisionResults results5 = results5();
            CollisionResults results6 = results6();
            CollisionResults results7 = results7();
            CollisionResults results8 = results8();
            CollisionResults results9 = results9();
            CollisionResults results10 = results10();
            CollisionResults results11 = results11();
            CollisionResults results12 = results12();
            CollisionResults results13 = results13();
            if (move.equals(name) && isPressed) {
                if (results1 != null && results1.size() > 0) {
                    battle1 = 0;
                } else if (results2 != null && results2.size() > 0) {
                    battle1 = 1;
                } else if (results3 != null && results3.size() > 0) {
                    battle1 = 2;
                } else if (results4 != null && results4.size() > 0) {
                    battle1 = 3;
                } else if (results5 != null && results5.size() > 0) {
                    battle1 = 4;
                } else if (results6 != null && results6.size() > 0) {
                    battle1 = 5;
                } else if (results7 != null && results7.size() > 0) {
                    battle1 = 6;
                } else if (results8 != null && results8.size() > 0) {
                    battle1 = 7;
                } else if (results9 != null && results9.size() > 0) {
                    battle1 = 8;
                } else if (results10 != null && results10.size() > 0) {
                    battle1 = 9;
                } else if (results11 != null && results11.size() > 0) {
                    battle1 = 10;
                } else if(results12!=null&&results12.size()>0){
                    System.out.println("chest");
                    c1.open();
                }else if(results13!=null&&results13.size()>0){
                    System.out.println("chest");
                    c2.open();
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
                            new Robot(80, "Enemies/fifthMap/airplane.j3o", 5, 0, 0, 0, 0, 0, 0, 0),
                            new Robot(80, "Enemies/fifthMap/airplane.j3o", 5, 0, 0, 0, 0, 0, 0, 0)
                    );
                    state.detach(p1);
                    states.remove(p1);
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
                            new Robot(80, "Enemies/fifthMap/airplane.j3o", 0, 0, 0, 0, 0, 0, 0, 0),
                            new Robot(80, "Enemies/fifthMap/airplane.j3o", 0, 0, 0, 0, 0, 0, 0, 0)
                    );
                    state.detach(p2);
                    states.remove(p2);
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
                            new Robot(80, "Enemies/fifthMap/airplane.j3o", 0, 0, 0, 0, 0, 0, 0, 0),
                            new Robot(80, "Enemies/fifthMap/airplane.j3o", 0, 0, 0, 0, 0, 0, 0, 0)
                    );
                    state.detach(p3);
                    states.remove(p3);
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
                            new SteamRobot(75, "Enemies/fifthMap/walking_robot/scene.j3o", 5, 0, 0, 0, 0, 0, 0, 0),
                            new SteamRobot(75, "Enemies/fifthMap/walking_robot/scene.j3o", 5, 0, 0, 0, 0, 0, 0, 0)
                    );
                    state.detach(robot1);
                    states.remove(robot1);
                    state.attach(new Battle(states));
//                    cam.setLocation(new Vector3f(0, 0, 10.3f));
//                    cam.lookAtDirection(new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
                    GetCardState.setGoldCountAfterThisBattle(30);

                    break;

                case 4:
                    state.detach(input);
                    states.remove(input);
                    inputManager.deleteTrigger(talk, TALK);
                    inputManager.deleteTrigger(change, CHANGECAMERA);
                    inputManager.deleteTrigger(bag, BAG);
                    inputManager.deleteTrigger(move, MOVE);
                    EnemyState.getInstance().addEnemies(
                            new StrongRobot(80, "Enemies/fifthMap/mech_drone/scene.j3o", 0, 1, 0, 0, 0, 0, 0, 0),
                            new StrongRobot(80, "Enemies/fifthMap/mech_drone/scene.j3o", 0, 3, 0, 0, 0, 0, 0, 0)
                    );
                    state.detach(robot2);
                    states.remove(robot2);
                    state.attach(new Battle(states));
//                    cam.setLocation(new Vector3f(0, 0, 10.3f));
//                    cam.lookAtDirection(new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
                    GetCardState.setGoldCountAfterThisBattle(40);

                    break;

                case 5:
                    state.detach(input);
                    states.remove(input);
                    inputManager.deleteTrigger(talk, TALK);
                    inputManager.deleteTrigger(change, CHANGECAMERA);
                    inputManager.deleteTrigger(bag, BAG);
                    inputManager.deleteTrigger(move, MOVE);
                    EnemyState.getInstance().addEnemies(
                            new StrongRobot(80, "Enemies/fifthMap/mech_drone/scene.j3o", 0, 1, 0, 0, 0, 0, 0, 0),
                            new StrongRobot(80, "Enemies/fifthMap/mech_drone/scene.j3o", 0, 3, 0, 0, 0, 0, 0, 0)
                    );
                    state.detach(robot3);
                    states.remove(robot3);
                    state.attach(new Battle(states));
//                    cam.setLocation(new Vector3f(0, 0, 10.3f));
//                    cam.lookAtDirection(new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
                    GetCardState.setGoldCountAfterThisBattle(40);

                    break;

                case 6:
                    state.detach(input);
                    states.remove(input);
                    inputManager.deleteTrigger(talk, TALK);
                    inputManager.deleteTrigger(change, CHANGECAMERA);
                    inputManager.deleteTrigger(bag, BAG);
                    inputManager.deleteTrigger(move, MOVE);
                    EnemyState.getInstance().addEnemies(
                            new StrongRobot(80, "Enemies/fifthMap/mech_drone/scene.j3o", 0, 1, 0, 0, 0, 0, 0, 0),
                            new StrongRobot(80, "Enemies/fifthMap/mech_drone/scene.j3o", 0, 3, 0, 0, 0, 0, 0, 0)
                    );
                    state.detach(robot4);
                    states.remove(robot4);
                    state.attach(new Battle(states));
//                    cam.setLocation(new Vector3f(0, 0, 10.3f));
//                    cam.lookAtDirection(new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
                    GetCardState.setGoldCountAfterThisBattle(40);

                    break;


                case 7:
                    state.detach(input);
                    states.remove(input);
                    inputManager.deleteTrigger(talk, TALK);
                    inputManager.deleteTrigger(change, CHANGECAMERA);
                    inputManager.deleteTrigger(bag, BAG);
                    inputManager.deleteTrigger(move, MOVE);
                    EnemyState.getInstance().addEnemies(
                            new RampageRobot(80, "Enemies/snowRobot/snowRobot.j3o", 0, 1, 0, 0, 0, 0, 0, 0),
                            new RampageRobot(80, "Enemies/snowRobot/snowRobot.j3o", 0, 3, 0, 0, 0, 0, 0, 0)
                    );
                    state.detach(robot5);
                    states.remove(robot5);
                    state.attach(new Battle(states));
//                    cam.setLocation(new Vector3f(0, 0, 10.3f));
//                    cam.lookAtDirection(new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
                    GetCardState.setGoldCountAfterThisBattle(40);

                    break;
                case 8:
                    state.detach(input);
                    states.remove(input);
                    inputManager.deleteTrigger(talk, TALK);
                    inputManager.deleteTrigger(change, CHANGECAMERA);
                    inputManager.deleteTrigger(bag, BAG);
                    inputManager.deleteTrigger(move, MOVE);
                    EnemyState.getInstance().addEnemies(
                            new RampageRobot(75, "Enemies/fifthMap/walking_robot/scene.j3o", 5, 0, 0, 0, 0, 0, 0, 0),
                            new RampageRobot(75, "Enemies/fifthMap/walking_robot/scene.j3o", 5, 0, 0, 0, 0, 0, 0, 0)
                    );
                    state.detach(robot6);
                    states.remove(robot6);
                    state.attach(new Battle(states));
//                    cam.setLocation(new Vector3f(0, 0, 10.3f));
//                    cam.lookAtDirection(new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
                    GetCardState.setGoldCountAfterThisBattle(30);

                    break;
                case 9:
                    state.detach(input);
                    states.remove(input);
                    inputManager.deleteTrigger(talk, TALK);
                    inputManager.deleteTrigger(change, CHANGECAMERA);
                    inputManager.deleteTrigger(bag, BAG);
                    inputManager.deleteTrigger(move, MOVE);
                    EnemyState.getInstance().addEnemies(
                            new RampageRobot(75, "Enemies/fifthMap/walking_robot/scene.j3o", 5, 0, 0, 0, 0, 0, 0, 0),
                            new RampageRobot(75, "Enemies/fifthMap/walking_robot/scene.j3o", 5, 0, 0, 0, 0, 0, 0, 0)
                    );
                    state.detach(robot7);
                    states.remove(robot7);
                    state.attach(new Battle(states));
//                    cam.setLocation(new Vector3f(0, 0, 10.3f));
//                    cam.lookAtDirection(new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
                    GetCardState.setGoldCountAfterThisBattle(30);

                    break;
                case 10:
                    state.detach(input);
                    states.remove(input);
                    inputManager.deleteTrigger(talk, TALK);
                    inputManager.deleteTrigger(change, CHANGECAMERA);
                    inputManager.deleteTrigger(bag, BAG);
                    inputManager.deleteTrigger(move, MOVE);
                    EnemyState.getInstance().addEnemies(
                            new RampageRobot(75, "Enemies/snowRobot/snowRobot.j3o", 5, 0, 0, 0, 0, 0, 0, 0),
                            new RampageRobot(75, "Enemies/snowRobot/snowRobot.j3o", 5, 0, 0, 0, 0, 0, 0, 0)
                    );
                    state.detach(robot8);
                    states.remove(robot8);
                    state.attach(new Battle(states));
//                    cam.setLocation(new Vector3f(0, 0, 10.3f));
//                    cam.lookAtDirection(new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
                    GetCardState.setGoldCountAfterThisBattle(30);

                    break;

                default:
                    break;

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