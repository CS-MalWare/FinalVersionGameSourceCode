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

import gamesource.State.controlState.InputAppState;
import gamesource.State.mapState.FirstWorldState;
import gamesource.State.worldState.FifthState;
import gamesource.State.worldState.FirstState;
import gamesource.State.worldState.ForthState;
import gamesource.State.worldState.SecondState;
import gamesource.State.worldState.ThirdState;
import gamesource.battleState.appState.EnemyState;
import gamesource.battleState.appState.GetCardState;
import gamesource.battleState.battle.Battle;
import gamesource.battleState.character.enemy.originalForest.RedSlime;
import gamesource.uiState.bagstate.BagAppState;
import gamesource.uiState.shopstate.ShopAppState;

public class TalkWithOption extends BaseAppState{
    private ArrayList<String> talkContent = new ArrayList<>();
    private String modelName;
    private SimpleApplication app;
    private Container window;
    private Label contentLabel;
    private int contentStep;
    private int stage;
    private ActionButton continueButton;
    private ArrayList<BaseAppState> states;

    private ActionButton optionButton;
    private ActionButton closeButton;

    public static enum CallType{            //Talk option 
        CONFIRM,
        SHOP,
        FIGHT,
        SAVE
    }
    private CallType callType;

    public TalkWithOption(String modelName, ArrayList<String> talkContent, CallType callType, int stage, ArrayList<BaseAppState> states){
        this.modelName = modelName;
        this.talkContent = talkContent;
        this.callType = callType;
        this.stage = stage;
        this.states = states;
    }

    @Override
    protected void initialize(Application application){             //initialize GUI
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
    protected void onEnable(){                  //set basic components
        window = new Container();
        window.addChild(new Label(modelName, new ElementId("window.title.label")));
        contentLabel = window.addChild(new Label(talkContent.get(contentStep)));
        contentLabel.setMaxWidth(400);
        calculatePreferLocation();
        window.setAlpha(2f);
        
        continueButton = window.addChild(new ActionButton(new CallMethodAction("Continue", this, "continueToNext")));
        getState(PopupState.class).showPopup(window);
    }

    protected void calculatePreferLocation(){               //calculate prefer location for window
        float preferCenterWith = app.getCamera().getWidth() / 2;
        float preferCenterHeight = app.getCamera().getHeight() / 2;

        float halfLabelWidth = window.getPreferredSize().x / 2;
        float halfLabelHeight = window.getPreferredSize().y / 2;

        window.setLocalTranslation(preferCenterWith - halfLabelWidth, preferCenterHeight - halfLabelHeight, 100);
    }

    public void continueToNext(){                           //roll up the content of talking
        contentStep ++;
        contentLabel.setText(talkContent.get(contentStep));
        contentLabel.setFontSize(18f);
        if(contentStep == talkContent.size() - 1){
            optionState();
        }
    }

    public void optionState(){                              //identify the option of talking
        if(callType == CallType.CONFIRM){
            window.removeChild(continueButton);
            optionButton = window.addChild(new ActionButton(new CallMethodAction("Yes", this, "confirm")));
            closeButton = window.addChild(new ActionButton(new CallMethodAction("Close", this, "close")));
        }else if(callType == CallType.SHOP){
            window.removeChild(continueButton);
            optionButton = window.addChild(new ActionButton(new CallMethodAction("Open", this, "shop")));
            closeButton = window.addChild(new ActionButton(new CallMethodAction("Close", this, "close")));
        }else if(callType == CallType.FIGHT){
            window.removeChild(continueButton);
            optionButton = window.addChild(new ActionButton(new CallMethodAction("Fight", this, "fight")));
            closeButton = window.addChild(new ActionButton(new CallMethodAction("Close", this, "close")));
        }else if(callType == CallType.SAVE){
            window.addChild(new ActionButton(new CallMethodAction("Save", this, "save")));
        }
    }

    public void confirm(){                                     //check option to end conversation
        Label endLabel = new Label("Press N to Exit...");
        window.addChild(endLabel);
        window.removeChild(continueButton);
    }

    public void fight(){
        switch (stage) {
            case 1:                                             
                getState(PopupState.class).closePopup(window);
                getState(FirstState.class).getStateManager().detach(TalkWithOption.this);
                app.getFlyByCamera().setDragToRotate(false);
                app.getStateManager().detach(app.getStateManager().getState(InputAppState.class));
                app.getStateManager().getState(ShopAppState.class).onFight();
                app.getStateManager().getState(BagAppState.class).onFight();
                app.getInputManager().deleteTrigger(FirstState.talk, FirstState.TALK);
                app.getInputManager().deleteTrigger(FirstState.change, FirstState.CHANGECAMERA);
                app.getInputManager().deleteTrigger(FirstState.bag, FirstState.BAG);
                app.getInputManager().deleteTrigger(FirstState.move, FirstState.MOVE);
                EnemyState.getInstance().addEnemies(
                    new RedSlime(50, "character/solidier/solidier.j3o", 7, 2, 2, 0, 0, 0, 0, 0),
                    new RedSlime(50, "character/solidier/solidier.j3o", 7, 2, 2, 0, 0, 0, 0, 0)
                );
                app.getStateManager().attach(new Battle(states));
                GetCardState.setGoldCountAfterThisBattle(40);
                
                app.getFlyByCamera().setDragToRotate(false);
                break;
        
            default:
                break;
        }
    }

    public void shop(){
        switch(stage){
            case 1:
            getState(FirstState.class).getState(ShopAppState.class).showShop();
            getState(FirstState.class).getStateManager().detach(TalkWithOption.this);
            getState(PopupState.class).closePopup(window);
            app.getFlyByCamera().setDragToRotate(true);
            break;

            case 2:
            getState(SecondState.class).getState(ShopAppState.class).showShop();
            getState(SecondState.class).getStateManager().detach(TalkWithOption.this);
            getState(PopupState.class).closePopup(window);
            app.getFlyByCamera().setDragToRotate(true);
            break;

            case 3:
            getState(ThirdState.class).getState(ShopAppState.class).showShop();
            getState(ThirdState.class).getStateManager().detach(TalkWithOption.this);
            getState(PopupState.class).closePopup(window);
            app.getFlyByCamera().setDragToRotate(true);
            break;
            
            case 4:
            getState(ForthState.class).getState(ShopAppState.class).showShop();
            getState(ForthState.class).getStateManager().detach(TalkWithOption.this);
            getState(PopupState.class).closePopup(window);
            app.getFlyByCamera().setDragToRotate(true);
            break;

            case 5:
            getState(FifthState.class).getState(ShopAppState.class).showShop();
            getState(FifthState.class).getStateManager().detach(TalkWithOption.this);
            getState(PopupState.class).closePopup(window);
            app.getFlyByCamera().setDragToRotate(true);
            break;
        }
    }

    public void save(){
    }

    public void close(){                                        //close the talking option window
        window.detachChild(optionButton);
        window.detachChild(closeButton);
        contentLabel.setText("You have finished the conversation ! Press N to Exit!");
        contentLabel.setAlpha(2f);
        contentLabel.setFontSize(18f);
    }

    public Container getWindow(){
        return window;
    }

    // public void update(float tpf){
    //     if(EnemyState.getInstance().getEnemies().size() == 0){
    //         app.getStateManager().attach(app.getStateManager().getState(InputAppState.class));
            
    //     }
    // }
}