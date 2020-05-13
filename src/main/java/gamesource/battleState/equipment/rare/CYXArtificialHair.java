package gamesource.battleState.equipment.rare;

import gamesource.battleState.appState.EnemyState;
import gamesource.battleState.equipment.Equipment;
import gamesource.battleState.utils.buffs.limitBuffs.Stun;

public class CYXArtificialHair extends Equipment {
    public CYXArtificialHair() {
        super("CYX's artificial hair", "陈宇轩的假发", "When I get damage, there is 10% of stunning a random enemy", EquipmentDegree.RARE, Opportunity.GETD);
    }

    @Override
    public void fun() {
        if(Math.random()<0.1){
            int random = (int)(Math.random()* EnemyState.getInstance().getEnemies().size());
            EnemyState.getInstance().getEnemies().get(random).getBuff(new Stun(EnemyState.getInstance().getEnemies().get(random),1));
        }
    }
}
