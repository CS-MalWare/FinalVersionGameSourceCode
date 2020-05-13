package gamesource.battleState.card.saber.skill;

import gamesource.battleState.card.SkillCard;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;
import gamesource.battleState.utils.buffs.limitBuffs.Intangible;

public class SealingSpell extends SkillCard {
    public SealingSpell() {
        super(OCCUPATION.SABER, "缄咒", 1, RARITY.COMMON, "can only be released after card rare silence is released. the enemies's block will be removed, if the block is less than 15");
        this.AOE = true;
    }

    public SealingSpell(boolean upgraded) {
        super(OCCUPATION.SABER, "缄咒+", 1, RARITY.COMMON, "can only be released after card rare silence is released. the enemies's block will be removed, if the block is less than 23");
        this.AOE = true;
        this.upgraded = true;
    }


    @Override
    public boolean upgrade() {
        if (this.upgraded) {
            return false;
        } else {
            this.upgraded = true;
            this.setCardName("缄咒+");
            this.setDescription("can only be released after card rare silence is released. the enemies's block will be removed, if the block is less than 23");
        }
        return true;
    }


    @Override
    public boolean use(Role target) {
        // TODO 放弃
        return true;
    }

}
