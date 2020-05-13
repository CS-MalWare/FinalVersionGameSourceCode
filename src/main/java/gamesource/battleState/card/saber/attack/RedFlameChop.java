package gamesource.battleState.card.saber.attack;

import gamesource.battleState.card.AttackCard;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.character.Role;
import com.jme3.scene.plugins.blender.textures.blending.TextureBlenderDDS;
import gamesource.battleState.utils.buffs.limitBuffs.Bleeding;
import gamesource.battleState.utils.buffs.limitBuffs.Stun;

public class RedFlameChop extends AttackCard {
    public RedFlameChop() {
        super(OCCUPATION.SABER, "烈火斩", 2, RARITY.RARE, "deal 15 fire property damage and 3 bleeding", 15, 1);
        this.property = PROPERTY.FIRE;
    }


    public RedFlameChop(boolean upgraded) {
        super(OCCUPATION.SABER, "烈火斩+", 2, RARITY.RARE, "deal 20 fire property damage and 4 bleeding", 20, 1);
        this.property = PROPERTY.FIRE;
        this.upgraded = true;
    }

    @Override
    public boolean upgrade() {
        if (upgraded) return false;
        this.setCardName("烈火斩+");
        this.setDamage(20);
        this.upgraded = true;
        this.setDescription("deal 20 fire property damage and 4 bleeding");
        return true;
    }

    @Override
    public boolean use(Role target) {
        if (!super.use(target)) {
            return false;
        }
        if (!upgraded)
            target.getBuff(new Bleeding(target, 3));
        else
            target.getBuff(new Bleeding(target, 4));
        return true;
    }


}
