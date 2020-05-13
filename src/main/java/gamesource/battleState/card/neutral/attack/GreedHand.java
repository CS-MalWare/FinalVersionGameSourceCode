package gamesource.battleState.card.neutral.attack;

import gamesource.battleState.card.AttackCard;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;

public class GreedHand extends AttackCard {
    public GreedHand() {
        super(OCCUPATION.NEUTRAL, "贪婪之手", 2, RARITY.EPIC, "deal 14 damage, if kill the target, gain extra 15 coin after battle finish. exhaust.", 14, 1);
        this.exhaust = true;

    }

    public GreedHand(boolean upgrade) {
        super(OCCUPATION.NEUTRAL, "贪婪之手+", 2, RARITY.EPIC, "deal 14 damage, if kill the target, gain extra 15 coin after battle finish.", 14, 1);
        this.exhaust = false;
        this.upgraded = true;

    }

    @Override
    public boolean upgrade() {
        if (upgraded) return false;
        this.setCardName("贪婪之手+");
        this.setDamage(14);
        this.exhaust = false;
        this.upgraded = true;
        this.setDescription("deal 14 damage, if kill the target, gain extra 15 coin after battle finish");
        return true;
    }

    @Override
    public boolean use(Role target) {
        if (!super.use(target)) {
            return false;
        }

        if (target.getHP() <= 0) {
            MainRole.getInstance().getGold(15);
        }

        return true;
    }
}
