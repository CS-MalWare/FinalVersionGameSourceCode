package gamesource.battleState.card.saber.attack;

import gamesource.battleState.card.AttackCard;
import gamesource.battleState.character.Role;

public class DrawSwordStrike extends AttackCard {
    public DrawSwordStrike() {
        super(OCCUPATION.SABER, "拔刀斩", 3, RARITY.COMMON, "deal 15 damage to all enemies", 15, 1);
        this.AOE = true;
    }


    public DrawSwordStrike(boolean upgraded) {
        super(OCCUPATION.SABER, "拔刀斩+", 3, RARITY.COMMON, "deal 20 damage to all enemies", 20, 1);
        this.AOE = true;
        this.upgraded = true;
    }

    @Override
    public boolean upgrade() {
        if (upgraded) return false;
        this.setCardName("拔刀斩+");
        this.setDamage(20);
        this.upgraded = true;
        this.setDescription("deal 20 damage to all enemies");
        return true;
    }

    @Override
    public boolean use(Role target) {
        if (!super.use(target)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean use(Role... targets) {
        if (!super.use(targets)) {
            return false;
        }
        return true;
    }


}
