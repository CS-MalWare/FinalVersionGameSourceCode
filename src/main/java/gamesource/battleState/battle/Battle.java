package gamesource.battleState.battle;

import com.jme3.app.Application;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.scene.Node;
import com.jme3.shadow.DirectionalLightShadowFilter;
import gamesource.State.CharacterState.MajorActor;
import gamesource.State.controlState.InputAppState;
import gamesource.battleState.appState.BattleState;
import gamesource.battleState.appState.EnemyState;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.enemy.dragonWat.DarkDragon;
import gamesource.battleState.equipment.CreateEquipment;
import gamesource.battleState.equipment.common.KnightGloves;
import gamesource.battleState.equipment.epic.ArmstrongGun;
import gamesource.battleState.equipment.epic.LampOfAladdin;
import gamesource.battleState.equipment.legendary.BalancedLibra;
import gamesource.battleState.equipment.legendary.MerlinGown;
import gamesource.battleState.equipment.legendary.MerlinWand;
import gamesource.uiState.bagstate.BagAppState;
import gamesource.uiState.menustate.MenuAppState;
import gamesource.uiState.shopstate.ShopAppState;

import java.util.ArrayList;

public class Battle extends BaseAppState {

    private BattleState b1=new BattleState();

    private SimpleApplication app;

    private AppStateManager state;

    private InputAppState input=new InputAppState();

    // 屏幕分辨率

    private ArrayList<BaseAppState> State=null;
    DirectionalLight sun;
    AmbientLight ambient;

    public final static String talk="TALK";
    public final static Trigger TALK = new KeyTrigger(KeyInput.KEY_N);
    public final static String change="CHANGE";
    public final static Trigger CHANGECAMERA = new KeyTrigger(KeyInput.KEY_C);
    public final static String bag="BAG";
    public final static Trigger BAG = new KeyTrigger(KeyInput.KEY_B);
    public final static String move="MOVE";
    public final static Trigger MOVE=new KeyTrigger(KeyInput.KEY_W);

    private InputManager inputManager;

    public Battle(){}

    public Battle(ArrayList<BaseAppState> State){
        this.State=State;
    }

    @Override
    protected void initialize(Application application) {

        state=application.getStateManager();
        state.attach(b1);
        state.detach(state.getState(FlyCamAppState.class));
        app=(SimpleApplication)application;
        inputManager=app.getInputManager();

        for (BaseAppState baseAppState : State) {
            if (!(baseAppState instanceof MenuAppState) && !(baseAppState instanceof BagAppState) && !(baseAppState instanceof ShopAppState)) {
                baseAppState.setEnabled(false);
                //state.detach(baseAppState);
            }

        }




        MainRole mainRole = MainRole.getInstance();
        mainRole.bindApp(app);
        mainRole.getEquipment(new BalancedLibra());
        mainRole.getEquipment(new MerlinGown());
        mainRole.getEquipment(new MerlinWand());
        mainRole.getEquipment(new ArmstrongGun());
        mainRole.getEquipment(new LampOfAladdin());
        mainRole.getEquipment(new KnightGloves());
        mainRole.getEquipment(CreateEquipment.getRandomEquipment());
        EnemyState enemyState = EnemyState.getInstance();

        enemyState.addEnemies(
//                new DarkDragon(1, "Enemies/zhenwang/boss.j3o", 0, 0, 0, 0, 0, 0, 0, 0)

//        new DarkDragon(1, "Enemies/skeleton/skeleton.j3o", 0, 0, 0, 0, 0, 0, 0, 0),
//        new DarkDragon(1, "Enemies/snowRobot/snowRobot.j3o", 0, 0, 0, 0, 0, 0, 0, 0),
//                new DarkDragon(1, "Enemies/bossKnight/scene.j3o", 0, 0, 0, 0, 0, 0, 0, 0),
//        new DarkDragon(1, "Enemies/snowRobot/snowRobot.j3o", 0, 0, 0, 0, 0, 0, 0, 0)
//                new DarkDragon(100, "Enemies/skeleton/KingSkeleton/kingSkeleton.j3o", 0, 0, 0, 0, 0, 0, 0, 0)
                new DarkDragon(20, "Enemies/bat/scene.j3o", 0, 0, 0, 0, 0, 0, 0, 0)

        );
        //addLight();

    }




    public void addLight() {
        // 定向光
        sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-1, -2, -3));

        //viewPort.setBackgroundColor(ColorRGBA.LightGray);
        // 环境光
        ambient = new AmbientLight();

        // 调整光照亮度
        ColorRGBA lightColor = new ColorRGBA();
        sun.setColor(lightColor.mult(2f));
        ambient.setColor(lightColor.mult(2f));

        app.getRootNode().addLight(sun);
        app.getRootNode().addLight(ambient);

        DirectionalLightShadowFilter su = new DirectionalLightShadowFilter(app.getAssetManager(), 1024, 3);
        su.setLight(sun);
        su.setEnabled(true);
        FilterPostProcessor fpp = new FilterPostProcessor(app.getAssetManager());
        fpp.addFilter(su);
        app.getViewPort().addProcessor(fpp);
    }


    @Override
    protected void cleanup(Application application) {
    }

    @Override
    protected void onEnable() {

    }


    @Override
    protected void onDisable() {
        //app.getRootNode().removeLight(sun);
        //app.getRootNode().removeLight(ambient);
        for (BaseAppState baseAppState : State) {
            if (!(baseAppState instanceof MenuAppState) && !(baseAppState instanceof BagAppState) && !(baseAppState instanceof ShopAppState)) {
                baseAppState.setEnabled(true);
                //state.attach(baseAppState);
            }
            if((baseAppState instanceof MajorActor)){
                ((MajorActor) baseAppState).mouseChange();
                ((MajorActor) baseAppState).mouseChange();
                ((MajorActor) baseAppState).idle();
            }
        }
        System.out.println("Battle removed");
        state.attach(input);
        //input.idle();
        state.attach(new FlyCamAppState());
        inputManager.addMapping(talk,TALK);
        inputManager.addMapping(change,CHANGECAMERA);
        inputManager.addMapping(bag,BAG);
        inputManager.addMapping(move,MOVE);
    }
}