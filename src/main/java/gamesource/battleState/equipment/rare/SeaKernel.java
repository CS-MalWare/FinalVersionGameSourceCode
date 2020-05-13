package gamesource.battleState.equipment.rare;

import gamesource.battleState.equipment.Equipment;

public class SeaKernel extends Equipment {
    public SeaKernel() {
        super("sea kernel", "海洋之心","All damage is attached with water",EquipmentDegree.RARE, Opportunity.GET);
    }

    @Override
    public void fun() {
        //TODO 所有伤害附加水属性
    }
}
