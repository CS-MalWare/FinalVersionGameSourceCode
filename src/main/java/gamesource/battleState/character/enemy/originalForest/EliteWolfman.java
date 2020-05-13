package gamesource.battleState.character.enemy.originalForest;

import gamesource.battleState.character.Enemy;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.utils.buffs.limitBuffs.Bleeding;
import gamesource.battleState.utils.buffs.limitBuffs.Vulnerable;
import gamesource.battleState.utils.buffs.limitBuffs.Weak;

public class EliteWolfman extends Enemy {
    //TODO 固化HP和src等属性
    private boolean isWake = false;//用于判断是否能够攻击吸血

    public EliteWolfman(int HP, String src, int block, int strength, int dexterity, int dodge, int artifact, int shield, int disarm, int silence) {
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
        //是否能够触发吸血效果
        if (this.getHP() < 0.3 * this.getTotalHP()) {
            this.strength += 2;
            this.isWake = true;
        } else {
            this.isWake = false;
        }
        this.updateHints();
    }

    @Override
    public void updateHints() {
        this.nextActionSet = new String[]{
                String.format(hints[7], computeDamage(3), 2),
                String.format(hints[4], computeDamage(15), computeBlock(8)),
                String.format(hints[3], computeBlock(20)),
                hints[1],
                hints[2],
        };
    }

    @Override
    protected void attack() {
        if (!this.isWake) {
            for (int i = 0; i < 2; i++) {
                this.target.getDamage(computeDamage(3));
            }
        } else {
            for (int i = 0; i < 2; i++) {
                int damage = this.target.getDamage(computeDamage(3));
                this.treat((int) (damage * 0.25));
            }
        }
        this.target.getBuff(new Bleeding(target, 2));
    }

    @Override
    protected void releaseDebuff(){
        this.target.setStrength(target.getStrength() - 2);
    }

    @Override
    protected void releaseCurses() {
        //3层 脆弱 与 2层 虚弱
        target.getBuff(new Vulnerable(target, 3), new Weak(target, 2));
    }
    @Override
    protected void getBlocks() {
        this.gainBlock(computeBlock(20));
        this.strength += 1;
    }

    @Override
    protected void getBlockAndAttack() {
        if (!this.isWake) {
            this.target.getDamage(computeDamage(15));
        } else {
            int damage = this.target.getDamage(computeDamage(15));
            this.treat((int) (damage * 0.25));
        }
        this.gainBlock(computeBlock(8));
    }

    @Override
    protected void releaseBuff() {
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
                getBlockAndAttack();
                this.newAction();
                return "wolfman attack";
            case 2:
                getBlocks();
                this.newAction();
                return "wolfman skill";
            case 3:
                releaseDebuff();
                this.newAction();
                return "wolfman skill";
            case 4:
                releaseCurses();
                this.newAction();
                return "wolfman skill";
            default:
                return "";
        }
    }

}
