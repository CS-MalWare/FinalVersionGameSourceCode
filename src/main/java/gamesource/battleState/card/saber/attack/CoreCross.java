package gamesource.battleState.card.saber.attack;

import gamesource.battleState.card.AttackCard;
import gamesource.battleState.character.Role;
import gamesource.battleState.utils.buffs.limitBuffs.Bleeding;

public class CoreCross extends AttackCard {
    public CoreCross() {
        super(OCCUPATION.SABER, "十字斩", 1, RARITY.RARE, "deal 7 damage to target, inflicts 2 bleeding", 7, 1);

    }


    public CoreCross(boolean upgraded) {
        super(OCCUPATION.SABER, "十字斩+", 1, RARITY.RARE, "deal 9 damage to target, inflicts 3 bleeding", 3, 1);
        this.upgraded = true;
    }

    @Override
    public boolean upgrade() {
        if (upgraded) return false;
        this.setCardName("十字斩+");
        this.setDamage(9);
        this.upgraded = true;
        this.setDescription("deal 9 damage to target, inflicts 3 bleeding");
        return true;
    }

    @Override
    public boolean use(Role target) {
        if (!super.use(target)) {
            return false;
        }
        if (!upgraded)
            target.getBuff(new Bleeding(target, 2));
        else
            target.getBuff(new Bleeding(target, 3));

        return true;
    }


}
