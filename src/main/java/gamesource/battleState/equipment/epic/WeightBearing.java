package gamesource.battleState.equipment.epic;

import gamesource.battleState.character.MainRole;
import gamesource.battleState.equipment.Equipment;

public class WeightBearing extends Equipment {

    public WeightBearing() {
        super("Weight-Bearing", "负重", "At the beginning of the fight, gain -1 __strength -1 dexterity", EquipmentDegree.EPIC, Opportunity.STARTB);

    }

    @Override
    public void fun() {
        MainRole.getInstance().setStrength(MainRole.getInstance().getStrength() - 1);
        MainRole.getInstance().setDexterity(MainRole.getInstance().getDexterity() - 1);
    }
}
