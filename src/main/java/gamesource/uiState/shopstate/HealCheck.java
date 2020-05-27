package gamesource.uiState.shopstate;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.simsilica.lemur.ActionButton;
import com.simsilica.lemur.CallMethodAction;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.event.ConsumingMouseListener;
import com.simsilica.lemur.event.CursorEventControl;
import com.simsilica.lemur.event.DragHandler;
import com.simsilica.lemur.event.MouseEventControl;
import com.simsilica.lemur.event.PopupState;
import com.simsilica.lemur.style.ElementId;

import gamesource.battleState.character.MainRole;

public class HealCheck extends BaseAppState{
    private Container dragWindow;
    private int cost;
    private Label label;
    private ActionButton actionButton;
    private SimpleApplication app;

    public HealCheck(int cost, String labelContent){
        this.label = new Label(labelContent);
        this.cost = cost;
    }

    @Override
    protected void initialize(Application application){
        app = (SimpleApplication) application;
    }

    @Override
    protected void cleanup(Application applicaton){

    }

    protected void createAllDrag(){
        dragWindow = new Container();

        dragWindow.addChild(new Label("Check", new ElementId("window.title.label")));
        CursorEventControl.addListenersToSpatial(dragWindow, new DragHandler());
        MouseEventControl.addListenersToSpatial(dragWindow, ConsumingMouseListener.INSTANCE);

        dragWindow.addChild(label);
        actionButton = dragWindow.addChild(new ActionButton(new CallMethodAction(this, "Confirm")));
        dragWindow.setAlpha(2f);
        label.setFontSize(18f);
        calculatePreferLocation();
        app.getGuiNode().attachChild(dragWindow);
        System.out.println("get Health");
    }

    public void calculatePreferLocation(){
        float xOfWindow = getState(ShopAppState.class).getGeneral().getLocalTranslation().x + 
            getState(ShopAppState.class).getGeneral().getPreferredSize().x;
        float yOfWindow = getState(ShopAppState.class).getGeneral().getLocalTranslation().y - 
            getState(ShopAppState.class).getGeneral().getPreferredSize().y - 50;
        dragWindow.setLocalTranslation(xOfWindow, yOfWindow, 100);
    }

    public void Confirm(){
        int totalMoney = MainRole.getInstance().getGold();
        if(totalMoney - cost >= 0){
            MainRole.getInstance().setHP(MainRole.getInstance().getHP()+20);
        }else{
            dragWindow.detachChild(actionButton);
            label.setText("Sorry! You have not enough money!");
            dragWindow.addChild(new ActionButton(new CallMethodAction(this, "OK")));
        }
    }

    public void OK(){
        app.getGuiNode().detachChild(dragWindow);
        getState(ShopAppState.class).getStateManager().detach(HealCheck.this);
    }

    @Override
    protected void onEnable(){
        createAllDrag();
    }

    @Override
    protected void onDisable(){
        if(dragWindow != null){
            dragWindow.removeFromParent();
        }
        dragWindow.removeFromParent();
    }

    private class CloseCommand implements Command<Object>{
        public void execute(Object src){
            getState(ShopAppState.class).getStateManager().detach(HealCheck.this);
        }
    }

}