package gamesource.battleState.equipment.legendary;

import gamesource.battleState.character.MainRole;
import gamesource.battleState.equipment.Equipment;

public class MedusaEyeMask extends Equipment {

    public MedusaEyeMask() {
        super(" Medusa's Eye Mask", "美杜莎的眼罩", "increase the maximum MP by 1. decrease 1 dexterity", EquipmentDegree.LEGENDARY, Opportunity.GET);

    }

    @Override
    public void fun() {
        MainRole.getInstance().setMP_max(MainRole.getInstance().getMP_max() + 1);
        MainRole.getInstance().setDexterityForever(MainRole.getInstance().getDexterityForever() - 1);
    }
}
