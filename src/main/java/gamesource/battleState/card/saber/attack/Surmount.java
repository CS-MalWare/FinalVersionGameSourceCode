package gamesource.battleState.card.saber.attack;

import gamesource.battleState.card.AttackCard;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;

import gamesource.battleState.utils.buffs.limitBuffs.Stun;

public class Surmount extends AttackCard {
    public Surmount() {
        super(OCCUPATION.SABER, "超越", 2, RARITY.RARE, "deal 30 damage and stun myself", 30, 1);

    }


    public Surmount(boolean upgraded) {
        super(OCCUPATION.SABER, "超越+", 2, RARITY.COMMON, "deal 40 damage and stun myself", 40, 1);

        this.upgraded = true;
    }

    @Override
    public boolean upgrade() {
        if (upgraded) return false;
        this.setCardName("超越+");
        this.setDamage(40);
        this.upgraded = true;
        this.setDescription("deal 40 damage and stun myself");
        return true;
    }

    @Override
    public boolean use(Role target) {
        if (!super.use(target)) {
            return false;
        }
        MainRole.getInstance().getBuff(new Stun(MainRole.getInstance(), 1));
        return true;
    }


}
