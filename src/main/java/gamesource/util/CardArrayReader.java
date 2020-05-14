package gamesource.util;

import java.util.ArrayList;

import com.simsilica.lemur.Checkbox;
import com.simsilica.lemur.component.IconComponent;

import gamesource.battleState.card.Card;
import gamesource.uiState.bagstate.CardUI;

public class CardArrayReader {
    private static ArrayList<Card> cards;
    private static CardUI[] cardUIs;

    public CardArrayReader(){

    }

    public CardArrayReader(ArrayList<Card> cards){
        this.cards = cards;
        cardUIs = new CardUI[cards.size()];
    }

    public CardUI[] CardArrayToCardUIs(){
        for(int it = 0; it < cards.size(); it++){
            IconComponent iconComponent = new IconComponent(cards.get(it).getPath());
            iconComponent.setIconScale(0.3f);
            CardUI cardUI = new CardUI(iconComponent, cards.get(it).getName(), cards.get(it).getCost(), 
            new Checkbox("Buy"), cards.get(it).getDescription());
            cardUIs[it] = cardUI;
        }
        return cardUIs;
    }

    public static CardUI cardToCardUI(Card card){
        IconComponent iconComponent = new IconComponent(card.getPath());
        iconComponent.setIconScale(0.3f);
        CardUI cardUI = new CardUI(iconComponent, card.getName(), card.getCost(), card.getDescription());
        return cardUI;
    }

    public Card findCardByUI(CardUI cardUI){
        Card card = cards.get(0);
        for(int i=0; i<cardUIs.length; i++){
            if(cardUIs[i] == cardUI){
                card = cards.get(i);
            }
        }
        return card;
    }

    public static Card findCardByCardUIs(Card[] cardsExtern, CardUI[] cardUIsExtern, CardUI cardUI){
        Card card = cardsExtern[0];
        for(int i=0; i<cardUIsExtern.length; i++){
            if(cardUI.equals(cardUIsExtern[i].getButton())){
                card = cardsExtern[i];
            }
        }
        return card;
    }
}