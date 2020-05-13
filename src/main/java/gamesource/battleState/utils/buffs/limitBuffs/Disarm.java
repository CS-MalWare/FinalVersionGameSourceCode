package gamesource.battleState.utils.buffs.limitBuffs;

import gamesource.battleState.character.Role;
import com.jme3.ui.Picture;
import gamesource.battleState.utils.buffs.BuffFunction;
import gamesource.battleState.utils.buffs.LimitBuff;

public class Disarm extends LimitBuff implements BuffFunction {
    public Disarm(Role role, int duration) {
        super("disarm", "unable to use attack card", new Picture(), role, duration);
    }

    @Override
    public void getFunc() {
//        this.getRole().setUnableAttack(true);
    }

    @Override
    public void triggerFunc() {
        if (this.getDuration() > 1) {//如果层数大于0就继续缴械
            this.decDuration();
        } else if (this.getDuration() == 1) {
//            this.getRole().setUnableAttack(false);
            this.decDuration();
        }
    }
}
