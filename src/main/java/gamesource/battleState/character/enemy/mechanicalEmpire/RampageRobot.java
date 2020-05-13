package gamesource.battleState.character.enemy.mechanicalEmpire;

import gamesource.battleState.character.Enemy;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.utils.buffs.limitBuffs.Disarm;
import gamesource.battleState.utils.buffs.limitBuffs.Intangible;
import gamesource.battleState.utils.buffs.limitBuffs.Silence;
import gamesource.battleState.utils.buffs.limitBuffs.Weak;

public class RampageRobot extends Enemy {
    //TODO 固化HP和SRC等属性
    public RampageRobot(int HP, String src, int block, int strength, int dexterity, int dodge, int artifact, int shield, int disarm, int silence) {
        super(HP, src, block, strength, dexterity, dodge, artifact, shield, disarm, silence);
        this.updateHints();

        this.nextActionIndex = (int) (Math.random() * this.nextActionSet.length);
        this.getBuff(new Intangible(this, 2));
    }

    @Override
    public void startTurn() {
        super.startTurn();
        if (stun.getDuration() > 0) {
            return;
        }
        this.updateHints();
    }

    @Override
    public void updateHints() {
        this.nextActionSet = new String[]{
                String.format(hints[0], computeDamage(20)),
                hints[1],
                String.format(hints[4], computeDamage(10), computeBlock(10)),
        };
    }

    @Override
    public String enemyAction() {
        switch (this.nextActionIndex) {
            case 0:
                this.attack();
                this.newAction();
                return "robot attack";
            case 1:
                this.releaseDebuff();
                this.newAction();
                return "robot skill";
            case 2:
                this.getBlockAndAttack();
                this.newAction();
                return "robot attack";
            default:
                return "";
        }
    }

    @Override
    protected void attack() {
        this.target.getDamage(computeDamage(20));
        this.target.getBuff(new Weak(this.target, 2));
    }

    @Override
    protected void releaseDebuff() {
        if (Math.random() > 0.5) {
            this.target.getBuff(new Disarm(this.target, 1));
        } else {
            this.target.getBuff(new Silence(this.target, 1));
        }
        this.treat(5);
    }

    @Override
    protected void releaseCurses() {

    }

    @Override
    protected void getBlocks() {

    }

    @Override
    protected void getBlockAndAttack() {
        this.target.getDamage(computeDamage(10));
        this.setBlock(this.getBlock() + computeBlock(10));
    }

    @Override
    protected void releaseBuff() {

    }

    @Override
    protected void getBlessing() {

    }
}
