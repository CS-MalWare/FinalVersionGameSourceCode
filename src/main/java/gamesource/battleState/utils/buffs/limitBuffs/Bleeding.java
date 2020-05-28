package gamesource.battleState.utils.buffs.limitBuffs;

import gamesource.battleState.character.Role;
import com.jme3.ui.Picture;
import gamesource.battleState.utils.buffs.BuffFunction;
import gamesource.battleState.utils.buffs.LimitBuff;
// 受到攻击时,受到和流血层数相同的真实伤害
public class Bleeding extends LimitBuff implements BuffFunction {
    public Bleeding(Role role, int duration) {
        super("bleeding", "get x damage when attacked", new Picture(), role, duration);
    }

    @Override
    public void getFunc() {

    }

    @Override
    public void triggerFunc() {
        if (this.getDuration() > 0) {//层数大于0就触发
            this.getRole().getTrueDamage(this.getDuration());
            this.decDuration();
        }
    }


}
