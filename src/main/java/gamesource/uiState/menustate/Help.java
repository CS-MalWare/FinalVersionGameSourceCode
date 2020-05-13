package gamesource.uiState.menustate;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.simsilica.lemur.ActionButton;
import com.simsilica.lemur.CallMethodAction;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Insets3f;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.TabbedPanel;
import com.simsilica.lemur.core.VersionedReference;
import com.simsilica.lemur.event.ConsumingMouseListener;
import com.simsilica.lemur.event.MouseEventControl;
import com.simsilica.lemur.event.PopupState;
import com.simsilica.lemur.style.ElementId;

public class Help extends BaseAppState{
    private CloseCommand closeCommand = new CloseCommand();
    private TabbedPanel tabs;
    private VersionedReference<TabbedPanel.Tab> selectionRef;
    private Label stateLabel;
    private SimpleApplication app;
    private Container window;

    public Help(){

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
        window.addChild(new Label("Help", new ElementId("window.title.label")));
        MouseEventControl.addListenersToSpatial(window, ConsumingMouseListener.INSTANCE);

        tabs = window.addChild(new TabbedPanel());
        tabs.setInsets(new Insets3f(5, 5, 5, 5));
        selectionRef = tabs.getSelectionModel().createReference();

        tabs.addTab("Explore", createTabContents("In this state, YOU can USE W, S, A, D to move forward, backward, left, and right." 
            + "If you want to jump, YOU can press 'Space'"));
        tabs.addTab("Bag", createTabContents("We have design the Bag system to make sure you Can orgnize your cards and equipments"));
        tabs.addTab("Talk", createTabContents("Sometimes YOU need talk with others if you approach them, if you want to talk, you can press 'N'"));
    
        stateLabel = window.addChild(new Label("Current Tab: "));
        stateLabel.setInsets(new Insets3f(2, 5, 2, 5));

        window.addChild(new ActionButton(new CallMethodAction("Close", window, "removeFromParent")));
        window.setAlpha(2f);
        calculatePreferLocation();
        getState(PopupState.class).showPopup(window, closeCommand);
    }

    @Override
    public void update(float tpf){
        if(selectionRef.update()){
            stateLabel.setText("Select: " + selectionRef.get().getTitle());
        }
    }

    public void calculatePreferLocation(){
        float xOfCenter = app.getCamera().getWidth() / 2;
        float yOfCenter = app.getCamera().getHeight() / 2;

        float halfWidthOfWindow = window.getPreferredSize().x / 2;
        float halfHeightOfWindow = window.getPreferredSize().y / 2;
        window.setLocalTranslation(xOfCenter-halfWidthOfWindow, yOfCenter-halfHeightOfWindow, 100);
    }

    protected Container createTabContents(String content){
        Container contents = new Container();
        Label label = contents.addChild(new Label(content));

        label.setInsets(new Insets3f(5, 5, 5, 5));
        return contents;
    }

    private class CloseCommand implements Command<Object>{
        public void execute(Object src){
            getState(MenuAppState.class).closeChild(Help.this);
        }
    }
}