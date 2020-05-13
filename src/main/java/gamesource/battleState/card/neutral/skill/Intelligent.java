package gamesource.battleState.card.neutral.skill;

import gamesource.battleState.card.SkillCard;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;

public class Intelligent extends SkillCard {
    public Intelligent() {
        super(OCCUPATION.NEUTRAL, "机制", 0, RARITY.COMMON, "draw 2 cards");
    }

    public Intelligent(boolean upgraded) {
        super(OCCUPATION.NEUTRAL, "机制+", 0, RARITY.COMMON, "draw 3 cards");
        this.upgraded = true;
    }

    @Override
    public boolean upgrade() {
        if (this.upgraded) {
            return false;
        } else {
            this.upgraded = true;

            this.setCardName("机制+");
            this.setDescription("draw 3 cards");

        }
        return true;
    }

    @Override
    public boolean use(Role target) {
        if (!upgraded)
            MainRole.getInstance().drawCards(2);
        else {
            MainRole.getInstance().drawCards(3);
        }
        return true;
    }

}
