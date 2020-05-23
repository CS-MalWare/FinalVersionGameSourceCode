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
import gamesource.State.CharacterState.enemies.BatState;
import gamesource.State.CharacterState.enemies.BlueSkeletonState;
import gamesource.State.CharacterState.enemies.RedSkeletonState;
import gamesource.State.CharacterState.enemies.forth.BossKnight;
import gamesource.State.CharacterState.enemies.forth.StoneMan;
import gamesource.State.CharacterState.enemies.forth.boss;
import gamesource.State.SpecialEffect.FifthOtherSpecial;
import gamesource.State.SpecialEffect.ThirdWorldLight;
import gamesource.State.SpecialEffect.Water;
import gamesource.State.SpecialEffect.makeCross;
import gamesource.State.controlState.InputAppState;
import gamesource.State.controlState.PositionInputState;
import gamesource.State.mapState.Chest;
import gamesource.State.mapState.ForthWorldMap;
import gamesource.State.mapState.SkyBox;
import gamesource.State.musicState.ForthBackMusic;
import gamesource.battleState.appState.BattleBackGroundState;
import gamesource.battleState.appState.EnemyState;
import gamesource.battleState.battle.Battle;
import gamesource.battleState.character.enemy.boss.Ace;
import gamesource.battleState.character.enemy.boss.Zac;
import gamesource.battleState.character.enemy.dragonWat.DarkDragon;
import gamesource.battleState.character.enemy.originalForest.*;
import gamesource.uiState.bagstate.BagAppState;
import gamesource.uiState.menustate.MenuAppState;
import gamesource.uiState.shopstate.ShopAppState;

import java.util.ArrayList;

public class ForthState extends BaseAppState {
    public static String canGo = "cannot"; // 这是用于存档的变量, 大佬们不要改没了
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
    Chest c1 = new Chest(new Vector3f(3.6333275f, -21.916998f, 25.883167f),-1.3f);
    Chest c2 = new Chest(new Vector3f(-2.0793266f, -11.933819f, -37.70006f),-1f);

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
    private ForthBackMusic music=new ForthBackMusic();
    private BoundingVolume maj;
    private FifthOtherSpecial effect=new FifthOtherSpecial();

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
        states.add(light);
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
        state.attach(c1);
        states.add(c1);
        state.attach(c2);
        states.add(c2);
        state.attach(music);
        states.add(music);
        state.attach(effect);
        states.add(effect);
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
        BattleBackGroundState.setBackgroundSrc("Map/fourth.j3o");
    }

    public CollisionResults results1() {
        try {
            maj = major.getMajor();
            BoundingVolume ske = man1.get();
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
            BoundingVolume ske = man2.get();
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
            BoundingVolume ske = man3.get();
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
            BoundingVolume ske = bat1.get();
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
            BoundingVolume ske = bat2.get();
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
            BoundingVolume ske = bat3.get();
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
            BoundingVolume ske = ske1.get();
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
            BoundingVolume ske = ske2.get();
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
            BoundingVolume ske = bos.get();
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
            BoundingVolume ske = knight.get();
            CollisionResults result = new CollisionResults();
            maj.collideWith(ske, result);
            return result;
        } catch (Exception e) {
            return null;
        }
    }
    public CollisionResults results11(){
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
    public CollisionResults results12(){
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
        public void onAction(String name,boolean isPressed,float tpf){
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
            if (change.equals(name) && isPressed) {
                System.out.println("change");
                major.change();
            }
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
                } else if(results11!=null&&results11.size()>0){
                    System.out.println("chest");
                    c1.open();
                }else if(results12!=null&&results12.size()>0){
                    System.out.println("chest");
                    c2.open();
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
            switch (battle1) {
                case 0:
                    state.detach(input);
                    states.remove(input);
                    inputManager.deleteTrigger(talk, TALK);
                    inputManager.deleteTrigger(change, CHANGECAMERA);
                    inputManager.deleteTrigger(bag, BAG);
                    inputManager.deleteTrigger(move, MOVE);
                    EnemyState.getInstance().addEnemies(
                            new DarkDragon(80, "Enemies/forth/scene.j3o", 0, 0, 0, 0, 0, 0, 0, 0),
                            new DarkDragon(80, "Enemies/forth/scene.j3o", 5, 0, 0, 0, 0, 0, 0, 0)
                    );
                   // cam.setLocation(new Vector3f(0, 0, 10.3f));
                  //  cam.lookAtDirection(new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
                    state.detach(man1);
                    states.remove(man1);
                    state.attach(new Battle(states));
                  //  major.setPlace(man1.get().getCenter());

                    break;
                case 1:
                    state.detach(input);
                    states.remove(input);
                    inputManager.deleteTrigger(talk, TALK);
                    inputManager.deleteTrigger(change, CHANGECAMERA);
                    inputManager.deleteTrigger(bag, BAG);
                    inputManager.deleteTrigger(move, MOVE);
                    EnemyState.getInstance().addEnemies(
                            new DarkDragon(80, "Enemies/forth/scene.j3o", 0, 0, 0, 0, 0, 0, 0, 0),
                            new DarkDragon(80, "Enemies/forth/scene.j3o", 0, 0, 0, 0, 0, 0, 0, 0)
                    );
                   // cam.setLocation(new Vector3f(0, 0, 10.3f));
                   // cam.lookAtDirection(new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
                    state.detach(man2);
                    states.remove(man2);
                    state.attach(new Battle(states));
                   // major.setPlace(man2.get().getCenter());
                    break;
                case 2:
                    state.detach(input);
                    states.remove(input);
                    inputManager.deleteTrigger(talk, TALK);
                    inputManager.deleteTrigger(change, CHANGECAMERA);
                    inputManager.deleteTrigger(bag, BAG);
                    inputManager.deleteTrigger(move, MOVE);
                    EnemyState.getInstance().addEnemies(
                            new DarkDragon(80, "Enemies/forth/scene.j3o", 0, 0, 0, 0, 0, 0, 0, 0),
                            new DarkDragon(80, "Enemies/forth/scene.j3o", 0, 0, 0, 0, 0, 0, 0, 0)
                    );
                   // cam.setLocation(new Vector3f(0, 0, 10.3f));
                   // cam.lookAtDirection(new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
                    state.detach(man3);
                    states.remove(man3);
                    state.attach(new Battle(states));
                   // major.setPlace(man3.get().getCenter());
                    break;
                case 3:
                    state.detach(input);
                    states.remove(input);
                    inputManager.deleteTrigger(talk, TALK);
                    inputManager.deleteTrigger(change, CHANGECAMERA);
                    inputManager.deleteTrigger(bag, BAG);
                    inputManager.deleteTrigger(move, MOVE);
                    EnemyState.getInstance().addEnemies(
                            new RedSlime(65, "Enemies/bat/scene.j3o", 5, 0, 0, 0, 0, 0, 0, 0),
                            new RedSlime(65, "Enemies/bat/scene.j3o", 5, 0, 0, 0, 0, 0, 0, 0)
                    );
                    //cam.setLocation(new Vector3f(0, 0, 10.3f));
                   // cam.lookAtDirection(new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
                    state.detach(bat1);
                    states.remove(bat1);
                    state.attach(new Battle(states));
                    //major.setPlace(bat1.get().getCenter());
                    break;

                case 4:
                    state.detach(input);
                    states.remove(input);
                    inputManager.deleteTrigger(talk, TALK);
                    inputManager.deleteTrigger(change, CHANGECAMERA);
                    inputManager.deleteTrigger(bag, BAG);
                    inputManager.deleteTrigger(move, MOVE);
                    EnemyState.getInstance().addEnemies(
                            new BlackSlime(50, "Enemies/bat/scene.j3o", 0, 1, 0, 0, 0, 0, 0, 0),
                            new BlackSlime(50, "Enemies/bat/scene.j3o", 0, 3, 0, 0, 0, 0, 0, 0)
                    );
                   // cam.setLocation(new Vector3f(0, 0, 10.3f));
                   // cam.lookAtDirection(new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
                    state.detach(bat2);
                    states.remove(bat2);
                    state.attach(new Battle(states));
                    //major.setPlace(bat2.get().getCenter());
                    break;

                case 5:
                    state.detach(input);
                    states.remove(input);
                    inputManager.deleteTrigger(talk, TALK);
                    inputManager.deleteTrigger(change, CHANGECAMERA);
                    inputManager.deleteTrigger(bag, BAG);
                    inputManager.deleteTrigger(move, MOVE);
                    EnemyState.getInstance().addEnemies(
                            new EliteSlime(90, "Enemies/bat/scene.j3o", 10, 1, 2, 0, 0, 0, 0, 0)
                    );
                    //cam.setLocation(new Vector3f(0, 0, 10.3f));
                    //cam.lookAtDirection(new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
                    state.detach(bat3);
                    states.remove(bat3);
                    state.attach(new Battle(states));
                    //major.setPlace(bat3.get().getCenter());
                    break;

                case 6:
                    state.detach(input);
                    states.remove(input);
                    inputManager.deleteTrigger(talk, TALK);
                    inputManager.deleteTrigger(change, CHANGECAMERA);
                    inputManager.deleteTrigger(bag, BAG);
                    inputManager.deleteTrigger(move, MOVE);
                    EnemyState.getInstance().addEnemies(
                            new KingSlime(100, "Enemies/skeleton/blueSkeleton/blueSkeleton.j3o", 0, 4, 1, 0, 0, 0, 0, 0)
                    );
                    //cam.setLocation(new Vector3f(0, 0, 10.3f));
                   // cam.lookAtDirection(new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
                    state.detach(ske1);
                    states.remove(ske1);
                    state.attach(new Battle(states));
                    //major.setPlace(ske1.get().getCenter());
                    break;


                case 7:
                    state.detach(input);
                    states.remove(input);
                    inputManager.deleteTrigger(talk, TALK);
                    inputManager.deleteTrigger(change, CHANGECAMERA);
                    inputManager.deleteTrigger(bag, BAG);
                    inputManager.deleteTrigger(move, MOVE);
                    EnemyState.getInstance().addEnemies(
                            new EliteSlime(80, "Enemies/skeleton/redSkeleton/redSkeleton.j3o", 5, 0, 3, 0, 0, 0, 0, 0),
                            new EliteSlime(80, "Enemies/skeleton/redSkeleton/redSkeleton.j3o", 5, 0, 2, 0, 0, 0, 0, 0)
                    );
                    //cam.setLocation(new Vector3f(0, 0, 10.3f));
                    //cam.lookAtDirection(new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
                    state.detach(ske2);
                    states.remove(ske2);
                    state.attach(new Battle(states));
                    //major.setPlace(ske2.get().getCenter());
                    break;
                case 8:
                    state.detach(input);
                    states.remove(input);
                    inputManager.deleteTrigger(talk, TALK);
                    inputManager.deleteTrigger(change, CHANGECAMERA);
                    inputManager.deleteTrigger(bag, BAG);
                    inputManager.deleteTrigger(move, MOVE);
                    EnemyState.getInstance().addEnemies(
                            new Zac(160, "Enemies/forth/boss/scene.j3o", 20, 0, 2, 0, 0, 0, 0, 0)
                    );
                    //cam.setLocation(new Vector3f(0, 0, 10.3f));
                    //cam.lookAtDirection(new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
                    state.detach(bos);
                    states.remove(bos);
                    state.attach(new Battle(states));
                    //major.setPlace(bos.get().getCenter());
                    break;
                case 9:
                    state.detach(input);
                    states.remove(input);
                    inputManager.deleteTrigger(talk, TALK);
                    inputManager.deleteTrigger(change, CHANGECAMERA);
                    inputManager.deleteTrigger(bag, BAG);
                    inputManager.deleteTrigger(move, MOVE);
                    EnemyState.getInstance().addEnemies(
                            new Ace(135, "Enemies/bossKnight/scene.j3o", 0, 5, 1, 0, 0, 0, 0, 0)
                    );
                    //cam.setLocation(new Vector3f(0, 0, 10.3f));
                    //cam.lookAtDirection(new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
                    state.detach(knight);
                    states.remove(knight);
                    state.attach(new Battle(states));
                    //major.setPlace(knight.get().getCenter());
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