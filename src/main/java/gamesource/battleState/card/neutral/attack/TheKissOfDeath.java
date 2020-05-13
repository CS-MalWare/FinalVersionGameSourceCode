package gamesource.battleState.card.neutral.attack;

import gamesource.battleState.card.AttackCard;
import gamesource.battleState.character.Role;
import gamesource.battleState.utils.buffs.limitBuffs.Disarm;

public class TheKissOfDeath extends AttackCard {
    public TheKissOfDeath() {
        super(OCCUPATION.NEUTRAL, "黯灭吻", 2, RARITY.COMMON, "deal 12 damage, apply 1 disarm to the target", 12, 1);

    }

    public TheKissOfDeath(boolean upgrade) {
        super(OCCUPATION.NEUTRAL, "黯灭吻+", 2, RARITY.COMMON, "deal 16 damage, apply 1 disarm to the target", 16, 1);
        this.upgraded = true;

    }

    @Override
    public boolean upgrade() {
        if (upgraded) return false;
        this.setCardName("黯灭吻+");
        this.setDamage(16);
        this.upgraded = true;
        this.setDescription("deal 16 damage, apply 1 disarm to the target");
        return true;
    }

    @Override
    public boolean use(Role target) {
        if (!super.use(target)) {
            return false;
        }
        target.getBuff(new Disarm(target, 1));
        return true;
    }
}
