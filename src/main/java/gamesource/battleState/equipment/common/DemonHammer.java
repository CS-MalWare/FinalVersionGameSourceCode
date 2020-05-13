package gamesource.battleState.equipment.common;

import gamesource.battleState.appState.EnemyState;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.equipment.Equipment;

public class DemonHammer extends Equipment {
    private boolean firstTime = true;//第一次攻击
    public DemonHammer() {
        super("Demon's Hammer", "恶魔之锤", "The first damage card dealt in battle deals 10 damage to random enemy", EquipmentDegree.COMMON, Opportunity.ATTACK);
    }

    @Override
    public void fun() {
        if(firstTime){
            int size = EnemyState.getInstance().getEnemies().size();
            int random = (int)(Math.random()*size);
            EnemyState.getInstance().getEnemies().get(random).getDamage(10);
            firstTime = false;
        }
    }

    @Override
    public void resetBuff() {
        firstTime = true;
    }
}
