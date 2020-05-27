package gamesource.uiState.bagstate;

import java.util.ArrayList;

import javax.swing.SpringLayout;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.simsilica.lemur.Axis;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Checkbox;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.FillMode;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.HAlignment;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.LayerComparator;
import com.simsilica.lemur.Panel;
import com.simsilica.lemur.ProgressBar;
import com.simsilica.lemur.component.BorderLayout;
import com.simsilica.lemur.component.IconComponent;
import com.simsilica.lemur.component.QuadBackgroundComponent;
import com.simsilica.lemur.component.SpringGridLayout;
import com.simsilica.lemur.component.BorderLayout.Position;
import com.simsilica.lemur.core.VersionedReference;
import com.simsilica.lemur.style.BaseStyles;
import com.simsilica.lemur.style.ElementId;
import com.simsilica.lemur.style.Styles;

import gamesource.battleState.card.Card;
import gamesource.battleState.card.Card.OCCUPATION;
import gamesource.battleState.character.MainRole;
import gamesource.util.*;

public class BagAppState extends BaseAppState{
    private SimpleApplication app;
    private InputManager inputManager;
    private final static String bagString = "Bag";
    private VersionedReference[] cardsReference = new VersionedReference[100];
    private ArrayList<Card> mainRoleCards = MainRole.getInstance().getDeck_();
    private CardArrayReader cardArrayReader = new CardArrayReader(MainRole.getInstance().getDeck_());
    private CardUI[] cardUIs = new CardUI[60];
    private CardUI[] cardUIsCopy = new CardUI[60];
    private CardUI[] saberCardUIs = new CardUI[30];
    private CardUI[] neutralCardUIs = new CardUI[30];
    private Container generalBorder;
    private Container centralPart;
    private Container leftPartContainer;
    private Container buttomPartContainer;
    private Container pagesContainer;
    private Container leftPart;
    private ProgressBar progressBar;
    private Label moneyLabel;

    private BagMoney totalMoney = new BagMoney();
    private Styles styles;
    private boolean isOpenBag = false;
    private boolean isShowCards = false;

    private boolean isFirstOpen = true;

    @Override
    public void initialize(Application application){
        app = (SimpleApplication) application;
        inputManager = app.getInputManager();
        
        inputManager.addMapping(bagString, new KeyTrigger(KeyInput.KEY_B));
        inputManager.addListener(new BagListener(), bagString);

        GuiGlobals.initialize(app);
        BaseStyles.loadGlassStyle();
        GuiGlobals.getInstance().requestFocus(generalBorder);

        generalBorder = new Container("glass");
        BorderLayout borderLayout = new BorderLayout();
        generalBorder.setLayout(borderLayout);
        generalBorder.setLocalTranslation(5, app.getCamera().getHeight(), 0);
        totalMoney.setMoney(MainRole.getInstance().getGold());

        app.getInputManager().setCursorVisible(false);
    }

    protected void showBag(){
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
        styles.getSelector("header", "glass").set("shadowColor", new ColorRGBA(1, 0f, 0f, 1));
        app.getGuiNode().attachChild(generalBorder);

        leftPart = new Container("glass");
        SpringGridLayout leftLayOut = new SpringGridLayout(Axis.Y, Axis.X, FillMode.None, FillMode.None);
        leftPart.setLayout(leftLayOut);
        //Panel leftPart = new Panel("glass");
        //leftPart.setBackground(new QuadBackgroundComponent(new ColorRGBA(0, 0.5f, 0.5f, 0.5f), 5, 5, 0.02f, false));
        leftPart.addChild(new Label("Bag", new ElementId("header"), "glass"), 0, 0);
        leftPart.addChild(new Panel(2, 2, ColorRGBA.Cyan, "glass"), 1, 0).setUserData(LayerComparator.LAYER, 2);

        Button cards = new Button("Cards");
        Button equipments = new Button("Equipments");
        leftPart.addChild(cards, 2, 0);
        leftPart.addChild(equipments, 3, 0);
        leftPartContainer = new Container("glass");
        leftPartContainer.addChild(leftPart);
        generalBorder.addChild(leftPartContainer, Position.West);
        //cards.addClickCommands();

        centralPart = new Container("glass");
        generalBorder.addChild(centralPart, Position.Center);
        centralPart.setLocalTranslation(200, app.getCamera().getHeight() - 50, 0);
 
        buttomPartContainer = new Container("glass");
        generalBorder.addChild(buttomPartContainer, Position.South);
        //尝试在界面中添加主角有点失败，稍后解决
        //Spatial mainCharacter = app.getAssetManager().loadModel("/LeadingActor/MajorActor4.j3o");
        //app.getGuiNode().attachChild(mainCharacter);
        cards.addClickCommands(new CardsDirectoryClick());        
        generalBorder.setAlpha(2f);
    }

    public void showCardsType(){
        leftPart.setBackground(new QuadBackgroundComponent(new ColorRGBA(0, 0.5f, 0.5f, 0.5f), 5, 5, 0.02f, false));
        leftPart.addChild(new Label("Bag", new ElementId("header"), "glass"));
        leftPart.addChild(new Panel(2, 2, ColorRGBA.Cyan, "glass")).setUserData(LayerComparator.LAYER, 2);

        Button general = new Button("General");
        Button saber = new Button("Saber");
        Button neutral = new Button("Neutral");
        Button backToStart = new Button("Back");
        //Button saber = new Button("Saber");

        leftPart.addChild(saber);
        leftPart.addChild(neutral);
        leftPart.addChild(backToStart);
        //leftPart.addChild(saber);

        saber.addClickCommands(new ShowSaber());
        neutral.addClickCommands(new ShowNeutral());
        general.addClickCommands(new showGeneral());
        backToStart.addClickCommands(new BackToStart());
    }

    public void showMoney(){
        Container characterInfo = new Container();
        SpringGridLayout northLayOut = new SpringGridLayout(Axis.X, Axis.Y, FillMode.None, FillMode.None);
        characterInfo.setLayout(northLayOut);
        
        progressBar = new ProgressBar("glass");
        progressBar.getLabel().setText("Health");
        progressBar.setProgressPercent(100);
        characterInfo.addChild(progressBar, 0);
        
        moneyLabel = characterInfo.addChild(new Label("Money:"), 2);
        moneyLabel.setText("Money: "+totalMoney.getMoney());
        moneyLabel.setSize(new Vector3f(60, 20, 0));
        generalBorder.addChild(characterInfo, Position.North);
    }

    public void detachBag(){
        leftPart.detachAllChildren();        
    }

    public void showCards(Container centralPart){
        cardUIs = cardArrayReader.CardArrayToCardUIs();
        System.arraycopy(cardUIs, 0, cardUIsCopy, 0, cardUIs.length);

        for(int i=0; i<mainRoleCards.size(); i++){
            if(mainRoleCards.get(i).getOccupation() == OCCUPATION.SABER){
                appendToSaberCardUIs(cardUIs[i]);
            }else{
                appendToNeutralCardUIs(cardUIs[i]);
            }
        }
        
        pagesContainer = new Container();
        pagesContainer.setLayout(new SpringGridLayout(Axis.X, Axis.Y, FillMode.None, FillMode.None));
        buttomPartContainer.setLayout(new SpringGridLayout(Axis.Y, Axis.Y));
        buttomPartContainer.addChild(pagesContainer);

        int pageNumber = (getCardUIsLength() - getCardUIsLength() % 12) / 12 + 1;
        for(int i=1; i<=pageNumber; i++){
            Button button = pagesContainer.addChild(new Button(String.valueOf(i)));
            button.addClickCommands(new PageButtonClick());
        }
        

        for(CardUI cardUI: cardUIs){
            cardUI.addAction(new CardsButtonClick());
        }

        for(int i=0; i<12; i++){
            int j = i % 4;
            int z = (i - j)/4;
            cardUIs[i].addButtonToContainer(centralPart, 2*z, j);
                //cardUI.getCheckBox().setTextHAlignment(HAlignment.Center);
            VersionedReference<Boolean> reference = cardUIs[i].getCheckBox().getModel().createReference();
            cardsReference[i] = reference;
        }
    }

    public int getCardUIsLength(){
        int length = 0;
        for(int i=0; i<cardUIs.length; i++){
            if(cardUIs[i] == null){
                length = i;
                break;
            }
        }
        return length;
    }

    private class showGeneral implements Command<Button>{
        public void execute(Button button){
            centralPart.detachAllChildren();
            pagesContainer.detachAllChildren();
            int pageNumber = (getCardUIsLength() - getCardUIsLength() % 12) / 12 + 1;

            for(int i=0; i<12; i++){
                int j = i % 4;
                int z = (i - j) / 4;
                cardUIs[i].addButtonToContainer(centralPart, 2*z, j);
            }

            for(int i=1; i<pageNumber; i++){
                Button pageButton = pagesContainer.addChild(new Button(String.valueOf(i)));
                pageButton.addClickCommands(new PageButtonClick());
            }
        }
    } 
    
    private class ShowSaber implements Command<Button>{
        public void execute(Button button){
            if(saberCardUIs[0] == null){
                Label infoLabel = new Label("You have no saber cards!");
                infoLabel.setFontSize(20f);
                infoLabel.setAlpha(2f);
                centralPart.detachAllChildren();
                if(pagesContainer != null){
                    pagesContainer.detachAllChildren();
                }
                centralPart.addChild(infoLabel);
            }else{
                centralPart.detachAllChildren();
                if(pagesContainer != null){
                    pagesContainer.detachAllChildren();
                }
                int pageNumber = (getCardUIsLength() - getCardUIsLength() % 12) / 12 + 1;

                for(int i=0; i<12; i++){
                    int j = i % 4;
                    int z = (i - j)/4;
                    cardUIs[i].addButtonToContainer(centralPart, 2*z, j);
                }

                for(int i=1; i<=pageNumber; i++){
                    Button pageButton = pagesContainer.addChild(new Button(String.valueOf(i)));
                    pageButton.addClickCommands(new PageButtonClick());
                }
            }
        }
    }

    private class ShowNeutral implements Command<Button>{
        public void execute(Button button){
            if(neutralCardUIs[0] == null){
                Label infoLabel = new Label("You have no neutral cards!");
                infoLabel.setAlpha(2f);
                infoLabel.setFontSize(20f);
                centralPart.detachAllChildren();
                pagesContainer.detachAllChildren();
                centralPart.addChild(infoLabel);
            }else{
                centralPart.detachAllChildren();
                pagesContainer.detachAllChildren();
                int pageNumber = (getCardUIsLength() - getCardUIsLength() % 12) / 12 + 1;

                for(int i=0; i<12; i++){
                    int j = i % 4;
                    int z = (i - j) / 4;
                    cardUIs[i].addButtonToContainer(centralPart, 2*z, j);
                }

                for(int i=1; i<=pageNumber; i++){
                    Button pageButton = pagesContainer.addChild(new Button(String.valueOf(i)));
                    pageButton.addClickCommands(new PageButtonClick());
                }
            }
        }
    }

    private class BackToStart implements Command<Button>{
        public void execute(Button button){
            pagesContainer.detachAllChildren();
            leftPart.detachAllChildren();
            SpringGridLayout leftGridLayout = new SpringGridLayout(Axis.Y, Axis.X, FillMode.None, FillMode.None);
            leftPart.setLayout(leftGridLayout);
        
            leftPart.setBackground(new QuadBackgroundComponent(new ColorRGBA(0, 0.5f, 0.5f, 0.5f), 5, 5, 0.02f, false));
            leftPart.addChild(new Label("Bag", new ElementId("header"), "glass"), 0, 0);
            leftPart.addChild(new Panel(2, 2, ColorRGBA.Cyan, "glass"), 1, 0).setUserData(LayerComparator.LAYER, 2);
        
            Button cards = new Button("Cards");
            Button equipments = new Button("Equipments");
            leftPart.addChild(cards, 2, 0);
            leftPart.addChild(equipments, 3, 0);

            cards.addClickCommands(new CardsDirectoryClick());
        }
    }

    private class PageButtonClick implements Command<Button>{
        public void execute(Button button){
            int pageIndex = Integer.parseInt(button.getText());

            centralPart.detachAllChildren();
            if(cardUIs.length < 12 * pageIndex){
                for(int i = 12*(pageIndex - 1); i<cardUIs.length; i++){
                    int index = i - 12*(pageIndex-1);
                    int j = index % 4;
                    int z = (index - j)/4;
                    cardUIs[i].addButtonToContainer(centralPart, 2*z, j);
                }
            }else{
                for(int i = 12*(pageIndex - 1); i<12*pageIndex; i++){
                    int index = i - 12*(pageIndex - 1);
                    int j = index % 4;
                    int z = (index - j)/4;
                    cardUIs[i].addButtonToContainer(centralPart, 2*z, j);
                }
            }
        }
    }

    public void refreshPage(CardUI[] cardUIs){
        buttomPartContainer.detachAllChildren();
    }

    @Override
    protected void cleanup(Application application){
        app.getGuiNode().detachChild(generalBorder);
        app.getFlyByCamera().setDragToRotate(false);
    }

    @Override
    protected void onEnable(){
    }

    @Override
    protected void onDisable(){
        app.getGuiNode().removeFromParent();
    }
    
    class BagListener implements ActionListener{
        @Override
        public void onAction(String name, boolean isPressed, float tpf){
            if(bagString.equals(name) && isPressed && !isOpenBag){
                showMoney();
                showBag();
                isOpenBag = true;
            }else if(bagString.equals(name) && isPressed && isOpenBag){
                isOpenBag = false;
                cleanup();
            }
        }
    }

    private class CardsDirectoryClick implements Command<Button>{
        public void execute(Button button){
            if(button.isPressed()){
                detachBag();
                showCards(centralPart);
                showCardsType();
                isShowCards = true;
            }
        }
    }

    private class CardsButtonClick implements Command<Button>{
        public void execute(Button button){
            if(button.isPressed()){
                for(CardUI cardUI : cardUIs){
                    if(cardUI.equals(button)){
                        TabTextForBag textForBag = new TabTextForBag.Builder(cardUI, "Info", "Name", "Cost")
                            .setMessage1(cardUI.getDescription()).setMessgae2(cardUI.getButton().getName())
                            .setMessage3(Integer.toString(cardUI.getCardMoney())).build();
                        getStateManager().attach(textForBag);
                        break;
                    }
                }
            }
        }
    }

    public void appendToSaberCardUIs(CardUI cardUI){
        for(int i=0; i<saberCardUIs.length; i++){
            if(saberCardUIs[i] == null){
                saberCardUIs[i] = cardUI;
                break;
            }
        }
    }

    public void appendToNeutralCardUIs(CardUI cardUI){
        for(int i=0; i< neutralCardUIs.length; i++){
            if(neutralCardUIs[i] == null){
                neutralCardUIs[i] = cardUI;
                break;
            }
        }
    }

    public void onFight(){
        cleanup();
        app.getInputManager().deleteMapping(bagString);
    }

    public void exitFight(){
        app.getInputManager().addMapping(bagString, new KeyTrigger(KeyInput.KEY_B));
        app.getInputManager().addListener(new BagListener(), bagString);
    }
    
    public void update(float tpf){
        if(isOpenBag){
            totalMoney.setMoney(MainRole.getInstance().getGold());
            moneyLabel.setText("Money: " + totalMoney.getMoney());
            progressBar.setProgressPercent(MainRole.getInstance().getHP() / MainRole.getInstance().getTotalHP());

            cardUIs = cardArrayReader.CardArrayToCardUIs();
            System.arraycopy(cardUIs, 0, cardUIsCopy, 0, getCardUIsLength());
        }
    }
}