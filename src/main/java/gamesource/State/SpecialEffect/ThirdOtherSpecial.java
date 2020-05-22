package gamesource.State.SpecialEffect;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.SceneProcessor;
import com.jme3.post.filters.*;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.util.SafeArrayList;

public class ThirdOtherSpecial extends BaseAppState {
    private Node rootNode;
    private SimpleApplication app;
    private AssetManager manager;
    private ViewPort view;

    private FilterPostProcessor fpp;

    SafeArrayList<SceneProcessor> x1;

    BloomFilter bloom;
    Vector3f sunDir;
    LightScatteringFilter lightScattering;
    CartoonEdgeFilter cartoonEdge;
    DepthOfFieldFilter depthOfField;

    @Override
    protected void initialize(Application application) {
        bloom = new BloomFilter();
        bloom.setBloomIntensity(0.2f);
        bloom.setBlurScale(0.2f);
        sunDir = new Vector3f(-1, -2, -3);
        lightScattering = new LightScatteringFilter(sunDir.mult(-3000));
        cartoonEdge = new CartoonEdgeFilter();
        cartoonEdge.setDepthSensitivity(0.4f);
        cartoonEdge.setEdgeIntensity(0.55f);
        cartoonEdge.setEdgeWidth(0.55f);
        cartoonEdge.setNormalThreshold(0.55f);
        depthOfField = new DepthOfFieldFilter();
        depthOfField.setFocusDistance(0);
        depthOfField.setFocusRange(25);
        depthOfField.setBlurScale(1.2f);

        app = (SimpleApplication) application;
        view = app.getViewPort();
        x1 = view.getProcessors();
        for (int i = 0; i < x1.size(); i++) {
            if (x1.get(i) instanceof FilterPostProcessor)
                fpp = (FilterPostProcessor) x1.get(i);
        }
    }

    @Override
    protected void cleanup(Application application) {

    }

    @Override
    protected void onEnable() {
        fpp.addFilter(cartoonEdge);
        fpp.addFilter(depthOfField);
        fpp.addFilter(bloom);
    }

    @Override
    protected void onDisable() {
        fpp.removeFilter(cartoonEdge);
        fpp.removeFilter(depthOfField);
        fpp.removeFilter(bloom);
    }
}
