package gamesource.uiState.bagstate;

import com.simsilica.lemur.Button;
import com.simsilica.lemur.Checkbox;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.component.IconComponent;

import gamesource.battleState.card.Card;

public class CardUI {
    private Button button = new Button("name");
    private BagMoney money = new BagMoney();
    private Checkbox checkbox = new Checkbox("name");
    private String description;

    public CardUI(IconComponent iconComponent, Button button, BagMoney money, Checkbox checkbox, String description){
        this.button = button;
        this.money = money;
        this.checkbox = checkbox;
        button.setIcon(iconComponent);
        checkbox.setChecked(false);
        this.description = description;
    }

    public CardUI(IconComponent iconComponent, String name, int money, Checkbox checkbox, 
            String description){
        this.button.setName(name);
        this.button.setIcon(iconComponent);
        this.money.setMoney(money);
        this.checkbox = checkbox;
        this.checkbox.setChecked(false);
        this.description = description;
    }

    public CardUI(IconComponent iconComponent, String name, int money, String checkbox, String description){
        this.button.setIcon(iconComponent);
        this.button.setName(name);
        this.money.setMoney(money);
        this.checkbox.setName(checkbox);
        this.checkbox.setChecked(false);
        this.description = description;
    }
    
    public CardUI(IconComponent iconComponent, String name, int money, String description){
        this.button.setIcon(iconComponent);
        this.button.setName(name);
        this.money.setMoney(money);
        this.description = description;
    }

    public void addToContainer(Container container, int xOfButton, int yOfButton){
        int xOfCheckBox = xOfButton + 1;
        int yOfCheckBox = yOfButton;
        container.addChild(button, xOfButton, yOfButton);
        container.addChild(checkbox, xOfCheckBox, yOfCheckBox);
    }

    public void addButtonToContainer(Container container, int xOfButton, int yOfButton){
        container.addChild(button, xOfButton, yOfButton);
    }

    public void addAction(Command<? super Button>...commands){
        button.addClickCommands(commands);
    }

    public boolean equals(Button button){
        return this.button == button;
    }

    public int getCardMoney(){
        return money.getMoney();
    }

    public void setMoney(int money){
        this.money.setMoney(money);
    }

    public Checkbox getCheckBox(){
        return checkbox;
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