package gamesource.battleState.card.saber.skill;

import gamesource.battleState.card.SkillCard;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;
import gamesource.battleState.utils.buffs.limitBuffs.Intangible;

public class DeathTogether extends SkillCard {
    public DeathTogether() {
        super(OCCUPATION.SABER, "同生共死", 0, RARITY.LEGENDARY, "Reduces your health to 1 and deals equal damage to enemies, exhaust");
        this.AOE = true;
        this.exhaust = true;
    }

    public DeathTogether(boolean upgraded) {
        super(OCCUPATION.SABER, "同生共死+", 0, RARITY.LEGENDARY, "Reduces your health to 1 and deals equal damage to enemies");
        this.AOE = true;

        this.upgraded = true;
    }


    @Override
    public boolean upgrade() {
        if (this.upgraded) {
            return false;
        } else {
            this.upgraded = true;
            this.exhaust = false;
            this.setCardName("同生共死+");
            this.setDescription("Reduces your health to 1 and deals equal damage to enemies");
        }
        return true;
    }


    @Override
    public boolean use(Role... targets) {
        int lostHP = MainRole.getInstance().getHP() - 1;
        MainRole.getInstance().setHP(1);
        for (Role target : targets) {
            target.getDamage(MainRole.getInstance().computeDamage(lostHP));
        }
        return true;
    }

}
