package gamesource.battleState.equipment.rare;

import gamesource.battleState.character.MainRole;
import gamesource.battleState.equipment.Equipment;

public class Popcorn extends Equipment {
    public Popcorn() {
        super("popcorn","爆米花", "Ends the battle with no damage taken, gaining 5 maximum HP up", EquipmentDegree.RARE, Opportunity.ENDB);
    }

    @Override
    public void fun() {
        if(MainRole.getInstance().getHP() == MainRole.getInstance().getTotalHP()){
            MainRole.getInstance().setTotalHP(MainRole.getInstance().getTotalHP()+5);
        }
    }
}
