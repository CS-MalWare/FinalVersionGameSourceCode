package gamesource.battleState.equipment.common;

import gamesource.battleState.equipment.Equipment;

public class SellManualOfGSW extends Equipment {
    private boolean canUse = true;
    public SellManualOfGSW() {
        super("Sell manual of GSW", "推销手册", "You can refresh the store's items once", EquipmentDegree.COMMON, Opportunity.SHOP);
    }

    @Override
    public void fun() {
        //TODO 一次刷新货物
    }
}
