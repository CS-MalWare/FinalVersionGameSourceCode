package gamesource.battleState.card.saber.skill;

import gamesource.battleState.appState.EnemyState;
import gamesource.battleState.card.SkillCard;
import gamesource.battleState.character.Enemy;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;
import gamesource.battleState.utils.buffs.foreverBuffs.Dodge;

import java.util.ArrayList;

public class Flying extends SkillCard {
    public Flying() {
        super(OCCUPATION.SABER, "飞行术", 2, RARITY.COMMON, "gain 2 dodge, draw 3 cards if you don't have debuff");

    }

    public Flying(boolean upgraded) {
        super(OCCUPATION.SABER, "飞行术+", 2, RARITY.COMMON, "gain 2 dodge, draw 5 cards if you don't have debuff");
        this.upgraded = true;
    }


    @Override
    public boolean upgrade() {
        if (this.upgraded) {
            return false;
        } else {
            this.upgraded = true;
            this.setCardName("飞行术+");
            this.setDescription("gain 2 dodge, draw 5 cards if you don't have debuff");
        }
        return true;
    }


    @Override
    public boolean use(Role target) {
        boolean flag = false;
        MainRole.getInstance().getBuff(new Dodge(MainRole.getInstance(), 2));
        if (MainRole.getInstance().getWeak().getDuration() > 0) return true;
        if (MainRole.getInstance().getBleeding().getDuration() > 0) return true;
        if (MainRole.getInstance().getDisarm().getDuration() > 0) return true;
        if (MainRole.getInstance().getErode().getDuration() > 0) return true;
        if (MainRole.getInstance().getPoison().getDuration() > 0) return true;
        if (MainRole.getInstance().getSilence().getDuration() > 0) return true;
        if (MainRole.getInstance().getStun().getDuration() > 0) return true;
        if (MainRole.getInstance().getVulnerable().getDuration() > 0) return true;

        if (!upgraded) {
            MainRole.getInstance().drawCards(3);
        } else {
            MainRole.getInstance().drawCards(5);
        }

        return true;
    }

}
