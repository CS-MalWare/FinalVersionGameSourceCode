package gamesource.battleState.card.neutral.skill;

import gamesource.battleState.appState.DecksState;
import gamesource.battleState.appState.HandCardsState;
import gamesource.battleState.card.Card;
import gamesource.battleState.card.SkillCard;
import gamesource.battleState.character.Role;

import java.util.ArrayList;

public class SecretAttack extends SkillCard {
    public SecretAttack() {
        super(OCCUPATION.NEUTRAL, "秘密攻击", 0, RARITY.COMMON, "draw a attack card, exhaust");
        this.exhaust = true;

    }

    public SecretAttack(boolean upgraded) {
        super(OCCUPATION.NEUTRAL, "秘密攻击+", 0, RARITY.COMMON, "draw a attack card");

    }


    @Override
    public boolean upgrade() {
        if (this.upgraded) {
            return false;
        } else {
            this.upgraded = true;
            this.exhaust = false;
            this.setCardName("秘密攻击+");
            this.setDescription("draw a attack card");

        }
        return true;
    }


    @Override
    public boolean use(Role target) {
        ArrayList<Card> drawDeck = DecksState.getInstance().getDrawDeck();
        for (Card card : drawDeck) {
            if (card.getType() == TYPE.ATTACK) {
                drawDeck.remove(card);
                DecksState.getInstance().updateDrawNum();
                HandCardsState.getInstance().addToHand(card);
            }
            return true;
        }
        return true;
    }

}
