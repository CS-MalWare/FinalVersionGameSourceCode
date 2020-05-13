package gamesource.battleState.equipment.rare;

import gamesource.battleState.equipment.Equipment;

public class SoilKernel extends Equipment {
    public SoilKernel() {
        super("soil kernel:", "厚土之靴","All damage is attached with soil",EquipmentDegree.RARE, Opportunity.GET);
    }

    @Override
    public void fun() {
        //TODO 所有伤害附加土属性
    }
}
