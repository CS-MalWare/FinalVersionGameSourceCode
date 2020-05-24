package gamesource.uiState;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.texture.Texture;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.component.QuadBackgroundComponent;
import com.simsilica.lemur.component.TbtQuadBackgroundComponent;
import com.simsilica.lemur.event.PopupState;
import com.simsilica.lemur.style.BaseStyles;
import com.simsilica.lemur.style.ElementId;
import com.simsilica.lemur.style.Styles;

import gamesource.State.CharacterState.MajorActor;
import gamesource.State.worldState.FirstState;

public class SmallMap extends BaseAppState{
    private Container window;
    private Container point = new Container("glass");
    private SimpleApplication app;
    private int width;
    private int height;
    private int size;
    private MajorActor majorActor;
    private boolean isShowMap = false;

    private CloseCommand closeCommand = new CloseCommand();
    
    public SmallMap(int width, int height, int size){
        this.width = width;
        this.height = height;
        this.size = size;
    }

    @Override
    protected void initialize(Application application){
        app = (SimpleApplication) application;
        majorActor = app.getStateManager().getState(MajorActor.class);
    }

    @Override
    protected void cleanup(Application application){
        app.getGuiNode().detachChild(window);
        app.getFlyByCamera().setDragToRotate(false);
    }

    @Override
    protected void onEnable(){
        window = new Container("glass");
        window.addChild(new Label("Map"));
        
        // Texture mapTexture = app.getAssetManager().loadTexture("SmallMap.png");
        // mapTexture.getImage().setWidth(size);
        // mapTexture.getImage().setHeight(size);
        // window.setBackground(new QuadBackgroundComponent(mapTexture));
        app.getInputManager().addMapping("Map", new KeyTrigger(KeyInput.KEY_M));
        app.getInputManager().addListener(new SmallMapListener(), "Map");
    }

    @Override
    protected void onDisable(){
        window.removeFromParent();
    }

    protected void calculatePreferLocation(){
        float x = width - size;
        float y = height - size - 50;

        window.setLocalTranslation(x, y, 100);
    }

    @Override
    public void update(float tpf){
        point.setLocalTranslation(majorActor.getX(), majorActor.getY(), 100);
    }

    class SmallMapListener implements ActionListener{
        public void onAction(String name, boolean isPressed, float tpf){
            if("Map".equals(name) && isPressed && !isShowMap){
                GuiGlobals.initialize(app);
                BaseStyles.loadGlassStyle();
                GuiGlobals.getInstance().getStyles().setDefaultStyle("glass");
                GuiGlobals.getInstance().requestFocus(window);
                app.getFlyByCamera().setDragToRotate(true);
                
                Texture mapTexture = app.getAssetManager().loadTexture("SmallMap.png");
                mapTexture.getImage().setWidth(size);
                mapTexture.getImage().setHeight(size);
                window.setBackground(new QuadBackgroundComponent(mapTexture));
                calculatePreferLocation();

                point.setBackground(new QuadBackgroundComponent(new ColorRGBA(0f, 0f, 1f, 1f)));
                window.addChild(point);

                getState(PopupState.class).showPopup(window, closeCommand);
                isShowMap = true;
            }else if("Map".equals(name) && isPressed && isShowMap){
                isShowMap = false;
                cleanup();
            }
        }
    }

    private class CloseCommand implements Command<Object>{
        public void execute(Object src){
            getState(FirstState.class).getStateManager().detach(SmallMap.this);
        }
    }
}