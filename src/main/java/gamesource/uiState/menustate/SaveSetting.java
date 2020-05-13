package gamesource.uiState.menustate;

import java.util.ArrayList;
import java.util.HashMap;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.simsilica.lemur.ActionButton;
import com.simsilica.lemur.Axis;
import com.simsilica.lemur.CallMethodAction;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.ListBox;
import com.simsilica.lemur.component.SpringGridLayout;
import com.simsilica.lemur.event.ConsumingMouseListener;
import com.simsilica.lemur.event.MouseEventControl;
import com.simsilica.lemur.event.PopupState;
import com.simsilica.lemur.style.ElementId;

import gamesource.util.Storage;

public class SaveSetting extends BaseAppState{
    private Container window;
    private CloseCommand closeCommand = new CloseCommand();
    private ListBox listBox;
    private SimpleApplication app;
    private HashMap<Integer, String> fileNameMap = new HashMap<>();

    private int nextItem = 1;

    public SaveSetting(){

    }

    @Override
    protected void initialize(Application application){ 
        app = (SimpleApplication) application;
    }   

    @Override
    protected void cleanup(Application application){

    }

    @Override
    protected void onDisable() {

    }

    @Override
    protected void onEnable(){
        window = new Container();
        window.addChild(new Label("Save", new ElementId("window.title.label")));
        MouseEventControl.addListenersToSpatial(window, ConsumingMouseListener.INSTANCE);

        listBox = window.addChild(new ListBox());
        listBox.setVisibleItems(5);

        for(int i=0; i<5; i++){
            listBox.getModel().add("Save " + nextItem);
            nextItem ++;
        }

        Container buttons = window.addChild(new Container(new SpringGridLayout(Axis.X, Axis.Y)));
        buttons.addChild(new ActionButton(new CallMethodAction(this, "save")));
        buttons.addChild(new ActionButton(new CallMethodAction(this, "delete")));

        window.addChild(new ActionButton(new CallMethodAction("Close", window, "removeFromParent")));
        window.setAlpha(2f);
        calculatePreferLocation();
        getState(PopupState.class).showPopup(window, closeCommand);
    }

    public void save(){
        Integer selection = listBox.getSelectionModel().getSelection();
        if(selection == null){
            return;
        }
        //fileNameMap.get(selection);
    }

    public void delete(){
        Integer selection = listBox.getSelectionModel().getSelection();
        if(selection == null){
            return;
        }
        //fileNameMap.put(selection, String.valueOf(System.currentTimeMillis()));
        //Storage.save();
    }

    public void calculatePreferLocation(){  
        float xOfCenter = app.getCamera().getWidth() / 2;
        float yOfCenter = app.getCamera().getHeight() / 2;

        float halfWidthOfWindow = window.getPreferredSize().x / 2;
        float halfHeightOfWindow = window.getPreferredSize().y / 2;
        window.setLocalTranslation(xOfCenter-halfWidthOfWindow, yOfCenter-halfHeightOfWindow, 100);
    }
    private class CloseCommand implements Command<Object>{
        public void execute(Object src){
            getState(MenuAppState.class).closeChild(SaveSetting.this);
        }
    }
}