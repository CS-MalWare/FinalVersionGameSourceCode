package gamesource.uiState.menustate;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.simsilica.lemur.ActionButton;
import com.simsilica.lemur.CallMethodAction;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.DefaultRangedValueModel;
import com.simsilica.lemur.HAlignment;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.Slider;
import com.simsilica.lemur.VAlignment;
import com.simsilica.lemur.component.IconComponent;
import com.simsilica.lemur.core.VersionedReference;
import com.simsilica.lemur.event.PopupState;
import com.simsilica.lemur.style.ElementId;

public class AlphaPanel extends BaseAppState{
    private Container window;
    private CloseCommand closeCommand = new CloseCommand();

    private Label description;
    private VersionedReference<Double> alpha;

    public AlphaPanel(){

    }

    @Override
    protected void initialize(Application application){
        
    }

    @Override
    protected void cleanup(Application application){

    }

    @Override
    protected void onEnable(){
        window = new Container();
        window.addChild(new Label("Alpha Setting", new ElementId("window.title.label")));
    
        description = window.addChild(new Label("Drag the slider to change the window alpha:" + 0.5));
        IconComponent iconComponent = new IconComponent("/bluecloud_bk.jpg");
        iconComponent.setIconScale(0.5f);
        iconComponent.setHAlignment(HAlignment.Center);
        iconComponent.setVAlignment(VAlignment.Top);
        description.setIcon(iconComponent);

        Slider slider = window.addChild(new Slider(new DefaultRangedValueModel(0.1, 1, 0.5)));
        alpha = slider.getModel().createReference();

        window.addChild(new ActionButton(new CallMethodAction("Close", window, "removeFromParent")));
        window.setLocalTranslation(400, getApplication().getCamera().getHeight() * 0.9f, 50);
        getState(PopupState.class).showPopup(window, closeCommand);

        refreshAlpha();
    }

    protected void refreshAlpha(){
        String s = String.format("Drag the slider to change the window alpha: %.2f", alpha.get());
        description.setText(s);
        window.setAlpha((float)(double)alpha.get());
    }

    @Override
    public void update(float tpf){
        if(alpha.update()){
            refreshAlpha();
        }
    }

    @Override
    protected void onDisable(){
        window.removeFromParent();
    }

    private class CloseCommand implements Command<Object>{
        public void execute(Object src){
            getState(MenuAppState.class).closeChild(AlphaPanel.this);
        }
    }
}