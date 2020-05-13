package gamesource.battleState.card.saber.skill;

import gamesource.battleState.card.SkillCard;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;
import gamesource.battleState.utils.buffs.foreverBuffs.Dodge;

public class Peripateticism extends SkillCard {
    public Peripateticism() {
        super(OCCUPATION.SABER, "逍遥游", 1, RARITY.COMMON, "Earn 2 dodge, exhaust");
        this.exhaust = true;
    }

    public Peripateticism(boolean upgraded) {
        super(OCCUPATION.SABER, "逍遥游+", 1, RARITY.COMMON, "Earn 2 dodge");
        this.upgraded = true;
    }


    @Override
    public boolean upgrade() {
        if (this.upgraded) {
            return false;
        } else {
            this.upgraded = true;
            this.exhaust = false;
            this.setCardName("逍遥游+");
            this.setDescription("Earn 2 dodge");
        }
        return true;
    }


    @Override
    public boolean use(Role target) {
        MainRole.getInstance().getBuff(new Dodge(MainRole.getInstance(), 2));
        return true;
    }

}
