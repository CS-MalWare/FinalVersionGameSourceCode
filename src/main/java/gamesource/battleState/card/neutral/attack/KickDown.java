package gamesource.battleState.card.neutral.attack;

import gamesource.battleState.card.AttackCard;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;
import gamesource.battleState.utils.buffs.limitBuffs.Weak;

public class KickDown extends AttackCard {
    public KickDown() {
        super(OCCUPATION.NEUTRAL, "下段踢", 1, RARITY.COMMON, "deal 7 damage,  draw 1 card", 5, 1);
    }

    public KickDown(boolean upgrade) {
        super(OCCUPATION.NEUTRAL, "下段踢+", 1, RARITY.COMMON, "deal 9 damage,  draw 2 card", 7, 1);
        this.upgraded = true;
    }

    @Override
    public boolean upgrade() {
        if (upgraded) return false;
        this.setCardName("下段踢+");
        this.setDamage(9);
        this.upgraded = true;
        this.setDescription("deal 7 damage, apply 1 weak to the target, draw 2 card");
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
