package gamesource.battleState.utils.buffs.limitBuffs;

import gamesource.battleState.character.Role;
import com.jme3.ui.Picture;
import gamesource.battleState.utils.buffs.BuffFunction;
import gamesource.battleState.utils.buffs.LimitBuff;

public class Sheild extends LimitBuff implements BuffFunction {

    public Sheild(Role role, int duration) {
        super("shield", "get x blocks at the end of turn", new Picture(), role, duration);
    }

    @Override
    public void getFunc() {

    }

    @Override
    public void triggerFunc() {
        if (this.getDuration() > 0) {
            this.getRole().gainBlock(this.getDuration());
            this.decDuration();
        }
    }
}
