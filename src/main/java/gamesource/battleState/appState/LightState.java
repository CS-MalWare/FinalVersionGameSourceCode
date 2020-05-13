package gamesource.battleState.appState;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.SceneProcessor;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.shadow.DirectionalLightShadowFilter;
import com.jme3.util.SafeArrayList;

public class LightState extends BaseAppState {
    DirectionalLight sun = new DirectionalLight();
    AmbientLight ambient = new AmbientLight();
    private Node rootNode;
    private SimpleApplication app;
    private FilterPostProcessor fpp;
    DirectionalLightShadowFilter su;
    private AssetManager manager;
    private ViewPort view;
    SafeArrayList<SceneProcessor> x1  ;

    @Override
    protected void initialize(Application application) {
        app=(SimpleApplication)application;
        rootNode=app.getRootNode();
        manager=app.getAssetManager();
        view=app.getViewPort();
        sun.setDirection(new Vector3f(-1, -2, -3));


        ColorRGBA lightColor = new ColorRGBA();
        sun.setColor(lightColor.mult(2f));
        ambient.setColor(lightColor.mult(2f));

        rootNode.addLight(sun);
        rootNode.addLight(ambient);

        su = new DirectionalLightShadowFilter(manager, 1024, 1);

        su.setLight(sun);
        x1 = view.getProcessors();
        for (int i = 0; i < x1.size(); i++) {
            if (x1.get(i) instanceof FilterPostProcessor)
                fpp = (FilterPostProcessor) x1.get(i);
        }
        fpp.addFilter(su);
    }

    @Override
    protected void cleanup(Application application) {

    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {
        rootNode.removeLight(sun);
        rootNode.removeLight(ambient);
            fpp.removeFilter(su);
    }
}
