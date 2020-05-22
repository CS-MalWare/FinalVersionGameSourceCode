package gamesource.State.musicState;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.audio.AudioData;
import com.jme3.audio.AudioNode;
import com.jme3.scene.Node;

public class FifthBackMusic  extends BaseAppState {
    private AudioNode BackGround;

    private SimpleApplication app;

    private Node node = new Node("music");

    @Override
    protected void initialize(Application application) {
        app = (SimpleApplication) application;
        initAudio();
    }

    private void initAudio() {


        BackGround = new AudioNode(app.getAssetManager(), "Sound/fifth/fifth.wav", AudioData.DataType.Stream);
        BackGround.setLooping(true);
        BackGround.setPositional(false);
        BackGround.setVolume(3f);
        node.attachChild(BackGround);

        BackGround.setLooping(true);
    }


    @Override
    protected void cleanup(Application application) {

    }

    @Override
    protected void onEnable() {
        app.getRootNode().attachChild(this.node);
        BackGround.play();
    }

    @Override
    protected void onDisable() {
        this.node.removeFromParent();
        BackGround.stop();
    }
}
