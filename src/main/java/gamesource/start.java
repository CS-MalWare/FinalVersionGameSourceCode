package gamesource;

import com.jme3.system.AppSettings;

public class start {
    public  void Start(int width,int length,int frame,int state){
        App app=new App(state);
        AppSettings settings =new AppSettings(true);
        settings.setTitle("malware");
        settings.setResolution(width,length);
        settings.setFrameRate(frame);
        settings.setVSync(true);
        app.setSettings(settings);
        app.setShowSettings(false);
        app.start();
    }
}
