package gamesource.battleState.utils.buffs.foreverBuffs;

import gamesource.battleState.character.Role;
import com.jme3.ui.Picture;
import gamesource.battleState.utils.buffs.BuffFunction;
import gamesource.battleState.utils.buffs.ForeverBuff;
// 免疫下次被施加的debuff
public class Artifact extends ForeverBuff implements BuffFunction {
    public Artifact(Role role, int times) {
        super("artifact", "become immune to debuffs", new Picture(), role, times);
        this.getFunc();
    }


    @Override
    public String getDescription() {
        return super.getDescription();
    }

    @Override
    public void getFunc() {
//        this.getRole().setArtifact(this.getRole().getArtifact() + this.getTimes());
    }

    @Override
    public void triggerFunc() {
        this.decTimes();
    }

}
