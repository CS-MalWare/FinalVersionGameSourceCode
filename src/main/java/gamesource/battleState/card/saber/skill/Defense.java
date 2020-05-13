package gamesource.battleState.card.saber.skill;

import gamesource.battleState.card.SkillCard;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;
import gamesource.battleState.utils.buffs.foreverBuffs.Dodge;

public class Defense extends SkillCard {
    public Defense() {
        super(OCCUPATION.SABER, "格挡", 1, RARITY.COMMON, "Gain 5 block");

    }

    public Defense(boolean upgraded) {
        super(OCCUPATION.SABER, "格挡+", 1, RARITY.COMMON, "Gain 8 block");
        this.upgraded = true;
    }


    @Override
    public boolean upgrade() {
        if (this.upgraded) {
            return false;
        } else {
            this.upgraded = true;
            this.setCardName("格挡+");
            this.setDescription("Gain 8 block");
        }
        return true;
    }


    @Override
    public boolean use(Role target) {
        if (!upgraded) {
            MainRole.getInstance().gainBlock(5);
        } else {
            MainRole.getInstance().gainBlock(8);
        }

        return true;
    }

}
