package gamesource.battleState.card.saber.attack;

import gamesource.battleState.card.AttackCard;
import gamesource.battleState.character.Role;

public class SoilSlash extends AttackCard {
    public SoilSlash() {
        super(OCCUPATION.SABER, "裂地斩", 1, RARITY.COMMON, "deal 6 soil property damage", 6, 1);
        this.property = PROPERTY.SOIL;
    }


    public SoilSlash(boolean upgraded) {
        super(OCCUPATION.SABER, "裂地斩+", 1, RARITY.COMMON, "deal 8 soil property damage", 8, 1);
        this.property = PROPERTY.SOIL;
        this.upgraded = true;
    }

    @Override
    public boolean upgrade() {
        if (upgraded) return false;
        this.setCardName("裂地斩+");
        this.setDamage(8);
        this.upgraded = true;
        this.setDescription("deal 8 soil property damage");
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
