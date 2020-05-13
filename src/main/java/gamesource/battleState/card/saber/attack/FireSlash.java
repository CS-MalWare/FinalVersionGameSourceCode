package gamesource.battleState.card.saber.attack;

import gamesource.battleState.card.AttackCard;
import gamesource.battleState.character.Role;

public class FireSlash extends AttackCard {
    public FireSlash() {
        super(OCCUPATION.SABER, "火焰斩", 1, RARITY.COMMON, "deal 6 fire property damage", 6, 1);
        this.property = PROPERTY.FIRE;
    }


    public FireSlash(boolean upgraded) {
        super(OCCUPATION.SABER, "火焰斩+", 1, RARITY.COMMON, "deal 8 fire property damage", 8, 1);
        this.upgraded = true;
        this.property = PROPERTY.FIRE;

    }

    @Override
    public boolean upgrade() {
        if (upgraded) return false;
        this.setCardName("火焰斩+");
        this.setDamage(8);
        this.upgraded = true;
        this.setDescription("deal 8 fire property damage");
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
