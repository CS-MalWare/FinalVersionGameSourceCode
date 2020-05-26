package gamesource.testState;

import com.jme3.app.SimpleApplication;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import truetypefont.TrueTypeFont;
import truetypefont.TrueTypeKey;
import truetypefont.TrueTypeLoader;

public class FontTest extends SimpleApplication {

    public static void main(String[] args) {
        // 启动程序
        FontTest app = new FontTest();
        app.start();
    }

    // 字形
    public final static int PLAIN = 0;// 普通
    public final static int BOLD = 1;// 粗体
    public final static int ITALIC = 2;// 斜体

    // 字号
    public final static int FONT_SIZE = 64;

    @Override
    public void simpleInitApp() {
        flyCam.setMoveSpeed(50);
        // 注册ttf字体资源加载器
        assetManager.registerLoader(TrueTypeLoader.class, "ttf");

        // 创建字体 (例如：楷书)
        TrueTypeKey ttk=new TrueTypeKey("Util/MTCORSVA.TTF",0,64);

        TrueTypeFont font = (TrueTypeFont) assetManager.loadAsset(ttk);


        // 在屏幕中央显示一首五言绝句。
        String[] poem = { "Hello", "天气晚来秋", "明月松间照", "清泉石上流" };

        // 计算坐标
        float x = 0.5f * (cam.getWidth() - FONT_SIZE * 5);
        float y = 0.5f * (cam.getHeight() + FONT_SIZE * 2);

        String content=String.format("HP: %s/%s", 12,12);
        Geometry text=font.getBitmapGeom(content,0,ColorRGBA.Red);
        text.setLocalTranslation(1.7f, 1.7f,0);
            rootNode.attachChild(text);

    }

}