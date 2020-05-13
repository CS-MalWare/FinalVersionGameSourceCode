package gamesource.battleState.utils.buffs;

import gamesource.battleState.character.Role;
import com.jme3.ui.Picture;

public class ForeverBuff extends Buff {
    private int times;//部分永久buff有次数限制或者有增加数值（类似增加力量）

    //某些buff有次数限制或者有增加数值（类似增加力量）
    public ForeverBuff(String name, String description, Picture buffPicture, Role role, int times) {
        super(name, description, buffPicture, role);
        this.times = times;
    }

    //某些buff没有次数限制或者有增加数值（类似增加力量）
    public ForeverBuff(String name, String description, Picture buffPicture, Role role) {
        super(name, description, buffPicture, role);
    }

    //返回buff持续次数或者增加数值（类似增加力量）
    public int getTimes() {
        return times;
    }

    public void incTimes(int times) {
        this.times += times;
    }

    public void decTimes() {
        this.times -= 1;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    //不知道王一润巨佬的实现思路，暂时注释掉
//    //用于多次重复使用同一buff叠加或者增加数值（类似增加力量）
//    public void addTimes(int times) {
//        this.times += times;
//    }

    //因为王一润巨佬在人物中直接减少了，这里就暂时注释掉
//    //用于消耗次数
//    public void decTimes(int times) {
//        this.times -= times;
//    }
}
