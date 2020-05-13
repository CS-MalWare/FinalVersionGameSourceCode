package gamesource.battleState.equipment.rare;

import gamesource.battleState.appState.EnemyState;
import gamesource.battleState.character.Enemy;
import gamesource.battleState.equipment.Equipment;

public class AlienwareOfWGL extends Equipment {
    private int turnCount = 1;
    public AlienwareOfWGL() {
        super("Alienware of WGL","魅高林的外星人", " Deal 30 damage to all enemies in turn 5", EquipmentDegree.RARE, Opportunity.STARTT);
    }

    @Override
    public void fun() {
        if(turnCount == 5){
            for (Enemy enemy: EnemyState.getInstance().getEnemies()) {
                enemy.getDamage(30);
            }
        }
        turnCount++;
    }

    @Override
    public void resetBuff() {
        turnCount = 1;
    }
}
