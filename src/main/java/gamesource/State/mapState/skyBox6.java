package gamesource.State.mapState;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;

public class skyBox6 extends BaseAppState {

    Spatial sky;
    private Node rootNode;
    private SimpleApplication app;

    @Override
    protected void initialize(Application application) {
        app=(SimpleApplication)application;
        Texture west = application.getAssetManager().loadTexture("skyBox/six/fr.jpg");
        Texture east = application.getAssetManager().loadTexture("skyBox/six/bk.jpg");
        Texture north = application.getAssetManager().loadTexture("skyBox/six/left.jpg");
        Texture south = application.getAssetManager().loadTexture("skyBox/six/right.jpg");
        Texture up = application.getAssetManager().loadTexture("skyBox/six/up.jpg");
        Texture down = application.getAssetManager().loadTexture("skyBox/six/down.jpg");
        sky = SkyFactory.createSky(application.getAssetManager(), west, east, north, south, up, down);
        rootNode=app.getRootNode();
    }

    @Override
    protected void cleanup(Application application) {

    }

    @Override
    protected void onEnable() {
        rootNode.attachChild(sky);
    }

    @Override
    protected void onDisable() {
        rootNode.detachChild(sky);
    }
}
