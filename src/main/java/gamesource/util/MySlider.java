package gamesource.util;

import com.jme3.input.MouseInput;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.simsilica.lemur.Axis;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.DefaultRangedValueModel;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.Panel;
import com.simsilica.lemur.RangedValueModel;
import com.simsilica.lemur.component.BorderLayout;
import com.simsilica.lemur.component.BorderLayout.Position;
import com.simsilica.lemur.core.AbstractGuiControlListener;
import com.simsilica.lemur.core.GuiControl;
import com.simsilica.lemur.core.VersionedReference;
import com.simsilica.lemur.event.CursorButtonEvent;
import com.simsilica.lemur.event.CursorEventControl;
import com.simsilica.lemur.event.CursorMotionEvent;
import com.simsilica.lemur.event.DefaultCursorListener;
import com.simsilica.lemur.style.Attributes;
import com.simsilica.lemur.style.ElementId;
import com.simsilica.lemur.style.StyleDefaults;
import com.simsilica.lemur.style.Styles;

public class MySlider extends Panel{
    public static final String ELEMENT_ID = "myslider";
    public static final String UP_ID = "up.button";
    public static final String DOWN_ID = "down.button";
    public static final String THUMB_ID = "thumb.button";
    public static final String RANGE_ID = "range";

    private BorderLayout borderLayout;
    private Container container = new Container("glass");
    private Button increment;
    private Button decremnet;
    private Button thumb;
    private Panel range;
    private Axis axis;

    private RangedValueModel model;
    private double delta = 1f;
    private VersionedReference<Double> state;

    public MySlider(RangedValueModel model, Axis axis, boolean applyStyles, ElementId elementId, String style){
        Styles styles = GuiGlobals.getInstance().getStyles();
        styles.initializeStyles(getClass());
        this.borderLayout = new BorderLayout();
        container.setLayout(borderLayout);
        this.model = model;
        this.axis = axis;

        increment = container.addChild(new Button(null, elementId.child(UP_ID), style), Position.North);
        decremnet = container.addChild(new Button(null, elementId.child(DOWN_ID), style), Position.South);
        range = container.addChild(new Panel(2, 50, elementId.child(RANGE_ID), style));

        setupCommands();
        thumb = new Button(null, elementId.child(THUMB_ID), style);
        ButtonDragger dragger = new ButtonDragger();
        CursorEventControl.addListenersToSpatial(thumb, dragger);
        container.attachChild(thumb);

        thumb.getControl(GuiControl.class).setSize(thumb.getControl(GuiControl.class).getPreferredSize());
        if(applyStyles){
            styles.applyStyles(this, elementId, style);
        }
    }

    public MySlider(){
        this(new DefaultRangedValueModel(), Axis.Y, true, new ElementId(ELEMENT_ID), "glass");
    }

    @SuppressWarnings("unchecked")
    protected final void setupCommands(){
        increment.addClickCommands(new ChangeValueCommand(1));
        decremnet.addClickCommands(new ChangeValueCommand(-1));
    }

    @StyleDefaults(ELEMENT_ID)
    public void initializeDefaultStyles(Styles styles, Attributes attributes){
        ElementId parent = new ElementId(ELEMENT_ID);
        styles.getSelector(parent.child(UP_ID), null).set("text", "^", false);
        styles.getSelector(parent.child(DOWN_ID), null).set("text", "v", false);
        styles.getSelector(parent.child(THUMB_ID), null).set("text", "[]", false);
    }

    private class ButtonDragger extends DefaultCursorListener{
        private Vector2f drag = null;
        private double startPercent;

        @Override
        public void cursorButtonEvent(CursorButtonEvent event, Spatial target, Spatial capture){
            if(event.getButtonIndex() != MouseInput.BUTTON_LEFT){
                return;
            }
            event.setConsumed();
            if(event.isPressed()){
                drag = new Vector2f(event.getX(), event.getY());
                startPercent = model.getPercent();
            }
        }

        @Override
        public void cursorMoved(CursorMotionEvent event, Spatial target, Spatial capture){
            if(drag == null){
                return;
            }
            Vector3f v1 = null;
            Vector3f v2 = null;
            v1 = new Vector3f(0, thumb.getSize().y * 0.5f, 0);
            v2 = v1.add(0, range.getSize().y - thumb.getSize().y * 0.5f, 0);

            v1 = event.getRelativeViewCoordinates(range, v1);
            v2 = event.getRelativeViewCoordinates(range, v2);

            Vector3f dir = v2.subtract(v1);
            float length = dir.length();
            dir.multLocal(1/length);
            Vector3f cursorDir = new Vector3f(event.getX() - drag.x, event.getY() - drag.y, 0);
            float dot = cursorDir.dot(dir);

            float percent = dot / length;
            model.setPercent(startPercent + percent);
            event.setConsumed();
        }
    }

    private class ChangeValueCommand implements Command<Button>{
        private double scale;

        public ChangeValueCommand(double scale){
            this.scale = scale;
        }

        public void execute(Button source){
            model.setValue(model.getValue() + delta * scale);
        }

    }

    private class ReshapeListener extends AbstractGuiControlListener{
        @Override
        public void reshape(GuiControl source, Vector3f pos, Vector3f size){
            resetStateView();
        }
    }

    public double getValueForLocation(Vector3f loc){
        Vector3f relative = loc.subtract(range.getLocalTranslation());
        relative.y *= -1;

        Vector3f aixsDir = axis.getDirection();
        double projection = relative.dot(aixsDir);
        if(projection < 0){
            if(axis == Axis.Y){
                return model.getMaximum();
            }else{
                return model.getMinimum();
            }
        }

        Vector3f rangeSize = range.getSize().clone();
        double rangeLength = rangeSize.dot(aixsDir);
        projection = Math.min(projection, rangeLength);
        double part = projection/rangeLength;
        double rangeDelta = model.getMaximum() - model.getMinimum();

        if(axis == Axis.Y){
            part = 1-part;
        }

        return model.getMinimum() + rangeDelta * part;
    }

    @Override
    public void updateLogicalState(float tpf){
        super.updateLogicalState(tpf);
        if(state == null || state.update()){
            resetStateView();
        }
    }

    protected void resetStateView(){
        if(state == null){
            state = model.createReference();
        }

        Vector3f pos = range.getLocalTranslation();
        Vector3f rangeSize = range.getSize();
        Vector3f thumbSize = thumb.getSize();
        Vector3f size = getSize();

        double visibleRange;
        double x, y;

        visibleRange = rangeSize.y - thumbSize.y;
        x = pos.x + rangeSize.x * 0.5;
        y = pos.y - rangeSize.y + (visibleRange * model.getPercent());
        thumb.setLocalTranslation((float)(x- thumbSize.x * 0.5), (float)(y+thumbSize.y), pos.z + size.z);
    }

    public RangedValueModel getModel(){
        return model;
    }
}