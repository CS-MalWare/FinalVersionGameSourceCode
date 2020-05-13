package gamesource.battleState.card.saber.skill;

import gamesource.battleState.card.SkillCard;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;

import gamesource.battleState.utils.buffs.foreverBuffs.Dodge;

public class CanyingShadow extends SkillCard {
    public CanyingShadow() {
        super(OCCUPATION.SABER, "残影", 4, RARITY.EPIC, "gain 30 blocks. keep your block for extra one turn");

    }

    public CanyingShadow(boolean upgraded) {
        super(OCCUPATION.SABER, "残影+", 4, RARITY.EPIC, "gain 45 blocks. keep your block for extra one turn");
        this.upgraded = true;
    }


    @Override
    public boolean upgrade() {
        if (this.upgraded) {
            return false;
        } else {
            this.upgraded = true;
            this.setCardName("残影+");
            this.setDescription("gain 45 blocks. keep your block for extra one turn");
        }
        return true;
    }


    @Override
    public boolean use(Role target) {
        if (!upgraded) {
            MainRole.getInstance().gainBlock(30);
        } else {
            MainRole.getInstance().gainBlock(45);
        }

        MainRole.getInstance().addCardEffect(this.getCardName());
        return true;
    }

}
