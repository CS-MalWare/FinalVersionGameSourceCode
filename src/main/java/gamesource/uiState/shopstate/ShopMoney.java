package gamesource.uiState.shopstate;

import gamesource.util.Money;

public class ShopMoney implements Money{
    private int totalMoney;
    private int currentMoney;
    private int totalMoneyOfCards;

    public ShopMoney(){}

    public ShopMoney(int totalmoney){
        this.currentMoney = totalmoney;
        this.totalMoney = totalmoney;
        this.totalMoneyOfCards = 0;
    }

    public void setMoney(int money){
        this.currentMoney = money;
    }

    public int getMoney(){
        return currentMoney;
    }

    public int getTotalMoney(){
        return totalMoney;
    }

    public void setTotalMoney(int totalMoney){
        this.totalMoney = totalMoney;
    }

    public void setTotalMoneyOfCards(int totalMoneyOfCards){
        this.totalMoneyOfCards = totalMoneyOfCards;
    }

    public int getTotalMoneyOfCards(){
        return totalMoneyOfCards;
    }

    public void addToCardsMoney(int money){
        this.totalMoneyOfCards = totalMoneyOfCards + money;
    }

    public void update(){
        this.currentMoney = totalMoney - totalMoneyOfCards;
    }
}