package gamesource.uiState;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.texture.Texture;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.component.QuadBackgroundComponent;
import com.simsilica.lemur.event.PopupState;
import com.simsilica.lemur.style.ElementId;

import gamesource.State.worldState.FirstState;

public class SmallMap extends BaseAppState{
    private Container window;
    private Container mapWindow;
    private SimpleApplication app;
    private int width;
    private int height;
    private int size;

    private CloseCommand closeCommand = new CloseCommand();
    
    public SmallMap(int width, int height, int size){
        this.width = width;
        this.height = height;
        this.size = size;
    }

    @Override
    protected void initialize(Application application){
        app = (SimpleApplication) application;
    }

    @Override
    protected void cleanup(Application application){

    }

    @Override
    protected void onEnable(){
        window = new Container();
        window.addChild(new Label("Map"), new ElementId("smallmap.title.label"));
        
        mapWindow = new Container();
        Texture mapTexture = app.getAssetManager().loadTexture("SmallMap.png");
        mapTexture.getImage().setWidth(size);
        mapTexture.getImage().setHeight(size);
        mapWindow.setBackground(new QuadBackgroundComponent(mapTexture));
        calculatePreferLocation();

        getState(PopupState.class).showPopup(window, closeCommand);
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
        
    }

    private class CloseCommand implements Command<Object>{
        public void execute(Object src){
            getState(FirstState.class).getStateManager().detach(SmallMap.this);
        }
    }
}