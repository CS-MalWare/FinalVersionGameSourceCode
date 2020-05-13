package gamesource.battleState.equipment.rare;

import gamesource.battleState.character.MainRole;
import gamesource.battleState.equipment.Equipment;

public class LifeKernel extends Equipment {
    public LifeKernel() {
        super("life kernel", "生命之核"," All damage is attached with wood", EquipmentDegree.RARE, Opportunity.GET);
    }

    @Override
    public void fun() {
        //TODO 所有伤害附加木属性
    }
}
