package gamesource.battleState.utils.buffs.foreverBuffs;

import gamesource.battleState.character.Role;
import com.jme3.ui.Picture;
import gamesource.battleState.utils.buffs.BuffFunction;
import gamesource.battleState.utils.buffs.ForeverBuff;
// 从牌中获得的格挡值增加相应层数
public class Dexterity extends ForeverBuff implements BuffFunction {
    public Dexterity(Role target, int times) {
        super("dexterity", "your block gained from card increase by x", new Picture(), target, times);
        this.getFunc();
    }

    @Override
    public String getDescription() {
        return "your block gained from card increase by x";
    }

    @Override
    public void getFunc() {
        this.getRole().setDexterity(this.getRole().getDexterity() + this.getTimes());
    }

    @Override
    public void triggerFunc() {

    }
}
