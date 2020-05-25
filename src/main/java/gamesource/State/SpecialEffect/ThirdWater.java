package gamesource.State.SpecialEffect;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.ColorRGBA;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.SceneProcessor;
import com.jme3.renderer.ViewPort;
import com.jme3.util.SafeArrayList;
import com.jme3.water.WaterFilter;

public class ThirdWater extends BaseAppState {

    private ViewPort view;

    private SimpleApplication app;

    private float height = 0;

    private float Transparency = 0.2f;

    private ColorRGBA color = new ColorRGBA(0.4314f, 0.9373f, 0.8431f, 0.6f);

    private FilterPostProcessor fpp;

    SafeArrayList<SceneProcessor> x1;

    WaterFilter water = new WaterFilter();

    @Override
    protected void initialize(Application application) {
        app = (SimpleApplication) application;
        view = app.getViewPort();
        x1 = view.getProcessors();
        for (int i = 0; i < x1.size(); i++) {
            if (x1.get(i) instanceof FilterPostProcessor)
                fpp = (FilterPostProcessor) x1.get(i);
        }
        water.setRadius(350);
        water.setWaterHeight(height);
        water.setWaterTransparency(Transparency);
        water.setShapeType(WaterFilter.AreaShape.Circular);
        water.setWaterColor(color);
        water.setUnderWaterFogDistance(170);

    }

    public ThirdWater(float height) {
        this.height = height;
    }

    @Override
    protected void cleanup(Application application) {

    }

    @Override
    protected void onEnable() {
        fpp.addFilter(water);
    }

    @Override
    protected void onDisable() {
        fpp.removeFilter(water);
    }
}
