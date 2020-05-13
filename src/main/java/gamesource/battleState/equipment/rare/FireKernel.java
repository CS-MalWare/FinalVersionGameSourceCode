package gamesource.battleState.equipment.rare;

import gamesource.battleState.equipment.Equipment;

public class FireKernel extends Equipment {
    public FireKernel() {
        super("fire kernel","烈火之栗","All damage is attached with fire", EquipmentDegree.RARE, Opportunity.GET);
    }

    @Override
    public void fun() {
        //TODO 所有伤害附加火属性
    }
}
