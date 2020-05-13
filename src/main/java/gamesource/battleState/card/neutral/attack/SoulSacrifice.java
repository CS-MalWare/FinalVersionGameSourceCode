package gamesource.battleState.card.neutral.attack;

import gamesource.battleState.card.AttackCard;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;

public class SoulSacrifice extends AttackCard {
    public SoulSacrifice() {
        super(OCCUPATION.NEUTRAL, "千魂祭", 2, RARITY.COMMON, "deal 10 damage to all enemies, draw 1 card", 10, 1);
        this.AOE = true;
    }

    public SoulSacrifice(boolean upgrade) {
        super(OCCUPATION.NEUTRAL, "千魂祭+", 2, RARITY.COMMON, "deal 12 damage to all enemies, draw 2 card", 12, 1);
        this.upgraded = true;
        this.AOE = true;
    }

    @Override
    public boolean upgrade() {
        if (upgraded) return false;
        this.setCardName("千魂祭+");
        this.setDamage(12);
        this.upgraded = true;
        this.setDescription("deal 12 damage to all enemies, draw 2 card");
        return true;
    }

    @Override
    public boolean use(Role... targets) {
        if (!super.use(targets)) return false;
        if (!upgraded) {
            MainRole.getInstance().drawCards(1);
        } else {
            MainRole.getInstance().drawCards(2);
        }
        return true;
    }


    @Override
    public boolean use(Role target) {
        if (!super.use(target)) return false;
        if (!upgraded) {
            MainRole.getInstance().drawCards(1);
        } else {
            MainRole.getInstance().drawCards(2);
        }
        return true;
    }
}
