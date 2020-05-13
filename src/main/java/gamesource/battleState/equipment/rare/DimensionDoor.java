package gamesource.battleState.equipment.rare;

import gamesource.battleState.equipment.Equipment;

public class DimensionDoor extends Equipment {
    private boolean canResetBattle = true;
    public DimensionDoor() {
        super("dimension door", "任意门", "Every battle has a chance to start again", EquipmentDegree.RARE, Opportunity.ENDB);
    }

    @Override
    public void fun() {
        if(canResetBattle) {
            //TODO 有机会重置一次战斗
        }
        canResetBattle = false;
    }

    @Override
    public void resetBuff() {
        canResetBattle = true;
    }
}
