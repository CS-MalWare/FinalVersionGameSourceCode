package gamesource.battleState.utils.buffs.limitBuffs;

import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;
import com.jme3.ui.Picture;
import gamesource.battleState.utils.buffs.BuffFunction;
import gamesource.battleState.utils.buffs.LimitBuff;

public class Stun extends LimitBuff implements BuffFunction {

    public Stun(Role role, int duration) {
        super("stun", "skip this turn", new Picture(), role, duration);
    }

    @Override
    public void getFunc() {
        //TODO
    }

    @Override
    public void triggerFunc() {
        MainRole.getInstance().endTurn();
        this.decDuration();
    }
}
