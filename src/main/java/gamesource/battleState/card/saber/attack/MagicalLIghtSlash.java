package gamesource.battleState.card.saber.attack;

import gamesource.battleState.card.AttackCard;
import gamesource.battleState.character.Role;

public class MagicalLIghtSlash extends AttackCard {
    public MagicalLIghtSlash() {
        super(OCCUPATION.SABER, "魔光斩", 3, RARITY.COMMON, "deal 21 damage", 21, 1);

    }


    public MagicalLIghtSlash(boolean upgraded) {
        super(OCCUPATION.SABER, "魔光斩+", 3, RARITY.COMMON, "deal 28 damage", 28, 1);
        this.upgraded = true;
    }

    @Override
    public boolean upgrade() {
        if (upgraded) return false;
        this.setCardName("魔光斩+");
        this.setDamage(28);
        this.upgraded = true;
        this.setDescription("deal 28 damage");
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
