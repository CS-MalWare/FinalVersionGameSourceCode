package gamesource.battleState.card;

import gamesource.battleState.card.Card;
import gamesource.battleState.character.Enemy;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;


public abstract class AttackCard extends Card {
    protected int damage;
    protected int times;

    public enum PROPERTY {NONE, FIRE, GOLD, WOOD, WATER, SOIL}

    protected PROPERTY property;


    public AttackCard(OCCUPATION occupation, String name, int cost, RARITY rarity, String description, int damage, int times) {
        super(occupation, name, cost, TYPE.ATTACK, rarity, description);
        this.damage = damage;
        this.times = times;
        this.property = PROPERTY.NONE;
        this.AOE = false;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }


    // 对目标造成伤害,如果卡牌为群体攻击,则对多个目标造成伤害
    @Override
    public boolean use(Role... targets) {
        for (Role target : targets) {
            if (!(target instanceof Enemy)) {
                return false;
            }
            for (int i = 0; i < this.times; i++) {
                target.getDamage(MainRole.getInstance().computeDamage(this.damage, this.property));
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        return true;
    }


    public boolean use(Role target) {

        if (!(target instanceof Enemy)) {
            return false;
        }
        for (int i = 0; i < this.times; i++) {
            target.getDamage(MainRole.getInstance().computeDamage(this.damage, this.property));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        return true;
    }


}
