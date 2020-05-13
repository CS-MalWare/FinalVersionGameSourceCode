package gamesource.battleState.character.enemy.mechanicalEmpire;

import gamesource.battleState.character.Enemy;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.utils.buffs.limitBuffs.*;

public class EliteRobot extends Enemy {
    //TODO 固化HP和SRC等属性
    public EliteRobot(int HP, String src, int block, int strength, int dexterity, int dodge, int artifact, int shield, int disarm, int silence) {
        super(HP, src, block, strength, dexterity, dodge, artifact, shield, disarm, silence);


        this.updateHints();

        this.nextActionIndex = (int) (Math.random() * (this.nextActionSet.length - 1));//第一回合不可能随机到最后一个事件
    }

    @Override
    public void startTurn() {
        super.startTurn();
        if (stun.getDuration() > 0) {
            return;
        }
        // 确保50%以下血量才能触发最后一个事件
        while (this.nextActionIndex == this.nextActionSet.length - 1) {
            if (this.getHP() < 0.5 * this.getTotalHP()) {
                break;
            } else {
                this.nextActionIndex = (int) (Math.random() * this.nextActionSet.length);
            }
        }
        this.updateHints();
    }

    @Override
    public void updateHints() {
        this.nextActionSet = new String[]{
                String.format(hints[0], computeDamage(20)),
                hints[2],
                String.format(hints[3], computeBlock(25)),
                hints[5],
                String.format(hints[7], computeDamage(15), 2),
                "???"
        };
    }

    @Override
    public String enemyAction() {
        switch (this.nextActionIndex){
            case 0:
                this.attack();
                this.newAction();
                return "robot attack";
            case 1:
                this.releaseCurses();
                this.newAction();
                return "robot skill";
            case 2:
                this.getBlocks();
                this.newAction();
                return "robot skill";
            case 3:
                this.releaseBuff();
                this.newAction();
                return "robot skill";
            case 4:
                this.attack2();
                this.newAction();
                return "robot attack";
            case 5:
                this.$();
                this.newAction();
                return "robot attack";
            default:
                return "";
        }

    }

    protected void $(){
        int treatValue = computeDamage((int)(0.15*(this.getTotalHP()-this.getHP())));
        this.target.getDamage(treatValue);
        this.treat(treatValue);
    }

    @Override
    protected void attack() {
        this.target.getDamage(computeDamage(20));
        this.target.getBuff(new Weak(this.target,2), new Vulnerable(this.target,2));
    }

    protected void attack2(){
        for(int i = 0;i<2;i++){
            this.target.getDamage(computeDamage(15));
        }
    }

    @Override
    protected void releaseDebuff() {
    }

    @Override
    protected void releaseCurses() {
        this.setStrength(this.getStrength()+2);
        this.target.getBuff(new Disarm(this.target,1),new Silence(this.target,1));

    }

    @Override
    protected void getBlocks() {
        this.setBlock(this.getBlock()+computeDamage(25));
    }

    @Override
    protected void getBlockAndAttack() {

    }

    @Override
    protected void releaseBuff() {
        this.getBuff(new Intangible(this,2));

    }

    @Override
    protected void getBlessing() {

    }
}
