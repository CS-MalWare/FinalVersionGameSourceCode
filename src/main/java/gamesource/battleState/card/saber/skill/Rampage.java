package gamesource.battleState.card.saber.skill;

import gamesource.battleState.appState.DecksState;
import gamesource.battleState.appState.HandCardsState;
import gamesource.battleState.card.Card;
import gamesource.battleState.card.SkillCard;
import gamesource.battleState.card.saber.attack.BloodthirstyChop;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;
import com.jme3.math.Vector3f;
import gamesource.battleState.utils.buffs.foreverBuffs.Dodge;

import java.util.ArrayList;

public class Rampage extends SkillCard {
    public Rampage() {
        super(OCCUPATION.SABER, "暴走", 4, RARITY.LEGENDARY, "gain 1 dodge, replace all normal cards in the card group with bloodthirsty chop.");

    }

    public Rampage(boolean upgraded) {
        super(OCCUPATION.SABER, "暴走+", 4, RARITY.LEGENDARY, "gain 6 dodge, replace all normal cards in the card group with bloodthirsty chop.");
        this.upgraded = true;
    }


    @Override
    public boolean upgrade() {
        if (this.upgraded) {
            return false;
        } else {
            this.upgraded = true;
            this.setCardName("暴走+");
            this.setDescription("gain 6 dodge, replace all normal cards in the card group with bloodthirsty chop.");
        }
        return true;
    }


    @Override
    public boolean use(Role target) {
        ArrayList<Card> handCards = HandCardsState.getInstance().getHandCards();
        for (int i = 0; i < handCards.size(); i++) {
            if (handCards.get(i).getRarity() == RARITY.COMMON) {
                Vector3f oldLocation = handCards.get(i).getLocalTranslation();
                handCards.get(i).removeFromParent();
                handCards.set(i, new BloodthirstyChop());
                handCards.get(i).setImage(MainRole.getInstance().getApp().getAssetManager());
                handCards.get(i).setLocalTranslation(oldLocation);
                HandCardsState.getInstance().getRootNode().attachChild(handCards.get(i));
            }
        }
        ArrayList<Card> drawDeck = DecksState.getInstance().getDrawDeck();
        for (int i = 0; i < drawDeck.size(); i++) {
            if (drawDeck.get(i).getRarity() == RARITY.COMMON) {
                Vector3f oldLocation = drawDeck.get(i).getLocalTranslation();
                drawDeck.get(i).removeFromParent();
                drawDeck.set(i, new BloodthirstyChop());
                drawDeck.get(i).setImage(MainRole.getInstance().getApp().getAssetManager());
                drawDeck.get(i).setLocalTranslation(oldLocation);
                HandCardsState.getInstance().getRootNode().attachChild(drawDeck.get(i));
            }
        }
        ArrayList<Card> dropDeck = DecksState.getInstance().getDropDeck();
        for (int i = 0; i < dropDeck.size(); i++) {
            if (dropDeck.get(i).getRarity() == RARITY.COMMON) {
                Vector3f oldLocation = dropDeck.get(i).getLocalTranslation();
                dropDeck.get(i).removeFromParent();
                dropDeck.set(i, new BloodthirstyChop());
                dropDeck.get(i).setImage(MainRole.getInstance().getApp().getAssetManager());
                dropDeck.get(i).setLocalTranslation(oldLocation);
                HandCardsState.getInstance().getRootNode().attachChild(dropDeck.get(i));
            }
        }
        ArrayList<Card> exhaustDeck = DecksState.getInstance().getExhaustDeck();
        for (int i = 0; i < exhaustDeck.size(); i++) {
            if (exhaustDeck.get(i).getRarity() == RARITY.COMMON) {
                Vector3f oldLocation = exhaustDeck.get(i).getLocalTranslation();
                exhaustDeck.get(i).removeFromParent();
                exhaustDeck.set(i, new BloodthirstyChop());
                exhaustDeck.get(i).setImage(MainRole.getInstance().getApp().getAssetManager());
                exhaustDeck.get(i).setLocalTranslation(oldLocation);
                HandCardsState.getInstance().getRootNode().attachChild(exhaustDeck.get(i));
            }
        }
        if (!upgraded) {
            MainRole.getInstance().getBuff(new Dodge(MainRole.getInstance(), 1));
        } else {
            MainRole.getInstance().getBuff(new Dodge(MainRole.getInstance(), 6));

        }
        return true;
    }

}
