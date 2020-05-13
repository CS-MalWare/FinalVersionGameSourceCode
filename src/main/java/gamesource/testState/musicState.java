package gamesource.testState;

import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioData;
import com.jme3.audio.AudioNode;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

public class musicState extends SimpleApplication {

    public static void main(String[] args) {
        // 启动程序
        musicState app = new musicState();
        app.start();
    }


    /**
     * 环境音效
     */
    private AudioNode audioNature;

    /**
     * 开枪
     */
    private final static String SHOOT = "Shoot";

    public final static String stop="STOP";
    public final static Trigger STOPMUSIC = new KeyTrigger(KeyInput.KEY_SPACE);

    private int STOP=0;

    @Override
    public void simpleInitApp() {
        flyCam.setMoveSpeed(40);

        /**
         * 制作一个蓝色的小方块，用来表示音源的位置。
         */
        Geometry geom = new Geometry("Player", new Box(1, 1, 1));
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);
        rootNode.attachChild(geom);
        cam.setLocation(new Vector3f(35,0,0));

        inputManager.addMapping(stop, STOPMUSIC);
        inputManager.addListener(new stopmusic(),stop);

        /**
         * 初始化用户输入
         */

        /**
         * 初始化音效
         */
        initAudio();

    }

    /**
     * 保持音频监听器（Audio Listener）和摄像机（Camera）同步运动。
     */
    @Override
    public void simpleUpdate(float tpf) {
        listener.setLocation(cam.getLocation());
        listener.setRotation(cam.getRotation());
    }

    /**
     * 创建两个AudioNode作为音源，并添加到场景中。
     */
    private void initAudio() {
        /**
         * 创建一个“枪声”音源，用户点击鼠标时会发出枪声。
         */

        /**
         * 创建一个自然音效（海潮声），这个音源会一直循环播放。
         */
        audioNature = new AudioNode(assetManager, "Sound/secondWorld/waterfallAndBird1.wav", AudioData.DataType.Stream);
        audioNature.setLooping(true); // 循环播放
        audioNature.setVolume(3);// 音量
        audioNature.setLocalTranslation(new Vector3f(35,0,0));
        //audioNature.setMaxDistance(5f);
        audioNature.setRefDistance(2.4f);
        audioNature.setPositional(true);// 设置为定位音源，这将产生3D音效，玩家能够通过耳机来辨别音源的位置。
        // 将音源添加到场景中
        rootNode.attachChild(audioNature);

        audioNature.play(); // 持续播放
    }

    class stopmusic implements ActionListener {
        @Override
        public void onAction(String name,boolean isPressed,float tpf) {
            if(name.equals(stop)&&isPressed) {
                if(STOP==0) {

                    audioNature.pause();
                    STOP=1;
                }else{
                    audioNature.play();
                    STOP=0;
                }
            }
        }

        }

    /**
     * 定义“开枪(SHOOT)”动作，用户点击鼠标左键时触发此动作。
     */

    /**
     * 定义一个监听器，用来处理开枪动作，当玩家点击鼠标左键时发出枪声。
     */


}