package gamesource.battleState.card.neutral.attack;

import gamesource.battleState.card.AttackCard;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;
import gamesource.battleState.utils.buffs.limitBuffs.Weak;

public class HookBoxing extends AttackCard {
    public HookBoxing() {
        super(OCCUPATION.NEUTRAL, "上勾拳", 1, RARITY.COMMON, "deal 5 damage, apply 1 weak to the target, draw 1 card", 5, 1);
    }

    public HookBoxing(boolean upgrade) {
        super(OCCUPATION.NEUTRAL, "上勾拳+", 1, RARITY.COMMON, "deal 7 damage, apply 1 weak to the target, draw 1 card", 7, 1);
        this.upgraded = true;
    }

    @Override
    public boolean upgrade() {
        if (upgraded) return false;
        this.setCardName("上勾拳+");
        this.setDamage(7);
        this.upgraded = true;
        this.setDescription("deal 7 damage, apply 1 weak to the target, draw 1 card");
        return true;
    }

    @Override
    public boolean use(Role target) {
        if (!super.use(target)) return false;

        target.getBuff(new Weak(target, 1));

        MainRole.getInstance().drawCards(1);

        return true;
    }
}
