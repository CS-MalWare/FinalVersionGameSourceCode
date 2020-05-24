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

public class ShopAppState extends BaseAppState implements ActionListener{
    private AppStateManager stateManager;
    private final static String shopString = "Shop";
    private Container general;
    private static CardUI[] cardUIs = new CardUI[40];
    private static Card[] cards = new Card[40];
    private CardUI[] cardUIsCopy = new CardUI[40];
    private CardUI[] saberCardUIs = new CardUI[20];
    private CardUI[] neutralCardUIs = new CardUI[20];

    private EquipmentUI[] equipmentUIs = new EquipmentUI[40];
    private Equipment[] equipments = new Equipment[40];
    private EquipmentUI[] equipmentUIsCopy = new EquipmentUI[40];
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
    public void initialize(Application application){//测试的时候先是启动加载，稍后会做出更新
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
            //cardUIs[i+40] =  saberCardUIs[i];
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
        leftPart.addChild(cards, 2, 0);
        leftPart.addChild(equipments, 3, 0);
        leftPartContainer = new Container("glass");
        leftPartContainer.addChild(leftPart);
        general.addChild(leftPartContainer, Position.West);

        centralPart = new Container("glass");
        general.addChild(centralPart, Position.Center);
        centralPart.setLocalTranslation(200, app.getCamera().getHeight() - 50, 0);
        
        cards.addClickCommands(new CardsDirectoryClick());
        general.setAlpha(2f);
    }

    public void showCards(Container centralPart){
        System.out.println(cardUIs.length);
       if(cardUIs.length > 12){
            buttomPartContainer = new Container();
            pagesContainer = new Container();
            pagesContainer.setLayout(new SpringGridLayout(Axis.X, Axis.Y, FillMode.None, FillMode.None));
            general.addChild(buttomPartContainer, Position.South);
            buttomPartContainer.setLayout(new SpringGridLayout(Axis.Y, Axis.Y));
            buttomPartContainer.addChild(pagesContainer);
            
            int pageNumber = cardUIs.length % 12;
            for(int i=1; i<=pageNumber; i++){
                Button button = pagesContainer.addChild(new Button(String.valueOf(i)));
                button.addClickCommands(new PageButtonClick());
            }
            Button closeButton = buttomPartContainer.addChild(new Button("Close"), 1, 0);
            closeButton.addClickCommands(new CloseCommand());
        }

        for(CardUI cardUI: cardUIs){
            cardUI.addAction(new CardsButtonClick());
        }

        for(int i=0; i<12; i++){
            int j = i % 4;
            int z = (i - j)/4;
            cardUIs[i].addButtonToContainer(centralPart, 2*z, j);
            //cardUI.addAction(new CardsButtonClick());
            //cardUI.getCheckBox().setTextHAlignment(HAlignment.Center);
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
                        TabTextForShop textForShop = new TabTextForShop.Builder(cardUI, "Info", "Name", "Cost")
                            .setMessage1(cardUI.getDescription()).setMessage2(cardUI.getButton().getName())
                            .setMessage3(Integer.toString(cardUI.getCardMoney())).build();
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
        Button caster = new Button("Saber");
        Button neutral = new Button("Neutral");
        Button backToStart = new Button("Back");
        //Button saber = new Button("Saber");

        leftPart.addChild(general);
        leftPart.addChild(caster);
        leftPart.addChild(neutral);
        leftPart.addChild(backToStart);
        //leftPart.addChild(saber);

        general.addClickCommands(new ShowGeneral());
        caster.addClickCommands(new ShowCaster());
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

    private class ShowCaster implements Command<Button>{
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
            leftPart.addChild(cards, 2, 0);
            leftPart.addChild(equipments, 3, 0);

            cards.addClickCommands(new CardsDirectoryClick());
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
 }
