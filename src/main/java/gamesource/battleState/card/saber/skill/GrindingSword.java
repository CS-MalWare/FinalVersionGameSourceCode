package gamesource.battleState.card.saber.skill;

import gamesource.battleState.card.SkillCard;
import gamesource.battleState.character.Enemy;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;
import gamesource.battleState.utils.buffs.limitBuffs.Vulnerable;
import gamesource.battleState.utils.buffs.limitBuffs.Weak;

public class GrindingSword extends SkillCard {
    public GrindingSword() {
        super(OCCUPATION.SABER, "磨剑", 1, RARITY.COMMON, "take 5 damage and gains 3 strength");

    }

    public GrindingSword(boolean upgraded) {
        super(OCCUPATION.SABER, "磨剑+", 1, RARITY.COMMON, "take 4 damage and gains 4 strength");
        this.upgraded = true;
    }


    @Override
    public boolean upgrade() {
        if (this.upgraded) {
            return false;
        } else {
            this.upgraded = true;
            this.setCardName("磨剑+");
            this.setDescription("take 4 damage and gains 4 strength");
        }
        return true;
    }


    @Override
    public boolean use(Role target) {
        if (!upgraded) {
            MainRole.getInstance().getDamage(5);
            MainRole.getInstance().setStrength(MainRole.getInstance().getStrength() + 3);
        } else {
            MainRole.getInstance().getDamage(4);
            MainRole.getInstance().setStrength(MainRole.getInstance().getStrength() + 4);
        }
        return true;
    }

}
