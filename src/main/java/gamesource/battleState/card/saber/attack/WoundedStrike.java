package gamesource.battleState.card.saber.attack;

import gamesource.battleState.card.AttackCard;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;

public class WoundedStrike extends AttackCard {
    public WoundedStrike() {
        super(OCCUPATION.SABER, "负伤强击", 4, RARITY.COMMON, "deal 10 damage plus 50% of lost HP", 10, 1);

    }


    public WoundedStrike(boolean upgraded) {
        super(OCCUPATION.SABER, "负伤强击+", 4, RARITY.COMMON, "deal 18 damage plus 50% of lost HP", 18, 1);
        this.upgraded = true;
    }

    @Override
    public boolean upgrade() {
        if (upgraded) return false;
        this.setCardName("负伤强击+");
        this.setDamage(18);
        this.upgraded = true;
        this.setDescription("deal 18 damage plus 50% of lost HP");
        return true;
    }

    @Override
    public boolean use(Role target) {
        if (!super.use(target)) {
            return false;
        }
        MainRole mainRole = MainRole.getInstance();
        int lostHP = (mainRole.getTotalHP() - mainRole.getHP()) / 2;
        target.getDamage(mainRole.computeDamage(lostHP));
        return true;
    }


}
