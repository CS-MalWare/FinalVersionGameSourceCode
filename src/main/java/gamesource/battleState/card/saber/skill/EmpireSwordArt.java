package gamesource.battleState.card.saber.skill;

import gamesource.battleState.appState.EnemyState;
import gamesource.battleState.card.SkillCard;
import gamesource.battleState.character.Enemy;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;

import java.util.ArrayList;

public class EmpireSwordArt extends SkillCard {
    public EmpireSwordArt() {
        super(OCCUPATION.SABER, "帝国剑术", 3, RARITY.RARE, "gain 5 ATK. If any enemy is bleeding, gain 2 MP");

    }

    public EmpireSwordArt(boolean upgraded) {
        super(OCCUPATION.SABER, "帝国剑术+", 3, RARITY.RARE, "gain 5 ATK. If any enemy is bleeding, gain 4 MP");

        this.upgraded = true;
    }


    @Override
    public boolean upgrade() {
        if (this.upgraded) {
            return false;
        } else {
            this.upgraded = true;
            this.setCardName("帝国剑术+");
            this.setDescription("gain 5 ATK. If any enemy is bleeding, gain 4 MP");
        }
        return true;
    }


    @Override
    public boolean use(Role target) {
        ArrayList<Enemy> enemies = EnemyState.getInstance().getEnemies();
        boolean flag = false;
        for (Enemy enemy : enemies) {
            if (enemy.getBleeding().getDuration() > 0) {
                flag = true;
                break;
            }
        }
        if (flag) {
            if (!upgraded) MainRole.getInstance().gainMP(2);
            else MainRole.getInstance().gainMP(4);
        }
        MainRole.getInstance().setAtk(MainRole.getInstance().getAtk() + 5);
        return true;

    }

}