package gamesource;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.cursors.plugins.JmeCursor;
import com.jme3.input.KeyInput;
import com.jme3.input.RawInputListener;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.event.*;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.*;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;
import gamesource.State.CharacterState.MajorActor;
import gamesource.State.SpecialEffect.makeCross;
import gamesource.State.controlState.InputAppState;
import gamesource.State.worldState.*;
import gamesource.battleState.character.MainRole;
import gamesource.testState.First;
import gamesource.uiState.bagstate.BagAppState;
import gamesource.uiState.menustate.MenuAppState;
import gamesource.uiState.shopstate.ShopAppState;
import gamesource.util.Storage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;


/**
 * Hello world!
 */
public class App extends SimpleApplication {

    public final static String world = "WORLD";
        public final static String world2="WORLD2";
    public static int guan = 2;

    FirstState f1;
    SecondState f2;
    ThirdState f3;
    ForthState f4;
    FifthState f5;
    SixthState f6;
    SeventhState f7;
    First t1;
    private AppStateManager state;
    private int shadow = 2048, open = 1, juchi = 4;
    private StartTalk st=new StartTalk();
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
        fpp.setNumSamples(juchi);
        viewPort.addProcessor(fpp);
        stateManager.attach(new BagAppState());
        stateManager.attach(new ShopAppState());
        stateManager.attach(new MenuAppState());
        makeCross cross = new makeCross();
        stateManager.attach(cross);
        stateManager.attach(new MajorActor());
        stateManager.attach(new InputAppState());
        //stateManager.attach(f1);
        //t1=new First();
        switch (guan) {
            case 1:
                f1 = new FirstState(shadow, open);
                stateManager.attach(f1);
                break;
            case 2:
                f2 = new SecondState(shadow, open);
                stateManager.attach(f2);
                break;
            case 3:
                f3 = new ThirdState(shadow, open);
                stateManager.attach(f3);
                break;
            case 4:
                f4 = new ForthState(shadow, open);
                stateManager.attach(f4);
                break;
            case 5:
                f5 = new FifthState(shadow, open);
                stateManager.attach(f5);
                break;
            case 6:
                f6 = new SixthState(shadow, open);
                stateManager.attach(f6);
                break;
            case 7:
                f7=new SeventhState(shadow,open);
                stateManager.attach(f7);
        }
        //stateManager.getState(SecondState.class).setEnabled(false);
        //f2.setEnabled(false);
        //stateManager.attach(f2);
        //initSecondWorld();

        inputManager.addMapping(world, new KeyTrigger(KeyInput.KEY_Z));
        inputManager.addListener(new StartTalk(), world);
        inputManager.addMapping(world2, new KeyTrigger(KeyInput.KEY_X));
        inputManager.addListener(new StartTalk(), world2);

        //flyCam.setMoveSpeed(70);
        // ??????????????????
        changeCursor();

        // ???????????????
        getInputManager().addRawInputListener(inputListener);

        //guiNode.attachChild(pic);
        cross.setEnabled(false);
        guiNode.detachAllChildren();
    }


    private void changeCursor() {

        JmeCursor jmeCursor = (JmeCursor) assetManager.loadAsset("Util/cursor/common.cur");
        getInputManager().setMouseCursor(jmeCursor);
    }


    private RawInputListener inputListener = new RawInputListener() {


//        BitmapText msg = new BitmapText(getAssetManager().loadFont("Interface/Fonts/Default.fnt"), false);

        public void onMouseMotionEvent(MouseMotionEvent evt) {
//            msg.removeFromParent();
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
                // ??????????????????
                JmeCursor jmeCursor = (JmeCursor) assetManager.loadAsset("Util/cursor/click.cur");
                getInputManager().setMouseCursor(jmeCursor);
            }

            boolean isReleased = evt.isReleased();
            if (isReleased) {
                // ??????????????????
                JmeCursor jmeCursor = (JmeCursor) assetManager.loadAsset("Util/cursor/common.cur");
                getInputManager().setMouseCursor(jmeCursor);
            }
        }

        public void onKeyEvent(KeyInputEvent evt) {

            int keyCode = evt.getKeyCode();
            boolean isPressed = evt.isPressed();
            if (keyCode == KeyInput.KEY_I && isPressed) {
                Storage.save();
//                msg.setText("Saving success!");
//                msg.setSize(0.2f);
//                msg.setBox(new Rectangle(-3.5f, 2f, 10, 2));
//                msg.setQueueBucket(RenderQueue.Bucket.Transparent);
//                rootNode.attachChild(msg);
            } else if (keyCode == KeyInput.KEY_U && isPressed) {
                Storage.reset();
            }
        }

        public void onTouchEvent(TouchEvent evt) {
        }
    };

    public App(int guan, int shadow, int open, int juchi) {
        this.guan = guan;
        this.shadow = shadow;
        this.open = open;
        this.juchi = juchi;
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
                switch (guan) {
                    case 1:
                        if (SecondState.canGo.equals("can")) {
                            stateManager.detach(f1);
                            f2=new SecondState(shadow, open);
                            stateManager.attach(f2);
                            guan = 2;
                        }
                        break;
                    case 2:
                        if (ThirdState.canGo.equals("can")) {
                            stateManager.detach(f2);
                            f3=new ThirdState(shadow, open);
                            stateManager.attach(f3);
                            guan = 3;
                        }
                        break;
                    case 3:
                        if (ForthState.canGo.equals("can")) {
                            stateManager.detach(f3);
                            f4=new ForthState(shadow, open);
                            stateManager.attach(f4);
                            guan = 4;
                        }
                        break;
                    case 4:
                        if (FifthState.canGo.equals("can")) {
                            stateManager.detach(f4);
                            f5=new FifthState(shadow, open);
                            stateManager.attach(f5);
                            guan = 5;
                        }
                        break;
                    case 5:
                        if (SixthState.canGo.equals("can")) {
                            stateManager.detach(f5);
                            f6=new SixthState(shadow, open);
                            stateManager.attach(f6);
                            guan = 6;
                        }
                        break;
                    case 6:
                        if (SeventhState.canGo.equals("can")) {
                            stateManager.detach(f6);
                            f7=new SeventhState(shadow, open);
                            stateManager.attach(f7);
                            guan = 7;
                        }
                        break;
                    case 7:
                        if (FirstState.canGo.equals("can")) {
                            stateManager.detach(f7);
                            f1=new FirstState(shadow, open);
                            stateManager.attach(f1);
                            guan = 1;
                        }
                        break;
                }
                //f1.setEnabled(false);
                //f2.setEnabled(true);
                //System.out.println("zzzzzz");

                //x1.setEnabled(false);
            }else if (world2.equals(name) && isPressed) {
                switch (guan) {
                    case 1:
                        if (SeventhState.canGo.equals("can")) {
                            stateManager.detach(f1);
                            f7=new SeventhState(shadow, open);
                            stateManager.attach(f7);
                            guan = 7;
                        }
                        break;
                    case 2:
                        if (FirstState.canGo.equals("can")) {
                            stateManager.detach(f2);
                            f1=new FirstState(shadow, open);
                            stateManager.attach(f1);
                            guan = 1;
                        }
                        break;
                    case 3:
                        if (SecondState.canGo.equals("can")) {
                            stateManager.detach(f3);
                            f2=new SecondState(shadow, open);
                            stateManager.attach(f2);
                            guan = 2;
                        }
                        break;
                    case 4:
                        if (ThirdState.canGo.equals("can")) {
                            stateManager.detach(f4);
                            f3=new ThirdState(shadow, open);
                            stateManager.attach(f3);
                            guan = 3;
                        }
                        break;
                    case 5:
                        if (ForthState.canGo.equals("can")) {
                            stateManager.detach(f5);
                            f4=new ForthState(shadow, open);
                            stateManager.attach(f4);
                            guan = 4;
                        }
                        break;
                    case 6:
                        if (FifthState.canGo.equals("can")) {
                            stateManager.detach(f6);
                            f5=new FifthState(shadow, open);
                            stateManager.attach(f5);
                            guan = 5;
                        }
                        break;
                    case 7:
                        if(SixthState.canGo.equals("can")){
                            stateManager.detach(f7);
                            f6 = new SixthState(shadow, open);
                            stateManager.attach(f6);
                            guan = 6;
                        }
                }
            }
        }
    }

    private Spatial getPicture(int number) {
        // ?????????????????????
        int x = cam.getWidth();
        int y = cam.getHeight();
        Quad quad = new Quad(x, y);
        Geometry geom = new Geometry("Picture", quad);
        Texture tex;
        // ????????????
        switch (number) {
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

        // ??????????????????
        geom.setMaterial(mat);

        return geom;
    }


    public static void main(String[] args) throws IOException {
        MainRole.getInstance();
        App app = new App(2, 4096, 0, 1);
        AppSettings settings = new AppSettings(true);

        // ??????????????????????????????,??????????????????????????? MainRole??????
//        Storage.load();

        settings.setTitle("Cholera");// ??????
        settings.setResolution(1600, 900);// ?????????
        settings.setFrameRate(100);//??????fps
        settings.setIcons(new BufferedImage[]{
                ImageIO.read(App.class.getResource( "/Map/icon.png" ))});
        settings.setSamples(1);
        app.setSettings(settings);
        //app.setShowSettings(true);
//        settings.setFullscreen(true);
        //app.setShowSettings(false);
        //Storage.load();
        app.start();

    }
}
