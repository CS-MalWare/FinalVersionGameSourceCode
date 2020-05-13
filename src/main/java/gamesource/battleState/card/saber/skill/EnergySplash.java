package gamesource.battleState.card.saber.skill;

import gamesource.battleState.appState.DecksState;
import gamesource.battleState.appState.HandCardsState;
import gamesource.battleState.card.Card;
import gamesource.battleState.card.SkillCard;
import gamesource.battleState.character.Enemy;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;

import java.util.ArrayList;

public class EnergySplash extends SkillCard {
    public EnergySplash() {
        super(OCCUPATION.SABER, "能量飞溅", 2, RARITY.EPIC, "draw 3 cards, deal the total damage of the 3 cards MP");

    }

    public EnergySplash(boolean upgraded) {
        super(OCCUPATION.SABER, "能量飞溅+", 2, RARITY.EPIC, "draw 4 cards, deal the total damage of the 4 cards MP");
        this.upgraded = true;
    }


    @Override
    public boolean upgrade() {
        if (this.upgraded) {
            return false;
        } else {
            this.upgraded = true;
            this.setCardName("能量飞溅+");
            this.setDescription("draw 4 cards, deal the total damage of the 4 cards MP");
        }
        return true;
    }


    @Override
    public boolean use(Role target) {
        if (!(target instanceof Enemy)) return false;
        if (!upgraded) {
            ArrayList<Card> cards = DecksState.getInstance().drawCard(3);
            int totalMP = 0;
            for (Card card : cards) {
                totalMP += card.getCost();
            }
            HandCardsState.getInstance().addToHand(cards);
            target.getDamage(MainRole.getInstance().computeDamage(totalMP));
        } else {
            ArrayList<Card> cards = DecksState.getInstance().drawCard(4);
            int totalMP = 0;
            for (Card card : cards) {
                totalMP += card.getCost();
            }
            HandCardsState.getInstance().addToHand(cards);
            target.getDamage(MainRole.getInstance().computeDamage(totalMP));
        }

        return true;
    }

}
