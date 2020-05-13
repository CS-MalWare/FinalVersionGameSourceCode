package gamesource.battleState.card.saber.skill;

import gamesource.battleState.card.SkillCard;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;

public class Heal extends SkillCard {
    public Heal() {
        super(OCCUPATION.SABER, "治疗", 1, RARITY.COMMON, "heal me for 7 HP");

    }

    public Heal(boolean upgraded) {
        super(OCCUPATION.SABER, "治疗+", 1, RARITY.COMMON, "heal me for 10 HP");
        this.upgraded = true;
    }


    @Override
    public boolean upgrade() {
        if (this.upgraded) {
            return false;
        } else {
            this.upgraded = true;
            this.setCardName("治疗+");
            this.setDescription("heal me for 10 HP");
        }
        return true;
    }


    @Override
    public boolean use(Role target) {
        if (!upgraded) {
            MainRole.getInstance().treat(7);
        } else {
            MainRole.getInstance().treat(10);
        }

        return true;
    }

}
