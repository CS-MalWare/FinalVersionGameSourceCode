package gamesource.battleState.card.neutral.skill;

import gamesource.battleState.card.SkillCard;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;

public class Unyielding extends SkillCard {
    public Unyielding() {
        super(OCCUPATION.NEUTRAL, "不屈", 1, RARITY.RARE, "after using this card, prevent a death and keep 1 HP, exhaust");
        this.exhaust = true;
    }

    public Unyielding(boolean upgraded) {
        super(OCCUPATION.NEUTRAL, "不屈+", 1, RARITY.RARE, "after using this card, prevent a death and keep current HP, exhaust");
        this.exhaust = true;
        this.upgraded = true;
    }


    @Override
    public boolean upgrade() {
        if (this.upgraded) {
            return false;
        } else {
            this.setCardName("不屈+");
            this.setDescription("after using this card, prevent a death and keep current HP, exhaust");

        }
        return true;
    }

    @Override
    public boolean use(Role target) {
        MainRole.getInstance().cardEffects.add(this.getCardName());
        return true;
    }

}
