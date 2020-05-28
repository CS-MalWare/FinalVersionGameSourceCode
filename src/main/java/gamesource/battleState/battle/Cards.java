package gamesource.battleState.battle;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.RawInputListener;
import com.jme3.input.event.*;
import com.jme3.math.FastMath;
import com.jme3.system.AppSettings;
import com.jme3.ui.Picture;

public class Cards extends SimpleApplication {
    private Picture card = new Picture("card1");


    @Override
    public void simpleInitApp() {

        card.setHeight(350);
        card.setWidth(350);
        card.setImage(assetManager, "Cards/caster/attack/爆破.png", true);
        card.setPosition(650, -50);
        Picture card2 = new Picture("cacrd2");
        card2.setHeight(350);
        card2.setWidth(350);
        card2.setImage(assetManager, "Cards/caster/attack/爆破.png", true);
        card2.setPosition(500, -80);
        card2.rotate(0,0,FastMath.PI/180 *5 );

        guiNode.attachChild(card2);
        guiNode.attachChild(card);
        flyCam.setEnabled(false);
        inputManager.addRawInputListener(new MyRawInputListener());


    }

    // 原始输入监听器
    class MyRawInputListener implements RawInputListener {

        /**
         * 键盘输入事件
         */
        @Override
        public void onKeyEvent(KeyInputEvent evt) {
            int keyCode = evt.getKeyCode();
            boolean isPressed = evt.isPressed();

            //当玩家按下Y键时，输出"Yes!"
            if (isPressed) {
                switch (keyCode) {
                    case KeyInput.KEY_R: {
                        card.rotate(0,0, FastMath.PI/180);
                        break;
                    }
                }
            }
        }

        /**
         * 鼠标输入事件
         */
        @Override
        public void onMouseMotionEvent(MouseMotionEvent evt) {
            int x = evt.getX();
            int y = evt.getY();
            // 打印鼠标的坐标
            System.out.println("x=" + x + " y=" + y);
        }

        public void onMouseButtonEvent(MouseButtonEvent evt) {
        }

        @Override
        public void beginInput() {
        }

        @Override
        public void endInput() {
        }


        public void onJoyAxisEvent(JoyAxisEvent evt) {
        }

        public void onJoyButtonEvent(JoyButtonEvent evt) {
        }

        public void onTouchEvent(TouchEvent evt) {
        }
    }

   /* public static void main(String[] args) {
        // 配置参数
        AppSettings settings = new AppSettings(true);
        settings.setTitle("一个方块");// 标题
        settings.setResolution(1600, 900);// 分辨率

        Cards app = new Cards();
        app.setSettings(settings);
        app.setShowSettings(false);

        app.start();
    }*/
}
