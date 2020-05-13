package gamesource.battleState.equipment.rare;

import gamesource.battleState.equipment.Equipment;

public class MetalKernal extends Equipment {
    public MetalKernal() {
        super("metal kernal","精金之铠","All damage is attached with metal",EquipmentDegree.RARE, Opportunity.GET);
    }

    @Override
    public void fun() {
        //TODO 所有伤害附加金属性
    }
}
