package gamesource.battleState.card.saber.attack;

import gamesource.battleState.card.AttackCard;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;

public class BlastStrike extends AttackCard {
    public BlastStrike() {
        super(OCCUPATION.SABER, "奋力打击", 2, RARITY.COMMON, "deal 10 damage and draw 1 card", 10, 1);

    }


    public BlastStrike(boolean upgraded) {
        super(OCCUPATION.SABER, "奋力打击+", 2, RARITY.COMMON, "deal 10 damage and draw 2 cards", 10, 1);
        this.upgraded = true;
    }

    @Override
    public boolean upgrade() {
        if (upgraded) return false;
        this.setCardName("奋力打击+");
        this.setDamage(10);
        this.upgraded = true;
        this.setDescription("deal 10 damage and draw 2 cards");
        return true;
    }

    @Override
    public boolean use(Role target) {
        if (!super.use(target)) {
            return false;
        }
        if (!upgraded) {
            MainRole.getInstance().drawCards(1);
        } else {
            MainRole.getInstance().drawCards(2);
        }
        return true;
    }


}
