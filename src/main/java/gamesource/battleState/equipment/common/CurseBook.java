package gamesource.battleState.equipment.common;

import gamesource.battleState.appState.EnemyState;
import gamesource.battleState.character.Enemy;
import gamesource.battleState.equipment.Equipment;

public class CurseBook extends Equipment {
    private int timeCount = 1;
    public CurseBook() {
        super("Curse book", "诅咒魔法书", "The next three battles against ordinary monsters will turn all enemies'HP into 1 at the beginning of the battle", EquipmentDegree.COMMON, Opportunity.STARTB);
    }

    @Override
    public void fun() {
        if(timeCount<=3){
            for(Enemy enemy: EnemyState.getInstance().getEnemies() ){
                switch (enemy.getSrc()){
                    case "Enemies/skeleton/blueSkeleton/blueSkeleton0.j3o":
                    case "Enemies/zhenwang/boss0.j3o":
                    case "Enemies/underWater/fishboss0.j3o":
                    case "Enemies/skeleton/KingSkeleton/kingSkeleton0.j3o":
                    case "character/Solidier/darksolidier0.j3o":
                    case "character/master/Master20.j3o":
                    case "character/Knight/darkknight0.j3o":
                    case "Enemies/fifthMap/boss/scene0.j3o":
                        return;
                    default:
                        break;
                }
                enemy.setHP(1);
            }
            EnemyState.getInstance().updateHints(true);
            timeCount++;
        }
    }
}
