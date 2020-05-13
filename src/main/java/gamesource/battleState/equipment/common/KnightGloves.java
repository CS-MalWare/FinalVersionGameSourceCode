package gamesource.battleState.equipment.common;

import gamesource.battleState.character.MainRole;
import gamesource.battleState.equipment.Equipment;

public class KnightGloves extends Equipment {
    private boolean firstTurn = true;
    public KnightGloves() {
        super("Knight gloves", "骑士手套", "Draw 2 extra cards in the first turn of a battle", EquipmentDegree.COMMON, Opportunity.STARTT);
    }

    @Override
    public void fun() {
        if(firstTurn){
            MainRole.getInstance().drawCards(2,true);
            firstTurn = false;
        }
    }

    @Override
    public void resetBuff() {
        firstTurn = true;
    }
}
