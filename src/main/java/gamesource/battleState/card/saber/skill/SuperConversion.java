package gamesource.battleState.card.saber.skill;

import gamesource.battleState.card.AttackCard;
import gamesource.battleState.card.SkillCard;
import gamesource.battleState.card.saber.power.ManaBoost;
import gamesource.battleState.character.Enemy;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;

public class SuperConversion extends SkillCard {
    public SuperConversion() {
        super(OCCUPATION.SABER, "超能转换", 3, RARITY.LEGENDARY, "specify an enemy and exchange its buff and debuff with yours");

    }

    public SuperConversion(boolean upgraded) {
        super(OCCUPATION.SABER, "超能转换+", 3, RARITY.LEGENDARY, "specify an enemy and exchange its buff and debuff with yours, and clear its buff and debuff");

        this.upgraded = true;
    }


    @Override
    public boolean upgrade() {
        if (this.upgraded) {
            return false;
        } else {
            this.upgraded = true;
            this.setCardName("超能转换+");
            this.setDescription("specify an enemy and exchange its buff and debuff with yours, and clear its buff and debuff");
        }
        return true;
    }


    @Override
    public boolean use(Role target) {
        if (!(target instanceof Enemy)) {
            return false;
        }
        int artifact_A = MainRole.getInstance().getArtifact().getTimes();
        int dexterity_A = MainRole.getInstance().getDexterity();
        int strength_A = MainRole.getInstance().getStrength();
        int bleeding_A = MainRole.getInstance().getBleeding().getDuration();
        int disarm_A = MainRole.getInstance().getDisarm().getDuration();
        int erode_A = MainRole.getInstance().getErode().getDuration();
        int excite_A = MainRole.getInstance().getExcite().getDuration();
        int intangible_A = MainRole.getInstance().getIntangible().getDuration();
        int dodge_A = MainRole.getInstance().getDodge().getTimes();
        int poison_A = MainRole.getInstance().getPoison().getDuration();
        int vulnerable_A = MainRole.getInstance().getVulnerable().getDuration();
        int shield_A = MainRole.getInstance().getSheild().getDuration();
        int silence_A = MainRole.getInstance().getSilence().getDuration();
        int weak_A = MainRole.getInstance().getWeak().getDuration();


        int artifact_B = target.getArtifact().getTimes();
        int dexterity_B = target.getDexterity();
        int strength_B = target.getStrength();
        int bleeding_B = target.getBleeding().getDuration();
        int disarm_B = target.getDisarm().getDuration();
        int erode_B = target.getErode().getDuration();
        int excite_B = target.getExcite().getDuration();
        int intangible_B = target.getIntangible().getDuration();
        int dodge_B = target.getDodge().getTimes();
        int poison_B = target.getPoison().getDuration();
        int vulnerable_B = target.getVulnerable().getDuration();
        int shield_B = target.getSheild().getDuration();
        int silence_B = target.getSilence().getDuration();
        int weak_B = target.getWeak().getDuration();


        target.getArtifact().setTimes(artifact_A);
        target.setDexterity(dexterity_A);
        target.setStrength(strength_A);
        target.getBleeding().setDuration(bleeding_A);
        target.getDisarm().setDuration(disarm_A);
        target.getErode().setDuration(erode_A);
        target.getExcite().setDuration(excite_A);
        target.getIntangible().setDuration(intangible_A);
        target.getDodge().setTimes(dodge_A);
        target.getPoison().setDuration(poison_A);
        target.getVulnerable().setDuration(vulnerable_A);
        target.getSheild().setDuration(shield_A);
        target.getSilence().setDuration(silence_A);
        target.getWeak().setDuration(weak_A);


        MainRole.getInstance().getArtifact().setTimes(artifact_B);
        MainRole.getInstance().setDexterity(dexterity_B);
        MainRole.getInstance().setStrength(strength_B);
        MainRole.getInstance().getBleeding().setDuration(bleeding_B);
        MainRole.getInstance().getDisarm().setDuration(disarm_B);
        MainRole.getInstance().getErode().setDuration(erode_B);
        MainRole.getInstance().getExcite().setDuration(excite_B);
        MainRole.getInstance().getIntangible().setDuration(intangible_B);
        MainRole.getInstance().getDodge().setTimes(dodge_B);
        MainRole.getInstance().getPoison().setDuration(poison_B);
        MainRole.getInstance().getVulnerable().setDuration(vulnerable_B);
        MainRole.getInstance().getSheild().setDuration(shield_B);
        MainRole.getInstance().getSilence().setDuration(silence_B);
        MainRole.getInstance().getWeak().setDuration(weak_B);

        if (upgraded) {
            target.getArtifact().setTimes(0);
            target.setDexterity(0);
            target.setStrength(0);
            target.getBleeding().setDuration(0);
            target.getDisarm().setDuration(0);
            target.getErode().setDuration(0);
            target.getExcite().setDuration(0);
            target.getIntangible().setDuration(0);
            target.getDodge().setTimes(0);
            target.getPoison().setDuration(0);
            target.getVulnerable().setDuration(0);
            target.getSheild().setDuration(0);
            target.getSilence().setDuration(0);
            target.getWeak().setDuration(0);
        }

        return true;
    }

}