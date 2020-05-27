package gamesource.battleState.appState;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;

public class Board extends BaseAppState {
    private Geometry geom;
    private SimpleApplication app;

    @Override
    protected void initialize(Application application) {
        app=(SimpleApplication)application;
        Material mat = new Material(app.getAssetManager(),
                "Common/MatDefs/Misc/Unshaded.j3md");
        // 加载一个带透明度通道的纹理
        mat.setTexture("ColorMap",
                app.getAssetManager().loadTexture("Util/board1.png"));

        geom = new Geometry("window frame", new Quad(6.1f, 3));
        geom.setMaterial(mat);

        geom.move(1.8f, 1.4f, -0.8f);
    }

    @Override
    protected void cleanup(Application application) {

    }

    @Override
    protected void onEnable() {
        app.getRootNode().attachChild(geom);
    }

    @Override
    protected void onDisable() {
        app.getRootNode().detachChild(geom);
    }
}
