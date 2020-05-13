package gamesource.battleState.equipment.legendary;

import gamesource.battleState.character.MainRole;
import gamesource.battleState.equipment.Equipment;

public class CurseChest extends Equipment {

    public CurseChest() {
        super("Curse Chest", "诅咒的宝箱", "Increase the maximum MP by 1, and turn the current gold to half.", EquipmentDegree.LEGENDARY, Opportunity.GET);

    }

    @Override
    public void fun() {
        MainRole.getInstance().setMP_max(MainRole.getInstance().getMP_max() + 1);
        MainRole.getInstance().setGold(MainRole.getInstance().getGold() / 2);
    }
}
