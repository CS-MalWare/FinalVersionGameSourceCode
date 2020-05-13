package gamesource.battleState.card.saber.attack;

import gamesource.battleState.card.AttackCard;
import gamesource.battleState.character.Role;
import gamesource.battleState.utils.buffs.limitBuffs.Stun;

public class StaggeringBlow extends AttackCard {
    public StaggeringBlow() {
        super(OCCUPATION.SABER, "当头一击", 2, RARITY.COMMON, "deal 9 damage, if enemy is stun, it will give one stun, exhaust", 9, 1);
        this.exhaust = true;

    }


    public StaggeringBlow(boolean upgraded) {
        super(OCCUPATION.SABER, "当头一击+", 2, RARITY.COMMON, "deal 9 damage, if enemy is stun, it will give one stun", 9, 1);
        this.upgraded = true;
    }

    @Override
    public boolean upgrade() {
        if (upgraded) return false;
        this.setCardName("当头一击+");
        this.setDamage(9);
        this.exhaust = false;
        this.upgraded = true;
        this.setDescription("deal 9 damage");
        return true;
    }

    @Override
    public boolean use(Role target) {
        if (!super.use(target)) {
            return false;
        }
        if (target.getStun().getDuration() > 0) {
            target.getBuff(new Stun(target, 1));
        }
        return true;
    }

}
