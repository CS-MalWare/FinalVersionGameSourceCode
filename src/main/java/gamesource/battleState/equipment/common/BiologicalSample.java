package gamesource.battleState.equipment.common;

import gamesource.battleState.appState.EnemyState;
import gamesource.battleState.character.Enemy;
import gamesource.battleState.equipment.Equipment;

public class BiologicalSample extends Equipment {
    public BiologicalSample() {
        super("biological sample", "生物标本", "monster enemy's hp is reduced by 20%", EquipmentDegree.COMMON, Opportunity.STARTB);
    }

    @Override
    public void fun() {
        for (Enemy enemy : EnemyState.getInstance().getEnemies()) {
            enemy.setHP((int) (0.8 * enemy.getTotalHP()));
        }
    }
}
