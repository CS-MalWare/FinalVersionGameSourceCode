package gamesource.battleState.card.saber.attack;

import gamesource.battleState.card.AttackCard;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;
import gamesource.battleState.utils.buffs.Buff;
import gamesource.battleState.utils.buffs.limitBuffs.Bleeding;

public class SuperDrawSwordSlash extends AttackCard {
    public SuperDrawSwordSlash() {
        super(OCCUPATION.SABER, "破空拔刀斩", 4, RARITY.RARE, "deal 18 damage to all enemies and apply 3 bleeding", 18, 1);
        this.AOE = true;
    }


    public SuperDrawSwordSlash(boolean upgraded) {
        super(OCCUPATION.SABER, "破空拔刀斩+", 4, RARITY.RARE, "deal 25 damage to all enemies and apply 5 bleeding", 25, 1);
        this.AOE = true;
        this.upgraded = true;
    }

    @Override
    public boolean upgrade() {
        if (upgraded) return false;
        this.setCardName("破空拔刀斩+");
        this.setDamage(25);
        this.upgraded = true;
        this.setDescription("deal 25 damage to all enemies and apply 5 bleeding");
        return true;
    }

    @Override
    public boolean use(Role target) {
        if (!super.use(target)) {
            return false;
        }
        if (!upgraded)
            target.getBuff(new Bleeding(target, 3));
        else
            target.getBuff(new Bleeding(target, 5));

        return true;
    }

    @Override
    public boolean use(Role... targets) {
        if (!super.use(targets)) {
            return false;
        }
        if (!upgraded)
            for (Role target : targets)
                target.getBuff(new Bleeding(target, 3));
        else
            for (Role target : targets)
                target.getBuff(new Bleeding(target, 5));
        return true;
    }
}
