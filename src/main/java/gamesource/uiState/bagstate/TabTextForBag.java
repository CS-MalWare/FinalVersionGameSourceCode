package gamesource.uiState.bagstate;

import com.jme3.app.Application;
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

public class TabTextForBag extends BaseAppState{
    private Container window;
    private final String message1;
    private final String message2;
    private final String message3;
    private final String tab1;
    private final String tab2;
    private final String tab3;
    private final CardUI cardUI;

    private CloseCommand closeCommand = new CloseCommand();
    private TabbedPanel tabs;
    private Label statusLabel;
    private VersionedReference<TabbedPanel.Tab> selectionRef;

    @Override
    protected void initialize(Application application){

    }

    @Override
    protected void onDisable(){
        window.removeFromParent();
    }

    @Override 
    protected void cleanup(Application application){

    }

    @Override
    protected void onEnable(){
        window = new Container();
        window.addChild(new Label("Card Description", new ElementId("window.title.label")));
        MouseEventControl.addListenersToSpatial(window, ConsumingMouseListener.INSTANCE);

        tabs = window.addChild(new TabbedPanel());
        tabs.setInsets(new Insets3f(5, 5, 5, 5));
        selectionRef = tabs.getSelectionModel().createReference();

        tabs.addTab(tab1, setTabContents(message1));
        tabs.addTab(tab2, setTabContents(message2));
        tabs.addTab(tab3, setTabContents(message3));

        statusLabel = window.addChild(new Label("Status"));
        statusLabel.setInsets(new Insets3f(2, 5, 2, 5));

        window.addChild(new ActionButton(new CallMethodAction("Close", window, "removeFromParent")));
        calculatePreferLocation();
        getState(PopupState.class).showPopup(window, closeCommand);
        window.scale(1.2f);
        window.setAlpha(2f);
    }

    public void calculatePreferLocation(){
        float xOfWindow = getState(BagAppState.class).getWindow().getPreferredSize().x +
            getState(BagAppState.class).getWindow().getLocalTranslation().x;
        float yOfWindow = getState(BagAppState.class).getWindow().getLocalTranslation().y;
        window.setLocalTranslation(xOfWindow, yOfWindow, 100);
    }

    @Override
    public void update(float tpf){
        if(selectionRef.update()){
            statusLabel.setText("Selected " + selectionRef.get().getTitle());
        }
    }

    protected Container setTabContents(String content){
        Container contents = new Container();
        Label label = contents.addChild(new Label(content));
        label.setInsets(new Insets3f(5, 5, 5, 5));
        return contents;
    }

    public TabTextForBag(CardUI cardUI, String tab1, String tab2, String tab3, String message1, String message2, String message3){
        this.cardUI = cardUI;
        this.tab1 = tab1;
        this.tab2 = tab2;
        this.tab3 = tab3;
        this.message1 = message1;
        this.message2 = message2;
        this.message3 = message3;
    } 

    public static class Builder{
        private final String tab1;
        private final String tab2;
        private final String tab3;
        private final CardUI cardUI;
        private String message1 = "";
        private String message2 = "";
        private String message3 = "";
        
        public Builder(CardUI cardUI, String tab1, String tab2, String tab3){
            this.cardUI = cardUI;
            this.tab1 = tab1;
            this.tab2 = tab2;
            this.tab3 = tab3;
        }
        

        public Builder setMessage1(String message1){
            this.message1 = message1;
            return this;
        }

        public Builder setMessgae2(String message2){
            this.message2 = message2;
            return this;
        }

        public Builder setMessage3(String message3){
            this.message3 = message3;
            return this;
        }

        public TabTextForBag build(){
            return new TabTextForBag(this);
        }
    }

    private TabTextForBag(Builder builder){
        this.tab1 = builder.tab1;
        this.tab2 = builder.tab2;
        this.tab3 = builder.tab3;
        this.cardUI = builder.cardUI;
        this.message1 = builder.message1;
        this.message2 = builder.message2;
        this.message3 = builder.message3;
    }


    private class CloseCommand implements Command<Object>{
        public void execute(Object src){
            getState(BagAppState.class).getStateManager().detach(TabTextForBag.this);
        }
    }
}