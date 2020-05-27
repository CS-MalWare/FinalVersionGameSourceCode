package gamesource.battleState.character;

import gamesource.battleState.appState.DecksState;
import gamesource.battleState.appState.EnemyState;
import gamesource.battleState.appState.HandCardsState;
import gamesource.battleState.appState.LeadingActorState;
import gamesource.battleState.card.AttackCard;
import gamesource.battleState.card.Card;
import gamesource.battleState.card.saber.attack.*;
import gamesource.battleState.card.saber.skill.Defense;
import gamesource.battleState.card.saber.skill.Heal;
import gamesource.battleState.card.saber.skill.MagicSheild;
import gamesource.battleState.card.saber.skill.WhirlingShield;
import gamesource.battleState.equipment.Equipment;
import gamesource.battleState.utils.buffs.foreverBuffs.Dodge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;


public class MainRole extends Role {
    private static MainRole instance = null;


    //  原本固有的属性
    private int strength_;//力量，提升基础伤害
    private int dexterity_;//灵巧，提升基础获得护甲值

    private int draw_;// 原本抽牌数量

    private boolean keepCard = false;

    //玩家的独特属性
    private int MP_max;
    private int MP_current;

    private int draw; // 战斗中的抽牌数量
    private int potionBag;
    private int gold;

    private boolean untreatable; // 无法治疗
    private boolean freeCard; // 卡牌的费用为0, 为梅林法杖准备的变量

    private ArrayList<Card> deck_ = new ArrayList<Card>();// 原卡组（有序排列）
    public ArrayList<Card> deck;// 战斗中卡组（无序排列）

    public ArrayList<Equipment> equipments;

    public ArrayList<String> cardEffects;


    public HashMap<String, Integer> cardEffectsMap;

    public static MainRole getInstance() {
        if (instance == null) {
            System.out.println("初始化主角类");
            instance = new MainRole(85, "LeadingActor/MajorActor4.j3o");
        }
        return instance;
    }


    public MainRole(int HP, String src) {
        super(HP, src, ROLE.MAINROLE);

        this.strength_ = 0;
        this.dexterity_ = 0;
        this.draw_ = 5;
        this.potionBag = 3;
        this.gold = 0;
        this.deck = new ArrayList<Card>();
        this.cardEffects = new ArrayList<String>();

        this.cardEffectsMap = new HashMap<>();
        this.MP_max = 6;
        this.MP_current = 6;
        this.untreatable = false;
        this.freeCard = false;
        this.equipments = new ArrayList<Equipment>();
        this.deck_.add(new SnakeSkinOperation());
        this.deck_.add(new Slash());
        this.deck_.add(new IceSlash());
        this.deck_.add(new FireSlash());
        this.deck_.add(new GoldSlash());
        this.deck_.add(new LightSlash());
        this.deck_.add(new SoilSlash());
        this.deck_.add(new RedFlameChop());
        this.deck_.add(new WoundedStrike());
        this.deck_.add(new Defense());
        this.deck_.add(new Defense());
        this.deck_.add(new Defense());
        this.deck_.add(new Defense());
        this.deck_.add(new Defense());
        this.deck_.add(new MagicSheild());
        this.deck_.add(new WhirlingShield());
        this.deck_.add(new Heal());


    }


    public void startBattle() {
        for (Equipment equipment : equipments) {
            if (equipment.getOpportunity() == Equipment.Opportunity.STARTB)
                equipment.fun();
            for (Enemy enemy : EnemyState.getInstance().getEnemies()) {
                enemy.updateHints();
            }
        }

        this.setStrength(this.strength_);

        this.setDexterity(this.dexterity_);

        // 将卡组复制一份,因为战斗中可能会修改卡组
        deck = new ArrayList<Card>(Arrays.asList(new Card[deck_.size()]));
        Collections.copy(deck, deck_);
        Collections.shuffle(deck);
        //将卡组加入抽牌堆
        app.getStateManager().getState(DecksState.class).addToDraw(this.deck);
        this.draw = this.draw_; // 复制一回合中的抽牌数
        this.MP_current = this.MP_max;
        this.strength = this.strength_;  // 复制力量
        this.dexterity = this.dexterity_; // 复制敏捷
    }

    public void getCard(Card... cards) {
        deck_.addAll(Arrays.asList(cards));
        for (Equipment equipment : equipments) {
            if (equipment.getOpportunity() == Equipment.Opportunity.GETCARD)
                equipment.fun();
        }
    }

    public Boolean getEquipment(Equipment equipment) {
        // 先查看是否已经有这件装备
        for (Equipment e : equipments) {
            if (e.getName().equals(equipment.getName()))
                return false;
        }
        equipment.setImage(app.getAssetManager());
        this.equipments.add(equipment);
        if (equipment.getOpportunity() == Equipment.Opportunity.GET) {
            equipment.fun();
        }
        return true;
    }

    public void removeCard(Card card) {
        this.deck_.remove(card);
    }

    //每回合开始时候的抽牌
    public void startTurn() {


        if (!(this.cardEffects.contains("残影") || this.cardEffects.contains("残影+"))) {
            this.block = 0;
        } else {
            this.cardEffects.remove("残影");
            this.cardEffects.remove("残影+");
        }
        if (this.stun.getDuration() > 0) {
            return;
        }
        if (cardEffects.contains("晶化")) {
            this.drawCards(5);
            this.cardEffects.remove("晶化");
        }
        if (cardEffects.contains("晶化+")) {
            this.drawCards(8);
            this.cardEffects.remove("晶化+");
        }

        if (cardEffects.contains("生命剑术")) {
            this.drawCards(3);
            this.cardEffects.remove("生命剑术");
        }

        if (cardEffects.contains("生命剑术+")) {
            this.drawCards(3);
            this.cardEffects.remove("生命剑术+");
        }


        if (cardEffectsMap.containsKey("藏剑")) {
            int num = cardEffectsMap.get("藏剑");
            cardEffectsMap.put("藏剑", num + 2);
        }
        if (cardEffectsMap.containsKey("藏剑+")) {
            int num = cardEffectsMap.get("藏剑+");
            cardEffectsMap.put("藏剑+", num + 3);
        }

        if (cardEffectsMap.containsKey("炸弹")) {
            int duration = cardEffectsMap.get("炸弹");
            if (duration == 0) {
                for (Enemy enemy : EnemyState.getInstance().getEnemies()) {
                    enemy.getDamage(35);
                }
                cardEffectsMap.remove("炸弹");
            } else {
                cardEffectsMap.put("炸弹", duration - 1);
            }
        }

        if (cardEffectsMap.containsKey("炸弹+")) {
            int duration = cardEffectsMap.get("炸弹+");
            if (duration == 0) {
                for (Enemy enemy : EnemyState.getInstance().getEnemies()) {
                    enemy.getDamage(40);
                }
                cardEffectsMap.remove("炸弹+");
            } else {
                cardEffectsMap.put("炸弹+", duration - 1);
            }
        }

        while (cardEffects.contains("盾牌激发")) {
            this.block += 8;
            cardEffects.remove("盾牌激发");
        }


        while (cardEffects.contains("盾牌激发+")) {
            this.block += 8;
            cardEffects.remove("盾牌激发");
        }

        this.keepCard = false;
        if (this.excite.getDuration() > 0) {
            this.drawCards(1 + this.draw, true);
        } else {
            this.drawCards(this.draw, true);
        }
        for (Equipment equipment : equipments) {
            if (equipment.getOpportunity() == Equipment.Opportunity.STARTT)
                equipment.fun();
        }

        this.MP_current = this.MP_max;

    }


    public void endBattle() {
        for (Equipment equipment : equipments) {
            if (equipment.getOpportunity() == Equipment.Opportunity.ENDB)
                equipment.fun();
        }
        this.cardEffectsMap.clear();
        this.cardEffects.clear();
        this.deck.clear();


        vulnerable.setDuration(0);
        weak.setDuration(0);
        stun.setDuration(0);
        silence.setDuration(0);
        shield.setDuration(0);
        poison.setDuration(0);
        intangible.setDuration(0);
        excite.setDuration(0);
        erode.setDuration(0);
        disarm.setDuration(0);
        bleeding.setDuration(0);
        artifact.setTimes(0);
        dodge.setTimes(0);
        dexterity = 0;
        strength = 0;
        atk = 0;
        this.block = 0;
        // 人物被动"苟"
        this.treat((int) (0.1 * (this.getTotalHP() - this.getHP())));
    }


    public int getMP_current() {
        return MP_current;
    }

    public void setMP_current(int MP_current) {
        this.MP_current = MP_current;
    }

    public void gainMP(int num) {
        this.MP_current = Math.min(this.MP_current + num, this.MP_max);
    }

    public boolean decMP(int num) {
        if (freeCard) {
            freeCard = false;
            return true;
        }
        if (num > MP_current) {
            return false;
        } else {
            this.MP_current -= num;
            return true;
        }
    }

    public int computeDamage(int num, AttackCard.PROPERTY property) {
        for (Equipment equipment : equipments) {
            if (equipment.getOpportunity() == Equipment.Opportunity.ATTACK)
                equipment.fun();
        }
        if (this.cardEffects.contains("分身")) {
            num = num * 2;
            cardEffects.remove("分身");
        }

        if (this.cardEffectsMap.containsKey("藏剑")) {
            num += this.cardEffectsMap.get("藏剑");
            cardEffectsMap.remove("藏剑");
        }

        if (this.cardEffectsMap.containsKey("藏剑+")) {
            num += this.cardEffectsMap.get("藏剑+");
            cardEffectsMap.remove("藏剑+");
        }

        if (this.cardEffectsMap.containsKey("晶化")) {
            int layer = this.cardEffectsMap.get("晶化");
            if (layer == 0) cardEffectsMap.remove("晶化");
            else {
                this.cardEffectsMap.put("晶化", layer - 1);
                return super.computeDamage(num, AttackCard.PROPERTY.GOLD);
            }
        }

        if (this.cardEffectsMap.containsKey("晶化+")) {
            int layer = this.cardEffectsMap.get("晶化+");
            if (layer == 0) cardEffectsMap.remove("晶化+");
            else {
                this.cardEffectsMap.put("晶化+", layer - 1);
                return super.computeDamage(num, AttackCard.PROPERTY.GOLD);
            }
        }


        if (this.cardEffectsMap.containsKey("生命剑术")) {
            int layer = this.cardEffectsMap.get("生命剑术");
            if (layer == 0) cardEffectsMap.remove("生命剑术");
            else {
                this.cardEffectsMap.put("生命剑术", layer - 1);
                return super.computeDamage(num, AttackCard.PROPERTY.WOOD);
            }
        }

        if (this.cardEffectsMap.containsKey("生命剑术+")) {
            int layer = this.cardEffectsMap.get("生命剑术+");
            if (layer == 0) cardEffectsMap.remove("生命剑术+");
            else {
                this.cardEffectsMap.put("生命剑术+", layer - 1);
                return super.computeDamage(num, AttackCard.PROPERTY.WOOD);
            }
        }

        return super.computeDamage(num, property);
    }

    @Override
    public int getDamage(int damage) {
        LeadingActorState.getDamage(damage);
        for (Equipment equipment : equipments) {
            if (equipment.getOpportunity() == Equipment.Opportunity.GETD)
                equipment.fun();
        }
        if (cardEffects.contains("神御格挡") || cardEffects.contains("神御格挡+")) {
            if (Math.random() * 5 <= 1) {
                this.getBuff(new Dodge(this, 1));
            }
        }

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

        if (cardEffects.contains("魔法盾") || cardEffects.contains("魔法盾+")) {
            damage = (int) (damage * 0.5);
            cardEffects.remove("魔法盾");
            cardEffects.remove("魔法盾+");
        }

        damage = (int) (damage * this.getMultiplyingGetDamage());
        if (this.cardEffects.contains("逆转反击"))
            for (Enemy enemy : EnemyState.getInstance().getEnemies()) {
                enemy.getDamage((int) (damage * 0.3));
            }

        if (this.cardEffects.contains("逆转反击+"))
            for (Enemy enemy : EnemyState.getInstance().getEnemies()) {
                enemy.getDamage((int) (damage * 0.4));
            }

        if (this.block >= damage) {
            this.block -= damage;
            return 0;
        } else {
            this.block = 0;
            if (this.HP > damage - block + this.bleeding.getDuration()) {
                this.HP -= damage - block;
                if (this.bleeding.getDuration() > 0) {
                    this.bleeding.triggerFunc();
                }
                return damage - block;
            } else {
                if (this.cardEffects.contains("不屈")) {
                    int oldHP = this.HP;
                    this.HP = 1;
                    return oldHP - 1;
                }
                if (this.cardEffects.contains("不屈+")) return 0;

                this.HP -= damage - block;
                if (this.bleeding.getDuration() > 0) {
                    this.bleeding.triggerFunc();
                }
                return damage - block;
            }

        }
    }

    @Override
    public void endTurn() {
        for (Equipment equipment : equipments) {
            if (equipment.getOpportunity() == Equipment.Opportunity.ENDT)
                equipment.fun();
        }
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


        if (this.cardEffects.contains("蓄势")) {
            this.strength -= 2;
        }
        if (this.cardEffects.contains("蓄势+")) {
            this.strength -= 3;
        }


        if (this.keepCard) {
            return;
        } else {
            DecksState deckState = app.getStateManager().getState(DecksState.class);
            HandCardsState handCardsState = app.getStateManager().getState(HandCardsState.class);
            ArrayList<Card> handCards = handCardsState.getHandCards();
            for (Card card : handCards) {
                if (card.isEthereal()) {
                    deckState.addToExhaust(card);
                } else {
                    deckState.addToDrop(card);
                }
            }
            handCardsState.getHandCards().clear();
        }

    }

    public ArrayList<Card> getDeck_() {
        return deck_;
    }

    public void setDeck_(ArrayList<Card> deck_) {
        this.deck_ = deck_;
    }


    public int getMP_max() {
        return MP_max;
    }

    public int getGold() {
        return gold;
    }

    public void getGold(int num) {
        this.gold += num;
    }

    public void drawCards(int num) {

        this.app.getStateManager().getState(HandCardsState.class).drawCards(num, false);
    }

    public void drawCards(int num, boolean withAdjust) {
        this.app.getStateManager().getState(HandCardsState.class).drawCards(num, withAdjust);
    }


    public void setKeepCard(boolean keepCard) {
        this.keepCard = keepCard;
    }


    public void addCardEffect(String cardName) {
        this.cardEffects.add(cardName);
    }


    public void setMP_max(int MP_max) {
        this.MP_max = MP_max;
    }

    @Override
    public void treat(int number) {
        if (this.untreatable)
            number = 1;
        super.treat(number);
    }

    public boolean isUntreatable() {
        return untreatable;
    }

    public void setUntreatable(boolean untreatable) {
        this.untreatable = untreatable;
    }

    public void setGold(int gold) {
        this.gold = gold >= 0 ? gold : 0;
    }


    public int getStrengthForever() {
        return strength_;
    }

    public void setStrengthForever(int strength_) {
        this.strength_ = strength_;
    }

    public int getDexterityForever() {
        return dexterity_;
    }

    public void setDexterityForever(int dexterity_) {
        this.dexterity_ = dexterity_;
    }

    public boolean isFreeCard() {
        return freeCard;
    }

    public void setFreeCard(boolean freeCard) {
        this.freeCard = freeCard;
    }

    public ArrayList<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(ArrayList<Equipment> equipments) {
        this.equipments = equipments;
    }
}
