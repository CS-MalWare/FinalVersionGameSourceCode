package gamesource.uiState.shopstate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.simsilica.lemur.Checkbox;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.DefaultRangedValueModel;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.LayerComparator;
import com.simsilica.lemur.Panel;
import com.simsilica.lemur.Slider;
import com.simsilica.lemur.TextField;
import com.simsilica.lemur.component.DynamicInsetsComponent;
import com.simsilica.lemur.component.QuadBackgroundComponent;
import com.simsilica.lemur.component.TbtQuadBackgroundComponent;
import com.simsilica.lemur.core.VersionedReference;
import com.simsilica.lemur.core.VersionedReferenceList;
import com.simsilica.lemur.event.CursorEventControl;
import com.simsilica.lemur.event.CursorMotionEvent;
import com.simsilica.lemur.event.DefaultCursorListener;
import com.simsilica.lemur.event.DragHandler;
import com.simsilica.lemur.style.ElementId;
import com.simsilica.lemur.style.Styles;

import gamesource.util.MySlider;

public class Demo extends SimpleApplication{
    private VersionedReference<Double> redReference;
    private VersionedReference<Double> greenReference;
    private VersionedReference<Double> blueReference;
    private VersionedReference<Double> alphReference;
    private VersionedReference<Boolean> showStatesReference;
    private VersionedReference<Boolean> showFpsReference;

    private ColorRGBA boxColor = ColorRGBA.Blue.clone();

    private Panel test;
    private TextField tf;
    private String strInsertText = "Inserted";
    private VersionedReference[] references = new VersionedReference[100];

    public static void main(String... args){
        Demo demo = new Demo();
        demo.start();
    }

    @Override
    public void simpleInitApp(){
        GuiGlobals.initialize(this);

        stateManager.detach(stateManager.getState(FlyCamAppState.class));

        Styles styles = GuiGlobals.getInstance().getStyles();
        styles.getSelector(Slider.THUMB_ID, "glass").set("text", "[]", false);
        styles.getSelector(Panel.ELEMENT_ID, "glass").set("background", 
            new QuadBackgroundComponent(new ColorRGBA(0, 0.25f, 0.25f, 0.5f)));
        styles.getSelector(Checkbox.ELEMENT_ID, "glass").set("background", 
            new QuadBackgroundComponent(new ColorRGBA(0, 0.5f, 0.5f, 0.5f)));
        styles.getSelector("spacer", "glass").set("background", 
            new QuadBackgroundComponent(new ColorRGBA(1, 0.0f, 0.0f, 0.0f)));
        styles.getSelector("header", "glass").set("background", 
            new QuadBackgroundComponent(new ColorRGBA(0, 0.75f, 0.75f, 0.5f)));
        styles.getSelector("header", "glass").set("shadowColor", new ColorRGBA(1, 0f, 0f, 1));

        Container hudPanel = new Container("glass");
        hudPanel.setLocalTranslation(5, cam.getHeight() - 50, 0);
        guiNode.attachChild(hudPanel);

        Container panel = new Container("glass");
        hudPanel.addChild(panel);

        panel.setBackground(new QuadBackgroundComponent(new ColorRGBA(0, 0.5f, 0.5f, 0.5f), 5, 5, 0.02f, false));
        panel.addChild(new Label("States Settings", new ElementId("header"), "glass"));
        panel.addChild(new Panel(2, 2, ColorRGBA.Cyan, "glass")).setUserData(LayerComparator.LAYER, 2);

        Checkbox temp = panel.addChild(new Checkbox("Show States"));
        temp.setChecked(true);
        showStatesReference = temp.getModel().createReference();

        temp = panel.addChild(new Checkbox("Show FPS"));
        temp.setChecked(true);
        showFpsReference = temp.getModel().createReference();
        references[0] = showFpsReference;

        hudPanel.addChild(new Panel(10f, 10f, new ElementId("spacer"), "glass"));

        panel = new Container("glass");
        panel.setBackground(new QuadBackgroundComponent(new ColorRGBA(0, 0.5f, 0.5f, 0.5f), 5, 5, 0.02f, false));
        panel.addChild(new Label("Cube Settings", new ElementId("header"), "glass"));
        panel.addChild(new Panel(2, 2, ColorRGBA.Cyan, "glass")).setUserData(LayerComparator.LAYER, 2);
        panel.addChild(new Label("Red:"));
        final MySlider redSlider = new MySlider();
        redSlider.setBackground(new QuadBackgroundComponent(new ColorRGBA(0.5f, 0.1f, 0.1f, 0.5f), 5, 5, 0.02f, false));
        redReference = panel.addChild(redSlider).getModel().createReference();
        CursorEventControl.addListenersToSpatial(redSlider, new DefaultCursorListener(){
            @Override
            public void cursorMoved(CursorMotionEvent event, Spatial target, Spatial capture){
                System.out.println("event:" + event);
                Vector3f cp = event.getCollision().getContactPoint();
                cp = redSlider.worldToLocal(cp, null);
                System.out.println("Range value:" + redSlider.getValueForLocation(cp));
            }
        });

        panel.addChild(new Label("Green:"));
        greenReference = panel.addChild(new Slider("glass")).getModel().createReference();
        panel.addChild(new Label("Blue:"));
        blueReference = panel.addChild(new Slider(new DefaultRangedValueModel(0, 100, 100), "glass")).getModel().createReference();
        panel.addChild(new Label("Alpha:"));
        alphReference = panel.addChild(new Slider(new DefaultRangedValueModel(0, 100, 100), "glass")).getModel().createReference();
        hudPanel.addChild(panel);

        hudPanel.addChild(new Panel(10f, 10f, new ElementId("spacer"), "glass"));

        panel = new Container("glass");
        panel.addChild(new Label("Test entry:"));
        hudPanel.addChild(panel);

        guiNode.attachChild(hudPanel);

        Vector3f hudSize = new Vector3f(200, 0, 0);
        hudSize.maxLocal(hudPanel.getPreferredSize());
        hudPanel.setPreferredSize(hudSize);

        //Box box = new Box(1, 1, 1);
        //Geometry geometry = new Geometry("Box", box);
       // rootNode.attachChild(box);

        Container testPanel = new Container();
        testPanel.setPreferredSize(new Vector3f(200, 200, 0));
        testPanel.setBackground(TbtQuadBackgroundComponent.create("textures/bench_mat_baseColor.png", 
            2, 2, 2, 3, 3, 0, false));
        Label test = testPanel.addChild(new Label("Border Test"));
        test.setShadowColor(ColorRGBA.Red);

        test.setInsetsComponent(new DynamicInsetsComponent(0.5f, 0.5f, 0.5f, 0.5f));
        testPanel.setLocalTranslation(400, 400, 0);

        CursorEventControl.addListenersToSpatial(testPanel, new DragHandler());
        guiNode.attachChild(testPanel);
    }

    @Override
    public void simpleUpdate(float tpf){
        if(showStatesReference.update()){
            setDisplayStatView(showStatesReference.get());
        }
        if(showFpsReference.update()){
            setDisplayFps(showFpsReference.get());
        }

        boolean updateColor = false;
        if(redReference.update()){
            updateColor = true;
        }
        if(greenReference.update())
            updateColor = true;
        if(blueReference.update())
            updateColor = true;
        if(alphReference.update())
            updateColor = true;
        if(updateColor){
            boxColor.set((float)(redReference.get()/100),
                        (float)(greenReference.get()/100),
                        (float)(blueReference.get()/100),
                        (float)(alphReference.get()/100));
        }

    }
}