package gamesource.battleState.utils.buffs.limitBuffs;

import gamesource.battleState.character.Role;
import com.jme3.ui.Picture;
import gamesource.battleState.utils.buffs.BuffFunction;
import gamesource.battleState.utils.buffs.LimitBuff;
// 无法使用技能卡一回合
public class Silence extends LimitBuff implements BuffFunction {

    public Silence(Role role, int duration) {
        super("slience", "unable to use skill card", new Picture(), role, duration);
    }

    @Override
    public void getFunc() {
//        this.getRole().setUnableSkill(true);
    }

    @Override
    public void triggerFunc() {
        if (this.getDuration() > 1) {
            this.decDuration();
        } else if (this.getDuration() == 1) {
//            this.getRole().setUnableSkill(true);
            this.decDuration();
        }
    }
}
