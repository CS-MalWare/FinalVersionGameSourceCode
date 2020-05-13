package gamesource.battleState.card.saber.skill;

import gamesource.battleState.card.AttackCard;
import gamesource.battleState.card.SkillCard;
import gamesource.battleState.character.Enemy;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;
import gamesource.battleState.utils.buffs.foreverBuffs.Dodge;

public class IceMagicShield extends SkillCard {
    public IceMagicShield() {
        super(OCCUPATION.SABER, "冰灵盾", 3, RARITY.RARE, "deal 10 water property damage to all enemies and gain 20 block");
        this.AOE = true;
    }

    public IceMagicShield(boolean upgraded) {
        super(OCCUPATION.SABER, "冰灵盾+", 3, RARITY.RARE, "deal 12 water property damage to all enemies and gain 25 block");
        this.AOE = true;
        this.upgraded = true;
    }


    @Override
    public boolean upgrade() {
        if (this.upgraded) {
            return false;
        } else {
            this.upgraded = true;
            this.setCardName("冰灵盾+");
            this.setDescription("deal 12 water property damage to all enemies and gain 25 block");
        }
        return true;
    }

    @Override
    public boolean use(Role... targets) {
        if (!upgraded) {
            for (Role target : targets) {
                if (!(target instanceof Enemy)) {
                    return false;
                }
                target.getDamage(MainRole.getInstance().computeDamage(10, AttackCard.PROPERTY.WATER));
            }
            MainRole.getInstance().gainBlock(20);
        } else {
            for (Role target : targets) {
                if (!(target instanceof Enemy)) {
                    return false;
                }
                target.getDamage(MainRole.getInstance().computeDamage(12, AttackCard.PROPERTY.WATER));
            }
            MainRole.getInstance().gainBlock(25);
        }

        return true;
    }

    @Override
    public boolean use(Role target) {
        if (!(target instanceof Enemy)) {
            return false;
        }

        if (!upgraded) {
            target.getDamage(MainRole.getInstance().computeDamage(10, AttackCard.PROPERTY.WATER));
            MainRole.getInstance().gainBlock(20);
        } else {

            target.getDamage(MainRole.getInstance().computeDamage(12, AttackCard.PROPERTY.WATER));
            MainRole.getInstance().gainBlock(25);
        }
        return true;
    }

}