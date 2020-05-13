package gamesource.State.SpecialEffect;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.renderer.Camera;

public class makeCross extends BaseAppState {

    private SimpleApplication app;
    private BitmapFont fnt;
    private BitmapText text;

    @Override
    protected void initialize(Application application) {
        app=(SimpleApplication)application;
        fnt=app.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
        text=new BitmapText(fnt,false);
        text.setText("+");
        Camera cam=app.getCamera();
        text.setSize(fnt.getPreferredSize() * 2f);
        float x = (cam.getWidth() - text.getLineWidth()) * 0.5f;
        float y = (cam.getHeight() + text.getLineHeight()) * 0.5f;
        text.setLocalTranslation(x, y, 0);

    }

    @Override
    protected void cleanup(Application application) {

    }

    @Override
    protected void onEnable() {
        app.getGuiNode().attachChild(text);
    }

    @Override
    protected void onDisable() {
        app.getGuiNode().detachChild(text);
    }
}
