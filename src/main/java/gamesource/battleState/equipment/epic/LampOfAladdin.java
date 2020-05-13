package gamesource.battleState.equipment.epic;

import gamesource.battleState.appState.HandCardsState;
import gamesource.battleState.card.Card;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.equipment.Equipment;

import java.util.ArrayList;

public class LampOfAladdin extends Equipment {
    public LampOfAladdin() {
        super("Lamp of Aladdin", "阿拉丁神灯", "Each round, randomly upgrade a card, and then give the card exhaust effect", EquipmentDegree.EPIC, Opportunity.STARTT);
    }

    @Override
    public void fun() {
        ArrayList<Card> handcards = HandCardsState.getInstance().getHandCards();
        Card chosen = handcards.get((int) (Math.random() * handcards.size()));
        if(chosen.upgrade()){
            chosen.setImage(MainRole.getInstance().getApp().getAssetManager());
            chosen.setExhaust(true);
        }
    }
}
