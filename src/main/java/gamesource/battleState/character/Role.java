package gamesource.battleState.character;

import gamesource.battleState.card.AttackCard;
import com.jme3.app.SimpleApplication;
import gamesource.battleState.utils.buffs.Buff;
import gamesource.battleState.utils.buffs.foreverBuffs.Artifact;
import gamesource.battleState.utils.buffs.foreverBuffs.Dexterity;
import gamesource.battleState.utils.buffs.foreverBuffs.Dodge;
import gamesource.battleState.utils.buffs.limitBuffs.*;
import gamesource.battleState.card.AttackCard.PROPERTY;
public class Role {
    public enum ROLE {MAINROLE, NPC, ENEMY}

    protected int totalHP; //总血量
    protected int HP; // 当前血量
    protected String src; //模型路径
    protected ROLE role; // 属于什么角色
    protected PROPERTY property;
    protected int atk; // 攻击力,只应用于一次伤害
    protected SimpleApplication app; //主要用于调用各个state中的方法
    //战斗中的实际属性
    protected int block;
    protected int strength;

    protected int dexterity;

//    private boolean unableAttack;
//    private boolean unableSkill;


    // 以下三条可以用于逐渐增加难度使用, 比如随着时间,敌人1,3增加,2减少
    private double multiplyingDealDamage; //造成伤害的倍率
    private double multiplyingGetDamage; //受到伤害的倍率
    private double multiplyingGetBlock; //从卡牌中获取格挡值的倍率


    protected Vulnerable vulnerable;
    protected Weak weak;
    protected Stun stun;
    protected Silence silence;
    protected Sheild shield;
    protected Poison poison;
    protected Intangible intangible;
    protected Excite excite;
    protected Erode erode;
    protected Disarm disarm;
    protected Bleeding bleeding;
    protected Artifact artifact;
    protected Dodge dodge;


    public Role(int HP, String src, ROLE role) {
        this.totalHP = HP;
        this.HP = HP;
        this.src = src;
        this.role = role;
        this.property = PROPERTY.NONE;
        vulnerable = new Vulnerable(this, 0);
        weak = new Weak(this, 0);
        stun = new Stun(this, 0);
        silence = new Silence(this, 0);
        shield = new Sheild(this, 0);
        poison = new Poison(this, 0);
        intangible = new Intangible(this, 0);
        excite = new Excite(this, 0);
        erode = new Erode(this, 0);
        disarm = new Disarm(this, 0);
        bleeding = new Bleeding(this, 0);
        artifact = new Artifact(this, 0);
        dodge = new Dodge(this, 0);
        dexterity = 0;
        strength = 0;
        atk = 0;
        this.multiplyingDealDamage = 1;
        this.multiplyingGetDamage = 1;
        this.multiplyingGetBlock = 1;
    }

    public String getSrc() {
        return src;
    }

    public void startTurn() {
        if (this.stun.getDuration() > 0) {
            this.endTurn();
        }
        this.block = 0;
    }

    public boolean isRestrain(PROPERTY a, PROPERTY b) {
        if (a == PROPERTY.FIRE && b == PROPERTY.GOLD) return true;
        if (a == PROPERTY.GOLD && b == PROPERTY.WOOD) return true;
        if (a == PROPERTY.WOOD && b == PROPERTY.WATER) return true;
        if (a == PROPERTY.WATER && b == PROPERTY.SOIL) return true;
        if (a == PROPERTY.SOIL && b == PROPERTY.FIRE) return true;
        return false;
    }


    // 表示收到伤害,并且将扣除hp返回,用于计算吸血
    public int getDamage(int damage) {
        if (this.dodge.getTimes() > 0) {
            this.dodge.decTimes();
            if (this.block >= 1) {
                this.block -= 1;
            } else {
                this.HP -= 1;
                if (this.bleeding.getDuration() > 0)
                    this.bleeding.triggerFunc();
            }
            return 1;
        }

        if (this.vulnerable.getDuration() > 0) {
            damage = (int) (damage * 1.5);
        }

        if (this.intangible.getDuration() > 0) {
            damage = (int) (damage * 0.5);
        }
        damage = (int) (damage * multiplyingGetDamage);
        if (this.block >= damage) {
            this.block -= damage;
            return 0;
        } else {
            this.HP -= damage - block;
            this.block = 0;
            if (this.bleeding.getDuration() > 0) {
                this.bleeding.triggerFunc();
            }
            return damage - block;
        }
    }


    // 回合结束,计算各个buff效果,以及清空护甲
    public void endTurn() {
        this.shield.triggerFunc();
        this.poison.triggerFunc();
        this.bleeding.triggerFunc();
        this.vulnerable.triggerFunc();
        this.intangible.triggerFunc();
        this.disarm.triggerFunc();
        this.silence.triggerFunc();
        this.stun.triggerFunc();
        this.excite.triggerFunc();
        this.erode.triggerFunc();


    }


    public void bindApp(SimpleApplication app) {
        System.out.println("绑定app");
        this.app = app;
    }

    // 施加buff的接口
    public void getBuff(Buff... buffs) {
        for (Buff buff : buffs) {

            if (buff instanceof Sheild) {
                this.shield.incDuration(((Sheild) buff).getDuration());
                return;
            } else if (buff instanceof Excite) {
                this.excite.incDuration(((Excite) buff).getDuration());
                return;
            } else if (buff instanceof Artifact) {
                this.artifact.incTimes(((Artifact) buff).getTimes());
                return;
            } else if (buff instanceof Dodge) {
                this.dodge.incTimes(((Dodge) buff).getTimes());
                return;
            }


            if (this.artifact.getTimes() > 0) {
                this.artifact.triggerFunc();
            } else {
                if (buff instanceof Vulnerable) {
                    this.vulnerable.incDuration(((Vulnerable) buff).getDuration());
                } else if (buff instanceof Weak) {
                    this.weak.incDuration(((Weak) buff).getDuration());
                } else if (buff instanceof Stun) {
                    this.stun.incDuration(((Stun) buff).getDuration());
                } else if (buff instanceof Silence) {
                    this.silence.incDuration(((Silence) buff).getDuration());
                } else if (buff instanceof Poison) {
                    this.poison.incDuration(((Poison) buff).getDuration());
                } else if (buff instanceof Intangible) {
                    this.intangible.incDuration(((Intangible) buff).getDuration());
                } else if (buff instanceof Erode) {
                    this.erode.incDuration(((Erode) buff).getDuration());
                } else if (buff instanceof Disarm) {
                    this.disarm.incDuration(((Disarm) buff).getDuration());
                } else if (buff instanceof Bleeding) {
                    this.bleeding.incDuration(((Bleeding) buff).getDuration());
                }

            }
        }
    }

    // 获取护甲, 记得与 computeBlock 一起搭配
    public void gainBlock(int num) {
        this.block += computeBlock(num);
    }

    // 计算经过buff后的,应该造成的伤害值
    public int computeDamage(int num) {
        num = num + strength + this.atk;
        if (this.weak.getDuration() > 0)
            num = (int) (num * 0.75);
        if (this.disarm.getDuration() > 0) {
            float random = (float) (Math.random() * 3);
            if (random < 1) return 1;
        }
        this.atk = 0;
        return (int) (num * this.multiplyingDealDamage);
    }

    public int computeDamage(int num, PROPERTY property) {
        int oldDamage = computeDamage(num);
        if (isRestrain(property, this.property)) {
            oldDamage = (int) (oldDamage * 1.5);
        }
        return oldDamage;
    }

    // 计算经过buff后,应该获得护甲值
    public int computeBlock(int num) {
        num = num + dexterity;
        if (this.erode.getDuration() > 0)
            num = (int) (num * 0.66);
        return (int) (num * this.multiplyingGetBlock);
    }


    public int getTotalHP() {
        return totalHP;
    }

    public void setTotalHP(int totalHP) {
        this.totalHP = totalHP;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public void setRole(ROLE role) {
        this.role = role;
    }

    public int getBlock() {
        return block;
    }


    public void setBlock(int block) {
        this.block = block;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }


    public double getMultiplyingDealDamage() {
        return multiplyingDealDamage;
    }

    public void setMultiplyingDealDamage(double multiplyingDealDamage) {
        this.multiplyingDealDamage = multiplyingDealDamage;
    }

    public double getMultiplyingGetDamage() {
        return multiplyingGetDamage;
    }

    public void setMultiplyingGetDamage(double multiplyingGetDamage) {
        this.multiplyingGetDamage = multiplyingGetDamage;
    }

    public double getMultiplyingGetBlock() {
        return multiplyingGetBlock;
    }

    public void setMultiplyingGetBlock(double multiplyingGetBlock) {
        this.multiplyingGetBlock = multiplyingGetBlock;
    }

    public void getTrueDamage(int damage) {
        this.HP -= damage;
    }

    public ROLE getRole() {
        return role;
    }

//    public boolean getUnableAttack() {
//        return this.unableAttack;
//    }
//
//    public void setUnableAttack(boolean unableAttack) {
//        this.unableAttack = unableAttack;
//    }
//
//    public boolean getUnableSkill() {
//        return this.unableSkill;
//    }
//
//    public void setUnableSkill(boolean unableSkill) {
//        this.unableSkill = unableSkill;
//    }


    public PROPERTY getProperty() {
        return property;
    }

    public Vulnerable getVulnerable() {
        return vulnerable;
    }

    public Weak getWeak() {
        return weak;
    }

    public Stun getStun() {
        return stun;
    }

    public Silence getSilence() {
        return silence;
    }

    public Sheild getSheild() {
        return shield;
    }

    public Poison getPoison() {
        return poison;
    }

    public Intangible getIntangible() {
        return intangible;
    }

    public Excite getExcite() {
        return excite;
    }

    public Erode getErode() {
        return erode;
    }

    public Disarm getDisarm() {
        return disarm;
    }

    public Bleeding getBleeding() {
        return bleeding;
    }

    public Artifact getArtifact() {
        return artifact;
    }

    public Dodge getDodge() {
        return dodge;
    }

    //适用于吸血效果或者治疗效果
    public void treat(int number) {
        if (this.getHP() + number >= this.getTotalHP()) {
            this.setHP(this.getTotalHP());
        } else {
            this.setHP(this.getHP() + number);
        }
    }


    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public SimpleApplication getApp() {
        return app;
    }
}
