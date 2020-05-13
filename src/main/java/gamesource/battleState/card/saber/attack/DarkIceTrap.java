package gamesource.battleState.card.saber.attack;

import gamesource.battleState.card.AttackCard;
import gamesource.battleState.character.Enemy;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;

public class DarkIceTrap extends AttackCard {
    public DarkIceTrap() {
        super(OCCUPATION.SABER, "冥海玄冰阵", 3, RARITY.EPIC, "change all enemy actions randomly. Deal 15 water property damage to all enemies", 15, 1);
        this.property = PROPERTY.WATER;
        this.AOE = true;
    }


    public DarkIceTrap(boolean upgraded) {
        super(OCCUPATION.SABER, "冥海玄冰阵+", 3, RARITY.EPIC, "change all enemy actions randomly. Deal 20 water property damage to all enemies", 20, 1);
        this.property = PROPERTY.WATER;
        this.AOE = true;
        this.upgraded = true;
    }

    @Override
    public boolean upgrade() {
        if (upgraded) return false;
        this.setCardName("冥海玄冰阵+");
        this.setDamage(20);
        this.upgraded = true;
        this.setDescription("change all enemy actions randomly. Deal 20 water property damage to all enemies");
        return true;
    }

    @Override
    public boolean use(Role target) {
        if (!super.use(target)) {
            return false;
        }
        ((Enemy) target).setNextActionIndex((int) (Math.random() * ((Enemy) target).getNextActionSet().length));
        return true;
    }

    @Override
    public boolean use(Role... targets) {
        if (!super.use(targets)) {
            return false;
        }
        for (Role target : targets)
            ((Enemy) target).setNextActionIndex((int) (Math.random() * ((Enemy) target).getNextActionSet().length));

        return true;
    }
}
