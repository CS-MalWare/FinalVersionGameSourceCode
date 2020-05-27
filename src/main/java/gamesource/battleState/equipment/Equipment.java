package gamesource.battleState.equipment;

import gamesource.battleState.character.Enemy;
import gamesource.battleState.character.MainRole;
import com.jme3.asset.AssetManager;
import com.jme3.ui.Picture;

import java.util.ArrayList;

// 装备类
public  class Equipment extends Picture {
    public static enum EquipmentDegree {COMMON, RARE, EPIC, LEGENDARY}
    //装备触发的时机，分为以下几个时机
    //：
    // 1. 战斗结束时触发 (回血,获得金币)
    // 2. 回合结束时触发（包括 每几个回合 触发）
    // 3. 战斗开始时触发 (为敌人加力量)
    // 4. 回合开始时触发
    // 5. 拾取该装备时触发 (加血等等)
    // 6. 打出牌时触发（包含 各种类型 牌）
    // 7. 造成伤害时触发
    // 8. 受到伤害时触发
    // 9. 死亡时触发 (陈钰炫的女装)
    // 10. 获取卡片时候触发(例如将获得之后获得的卡都升级)
    // 11. 进入商店触发

    // 实现思路： 将角色所有装备作为一个装备数组添加到主角类中。每次进行动作，遍历装备数组，找到对应触发时机的装备触发。
    public static enum Opportunity {ENDB, ENDT, STARTB, STARTT, GET, USE, ATTACK, GETD, DEAD, GETCARD, SHOP}

    protected String name;
    protected String picName;
    protected String description;//装备效果的描述
    protected String imgSrc;//装备图片的路径
    protected ArrayList<Enemy> enemies;//当前对局的敌人链表
    protected EquipmentDegree degree;
    protected Opportunity op;

    public Equipment(String name, String picName, String description, EquipmentDegree degree, Opportunity op) {
        super(picName);
        this.imgSrc = "Equipments";
        this.name = name;
        this.description = description;

        this.degree = degree;
        this.op = op;
        switch (degree) {
            case COMMON:
                this.imgSrc += "/common";
                break;
            case RARE:
                this.imgSrc += "/rare";
                break;
            case EPIC:
                this.imgSrc += "/epic";
                break;
            case LEGENDARY:
                this.imgSrc += "/legendary";
                break;
        }
        this.picName = picName;
        this.imgSrc += "/" + picName + ".png";
    }
    public String getPicName(){
        return picName;
    }
    public void setImage(AssetManager assetManager) {

        super.setImage(assetManager, this.imgSrc, true);
        this.setHeight(60);
        this.setWidth(60);
    }

    public String getDescription() {
        return description;
    }

    //可以考虑删除，描述一般不改变
    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    //可以考虑删除，因为图片路径一般不改变
    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    //部分卡牌可能和敌人有联动效果，这里可以设置和清除敌人, 每次战斗完需要吧敌人链表设置为null
    //便于触发战斗结束的装备
    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EquipmentDegree getDegree() {
        return degree;
    }

    public void setDegree(EquipmentDegree degree) {
        this.degree = degree;
    }

    public Opportunity getOpportunity() {
        return op;
    }

    //可以删除
    public void setOpportunity(Opportunity op) {
        this.op = op;
    }

    // 在这里写装备的功能
    public void fun() {

    }

    // 某些buff,比如每次战斗中第一次等，使用了变量控制，需要重置它的状态
    public void resetBuff(){};
}
