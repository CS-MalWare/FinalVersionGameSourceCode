package gamesource.battleState.card.saber.attack;

import gamesource.battleState.card.AttackCard;
import gamesource.battleState.character.Role;

public class IceSlash extends AttackCard {
    public IceSlash() {
        super(OCCUPATION.SABER, "冰魄斩", 1, RARITY.COMMON, "deal 6 water property damage", 6, 1);
        this.property = PROPERTY.WATER;
    }


    public IceSlash(boolean upgraded) {
        super(OCCUPATION.SABER, "冰魄斩+", 1, RARITY.COMMON, "deal 8 water property damage", 8, 1);
        this.property = PROPERTY.WATER;
        this.upgraded = true;
    }

    @Override
    public boolean upgrade() {
        if (upgraded) return false;
        this.setCardName("冰魄斩+");
        this.setDamage(8);
        this.upgraded = true;
        this.setDescription("deal 8 water property damage");
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
