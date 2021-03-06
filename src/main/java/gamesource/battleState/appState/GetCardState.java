package gamesource.battleState.appState;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.asset.AssetNotFoundException;
import com.jme3.collision.CollisionResults;
import com.jme3.cursors.plugins.JmeCursor;
import com.jme3.input.InputManager;
import com.jme3.input.RawInputListener;
import com.jme3.input.event.*;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.ui.Picture;
import gamesource.State.CharacterState.MajorActor;
import gamesource.battleState.battle.Battle;
import gamesource.battleState.card.Card;
import gamesource.battleState.card.CreateCard;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.equipment.CreateEquipment;
import gamesource.battleState.equipment.Equipment;
import gamesource.util.Storage;
import truetypefont.TrueTypeFont;
import truetypefont.TrueTypeKey;
import truetypefont.TrueTypeLoader;

import java.util.ArrayList;

import static gamesource.battleState.card.Card.OCCUPATION.NEUTRAL;
import static gamesource.battleState.card.Card.OCCUPATION.SABER;

// 获取卡牌的state
public class GetCardState extends BaseAppState {
    private SimpleApplication app;
    private Node rootNode = new Node("GetCardState");
    private AssetManager assetManager;
    private AppStateManager stateManager;
    private InputManager inputManager;
    private ViewPort viewPort;
    private Camera camera;
    private ArrayList<Card> card;
    private MyRawInputListener mril;
    private static int getGoldCountAfterThisBattle = 0;
    private boolean canStorage;
    private String world = "";

    private TrueTypeFont font;

    @Override
    protected void initialize(Application application) {
        this.app = (SimpleApplication) getApplication();
        app.getAssetManager().registerLoader(TrueTypeLoader.class, "ttf");
        this.assetManager = app.getAssetManager();
        this.stateManager = app.getStateManager();
        this.inputManager = app.getInputManager();
        this.viewPort = app.getViewPort();
        this.camera = app.getCamera();
        mril = new MyRawInputListener();
        this.card = new ArrayList<Card>() {{
            add(CreateCard.getRandomCard(Math.random() < 0.7 ? SABER : NEUTRAL));
            add(CreateCard.getRandomCard(Math.random() < 0.7 ? SABER : NEUTRAL));
            add(CreateCard.getRandomCard(Math.random() < 0.7 ? SABER : NEUTRAL));
        }};

        // start ----- 卡牌去重
        while (card.get(0).getCardName().equals(card.get(1).getCardName())) {
            card.set(1, CreateCard.getRandomCard(Math.random() < 0.7 ? SABER : NEUTRAL));
        }
        while (card.get(0).getCardName().equals(card.get(2).getCardName())) {
            card.set(2, CreateCard.getRandomCard(Math.random() < 0.7 ? SABER : NEUTRAL));
        }
        while (card.get(1).getCardName().equals(card.get(2).getCardName())) {
            card.set(2, CreateCard.getRandomCard(Math.random() < 0.7 ? SABER : NEUTRAL));
        }
        // end ----- 卡牌去重
        // 创建字体 (例如：楷书)
        TrueTypeKey ttk = new TrueTypeKey("Util/font.ttf", // 字体
                1, // 字形：0 普通、1 粗体、2 斜体
                24);// 字号
        font = (TrueTypeFont) this.app.getAssetManager().loadAsset(ttk);


        Geometry word = font.getBitmapGeom("Please choose one card or equipment. You can use the card in continue battles.", 0, ColorRGBA.Red);

        word.setLocalTranslation(200, 850, 0);


        // 提示获得的金币数量
        Geometry goldCount = font.getBitmapGeom(String.format("Get %d gold", getGoldCountAfterThisBattle), 0, ColorRGBA.Orange);

        goldCount.setLocalTranslation(700, 800, 0);
        rootNode.attachChild(goldCount);

        //   如果想要黑色背景,就取消这段注释
//        Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
//        mat.setColor("Color", new ColorRGBA(0f, 0f, 0f, 0.1f));// 镜面反射时，高光的颜色。
//        // 应用材质。
//        Geometry geom = new Geometry("选卡界面", new Quad(1600, 900));
//        geom.setMaterial(mat);
//
//        // 使物体看起来透明
//        mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
//        geom.setQueueBucket(RenderQueue.Bucket.Transparent);
//        geom.center();

        // 跳过按钮
        Picture skip = new Picture("跳过");
        skip.setWidth(200);
        skip.setHeight(80);
        skip.setImage(app.getAssetManager(), "Util/跳过.png", true);
        skip.setLocalTranslation(1300, 20, -1);
        rootNode.attachChild(skip);

        try {// 因为卡牌没有做完会爆错，所以这里有个TRY-CATCH

            int count = 0;
            for (Card card : card) {
                // 每个卡都有0.1的概率直接获得升级版本
                if (Math.random() < 0.1)
                    card.upgrade();
                card.setImage(app.getAssetManager());//将卡牌添加进Assetmanager
                card.setLocalTranslation(400 + (count++) * 300f, (float) (HandCardsState.height - HandCardsState.cardHeight) / 2, 1);
                rootNode.attachChild(card);

            }
        } catch (AssetNotFoundException e) {
            System.out.println("this bug belong to WYR：" + e.getMessage());
        }

        // 10%概率获得装备
//        if (Math.random() < 0.1f) {
        Equipment equipment = CreateEquipment.getRandomEquipment();
        equipment.setImage(app.getAssetManager());//将装备添加进Assetmanager
        equipment.setLocalTranslation(750f, 700, -1);
        rootNode.attachChild(equipment);
//        }


        rootNode.attachChild(word);

//        rootNode.attachChild(geom);


    }

    public static void setGoldCountAfterThisBattle(int goldCount) {
        getGoldCountAfterThisBattle = goldCount;
    }

    // 主要用来判断鼠标碰撞到guiNode
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

        Ray ray = new Ray(new Vector3f(x, y, 1), dir);
        CollisionResults results = new CollisionResults();
        rootNode.collideWith(ray, results);//检测guinode 中所有图形对象 和 ray 的碰撞

        return results;
    }

    private void enlargeCard(Card img, Card closest) {
        closest.setWidth((float) (HandCardsState.cardWidth * 1.25));
        closest.setHeight((float) (HandCardsState.cardHeight * 1.25));
        Vector3f location = closest.getLocalTranslation();
        closest.setLocalTranslation(location.x, location.y, -1);//通过竖坐标增加来使得图片在前显示
        //放大这个离鼠标最近的图片

        if (img != null) {
            img.setWidth((float) HandCardsState.cardWidth);
            img.setHeight((float) HandCardsState.cardHeight);
            location = img.getLocalTranslation();
            img.setLocalTranslation(location.x, location.y, -1);//图片还原
        }
    }

    private void recoverCard(Card img) {
        img.setWidth((float) HandCardsState.cardWidth);
        img.setHeight((float) HandCardsState.cardHeight);
        Vector3f location = img.getLocalTranslation();
        img.setLocalTranslation(location.x, location.y, -1);
    }

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


        Ray ray = new Ray(new Vector3f(x, y, 10), dir);
        CollisionResults results = new CollisionResults();
        rootNode.collideWith(ray, results);//检测guinode 中所有图形对象 和 ray 的碰撞

        return results;
    }

    private void enlargeEquipment(Equipment img, Equipment closest) {
        closest.setWidth((float) (100));
        closest.setHeight((float) (100));
        Vector3f location = closest.getLocalTranslation();
        closest.setLocalTranslation(location.x, location.y, 1);//通过竖坐标增加来使得图片在前显示
        //放大这个离鼠标最近的图片

        if (img != null) {
            img.setWidth((float) 50);
            img.setHeight((float) 50);
            location = img.getLocalTranslation();
            img.setLocalTranslation(location.x, location.y, 0);//图片还原
        }
    }

    private void recoverEquipment(Equipment img) {
        img.setWidth((float) 50);
        img.setHeight((float) 50);
        Vector3f location = img.getLocalTranslation();
        img.setLocalTranslation(location.x, location.y, 0);
    }

    class MyRawInputListener implements RawInputListener {
        Card last = CreateCard.createCard("null", Card.TYPE.ATTACK);//上次划过的图片

        Equipment lastEquipment = null;
        Geometry des;
        Geometry title;

        /**
         * 键盘输入事件
         */
        @Override
        public void onKeyEvent(KeyInputEvent evt) {
        }

        /**
         * 鼠标输入事件
         */
        @Override
        public void onMouseMotionEvent(MouseMotionEvent evt) {
            JmeCursor jmeCursor = (JmeCursor) app.getAssetManager().loadAsset("Util/cursor/click.cur");
            app.getInputManager().setMouseCursor(jmeCursor);
            CollisionResults results = getGuiCollision(evt);

            if (results.size() > 0) {
                // 获得离射线原点最近的交点所在的图片
                Geometry res = results.getClosestCollision().getGeometry();
                Card closest;
                Equipment closestEquipment;


                if (res instanceof Picture) {
                    // 如果碰到了装备
                    if (res instanceof Equipment) {
                        closestEquipment = (Equipment) res;
                        if (lastEquipment != closestEquipment) {
                            enlargeEquipment(lastEquipment, closestEquipment);//放大选中图片
                            lastEquipment = closestEquipment;
                            if (des != null)
                                des.removeFromParent();
                            if (title != null)
                                title.removeFromParent();
                            des = font.getBitmapGeom(lastEquipment.getDescription(), 0, ColorRGBA.Green);
                            des.setLocalTranslation(200, 600, 0);
                            title = font.getBitmapGeom(lastEquipment.getName(), 0, ColorRGBA.Green);
                            title.setLocalTranslation(650, 650, 0);
                            rootNode.attachChild(title);
                            rootNode.attachChild(des);
                        }
                    } else {
                        // 恢复原状
                        if (lastEquipment != null) {
                            recoverEquipment(lastEquipment);
                            lastEquipment = null;
                            des.removeFromParent();
                            title.removeFromParent();
                        }
                    }

                    // 如果碰到了卡牌
                    if (res instanceof Card) {
                        closest = (Card) res;

                        if (last != closest) {
                            enlargeCard(last, closest);//放大选中图片
                            last = closest;
                        }
                    } else {
                        // 使卡牌恢复原状
                        if (last != null) {
                            recoverCard(last);
                            last = CreateCard.createCard("null", Card.TYPE.ATTACK);
                        }
                    }
                } else {
                    return;
                }
            }
        }

        public void onMouseButtonEvent(MouseButtonEvent evt) {
            //如果是鼠标按下去

            if (evt.isPressed()) {
                System.out.println("Mouse press");
//                System.out.println(evt.getX());
//                System.out.println(evt.getY());
                //获得当前鼠标选中的位置
                CollisionResults guiResults = getGuiCollision(evt);
                System.out.println(guiResults);
                if (guiResults.size() > 0) {
                    // 获得离射线原点最近的交点所在的图片
                    Geometry res = guiResults.getClosestCollision().getGeometry();
                    System.out.println("Click sth");
                    if (res instanceof Card) {
//                        System.out.println((Card)res);
                        System.out.println("choose card");
                        MainRole.getInstance().getCard((Card) res);
                        res.removeFromParent();
                        // 完成操作，删除这个 state
                        finish();
                    } else {
                        if (res instanceof Equipment) {
//                        System.out.println((Card)res);
                            // 将装备加入到玩家手中
                            System.out.println("choose equipment");
                            MainRole.getInstance().getEquipment((Equipment) res);
                            res.removeFromParent();
                            // 完成操作，删除这个 state
                            finish();
                        }
                        if (res.getName().equals("跳过")) {
                            finish();
                        }
                    }
                }
            }
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

    // 完成卡牌选择
    public void finish() {
        this.onDisable();
    }

    @Override
    protected void cleanup(Application application) {

    }

    @Override
    protected void onEnable() {
        Storage.save();
        canStorage = true;
//        app.getFlyByCamera().setDragToRotate(false);
        app.getStateManager().getState(MajorActor.class).mouseChange();

        // 这里是触发宝箱的逻辑
//        if(stateManager.detach(stateManager.getState(FirstState.class))){
//            world = "first";
//        }
//        else if(stateManager.detach(stateManager.getState(SecondState.class))){
//            world = "second";
//        }else if(stateManager.detach(stateManager.getState(ThirdState.class))){
//            world = "third";
//        }else if(stateManager.detach(stateManager.getState(ForthState.class))){
//            world = "forth";
//        }else if(stateManager.detach(stateManager.getState(FifthState.class))){
//            world = "fifth";
//        }

        stateManager.detach(stateManager.getState(EnemyState.class));
        stateManager.detach(stateManager.getState(HandCardsState.class));
        stateManager.detach(stateManager.getState(DecksState.class));
        stateManager.detach(stateManager.getState(LeadingActorState.class));
        try {
            FilterPostProcessor fpp = stateManager.getState(BattleBackGroundState.class).getFpp();
            fpp.removeAllFilters();
            app.getViewPort().removeProcessor(fpp);
        } catch (NullPointerException npe) {
            System.out.println("from box");
//            canStorage = false;
        }
        stateManager.detach(stateManager.getState(BattleBackGroundState.class));
        app.getGuiNode().attachChild(this.rootNode);
        app.getInputManager().addRawInputListener(mril);

    }

    @Override
    protected void onDisable() {
        MainRole.getInstance().getGold(getGoldCountAfterThisBattle);
//        // TODO 通过完善宝箱获取卡来修复这个bug
//        if (canStorage)
        Storage.save();
        this.rootNode.removeFromParent();
        app.getInputManager().removeRawInputListener(mril);
        app.getStateManager().detach(app.getStateManager().getState(BattleState.class));
        app.getStateManager().detach(app.getStateManager().getState(Battle.class));
        app.getStateManager().getState(MajorActor.class).mouseChange();
        //        switch (world){
        //        switch (world){
//            case "first":
//                app.getStateManager().attach(stateManager.getState(FirstState.class));
//                break;
//            case "second":
//                app.getStateManager().attach(stateManager.getState(SecondState.class));
//                break;
//            case "third":
//                app.getStateManager().attach(stateManager.getState(ThirdState.class));
//                break;
//            case "forth":
//                app.getStateManager().attach(stateManager.getState(ForthState.class));
//                break;
//            case "fifth":
//                app.getStateManager().attach(stateManager.getState(FifthState.class));
//                break;
//            default:
//                break;
//        }
    }
}
