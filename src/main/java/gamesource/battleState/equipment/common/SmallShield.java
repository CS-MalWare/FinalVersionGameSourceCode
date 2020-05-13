package gamesource.battleState.equipment.common;

import gamesource.battleState.character.MainRole;
import gamesource.battleState.equipment.Equipment;

public class SmallShield extends Equipment {
    public SmallShield() {
        super("Small shield", "小盾牌", "At the end of the turn, gain 2 blocks", EquipmentDegree.COMMON, Opportunity.ENDT);
    }

    @Override
    public void fun() {
        MainRole.getInstance().setBlock(MainRole.getInstance().getBlock()+MainRole.getInstance().computeBlock(2));
    }
}
