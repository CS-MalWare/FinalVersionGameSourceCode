package gamesource.uiState.menustate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import com.simsilica.lemur.ActionButton;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.CallMethodAction;
import com.simsilica.lemur.Checkbox;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.Insets3f;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.OptionPanelState;
import com.simsilica.lemur.style.BaseStyles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MenuAppState extends BaseAppState{

    static Logger logger = LoggerFactory.getLogger(MenuAppState.class);

    private HashMap<Class, String> classMap = new HashMap<>();
    private SimpleApplication app;
    private InputManager inputManager;
    private final static String MenuString = "Menu";
    private AppStateManager stateManager;

    private Container mainWindow;
    private boolean isMenuShow = false;
    private List<ToggleChild> toggles = new ArrayList<>();
    public static Class[] classes = {
        AlphaPanel.class,
        SaveSetting.class,
        Help.class
    };

    private boolean isStartOpen = false;

    public MenuAppState(){
        classMap.put(AlphaPanel.class, "Alpha Setting");
        classMap.put(SaveSetting.class, "Save Setting");
        classMap.put(Help.class, "Help");
    }

    public void closeChild(AppState child){
        for(ToggleChild toggleChild : toggles){
            if(toggleChild.child == child){
                toggleChild.close();
            }
        }
    }

    public float getStandardScale(){
        int height = getApplication().getCamera().getHeight();
        return height / 720f;
    }

    protected void showError(String title, String error){
        getState(OptionPanelState.class).show(title, error);
    }

    @Override
    protected void initialize(Application application){
        app = (SimpleApplication) application;
        this.inputManager = app.getInputManager();
        this.stateManager = app.getStateManager();

        inputManager.addMapping(MenuString, new KeyTrigger(KeyInput.KEY_V));
        inputManager.addListener(new MenuListener(), MenuString);
    }

    @Override
    protected void onEnable(){
    }

    @Override
    protected void onDisable(){
        try {
            mainWindow.removeFromParent();
        }
        catch(NullPointerException ne){
            ne.printStackTrace();
        }
    }

    private String classToName(Class type){
        return classMap.get(type);
    }

    @Override
    protected void cleanup(Application application){

    }

    class MenuListener implements ActionListener{
        @Override
        public void onAction(String name, boolean isPressed, float tpf){
            if(MenuString.equals(name) && isPressed && !isMenuShow){
                showMenu();
                isMenuShow = true;
            }else if(MenuString.equals(name) && isPressed){
                app.getGuiNode().detachChild(mainWindow);
                app.getFlyByCamera().setDragToRotate(false);
                app.getGuiNode().detachAllChildren();
                isMenuShow = false;
            }
        }
    }

    public void showMenu(){
        GuiGlobals.initialize(app);
        BaseStyles.loadGlassStyle();
        GuiGlobals.getInstance().getStyles().setDefaultStyle("glass");
        GuiGlobals.getInstance().requestFocus(mainWindow);

        mainWindow = new Container();
        Label title = mainWindow.addChild(new Label("Menu"));
        title.setFontSize(32);
        title.setInsets(new Insets3f(10, 10, 0, 10));

        Container actions = mainWindow.addChild(new Container());
        actions.setInsets(new Insets3f(10, 10, 0, 10));

        for(Class demClass : classes){
            ToggleChild toggleChild = new ToggleChild(demClass);
            toggles.add(toggleChild);
            Checkbox cb = actions.addChild(new Checkbox(toggleChild.getName()));
            cb.addClickCommands(toggleChild);
            cb.setInsets(new Insets3f(2, 2, 2, 2));
        }

        ActionButton exit = mainWindow.addChild(new ActionButton(new CallMethodAction("Exit Game", app, "stop")));
        exit.setInsets(new Insets3f(10, 10, 10, 10));

        int height = app.getCamera().getHeight();
        Vector3f pref = mainWindow.getPreferredSize().clone();
        float standardScale = getStandardScale();
        pref.multLocal(standardScale);

        float y = height * 0.9f;
        mainWindow.setLocalTranslation(100 * standardScale, y, 0);
        mainWindow.setLocalScale(standardScale);
        mainWindow.setAlpha(2f);
        
        app.getGuiNode().attachChild(mainWindow);
    }

    private class ToggleChild implements Command<Button>{
        private String name;
        private Checkbox check;
        private Class type;
        private AppState child;

        public ToggleChild(Class type){
            this.type = type;
            this.name = classToName(type);
        }

        public String getName(){
            return name;
        }

        public void execute(Button button){
            this.check = (Checkbox) button;
            System.out.println("Click:" + check);
            if(check.isChecked()){
                open();
            }else{
                close();
            }
        }

        public void open(){
            if(child != null){
                return;
            }
            try{
                child = (AppState)type.newInstance();
                getStateManager().attach(child);
            }catch(Exception e){
                showError("Error for demo:" + type.getSimpleName(), e.toString());
            }
        }

        public void close(){
            if(check != null){
                check.setChecked(false);
            }
            if(child != null){
                getStateManager().detach(child);
                child = null;
            }
        }
    }

    @Override
    public void update(float tpf){
        if(!isStartOpen){
            showMenu();
            isMenuShow = true;
            app.getGuiNode().detachChild(mainWindow);
            app.getFlyByCamera().setDragToRotate(false);
            isMenuShow = false;

            isStartOpen = true;
        }
    }
}
