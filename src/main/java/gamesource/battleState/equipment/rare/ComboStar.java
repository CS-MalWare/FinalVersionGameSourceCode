package gamesource.battleState.equipment.rare;

import gamesource.battleState.character.MainRole;
import gamesource.battleState.equipment.Equipment;

public class ComboStar extends Equipment {
    private int useCount = 1;
    public ComboStar() {
        super("Combo star", "连击星", "In each turn, when a third card is played, a card is drawn", EquipmentDegree.RARE, Opportunity.USE);
    }

    @Override
    public void fun() {
        if(useCount==3){
            MainRole.getInstance().drawCards(1);
        }
        useCount++;
    }

    @Override
    public void resetBuff() {
        useCount = 1;
    }
}
