package gamesource.State.controlState;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.renderer.Camera;

public class getCamState extends BaseAppState {

        public final static String get ="GET";

        private InputManager inputManager;
        private AppStateManager stateManager;
        private Camera cam;

        protected void initialize (Application application){
        this.inputManager = application.getInputManager();
        this.stateManager = application.getStateManager();
        cam=application.getCamera();
        inputManager.addMapping(get,new KeyTrigger(KeyInput.KEY_F1));

        inputManager.addListener(new MyActionListener(),get);
    }

        @Override
        protected void cleanup(Application application) {

    }

        @Override
        protected void onEnable() {

    }

        @Override
        protected void onDisable() {

    }

        class MyActionListener implements ActionListener {
            @Override
            public void onAction(String name,boolean isPressed,float tpf){
                if(get.equals(name)&&isPressed){
                    System.out.println(cam.getLocation());
                    System.out.println(cam.getDirection());
                }
            }
        }
}
