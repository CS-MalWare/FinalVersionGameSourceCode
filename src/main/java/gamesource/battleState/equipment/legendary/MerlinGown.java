package gamesource.battleState.equipment.legendary;

import gamesource.battleState.character.MainRole;
import gamesource.battleState.equipment.Equipment;

public class MerlinGown extends Equipment {

    public MerlinGown() {
        super("Merlin's Gown", "梅林法袍", "Gain 10 blocks at the end of each turn", EquipmentDegree.LEGENDARY, Opportunity.ENDT);

    }

    @Override
    public void fun() {
        MainRole.getInstance().gainBlock(10);
    }
}
