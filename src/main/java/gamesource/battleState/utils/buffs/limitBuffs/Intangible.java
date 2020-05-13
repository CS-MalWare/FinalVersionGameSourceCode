package gamesource.battleState.utils.buffs.limitBuffs;

import gamesource.battleState.character.Role;
import com.jme3.ui.Picture;
import gamesource.battleState.utils.buffs.BuffFunction;
import gamesource.battleState.utils.buffs.LimitBuff;

public class Intangible extends LimitBuff implements BuffFunction {
    public Intangible(Role role, int duration) {
        super("intangible", "reduce the damage received by 50%", new Picture(), role, duration);
    }

    @Override
    public void getFunc() {

    }

    @Override
    public void triggerFunc() {
        if (this.getDuration() > 1) {
//            this.getRole().setMultiplyingGetDamage(0.5);
            this.decDuration();
        } else if (this.getDuration() == 1) {
//            this.getRole().setMultiplyingGetDamage(1);
            this.decDuration();
        }
    }
}
