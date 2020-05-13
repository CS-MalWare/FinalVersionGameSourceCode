package gamesource.battleState.card.saber.attack;

import gamesource.battleState.card.AttackCard;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;

public class BladeDance extends AttackCard {
    public BladeDance() {
        super(OCCUPATION.SABER, "剑舞", 2, RARITY.COMMON, "deal 8 damage to all enemies and draw 1 card", 8, 1);
        this.AOE = true;
    }


    public BladeDance(boolean upgraded) {
        super(OCCUPATION.SABER, "剑舞+", 2, RARITY.COMMON, "deal 10 damage to all enemies and draw 1 card", 10, 1);
        this.AOE = true;
        this.upgraded = true;
    }

    @Override
    public boolean upgrade() {
        if (upgraded) return false;
        this.setCardName("剑舞+");
        this.setDamage(10);
        this.upgraded = true;
        this.setDescription("deal 10 damage to all enemies and draw 1 card");
        return true;
    }

    @Override
    public boolean use(Role target) {
        if (!super.use(target)) {
            return false;
        }
        MainRole.getInstance().drawCards(1);
        return true;
    }

    @Override
    public boolean use(Role... targets) {
        if (!super.use(targets)) {
            return false;
        }
        MainRole.getInstance().drawCards(1);
        return true;
    }
}
