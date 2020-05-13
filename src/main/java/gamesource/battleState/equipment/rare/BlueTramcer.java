package gamesource.battleState.equipment.rare;

import gamesource.battleState.equipment.Equipment;

public class BlueTramcer extends Equipment {
    public BlueTramcer() {
        super("blue tramcer","蓝矿车", "Turns the next 2 cards you get into random curse cards", EquipmentDegree.RARE, Opportunity.GETCARD);
    }

    @Override
    public void fun() {
        //TODO 将下2张获取的卡牌，变为随机的诅咒牌
    }
}
