package gamesource.battleState.character.enemy.dragonWat;

import gamesource.battleState.character.Enemy;
import gamesource.battleState.utils.buffs.foreverBuffs.Dodge;

public class EliteDarkDragon extends Enemy {
    public EliteDarkDragon(int HP, String src, int block, int strength, int dexterity, int dodge, int artifact, int shield, int disarm, int silence) {
        super(HP, src, block, strength, dexterity, dodge, artifact, shield, disarm, silence);

        this.updateHints();
        this.nextActionIndex = (int) (Math.random() * (this.nextActionSet.length));
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
                String.format(hints[0], computeDamage(35)),
                hints[1],
                String.format(hints[3], computeBlock(10)),
                String.format(hints[7], computeDamage(6), 4),
        };
    }

    @Override
    public String enemyAction() {
        switch (this.nextActionIndex){
            case 0:
                this.attack();
                this.newAction();
                return "dragon attack";
            case 1:
                this.releaseDebuff();
                this.newAction();
                return "dragon skill";
            case 2:
                this.getBlocks();
                this.newAction();
                return "dragon skill";
            case 3:
                this.attack2();
                this.newAction();
                return "dragon attack";
            default:
                return "";
        }
    }

    @Override
    protected void attack() {
        this.target.getDamage(computeDamage(35));
    }

    protected void attack2() {
        for(int i = 0;i<4;i++){
            this.target.getDamage(computeDamage(6));
        }
        this.getBuff(new Dodge(this,1));
    }

    @Override
    protected void releaseDebuff() {
        this.target.setStrength(this.target.getStrength()-2);
    }

    @Override
    protected void releaseCurses() {

    }

    @Override
    protected void getBlocks() {
        this.setBlock(this.getBlock()+computeBlock(10));
        this.setStrength(this.getStrength()+4);
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
}
