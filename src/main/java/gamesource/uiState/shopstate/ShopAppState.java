package gamesource.uiState.shopstate;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.ColorRGBA;
import com.simsilica.lemur.*;
import com.simsilica.lemur.component.BorderLayout;
import com.simsilica.lemur.component.IconComponent;
import com.simsilica.lemur.component.QuadBackgroundComponent;
import com.simsilica.lemur.component.SpringGridLayout;
import com.simsilica.lemur.component.BorderLayout.Position;
import com.simsilica.lemur.style.BaseStyles;
import com.simsilica.lemur.style.ElementId;
import com.simsilica.lemur.style.Styles;

import gamesource.battleState.card.Card;
import gamesource.battleState.card.CreateCard;
import gamesource.battleState.card.Card.OCCUPATION;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.equipment.CreateEquipment;
import gamesource.battleState.equipment.Equipment;
import gamesource.uiState.bagstate.CardUI;
import gamesource.util.CardArrayReader;
import gamesource.util.EquipmentArrayReader;

public class ShopAppState extends BaseAppState implements ActionListener{
    private AppStateManager stateManager;
    private final static String shopString = "Shop";
    private Container general;
    private static CardUI[] cardUIs = new CardUI[40];
    private static Card[] cards = new Card[40];
    private CardUI[] cardUIsCopy = new CardUI[40];
    private CardUI[] saberCardUIs = new CardUI[20];
    private CardUI[] neutralCardUIs = new CardUI[20];

    private static EquipmentUI[] equipmentUIs = new EquipmentUI[20];
    private static Equipment[] equipments = new Equipment[20];
    private EquipmentUI[] equipmentUIsCopy = new EquipmentUI[20];
    //private CardUI[] saberCardUIs = new CardUI[20];
    private Container centralPart;
    private Container leftPartContainer;
    private Container leftPart;
    private Container buttomPartContainer;
    private Container pagesContainer;
    private SimpleApplication app;
    private ShopMoney shopMoney = new ShopMoney(MainRole.getInstance().getGold());
    private Styles styles;
    private boolean isMapPressed = false;

    private Card saberCard;
    private Card neutralCard;
    private Equipment commonEquipment;

    @Override
    public void initialize(Application application){
        app = (SimpleApplication) application;
        GuiGlobals.initialize(app);
        BaseStyles.loadGlassStyle();
        GuiGlobals.getInstance().requestFocus(general);

        app.getInputManager().addMapping("shop", new KeyTrigger(KeyInput.KEY_G));
        app.getInputManager().addListener(new ShopListener(), "shop");

        general = new Container("glass");
        BorderLayout borderLayout = new BorderLayout();
        general.setLayout(borderLayout);
        general.setLocalTranslation(5, app.getCamera().getHeight()-50, 0);
     
        app.getInputManager().setCursorVisible(false);

        for(int i=0; i < 20; i++){
            saberCard = CreateCard.getRandomCard(OCCUPATION.SABER);
            neutralCard = CreateCard.getRandomCard(OCCUPATION.NEUTRAL);
            commonEquipment = CreateEquipment.getRandomCommonEquipment();
            saberCardUIs[i] = CardArrayReader.cardToCardUI(saberCard);
            neutralCardUIs[i] = CardArrayReader.cardToCardUI(neutralCard);

            //saberCardUIs[i] = CardArrayReader.cardToCardUI(CreateCard.getRandomCard(OCCUPATION.SABER));
            cardUIs[i] = saberCardUIs[i];
            cards[i] = saberCard;
            cards[i+20] = neutralCard;
            cardUIsCopy[i] = saberCardUIs[i];
            cardUIs[i+20] = neutralCardUIs[i];
            cardUIsCopy[i+20] = neutralCardUIs[i];

            equipmentUIs[i] = EquipmentArrayReader.equipmentToUI(commonEquipment);
            equipmentUIsCopy[i] = equipmentUIs[i];
            equipments[i] = commonEquipment;
            //cardUIs[i+40] =  saberCardUIs[i];
        }    

        for(CardUI cardUI: cardUIs){
            cardUI.addAction(new CardsButtonClick());
        }

        for(EquipmentUI equipmentUI : equipmentUIs){
            equipmentUI.addAction(new EquipmentCardsClick());
        }
    }

    public void showShop(){
        app.getFlyByCamera().setDragToRotate(true);
        styles = GuiGlobals.getInstance().getStyles();
        styles.setDefaultStyle("glass");

        styles.getSelector(Panel.ELEMENT_ID, "glass").set("background", 
            new QuadBackgroundComponent(new ColorRGBA(0, 0.25f, 0.25f, 0.5f)));
        styles.getSelector(Checkbox.ELEMENT_ID, "glass").set("background", 
            new QuadBackgroundComponent(new ColorRGBA(0, 0.5f, 0.5f, 0.5f)));
        styles.getSelector("spacer", "glass").set("background", 
            new QuadBackgroundComponent(new ColorRGBA(1, 0.0f, 0.0f, 0.0f)));
        styles.getSelector("header", "glass").set("background", 
            new QuadBackgroundComponent(new ColorRGBA(0, 0.75f, 0.75f, 0.5f)));
        styles.getSelector(Slider.THUMB_ID, "glass").set("text", "[]", false);
        styles.getSelector("header", "glass").set("shadowColor", new ColorRGBA(1, 0f, 0f, 1));
        app.getGuiNode().attachChild(general);
        
        leftPart = new Container("glass");
        SpringGridLayout leftLayOut = new SpringGridLayout(Axis.Y, Axis.X, FillMode.None, FillMode.None);
        leftPart.setLayout(leftLayOut);
        
        leftPart.setBackground(new QuadBackgroundComponent(new ColorRGBA(0, 0.5f, 0.5f, 0.5f), 5, 5, 0.02f, false));
        leftPart.addChild(new Label("Shop", new ElementId("header"), "glass"), 0, 0);
        leftPart.addChild(new Panel(2, 2, ColorRGBA.Cyan, "glass"), 1, 0).setUserData(LayerComparator.LAYER, 2);
        
        Button cards = new Button("Cards");
        Button equipments = new Button("Equipments");
        Button health = new Button("Health");

        leftPart.addChild(cards, 2, 0);
        leftPart.addChild(equipments, 3, 0);
        leftPart.addChild(health, 4, 0);

        leftPartContainer = new Container("glass");
        leftPartContainer.addChild(leftPart);
        general.addChild(leftPartContainer, Position.West);

        centralPart = new Container("glass");
        general.addChild(centralPart, Position.Center);
        centralPart.setLocalTranslation(200, app.getCamera().getHeight() - 50, 0);
        
        buttomPartContainer = new Container("glass");
        general.addChild(buttomPartContainer, Position.South);

        cards.addClickCommands(new CardsDirectoryClick());
        health.addClickCommands(new HealthClick());
        equipments.addClickCommands(new EquipmentClick());
        general.setAlpha(2f);
    }

    public void showCards(Container centralPart){
        System.out.println(cardUIs.length);
       if(cardUIs.length > 12){
            pagesContainer = new Container();
            pagesContainer.setLayout(new SpringGridLayout(Axis.X, Axis.Y, FillMode.None, FillMode.None));
            general.addChild(buttomPartContainer, Position.South);
            buttomPartContainer.setLayout(new SpringGridLayout(Axis.Y, Axis.Y));
            buttomPartContainer.addChild(pagesContainer);
            
            int pageNumber = (cardUIs.length - cardUIs.length % 12) / 12 + 1;
            for(int i=1; i<=pageNumber; i++){
                Button button = pagesContainer.addChild(new Button(String.valueOf(i)));
                button.addClickCommands(new PageButtonClick());
            }
            Button closeButton = buttomPartContainer.addChild(new Button("Close"), 1, 0);
            closeButton.addClickCommands(new CloseCommand());
        }

        for(int i=0; i<12; i++){
            int j = i % 4;
            int z = (i - j)/4;
            cardUIs[i].addButtonToContainer(centralPart, 2*z, j);
            //cardUI.addAction(new CardsButtonClick());
            //cardUI.getCheckBox().setTextHAlignment(HAlignment.Center);
        }
    }

    public void showEquipments(Container centralPart){
        pagesContainer = new Container();
        pagesContainer.setLayout(new SpringGridLayout(Axis.X, Axis.Y, FillMode.None, FillMode.None));
        general.addChild(buttomPartContainer, Position.South);
        buttomPartContainer.setLayout(new SpringGridLayout(Axis.Y, Axis.Y));
        buttomPartContainer.addChild(pagesContainer);
        
        int pageNumber = (equipmentUIs.length - equipmentUIs.length % 12) / 12 + 1;
        for(int i=1; i<=pageNumber; i++){
            Button button = pagesContainer.addChild(new Button(String.valueOf(i)));
            button.addClickCommands(new PageButtonClickForEquip());
        }
        Button closeButton = buttomPartContainer.addChild(new Button("Close"), 1, 0);
        closeButton.addClickCommands(new CloseCommand());

        for(int i=0; i<12; i++){
            int j = i % 4;
            int z = (i - j)/4;
            equipmentUIs[i].addToButtonContainer(centralPart, z*2, j);
        }
    }

    public void caculateMoney(int cardsMoney){
        this.shopMoney.setTotalMoneyOfCards(shopMoney.getTotalMoneyOfCards() + cardsMoney);
    }
    private class CardsButtonClick implements Command<Button>{
        public void execute(Button button){
            if(button.isPressed()){
                for(CardUI cardUI : cardUIs){
                    if(cardUI.equals(button)){
                        TabTextForShop textForShop = new TabTextForShop.Builder(cardUI, null,"Info", "Name", "Cost")
                            .setMessage1(cardUI.getDescription()).setMessage2(cardUI.getButton().getName())
                            .setMessage3(Integer.toString(cardUI.getCardMoney())).build();
                        getStateManager().attach(textForShop);
                        break;
                    }
                }
            }
        }
    }

    private class EquipmentCardsClick implements Command<Button>{
        public void execute(Button button){
            if(button.isPressed()){
                for(EquipmentUI equipmentUI : equipmentUIs){
                    if(equipmentUI.equals(button)){
                        TabTextForShop textForShop = new TabTextForShop.Builder(null, equipmentUI, "Info", "Name", "Cost")
                            .setMessage1(equipmentUI.getDescription()).setMessage2(equipmentUI.getButton().getName())
                            .setMessage3(Integer.toString(equipmentUI.getCardMoney())).build();
                        getStateManager().attach(textForShop);
                        break;
                    }
                }
            }
        }
    }

    private class CloseCommand implements Command<Button>{
        public void execute(Button button){
            if(button.isPressed()){
                general.removeFromParent();
                app.getFlyByCamera().setDragToRotate(false);
            }
        }
    }

    public void showCardsType(){
        leftPart.setBackground(new QuadBackgroundComponent(new ColorRGBA(0, 0.5f, 0.5f, 0.5f), 5, 5, 0.02f, false));
        leftPart.addChild(new Label("Shop", new ElementId("header"), "glass"));
        leftPart.addChild(new Panel(2, 2, ColorRGBA.Cyan, "glass")).setUserData(LayerComparator.LAYER, 2);

        Button general = new Button("General");
        Button saber = new Button("Saber");
        Button neutral = new Button("Neutral");
        Button backToStart = new Button("Back");
        //Button saber = new Button("Saber");

        leftPart.addChild(general);
        leftPart.addChild(saber);
        leftPart.addChild(neutral);
        leftPart.addChild(backToStart);
        //leftPart.addChild(saber);

        general.addClickCommands(new ShowGeneral());
        saber.addClickCommands(new ShowSaber());
        neutral.addClickCommands(new ShowNeutral());
        backToStart.addClickCommands(new BackToStart());
    }
    
    private class CardsDirectoryClick implements Command<Button>{
        public void execute(Button button){
            if(button.isPressed()){
                detachShop();
                showCards(centralPart);
                showCardsType();
                isMapPressed = true;
            }
        }
    }

    private class ShowGeneral implements Command<Button>{
        public void execute(Button button){
            centralPart.detachAllChildren();
            pagesContainer.detachAllChildren();
            cardUIs = cardUIsCopy;
            int pageNumber = cardUIs.length % 12;

            for(int i=0; i<12; i++){
                int j = i % 4;
                int z = (i-j) / 4;
                cardUIs[i].addButtonToContainer(centralPart, 2*z, j);
            }

            for(int i=1; i <= pageNumber; i++){
                Button pageButton = pagesContainer.addChild(new Button(String.valueOf(i)));
                pageButton.addClickCommands(new PageButtonClick());
            }
        }
    }

    private class ShowSaber implements Command<Button>{
        public void execute(Button button){
            centralPart.detachAllChildren();
            pagesContainer.detachAllChildren();
            cardUIs = saberCardUIs;

            for(int i=0; i<12; i++){
                int j = i % 4;
                int z = (i - j)/4;
                cardUIs[i].addButtonToContainer(centralPart, 2*z, j);
            }
            
            for(int i=1; i<=2; i++){
                Button pageButton = pagesContainer.addChild(new Button(String.valueOf(i)));
                pageButton.addClickCommands(new PageButtonClick());
            }

            cardUIs = cardUIsCopy;
        }
    }

    private class BackToStart implements Command<Button>{
        public void execute(Button button){
            leftPart.detachAllChildren();
            SpringGridLayout leftLayOut = new SpringGridLayout(Axis.Y, Axis.X, FillMode.None, FillMode.None);
            leftPart.setLayout(leftLayOut);
        
            leftPart.setBackground(new QuadBackgroundComponent(new ColorRGBA(0, 0.5f, 0.5f, 0.5f), 5, 5, 0.02f, false));
            leftPart.addChild(new Label("Shop", new ElementId("header"), "glass"), 0, 0);
            leftPart.addChild(new Panel(2, 2, ColorRGBA.Cyan, "glass"), 1, 0).setUserData(LayerComparator.LAYER, 2);
        
            Button cards = new Button("Cards");
            Button equipments = new Button("Equipments");
            Button health = new Button("Health");

            leftPart.addChild(cards, 2, 0);
            leftPart.addChild(equipments, 3, 0);
            leftPart.addChild(health, 4, 0);

            cards.addClickCommands(new CardsDirectoryClick());
            health.addClickCommands(new HealthClick());
            equipments.addClickCommands(new EquipmentClick());
        }
    }

    private class ShowNeutral implements Command<Button>{
        public void execute(Button button){
            centralPart.detachAllChildren();
            pagesContainer.detachAllChildren();
            cardUIs = neutralCardUIs;

            for(int i=0; i<12; i++){
                int j = i % 4;
                int z= (i - j)/4;
                cardUIs[i].addButtonToContainer(centralPart, 2*z, j);
            }

            for(int i=1; i<=2; i++){
                Button pageButton = pagesContainer.addChild(new Button(String.valueOf(i)));
                pageButton.addClickCommands(new PageButtonClick());
            }

            cardUIs = cardUIsCopy;
        }
    }

    private class PageButtonClick implements Command<Button>{
        public void execute(Button button){
            int pageIndex = Integer.parseInt(button.getText());
            
            centralPart.detachAllChildren();
            if(cardUIs.length <= 12*pageIndex){
                for(int i=12*(pageIndex-1); i<cardUIs.length; i++){
                    int index = i-12*(pageIndex-1);
                    int j = index % 4;
                    int z = (index - j)/4;
                    cardUIs[i].addButtonToContainer(centralPart, 2*z, j);
                }
            }else{
                for(int i=12*(pageIndex-1); i<12*pageIndex; i++){
                    int index = i-12*(pageIndex-1);
                    int j = index % 4;
                    int z = (index -j) / 4;
                    cardUIs[i].addButtonToContainer(centralPart, 2*z, j);
                }
            }
        }    
    }

    private class PageButtonClickForEquip implements Command<Button>{
        public void execute(Button button){
            int pageIndex = Integer.parseInt(button.getText());
            
            centralPart.detachAllChildren();
            if(equipmentUIs.length <= 12*pageIndex){
                for(int i=12*(pageIndex-1); i<equipmentUIs.length; i++){
                    int index = i-12*(pageIndex-1);
                    int j = index % 4;
                    int z = (index - j)/4;
                    equipmentUIs[i].addToButtonContainer(centralPart, z*2, j);
                }
            }else{
                for(int i=12*(pageIndex-1); i<12*pageIndex; i++){
                    int index = i-12*(pageIndex-1);
                    int j = index % 4;
                    int z = (index -j) / 4;
                    equipmentUIs[i].addToButtonContainer(centralPart, 2*z, j);
                }
            }    
        }
    }

    private class HealthClick implements Command<Button>{
        public void execute(Button button){
            centralPart.detachAllChildren();
            if(pagesContainer != null){
                pagesContainer.detachAllChildren();
            }

            IconComponent medicine = new IconComponent("Util/medicine.jpg");
            medicine.setIconScale(0.8f);
            CardUI healMedicine = new CardUI(medicine, "Medicine", 100, "Get 20 point health back");
            healMedicine.addButtonToContainer(centralPart, 0, 0);
            healMedicine.addAction(new Heal());
        }
    }

    private class Heal implements Command<Button>{
        public void execute(Button button){
            HealCheck healCheck = new HealCheck(50, "You will Cost 50 to get 20 point health!");
            getStateManager().attach(healCheck);
        }
    }

    private class EquipmentClick implements Command<Button>{
        public void execute(Button button){
            detachShop();
            leftPart.setBackground(new QuadBackgroundComponent(new ColorRGBA(0, 0.5f, 0.5f, 0.5f), 5, 5, 0.02f, false));
            leftPart.addChild(new Label("Shop", new ElementId("header"), "glass"));
            leftPart.addChild(new Panel(2, 2, ColorRGBA.Cyan, "glass")).setUserData(LayerComparator.LAYER, 2);

            Button backToStart = new Button("Back");
            leftPart.addChild(backToStart);

            backToStart.addClickCommands(new BackToStart());
            showEquipments(centralPart);
        }
    }

    public void refreshPage(CardUI[] cardUIs){
        buttomPartContainer.detachAllChildren();
    }

    public Container getGeneral(){
        return general;
    }

    public void detachShop(){
        leftPart.detachAllChildren();
    }

    @Override
    protected void cleanup(Application application){
        app.getGuiNode().detachChild(general);
        onDisable();
        app.getFlyByCamera().setDragToRotate(false);
    }

    @Override
    protected void onEnable(){
    }

    @Override
    protected void onDisable(){
        app.getGuiNode().removeFromParent();
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf){
        
    }

    public void onFight(){
        cleanup();
        app.getInputManager().deleteMapping("shop");
    }

    public void exitFight(){
        app.getInputManager().addMapping("shop", new KeyTrigger(KeyInput.KEY_G));
        app.getInputManager().addListener(new ShopListener(), "shop");
    }

    class ShopListener implements ActionListener{
        public void onAction(String name, boolean isPressed, float tpf){
            if("shop".equals(name) && isPressed && !isMapPressed){
                showShop();
                isMapPressed = true;
            }else if("shop".equals(name) && isPressed && isMapPressed){
                isMapPressed = false;
                cleanup();
            }
        }
    }

    public static CardUI[] getShopCardUIs(){
        return cardUIs;
    }

    public static Card[] getShopCard(){
        return cards;
    }

    public static EquipmentUI[] getShopEquipmentUIs(){
        return equipmentUIs;
    }

    public static Equipment[] getShopEquipment(){
        return equipments;
    }
}
