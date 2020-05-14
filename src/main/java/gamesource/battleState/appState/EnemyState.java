package gamesource.battleState.appState;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
import com.jme3.bounding.BoundingSphere;
import gamesource.battleState.character.Enemy;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.font.Rectangle;
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
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;
import gamesource.battleState.particle.DamageParticle;
import gamesource.battleState.particle.KingSkeletonStateParticle;
import gamesource.battleState.particle.StuxnetStateParticle;

import java.util.ArrayList;
import java.util.Arrays;

public class EnemyState extends BaseAppState {
    private SimpleApplication app;
    private Node rootNode = new Node("EnemyState");  //主节点
    private ArrayList<Enemy> enemies;
    private ArrayList<Spatial> enemiesModel;
    private ArrayList<BitmapText> hpHints;
    private ArrayList<BitmapText> blockHints;
    private ArrayList<Float> hpPositions;
    private ArrayList<Integer> modelPositions;
    private ArrayList<Float> blockPositions;

    private ArrayList<AnimChannel> animChannels;  // 用于播放动画
    private ArrayList<AnimControl> animControls;  // 用于播放动画

    private MyRawInputListener myRawInputListener;
    private Geometry chosen;  // 选中的模型
    private Enemy target;   // 选中模型对应的enemy类
    private int targetID; // 选中的enemy类在 数组中的位置,主要用于更新hp和block
    private static EnemyState instance = new EnemyState();
    public static boolean lock = false;// 互斥锁,避免主角和敌人同时修改buff显示
    private SimpleApplication simpleApp;
    private AssetManager assetManager;
    private AppStateManager stateManager;
    private InputManager inputManager;
    private ViewPort viewPort;
    private Camera camera;
    Quad q;
    BitmapFont fnt;
    protected BitmapText actionDisplay;
    private Geometry displayBoard;

    public EnemyState() {
        enemies = new ArrayList<Enemy>();
        hpHints = new ArrayList<BitmapText>();
        blockHints = new ArrayList<BitmapText>();
        enemiesModel = new ArrayList<Spatial>();
        animChannels = new ArrayList<AnimChannel>();
        animControls = new ArrayList<AnimControl>();
        modelPositions = new ArrayList<Integer>() {{
            //每个怪物的x y z坐标
            add(4);
            add(0);
            add(-1);

            add(6);
            add(0);
            add(-1);

            add(8);
            add(0);
            add(-1);
        }};
        hpPositions = new ArrayList<Float>() {{
            //每个血量提示的x y坐标
            add(1.7f);
            add(1.5f);

            add(3.7f);
            add(1.5f);

            add(5.7f);
            add(1.5f);
        }};
        blockPositions = new ArrayList<Float>() {{
            //每个护甲提示的x y坐标
            add(2f);
            add(-1f);

            add(4f);
            add(-1f);

            add(6f);
            add(-1f);
        }};
//        addEnemies(
//                new DarkDragon(85, "Enemies/Dragon/Dragon1/dragon.obj", 0, 0, 0, 0, 0, 0, 0, 0),
//                new DarkDragon(85, "Enemies/Dragon/Dragon2/dragon.obj", 0, 0, 0, 0, 0, 0, 0, 0),
//                new DarkDragon(85, "Enemies/Dragon/Dragon3/dragon.obj", 0, 0, 0, 0, 0, 0, 0, 0)
//        );
        chosen = null;
        target = null;
        targetID = -1;
    }


    @Override
    protected void initialize(Application application) {
        this.app = (SimpleApplication) getApplication();
        this.assetManager = app.getAssetManager();
        this.stateManager = app.getStateManager();
        this.inputManager = app.getInputManager();
        this.viewPort = app.getViewPort();
        this.camera = app.getCamera();
        q = new Quad(6, 3);
        fnt = assetManager.loadFont("Interface/Fonts/Default.fnt");
        actionDisplay = new BitmapText(fnt, false);
        displayBoard = new Geometry("quad", q);
        displayBoard.setLocalTranslation(2, 2, -1);
        Material mt = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mt.setColor("Color", ColorRGBA.Brown);
        mt.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        displayBoard.setQueueBucket(RenderQueue.Bucket.Transparent);
        displayBoard.setMaterial(mt);
        rootNode.attachChild(displayBoard);
        getInstance().displayAction();
        this.myRawInputListener = new MyRawInputListener();
//        rootNode.attachChild(DamageParticle.getParticle1(assetManager))
    }

    // start ----- 显示和移除敌人动作
    public void removeAction() {
        actionDisplay.removeFromParent();
    }

    public void displayAction() {
        removeAction();
        String txtB = "";
        for (int i = 0; i < enemies.size(); i++) {
            txtB += String.format("%d. %s\n\n\n", i + 1, enemies.get(i).getNextActionDescription());
        }
        actionDisplay.setBox(new Rectangle(2, 4, 6, 3));
        actionDisplay.setQueueBucket(RenderQueue.Bucket.Transparent);
        actionDisplay.setSize(0.25f);
        actionDisplay.setText(txtB);
        rootNode.attachChild(actionDisplay);
    }
    // end ----- 显示和移除敌人动作

    //加载的时候渲染护甲和血量的值
    public void initializeHints() {
        int index = 0;
        //为每个敌人添加血量
        BitmapFont fnt = this.assetManager.loadFont("Interface/Fonts/Default.fnt");
        for (Enemy enemy : enemies) {
            BitmapText hpHint = new BitmapText(fnt, false);
            hpHint.setBox(new Rectangle(hpPositions.get(2 * index), hpPositions.get(2 * index + 1), 6, 3));
            hpHint.setQueueBucket(RenderQueue.Bucket.Transparent);
            hpHint.setSize(0.3f);
            hpHint.setColor(ColorRGBA.Red);
            hpHint.setText(String.format("HP: %s/%s", enemy.getHP(), enemy.getTotalHP()));
            rootNode.attachChild(hpHint);
            hpHints.add(hpHint);
            BitmapText blockHint = new BitmapText(fnt, false);
            blockHint.setBox(new Rectangle(blockPositions.get(2 * index), blockPositions.get(2 * index + 1), 6, 3));
            blockHint.setQueueBucket(RenderQueue.Bucket.Transparent);
            blockHint.setSize(0.3f);
            blockHint.setColor(ColorRGBA.Blue);
            blockHint.setText(String.format("Blocks: %s", enemy.getBlock()));
            rootNode.attachChild(blockHint);
            blockHints.add(blockHint);
            index += 1;
        }
    }

    public void updateHints(boolean isAOE) {
        int index = 0;
        BitmapFont fnt = assetManager.loadFont("Interface/Fonts/Default.fnt");
        if (isAOE) {
            for (Enemy enemy : enemies) {
                hpHints.get(index).removeFromParent();
                BitmapText hpHint = new BitmapText(fnt, false);
                hpHint.setBox(new Rectangle(hpPositions.get(2 * index), hpPositions.get(2 * index + 1), 6, 3));
                hpHint.setQueueBucket(RenderQueue.Bucket.Transparent);
                hpHint.setSize(0.3f);
                hpHint.setColor(ColorRGBA.Red);
                hpHint.setText(String.format("HP: %s/%s", enemy.getHP(), enemy.getTotalHP()));
                rootNode.attachChild(hpHint);
                hpHints.set(index, hpHint);

                blockHints.get(index).removeFromParent();
                BitmapText blockHint = new BitmapText(fnt, false);
                blockHint.setBox(new Rectangle(blockPositions.get(2 * index), blockPositions.get(2 * index + 1), 6, 3));
                blockHint.setQueueBucket(RenderQueue.Bucket.Transparent);
                blockHint.setSize(0.3f);
                blockHint.setColor(ColorRGBA.Blue);
                blockHint.setText(String.format("Blocks: %s", enemies.get(index).getBlock()));
                rootNode.attachChild(blockHint);
                blockHints.set(index, blockHint);
                index += 1;
            }
        } else {
            if (targetID != -1) {
                hpHints.get(targetID).removeFromParent();
                BitmapText hpHint = new BitmapText(fnt, false);
                hpHint.setBox(new Rectangle(hpPositions.get(2 * targetID), hpPositions.get(2 * targetID + 1), 6, 3));
                hpHint.setQueueBucket(RenderQueue.Bucket.Transparent);
                hpHint.setSize(0.3f);
                hpHint.setColor(ColorRGBA.Red);
                hpHint.setText(String.format("HP: %s/%s", enemies.get(targetID).getHP(), enemies.get(targetID).getTotalHP()));
                rootNode.attachChild(hpHint);
                hpHints.set(targetID, hpHint);

                blockHints.get(targetID).removeFromParent();
                BitmapText blockHint = new BitmapText(fnt, false);
                blockHint.setBox(new Rectangle(blockPositions.get(2 * targetID), blockPositions.get(2 * targetID + 1), 6, 3));
                blockHint.setQueueBucket(RenderQueue.Bucket.Transparent);
                blockHint.setSize(0.3f);
                blockHint.setColor(ColorRGBA.Blue);
                blockHint.setText(String.format("Blocks: %s", enemies.get(targetID).getBlock()));
                rootNode.attachChild(blockHint);
                blockHints.set(targetID, blockHint);
            }
        }
    }


    public void addEnemies(Enemy... enemies) {
        this.enemies.addAll(Arrays.asList(enemies));
    }

    private void setModelSize(Spatial model) {
        switch (model.getName()) {
            case "Enemies/Dragon/Dragon/dragon0.obj":
            case "Enemies/Dragon/Dragon/dragon1.obj":
            case "Enemies/Dragon/Dragon/dragon2.obj":
                model.scale(0.03f);
                break;
            case "Enemies/skeleton/greenSkeleton/greenSkeleton0.j3o":
            case "Enemies/skeleton/greenSkeleton/greenSkeleton1.j3o":
            case "Enemies/skeleton/greenSkeleton/greenSkeleton2.j3o":
            case "Enemies/skeleton/redSkeleton/redSkeleton0.j3o":
            case "Enemies/skeleton/redSkeleton/redSkeleton1.j3o":
            case "Enemies/skeleton/redSkeleton/redSkeleton2.j3o":
            case "Enemies/skeleton/blueSkeleton/blueSkeleton0.j3o":
            case "Enemies/skeleton/blueSkeleton/blueSkeleton1.j3o":
            case "Enemies/skeleton/blueSkeleton/blueSkeleton2.j3o":
            case "Enemies/skeleton/KingSkeleton/kingSkeleton0.j3o":
            case "Enemies/skeleton/KingSkeleton/kingSkeleton1.j3o":
            case "Enemies/skeleton/KingSkeleton/kingSkeleton2.j3o":
                model.scale(0.01f);
                break;
            case "Enemies/skeleton/skeleton0.j3o":
            case "Enemies/skeleton/skeleton1.j3o":
            case "Enemies/skeleton/skeleton2.j3o":
                model.scale(0.015f);
                break;
            case "Enemies/bat/scene0.j3o":
            case "Enemies/bat/scene1.j3o":
            case "Enemies/bat/scene2.j3o":
                model.scale(0.23f);
                break;
            case "Enemies/bossKnight/scene0.j3o":
            case "Enemies/bossKnight/scene1.j3o":
            case "Enemies/bossKnight/scene2.j3o":
                model.scale(0.4f);
                break;

            case "Enemies/snowRobot/snowRobot0.j3o":
            case "Enemies/snowRobot/snowRobot1.j3o":
            case "Enemies/snowRobot/snowRobot2.j3o":

                model.scale(0.012f);
                break;

            case "Enemies/zhenwang/boss0.j3o":
                model.scale(0.04f);
                break;
            case "Enemies/underWater/drunkCrab0.j3o":
            case "Enemies/underWater/drunkCrab1.j3o":
            case "Enemies/underWater/drunkCrab2.j3o":
                model.scale(0.15f);
                break;
            case "Enemies/underWater/fish10.j3o":
            case "Enemies/underWater/fish11.j3o":
            case "Enemies/underWater/fish12.j3o":
                model.scale(0.06f);
                break;

            case "Enemies/underWater/fish20.j3o":
            case "Enemies/underWater/fish21.j3o":
            case "Enemies/underWater/fish22.j3o":
                model.scale(0.3f);
                break;

            case "Enemies/underWater/hollowKnight0.j3o":
            case "Enemies/underWater/hollowKnight1.j3o":
            case "Enemies/underWater/hollowKnight2.j3o":
                model.scale(0.01f);
                break;
            case "Enemies/underWater/mushroom_bug0.j3o":
            case "Enemies/underWater/mushroom_bug1.j3o":
            case "Enemies/underWater/mushroom_bug2.j3o":
                model.scale(1.2f);

            default:
                break;
        }
    }

    private void setModelPos(Spatial model) {
        switch (model.getName()) {
            case "Enemies/skeleton/greenSkeleton/greenSkeleton0.j3o":
                model.move(-1.1f, 0.1f, 0);
                break;
            case "Enemies/skeleton/greenSkeleton/greenSkeleton1.j3o":
                model.move(-0.9f, 0.1f, 0);
                break;
            case "Enemies/skeleton/greenSkeleton/greenSkeleton2.j3o":
                model.move(-0.7f, 0.1f, 0);
                break;
            case "Enemies/skeleton/redSkeleton/redSkeleton0.j3o":
                model.move(-0.8f, 0.1f, 0);
                break;
            case "Enemies/skeleton/redSkeleton/redSkeleton1.j3o":
                model.move(-0.6f, 0.1f, 0);
                break;
            case "Enemies/skeleton/redSkeleton/redSkeleton2.j3o":
                model.move(-0.3f, 0.1f, 0);
                break;
            case "Enemies/skeleton/blueSkeleton/blueSkeleton0.j3o":
                model.move(-0.8f, 0.1f, 0);
                break;
            case "Enemies/skeleton/blueSkeleton/blueSkeleton1.j3o":
                model.move(-0.6f, 0.1f, 0);
                break;
            case "Enemies/skeleton/blueSkeleton/blueSkeleton2.j3o":
                model.move(-0.3f, 0.1f, 0);
                break;
            case "Enemies/skeleton/KingSkeleton/kingSkeleton0.j3o":
                model.move(-0.8f, 0.2f, 0);
                break;
            case "Enemies/skeleton/KingSkeleton/kingSkeleton1.j3o":
                model.move(-0.6f, 0.2f, 0);
                break;
            case "Enemies/skeleton/KingSkeleton/kingSkeleton2.j3o":
                model.move(-0.3f, 0.2f, 0);
                break;
            case "Enemies/bat/scene0.j3o":
                model.move(-1.1f, 0, 0);
                break;
            case "Enemies/bat/scene1.j3o":
            case "Enemies/bat/scene2.j3o":
                model.move(-0.9f, 0, 0);
                break;

            case "Enemies/bossKnight/scene0.j3o":
                model.move(-1.4f, 0, 0);
                break;

            case "Enemies/bossKnight/scene1.j3o":
                model.move(-1.3f, 0, 0);
                break;

            case "Enemies/bossKnight/scene2.j3o":
                model.move(-1.2f, 0, 0);
                break;

            case "Enemies/snowRobot/snowRobot0.j3o":
                model.rotate(0, 3f, 0);
                model.move(-1.5f, 0, 0);
                break;
            case "Enemies/snowRobot/snowRobot1.j3o":
                model.rotate(0, 3f, 0);
                model.move(-1.3f, 0.2f, 0);
                break;
            case "Enemies/snowRobot/snowRobot2.j3o":
                model.rotate(0, 3f, 0);
                model.move(-1.1f, 0.2f, 0);
                break;

            case "Enemies/zhenwang/boss0.j3o":
                model.move(35.5f, -12f, -131f);
                break;

            case "Enemies/skeleton/skeleton0.j3o":
                model.move(-1.5f, 0, 0);

                break;
            case "Enemies/skeleton/skeleton1.j3o":
                model.move(-1f, 0, 0);

                break;
            case "Enemies/skeleton/skeleton2.j3o":
                model.move(-0.7f, 0, 0);
                break;
            case "Enemies/underWater/drunkCrab0.j3o":
                model.move(-1.7f, 0, 0);
                break;
            case "Enemies/underWater/drunkCrab1.j3o":
                model.move(-1.55f, -0.4f, 0);
                break;
            case "Enemies/underWater/drunkCrab2.j3o":
                model.move(-1.45f, -0.4f, 0);
                break;

            case "Enemies/underWater/fish10.j3o":
                model.move(-1.5f, 0, 0);
                break;
            case "Enemies/underWater/fish11.j3o":
                model.move(-1.3f, 0, 0);
                break;
            case "Enemies/underWater/fish12.j3o":
                model.move(-1.1f, 0, 0);
                break;

            case "Enemies/underWater/fish20.j3o":
                model.move(-1.5f, 0, 0);
                break;
            case "Enemies/underWater/fish21.j3o":
                model.move(-1.3f, 0, 0);
                break;
            case "Enemies/underWater/fish22.j3o":
                model.move(-1.1f, 0, 0);
                break;

            case "Enemies/underWater/hollowKnight0.j3o":
                model.move(-1.5f,0,0);
                break;
            case "Enemies/underWater/hollowKnight1.j3o":
                model.move(-1.3f,0,0);
                break;
            case "Enemies/underWater/hollowKnight2.j3o":
                model.move(-1.1f,0,0);
                break;

            case "Enemies/underWater/mushroom_bug0.j3o":
                model.move(-1.5f,0,0);
                break;
            case "Enemies/underWater/mushroom_bug1.j3o":
                model.move(-1.15f,-0.075f,0);
                break;
            case "Enemies/underWater/mushroom_bug2.j3o":
                model.move(-0.95f,-0.15f,0);
                break;

            default:
                break;
        }
    }

    public void initEnemies() {
        int i = 0;
        for (Enemy enemy : this.enemies) {
            enemy.bindApp(app);
            String src = enemy.getSrc();
            String[] parts = src.split("\\.");
            Spatial model = this.assetManager.loadModel(src);
            enemy.setSrc(parts[0] + i + "." + parts[1]);
            src = parts[0] + i + "." + parts[1];
            model.setName(src);
//            model.scale(0.01f);
            setModelSize(model);
            model.center();// 将模型的中心移到原点
            System.out.println(model.getLocalTranslation().x + " " + model.getLocalTranslation().y + " " + model.getLocalTranslation().z);
            setModelPos(model);
//            System.out.println(model.getName());
            model.move(modelPositions.get(i * 3), modelPositions.get(i * 3 + 1), modelPositions.get(i * 3 + 2));
            model.rotate(0, -0.9f, 0);//调整y角度可以设置怪物脸的朝向，0为正对屏幕，负数为向左看，正数为向右


            try {
                Node scene = (Node) model;
                Node bip001 = (Node) scene.getChild("bip001");

                AnimControl animControl = bip001.getControl(AnimControl.class);
                AnimChannel animChannel = animControl.createChannel();

                // 通过调用动画来让敌人立正站好
                animChannel.setAnim(animControl.getAnimationNames().toArray()[0].toString());
                animChannel.setLoopMode(LoopMode.DontLoop);
                animControls.add(animControl);
                animChannels.add(animChannel);
            } catch (Exception e) {
                System.out.println(src + " 不能加载动画");
                System.out.println(e);
            }
            model.setModelBound(new BoundingSphere());// 使用包围球
            model.updateModelBound();// 更新包围球
            this.enemiesModel.add(model);
            rootNode.attachChild(model);
            i++;
        }
    }

    private Ray getRay(MouseButtonEvent evt) {
        Vector2f screenCoord = this.inputManager.getCursorPosition();
        Vector3f worldCoord = this.camera.getWorldCoordinates(screenCoord, 1f);

        // 计算方向
        Vector3f dir = worldCoord.subtract(this.camera.getLocation());
        dir.normalizeLocal();
        Ray ray = new Ray();
        ray.setOrigin(this.camera.getLocation());
        ray.setDirection(dir);
        return ray;
    }

    private CollisionResults getRootCollision(MouseButtonEvent evt) {
        Ray ray = getRay(evt);
        CollisionResults results = new CollisionResults();
        rootNode.collideWith(ray, results);//检测guinode 中所有图形对象 和 ray 的碰撞

        return results;
    }

    private Ray getRay(MouseMotionEvent evt) {
        Vector2f screenCoord = this.inputManager.getCursorPosition();
        Vector3f worldCoord = this.camera.getWorldCoordinates(screenCoord, 1f);

        // 计算方向
        Vector3f dir = worldCoord.subtract(this.camera.getLocation());
        dir.normalizeLocal();
        Ray ray = new Ray();
        ray.setOrigin(this.camera.getLocation());
        ray.setDirection(dir);
        return ray;
    }

    private CollisionResults getGuiCollision(MouseMotionEvent evt) {
        Ray ray = getRay(evt);
        CollisionResults results = new CollisionResults();
        rootNode.collideWith(ray, results);//检测guinode 中所有图形对象 和 ray 的碰撞
        return results;
    }

    public ArrayList<AnimChannel> getAnimChannels() {
        return animChannels;
    }

    public static EnemyState getInstance() {
        return instance;
    }

    class MyRawInputListener implements RawInputListener {
        private BitmapText buffDisplay = new BitmapText(fnt, false);//显示的文字
        private Geometry buffDisplayBoard = new Geometry("quad", q);//文字后面的版


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
            int index = 0;
            for (Spatial spatial : enemiesModel) {
                if (spatial.getWorldBound().intersects(getRay(evt))) {
                    break;
                }
                index++;
            }
            if (index < enemiesModel.size() && results.size() > 0) {
                Geometry res = results.getClosestCollision().getGeometry();
                if (!res.getName().equals("BitmapFont")) {
                    //遍历敌人数组，确定鼠标选中的是哪一个敌人
                    lock = true; // 鼠标移动到敌人身上时,立即锁住,避免主角类修改
                    Enemy targetEnemy = enemies.get(index);
                    String txtB = "";
                    if (targetEnemy != null) {
                        txtB = String.format("This character's buff:\n");
                        txtB += String.format("Strength: %d        ", targetEnemy.getStrength());
                        txtB += String.format("Dexterity: %d       \n", targetEnemy.getDexterity());
                        txtB += String.format("Bleeding: %d        ", targetEnemy.getBleeding().getDuration());
                        txtB += String.format("Disarm: %d          ", targetEnemy.getDisarm().getDuration());
                        txtB += String.format("Erode: %d           \n", targetEnemy.getErode().getDuration());
                        txtB += String.format("Excite: %d             ", targetEnemy.getExcite().getDuration());
                        txtB += String.format("Intangible: %d     ", targetEnemy.getIntangible().getDuration());
                        txtB += String.format("Poison: %d          \n", targetEnemy.getPoison().getDuration());
                        txtB += String.format("Sheild: %d             ", targetEnemy.getSheild().getDuration());
                        txtB += String.format("Silence: %d         ", targetEnemy.getSilence().getDuration());
                        txtB += String.format("Stun: %d            \n", targetEnemy.getStun().getDuration());
                        txtB += String.format("Vunlnerable: %d   ", targetEnemy.getVulnerable().getDuration());
                        txtB += String.format("Weak: %d            ", targetEnemy.getWeak().getDuration());
                        txtB += String.format("Artifact: %d        \n", targetEnemy.getArtifact().getTimes());
                        txtB += String.format("Dexterity: %d        ", targetEnemy.getDexterity());
                        txtB += String.format("Dodge: %d           ", targetEnemy.getDodge().getTimes());
                        txtB += String.format("Excite: %d          ", targetEnemy.getStrength());
                    }
                    buffDisplay.setBox(new Rectangle(2, 4, 6, 3));
                    buffDisplay.setQueueBucket(RenderQueue.Bucket.Transparent);
                    buffDisplay.setSize(0.25f);
                    buffDisplay.setText(txtB);
                    getInstance().removeAction();
                    rootNode.attachChild(buffDisplay);
                }
            } else {
                lock = false;// 当鼠标从敌人身上挪走时,解锁,允许主角类修改,敌人类放弃修改提示的机会
//                if (buffDisplay != null)
                buffDisplay.removeFromParent();
                if (lock)
                    getInstance().displayAction();// 添加敌人行动提示
//                if (buffDisplayBoard != null)
//                buffDisplayBoard.removeFromParent();
            }
        }

        @Override
        public void onMouseButtonEvent(MouseButtonEvent evt) {
            //如果是鼠标按下去
            if (evt.isPressed()) {

                //获得当前鼠标选中的位置
                CollisionResults guiResults = getRootCollision(evt);
                if (guiResults.size() > 0) {
                    // 获得离射线原点最近的交点所在的图片
//                    Geometry res = guiResults.getClosestCollision().getGeometry();

                } else {
                    chosen = null;
                    target = null;
                    targetID = -1;
                }

            } else if (evt.isReleased()) {
                CollisionResults guiResults = getRootCollision(evt);
//                System.out.println("384 " + guiResults);
                if (guiResults.size() > 0) {
                    // 获得离射线原点最近的交点所在的图片
                    Geometry res = guiResults.getClosestCollision().getGeometry();
//                    System.out.println("388 " + res);
                    chosen = res;
                    targetID = 0;
//                    System.out.println("396 " + ((Spatial) res).getName());
//                    for (Enemy x : enemies) {
//                        if (x.getSrc().equals(res.getName())) {
//                            target = x;
//                            break;
//                        }
//                        targetID++;
//                    }

                    for (Spatial spatial : enemiesModel) {
                        if (spatial.getWorldBound().intersects(getRay(evt))) {
                            target = enemies.get(targetID);
                            break;
                        }
                        targetID++;
                    }
                } else {
                    chosen = null;
                    target = null;
                    targetID = -1;
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


    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public Enemy getTarget() {
        return target;
    }

    public Geometry getChosen() {
        return chosen;
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).getHP() <= 0) {
                enemiesModel.get(i).removeFromParent();
                hpHints.get(i).removeFromParent();
                blockHints.get(i).removeFromParent();
                blockHints.remove(i);
                hpHints.remove(i);
                enemies.remove(i);
                enemiesModel.remove(i);

                blockPositions.remove(2 * i);
                blockPositions.remove(2 * i);
                hpPositions.remove(2 * i);
                hpPositions.remove(2 * i);
                modelPositions.remove(i * 3);
                modelPositions.remove(i * 3);
                modelPositions.remove(i * 3);
            }
        }
    }

    @Override
    protected void cleanup(Application application) {
    }

    @Override
    protected void onEnable() {
        this.initEnemies();
        this.initializeHints();
        this.inputManager.addRawInputListener(myRawInputListener);
        // 在启动背景的时候吧这个特效加载背景节点上去
        if(this.enemies.size() == 1){//boss 只有一个
            switch (this.enemies.get(0).getSrc()){
                case "Enemies/skeleton/KingSkeleton/KingSkeleton0.j3o":
                    this.rootNode.attachChild(KingSkeletonStateParticle.getParticle1(app.getAssetManager()));
                    break;
                case "Enemies/zhenwang/boss0.j3o":
                    this.rootNode.attachChild(StuxnetStateParticle.getParticle1(app.getAssetManager()));
                    break;
                case "Enemies/bossKnight/scene0.j3o":
                    break;
            }
        }
        app.getRootNode().attachChild(this.rootNode);

    }

    public ArrayList<AnimControl> getAnimControls() {
        return animControls;
    }

    @Override
    protected void onDisable() {
        this.rootNode.removeFromParent();
        this.enemies.clear();
        for (Spatial spatial : enemiesModel) {
            spatial.removeFromParent();
        }
        this.enemiesModel.clear();
        this.animChannels.clear();
        this.animControls.clear();
        for (Spatial spatial : hpHints) {
            spatial.removeFromParent();
        }
        this.hpHints.clear();
        for (Spatial spatial : blockHints) {
            spatial.removeFromParent();
        }
        this.blockHints.clear();

        this.inputManager.removeRawInputListener(myRawInputListener);
        this.target = null;
        this.chosen = null;
    }
}