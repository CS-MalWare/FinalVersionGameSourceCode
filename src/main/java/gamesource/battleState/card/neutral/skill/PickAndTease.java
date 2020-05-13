package gamesource.battleState.card.neutral.skill;

import gamesource.battleState.card.SkillCard;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;
import gamesource.battleState.utils.buffs.foreverBuffs.Dexterity;

public class PickAndTease extends SkillCard {
    public PickAndTease() {
        super(OCCUPATION.NEUTRAL, "挑谑", 0, RARITY.RARE, "gain 2 dexterity and let the target get 1 strength");
    }

    public PickAndTease(boolean upgraded) {
        super(OCCUPATION.NEUTRAL, "挑谑+", 0, RARITY.RARE, "gain 2 dexterity");
        this.upgraded = true;
    }


    @Override
    public boolean upgrade() {
        if (this.upgraded) {
            return false;
        } else {
            this.upgraded = true;

            this.setCardName("挑谑+");
            this.setDescription("gain 2 dexterity");

        }
        return true;
    }

    @Override
    public boolean use(Role target) {
        int dex = MainRole.getInstance().getDexterity();
        MainRole.getInstance().setDexterity(dex + 2);
        if (!upgraded) {
            int strength = MainRole.getInstance().getStrength();
            target.setStrength(strength + 1);
        }
        return true;
    }

}
