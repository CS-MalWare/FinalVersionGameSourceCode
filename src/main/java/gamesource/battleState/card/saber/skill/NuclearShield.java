package gamesource.battleState.card.saber.skill;

import gamesource.battleState.card.SkillCard;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;

public class NuclearShield extends SkillCard {
    public NuclearShield() {
        super(OCCUPATION.SABER, "核力护盾", 3, RARITY.RARE, "gain 20 block. If HP is less than 20%, you will gain twice the armor");
    }

    public NuclearShield(boolean upgraded) {
        super(OCCUPATION.SABER, "核力护盾+", 3, RARITY.RARE, "gain 25 block. If HP is less than 20%, you will gain twice the armor");
        this.upgraded = true;
    }


    @Override
    public boolean upgrade() {
        if (this.upgraded) {
            return false;
        } else {
            this.upgraded = true;
            this.setCardName("核力护盾+");
            this.setDescription("gain 25 block. If HP is less than 20%, you will gain twice the armor");
        }
        return true;
    }


    @Override
    public boolean use(Role... targets) {
        MainRole mainRole = MainRole.getInstance();
        boolean flag = false;
        if (mainRole.getHP() < mainRole.getTotalHP() * 0.2) {
            flag = true;
        }
        if (!upgraded) {
            mainRole.gainBlock(20);
        } else {
            mainRole.gainBlock(25);
        }
        if (flag) {
            if (!upgraded) {
                mainRole.gainBlock(20);
            } else {
                mainRole.gainBlock(25);
            }
        }
        return true;
    }

    @Override
    public boolean use(Role target) {
        MainRole mainRole = MainRole.getInstance();
        boolean flag = false;
        if (mainRole.getHP() < mainRole.getTotalHP() * 0.2) {
            flag = true;
        }
        if (!upgraded) {
            mainRole.gainBlock(20);
        } else {
            mainRole.gainBlock(25);
        }
        if (flag) {
            if (!upgraded) {
                mainRole.gainBlock(20);
            } else {
                mainRole.gainBlock(25);
            }
        }
        return true;
    }
}
