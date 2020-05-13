package gamesource.battleState.card.saber.power;

import gamesource.battleState.card.PowerCard;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;

import gamesource.battleState.utils.buffs.limitBuffs.Excite;

public class ManaBoost extends PowerCard {
    public ManaBoost() {
        super(OCCUPATION.SABER, "魔力激发", 1, RARITY.RARE, "gain 99 excite");
    }

    public ManaBoost(boolean upgraded) {
        super(OCCUPATION.SABER, "魔力激发+", 1, RARITY.RARE, "gain 99 excite, innate");
        this.intrinsic = true;
    }


    @Override
    public boolean upgrade() {
        if (upgraded) return false;
        this.setCardName("魔力激发+");
        this.upgraded = true;
        this.setDescription("gain 99 excite, innate");
        return true;
    }


    public boolean use(Role target) {

        MainRole.getInstance().getBuff(new Excite(MainRole.getInstance(), 99));

        return true;
    }
}
