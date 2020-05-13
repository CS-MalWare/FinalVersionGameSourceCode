package gamesource.battleState.card.saber.attack;

import gamesource.battleState.card.AttackCard;
import gamesource.battleState.character.Role;

public class GoldSlash extends AttackCard {
    public GoldSlash() {
        super(OCCUPATION.SABER, "撒币", 1, RARITY.COMMON, "deal 6 gold property damage", 6, 1);
        this.property = PROPERTY.GOLD;
    }


    public GoldSlash(boolean upgraded) {
        super(OCCUPATION.SABER, "撒币+", 1, RARITY.COMMON, "deal 8 gold property damage", 8, 1);
        this.property = PROPERTY.GOLD;
        this.upgraded = true;
    }

    @Override
    public boolean upgrade() {
        if (upgraded) return false;
        this.setCardName("撒币+");
        this.setDamage(8);
        this.upgraded = true;
        this.setDescription("deal 8 gold property damage");
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
