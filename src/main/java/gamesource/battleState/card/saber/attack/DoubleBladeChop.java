package gamesource.battleState.card.saber.attack;


import gamesource.battleState.character.Role;
import gamesource.battleState.card.AttackCard;
import gamesource.battleState.utils.buffs.limitBuffs.Bleeding;

public class DoubleBladeChop extends AttackCard {
    public DoubleBladeChop() {
        super(OCCUPATION.SABER, "双刀斩", 2, RARITY.COMMON, "deal 10 damage. If the enemy still has armor, reapply the damage", 10, 1);

    }


    public DoubleBladeChop(boolean upgraded) {
        super(OCCUPATION.SABER, "双刀斩+", 2, RARITY.COMMON, "deal 12 damage. If the enemy still has armor, reapply the damage", 12, 1);
        this.upgraded = true;
    }

    @Override
    public boolean upgrade() {
        if (upgraded) return false;
        this.setCardName("双刀斩+");
        this.setDamage(12);
        this.upgraded = true;
        this.setDescription("deal 12 damage. If the enemy still has armor, reapply the damage");
        return true;
    }

    @Override
    public boolean use(Role target) {
        if (!super.use(target)) {
            return false;
        }
        if (target.getBlock() > 0) {
            super.use(target);
        }

        return true;
    }


}
