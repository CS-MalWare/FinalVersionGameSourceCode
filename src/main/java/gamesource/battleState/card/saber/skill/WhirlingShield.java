package gamesource.battleState.card.saber.skill;

import gamesource.battleState.card.SkillCard;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;

public class WhirlingShield extends SkillCard {
    public WhirlingShield() {
        super(OCCUPATION.SABER, "旋盾", 3, RARITY.COMMON, "Gain 25 block");

    }

    public WhirlingShield(boolean upgraded) {
        super(OCCUPATION.SABER, "旋盾+", 3, RARITY.COMMON, "Gain 30 block");
        this.upgraded = true;
    }


    @Override
    public boolean upgrade() {
        if (this.upgraded) {
            return false;
        } else {
            this.upgraded = true;
            this.setCardName("旋盾+");
            this.setDescription("Gain 30 block");
        }
        return true;
    }


    @Override
    public boolean use(Role target) {
        if (!upgraded) {
            MainRole.getInstance().gainBlock(25);
        } else {
            MainRole.getInstance().gainBlock(30);
        }

        return true;
    }

}
