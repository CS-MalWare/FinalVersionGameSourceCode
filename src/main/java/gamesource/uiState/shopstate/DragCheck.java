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

public class DragCheck extends BaseAppState{
    private Container dragWindow;
    private int totalCost;
    private int totalMoney;
    private CardUI cardUI;
    private int numberOfBuy;
    private CloseCommand closeCommand = new CloseCommand();

    public DragCheck(int totalCost, int totalMoney, CardUI cardUI, int numberOfBuy){
        this.totalCost = totalCost;
        this.totalMoney = totalMoney;
        this.cardUI = cardUI;
        this.numberOfBuy = numberOfBuy;
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

        dragWindow.addChild(new Label("Do you make sure you want to buy these cards ? It will cost " + totalCost));
        dragWindow.addChild(new ActionButton(new CallMethodAction(this, "Confirm")));
        dragWindow.setLocalTranslation(600, 700, 100);
        dragWindow.setAlpha(2f);
        getState(PopupState.class).showPopup(dragWindow);
    }

    protected void Confirm(){
        totalMoney = totalMoney - totalCost;
        for(int i=0; i<numberOfBuy; i++){
            MainRole.getInstance().getDeck_().add(CardArrayReader.findCardByUI(cardUI));
        }
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