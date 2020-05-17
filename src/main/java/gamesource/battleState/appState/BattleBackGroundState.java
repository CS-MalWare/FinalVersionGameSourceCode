package gamesource.battleState.appState;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.ColorOverlayFilter;
import com.jme3.post.filters.DepthOfFieldFilter;
import com.jme3.post.filters.FogFilter;
import com.jme3.post.ssao.SSAOFilter;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import gamesource.battleState.particle.KingSkeletonStateParticle;
import gamesource.battleState.particle.StuxnetStateParticle;

public class BattleBackGroundState extends BaseAppState {
    private SimpleApplication app;
    private Node rootNode = new Node("Map");

    private static String backgroundSrc="Map/scene.j3o";

    private FilterPostProcessor fpp;

    protected void initialize(Application application) {
        this.app = (SimpleApplication) getApplication();
        Spatial model1 = application.getAssetManager().loadModel(backgroundSrc);
        System.out.println(model1.getName());
        switch (model1.getName()){
            case "Map/first.j3o":
                model1.setName("Map");
                model1.scale(50f);// 按比例缩小
                model1.center();// 将模型的中心移到原点
                model1.move(2, (float) 5, -500);
                model1.rotate(0, 0f, 0);
                break;
            case "Map/second.j3o":
                model1.setName("Map");
                model1.scale(6f);// 按比例缩小
                model1.center();// 将模型的中心移到原点
                model1.move(2, (float) 4, -3);
                model1.rotate(0, 0f, 0);
                break;
            case "Map/scene.j3o":
                model1.setName("Map");
                model1.scale(-600000f);// 按比例缩小
                model1.center();// 将模型的中心移到原点
                model1.move(2, (float) 4, -30);
                model1.rotate(0, 0f, 0);
                break;
            case "Map/fourth.j3o":
                break;
            case "Map/fifth.j3o":
                break;
        }


//        model1.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);

      /*  DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(2, 1, -3));

        // 环境光
        AmbientLight ambient = new AmbientLight();

        // 调整光照亮度
        ColorRGBA lightColor = new ColorRGBA();
        sun.setColor(lightColor.mult(0.8f));
        ambient.setColor(lightColor.mult(1));
        // #3 将模型和光源添加到场景图中
        rootNode.addLight(sun);
        rootNode.addLight(ambient);*/
        fpp = new FilterPostProcessor(app.getAssetManager());
        initFpp();

        rootNode.attachChild(model1);
    }

    public void initFpp(){
        // 初始化滤镜处理器
        switch (backgroundSrc){
            case "Map/second.j3o":
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
                break;

            default: break;
        }

    }


    public FilterPostProcessor getFpp() {
        return fpp;
    }

    // 修改背景模型的地址
    public static void setBackgroundSrc(String src){
        backgroundSrc=src;
    }

    protected void cleanup(Application application) {

    }

    @Override
    protected void onEnable() {
        app.getRootNode().attachChild(this.rootNode);
    }

    @Override
    protected void onDisable() {
        this.rootNode.removeFromParent();
    }
}