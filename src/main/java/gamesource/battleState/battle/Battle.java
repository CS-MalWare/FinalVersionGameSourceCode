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
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;
import com.jme3.shadow.DirectionalLightShadowFilter;
import com.jme3.texture.Texture;
import gamesource.State.CharacterState.MajorActor;
import gamesource.State.controlState.InputAppState;
import gamesource.State.mapState.*;
import gamesource.battleState.appState.BattleState;
import gamesource.battleState.appState.EnemyState;
import gamesource.battleState.character.MainRole;
import gamesource.uiState.bagstate.BagAppState;
import gamesource.uiState.menustate.MenuAppState;
import gamesource.uiState.shopstate.ShopAppState;
import gamesource.util.Storage;

import java.util.ArrayList;


// 留给其他开发者的接口,调用即开始战斗
public class Battle extends BaseAppState {

    private BattleState b1;

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

    public final static String menu = "MENU";
    public final static Trigger MENU = new KeyTrigger(KeyInput.KEY_V);
    public final static String map = "MAP";
    public final static Trigger MAP = new KeyTrigger(KeyInput.KEY_M);
    private InputManager inputManager;

    public Battle(){}

    public Battle(ArrayList<BaseAppState> State){
        this.State=State;
    }

    @Override
    protected void initialize(Application application) {

        state=application.getStateManager();
        state.detach(state.getState(FlyCamAppState.class));
        app=(SimpleApplication)application;
        Spatial pic=getPicture(7);
        app.getGuiNode().attachChild(pic);
        inputManager=app.getInputManager();


        for (BaseAppState baseAppState : State) {
            if (!(baseAppState instanceof MenuAppState) && !(baseAppState instanceof BagAppState) && !(baseAppState instanceof ShopAppState)&&!(baseAppState instanceof SkyBox)&&!(baseAppState instanceof SkyBox2)&&!(baseAppState instanceof skyBox4)&&!(baseAppState instanceof skyBox5)&&!(baseAppState instanceof skyBox6)) {
                baseAppState.setEnabled(false);
                //state.detach(baseAppState);
            }
            //if((baseAppState instanceof MenuAppState) ||(baseAppState instanceof BagAppState) ||(baseAppState instanceof ShopAppState)){
           //     state.detach(baseAppState);
           // }

        }
        b1=new BattleState(pic);
        state.attach(b1);

        app.getCamera().setLocation(new Vector3f(0,0,10.5f));
        app.getCamera().lookAtDirection(new Vector3f(0,0,-1),new Vector3f(0,1,0));

        MainRole mainRole = MainRole.getInstance();
        mainRole.bindApp(app);
        Storage.loadCharacter();
        EnemyState enemyState = EnemyState.getInstance();


//        enemyState.addEnemies(
//                new DarkDragon(1, "Enemies/zhenwang/boss.j3o", 0, 0, 0, 0, 0, 0, 0, 0)

//        new DarkDragon(1, "Enemies/skeleton/skeleton.j3o", 0, 0, 0, 0, 0, 0, 0, 0),
//        new DarkDragon(1, "Enemies/snowRobot/snowRobot.j3o", 0, 0, 0, 0, 0, 0, 0, 0),
//                new DarkDragon(1, "Enemies/bossKnight/scene.j3o", 0, 0, 0, 0, 0, 0, 0, 0),
//                new DarkDragon(1, "Enemies/underWater/mushroom_bug.j3o", 0, 0, 0, 0, 0, 0, 0, 0),
//                new DarkDragon(20, "Enemies/underWater/mushroom_bug.j3o", 0, 0, 0, 0, 0, 0, 0, 0),
//                new DarkDragon(20, "Enemies/underWater/mushroom_bug.j3o", 0, 0, 0, 0, 0, 0, 0, 0)
//
//        );
        //addLight();
        //addLight();

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
            case 7:
                tex = app.getAssetManager().loadTexture("Map/fight.png");
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
            if (!(baseAppState instanceof MenuAppState) && !(baseAppState instanceof BagAppState) && !(baseAppState instanceof ShopAppState)&&!(baseAppState instanceof SkyBox)&&!(baseAppState instanceof SkyBox2)&&!(baseAppState instanceof skyBox4)&&!(baseAppState instanceof skyBox5)&&!(baseAppState instanceof skyBox6)) {
                baseAppState.setEnabled(true);
                //state.attach(baseAppState);
            }
           // if((baseAppState instanceof MenuAppState) ||(baseAppState instanceof BagAppState) ||(baseAppState instanceof ShopAppState)){
           //     state.detach(baseAppState);
            //}
             if((baseAppState instanceof BagAppState) ){
                 BagAppState bag= (BagAppState) baseAppState;
                 bag.exitFight();
            }
            if((baseAppState instanceof ShopAppState) ){
                ShopAppState shop= (ShopAppState) baseAppState;
                shop.exitFight();
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
        inputManager.addMapping(menu, MENU);


        inputManager.addMapping(map, MAP);
    }
}
