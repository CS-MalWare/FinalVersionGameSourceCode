package gamesource.battleState.card.saber.skill;

import gamesource.battleState.card.SkillCard;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;

public class TakeAnotherShield extends SkillCard {
    public TakeAnotherShield() {
        super(OCCUPATION.SABER, "再拿个盾", 2, RARITY.RARE, "Double my block");

    }

    public TakeAnotherShield(boolean upgraded) {
        super(OCCUPATION.SABER, "再拿个盾+", 2, RARITY.RARE, "Double my block, get 10 block");
        this.upgraded = true;
    }


    @Override
    public boolean upgrade() {
        if (this.upgraded) {
            return false;
        } else {
            this.upgraded = true;
            this.setCardName("再拿个盾+");
            this.setDescription("Double my block, get 10 block");
        }
        return true;
    }


    @Override
    public boolean use(Role target) {

        MainRole.getInstance().gainBlock(MainRole.getInstance().getBlock());
        if (upgraded)
            MainRole.getInstance().gainBlock(10);

        return true;
    }

}
