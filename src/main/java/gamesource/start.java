package gamesource;

import com.jme3.system.AppSettings;
import gamesource.util.Storage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class start {
    public  void Start(int frame,int state,int shadow,int open,int juchi,boolean full) throws IOException {
        if(full==true) {
            App app = new App(state, shadow, open, juchi);
            AppSettings settings = new AppSettings(true);

            // 这里是导入地图存档的,导入人物属性存档在 MainRole类中
            Storage.load();

            settings.setTitle("Cholera");// 标题
            settings.setResolution(1920, 1080);// 分辨率
            settings.setFrameRate(frame);//限制fps
       settings.setIcons(new BufferedImage[]{
               ImageIO.read(App.class.getResource( "/Map/icon.png" ))});
            app.setSettings(settings);
            settings.setFullscreen(full);
            app.setShowSettings(false);
            //app.setShowSettings(false);
//        Storage.load();
            app.start();
        }else {
            App app = new App(state, shadow, open, juchi);
            AppSettings settings = new AppSettings(true);

            // 这里是导入地图存档的,导入人物属性存档在 MainRole类中
            Storage.load();

            settings.setTitle("Cholera");// 标题
            settings.setResolution(1600, 900);// 分辨率
            settings.setFrameRate(frame);//限制fps
       settings.setIcons(new BufferedImage[]{
               ImageIO.read(App.class.getResource( "/Map/icon.png" ))});
            app.setSettings(settings);
            settings.setFullscreen(false);
            app.setShowSettings(false);
            //app.setShowSettings(false);
//        Storage.load();
            app.start();
        }
    }
    public  static void main(String[] args){
        App app = new App(6, 1024, 1, 4);
        AppSettings settings = new AppSettings(true);

        // 这里是导入地图存档的,导入人物属性存档在 MainRole类中
        //Storage.load();

        settings.setTitle("Cholera");// 标题
        settings.setResolution(1920, 1080);// 分辨率
        settings.setFrameRate(100);//限制fps
//        settings.setIcons(new BufferedImage[]{
//                ImageIO.read(Main.class.getResource( "/Map/icon.png" ))});
        app.setSettings(settings);
        settings.setFullscreen(true);
        app.setShowSettings(false);
        //app.setShowSettings(false);
//        Storage.load();
        app.start();
    }
}
