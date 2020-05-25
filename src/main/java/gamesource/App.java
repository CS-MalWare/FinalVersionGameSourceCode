package gamesource;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.cursors.plugins.JmeCursor;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.RawInputListener;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.event.*;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.*;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;
import com.jme3.shadow.DirectionalLightShadowRenderer;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;
import com.jme3.water.WaterFilter;
import gamesource.State.CharacterState.MajorActor;
import gamesource.State.SpecialEffect.makeCross;
import gamesource.State.controlState.InputAppState;
import gamesource.State.worldState.*;
import gamesource.testState.First;
import gamesource.uiState.bagstate.BagAppState;
import gamesource.uiState.menustate.MenuAppState;
import gamesource.uiState.shopstate.ShopAppState;
import gamesource.util.Storage;

/**
 * Hello world!
 */
public class App extends SimpleApplication {

    public final static String world = "WORLD";
    public int guan=1;

    FirstState f1;
    SecondState f2;
    ThirdState f3;
    ForthState f4;
    FifthState f5;
    SixthState f6;
    First t1;
    private AppStateManager state;


    /*public App(){
        super(new StatsAppState(),
              new AudioListenerState(),
              new DebugKeysAppState()
              //new BulletAppState(),
              //new FirstWorldState()
        );


    }*/
    //@Override
    public void simpleInitApp() {
        BulletAppState bullet = new BulletAppState();
        stateManager.attach(bullet);
        bullet.getPhysicsSpace().setGravity(new Vector3f(0, -9.81f, 0));

        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        fpp.setNumSamples(16);
        viewPort.addProcessor(fpp);
        makeCross cross=new makeCross();
        stateManager.attach(new MajorActor());
        stateManager.attach(new InputAppState());
        stateManager.attach(new BagAppState());
        stateManager.attach(new ShopAppState());
        stateManager.attach(new MenuAppState());
        stateManager.attach(cross);
        f1 = new FirstState(4096,1);
        //stateManager.attach(f1);
        f2 = new SecondState();
        f3 = new ThirdState();
        f4 = new ForthState();
        f5 = new FifthState();
        f6=new SixthState();
        //t1=new First();
        switch(guan){
            case 1:
                stateManager.attach(f1);
                break;
            case 2:
                stateManager.attach(f2);
                break;
            case 3:
                stateManager.attach(f3);
                break;
            case 4:
                stateManager.attach(f4);
                break;
            case 5:
                stateManager.attach(f5);
                break;
            case 6:
                stateManager.attach(f6);
                break;
        }
        //stateManager.getState(SecondState.class).setEnabled(false);
        //f2.setEnabled(false);
        //stateManager.attach(f2);
        //initSecondWorld();

        inputManager.addMapping(world, new KeyTrigger(KeyInput.KEY_Z));
        inputManager.addListener(new StartTalk(), world);
        //flyCam.setMoveSpeed(70);
        // 改变鼠标图标
        changeCursor();

        // 注册监听器
        getInputManager().addRawInputListener(inputListener);

        Spatial pic=getPicture(guan);
        //guiNode.attachChild(pic);
        cross.setEnabled(false);
    }


    private void changeCursor() {

        JmeCursor jmeCursor = (JmeCursor) assetManager.loadAsset("Util/cursor/common.cur");
        getInputManager().setMouseCursor(jmeCursor);
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
                JmeCursor jmeCursor = (JmeCursor) assetManager.loadAsset("Util/cursor/click.cur");
                getInputManager().setMouseCursor(jmeCursor);
            }

            boolean isReleased = evt.isReleased();
            if (isReleased) {
                // 显示鼠标位置
                JmeCursor jmeCursor = (JmeCursor) assetManager.loadAsset("Util/cursor/common.cur");
                getInputManager().setMouseCursor(jmeCursor);
            }
        }

        public void onKeyEvent(KeyInputEvent evt) {

            int keyCode = evt.getKeyCode();
            boolean isPressed = evt.isPressed();
             if (keyCode == KeyInput.KEY_I && isPressed) {
                Storage.save();
            }
             else if (keyCode == KeyInput.KEY_U && isPressed) {
                 Storage.reset();
             }
        }

        public void onTouchEvent(TouchEvent evt) {
        }
    };

    public App(int guan){
        this.guan=guan;
    }

    private void initEffect(FilterPostProcessor fpp) {
        BloomFilter bloom = new BloomFilter();
        bloom.setBloomIntensity(0.2f);
        bloom.setBlurScale(0.2f);
        FogFilter fog = new FogFilter(ColorRGBA.White, 0.4f, 250f);
        Vector3f sunDir = new Vector3f(-1, -2, -3);
        LightScatteringFilter lightScattering = new LightScatteringFilter(sunDir.mult(-3000));
        CartoonEdgeFilter cartoonEdge = new CartoonEdgeFilter();
        cartoonEdge.setDepthSensitivity(0.4f);
        cartoonEdge.setEdgeIntensity(0.55f);
        cartoonEdge.setEdgeWidth(0.55f);
        cartoonEdge.setNormalThreshold(0.55f);
        DepthOfFieldFilter depthOfField = new DepthOfFieldFilter();
        depthOfField.setFocusDistance(0);
        depthOfField.setFocusRange(25);
        depthOfField.setBlurScale(1.2f);
        fpp.addFilter(cartoonEdge);
        fpp.addFilter(depthOfField);
        fpp.addFilter(fog);
        fpp.addFilter(bloom);
    }

    public void skyBox() {
            /*Texture west = assetManager.loadTexture("skyBox/left2.png");
            Texture east = assetManager.loadTexture("skyBox/left1.png");
            Texture north = assetManager.loadTexture("skyBox/right1.png");
            Texture south = assetManager.loadTexture("skyBox/right2.png");
            Texture up = assetManager.loadTexture("skyBox/up.png");
            Texture down = assetManager.loadTexture("skyBox/down.png");

            Spatial sky = SkyFactory.createSky(assetManager, west, east, north, south, up, down);
            rootNode.attachChild(sky);*/
        Texture west = assetManager.loadTexture("bluecloud_ft.jpg");
        Texture east = assetManager.loadTexture("bluecloud_bk.jpg");
        Texture north = assetManager.loadTexture("bluecloud_lf.jpg");
        Texture south = assetManager.loadTexture("bluecloud_rt.jpg");
        Texture up = assetManager.loadTexture("bluecloud_up.jpg");
        Texture down = assetManager.loadTexture("bluecloud_dn.jpg");

        Spatial sky = SkyFactory.createSky(assetManager, west, east, north, south, up, down);
        rootNode.attachChild(sky);

    }

    public void skyBox2() {
            /*Texture west = assetManager.loadTexture("skyBox/left2.png");
            Texture east = assetManager.loadTexture("skyBox/left1.png");
            Texture north = assetManager.loadTexture("skyBox/right1.png");
            Texture south = assetManager.loadTexture("skyBox/right2.png");
            Texture up = assetManager.loadTexture("skyBox/up.png");
            Texture down = assetManager.loadTexture("skyBox/down.png");

            Spatial sky = SkyFactory.createSky(assetManager, west, east, north, south, up, down);
            rootNode.attachChild(sky);*/
        Texture west = assetManager.loadTexture("skyBox/back.jpg");
        Texture east = assetManager.loadTexture("skyBox/front.jpg");
        Texture north = assetManager.loadTexture("skyBox/lf.jpg");
        Texture south = assetManager.loadTexture("skyBox/rg.jpg");
        Texture up = assetManager.loadTexture("skyBox/up.jpg");
        Texture down = assetManager.loadTexture("skyBox/down.jpg");

        Spatial sky = SkyFactory.createSky(assetManager, west, east, north, south, up, down);
        rootNode.attachChild(sky);

    }

    class StartTalk implements ActionListener {
        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (world.equals(name) && isPressed) {
                switch(guan){
                    case 1:
                        stateManager.detach(f1);
                        stateManager.attach(f2);
                        guan=2;
                        break;
                    case 2:
                        stateManager.detach(f2);
                        stateManager.attach(f3);
                        guan=3;
                        break;
                    case 3:
                        stateManager.detach(f3);
                        stateManager.attach(f4);
                        guan=4;
                        break;
                    case 4:
                        stateManager.detach(f4);
                        stateManager.attach(f5);
                        guan=5;
                        break;
                    case 5:
                        stateManager.detach(f5);
                        stateManager.attach(f6);
                        guan=6;
                        break;
                    case 6:
                        stateManager.detach(f6);
                        stateManager.attach(f1);
                        guan=1;
                        break;
                }
                //f1.setEnabled(false);
                //f2.setEnabled(true);
                //System.out.println("zzzzzz");

                //x1.setEnabled(false);
            }
        }
    }

    private Spatial getPicture(int number) {
        // 创建一个四边形
        int x=cam.getWidth();
        int y=cam.getHeight();
        Quad quad = new Quad(x, y);
        Geometry geom = new Geometry("Picture", quad);
        Texture tex;
        // 加载图片
        switch(number){
            case 1:
                 tex = assetManager.loadTexture("Map/first.png");
                break;
            case 2:
                tex = assetManager.loadTexture("Map/second.png");
                break;
            case 3:
                tex = assetManager.loadTexture("Map/third.png");
                break;
            case 4:
                tex = assetManager.loadTexture("Map/forth.png");
                break;
            case 5:
                tex = assetManager.loadTexture("Map/fifth.png");
                break;
            case 6:
                tex = assetManager.loadTexture("Map/sixth.png");
                break;
            default:
                tex = assetManager.loadTexture("Map/first.png");
        }

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setTexture("ColorMap", tex);

        // 应用这个材质
        geom.setMaterial(mat);

        return geom;
    }


    public static void main(String[] args) {

        App app = new App(3);
        //app.setSettings(settings);
        //app.setShowSettings(false);
//        Storage.load();
        app.start();

    }
}
