package gamesource;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.*;
import com.jme3.scene.Spatial;
import com.jme3.shadow.DirectionalLightShadowRenderer;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;
import com.jme3.water.WaterFilter;
import gamesource.State.CharacterState.MajorActor;
import gamesource.State.SpecialEffect.makeCross;
import gamesource.State.controlState.InputAppState;
import gamesource.State.worldState.*;
import gamesource.uiState.bagstate.BagAppState;
import gamesource.uiState.menustate.MenuAppState;
import gamesource.uiState.shopstate.ShopAppState;

/**
 * Hello world!
 */
public class App extends SimpleApplication {

    public final static String world = "WORLD";

    FirstState f1;
    SecondState f2;
    ThirdState f3;
    ForthState f4;
    FifthState f5;
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
        stateManager.attach(new MajorActor());
        stateManager.attach(new InputAppState());
        stateManager.attach(new BagAppState());
        stateManager.attach(new ShopAppState());
        stateManager.attach(new MenuAppState());
        stateManager.attach(new makeCross());
        f1 = new FirstState();
        //stateManager.attach(f1);
        f2 = new SecondState();
        f3 = new ThirdState();
        f4 = new ForthState();
        f5 = new FifthState();
        stateManager.attach(f2);
        //stateManager.getState(SecondState.class).setEnabled(false);
        //f2.setEnabled(false);
        //stateManager.attach(f2);
        //initSecondWorld();

        inputManager.addMapping(world, new KeyTrigger(KeyInput.KEY_Z));
        inputManager.addListener(new StartTalk(), world);
    }

    public void initSecondWorld() {
        stateManager.attach(new SecondState());
        stateManager.attach(new BagAppState());
        stateManager.attach(new ShopAppState());
        stateManager.attach(new MenuAppState());
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        addLight(fpp);
        skyBox2();
        makeCross();
        initEffect(fpp);
    }

    public void initFirstWorld() {
        /*BulletAppState bullet=new BulletAppState();         //现在为为世界添加物理引擎的测试情况
        stateManager.attach(bullet);
        stateManager.attach(new FirstWorldState());
        //stateManager.attach(new AxisState());
        stateManager.attach(new InputAppState());
        stateManager.attach(new PositionInputState());
        stateManager.attach(new MajorActor());
        stateManager.attach(new soldierState(1,new Vector3f(75.06459f, -29.602854f, 3.6023405f)));
        stateManager.attach(new soldierState(2,new Vector3f(76.39369f, -25.91693f, -11.675956f),6f,2));
        stateManager.attach(new soldierState(3,new Vector3f(75.281975f, -27.164389f, -10.233985f),2.6f));
        stateManager.attach(new soldierState(4,new Vector3f(62.556515f, -26.660355f, -18.043798f),2.6f));
        stateManager.attach(new KnightState(1,new Vector3f(-46.05996f, 6.526695f, -26.613733f),1.4f,-1,0,1));
        stateManager.attach(new KnightState(1,new Vector3f(-46.07412f, 5.8200717f, -29.27783f),4.6f,1,0,-1,2));
        stateManager.attach(new girlState(new Vector3f(70.15531f, -27.27989f, -18.333033f),-0.3f));
        stateManager.attach(new metalKnightState(new Vector3f(64.4272f, -25.418774f, -20.45753f),-1.45f));
        stateManager.attach(new blackBoyState(new Vector3f(64.8886f, -26.233301f, -9.080749f),2.4f));
        stateManager.attach(new BagAppState());
        stateManager.attach(new shovelKnight(new Vector3f(-35.924995f, 7.3355093f, -13.190997f),4.4f));
        stateManager.attach(new shovelKnight(new Vector3f(-40.020714f, 5.764501f, -9.103482f),3.4f));
        stateManager.attach(new KingState(new Vector3f(-39.032665f, 4.674482f, -12.373539f),3.9f));
        bullet.getPhysicsSpace().setGravity(new Vector3f(0,-9.81f,0));
        //stateManager.attach(new QuadState());*/
        //bullet.setDebugEnabled(true);                     //这个功能为调试模式
        stateManager.attach(f1);

        //stateManager.attach(new FirstWorldLight(0));
        //addLight(fpp);
        flyCam.setMoveSpeed(30);
        //stateManager.attach(new FirstWorldOtherSpecial());
        //initCamera();
        //skyBox();
        //initWater(fpp);
        stateManager.attach(new makeCross());
        //makeCross();
        //initEffect(fpp);
    }


    private Spatial makeCross() {

        BitmapText text = guiFont.createLabel("+");
        text.setColor(ColorRGBA.Red);

        float x = (cam.getWidth() - text.getLineWidth()) * 0.5f;
        float y = (cam.getHeight() + text.getLineHeight()) * 0.5f;
        text.setLocalTranslation(x, y, 0);

        guiNode.attachChild(text);

        return text;
    }


    public void addLight(FilterPostProcessor fpp) {
        // 定向光
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-1, -2, -3));


        DirectionalLight sun1 = new DirectionalLight();
        //sun.setDirection(new Vector3f(0, 0, -1));
        DirectionalLight sun3 = new DirectionalLight();
        sun3.setDirection(new Vector3f(-1, 0, 0));
        DirectionalLight sun4 = new DirectionalLight();
        sun4.setDirection(new Vector3f(1, 0, 0));
        DirectionalLight sun6 = new DirectionalLight();
        sun6.setDirection(new Vector3f(0, 0, 1));


        sun.setColor(ColorRGBA.White.mult(1.07f));
        sun1.setColor(ColorRGBA.White.mult(1.07f));
        sun3.setColor(ColorRGBA.White.mult(0.4f));
        sun4.setColor(ColorRGBA.White.mult(0.4f));
        sun6.setColor(ColorRGBA.White.mult(0.4f));

        rootNode.addLight(sun);
        rootNode.addLight(sun1);
        rootNode.addLight(sun3);
        rootNode.addLight(sun4);
        rootNode.addLight(sun6);
        DirectionalLightShadowRenderer su = new DirectionalLightShadowRenderer(assetManager, 4096, 3);
        su.setLight(sun);
        viewPort.addProcessor(su);
        //su.setEnabled(true);
        //fpp.addFilter(su);

    }

 /*   private void initCamera(){
        flyCam.setEnabled(true);
        flyCam.setMoveSpeed(10f);

        cam.setLocation(new Vector3f(1,2,3));
        cam.lookAt(new Vector3f(0,0.5f,0),new Vector3f(0,1,0));
    }*/

    private void initWater(FilterPostProcessor fpp) {
        //FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        //viewPort.addProcessor(fpp);

        // 水
        WaterFilter waterFilter = new WaterFilter();
        //waterFilter.setCenter(new Vector3f(0, -5, 0));
        waterFilter.setRadius(350);// 水面半径
        waterFilter.setWaterHeight(-34.4f);// 水面高度
        waterFilter.setWaterTransparency(0.2f);// 透明度
        waterFilter.setShapeType(WaterFilter.AreaShape.Circular);
        waterFilter.setWaterColor(new ColorRGBA(0.4314f, 0.9373f, 0.8431f, 1f));// 水面颜色

        fpp.addFilter(waterFilter);
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
                stateManager.detach(f1);
                stateManager.attach(f2);
                //f1.setEnabled(false);
                //f2.setEnabled(true);
                //System.out.println("zzzzzz");

                //x1.setEnabled(false);
            }
        }
    }

    public static void main(String[] args) {

        App app = new App();
        //app.setSettings(settings);
        //app.setShowSettings(false);
        app.start();

    }
}
