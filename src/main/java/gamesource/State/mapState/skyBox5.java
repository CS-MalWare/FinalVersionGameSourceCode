package gamesource.State.mapState;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;

public class skyBox5 extends BaseAppState {

    Spatial sky;
    private Node rootNode;
    private SimpleApplication app;

    @Override
    protected void initialize(Application application) {
        app=(SimpleApplication)application;
        Texture west = application.getAssetManager().loadTexture("skyBox/five/graycloud_ft.jpg");
        Texture east = application.getAssetManager().loadTexture("skyBox/five/graycloud_bk.jpg");
        Texture north = application.getAssetManager().loadTexture("skyBox/five/graycloud_lf.jpg");
        Texture south = application.getAssetManager().loadTexture("skyBox/five/graycloud_rt.jpg");
        Texture up = application.getAssetManager().loadTexture("skyBox/five/graycloud_up.jpg");
        Texture down = application.getAssetManager().loadTexture("skyBox/five/graycloud_dn.jpg");
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
