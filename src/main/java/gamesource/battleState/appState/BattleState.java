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
import com.jme3.input.RawInputListener;
import com.jme3.input.event.*;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.ColorOverlayFilter;
import com.jme3.post.filters.DepthOfFieldFilter;
import com.jme3.post.filters.FogFilter;
import com.jme3.post.ssao.SSAOFilter;
import gamesource.State.CharacterState.MajorActor;
import gamesource.State.CharacterState.enemies.angryskeletonState;
import gamesource.State.CharacterState.enemies.greenSkeletonState;
import gamesource.State.CharacterState.secondWorldCharacter.goblinGirlState;
import gamesource.State.CharacterState.secondWorldCharacter.shanmanState;
import gamesource.State.controlState.InputAppState;
import gamesource.State.mapState.secondWorldMap;

public class BattleState extends BaseAppState {

    MajorActor major=new MajorActor(new Vector3f(-0.5884632f, -25.645144f, 76.421844f));

    private angryskeletonState skeleton1=new angryskeletonState(new Vector3f(3.0883105f, -31.735968f, 43.255566f),0.5f);

    private shanmanState shanman=new shanmanState(new Vector3f(5.1453485f, -32.197643f, 58.86895f),-1.5f);

    private goblinGirlState girl =new goblinGirlState(new Vector3f(5.3336577f, -31.696009f, 55.903286f),-1.5f);

    private greenSkeletonState skeleton2=new greenSkeletonState(new Vector3f(8.143902f, -32.197643f, 44.735046f),-0.7f);

    private InputAppState input=new InputAppState();

    private secondWorldMap world=new secondWorldMap();

    BulletAppState bullet=new BulletAppState();

    private AppStateManager state;
    private SimpleApplication app;
    FilterPostProcessor fpp;

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
//        app.getStateManager().attach(new FilterAppState());


        // 初始化滤镜处理器
        fpp = new FilterPostProcessor(app.getAssetManager());
        app.getViewPort().addProcessor(fpp);

        // 添加雾化滤镜
        FogFilter fogFilter = new FogFilter(ColorRGBA.Red, 0.5f, 500f);
        fpp.addFilter(fogFilter);


        // 纯色叠加
        ColorOverlayFilter colorOverlay = new ColorOverlayFilter(new ColorRGBA(1f, 0.8f, 0.8f, 0.4f));
        fpp.addFilter(colorOverlay);

        // 屏幕空间环境光遮蔽
        SSAOFilter ssao = new SSAOFilter(10f, 25f, 0.6f, 0.6f);
        fpp.addFilter(ssao);

        // 景深
        DepthOfFieldFilter depthOfField = new DepthOfFieldFilter();
        depthOfField.setFocusDistance(0);
        depthOfField.setFocusRange(20);
        depthOfField.setBlurScale(1.4f);

        fpp.addFilter(depthOfField);
        // 改变鼠标图标
        changeCursor();

        // 注册监听器
        app.getInputManager().addRawInputListener(inputListener);
    }


    private void changeCursor() {

        JmeCursor jmeCursor = (JmeCursor) app.getAssetManager().loadAsset("Util/cursor/common.cur");
        app.getInputManager().setMouseCursor(jmeCursor);
    }




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
            boolean isPressed = evt.isPressed();

            if (isPressed) {
                // 显示鼠标位置
                JmeCursor jmeCursor = (JmeCursor) app.getAssetManager().loadAsset("Util/cursor/click.cur");
                app.getInputManager().setMouseCursor(jmeCursor);
            }

            boolean isReleased = evt.isReleased();
            if (isReleased) {
                // 显示鼠标位置
                JmeCursor jmeCursor = (JmeCursor) app.getAssetManager().loadAsset("Util/cursor/common.cur");
                app.getInputManager().setMouseCursor(jmeCursor);
            }
        }

        public void onKeyEvent(KeyInputEvent evt) {
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
    }

    @Override
    protected void onEnable() {

    }

    public FilterPostProcessor getFpp(){
        return fpp;
    }

    @Override
    protected void onDisable() {

        app.getInputManager().removeRawInputListener(inputListener);

    }
}
