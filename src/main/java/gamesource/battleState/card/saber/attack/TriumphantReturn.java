package gamesource.battleState.card.saber.attack;

import gamesource.battleState.card.AttackCard;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;

public class TriumphantReturn extends AttackCard {
    public TriumphantReturn() {
        super(OCCUPATION.SABER, "凯旋", 3, RARITY.RARE, "deal 12 damage, if target is killed, gain 3 MP", 12, 1);
    }


    public TriumphantReturn(boolean upgraded) {
        super(OCCUPATION.SABER, "凯旋+", 3, RARITY.RARE, "deal 18 damage, if target is killed, gain 4 MP", 18, 1);
        this.upgraded = true;
    }

    @Override
    public boolean upgrade() {
        if (upgraded) return false;
        this.setCardName("凯旋+");
        this.setDamage(18);
        this.upgraded = true;
        this.setDescription("deal 18 damage, if target is killed, gain 4 MP");
        return true;
    }

    @Override
    public boolean use(Role target) {
        if (!super.use(target)) {
            return false;
        }
        if (target.getHP() <= 0) {
            if (!upgraded)
                MainRole.getInstance().gainMP(3);
            else
                MainRole.getInstance().gainMP(4);
        }
        return true;
    }


}
