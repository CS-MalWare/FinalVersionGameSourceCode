package gamesource.battleState.appState;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;
import com.jme3.shadow.DirectionalLightShadowFilter;

public class animationTry extends SimpleApplication {
    private final static String WALK = "walk";

    private boolean isWalking = false;

    private Spatial spatial;

    private AnimControl animControl;
    private AnimChannel animChannel;

    public static void main(String[] args) {
        animationTry app = new animationTry();
        app.start();
    }

    public void simpleInitApp() {
        initCamera();

        // 初始化灯光
        initLight();

        // 初始化场景
        initScene();


    }

    private void initCamera() {
        //flyCam.setEnabled(false);

        cam.setLocation(new Vector3f(1, 2, 3));
        cam.lookAt(new Vector3f(0, 0.5f, 0), new Vector3f(0, 1, 0));
    }

    private void initLight() {
        DirectionalLight sunLight = new DirectionalLight();
        sunLight.setDirection(new Vector3f(-1, -2, -3));
        sunLight.setColor(new ColorRGBA(0.8f, 0.8f, 0.8f, 1f));

        viewPort.setBackgroundColor(ColorRGBA.LightGray);
        // 环境光
        AmbientLight ambientLight = new AmbientLight();
        ambientLight.setColor(new ColorRGBA(0.2f, 0.2f, 0.2f, 1f));
        ColorRGBA lightColor = new ColorRGBA();
        sunLight.setColor(lightColor.mult(1.2f));
        ambientLight.setColor(lightColor.mult(1.2f));

        // 将光源添加到场景图中
        rootNode.addLight(sunLight);
        rootNode.addLight(ambientLight);

        DirectionalLightShadowFilter su = new DirectionalLightShadowFilter(assetManager, 1024, 3);
        su.setLight(sunLight);
        su.setEnabled(true);
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        fpp.addFilter(su);
        fpp.setNumSamples(16);
        viewPort.addProcessor(fpp);

    }

    private void initScene() {
        spatial = assetManager.loadModel("leader.j3o");
        //spatial=(Node)assetManager.loadModel("walk.j3o");

        spatial.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);

        Node scene = (Node) spatial;

        Node bip001 = (Node) scene.getChild("Bip001");

        //spatial.getChild("AnimControl");


        animControl = bip001.getControl(AnimControl.class);

        //AnimControl control = (AnimControl)spatial.getControl(0);

        System.out.println(animControl.getAnimationNames() + "zzzzzzz");

        animChannel = animControl.createChannel();

        spatial.move(0, 1, 0);


        animChannel.setAnim("walk");

        spatial.scale(0.01f);

        rootNode.attachChild(spatial);

        Geometry stage = new Geometry("Stage", new Quad(5, 5));
        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat.setColor("Diffuse", ColorRGBA.White);
        mat.setColor("Specular", ColorRGBA.White);
        mat.setColor("Ambient", ColorRGBA.Black);
        mat.setFloat("Shininess", 0);
        mat.setBoolean("UseMaterialColors", true);
        stage.setMaterial(mat);

        stage.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);

        stage.rotate(-FastMath.HALF_PI, 0, 0);
        stage.center();
        rootNode.attachChild(stage);
    }
}
