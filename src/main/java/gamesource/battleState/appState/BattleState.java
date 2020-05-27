package gamesource.battleState.appState;

import com.jme3.app.Application;
import com.jme3.app.DebugKeysAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.state.BaseAppState;
import com.jme3.audio.AudioListenerState;
import com.jme3.bullet.BulletAppState;
import com.jme3.cursors.plugins.JmeCursor;
import com.jme3.input.KeyInput;
import com.jme3.input.RawInputListener;
import com.jme3.input.event.*;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import gamesource.State.CharacterState.MajorActor;
import gamesource.State.CharacterState.enemies.AngrySkeletonState;
import gamesource.State.CharacterState.enemies.GreenSkeletonState;
import gamesource.State.CharacterState.secondWorldCharacter.goblinGirlState;
import gamesource.State.CharacterState.secondWorldCharacter.shanmanState;
import gamesource.State.controlState.InputAppState;
import gamesource.State.mapState.SecondWorldMap;
import gamesource.battleState.character.Enemy;
import gamesource.battleState.character.MainRole;

import java.util.ArrayList;
// 战斗场景的加载
public class BattleState extends BaseAppState {

    MajorActor major = new MajorActor(new Vector3f(-0.5884632f, -25.645144f, 76.421844f));

    private AngrySkeletonState skeleton1 = new AngrySkeletonState(new Vector3f(3.0883105f, -31.735968f, 43.255566f), 0.5f);

    private shanmanState shanman = new shanmanState(new Vector3f(5.1453485f, -32.197643f, 58.86895f), -1.5f);

    private goblinGirlState girl = new goblinGirlState(new Vector3f(5.3336577f, -31.696009f, 55.903286f), -1.5f);

    private GreenSkeletonState skeleton2=new GreenSkeletonState(new Vector3f(8.143902f, -32.197643f, 44.735046f),-0.7f);

    private InputAppState input=new InputAppState();

    private SecondWorldMap world=new SecondWorldMap();

    BulletAppState bullet=new BulletAppState();

    private AppStateManager state;
    private SimpleApplication app;
    private Spatial pic;

    @Override
    protected void initialize(Application app) {
        this.app = (SimpleApplication)app;
        state=app.getStateManager();
        app.getStateManager().attach(new StatsAppState());
        app.getStateManager().attach(new AudioListenerState());
        app.getStateManager().attach(new DebugKeysAppState());
        app.getStateManager().attach(EnemyState.getInstance());
        app.getStateManager().attach(new HandCardsState());
        app.getStateManager().attach(new DecksState());
        app.getStateManager().attach(new LeadingActorState());
        app.getStateManager().attach(new BattleBackGroundState());
        app.getStateManager().attach(new LightState());
        //app.getStateManager().attach(new board2(EnemyState.getInstance().size()));        //效果不好暂时取消
        app.getStateManager().attach(new BattleEffect(pic));
//        app.getStateManager().attach(new FilterAppState());

        // 注册监听器
        app.getInputManager().addRawInputListener(inputListener);
    }

    public BattleState(Spatial pic){
        this.pic=pic;
    }

    private void changeCursor() {

        JmeCursor jmeCursor = (JmeCursor) app.getAssetManager().loadAsset("Util/cursor/common.cur");
        app.getInputManager().setMouseCursor(jmeCursor);
    }



// 增加作弊按钮
    private RawInputListener inputListener = new RawInputListener() {


        public void onMouseMotionEvent(MouseMotionEvent evt) {

        }

        public void beginInput() {
        }

        public void endInput() {
        }

        public void onJoyAxisEvent(JoyAxisEvent evt) {
        }

        public void onJoyButtonEvent(JoyButtonEvent evt) {
        }

        public void onMouseButtonEvent(MouseButtonEvent evt) {

        }

        public void onKeyEvent(KeyInputEvent evt) {
            if (evt.getKeyCode() == KeyInput.KEY_K && evt.isPressed()) {
                ArrayList<Enemy> enemies = EnemyState.getInstance().getEnemies();
                for (Enemy enemy : enemies) {
                    enemy.getDamage(100);
                }
                EnemyState.getInstance().updateHints(true);
            }
        }

        public void onTouchEvent(TouchEvent evt) {
        }
    };


    @Override
    protected void cleanup(Application app) {

        AppStateManager stateManager = app.getStateManager();
        stateManager.detach(stateManager.getState(StatsAppState.class));
        stateManager.detach(stateManager.getState(AudioListenerState.class));
        stateManager.detach(stateManager.getState(DebugKeysAppState.class));
        stateManager.detach(stateManager.getState(LeadingActorState.class));
        stateManager.detach(stateManager.getState(BattleBackGroundState.class));
        stateManager.detach(stateManager.getState(LightState.class));
        stateManager.detach(stateManager.getState(BattleEffect.class));
        MainRole.getInstance().endBattle();
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

        app.getInputManager().removeRawInputListener(inputListener);

    }
}
