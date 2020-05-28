package gamesource.battleState.utils.buffs.limitBuffs;

import gamesource.battleState.character.Role;
import com.jme3.ui.Picture;
import gamesource.battleState.utils.buffs.BuffFunction;
import gamesource.battleState.utils.buffs.LimitBuff;
// 每回合结束,收到与毒层数相同的真实伤害
public class Poison extends LimitBuff implements BuffFunction {


    public Poison(Role role, int duration) {
        super("poison", "get x damage at the end of turn", new Picture(), role, duration);
    }


    @Override
    public void getFunc() {


    }

    @Override
    public void triggerFunc() {
        if (this.getDuration() > 0) {
            this.getRole().getTrueDamage(this.getDuration());
            this.decDuration();
        }
    }
}
