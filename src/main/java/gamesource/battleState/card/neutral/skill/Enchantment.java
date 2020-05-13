package gamesource.battleState.card.neutral.skill;

import gamesource.battleState.appState.DecksState;
import gamesource.battleState.appState.HandCardsState;
import gamesource.battleState.card.Card;
import gamesource.battleState.card.SkillCard;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;

import java.util.ArrayList;

public class Enchantment extends SkillCard {
    public Enchantment() {
        super(OCCUPATION.NEUTRAL, "魔化", 3, RARITY.LEGENDARY, "upgrade all skill cards in this battle, exhaust");
        this.exhaust = true;

    }

    public Enchantment(boolean upgraded) {
        super(OCCUPATION.NEUTRAL, "魔化+", 3, RARITY.LEGENDARY, "upgrade all skill cards in this battle, gain 3 MP, exhaust");
        this.exhaust = true;
        this.upgraded = true;
    }


    @Override
    public boolean upgrade() {
        if (this.upgraded) {
            return false;
        } else {
            this.upgraded = true;
            this.setCardName("魔化+");
            this.setDescription("upgrade all skill cards in this battle, gain 3 MP, exhaust");

        }
        return true;
    }


    @Override
    public boolean use(Role target) {
        ArrayList<Card> handCards = HandCardsState.getInstance().getHandCards();
        for (Card card : handCards) {
            if (card.getType() == TYPE.SKILL) {
                card.upgrade();
                card.setImage(MainRole.getInstance().getApp().getAssetManager());
            }
        }
        ArrayList<Card> drawDeck = DecksState.getInstance().getDrawDeck();
        for (Card card : drawDeck) {
            if (card.getType() == TYPE.SKILL) {
                card.upgrade();
                card.setImage(MainRole.getInstance().getApp().getAssetManager());
            }
        }
        ArrayList<Card> dropDeck = DecksState.getInstance().getDropDeck();
        for (Card card : dropDeck) {
            if (card.getType() == TYPE.SKILL) {
                card.upgrade();
                card.setImage(MainRole.getInstance().getApp().getAssetManager());
            }
        }
        ArrayList<Card> exhaustDeck = DecksState.getInstance().getExhaustDeck();
        for (Card card : exhaustDeck) {
            if (card.getType() == TYPE.SKILL) {
                card.upgrade();
                card.setImage(MainRole.getInstance().getApp().getAssetManager());
            }
        }
        if (upgraded) {
            MainRole.getInstance().gainMP(3);
        }
        return true;
    }

}
