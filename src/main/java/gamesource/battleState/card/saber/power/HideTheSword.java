package gamesource.battleState.card.saber.power;

import gamesource.battleState.card.PowerCard;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;

public class HideTheSword extends PowerCard {
    public HideTheSword() {
        super(OCCUPATION.SABER, "藏剑", 0, RARITY.EPIC, "The next attack gets 2^n ATK(n is the round)");
    }

    public HideTheSword(boolean upgraded) {
        super(OCCUPATION.SABER, "藏剑+", 0, RARITY.EPIC, "The next attack gets 3^n ATK(n is the round)");
    }


    @Override
    public boolean upgrade() {
        if (upgraded) return false;
        this.setCardName("藏剑+");
        this.upgraded = true;
        this.setDescription("The next attack gets 3^n ATK(n is the round)");
        return true;
    }


    public boolean use(Role target) {
        if (!upgraded)
            MainRole.getInstance().cardEffectsMap.put(this.getCardName(), 2);
        if (upgraded)
            MainRole.getInstance().cardEffectsMap.put(this.getCardName(), 3);

        return true;
    }
}
