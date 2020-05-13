package gamesource.battleState.equipment.common;

import gamesource.battleState.character.MainRole;
import gamesource.battleState.equipment.Equipment;

public class MetroCard extends Equipment {
    public MetroCard() {
        super("metro card", "一卡通", "At the end of the battle, there is a 1/3 chance of gaining 15 gold", EquipmentDegree.COMMON, Opportunity.ENDB);
    }

    @Override
    public void fun() {
        if(3*Math.random()<1){
            MainRole.getInstance().setGold(MainRole.getInstance().getGold()+15);
        }
    }
}
