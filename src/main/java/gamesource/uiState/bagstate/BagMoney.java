package gamesource.uiState.bagstate;

import gamesource.util.Money;

public class BagMoney implements Money{
    private int currentMoney;

    public BagMoney(){
        currentMoney = 0;
    }
    
    public void setMoney(int money){
        this.currentMoney = money;
    }

    public int getMoney(){
        return currentMoney;
    }
}