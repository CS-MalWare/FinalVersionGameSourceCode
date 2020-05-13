package gamesource.battleState.card.neutral.attack;

import gamesource.battleState.card.AttackCard;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;

public class SoilWaveSpells extends AttackCard {
    public SoilWaveSpells() {
        super(OCCUPATION.NEUTRAL, "土浪术", 4, RARITY.COMMON, "deal 20 soil property damage to all enemies, gain 8 block", 20, 1);
        this.AOE = true;
    }

    public SoilWaveSpells(boolean upgrade) {
        super(OCCUPATION.NEUTRAL, "土浪术+", 4, RARITY.COMMON, "deal 25 soil property damage to all enemies, gain 15 block", 25, 1);
        this.upgraded = true;
        this.AOE = true;
    }

    @Override
    public boolean upgrade() {
        if (upgraded) return false;
        this.setCardName("土浪术+");
        this.setDamage(25);
        this.upgraded = true;
        this.setDescription("deal 25 soil property damage to all enemies, gain 15 block");
        return true;
    }

    @Override
    public boolean use(Role... targets) {
        if (!super.use(targets)) return false;
        if (!upgraded) {
            MainRole.getInstance().gainBlock(8);
        } else {
            MainRole.getInstance().gainBlock(15);
        }
        return true;
    }


    @Override
    public boolean use(Role target) {
        if (!super.use(target)) return false;
        if (!upgraded) {
            MainRole.getInstance().gainBlock(8);
        } else {
            MainRole.getInstance().gainBlock(15);
        }
        return true;
    }
}
