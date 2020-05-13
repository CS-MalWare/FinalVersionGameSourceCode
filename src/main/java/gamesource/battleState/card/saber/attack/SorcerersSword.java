package gamesource.battleState.card.saber.attack;

import gamesource.battleState.card.AttackCard;
import gamesource.battleState.character.Role;

public class SorcerersSword extends AttackCard {
    public SorcerersSword() {
        super(OCCUPATION.SABER, "魔剑术", 3, RARITY.EPIC, "deal 20 damage, inflicts -1 strength on target. proficient increase the damage by 5", 20, 1);

    }


    public SorcerersSword(boolean upgraded) {
        super(OCCUPATION.SABER, "魔剑术+", 3, RARITY.EPIC, "deal 20 damage, inflicts -3 strength on target. proficient increase the damage by 7", 20, 1);
        this.upgraded = true;
    }

    @Override
    public boolean upgrade() {
        if (upgraded) return false;
        this.setCardName("魔剑术+");
        this.setDamage(20);
        this.upgraded = true;
        this.setDescription("deal 20 damage, inflicts -3 strength on target. proficient increase the damage by 7");
        return true;
    }

    @Override
    public boolean use(Role target) {
        if (!super.use(target)) {
            return false;
        }
        if (!upgraded) {
            target.setStrength(target.getStrength() - 1);
            this.setDamage(this.getDamage() + 5);
        } else {
            target.setStrength(target.getStrength() - 3);
            this.setDamage(this.getDamage() + 7);
        }

        return true;
    }


}
