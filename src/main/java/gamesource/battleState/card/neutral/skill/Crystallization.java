package gamesource.battleState.card.neutral.skill;

import gamesource.battleState.card.SkillCard;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;

public class Crystallization extends SkillCard {
    public Crystallization() {
        super(OCCUPATION.NEUTRAL, "晶化", 4, RARITY.COMMON, "next 5 attacks become gold property, draw 5 more cards in the next round");
    }

    public Crystallization(boolean upgraded) {
        super(OCCUPATION.NEUTRAL, "晶化+", 4, RARITY.COMMON, "next 8 attacks become gold property, draw 8 more cards in the next round");
        this.upgraded = true;
    }

    @Override
    public boolean upgrade() {
        if (this.upgraded) {
            return false;
        } else {
            this.upgraded = true;
            this.setCardName("晶化+");
            this.setDescription("next 8 attacks become gold property, draw 8 more cards in the next round");

        }
        return true;
    }

    @Override
    public boolean use(Role target) {

        if (!upgraded) {
            MainRole.getInstance().addCardEffect(this.getCardName());
            MainRole.getInstance().cardEffectsMap.put(this.getCardName(), 5);

        } else {
            MainRole.getInstance().addCardEffect(this.getCardName());
            MainRole.getInstance().cardEffectsMap.put(this.getCardName(), 8);

        }
        return true;
    }

}
