package gamesource.battleState.equipment.legendary;

import gamesource.battleState.character.MainRole;
import gamesource.battleState.equipment.Equipment;

public class ChunGeAmor extends Equipment {

    public ChunGeAmor() {
        super("ChunGe's Armor", "春哥甲", "increase maximum MP by 1, increase the strength by 1, increase the dexterity by 3, recover all the HP, any healing effect recover 1 HP at most in the rest of this game", EquipmentDegree.LEGENDARY, Opportunity.GET);

    }

    @Override
    public void fun() {

        MainRole.getInstance().setMP_max(MainRole.getInstance().getMP_max() + 1);

        MainRole.getInstance().setStrengthForever(MainRole.getInstance().getStrengthForever() + 1);
        MainRole.getInstance().setDexterityForever(MainRole.getInstance().getDexterityForever() + 3);
        MainRole.getInstance().setHP(MainRole.getInstance().getTotalHP());
        MainRole.getInstance().setUntreatable(true);
    }
}
