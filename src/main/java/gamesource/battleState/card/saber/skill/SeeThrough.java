package gamesource.battleState.card.saber.skill;

import gamesource.battleState.card.SkillCard;
import gamesource.battleState.character.Enemy;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;
import gamesource.battleState.utils.buffs.limitBuffs.Vulnerable;
import gamesource.battleState.utils.buffs.limitBuffs.Weak;

public class SeeThrough extends SkillCard {
    public SeeThrough() {
        super(OCCUPATION.SABER, "看破", 2, RARITY.COMMON, "apply 3 vulnerable and weakness to target");

    }

    public SeeThrough(boolean upgraded) {
        super(OCCUPATION.SABER, "看破+", 2, RARITY.COMMON, "apply 5 vulnerable and weakness to target");
        this.upgraded = true;
    }


    @Override
    public boolean upgrade() {
        if (this.upgraded) {
            return false;
        } else {
            this.upgraded = true;
            this.setCardName("看破+");
            this.setDescription("apply 5 vulnerable and weakness to target");
        }
        return true;
    }


    @Override
    public boolean use(Role target) {
        if (!(target instanceof Enemy)) return false;
        if (!upgraded) {
            target.getBuff(new Vulnerable(target, 3));
            target.getBuff(new Weak(target, 3));
        } else {
            target.getBuff(new Vulnerable(target, 5));
            target.getBuff(new Weak(target, 5));
        }
        return true;
    }

}
