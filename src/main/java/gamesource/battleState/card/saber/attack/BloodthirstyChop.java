package gamesource.battleState.card.saber.attack;

import gamesource.battleState.card.AttackCard;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;

public class BloodthirstyChop extends AttackCard {
    public BloodthirstyChop() {
        super(OCCUPATION.SABER, "嗜血斩", 1, RARITY.LEGENDARY, "deal 13 damage, heal myself 3 HP, draw 1 card", 13, 1);

    }


    public BloodthirstyChop(boolean upgraded) {
        super(OCCUPATION.SABER, "嗜血斩", 1, RARITY.LEGENDARY, "deal 13 damage, heal myself 3 HP, draw 1 card", 13, 1);

        this.upgraded = true;
    }

    @Override
    public boolean upgrade() {
        if (upgraded) return false;
        this.setCardName("嗜血斩");
        this.setDamage(13);
        this.upgraded = true;
        this.setDescription("deal 13 damage, heal myself 3 HP, draw 1 card");
        return true;
    }

    @Override
    public boolean use(Role target) {
        if (!super.use(target)) {
            return false;
        }
        MainRole.getInstance().treat(3);
        MainRole.getInstance().drawCards(1);
        return true;
    }

    @Override
    public boolean use(Role... targets) {
        if (!super.use(targets)) {
            return false;
        }
        MainRole.getInstance().treat(3);

        MainRole.getInstance().drawCards(1);
        return true;
    }
}
