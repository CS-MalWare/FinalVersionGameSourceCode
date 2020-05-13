package gamesource.battleState.equipment.epic;

import gamesource.battleState.card.Card;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.equipment.Equipment;
import gamesource.battleState.utils.buffs.limitBuffs.Intangible;

import java.util.ArrayList;

public class ThreeYearSimulationFiveYearCollegeEntranceExamination extends Equipment {
    private int count;

    public ThreeYearSimulationFiveYearCollegeEntranceExamination() {
        super("Three year simulation, five year college entrance examination", "五年高考三年模拟", "When picking, change all rare cards in the card deck to normal ones and randomly turn them into other ones", EquipmentDegree.EPIC, Opportunity.GET);
        count = 0;
    }

    @Override
    public void fun() {
        ArrayList<Card> cards = MainRole.getInstance().getDeck_();
        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            if (card.getRarity() == Card.RARITY.COMMON) {
                // TODO 将这张牌随机变化为其他牌
            }
        }
    }
}
