package gamesource.battleState.card.saber.attack;

import gamesource.battleState.card.AttackCard;
import gamesource.battleState.character.Role;

public class FlameChop extends AttackCard {
    public FlameChop() {
        super(OCCUPATION.SABER, "烈焰斩", 2, RARITY.COMMON, "deal 14 fire property damage", 14, 1);
        this.property = PROPERTY.FIRE;
    }


    public FlameChop(boolean upgraded) {
        super(OCCUPATION.SABER, "烈焰斩+", 2, RARITY.COMMON, "deal 18 fire property damage", 18, 1);
        this.upgraded = true;
        this.property = PROPERTY.FIRE;

    }

    @Override
    public boolean upgrade() {
        if (upgraded) return false;
        this.setCardName("烈焰斩+");
        this.setDamage(18);
        this.upgraded = true;
        this.setDescription("deal 18 fire property damage");
        return true;
    }

    @Override
    public boolean use(Role target) {
        if (!super.use(target)) {
            return false;
        }
        return true;
    }


}
