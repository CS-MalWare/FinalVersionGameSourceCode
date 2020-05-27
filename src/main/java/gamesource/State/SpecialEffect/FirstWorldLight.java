package gamesource.State.SpecialEffect;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.SceneProcessor;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.shadow.DirectionalLightShadowFilter;
import com.jme3.shadow.DirectionalLightShadowRenderer;
import com.jme3.util.SafeArrayList;

public class FirstWorldLight extends BaseAppState {

    DirectionalLight sun = new DirectionalLight();
    DirectionalLight sun1 = new DirectionalLight();
    DirectionalLight sun3 = new DirectionalLight();
    DirectionalLight sun4 = new DirectionalLight();
    DirectionalLight sun6 = new DirectionalLight();

    private Node rootNode;
    private SimpleApplication app;
    private AssetManager manager;
    private ViewPort view;

    private FilterPostProcessor fpp;
    DirectionalLightShadowFilter su;

    SafeArrayList<SceneProcessor> x1;

    private int turnOn = 0;
    private int state=1024;

    @Override
    protected void initialize(Application application) {
        app = (SimpleApplication) application;
        rootNode = app.getRootNode();
        manager = app.getAssetManager();
        view = app.getViewPort();
        sun.setDirection(new Vector3f(-1, -2, 1.25f));


        sun3.setDirection(new Vector3f(-1, 0, 0));
        sun4.setDirection(new Vector3f(1, 0, 0));
        sun6.setDirection(new Vector3f(0, 0, 1));

        sun.setColor(ColorRGBA.White.mult(0.97f));
        sun1.setColor(ColorRGBA.White.mult(0.97f));
        sun3.setColor(ColorRGBA.White.mult(0.4f));
        sun4.setColor(ColorRGBA.White.mult(0.4f));
        sun6.setColor(ColorRGBA.White.mult(0.4f));


    }

    public FirstWorldLight(int turnOn) {
        this.turnOn = turnOn;
    }


    public FirstWorldLight(int turnOn,int state) {
        this.turnOn = turnOn;
        this.state=state;
    }


    public FirstWorldLight() {
    }

    @Override
    protected void cleanup(Application application) {

    }

    @Override
    protected void onEnable() {
        //app.getRootNode().attachChild(this.rootNode);
        rootNode.addLight(sun);
        rootNode.addLight(sun1);
        rootNode.addLight(sun3);
        rootNode.addLight(sun4);
        rootNode.addLight(sun6);
        if (turnOn == 1) {
            su = new DirectionalLightShadowFilter(manager, state, 3);

            su.setLight(sun);
            x1 = view.getProcessors();
            for (int i = 0; i < x1.size(); i++) {
                if (x1.get(i) instanceof FilterPostProcessor)
                    fpp = (FilterPostProcessor) x1.get(i);
            }
            fpp.addFilter(su);
        }
    }

    @Override
    protected void onDisable() {
        rootNode.removeLight(sun);
        rootNode.removeLight(sun1);
        rootNode.removeLight(sun3);
        rootNode.removeLight(sun4);
        rootNode.removeLight(sun6);
        if (turnOn == 1) {
            fpp.removeFilter(su);
        }
    }
}
