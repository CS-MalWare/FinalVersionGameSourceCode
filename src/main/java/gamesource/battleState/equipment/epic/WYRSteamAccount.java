package gamesource.battleState.equipment.epic;

import gamesource.battleState.card.Card;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.equipment.Equipment;

import java.util.ArrayList;

public class WYRSteamAccount extends Equipment {

    public WYRSteamAccount() {
        super(" WYR's Steam account", "王逸润的steam账号", "WYR's Steam account: Upgrade the attack cards every time you gain them", EquipmentDegree.EPIC, Opportunity.GETCARD);


    }

    @Override
    public void fun() {
        ArrayList<Card> deck = MainRole.getInstance().getDeck_();
        Card card = deck.get(deck.size() - 1);
        if (card.getType() == Card.TYPE.ATTACK) {
            card.upgrade();
            card.setImage(MainRole.getInstance().getApp().getAssetManager());
        }
    }
}
