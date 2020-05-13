package gamesource.battleState.equipment.legendary;

import gamesource.battleState.card.Card;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.equipment.Equipment;

public class ThePiccoloOfTheDarkElves extends Equipment {

    public ThePiccoloOfTheDarkElves() {
        super("The piccolo of the dark elves", "黑暗精灵的短笛", " The piccolo of the dark elves: Increase the maximum MP by 1. Your Max HP decrease by 25", EquipmentDegree.LEGENDARY, Opportunity.GET);

    }

    @Override
    public void fun() {
        MainRole.getInstance().setMP_max(MainRole.getInstance().getMP_max() + 1);
        MainRole.getInstance().setTotalHP(MainRole.getInstance().getTotalHP() - 25);
    }
}
