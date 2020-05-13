package gamesource.battleState.card.neutral.skill;

import gamesource.battleState.card.SkillCard;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;

public class Bomb extends SkillCard {
    public Bomb() {
        super(OCCUPATION.NEUTRAL, "炸弹", 2, RARITY.EPIC, "deal 35 damage to all enemies after 3 rounds,exhaust");
        this.exhaust = true;
    }

    public Bomb(boolean upgraded) {
        super(OCCUPATION.NEUTRAL, "炸弹+", 2, RARITY.EPIC, "deal 40 damage to all enemies after 3 rounds");
        this.upgraded = true;
    }


    @Override
    public boolean upgrade() {
        if (this.upgraded) {
            return false;
        } else {
            this.upgraded = true;
            this.exhaust = false;
            this.setCardName("炸弹+");
            this.setDescription("deal 40 damage to all enemies after 3 rounds");
        }
        return true;
    }


    @Override
    public boolean use(Role target) {
        MainRole.getInstance().cardEffectsMap.put(this.getCardName(), 3);
        return true;
    }

}
