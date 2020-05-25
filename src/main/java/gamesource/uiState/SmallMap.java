package gamesource.uiState;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.texture.Texture;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.HAlignment;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.VAlignment;
import com.simsilica.lemur.component.IconComponent;
import com.simsilica.lemur.component.QuadBackgroundComponent;
import com.simsilica.lemur.component.TbtQuadBackgroundComponent;
import com.simsilica.lemur.event.ConsumingMouseListener;
import com.simsilica.lemur.event.MouseEventControl;
import com.simsilica.lemur.event.PopupState;
import com.simsilica.lemur.style.BaseStyles;
import com.simsilica.lemur.style.ElementId;
import com.simsilica.lemur.style.Styles;

import gamesource.State.CharacterState.MajorActor;
import gamesource.State.worldState.FirstState;

public class SmallMap extends BaseAppState{
    private Container window;
    private Container point;
    private SimpleApplication app;
    private int width;
    private int height;
    private int size;
    private MajorActor majorActor;
    private boolean isShowMap = false;
    
    public SmallMap(int width, int height, int size){
        this.width = width;
        this.height = height;
        this.size = size;
    }

    @Override
    protected void initialize(Application application){
        System.out.println("Small Map Get");
        app = (SimpleApplication) application;
        GuiGlobals.initialize(app);
        BaseStyles.loadGlassStyle();
        GuiGlobals.getInstance().getStyles().setDefaultStyle("glass");
        GuiGlobals.getInstance().requestFocus(window);

        majorActor = app.getStateManager().getState(MajorActor.class);
        
        // Texture mapTexture = app.getAssetManager().loadTexture("SmallMap.png");
        // mapTexture.getImage().setWidth(size);
        // mapTexture.getImage().setHeight(size);
        // window.setBackground(new QuadBackgroundComponent(mapTexture));
        app.getInputManager().addMapping("map", new KeyTrigger(KeyInput.KEY_M));
        app.getInputManager().addListener(new SmallMapListener(), "map");
    }

    @Override
    protected void cleanup(Application application){
        app.getGuiNode().detachChild(window);
        app.getFlyByCamera().setDragToRotate(false);
    }

    @Override
    protected void onEnable(){

    }

    @Override
    protected void onDisable(){
    }

    protected void calculatePreferLocation(){
        float x = width - size - 50;
        float y = height - size - 50;

        window.setLocalTranslation(x, y, 200);
    }

   
    public void update(float tpf){
        if(isShowMap){
            point.setLocalTranslation(majorActor.getX(), majorActor.getY(), 200);
        }
    }

    public class SmallMapListener implements ActionListener{
        public void onAction(String name, boolean isPressed, float tpf){
            System.out.println("Get Map");
            if("map".equals(name) && isPressed && !isShowMap){
                app.getFlyByCamera().setDragToRotate(true);

                window = new Container();
                Label map = window.addChild(new Label("Map", new ElementId("window.title.label")));
                MouseEventControl.addListenersToSpatial(window, ConsumingMouseListener.INSTANCE);
                
                IconComponent iconComponent = new IconComponent("SmallMap.png");
                iconComponent.setIconSize(new Vector2f(size, size));
                iconComponent.setHAlignment(HAlignment.Center);
                iconComponent.setVAlignment(VAlignment.Bottom);
                map.setIcon(iconComponent);
                calculatePreferLocation();

                point = new Container();
                point.setBackground(new QuadBackgroundComponent(new ColorRGBA(0f, 0f, 1f, 1f)));
                app.getGuiNode().attachChild(point);

                app.getGuiNode().attachChild(window);
                isShowMap = true;
            }else if("map".equals(name) && isPressed && isShowMap){
                isShowMap = false;
                cleanup();
            }
        }
    }
}