package gamesource.battleState.card.saber.attack;

import gamesource.battleState.appState.HandCardsState;
import gamesource.battleState.card.AttackCard;
import gamesource.battleState.character.Role;

public class InstantSlash extends AttackCard {
    public InstantSlash() {
        super(OCCUPATION.SABER, "瞬斩", 2, RARITY.LEGENDARY, "deals 8 * x damage(x is the number of cards played in the current round)", 8, 1);

    }


    public InstantSlash(boolean upgraded) {
        super(OCCUPATION.SABER, "瞬斩+", 2, RARITY.LEGENDARY, "deals 10 * x damage(x is the number of cards played in the current round)", 10, 1);
        this.upgraded = true;


    }

    @Override
    public boolean upgrade() {
        if (upgraded) return false;
        this.setCardName("瞬斩+");
        this.setDamage(8);
        this.upgraded = true;
        this.setDescription("deals 10 * x damage(x is the number of cards played in the current round)");
        return true;
    }

    @Override
    public boolean use(Role target) {
        this.setTimes(HandCardsState.getInstance().getCardUsedCount());
        if (!super.use(target)) {
            this.setTimes(0);
            return false;
        }
        this.setTimes(0);
        return true;
    }


}
