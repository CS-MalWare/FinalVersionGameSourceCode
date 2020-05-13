package gamesource.battleState.card.saber.skill;

import gamesource.battleState.card.SkillCard;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;

public class RaiseShield extends SkillCard {
    public RaiseShield() {
        super(OCCUPATION.SABER, "举盾", 2, RARITY.COMMON, "Gain 10 block and draw 1 card");

    }

    public RaiseShield(boolean upgraded) {
        super(OCCUPATION.SABER, "举盾+", 2, RARITY.COMMON, "Gain 12 block and draw 2 card");
        this.upgraded = true;
    }


    @Override
    public boolean upgrade() {
        if (this.upgraded) {
            return false;
        } else {
            this.upgraded = true;
            this.setCardName("举盾+");
            this.setDescription("Gain 12 block and draw 2 card");
        }
        return true;
    }


    @Override
    public boolean use(Role target) {
        if (!upgraded) {
            MainRole.getInstance().gainBlock(10);
            MainRole.getInstance().drawCards(1);
        } else {
            MainRole.getInstance().gainBlock(12);
            MainRole.getInstance().drawCards(2);

        }

        return true;
    }

}
