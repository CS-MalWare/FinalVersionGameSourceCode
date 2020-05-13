package gamesource.battleState.equipment.common;

import gamesource.battleState.appState.EnemyState;
import gamesource.battleState.equipment.Equipment;
import gamesource.battleState.utils.buffs.limitBuffs.Poison;

public class PoisonPill extends Equipment {
    public PoisonPill() {
        super("Poison pill","毒药丸"," endow a random enemy with 4 levels of poison at the beginning of battle", EquipmentDegree.COMMON, Opportunity.STARTB);
    }


    @Override
    public void fun() {
        int size = EnemyState.getInstance().getEnemies().size();
        int random = (int)(Math.random()*size);
        EnemyState.getInstance().getEnemies().get(random).getBuff(new Poison(EnemyState.getInstance().getEnemies().get(random),4));
    }
}
