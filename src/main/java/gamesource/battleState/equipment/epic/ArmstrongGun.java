package gamesource.battleState.equipment.epic;

import gamesource.battleState.character.MainRole;
import gamesource.battleState.equipment.Equipment;

public class ArmstrongGun extends Equipment {

    public ArmstrongGun() {
        super("Armstrong Gun", "阿姆斯特朗回旋炮", "Gain 2 strength forever", EquipmentDegree.EPIC, Opportunity.GET);

    }

    @Override
    public void fun() {
        MainRole.getInstance().setStrengthForever(MainRole.getInstance().getStrengthForever() + 2);
    }
}
