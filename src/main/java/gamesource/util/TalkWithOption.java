package gamesource.util;

import java.util.ArrayList;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.simsilica.lemur.ActionButton;
import com.simsilica.lemur.CallMethodAction;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.event.PopupState;
import com.simsilica.lemur.style.BaseStyles;
import com.simsilica.lemur.style.ElementId;

import gamesource.State.mapState.FirstWorldState;
import gamesource.State.worldState.FirstState;
import gamesource.uiState.shopstate.ShopAppState;

public class TalkWithOption extends BaseAppState{
    private ArrayList<String> talkContent = new ArrayList<>();
    private String modelName;
    private SimpleApplication app;
    private Container window;
    private Label contentLabel;
    private int contentStep;
    public static enum CallType{
        CONFIRM,
        SHOP
    }
    private CallType callType;

    public TalkWithOption(String modelName, ArrayList<String> talkContent, CallType callType){
        this.modelName = modelName;
        this.talkContent = talkContent;
        this.callType = callType;
    }

    @Override
    protected void initialize(Application application){
        app = (SimpleApplication) application;
        GuiGlobals.initialize(app);
        BaseStyles.loadGlassStyle();
        GuiGlobals.getInstance().getStyles().setDefaultStyle("glass");
        GuiGlobals.getInstance().requestFocus(window);
    }

    @Override
    protected void cleanup(Application application){

    }

    @Override
    protected void onDisable(){

    }

    @Override
    protected void onEnable(){
        window = new Container();
        window.addChild(new Label(modelName, new ElementId("window.title.label")));
        contentLabel = window.addChild(new Label(talkContent.get(contentStep)));
        contentLabel.setMaxWidth(400);
        calculatePreferLocation();
        window.setAlpha(1f);
        
        window.addChild(new ActionButton(new CallMethodAction("Continue", this, "continueToNext")));
        getState(PopupState.class).showPopup(window);
    }

    protected void calculatePreferLocation(){
        float preferCenterWith = app.getCamera().getWidth() / 2;
        float preferCenterHeight = app.getCamera().getHeight() / 2;

        float halfLabelWidth = window.getPreferredSize().x / 2;
        float halfLabelHeight = window.getPreferredSize().y / 2;

        window.setLocalTranslation(preferCenterWith - halfLabelWidth, preferCenterHeight - halfLabelHeight, 100);
    }

    public void continueToNext(){
        if(contentStep == talkContent.size() - 1){
            getState(FirstState.class).getStateManager().detach(TalkWithOption.this);
        }else{
            contentStep ++;
            contentLabel.setText(talkContent.get(contentStep));
            if(contentStep == talkContent.size() - 1){
                optionState();
            }
        }
    }

    public void optionState(){
        if(callType == CallType.CONFIRM){
            window.addChild(new ActionButton(new CallMethodAction("Yes", this, "confirm")));
        }else if(callType == CallType.SHOP){
            window.addChild(new ActionButton(new CallMethodAction("Open", this, "shop")));
        }
    }

    public void confirm(){
        //调用特定的战斗训练场景
    }

    public void shop(){
        getState(FirstWorldState.class).getState(ShopAppState.class).showShop();
        getState(FirstWorldState.class).getStateManager().detach(TalkWithOption.this);
    }
}