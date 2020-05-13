package gamesource.battleState.equipment.common;

import gamesource.battleState.equipment.Equipment;

public class Coupon extends Equipment {
    public Coupon() {
        super("coupon", "折扣券", "The price of the next purchase becomes 0", EquipmentDegree.COMMON, Opportunity.SHOP);
    }

    @Override
    public void fun() {
        //TODO 将下一个买的商品的价格变成0
    }
}
