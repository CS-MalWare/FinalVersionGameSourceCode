package gamesource.battleState.character.enemy.originalForest;

import gamesource.battleState.character.Enemy;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.utils.buffs.limitBuffs.Bleeding;
import gamesource.battleState.utils.buffs.limitBuffs.Intangible;
import gamesource.battleState.utils.buffs.limitBuffs.Vulnerable;
import gamesource.battleState.utils.buffs.limitBuffs.Weak;

public class KingWolfman extends Enemy {
    //TODO 固化HP和src等属性
    private boolean canIncreaseStrength = true;//增加力量，全局只能使用一次，因此这里写一个布尔值限制只能用一次

    public KingWolfman(int HP, String src, int block, int strength, int dexterity, int dodge, int artifact, int shield, int disarm, int silence) {
        super(HP, src, block, strength, dexterity, dodge, artifact, shield, disarm, silence);

        this.updateHints();

        this.nextActionIndex = (int)(Math.random()*this.nextActionSet.length);
    }

    @Override
    public void startTurn() {
        super.startTurn();
        if (stun.getDuration() > 0) {
            return;
        }
        if (this.getHP() == this.getTotalHP() / 2 && canIncreaseStrength) {
            this.setStrength(this.getStrength() + 5);
        }
        this.updateHints();
    }

    @Override
    public void updateHints() {
        this.nextActionSet = new String[]{
                String.format(hints[4], computeDamage(20), computeBlock(10)),
                hints[1],
                String.format(hints[7], computeDamage(5), 3),
                hints[2],
                hints[5],
                String.format(hints[3], computeBlock(20))
        };
    }

    @Override
    protected void attack() {
        int demage = computeDamage((int) (5 * this.getMultiplyingDealDamage()));
        for(int i = 0;i<3;i++) {
            this.target.getDamage(demage);

            this.treat(demage/4);
        }
    }

    @Override
    protected void releaseDebuff(){
        this.target.getBuff(new Bleeding(this.target,5));
    }

    @Override
    protected void releaseCurses() {
        this.target.setStrength(this.target.getStrength()-2);
    }
    @Override
    protected void getBlocks(){
        this.setBlock(this.getBlock() + computeDamage(20));
    }

    @Override
    protected void getBlockAndAttack() {
        int demage = computeDamage(20);
        this.target.getDamage(demage);

        this.treat(demage/4);
        this.setBlock(this.getBlock()+computeDamage(10));
        this.target.getBuff(new Weak(this.target,2),new Vulnerable(this.target,2));
    }

    @Override
    protected void releaseBuff() {
        this.getBuff(new Intangible(this,1));
    }

    @Override
    protected void getBlessing() {

    }

    //敌人行动
    @Override
    public String enemyAction(){
        switch (this.nextActionIndex){
            case 0:
                getBlockAndAttack();
                this.newAction();
                return "wolfman attack";
            case 1:
                releaseDebuff();
                this.newAction();
                return "wolfman skill";
            case 2:
                attack();
                this.newAction();
                return "wolfman attack";
            case 3:
                releaseCurses();
                this.newAction();
                return "wolfman skill";
            case 4:
                releaseBuff();
                this.newAction();
                return "wolfman skill";
            case 5:
                getBlocks();
                this.newAction();
                return "wolfman skill";
            default:
                return "";
        }
    }

}
