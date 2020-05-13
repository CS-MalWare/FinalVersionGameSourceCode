package gamesource.battleState.equipment.common;

import gamesource.battleState.character.MainRole;
import gamesource.battleState.equipment.Equipment;

public class SweaterWithThorns extends Equipment {
    public SweaterWithThorns() {
        super("A sweater with thorns", "带刺的毛衣", "Every time you play a card, you take 1 damage.", EquipmentDegree.COMMON, Opportunity.USE);
    }

    @Override
    public void fun() {
        MainRole.getInstance().getDamage(1);
    }
}
