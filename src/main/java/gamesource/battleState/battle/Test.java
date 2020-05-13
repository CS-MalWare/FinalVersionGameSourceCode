package gamesource.battleState.battle;

import gamesource.State.controlState.getCamState;
import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.*;
import com.jme3.post.FilterPostProcessor;
import com.jme3.shadow.DirectionalLightShadowFilter;
import com.jme3.system.AppSettings;


public class Test extends SimpleApplication {

    //Picture last = new Picture("null");

    private float width;
    private float height;

    public Test() {

        super(
                new Battle(),
                new getCamState()
//                , new GetCardState()
//                new GetEquipmentState()
        );

/*        enemyState.addEnemies(
                new DarkDragon(90, "Enemies/skeleton/blueSkeleton/blueSkeleton.j3o", 0, 0, 0, 0, 0, 0, 0, 0),
                new DarkDragon(90, "Enemies/skeleton/blueSkeleton/blueSkeleton.j3o", 0, 0, 0, 0, 0, 0, 0, 0),
                new DarkDragon(90, "Enemies/skeleton/blueSkeleton/blueSkeleton.j3o", 0, 0, 0, 0, 0, 0, 0, 0)
        );*/
/*       enemyState.addEnemies(
                new DarkDragon(70, "Enemies/skeleton/redSkeleton/redSkeleton.j3o", 0, 0, 0, 0, 0, 0, 0, 0),
                new DarkDragon(70, "Enemies/skeleton/redSkeleton/redSkeleton.j3o", 0, 0, 0, 0, 0, 0, 0, 0),
                new DarkDragon(70, "Enemies/skeleton/redSkeleton/redSkeleton.j3o", 0, 0, 0, 0, 0, 0, 0, 0)
        );*/
/*        enemyState.addEnemies(
                new DarkDragon(80, "Enemies/skeleton/greenSkeleton/greenSkeleton.j3o", 0, 0, 0, 0, 0, 0, 0, 0),
                new DarkDragon(80, "Enemies/skeleton/greenSkeleton/greenSkeleton.j3o", 0, 0, 0, 0, 0, 0, 0, 0),
                new DarkDragon(80, "Enemies/skeleton/greenSkeleton/greenSkeleton.j3o", 0, 0, 0, 0, 0, 0, 0, 0)
        );*/
//        enemyState.addEnemies(
//                new DarkDragon(85, "Enemies/Dragon/Dragon/dragon.obj", 0, 0, 0, 0, 0, 0, 0, 0),
//                new DarkDragon(85, "Enemies/Dragon/Dragon/dragon.obj", 0, 0, 0, 0, 0, 0, 0, 0),
//                new DarkDragon(85, "Enemies/Dragon/Dragon/dragon.obj", 0, 0, 0, 0, 0, 0, 0, 0)
//        );
    }




    @Override
    public void simpleInitApp() {
        addLight();
    }








    public void addLight() {
        // 定向光
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-1, -2, -3));

        //viewPort.setBackgroundColor(ColorRGBA.LightGray);
        // 环境光
        AmbientLight ambient = new AmbientLight();

        // 调整光照亮度
        ColorRGBA lightColor = new ColorRGBA();
        sun.setColor(lightColor.mult(2f));
        ambient.setColor(lightColor.mult(2f));

        rootNode.addLight(sun);
        rootNode.addLight(ambient);

        DirectionalLightShadowFilter su = new DirectionalLightShadowFilter(assetManager, 1024, 3);
        su.setLight(sun);
        su.setEnabled(true);
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        fpp.addFilter(su);
        viewPort.addProcessor(fpp);
    }


    public static void main(String[] args) {
        // 配置参数
        AppSettings settings = new AppSettings(true);


        settings.setTitle("protect yuetao king");// 标题
        settings.setResolution(1600, 900);// 分辨率
        settings.setFrameRate(100);//限制fps
        Test app = new Test();

        app.setSettings(settings);
        app.setShowSettings(false);

        app.start();
    }
}
