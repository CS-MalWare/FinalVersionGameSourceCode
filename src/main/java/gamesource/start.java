package gamesource;

import com.jme3.system.AppSettings;
import gamesource.util.Storage;

public class start {
    public  void Start(int frame,int state,int shadow,int open,int juchi,boolean full){
        App app = new App(state, shadow, open, juchi);
        AppSettings settings = new AppSettings(true);

        // 这里是导入地图存档的,导入人物属性存档在 MainRole类中
        //Storage.load();

        settings.setTitle("Cholera");// 标题
        settings.setResolution(1600, 900);// 分辨率
        settings.setFrameRate(frame);//限制fps
//        settings.setIcons(new BufferedImage[]{
//                ImageIO.read(Main.class.getResource( "/Map/icon.png" ))});
        app.setSettings(settings);
        app.setShowSettings(false);
        settings.setFullscreen(full);
        //app.setShowSettings(false);
//        Storage.load();
        app.start();
    }
}
