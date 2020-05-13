package gamesource.battleState.equipment.epic;

import gamesource.battleState.character.MainRole;
import gamesource.battleState.equipment.Equipment;
import gamesource.battleState.utils.buffs.limitBuffs.Intangible;

public class InvisibilityCloak extends Equipment {
    private int count;

    public InvisibilityCloak() {
        super("Invisibility Cloak", "隐形斗篷", "Gain one round of intangible effect every three rounds", EquipmentDegree.EPIC, Opportunity.STARTT);
        count = 0;
    }

    @Override
    public void fun() {
        count++;
        if (count == 3) {
            MainRole.getInstance().getBuff(new Intangible(MainRole.getInstance(), 1));
            count = 0;
        }
    }
}
