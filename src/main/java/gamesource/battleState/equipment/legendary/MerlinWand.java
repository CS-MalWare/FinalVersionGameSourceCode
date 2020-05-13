package gamesource.battleState.equipment.legendary;

import gamesource.battleState.character.MainRole;
import gamesource.battleState.equipment.Equipment;

public class MerlinWand extends Equipment {

    public MerlinWand() {
        super("Merlin's Wand", "梅林法杖", "First card you played in turn will cost 0 MP", EquipmentDegree.LEGENDARY, Opportunity.STARTT);

    }

    @Override
    public void fun() {
        MainRole.getInstance().setFreeCard(true);
    }
}
