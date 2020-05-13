package gamesource.uiState;

import java.util.ArrayList;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.simsilica.lemur.ActionButton;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.CallMethodAction;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.event.PopupState;
import com.simsilica.lemur.style.ElementId;

import gamesource.State.worldState.FirstState;
import gamesource.battleState.card.Card;
import gamesource.battleState.card.CreateCard;
import gamesource.battleState.card.Card.OCCUPATION;
import gamesource.battleState.character.MainRole;
import gamesource.uiState.bagstate.CardUI;
import gamesource.util.CardArrayReader;

public class ChestFind extends BaseAppState{
    private Container window;
    private CloseCommand closeCommand = new CloseCommand();
    private SimpleApplication app;
    private CardUI[] cardUIs= new CardUI[5];
    private ArrayList<Card> cards;

    public ChestFind(){
        cards.add(CreateCard.getRandomCard(OCCUPATION.NEUTRAL));
        cards.add(CreateCard.getRandomCard(OCCUPATION.CASTER));
        CardArrayReader cardArrayReader = new CardArrayReader(cards);
        cardUIs = cardArrayReader.CardArrayToCardUIs();
    }

    @Override
    protected void initialize(Application application){
        app = (SimpleApplication) application;
    }

    @Override
    protected void cleanup(Application application){

    }

    @Override
    protected void onDisable() {

    }

    @Override
    protected void onEnable(){
        int row = 0;
        int column = 0;
        
        window = new Container();
        window.addChild(new Label("Treasure Chest", new ElementId("window.title.label")));

        Container cardSets = window.addChild(new Container());
        for(CardUI cardUI : cardUIs){
            Button button = cardUI.getButton();
            cardSets.addChild(button, row, column++);
        }
        calculatePreferLocation();

        window.addChild(new ActionButton(new CallMethodAction("Yes", window, "removeFromParent")));
        getState(PopupState.class).showPopup(window, closeCommand);
    }

    protected void calculatePreferLocation(){
        float xOfCenter = getApplication().getCamera().getWidth() / 2;
        float yOfCenter = getApplication().getCamera().getHeight() / 2;
        
        float halfWindowWidth = window.getPreferredSize().x / 2;
        float halfWindowHeight = window.getPreferredSize().y / 2;

        window.setLocalTranslation(xOfCenter-halfWindowWidth, yOfCenter-halfWindowHeight, 100);
    }

    private class CloseCommand implements Command<Object>{
        public void execute(Object src){
            getState(FirstState.class).getStateManager().detach(ChestFind.this);
        }
    }
}