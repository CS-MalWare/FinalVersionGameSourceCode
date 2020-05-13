package gamesource.battleState.card.saber.skill;

import gamesource.battleState.appState.EnemyState;
import gamesource.battleState.card.SkillCard;
import gamesource.battleState.character.Enemy;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;

import java.util.ArrayList;

public class ShieldBash extends SkillCard {
    public ShieldBash() {
        super(OCCUPATION.SABER, "盾牌猛击", 2, RARITY.EPIC, "gain 8 block. Deal damage equal to my block to an enemy, exhaust");
        this.exhaust = true;
    }

    public ShieldBash(boolean upgraded) {
        super(OCCUPATION.SABER, "盾牌猛击+", 2, RARITY.EPIC, "gain 8 block. Deal damage equal to my block to an enemy");
        this.upgraded = true;
    }


    @Override
    public boolean upgrade() {
        if (this.upgraded) {
            return false;
        } else {
            this.upgraded = true;
            this.exhaust = false;
            this.setCardName("盾牌猛击+");
            this.setDescription("gain 8 block. Deal damage equal to my block to an enemy");
        }
        return true;
    }


    @Override
    public boolean use(Role target) {
        if (!(target instanceof Enemy)) return false;
        MainRole.getInstance().gainBlock(8);
        target.getDamage(MainRole.getInstance().getBlock());
        return true;
    }

}
