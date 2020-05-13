package gamesource.battleState.card.saber.attack;

import gamesource.battleState.card.AttackCard;
import gamesource.battleState.character.Role;

public class Slash extends AttackCard {
    public Slash() {
        super(OCCUPATION.SABER, "打击", 1, RARITY.COMMON, "deal 5 damage", 5, 1);

    }


    public Slash(boolean upgraded) {
        super(OCCUPATION.SABER, "打击+", 1, RARITY.COMMON, "deal 8 damage", 8, 1);
        this.upgraded = true;
    }

    @Override
    public boolean upgrade() {
        if (upgraded) return false;
        this.setCardName("打击+");
        this.setDamage(8);
        this.upgraded = true;
        this.setDescription("deal 8 damage");
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
