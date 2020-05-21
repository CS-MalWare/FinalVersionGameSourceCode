package gamesource.util;

import java.util.ArrayList;

import com.simsilica.lemur.component.IconComponent;

import gamesource.battleState.equipment.Equipment;
import gamesource.uiState.shopstate.EquipmentUI;

public class EquipmentArrayReader {
    private static ArrayList<Equipment> equipments;
    private static EquipmentUI[] equipmentUIs;

    public EquipmentArrayReader(){

    }

    public EquipmentArrayReader(ArrayList<Equipment> equipments){
        this.equipments = equipments;
        equipmentUIs = new EquipmentUI[equipments.size()];
    }

    public EquipmentUI[] toEquipmentUIs(){
        for(int it = 0; it < equipments.size(); it++){
            IconComponent iconComponent = new IconComponent(equipments.get(it).getImgSrc());
            iconComponent.setIconScale(0.3f);
            EquipmentUI equipmentUI = new EquipmentUI(iconComponent, equipments.get(it).getName(), equipments.get(it).getDegree(), 
                equipments.get(it).getDescription());
            equipmentUIs[it] = equipmentUI;
        }
        return equipmentUIs;
    }

    public static EquipmentUI equipmentToUI(Equipment equipment){
        IconComponent iconComponent = new IconComponent(equipment.getImgSrc());
        iconComponent.setIconScale(0.3f);
        EquipmentUI equipmentUI = new EquipmentUI(iconComponent, equipment.getName(), equipment.getDegree(), equipment.getDescription());
        return equipmentUI;
    }

    public Equipment findEquipmentByUI(EquipmentUI equipmentUI){
        Equipment equipment = equipments.get(0);
        for(int i=0; i<equipmentUIs.length; i++){
            if(equipmentUIs[i] == equipmentUI){
                equipment = equipments.get(i);
            }
        }
        return equipment;
    }

    public static Equipment findEquipByUIs(Equipment[] equipExtern, EquipmentUI[] equipUIsExtern, EquipmentUI equipmentUI){
        Equipment equipment = equipExtern[0];
        for(int i=0; i<equipUIsExtern.length; i++){
            if(equipmentUI.equals(equipUIsExtern[i].getButton())){
                equipment = equipExtern[i];
            }
        }
        return equipment;
    }
}