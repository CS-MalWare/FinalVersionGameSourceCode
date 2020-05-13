package gamesource.battleState.character.enemy.originalForest;

import gamesource.battleState.character.Enemy;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.utils.buffs.limitBuffs.Stun;
import gamesource.battleState.utils.buffs.limitBuffs.Weak;

public class EliteSlime extends Enemy {
    //TODO 固化HP和src等属性
    public EliteSlime(int HP, String src, int block, int strength, int dexterity, int dodge, int artifact, int shield, int disarm, int silence) {
        super(HP, src, block, strength, dexterity, dodge, artifact, shield, disarm, silence);

        this.updateHints();

        this.nextActionIndex = (int) (Math.random() * this.nextActionSet.length);
    }

    @Override
    public void startTurn() {
        super.startTurn();
        if (stun.getDuration() > 0) {
            return;
        }
        this.treat(5);//每回合开始回复5点血量
        this.updateHints();
    }

    @Override
    public void updateHints() {
        this.nextActionSet = new String[]{
                String.format(hints[0], computeDamage(20)),
                hints[1],
                String.format(hints[4], computeDamage(5), computeBlock(10)),
        };
    }


    @Override
    public String enemyAction() {
        switch (this.nextActionIndex){
            case 0:
                attack();
                this.newAction();
                return "slime attack";
            case 1:
                releaseDebuff();
                this.newAction();
                return "slime skill";
            case 2:
                getBlockAndAttack();
                this.newAction();
                return "slime attack";
            default:
                return "";
        }
    }

    @Override
    protected void attack() {
        this.target.getDamage(computeDamage(20));
        this.getBuff(new Stun(this, 1));
    }

    @Override
    protected void releaseDebuff() {
        this.target.getBuff(new Weak(this.target, 3));
    }

    @Override
    protected void releaseCurses() {
    }

    @Override
    protected void getBlocks() {

    }

    @Override
    protected void getBlockAndAttack() {
        this.gainBlock(computeBlock(10));
        this.target.getDamage(computeDamage(5));
    }

    @Override
    protected void releaseBuff() {

    }

    @Override
    protected void getBlessing() {

    }
}
