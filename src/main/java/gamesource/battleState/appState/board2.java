package gamesource.battleState.appState;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;

public class board2 extends BaseAppState {
    private Geometry geom1;
    private Geometry geom2;
    private Geometry geom3;
    private Geometry geomMajor;
    private Geometry blgeom1;
    private Geometry blgeom2;
    private Geometry blgeom3;
    private Geometry blgeomMajor;
    private SimpleApplication app;
    private int number;
    @Override
    protected void initialize(Application application) {
        app=(SimpleApplication)application;
        Material mat = new Material(app.getAssetManager(),
                "Common/MatDefs/Misc/Unshaded.j3md");
        // 加载一个带透明度通道的纹理
        mat.setTexture("ColorMap",
                app.getAssetManager().loadTexture("Util/board3.png"));
        if(number==1) {
            geom1 = new Geometry("window frame", new Quad(1.6f, 0.4f));
            geom1.setMaterial(mat);

            geom1.move(2.0f, 1.25f, -0.8f);

            blgeom1 = new Geometry("window frame", new Quad(1.6f, 0.4f));
            blgeom1.setMaterial(mat);

            blgeom1.move(2.0f, -1.5f, -0.8f);
        }else if(number==2){
            geom1 = new Geometry("window frame", new Quad(1.6f, 0.4f));
            geom1.setMaterial(mat);

            geom1.move(2.0f, 1.25f, -0.8f);
            geom2 = new Geometry("window frame", new Quad(1.6f, 0.4f));
            geom2.setMaterial(mat);

            geom2.move(4.0f, 1.25f, -0.8f);// 将材质的混色模式设置为：BlendMode.Alpha
            blgeom1 = new Geometry("window frame", new Quad(1.6f, 0.4f));
            blgeom1.setMaterial(mat);

            blgeom1.move(2.0f, -1.5f, -0.8f);
            blgeom2 = new Geometry("window frame", new Quad(1.6f, 0.4f));
            blgeom2.setMaterial(mat);

            blgeom2.move(4.0f, -1.5f, -0.8f);// 将材质的混色模式设置为：BlendMode.Alpha
        } else if(number==3){

            geom1 = new Geometry("window frame", new Quad(1.6f, 0.4f));
            geom1.setMaterial(mat);

            geom1.move(2.0f, 1.25f, -0.8f);
            geom2 = new Geometry("window frame", new Quad(1.6f, 0.4f));
            geom2.setMaterial(mat);

            geom2.move(4.2f, 1.25f, -0.8f);// 将材质的混色模式设置为：BlendMode.Alpha

            geom3 = new Geometry("window frame", new Quad(1.6f, 0.4f));
            geom3.setMaterial(mat);

            geom3.move(6.4f, 1.25f, -0.8f);// 将材质的混色模式设置为：BlendMode.Alpha
        }
        geomMajor=new Geometry("window frame", new Quad(1.6f, 0.4f));
        geomMajor.setMaterial(mat);

        geomMajor.move(-4.2f, 1.25f, -0.8f);// 将材质的混色模式设置为：BlendMode.Alpha

        blgeomMajor=new Geometry("window frame", new Quad(1.6f, 0.4f));
        blgeomMajor.setMaterial(mat);

        blgeomMajor.move(-4.2f, -1.5f, -0.8f);// 将材质的混色模式设置为：BlendMode.Alpha
    }

    @Override
    protected void cleanup(Application application) {

    }

    public board2(int number){
        this.number=number;
        System.out.println(number);
    }

    @Override
    protected void onEnable() {
        if(number==1) {
            app.getRootNode().attachChild(geom1);
            app.getRootNode().attachChild(blgeom1);
            app.getRootNode().attachChild(geomMajor);
            app.getRootNode().attachChild(blgeomMajor);
        }else if(number==2){

            app.getRootNode().attachChild(geom1);
            app.getRootNode().attachChild(geom2);
            app.getRootNode().attachChild(blgeom1);
            app.getRootNode().attachChild(blgeom2);
            app.getRootNode().attachChild(geomMajor);
            app.getRootNode().attachChild(blgeomMajor);
        } else if(number==3){

        app.getRootNode().attachChild(geom1);
        app.getRootNode().attachChild(geom2);
            app.getRootNode().attachChild(geom3);
            app.getRootNode().attachChild(blgeom1);
            app.getRootNode().attachChild(blgeom2);
            app.getRootNode().attachChild(blgeom3);
        app.getRootNode().attachChild(geomMajor);
            app.getRootNode().attachChild(blgeomMajor);
    }
    }

    @Override
    protected void onDisable() {
        if(number==1) {
            app.getRootNode().detachChild(geom1);
            app.getRootNode().detachChild(blgeom1);
            app.getRootNode().detachChild(geomMajor);
            app.getRootNode().detachChild(blgeomMajor);
        }else if(number==2){

            app.getRootNode().detachChild(geom1);
            app.getRootNode().detachChild(geom2);
            app.getRootNode().detachChild(blgeom1);
            app.getRootNode().detachChild(blgeom2);
            app.getRootNode().detachChild(geomMajor);
            app.getRootNode().detachChild(blgeomMajor);
        }else if(number==3){

            app.getRootNode().detachChild(blgeom1);
            app.getRootNode().detachChild(blgeom2);
            app.getRootNode().detachChild(blgeom3);
            app.getRootNode().detachChild(geomMajor);
            app.getRootNode().detachChild(blgeomMajor);
        }
    }
}
