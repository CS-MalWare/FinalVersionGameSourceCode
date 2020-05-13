package gamesource.util;

import java.util.ArrayList;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.simsilica.lemur.ActionButton;
import com.simsilica.lemur.CallMethodAction;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.event.PopupState;
import com.simsilica.lemur.style.ElementId;
import gamesource.State.worldState.FirstState;

public class WordWrapForTalk extends BaseAppState{
    private SimpleApplication app;
    private Container window;
    private Label modelNameLabel;
    private Label contentLabel;
    private ArrayList<String> talkContent;
    private ArrayList<String> modelName;
    private int contentStep;

    public WordWrapForTalk(ArrayList<String> modelName, ArrayList<String> talkContent){
        this.talkContent = talkContent;
        this.modelName = modelName;
        this.contentStep = 0;
    }

    @Override
    protected void initialize(Application application){
        app = (SimpleApplication) application;
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
        modelNameLabel = window.addChild(new Label(modelName.get(0), new ElementId("window.title.label")));
        contentLabel = window.addChild(new Label(talkContent.get(contentStep)));
        contentLabel.setMaxWidth(400);
        calculatePreferLocation();

        window.addChild(new ActionButton(new CallMethodAction("Close", this, "close")));

        getState(PopupState.class).showPopup(window);
    }

    protected void calculatePreferLocation(){
        float preferCenterWith = app.getCamera().getWidth() / 2;
        float preferCenterHeight = app.getCamera().getHeight() / 2;

        float halfLabelWidth = window.getPreferredSize().x / 2;
        float halfLabelHeight = window.getPreferredSize().y / 2;

        window.setLocalTranslation(preferCenterWith - halfLabelWidth, preferCenterHeight - halfLabelHeight, 100);
    }

    public void close(){
        if(contentStep == talkContent.size() - 1){
            getState(FirstState.class).getStateManager().detach(WordWrapForTalk.this);
        }else{
            contentStep ++;
            modelNameLabel.setText(getAnotherNmae());
            contentLabel.setText(talkContent.get(contentStep));
        }
    }

    public String getAnotherNmae(){
        int index = modelName.indexOf(modelNameLabel.getText()) + 1;
        return modelName.get(modelName.size() - index);
    }
}