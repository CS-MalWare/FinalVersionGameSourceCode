package gamesource.battleState.equipment.rare;

import gamesource.battleState.character.MainRole;
import gamesource.battleState.equipment.Equipment;

public class StickyNote extends Equipment {
    private boolean firstTurn = true;
    public StickyNote() {
        super("sticky note", "便签纸", "Draw 4 cards at the first turn of each battle", EquipmentDegree.RARE, Opportunity.STARTT);
    }

    @Override
    public void fun() {
        if(firstTurn){
            MainRole.getInstance().drawCards(4);
            firstTurn = false;
        }
    }

    @Override
    public void resetBuff() {
        firstTurn = true;
    }
}
