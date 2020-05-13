package gamesource.battleState.card.saber.skill;

import gamesource.battleState.card.SkillCard;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;
import gamesource.battleState.utils.buffs.foreverBuffs.Dodge;

public class Charge extends SkillCard {
    public Charge() {
        super(OCCUPATION.SABER, "充能", 1, RARITY.COMMON, "next attack will inflict vulnerable on the target for 1 turn");

    }

    public Charge(boolean upgraded) {
        super(OCCUPATION.SABER, "充能+", 1, RARITY.COMMON, "next attack will inflict vulnerable on the target for 2 turn");
        this.upgraded = true;
    }


    @Override
    public boolean upgrade() {
        if (this.upgraded) {
            return false;
        } else {
            this.upgraded = true;
            this.setCardName("充能+");
            this.setDescription("next attack will inflict vulnerable on the target for 2 turn");
        }
        return true;
    }


    @Override
    public boolean use(Role target) {
        // TODO 将一下次攻击施加脆弱(放弃)

        return true;
    }

}
