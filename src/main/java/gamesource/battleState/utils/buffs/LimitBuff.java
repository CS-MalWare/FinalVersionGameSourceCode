package gamesource.battleState.utils.buffs;

import gamesource.battleState.character.Role;
import com.jme3.ui.Picture;

public class LimitBuff extends Buff {
    private int duration;//buff的持续时长

    public LimitBuff(String name, String description, Picture buffPicture, Role role, int duration) {
        super(name, description, buffPicture, role);
        this.duration = duration;
    }

    //返回buff持续时长
    public int getDuration() {
        return duration;
    }

    //每次调用Buff后持续回合应该减一
    public void decDuration() {
        if (this.duration > 0)
            this.duration--;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void incDuration(int layer) {
        this.duration += layer;
    }

    //不知道王一润巨佬的实现思路，暂时注释掉
//    //用于多次卡牌叠加buff
//    public void addDuration(int duration) {
//        this.duration += duration;
//    }

    //某些卡牌能够清除buff
    public void clearDuration() {
        this.duration = 0;
    }
}
