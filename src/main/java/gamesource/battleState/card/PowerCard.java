package gamesource.battleState.card;

public abstract class PowerCard extends Card {

    // 能力卡
    public PowerCard(OCCUPATION occupation, String name, int cost, RARITY rarity, String description) {
        super(occupation, name, cost, TYPE.POWER, rarity, description);
    }


}
