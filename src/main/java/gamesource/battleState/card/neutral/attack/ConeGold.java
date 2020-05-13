package gamesource.battleState.card.neutral.attack;

import gamesource.battleState.card.AttackCard;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;
import gamesource.battleState.utils.buffs.limitBuffs.Bleeding;
import gamesource.battleState.utils.buffs.limitBuffs.Weak;

public class ConeGold extends AttackCard {
    public ConeGold() {
        super(OCCUPATION.NEUTRAL, "凝金钻", 3, RARITY.COMMON, "deal 18 gold property damage, apply 3 bleeding to the target", 18, 1);
        this.property = PROPERTY.GOLD;
    }

    public ConeGold(boolean upgrade) {
        super(OCCUPATION.NEUTRAL, "凝金钻+", 3, RARITY.COMMON, "deal 22 gold property damage, apply 5 bleeding to the target", 22, 1);
        this.upgraded = true;
        this.property = PROPERTY.GOLD;
    }

    @Override
    public boolean upgrade() {
        if (upgraded) return false;
        this.setCardName("凝金钻+");
        this.setDamage(22);
        this.upgraded = true;
        this.setDescription("deal 22 gold property damage, apply 5 bleeding to the target");
        return true;
    }

    @Override
    public boolean use(Role target) {
        if (!super.use(target)) return false;
        if (!upgraded) {

            target.getBuff(new Bleeding(target, 3));
        } else {

            target.getBuff(new Bleeding(target, 5));
        }
        return true;
    }
}
