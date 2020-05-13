package gamesource.battleState.utils.buffs;

public interface BuffFunction {


    // 获得该buff的时候触发
    public void getFunc();


    // 触发时候调用,例如回合结束,受到攻击等等
    public void triggerFunc();

}
