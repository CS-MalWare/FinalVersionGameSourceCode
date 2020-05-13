package gamesource.testState;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.debug.Arrow;

public class AxisState extends BaseAppState {
    private AssetManager assetManager;
    private SimpleApplication app;
    private Node rootNode=new Node("AxisState");

    @Override
    protected void initialize(Application application) {
        app=(SimpleApplication)application;
        assetManager=app.getAssetManager();
        createArrow(new Vector3f(50,0,0), ColorRGBA.Green);
        createArrow(new Vector3f(0,50,0),ColorRGBA.Red);
        createArrow(new Vector3f(0,0,50),ColorRGBA.Blue);
    }


    private void createArrow(Vector3f vect,ColorRGBA color){
        Material mat=new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color",color);

        Geometry geom=new Geometry("arrow",new Arrow(vect));
        geom.setMaterial(mat);

        rootNode.attachChild(geom);
    }

    @Override
    protected void cleanup(Application application) {
    }

    @Override
    protected void onEnable() {
        app.getRootNode().attachChild(this.rootNode);
    }

    @Override
    protected void onDisable() {

    }
}
