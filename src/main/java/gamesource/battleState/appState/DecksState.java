package gamesource.battleState.appState;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.audio.AudioData;
import com.jme3.audio.AudioNode;
import com.jme3.collision.CollisionResults;
import com.jme3.input.RawInputListener;
import com.jme3.input.event.*;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.ui.Picture;
import gamesource.battleState.card.Card;
import gamesource.battleState.character.Enemy;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.equipment.Equipment;
import truetypefont.TrueTypeFont;
import truetypefont.TrueTypeKey;
import truetypefont.TrueTypeLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

// 牌组相关的模组加载
public class DecksState extends BaseAppState {
    private static DecksState instance = null;

    private Picture drawDeckPic;  // 抽牌堆图片
    private Picture exhaustDeckPic; // 消耗堆图片
    private Picture dropDeckPic; // 弃牌堆图片
    private SimpleApplication app;
    private Node rootNode;

    private ArrayList<AudioNode> audioNodes;
    private int drawNum = 10;
    private int dropNum = 10;
    private int exhaustNum = 1;
    private Geometry drawText; // 抽牌堆提示文字
    private Geometry dropText; // 弃牌堆提示文字
    private Geometry exhaustText; // 消耗堆提示文字
    public final static int PLAIN = 0;// 普通
    public final static int BOLD = 1;// 粗体
    public final static int ITALIC = 2;// 斜体

    public final static int FONT_SIZE = 26;

    private int[] positionX = new int[]{74, 1505, 1470}; //抽牌,弃牌,消耗堆的顺序
    private int[] positionY = new int[]{73, 73, 180};


    private ArrayList<Card> drawDeck = new ArrayList<Card>(); //抽牌堆
    private ArrayList<Card> exhaustDeck = new ArrayList<Card>(); // 消耗堆
    private ArrayList<Card> dropDeck = new ArrayList<Card>();  // 弃牌堆

    private ArrayList<Card> showDeck = new ArrayList<Card>();

    TrueTypeFont font;

    private MyInputListener myActionListener;

    public final static String CLICK = "CLICK";

    private Picture endTurn;

//    BitmapFont fnt;

    public static DecksState getInstance() {
        return instance;
    }

    @Override
    protected void initialize(final Application app) {
        rootNode = new Node("DeckNode");
        this.app = (SimpleApplication) getApplication();
        drawDeckPic = new Picture("抽牌堆");
        drawDeckPic.setImage(app.getAssetManager(), "Util/抽牌堆.png", true);
        exhaustDeckPic = new Picture("消耗堆");
        exhaustDeckPic.setImage(app.getAssetManager(), "Util/消耗堆.png", true);
        dropDeckPic = new Picture("弃牌堆");
        dropDeckPic.setImage(app.getAssetManager(), "Util/弃牌堆.png", true);
        endTurn = new Picture("结束回合");
        endTurn.setImage(app.getAssetManager(), "Util/结束.png", true);


        drawDeckPic.setPosition(30, 20);
        dropDeckPic.setPosition(1430, 20);
        exhaustDeckPic.setPosition(1430, 150);
        endTurn.setPosition(1220, 220);

        drawDeckPic.setHeight(140);
        drawDeckPic.setWidth(140);
        dropDeckPic.setHeight(140);
        dropDeckPic.setWidth(140);
        exhaustDeckPic.setHeight(100);
        exhaustDeckPic.setWidth(100);
        endTurn.setWidth(200);
        endTurn.setHeight(80);

        rootNode.attachChild(drawDeckPic);
        rootNode.attachChild(dropDeckPic);
        rootNode.attachChild(exhaustDeckPic);
        rootNode.attachChild(endTurn);

//        fnt = app.getAssetManager().loadFont("Interface/Fonts/Default.fnt");

        this.app.getAssetManager().registerLoader(TrueTypeLoader.class, "ttf");

        // 创建字体 (例如：楷书)
        TrueTypeKey ttk = new TrueTypeKey("Util/MTCORSVA.TTF", // 字体
                BOLD, // 字形：0 普通、1 粗体、2 斜体
                FONT_SIZE);// 字号

        font = (TrueTypeFont) this.app.getAssetManager().loadAsset(ttk);


        // 创建文字
        drawText = font.getBitmapGeom(drawNum + "", 0, ColorRGBA.Red);
        drawText.setLocalTranslation(positionX[0], positionY[0], 1);
        rootNode.attachChild(drawText);

        dropText = font.getBitmapGeom(dropNum + "", 0, ColorRGBA.Red);
        dropText.setLocalTranslation(positionX[1], positionY[1], 1);
        rootNode.attachChild(dropText);

        exhaustText = font.getBitmapGeom(exhaustNum + "", 0, ColorRGBA.Red);
        exhaustText.setLocalTranslation(positionX[2], positionY[2], 1);
        rootNode.attachChild(exhaustText);

//        drawDeck.add(new ConeFlame(true));
//        drawDeck.add(new ConeGold());
//        drawDeck.add(new Whirlpool());
//        drawDeck.add(new TheKissOfDeath());
//
//        dropDeck.add(new Slash());
//        dropDeck.add(new SoilSlash());
//
//        exhaustDeck.add(new LightSlash());


        myActionListener = new MyInputListener();
        instance = this;

        // 设置音效
        audioNodes = new ArrayList<AudioNode>() {{
            add(new AudioNode(app.getAssetManager(), "Sound/Attack/史莱姆攻击.wav", AudioData.DataType.Buffer));
            add(new AudioNode(app.getAssetManager(), "Sound/Skill/史莱姆技能.wav", AudioData.DataType.Buffer));
            add(new AudioNode(app.getAssetManager(), "Sound/Dead/史莱姆死亡.wav", AudioData.DataType.Buffer));
            add(new AudioNode(app.getAssetManager(), "Sound/Attack/狼人攻击.wav", AudioData.DataType.Buffer));
            add(new AudioNode(app.getAssetManager(), "Sound/Skill/狼人技能.wav", AudioData.DataType.Buffer));
            add(new AudioNode(app.getAssetManager(), "Sound/Dead/狼人死亡.wav", AudioData.DataType.Buffer));
            add(new AudioNode(app.getAssetManager(), "Sound/Attack/机器人攻击.wav", AudioData.DataType.Buffer));
            add(new AudioNode(app.getAssetManager(), "Sound/Skill/机器人技能.wav", AudioData.DataType.Buffer));
            add(new AudioNode(app.getAssetManager(), "Sound/Dead/机器人死亡.wav", AudioData.DataType.Buffer));
            add(new AudioNode(app.getAssetManager(), "Sound/Attack/龙攻击.wav", AudioData.DataType.Buffer));
            add(new AudioNode(app.getAssetManager(), "Sound/Skill/龙技能.wav", AudioData.DataType.Buffer));
            add(new AudioNode(app.getAssetManager(), "Sound/Dead/龙死亡.wav", AudioData.DataType.Buffer));
            add(new AudioNode(app.getAssetManager(), "Sound/Attack/Ace攻击.wav", AudioData.DataType.Buffer));
            add(new AudioNode(app.getAssetManager(), "Sound/Attack/Faker攻击.wav", AudioData.DataType.Buffer));
            add(new AudioNode(app.getAssetManager(), "Sound/Attack/Zac攻击.wav", AudioData.DataType.Buffer));
            add(new AudioNode(app.getAssetManager(), "Sound/Skill/Faker技能.wav", AudioData.DataType.Buffer));
            add(new AudioNode(app.getAssetManager(), "Sound/Skill/Ace技能.wav", AudioData.DataType.Buffer));
            add(new AudioNode(app.getAssetManager(), "Sound/Skill/Zac技能.wav", AudioData.DataType.Buffer));
            add(new AudioNode(app.getAssetManager(), "Sound/Dead/Faker死亡.wav", AudioData.DataType.Buffer));
            add(new AudioNode(app.getAssetManager(), "Sound/Dead/Zac死亡.wav", AudioData.DataType.Buffer));
            add(new AudioNode(app.getAssetManager(), "Sound/Dead/Ace死亡.wav", AudioData.DataType.Buffer));
            add(new AudioNode(app.getAssetManager(), "Sound/Attack/震网攻击.wav", AudioData.DataType.Buffer));
            add(new AudioNode(app.getAssetManager(), "Sound/Skill/震网技能.wav", AudioData.DataType.Buffer));
            add(new AudioNode(app.getAssetManager(), "Sound/Dead/震网死亡.wav", AudioData.DataType.Buffer));
        }};
        System.out.println(audioNodes.size());

        // 设置声音的基本属性
        for (AudioNode an : audioNodes) {
            an.setLooping(false);
            an.setPositional(false);
            an.setVolume(2);
            rootNode.attachChild(an);
        }
    }

    // 为所有card类初始化图片
    public void setImages(SimpleApplication app) {
        for (Card card : drawDeck) {
            card.setImage(app.getAssetManager());
        }

        for (Card card : dropDeck) {
            card.setImage(app.getAssetManager());
        }
        for (Card card : exhaustDeck) {
            card.setImage(app.getAssetManager());
        }
        this.updateNum();
    }

    @Override
    protected void cleanup(Application app) {
        this.drawDeck.clear();
        this.dropDeck.clear();
        this.exhaustDeck.clear();
    }

    @Override
    protected void onEnable() {

        app.getGuiNode().attachChild(this.rootNode);
        app.getInputManager().addRawInputListener(myActionListener);
        MainRole.getInstance().startBattle();
        int count = 0;
        for (Equipment equipment : MainRole.getInstance().getEquipments()) {
            int x = count % 20;
            int y = count / 20;
            equipment.setPosition(70 + x * 70, 830 - y * 100);
            rootNode.attachChild(equipment);
            count++;
        }
        this.setImages(app);
        for (Enemy enemy : EnemyState.getInstance().getEnemies()) {
            enemy.updateHints();
        }
        MainRole.getInstance().startTurn();
    }

    @Override
    protected void onDisable() {
        this.rootNode.removeFromParent();

        for (Equipment equipment : MainRole.getInstance().getEquipments()) {
            equipment.removeFromParent();
        }
        app.getInputManager().removeRawInputListener(myActionListener);
    }


    public int getDrawNum() {
        return drawNum;
    }

    // 更新抽牌堆提示
    public void updateDrawNum() {
        this.drawNum = drawDeck.size();
        this.drawText.removeFromParent();
        this.drawText = font.getBitmapGeom(drawNum + "", 0, ColorRGBA.Red);
        if (drawNum >= 10) {
            this.drawText.setLocalTranslation(positionX[0], positionY[0], 2);
        } else {
            this.drawText.setLocalTranslation(positionX[0] + 4, positionY[0], 2);
        }
        this.rootNode.attachChild(drawText);
    }

    public int getDropNum() {
        return dropNum;

    }

    // 更新弃牌堆提示
    public void updateDropNum() {
        this.dropNum = dropDeck.size();
        this.dropText.removeFromParent();
        this.dropText = font.getBitmapGeom(dropNum + "", 0, ColorRGBA.Red);
        if (dropNum >= 10) {
            this.dropText.setLocalTranslation(positionX[1], positionY[1], 2);
        } else {
            this.dropText.setLocalTranslation(positionX[1] + 5, positionY[1], 2);
        }
        this.rootNode.attachChild(dropText);
    }

    public int getExhaustNum() {

        return exhaustNum;
    }

    // 更新消耗堆提示
    public void updateExhaustNum() {
        this.exhaustNum = exhaustDeck.size();
        this.exhaustText.removeFromParent();
        this.exhaustText = font.getBitmapGeom(exhaustNum + "", 0, ColorRGBA.Red);
        if (dropNum < 10) {
            this.exhaustText.setLocalTranslation(positionX[2], positionY[2], 2);
        } else {
            this.exhaustText.setLocalTranslation(positionX[2] - 3, positionY[2], 2);
        }
        this.rootNode.attachChild(exhaustText);
    }

    // 更新全部牌堆提示
    public void updateNum() {
        this.updateDrawNum();
        this.updateDropNum();
        this.updateExhaustNum();
    }

    // 抽卡
    public ArrayList<Card> drawCard(int num) {
        int drawNum = drawDeck.size();
        ArrayList<Card> draws = new ArrayList<Card>();
        // 抽牌堆中的牌还没抽光的时候
        if (drawNum >= num) {
            for (int i = 0; i < num; i++) {
                draws.add(drawDeck.remove(0));
            }
        } else { //先洗牌再抽
            // 先抽光手牌堆中的牌
            for (int i = 0; i < drawNum; i++) {
                draws.add(drawDeck.remove(0));
            }
            // 将弃牌堆中的牌加入抽牌堆

            drawDeck.addAll(dropDeck);
            dropDeck.clear();

//            for(int i=0;i<dropDeck.size();i++){
//                drawDeck.add(dropDeck.remove(0));
//            }
//            System.out.println(i);
            Collections.shuffle(drawDeck);
            for (int i = 0; i < num - drawNum; i++) {
                if (drawDeck.size() > 0)
                    draws.add(drawDeck.remove(0));
            }
        }
        this.updateNum();
        return draws;
    }

    // 随机加入抽牌堆
    public void addToDraw(Card... cards) {
        if (cards.length == 1) {
            Random random = new Random();
            int pos = random.nextInt(drawDeck.size());
            cards[0].reset();
            this.drawDeck.add(pos, cards[0]);
        } else {
            for (Card card : cards) {
                card.reset();
                drawDeck.add(card);
                card.removeFromParent();
            }
        }
        this.updateDrawNum();
    }

    // 加入抽牌堆
    public void addToDraw(ArrayList<Card> cards) {

        for (Card card : cards) {
            card.reset();
            drawDeck.add(card);
            card.removeFromParent();
        }

        this.updateDrawNum();
    }

    // 加入消耗堆
    public void addToExhaust(Card... cards) {
        for (Card card : cards) {
            card.reset();
            exhaustDeck.add(card);
            card.removeFromParent();
        }
        this.updateExhaustNum();
    }

    // 加入弃牌堆
    public void addToDrop(Card... cards) {
        for (Card card : cards) {
            card.reset();
            dropDeck.add(card);
            card.removeFromParent();
        }
        this.updateDropNum();
    }


    public ArrayList<Card> getDrawDeck() {
        return drawDeck;
    }

    public ArrayList<Card> getExhaustDeck() {
        return exhaustDeck;
    }

    public ArrayList<Card> getDropDeck() {
        return dropDeck;
    }

    // 展示卡组
    public void showCards(ArrayList<Card> cards) {
        this.hideCards();
        for (int index = 0; index < cards.size(); index++) {
            int i = index / 8 + 1; // 1-行号
            int j = index % 8 + 1; // 1-6
            int x = 100 + (j - 1) * 180;
            int y = 900 - i * 200;
            Card card = cards.get(index).clone(true);
            card.setImage(app.getAssetManager(), card.getPath(), true);
            card.setWidth(150);
            card.setHeight(200);
            card.setPosition(x, y);
            rootNode.attachChild(card);
            showDeck.add(card);
        }
    }

    public void hideCards() {
        for (Card card : this.showDeck) {
            card.removeFromParent();
        }
        this.showDeck.clear();
    }

    // 获取碰撞
    private CollisionResults getGuiCollision(MouseButtonEvent evt) {
        int x = evt.getX();//得到鼠标的横坐标
        int y = evt.getY();//得到鼠标的纵坐标
        Camera cam = app.getCamera();//获得摄像机引用
        Vector2f screenCoord = new Vector2f(x, y);
        Vector3f worldCoord = cam.getWorldCoordinates(screenCoord, 1f);
        Vector3f worldCoord2 = cam.getWorldCoordinates(screenCoord, 0f);
        //通过得到鼠标位置，生成一个二维向量，然后通过设定不同的竖坐标，获得应该的视线方向
// 然后计算视线方向
        Vector3f dir = worldCoord.subtract(worldCoord2);
        dir.normalizeLocal();//获得该方向单位向量

// 生成射线
//        Node guiNode = app.getGuiNode();//GUInode 包含了所有图形对象

        Ray ray = new Ray(new Vector3f(x, y, 10), dir);
        CollisionResults results = new CollisionResults();
        rootNode.collideWith(ray, results);//检测guinode 中所有图形对象 和 ray 的碰撞

        return results;
    }

    // 获取碰撞
    private CollisionResults getGuiCollision(MouseMotionEvent evt) {
        int x = evt.getX();//得到鼠标的横坐标
        int y = evt.getY();//得到鼠标的纵坐标
        Camera cam = app.getCamera();//获得摄像机引用
        Vector2f screenCoord = new Vector2f(x, y);
        Vector3f worldCoord = cam.getWorldCoordinates(screenCoord, 1f);
        Vector3f worldCoord2 = cam.getWorldCoordinates(screenCoord, 0f);
        //通过得到鼠标位置，生成一个二维向量，然后通过设定不同的竖坐标，获得应该的视线方向
// 然后计算视线方向
        Vector3f dir = worldCoord.subtract(worldCoord2);
        dir.normalizeLocal();//获得该方向单位向量

// 生成射线
//        Node guiNode = app.getGuiNode();//GUInode 包含了所有图形对象

        Ray ray = new Ray(new Vector3f(x, y, 10), dir);
        CollisionResults results = new CollisionResults();
        rootNode.collideWith(ray, results);//检测guinode 中所有图形对象 和 ray 的碰撞

        return results;
    }


    class MyInputListener implements RawInputListener {
        private Boolean isDrawDeckShow = false;
        private Boolean isDropDeckShow = false;
        private Boolean isExhuastDeckShow = false;
        private String enemyActionAudio = "";
        TrueTypeKey ttk = new TrueTypeKey("Util/MTCORSVA.TTF", // 字体
                PLAIN, // 字形：0 普通、1 粗体、2 斜体
                FONT_SIZE);// 字号
        TrueTypeFont font = (TrueTypeFont) app.getAssetManager().loadAsset(ttk);

        private Geometry text;

        @Override
        public void beginInput() {

        }

        @Override
        public void endInput() {

        }

        @Override
        public void onJoyAxisEvent(JoyAxisEvent evt) {

        }

        @Override
        public void onJoyButtonEvent(JoyButtonEvent evt) {

        }

        @Override
        public void onMouseMotionEvent(MouseMotionEvent evt) {
            CollisionResults guiResults = getGuiCollision(evt);
            if (guiResults.size() > 0) {
                Geometry res = guiResults.getClosestCollision().getGeometry();
                if (res instanceof Equipment) {
                    if (text != null) {
                        text.removeFromParent();
                    }
                    text = font.getBitmapGeom(String.format("%s", ((Equipment) res).getDescription()), 0, ColorRGBA.Red);
                    text.setLocalTranslation(50, 800, 1);
                    rootNode.attachChild(text);

                }
            } else {
                if (text != null)
                    text.removeFromParent();
            }
        }

        @Override
        public void onMouseButtonEvent(MouseButtonEvent evt) {
            if (evt.isPressed()) {
                CollisionResults guiResults = getGuiCollision(evt);
                if (guiResults.size() > 0) {
                    Geometry res = guiResults.getClosestCollision().getGeometry();

                    // 根据图片名字进行相应的鼠标操作
                    switch (res.getName()) {
                        // 展示牌堆操作

                        case "抽牌堆":
                            if (!isDrawDeckShow) showCards(drawDeck);
                            else hideCards();
                            isDrawDeckShow = !isDrawDeckShow;
                            isDropDeckShow = false;
                            isExhuastDeckShow = false;
                            break;
                        case "弃牌堆":
                            if (!isDropDeckShow) showCards(dropDeck);
                            else hideCards();
                            isDropDeckShow = !isDropDeckShow;
                            isDrawDeckShow = false;
                            isExhuastDeckShow = false;
                            break;
                        case "消耗堆":
                            if (!isExhuastDeckShow) showCards(exhaustDeck);
                            else hideCards();
                            isExhuastDeckShow = !isExhuastDeckShow;
                            isDrawDeckShow = false;
                            isDropDeckShow = false;
                            break;

                            // 结束回合操作
                        case "结束回合":

                            boolean flag= false;
                            do{
                                MainRole.getInstance().endTurn();
                                app.getStateManager().getState(LeadingActorState.class).updateHints();

                                ArrayList<Enemy> enemies = app.getStateManager().getState(EnemyState.class).getEnemies();
                                // 所有敌人开始行动
                                int i = 0;
                                ArrayList<AnimControl> animControls = app.getStateManager().getState(EnemyState.class).getAnimControls();
                                ArrayList<AnimChannel> animChannels = app.getStateManager().getState(EnemyState.class).getAnimChannels();
                                for (Enemy enemy : enemies) {
                                    if(enemy.getStun().getDuration()==0){
                                        enemy.startTurn();
                                        EnemyState.getInstance().displayAction();

                                        enemyActionAudio = enemy.enemyAction();

                                        // 根据敌人的行动提示,播放一些音效
                                        switch (enemyActionAudio){
                                            case "slime attack":
                                                audioNodes.get(0).playInstance();
                                                break;
                                            case "slime skill":
                                                audioNodes.get(1).playInstance();
                                                break;
                                            case "wolfman attack":
                                                audioNodes.get(3).playInstance();
                                                break;
                                            case "wolfman skill":
                                                audioNodes.get(4).playInstance();
                                                break;
                                            case "robot attack":
                                                audioNodes.get(6).playInstance();
                                                break;
                                            case "robot skill":
                                                audioNodes.get(7).playInstance();
                                                break;
                                            case "dragon attack":
                                                audioNodes.get(9).playInstance();
                                                break;
                                            case "dragon skill":
                                                audioNodes.get(10).playInstance();
                                                break;
                                            case "ace attack":
                                                audioNodes.get(12).playInstance();
                                                break;
                                            case "faker attack":
                                                audioNodes.get(13).playInstance();
                                                break;
                                            case "zac attack":
                                                audioNodes.get(14).playInstance();
                                                break;
                                            case "faker skill":
                                                audioNodes.get(15).playInstance();
                                                break;
                                            case "ace skill":
                                                audioNodes.get(16).playInstance();
                                                break;
                                            case "zac skill":
                                                audioNodes.get(17).playInstance();
                                                break;
                                            case "boss attack":
                                                audioNodes.get(21).playInstance();
                                                break;
                                            case "boss skill":
                                                audioNodes.get(22).playInstance();
                                                break;
                                            default:
                                                break;
                                        }
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    Thread.sleep(500);
                                                } catch (InterruptedException e) {
                                                }
                                                try {
                                                    app.getCamera().setLocation(new Vector3f(0.15f,0.15f,10.5f));
                                                    Thread.sleep(70);
                                                    System.out.println("changeCam");
                                                } catch (Exception e) {
                                                }try {
                                                    app.getCamera().setLocation(new Vector3f(-0.15f,-0.15f,10.5f));
                                                    Thread.sleep(70);
                                                } catch (Exception e) {
                                                }try {
                                                    app.getCamera().setLocation(new Vector3f(0.15f,-0.15f,10.5f));
                                                    Thread.sleep(70);
                                                } catch (Exception e) {
                                                }try {
                                                    app.getCamera().setLocation(new Vector3f(-0.15f,0.15f,10.5f));
                                                    Thread.sleep(70);
                                                } catch (Exception e) {
                                                }try {
                                                    app.getCamera().setLocation(new Vector3f(0,0,10.5f));
                                                    Thread.sleep(70);
                                                } catch (Exception e) {
                                                }
                                            }
                                        }).start();
                                    }
                                    enemy.endTurn();

                                    // 更新敌人和主角的格挡和血量
                                    app.getStateManager().getState(EnemyState.class).updateHints(true);
                                    app.getStateManager().getState(LeadingActorState.class).updateHints();

                                    // 尝试播放动画
                                    if(i<animChannels.size()) {
                                        animChannels.get(i).setAnim(animControls.get(i).getAnimationNames().toArray()[0].toString());
//                                EnemyState.getInstance().getAnimChannels().get(i).setAnim("代命名");

                                        animChannels.get(i).setLoopMode(LoopMode.DontLoop);

                                        try {
                                            animChannels.get(i).setAnim("attack");
                                            animChannels.get(i).setLoopMode(LoopMode.DontLoop);
                                        } catch (Exception e) {

                                        }
                                    }

                                    i++;
                                }
                                // 停顿一下,假装在加载（yr巨佬还是高明）
                                try {
                                    Thread.sleep(20);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                // 敌人行动完后,主角可以行动
                                MainRole.getInstance().startTurn();
                                app.getStateManager().getState(HandCardsState.class).setCardUsedCount(0);
                                app.getStateManager().getState(LeadingActorState.class).updateHints();
                                LeadingActorState.getAnimChannel().setAnim("idle");
                                LeadingActorState.getAnimChannel().setLoopMode(LoopMode.Loop);
                                // 判断是否眩晕,如果眩晕,则敌人再次行动
                                if (MainRole.getInstance().getStun().getDuration() > 0) {
                                    flag = true;
                                    continue;
                                }
                                // 将本回合内中已打出的牌清0
                                flag = false;
                                EnemyState.getInstance().displayAction();

                            } while (flag);
                            ArrayList<AnimChannel> animChannels = app.getStateManager().getState(EnemyState.class).getAnimChannels();
                            // 尝试在敌人攻击后, 将状态恢复到idle
                            for (final AnimChannel animChannel : animChannels) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                        }
                                        try {
                                            animChannel.setAnim("idle");
                                            animChannel.setLoopMode(LoopMode.Loop);
                                        } catch (Exception e) {
                                        }
                                    }
                                }).start();
                            }

                            break;
                        default:
                            isExhuastDeckShow = false;
                            isDrawDeckShow = false;
                            isDropDeckShow = false;
                            hideCards();
                    }

                    // 使得重复点击不会出bug
                    if (res == drawText) {
                        if (!isDrawDeckShow) showCards(drawDeck);
                        else hideCards();
                        isDrawDeckShow = !isDrawDeckShow;
                        isDropDeckShow = false;
                        isExhuastDeckShow = false;
                    } else if (res == dropText) {
                        if (!isDropDeckShow) showCards(dropDeck);
                        else hideCards();
                        isDropDeckShow = !isDropDeckShow;
                        isDrawDeckShow = false;
                        isExhuastDeckShow = false;
                    } else if (res == exhaustText) {
                        if (!isExhuastDeckShow) showCards(exhaustDeck);
                        else hideCards();
                        isExhuastDeckShow = !isExhuastDeckShow;
                        isDrawDeckShow = false;
                        isDropDeckShow = false;
                    }
                } else {
//                    System.out.println("nothing");
                    isExhuastDeckShow = false;
                    isDrawDeckShow = false;
                    isDropDeckShow = false;
                    hideCards();
                }
            }
        }

        @Override
        public void onKeyEvent(KeyInputEvent evt) {

        }

        @Override
        public void onTouchEvent(TouchEvent evt) {

        }
    }


}
