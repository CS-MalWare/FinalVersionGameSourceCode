package gamesource.battleState.character.enemy.originalForest;

import gamesource.battleState.character.Enemy;
import gamesource.battleState.utils.buffs.limitBuffs.Weak;

public class Slime extends Enemy {
    //TODO 固化HP和src等属性
    public Slime(int HP, String src, int block, int strength, int dexterity, int dodge, int artifact, int shield, int disarm, int silence) {
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
        this.updateHints();
    }

    @Override
    public void updateHints() {
        this.nextActionSet = new String[]{
                String.format(hints[0], computeDamage(5)),
                hints[1],
                String.format(hints[3], computeBlock(5)),
        };
    }

    @Override
    protected void attack() {
        this.target.getDamage(computeDamage(5));
    }

    @Override
    protected void releaseDebuff() {
        this.target.getBuff(new Weak(target, 1));
    }

    @Override
    protected void releaseCurses() {

    }

    @Override
    protected void getBlocks() {
        this.gainBlock(computeBlock(5));
    }

    @Override
    protected void getBlockAndAttack() {

    }

    @Override
    protected void releaseBuff() {

    }

    @Override
    protected void getBlessing() {

    }

    //敌人行动
    @Override
    public String enemyAction() {
        switch (this.nextActionIndex) {
            case 0:
                attack();
                this.newAction();
                return "slime attack";
            case 1:
                releaseDebuff();
                this.newAction();
                return "slime skill";
            case 2:
                getBlocks();
                this.newAction();
                return "slime skill";
            default:
                return "";
        }
    }


}