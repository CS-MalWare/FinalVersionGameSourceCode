package gamesource.battleState.appState;

import com.jme3.post.FilterPostProcessor;
import com.jme3.ui.Picture;
import gamesource.battleState.battle.Battle;
import gamesource.battleState.card.Card;
import gamesource.battleState.card.CreateCard;
import gamesource.battleState.character.MainRole;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.asset.AssetNotFoundException;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.InputManager;
import com.jme3.input.RawInputListener;
import com.jme3.input.event.*;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import gamesource.battleState.equipment.CreateEquipment;
import gamesource.battleState.equipment.Equipment;

import java.util.ArrayList;

import static gamesource.battleState.card.Card.OCCUPATION.NEUTRAL;
import static gamesource.battleState.card.Card.OCCUPATION.SABER;

// 基本作用同GetCardState
public class GetEquipmentState extends BaseAppState {
    private SimpleApplication app;
    private Node rootNode = new Node("GetCardState");
    private AssetManager assetManager;
    private AppStateManager stateManager;
    private InputManager inputManager;
    private ViewPort viewPort;
    private Camera camera;
    private ArrayList<Equipment> equipments;
    private MyRawInputListener mril;
    private static int getGoldCountAfterThisBattle = 0;

    @Override
    protected void initialize(Application application) {
        this.app = (SimpleApplication) getApplication();
        this.assetManager = app.getAssetManager();
        this.stateManager = app.getStateManager();
        this.inputManager = app.getInputManager();
        this.viewPort = app.getViewPort();
        this.camera = app.getCamera();
        mril = new MyRawInputListener();

        ArrayList<Equipment> alreadyHave = MainRole.getInstance().getEquipments();
        this.equipments = new ArrayList<Equipment>();
        int t = 0;
        while (t < 3) {
            Equipment random = CreateEquipment.getRandomEquipment();
            boolean flag = true;
            for (Equipment equipment : alreadyHave) {
                if (equipment.getName().equals(random.getName())) {
                    flag = false;
                    break;
                }
            }

            for (Equipment equipment : equipments) {
                if (equipment.getName().equals(random.getName())) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                t++;
                equipments.add(random);
            }
        }
        BitmapFont fnt = app.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
        BitmapText word = new BitmapText(fnt, false);//显示的文字
        word.setText("Please choose one equipment. You can use the equipment in continue battles");
        word.setSize(0.2f);
//        word.setColor(ColorRGBA.Black);
        word.setLocalTranslation(-2.5f, -2.5f, 0);

        // 提示获得的金币数量
        BitmapText goldCount = new BitmapText(fnt, false);//显示的文字
        goldCount.setText(String.format("Get %d gold",getGoldCountAfterThisBattle));
        goldCount.setColor(ColorRGBA.Red);
        goldCount.setSize(0.2f);
        goldCount.setLocalTranslation(-0.5f, 3f, 0);
        rootNode.attachChild(goldCount);

//         如果想要黑色背景,就取消这段注释
        Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", new ColorRGBA(1f, 1f, 1f, 0.1f));// 镜面反射时，高光的颜色。

        // 应用材质。
        Geometry geom = new Geometry("选卡界面", new Quad(1600, 900));
        geom.setMaterial(mat);

        // 使物体看起来透明
        mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        geom.setQueueBucket(RenderQueue.Bucket.Transparent);

        geom.center();


        Picture skip = new Picture("跳过");
        skip.setWidth(200);
        skip.setHeight(80);
        skip.setImage(app.getAssetManager(), "Util/跳过.png", true);
        skip.setLocalTranslation(1300, 20, 1);
        rootNode.attachChild(skip);

        try {// 因为懒惰的逸润巨佬卡牌没有做完会爆错，所以这里有个TRY-CATCH

            int count = 0;
            for (Equipment equipment : equipments) {
                equipment.setImage(app.getAssetManager());//将装备添加进Assetmanager
                equipment.setLocalTranslation(500 + (count++) * 300f, (float) (HandCardsState.height - 50) / 2, 1);
                rootNode.attachChild(equipment);
            }
        } catch (AssetNotFoundException e) {
            System.out.println("缺少：" + e.getMessage());
        }
        rootNode.attachChild(word);

        rootNode.attachChild(geom);


    }

    public static void setGetGoldCountAfterThisBattle(int goldCount){
        getGoldCountAfterThisBattle = goldCount;
    }

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

    class MyRawInputListener implements RawInputListener {
        Equipment last = null;
        BitmapFont fnt = app.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
        BitmapText des = new BitmapText(fnt, false);//显示的文字
        BitmapText title = new BitmapText(fnt, false);//显示的文字

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
            CollisionResults results = getGuiCollision(evt);

            if (results.size() > 0) {
                // 获得离射线原点最近的交点所在的图片
                Geometry res = results.getClosestCollision().getGeometry();
                Equipment closest;

                if (res instanceof Equipment) {
                    closest = (Equipment) res;
                } else {
                    return;
                }

                if (last != closest) {
                    enlargeEquipment(last, closest);//放大选中图片
                    last = closest;
                    des.setText(last.getDescription());
                    des.setSize(0.2f);
//        word.setColor(ColorRGBA.Black);
                    des.setLocalTranslation(-3.5f, 1.5f, 0);
                    title.setText(last.getName());
                    title.setSize(0.3f);
                    title.setLocalTranslation(-3.5f, 2f, 0);
                    rootNode.attachChild(title);
                    rootNode.attachChild(des);
                }
            } else {
                // 恢复原状
                if (last != null) {
                    recoverEquipment(last);
                    last = null;
                    des.removeFromParent();
                    title.removeFromParent();
                }
            }

        }

        public void onMouseButtonEvent(MouseButtonEvent evt) {
            //如果是鼠标按下去

            if (evt.isReleased()) {
//                System.out.println(evt.getX());
//                System.out.println(evt.getY());
                //获得当前鼠标选中的位置
                CollisionResults guiResults = getGuiCollision(evt);
                if (guiResults.size() > 0) {
                    // 获得离射线原点最近的交点所在的图片
                    Geometry res = guiResults.getClosestCollision().getGeometry();

                    if (res instanceof Equipment) {
//                        System.out.println((Card)res);
                        // 将装备加入到玩家手中
                        MainRole.getInstance().getEquipment((Equipment) res);
                        res.removeFromParent();
                        // 完成操作，删除这个 state
                        finish();
                    }
                    else {
                        if (res.getName().equals("跳过")){
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
        stateManager.detach(stateManager.getState(EnemyState.class));
        stateManager.detach(stateManager.getState(HandCardsState.class));
        stateManager.detach(stateManager.getState(DecksState.class));
        stateManager.detach(stateManager.getState(LeadingActorState.class));
        FilterPostProcessor fpp = stateManager.getState(BattleBackGroundState.class).getFpp();
        fpp.removeAllFilters();
        app.getViewPort().removeProcessor(fpp);
        stateManager.detach(stateManager.getState(BattleBackGroundState.class));
        app.getRootNode().attachChild(this.rootNode);
        app.getInputManager().addRawInputListener(mril);

    }

    @Override
    protected void onDisable() {
        this.rootNode.removeFromParent();
        app.getInputManager().removeRawInputListener(mril);
        app.getStateManager().detach(app.getStateManager().getState(BattleState.class));
        app.getStateManager().detach(app.getStateManager().getState(Battle.class));
    }
}
