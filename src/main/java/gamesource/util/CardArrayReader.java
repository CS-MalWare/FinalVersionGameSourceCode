package gamesource.util;

import java.util.ArrayList;

import com.simsilica.lemur.Checkbox;
import com.simsilica.lemur.component.IconComponent;

import gamesource.battleState.card.Card;
import gamesource.battleState.card.Card.RARITY;
import gamesource.uiState.bagstate.CardUI;
/*
This class is try to change Card to CardUI which can be used for GUI settings
*/
public class CardArrayReader {
    private static ArrayList<Card> cards;
    private static CardUI[] cardUIs;

    public CardArrayReader(){

    }

    public CardArrayReader(ArrayList<Card> cards){
        this.cards = cards;
        cardUIs = new CardUI[cards.size()];
    }

    public CardUI[] CardArrayToCardUIs(){                       //change cards to array of CardUI
        CardUI cardUI;
        for(int it = 0; it < cards.size(); it++){
            IconComponent iconComponent = new IconComponent(cards.get(it).getPath());
            iconComponent.setIconScale(0.3f);
            if(cards.get(it).getRarity() == RARITY.RARE){
                cardUI = new CardUI(iconComponent, cards.get(it).getName(), 200, 
                    new Checkbox("Buy"), cards.get(it).getDescription());
                cardUIs[it] = cardUI;
            }else if(cards.get(it).getRarity() == RARITY.COMMON){
                cardUI = new CardUI(iconComponent, cards.get(it).getName(), 50, cards.get(it).getDescription());
                cardUIs[it] = cardUI;
            }else if(cards.get(it).getRarity() == RARITY.LEGENDARY){
                cardUI = new CardUI(iconComponent, cards.get(it).getName(), 250, cards.get(it).getDescription());
                cardUIs[it] = cardUI;
            }else if(cards.get(it).getRarity() == RARITY.EPIC){
                cardUI = new CardUI(iconComponent, cards.get(it).getCardName(), 100, cards.get(it).getDescription());
                cardUIs[it] = cardUI;
            }
        }
        return cardUIs;
    }

    public static CardUI[] cardArrayToCardUIs(ArrayList<Card> cardsEx){         //static methods
        CardUI cardUI;
        cardUIs = null;
        cardUIs = new CardUI[cardsEx.size()];
        for(int it = 0; it < cardsEx.size(); it++){
            IconComponent iconComponent = new IconComponent(cardsEx.get(it).getPath());
            iconComponent.setIconScale(0.3f);
            if(cardsEx.get(it).getRarity() == RARITY.RARE){
                cardUI = new CardUI(iconComponent, cardsEx.get(it).getName(), 200, 
                    new Checkbox("Buy"), cardsEx.get(it).getDescription());
                cardUIs[it] = cardUI;
            }else if(cardsEx.get(it).getRarity() == RARITY.COMMON){
                cardUI = new CardUI(iconComponent, cardsEx.get(it).getName(), 50, cardsEx.get(it).getDescription());
                cardUIs[it] = cardUI;
            }else if(cardsEx.get(it).getRarity() == RARITY.LEGENDARY){
                cardUI = new CardUI(iconComponent, cardsEx.get(it).getName(), 250, cardsEx.get(it).getDescription());
                cardUIs[it] = cardUI;
            }else if(cardsEx.get(it).getRarity() == RARITY.EPIC){
                cardUI = new CardUI(iconComponent, cardsEx.get(it).getCardName(), 100, cardsEx.get(it).getDescription());
                cardUIs[it] = cardUI;
            }
        }
        return cardUIs;
    }

    public static CardUI cardToCardUI(Card card){                       //change single card to cardUI
        IconComponent iconComponent = new IconComponent(card.getPath());
        iconComponent.setIconScale(0.3f);
        CardUI cardUI = new CardUI();
        if(card.getRarity() == RARITY.RARE){
            cardUI = new CardUI(iconComponent, card.getCardName(), 200, card.getDescription());
        }else if(card.getRarity() == RARITY.COMMON){
            cardUI = new CardUI(iconComponent, card.getCardName(), 50, card.getDescription());
        }else if(card.getRarity() == RARITY.LEGENDARY){
            cardUI = new CardUI(iconComponent, card.getCardName(), 250, card.getDescription());
        }else if(card.getRarity() == RARITY.EPIC){
            cardUI = new CardUI(iconComponent, card.getCardName(), 100, card.getDescription());
        }
        return cardUI;
    }

    public Card findCardByUI(CardUI cardUI){                            //find the card from CardUI
        Card card = cards.get(0);
        for(int i=0; i<cardUIs.length; i++){
            if(cardUIs[i] == cardUI){
                card = cards.get(i);
            }
        }
        return card;
    }

    public static Card findCardByCardUIs(Card[] cardsExtern, CardUI[] cardUIsExtern, CardUI cardUI){ //static methods
        Card card = cardsExtern[0];
        for(int i=0; i<cardUIsExtern.length; i++){
            if(cardUI.equals(cardUIsExtern[i].getButton())){
                card = cardsExtern[i];
            }
        }
        return card;
    }
}