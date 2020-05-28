package gamesource.battleState.utils.buffs.limitBuffs;

import gamesource.battleState.character.Role;
import com.jme3.ui.Picture;
import gamesource.battleState.utils.buffs.BuffFunction;
import gamesource.battleState.utils.buffs.LimitBuff;
// 有1/3概率,使得你的造成的伤害变成1
public class Disarm extends LimitBuff implements BuffFunction {
    public Disarm(Role role, int duration) {
        super("disarm", "have 1/3 chance, to change your attack damage to 1", new Picture(), role, duration);
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
