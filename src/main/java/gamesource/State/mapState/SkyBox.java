package gamesource.State.mapState;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;

public class SkyBox extends BaseAppState {

    Spatial sky;
    private Node rootNode;
    private SimpleApplication app;
    private Spatial pic;
    @Override
    protected void initialize(Application application) {
        app=(SimpleApplication)application;
        Texture west = application.getAssetManager().loadTexture("bluecloud_ft.jpg");
        Texture east = application.getAssetManager().loadTexture("bluecloud_bk.jpg");
        Texture north = application.getAssetManager().loadTexture("bluecloud_lf.jpg");
        Texture south = application.getAssetManager().loadTexture("bluecloud_rt.jpg");
        Texture up = application.getAssetManager().loadTexture("bluecloud_up.jpg");
        Texture down = application.getAssetManager().loadTexture("bluecloud_dn.jpg");
        sky = SkyFactory.createSky(application.getAssetManager(), west, east, north, south, up, down);
        rootNode=app.getRootNode();
        app.getGuiNode().detachChild(pic);
    }

    public SkyBox(Spatial pic){
        this.pic=pic;
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
