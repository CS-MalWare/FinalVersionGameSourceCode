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
import gamesource.State.worldState.FifthState;
import gamesource.State.worldState.FirstState;
import gamesource.State.worldState.ForthState;
import gamesource.State.worldState.SecondState;
import gamesource.State.worldState.ThirdState;
import gamesource.uiState.shopstate.ShopAppState;

public class TalkWithOption extends BaseAppState{
    private ArrayList<String> talkContent = new ArrayList<>();
    private String modelName;
    private SimpleApplication app;
    private Container window;
    private Label contentLabel;
    private int contentStep;
    private boolean isTalkShow = false;
    private int stage;

    public static enum CallType{
        CONFIRM,
        SHOP,
        FIGHT,
        SAVE
    }
    private CallType callType;

    public TalkWithOption(String modelName, ArrayList<String> talkContent, CallType callType, int stage){
        this.modelName = modelName;
        this.talkContent = talkContent;
        this.callType = callType;
        this.stage = stage;
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
        app.getFlyByCamera().setDragToRotate(false);
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
        isTalkShow = true;
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
            switch(stage){
                case 1: getState(FirstState.class).getStateManager().detach(TalkWithOption.this);
                case 2: getState(SecondState.class).getStateManager().detach(TalkWithOption.this);
                case 3: getState(ThirdState.class).getStateManager().detach(TalkWithOption.this);
                case 4: getState(ForthState.class).getStateManager().detach(TalkWithOption.this);
                case 5: getState(FifthState.class).getStateManager().detach(TalkWithOption.this);
            }
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
        }else if(callType == CallType.FIGHT){
            window.addChild(new ActionButton(new CallMethodAction("Fight", this, "fight")));
        }else if(callType == CallType.SAVE){
            window.addChild(new ActionButton(new CallMethodAction("Save", this, "save")));
        }
    }

    public void confirm(){
        isTalkShow = false;
        switch(stage){
            case 1:
            getState(FirstState.class).getStateManager().detach(TalkWithOption.this);
            cleanup();

            case 2:
            getState(SecondState.class).getStateManager().detach(TalkWithOption.this);
            cleanup();

            case 3:
            getState(ThirdState.class).getStateManager().detach(TalkWithOption.this);
            cleanup();

            case 4:
            getState(ForthState.class).getStateManager().detach(TalkWithOption.this);
            cleanup();

            case 5:
            getState(FifthState.class).getStateManager().detach(TalkWithOption.this);
            cleanup();
        }
    }

    public void fight(){
        isTalkShow = false;
        //调用战斗场景
    }

    public void shop(){
        isTalkShow = false;
        switch(stage){
            case 1:
            getState(FirstState.class).getState(ShopAppState.class).showShop();
            getState(FirstState.class).getStateManager().detach(TalkWithOption.this);

            case 2:
            getState(SecondState.class).getState(ShopAppState.class).showShop();
            getState(SecondState.class).getStateManager().detach(TalkWithOption.this);

            case 3:
            getState(ThirdState.class).getState(ShopAppState.class).showShop();
            getState(ThirdState.class).getStateManager().detach(TalkWithOption.this);
            
            case 4:
            getState(ForthState.class).getState(ShopAppState.class).showShop();
            getState(ForthState.class).getStateManager().detach(TalkWithOption.this);

            case 5:
            getState(FifthState.class).getState(ShopAppState.class).showShop();
            getState(FifthState.class).getStateManager().detach(TalkWithOption.this);
        }
    }

    public void save(){
        isTalkShow = false;
    }

    public boolean isTalkShow(){
        return isTalkShow;
    }
}