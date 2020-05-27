package gamesource.uiState.bagstate;

import java.util.ArrayList;

import com.jme3.app.SimpleApplication;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Checkbox;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.OptionPanel;
import com.simsilica.lemur.Panel;
import com.simsilica.lemur.RollupPanel;
import com.simsilica.lemur.component.QuadBackgroundComponent;
import com.simsilica.lemur.event.DefaultMouseListener;
import com.simsilica.lemur.event.MouseEventControl;
import com.simsilica.lemur.style.ElementId;
import com.simsilica.lemur.style.Styles;

import gamesource.util.MySlider;
import gamesource.util.WordWrapForTalk;


public class Test extends SimpleApplication{
    private Container container;
    private Vector3f location;
    public void simpleInitApp(){
        //IconComponent iconComponent = new IconComponent("Cards/neutral/attack/上勾拳+.png", 0.1f, 0, 0, 0, false);
        flyCam.setEnabled(false);
        GuiGlobals.initialize(this);

        Styles styles = GuiGlobals.getInstance().getStyles();
        styles.getSelector(Panel.ELEMENT_ID, "glass").set("background", 
            new QuadBackgroundComponent(new ColorRGBA(0, 0.25f, 0.25f, 0.5f)));
        styles.getSelector(Checkbox.ELEMENT_ID, "glass").set("background", 
            new QuadBackgroundComponent(new ColorRGBA(0, 0.5f, 0.5f, 0.5f)));
        styles.getSelector("spacer", "glass").set("background", 
            new QuadBackgroundComponent(new ColorRGBA(1, 0.0f, 0.0f, 0.0f)));
        styles.getSelector("header", "glass").set("background", 
            new QuadBackgroundComponent(new ColorRGBA(0, 0.75f, 0.75f, 0.5f)));
        styles.getSelector("header", "glass").set("shadowColor", new ColorRGBA(1, 0f, 0f, 1));

        container = new Container("glass");

        Button clickButton = new Button("click");
        container.setLocalTranslation(50, cam.getHeight() - 50, 0);
        container.addChild(clickButton);
        clickButton.addClickCommands(new Click());
        RollupPanel rollupPanel = new RollupPanel("test", new ElementId("roll"), "glass");
        rollupPanel.attachChild(container);
        guiNode.attachChild(rollupPanel);
        MySlider slider = new MySlider();
        styles.getSelector("slider", "button", "glass").set("text", "[]");

        container.addChild(slider);
        slider.setLocalTranslation(100, cam.getHeight() - 200, 0);
        ArrayList<String> content = new ArrayList<>();
        content.add("hello");
        content.add("Wei");
        ArrayList<String> modelName = new ArrayList<>();
        modelName.add("first");
        modelName.add("second");
        WordWrapForTalk wordWrapForTalk = new WordWrapForTalk(modelName, content, 1);
        stateManager.attach(wordWrapForTalk);
    }

    public static void main(String... agrs){
        Test test = new Test();
        test.start();
    }

    private class Click implements Command<Button>{
        public void execute(Button button){
            if(button.isPressed()){
                final String name = button.getName();
                MouseEventControl.addListenersToSpatial(button, new DefaultMouseListener(){
                    public void click(MouseButtonEvent event, Spatial target, Spatial capture){
                        Vector3f mp = new Vector3f(event.getX(), event.getY(), 0);
                        Container inner = new Container();
                        OptionPanel optionPanel = new OptionPanel(name);
                        inner.addChild(optionPanel);
                        inner.setLocalTranslation(mp);
                        container.addChild(inner);
                    }
                });
            }
        }
    }
}