package gamesource.battleState.card.saber.attack;

import gamesource.battleState.card.AttackCard;
import gamesource.battleState.character.Role;

public class LightSlash extends AttackCard {
    public LightSlash() {
        super(OCCUPATION.SABER, "光之挥舞", 2, RARITY.COMMON, "deal 4*3 light property damage to the target", 4, 3);

    }


    public LightSlash(boolean upgraded) {
        super(OCCUPATION.SABER, "光之挥舞+", 2, RARITY.COMMON, "deal 5*3 light property damage to the target", 5, 3);
        this.upgraded = true;
    }

    @Override
    public boolean upgrade() {
        if (upgraded) return false;
        this.setCardName("光之挥舞+");
        this.setDamage(5);
        this.upgraded = true;
        this.setDescription("deal 5*3 light property damage to the target");
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
