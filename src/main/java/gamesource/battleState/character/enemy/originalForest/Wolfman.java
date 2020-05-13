package gamesource.battleState.character.enemy.originalForest;

import gamesource.battleState.character.Enemy;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.utils.buffs.limitBuffs.Bleeding;

public class Wolfman extends Enemy {
    //TODO 固化HP和src等属性
    public Wolfman(int HP, String src, int block, int strength, int dexterity, int dodge, int artifact, int shield, int disarm, int silence) {
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
                hints[5],
        };
    }

    @Override
    protected void attack() {
        this.target.getDamage(computeDamage(5));
        this.target.getBuff(new Bleeding(target, 2));
    }

    @Override
    protected void releaseDebuff() {
    }

    @Override
    protected void releaseCurses() {

    }

    @Override
    protected void getBlocks() {

    }

    @Override
    protected void getBlockAndAttack() {

    }

    @Override
    protected void releaseBuff() {
        this.setStrength(this.getStrength() + 2);
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
                return "wolfman attack";
            case 1:
                releaseBuff();
                this.newAction();
                return "wolfman skill";
            default:
                return "";
        }
    }

}
