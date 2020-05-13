package gamesource.uiState.menustate;

import com.jme3.app.SimpleApplication;

public class MenuStateDemo extends SimpleApplication{
    public void simpleInitApp(){
        stateManager.attach(new MenuAppState());
    }

    public static void main(String... args){
        MenuStateDemo menuStateDemo = new MenuStateDemo();
        menuStateDemo.start();
    }
}