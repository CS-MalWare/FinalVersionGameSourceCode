package gamesource.battleState.equipment.rare;

import gamesource.battleState.character.MainRole;
import gamesource.battleState.equipment.Equipment;

public class CursedBelt extends Equipment {
    public CursedBelt() {
        super("cursed belt","诅咒的皮带","Every turn, reduce one MP, restores 2 hp and gain 2 blocks.",EquipmentDegree.RARE, Opportunity.STARTT);
    }

    @Override
    public void fun() {
        MainRole.getInstance().setMP_current(MainRole.getInstance().getMP_max()-1);
        MainRole.getInstance().treat(2);
        MainRole.getInstance().setBlock(MainRole.getInstance().getBlock()+MainRole.getInstance().computeBlock(2));
    }
}
