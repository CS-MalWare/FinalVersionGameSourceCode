package gamesource.battleState.equipment.legendary;

import gamesource.battleState.character.MainRole;
import gamesource.battleState.equipment.Equipment;

public class Tights extends Equipment {

    public Tights() {
        super("Tights", "紧身衣", " Increase the maximum MP by 1, decrease 1 strength", EquipmentDegree.LEGENDARY, Opportunity.GET);

    }

    @Override
    public void fun() {

        MainRole.getInstance().setMP_max(MainRole.getInstance().getMP_max() + 1);

        MainRole.getInstance().setStrengthForever(MainRole.getInstance().getStrengthForever() - 1);
    }
}
