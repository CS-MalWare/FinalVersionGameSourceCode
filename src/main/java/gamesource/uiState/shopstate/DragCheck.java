package gamesource.uiState.shopstate;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.simsilica.lemur.ActionButton;
import com.simsilica.lemur.CallMethodAction;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.event.ConsumingMouseListener;
import com.simsilica.lemur.event.CursorEventControl;
import com.simsilica.lemur.event.DragHandler;
import com.simsilica.lemur.event.MouseEventControl;
import com.simsilica.lemur.event.PopupState;
import com.simsilica.lemur.style.ElementId;
import gamesource.battleState.character.MainRole;
import gamesource.uiState.bagstate.CardUI;
import gamesource.util.CardArrayReader;
import gamesource.util.EquipmentArrayReader;

public class DragCheck extends BaseAppState{
    private Container dragWindow;
    private int totalCost;
    private int totalMoney;
    private CardUI cardUI;
    private EquipmentUI equipmentUI;
    private int numberOfBuy;
    private Label label;
    private ActionButton actionButton;
    private CloseCommand closeCommand = new CloseCommand();

    public DragCheck(int totalCost, int totalMoney, CardUI cardUI, EquipmentUI equipmentUI, int numberOfBuy){
        this.totalCost = totalCost;
        this.totalMoney = totalMoney;
        this.cardUI = cardUI;
        this.numberOfBuy = numberOfBuy;
        this.equipmentUI = equipmentUI;
    }

    @Override
    protected void initialize(Application application){

    }

    @Override
    protected void cleanup(Application application){

    }

    protected void createAllDrag(){
        dragWindow = new Container();
        
        dragWindow.addChild(new Label("Check", new ElementId("window.title.label")));
        CursorEventControl.addListenersToSpatial(dragWindow, new DragHandler());
        MouseEventControl.addListenersToSpatial(dragWindow, ConsumingMouseListener.INSTANCE);

        label = dragWindow.addChild(new Label("Do you make sure you want to buy these cards ? It will cost " + totalCost + "\nYou have "+ MainRole.getInstance().getGold()));
        actionButton = dragWindow.addChild(new ActionButton(new CallMethodAction(this, "Confirm")));
        dragWindow.setLocalTranslation(600, 700, 100);
        dragWindow.setAlpha(10f);
        label.setFontSize(18f);
        getState(PopupState.class).showPopup(dragWindow);
    }

    public void calculatePreferLocation(){
        float xOfWindow = getState(ShopAppState.class).getGeneral().getLocalTranslation().x + 
            getState(ShopAppState.class).getGeneral().getPreferredSize().x;
        float yOfWindow = getState(FormattedTextForShop.class).getWindow().getLocalTranslation().y - 
            getState(FormattedTextForShop.class).getWindow().getPreferredSize().y - 50;
        dragWindow.setLocalTranslation(xOfWindow, yOfWindow, 100);
    }

    protected void Confirm(){
        totalMoney = MainRole.getInstance().getGold();
        if(totalMoney - totalCost>= 0){
            if(cardUI != null){
                for(int i=0; i<numberOfBuy; i++){
                    MainRole.getInstance().getDeck_().add(CardArrayReader
                        .findCardByCardUIs(ShopAppState.getShopCard(), ShopAppState.getShopCardUIs(), cardUI));
                }
                MainRole.getInstance().setGold(MainRole.getInstance().getGold() - totalCost);
            }else{
                for(int i=0; i< numberOfBuy; i++){
                    MainRole.getInstance().getEquipments().add(EquipmentArrayReader
                        .findEquipByUIs(ShopAppState.getShopEquipment(), ShopAppState.getShopEquipmentUIs(), equipmentUI));
                }
                MainRole.getInstance().setGold(MainRole.getInstance().getGold() - totalCost);
            }
            getState(TabTextForShop.class).getStateManager().detach(DragCheck.this);
        }else{
            dragWindow.detachChild(actionButton);
            label.setText("Sorry! You have not enough money!");
            dragWindow.addChild(new ActionButton(new CallMethodAction(this, "OK")));
        }
    }

    public void OK(){
        getState(TabTextForShop.class).getStateManager().detach(DragCheck.this);
    }

    @Override
    protected void onEnable(){
        createAllDrag();
        getState(PopupState.class).showModalPopup(dragWindow, closeCommand);
    }

    @Override
    protected void onDisable(){
        if(dragWindow != null){
            dragWindow.removeFromParent();
        }
        dragWindow.removeFromParent();
    }

    private class CloseCommand implements Command<Object>{
        public void execute(Object src){
            getState(FormattedTextForShop.class).getStateManager().detach(DragCheck.this);
        }
    }
}