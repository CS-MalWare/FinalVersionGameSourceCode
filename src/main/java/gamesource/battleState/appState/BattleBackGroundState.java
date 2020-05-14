package gamesource.battleState.appState;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import gamesource.battleState.particle.KingSkeletonStateParticle;
import gamesource.battleState.particle.StuxnetStateParticle;

public class BattleBackGroundState extends BaseAppState {
    private SimpleApplication app;
    private Node rootNode = new Node("Map");

    private static String backgroundSrc="Map/second.j3o";

    protected void initialize(Application application) {
        this.app = (SimpleApplication) getApplication();
        Spatial model1 = application.getAssetManager().loadModel(backgroundSrc);
        System.out.println(model1.getName());
        model1.setName("Map");
        model1.scale(6f);// 按比例缩小
        model1.center();// 将模型的中心移到原点
        model1.move(2, (float) 4, -3);
        model1.rotate(0, 0f, 0);

        model1.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);

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
        rootNode.attachChild(model1);
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