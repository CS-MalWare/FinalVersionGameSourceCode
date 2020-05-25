package gamesource;

import com.jme3.system.AppSettings;

public class start {
    public  void Start(int frame,int state,int shadow,int open,int juchi,boolean full){
        App app=new App(state,shadow,open,juchi);
        AppSettings settings =new AppSettings(true);
        settings.setTitle("malware");
        if(full==false) {
            settings.setResolution(1600, 900);
            settings.setFrameRate(frame);
            settings.setVSync(true);
            settings.setFullscreen(false);
        }else{
            settings.setFrameRate(frame);
            settings.setVSync(true);
            settings.setFullscreen(true);
        }
        app.setSettings(settings);
        app.setShowSettings(false);
        app.start();
    }
}
