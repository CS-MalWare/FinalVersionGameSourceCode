package gamesource.battleState.card.saber.skill;

import gamesource.battleState.card.SkillCard;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;

public class Regenerate extends SkillCard {
    public Regenerate() {
        super(OCCUPATION.SABER, "重振", 0, RARITY.COMMON, "draw one card");

    }

    public Regenerate(boolean upgraded) {
        super(OCCUPATION.SABER, "重振+", 0, RARITY.COMMON, "draw two card");
        this.upgraded = true;
    }


    @Override
    public boolean upgrade() {
        if (this.upgraded) {
            return false;
        } else {
            this.upgraded = true;
            this.setCardName("重振+");
            this.setDescription("draw two card");
        }
        return true;
    }


    @Override
    public boolean use(Role target) {
        if (!upgraded) {
            MainRole.getInstance().drawCards(1);
        } else {
            MainRole.getInstance().drawCards(2);

        }

        return true;
    }

}
