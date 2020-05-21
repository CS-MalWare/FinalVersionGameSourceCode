package gamesource.uiState.shopstate;

import com.simsilica.lemur.Button;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.component.IconComponent;

import gamesource.battleState.equipment.Equipment.EquipmentDegree;

public class EquipmentUI {
    private String description;
    private Button button = new Button("name");
    private ShopMoney shopMoney = new ShopMoney();

    public EquipmentUI(IconComponent iconComponent, Button button, ShopMoney money, String description){
        this.button = button;
        this.shopMoney = money;
        button.setIcon(iconComponent);
        this.description = description;
    }

    public EquipmentUI(IconComponent iconComponent, String name, int money, String description){
        this.button.setName(name);
        this.button.setIcon(iconComponent);
        this.description = description;
        this.shopMoney.setMoney(money);
    }

    public EquipmentUI(IconComponent iconComponent, String name, EquipmentDegree equipmentDegree, String description){
        this.button.setName(name);
        this.button.setIcon(iconComponent);
        this.description = description;
        if(equipmentDegree == EquipmentDegree.COMMON){
            this.shopMoney.setMoney(100);
        }
    }

    public boolean equals(EquipmentUI equipmentUI){
        return this.description.equals(equipmentUI.getDescription());
    }

    public void addToButtonContainer(Container container, int xOfButton, int yOfButton){
        container.addChild(button, xOfButton, yOfButton);
    }

    public void addAction(Command<? super Button>...commands){
        button.addClickCommands(commands);
    }

    public boolean equals(Button button){
        return this.button == button;
    }

    public int getCardMoney(){
        return shopMoney.getMoney();
    }

    public void setMoney(int money){
        this.shopMoney.setMoney(money);
    }

    public Button getButton(){
        return button;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}