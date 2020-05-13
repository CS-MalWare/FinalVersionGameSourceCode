package gamesource.battleState.character.enemy.originalForest;

import gamesource.battleState.character.Enemy;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.utils.buffs.limitBuffs.Bleeding;

public class OneEyedWolfman extends Enemy {
    //TODO 固化HP和src等属性
    public OneEyedWolfman(int HP, String src, int block, int strength, int dexterity, int dodge, int artifact, int shield, int disarm, int silence) {
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
        int damage = computeDamage((int) (7 * this.getMultiplyingDealDamage()));
        this.target.getDamage(damage);

        //回复50%伤害值的血量，需要修改，因为伤害值计算还没有准确
        int treatValue = damage/2;
        this.treat(treatValue);
        this.target.getBuff(new Bleeding(this.target,3));
    }

    @Override
    protected void releaseDebuff(){
    }

    @Override
    protected void releaseCurses() {

    }
    @Override
    protected void getBlocks(){

    }

    @Override
    protected void getBlockAndAttack() {

    }

    @Override
    protected void releaseBuff() {
        this.setStrength(this.getStrength()+3);
    }

    @Override
    protected void getBlessing() {

    }

    //敌人行动
    @Override
    public String enemyAction(){
        switch (this.nextActionIndex){
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
