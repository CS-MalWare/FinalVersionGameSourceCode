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
import gamesource.battleState.card.Card.RARITY;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.equipment.Equipment;
import gamesource.battleState.equipment.Equipment.EquipmentDegree;
import gamesource.uiState.shopstate.EquipmentUI;
import gamesource.util.*;

public class BagAppState extends BaseAppState{
    private SimpleApplication app;
    private InputManager inputManager;
    private final static String bagString = "Bag";
    private VersionedReference[] cardsReference = new VersionedReference[100];
    private ArrayList<Card> mainRoleCards = MainRole.getInstance().getDeck_();
    private ArrayList<Equipment> mainRoleEquipments = MainRole.getInstance().getEquipments();

    private CardUI[] cardUIs = new CardUI[60];                          //use to initialize cards
    private CardUI[] cardUIsCopy = new CardUI[60];
    private CardUI[] saberCardUIs = new CardUI[30];
    private CardUI[] neutralCardUIs = new CardUI[30];
    
    private EquipmentUI[] equipmentUIs = new EquipmentUI[80];           //use to initialize equipments
    private EquipmentUI[] rareEquipmentUIs = new EquipmentUI[20];
    private EquipmentUI[] commonEquipmentUIs = new EquipmentUI[20];
    private EquipmentUI[] epicEquipmentUIs = new EquipmentUI[20];
    private EquipmentUI[] legeEquipmentUIs = new EquipmentUI[20];

    private Container generalBorder;                                    //basic components
    private Container centralPart;
    private Container buttomPartContainer;
    private Container pagesContainer;
    private Container leftPart;
    private ProgressBar progressBar;
    private Label moneyLabel;

    private BagMoney totalMoney = new BagMoney();                       //bag money initialize
    private Styles styles;
    private boolean isOpenBag = false;
    private boolean isShowCards = false;

    private boolean isFirstOpen = true;

    @Override
    public void initialize(Application application){
        app = (SimpleApplication) application;
        inputManager = app.getInputManager();
        
        inputManager.addMapping(bagString, new KeyTrigger(KeyInput.KEY_B));             //key mapping
        inputManager.addListener(new BagListener(), bagString);

        GuiGlobals.initialize(app);                                     //GUI initialize
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
        app.getFlyByCamera().setDragToRotate(true);                         //set mouse event and initialize style
        styles = GuiGlobals.getInstance().getStyles();
        styles.setDefaultStyle("glass");
        
        styles.getSelector(Panel.ELEMENT_ID, "glass").set("background",             //set basic styles
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

        Button cards = new Button("Cards");                             //give two buttons
        Button equipments = new Button("Equipments");
        leftPart.addChild(cards, 2, 0);
        leftPart.addChild(equipments, 3, 0);
        //leftPartContainer = new Container("glass");
        //leftPartContainer.addChild(leftPart);
        generalBorder.addChild(leftPart, Position.West);
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
        equipments.addClickCommands(new EquipmentClick());     
        generalBorder.setAlpha(2f);
    }

    public void showCardsType(){                                                  //show cards type throught several buttons
        leftPart.setBackground(new QuadBackgroundComponent(new ColorRGBA(0, 0.5f, 0.5f, 0.5f), 5, 5, 0.02f, false));
        leftPart.addChild(new Label("Bag", new ElementId("header"), "glass"));
        leftPart.addChild(new Panel(2, 2, ColorRGBA.Cyan, "glass")).setUserData(LayerComparator.LAYER, 2);

        Button general = new Button("General");                     //按键分类
        Button saber = new Button("Saber");
        Button neutral = new Button("Neutral");
        Button backToStart = new Button("Back");
        //Button saber = new Button("Saber");

        leftPart.addChild(saber);                       //加入按键
        leftPart.addChild(neutral);
        leftPart.addChild(backToStart);
        //leftPart.addChild(saber);

        saber.addClickCommands(new ShowSaber());        //按键触发事件
        neutral.addClickCommands(new ShowNeutral());
        general.addClickCommands(new showGeneral());
        backToStart.addClickCommands(new BackToStart());
    }

    public void showMoney(){                            //在背包中初始化金钱
        Container characterInfo = new Container();
        SpringGridLayout northLayOut = new SpringGridLayout(Axis.X, Axis.Y, FillMode.None, FillMode.None);
        characterInfo.setLayout(northLayOut);
        
        progressBar = new ProgressBar("glass");         //初始化血量
        progressBar.getLabel().setText("Health");
        progressBar.setProgressPercent(100);
        progressBar.getLabel().setColor(new ColorRGBA(1, 0, 0, 1));
        characterInfo.addChild(progressBar, 0);
        
        moneyLabel = characterInfo.addChild(new Label("Money:"), 2);    //显示金钱
        moneyLabel.setText("Money: "+totalMoney.getMoney());
        moneyLabel.setSize(new Vector3f(60, 20, 0));
        generalBorder.addChild(characterInfo, Position.North);
    }

    public void detachBag(){                            //clean cards type buttons
        leftPart.detachAllChildren();        
    }

    public void showCards(Container centralPart){       //show general cards 
        cardUIs = CardArrayReader.cardArrayToCardUIs(MainRole.getInstance().getDeck_());
        System.arraycopy(cardUIs, 0, cardUIsCopy, 0, cardUIs.length);
        mainRoleCards = MainRole.getInstance().getDeck_();

        cleanArray(neutralCardUIs);                     //clean cardUIs array
        cleanArray(saberCardUIs);

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

        int pageNumber = (cardUIs.length - cardUIs.length % 12) / 12 + 1;           //initialize pageButtons
        for(int i=1; i<=pageNumber; i++){
            Button button = pagesContainer.addChild(new Button(String.valueOf(i)));
            button.addClickCommands(new PageButtonClick(null));
        }
        

        for(CardUI cardUI: cardUIs){                                    //give every cards' events
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

    public void showEquipments(Container centralPart){              //similiar functions as showCards
        equipmentUIs = EquipmentArrayReader.toEquipmentUIs(MainRole.getInstance().getEquipments());
        mainRoleEquipments = MainRole.getInstance().getEquipments();
        
        cleanArray(commonEquipmentUIs);
        cleanArray(rareEquipmentUIs);
        cleanArray(epicEquipmentUIs);
        cleanArray(legeEquipmentUIs);

        for(int i=0; i<mainRoleEquipments.size(); i++){
            if(mainRoleEquipments.get(i).getDegree() == EquipmentDegree.COMMON){
                appendToCommonEquipmentUIs(equipmentUIs[i]);
            }else if(mainRoleEquipments.get(i).getDegree() == EquipmentDegree.EPIC){
                appendToEpicEquipmentUIs(equipmentUIs[i]);
            }else if(mainRoleEquipments.get(i).getDegree() == EquipmentDegree.LEGENDARY){
                appendToLegeEquipmentUIs(equipmentUIs[i]);
            }else if(mainRoleEquipments.get(i).getDegree() == EquipmentDegree.RARE){
                appendToRareEquipmentUIs(equipmentUIs[i]);
            }
        }

        pagesContainer = new Container();
        pagesContainer.setLayout(new SpringGridLayout(Axis.X, Axis.Y, FillMode.None, FillMode.None));
        buttomPartContainer.setLayout(new SpringGridLayout(Axis.Y, Axis.Y));
        buttomPartContainer.addChild(pagesContainer);
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

    public int getArrayLength(CardUI[] cUis){
        int length = 0;
        for(int i=0; i< cUis.length; i++){
            if(cUis[i] == null){
                length = i;
                break;
            }
        }
        return length;
    }

    public int getArrayLength(EquipmentUI[] eUis){
        int length = 0;
        for(int i=0; i< eUis.length; i++){
            if(eUis[i] == null){
                length = i;
                break;
            }
        }
        return length;
    }

    public int getEquipmentUIsLength(){
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
                pageButton.addClickCommands(new PageButtonClick(null));
            }
        }
    } 
    
    private class ShowSaber implements Command<Button>{                     //show saber type cards
        public void execute(Button button){
            int saberLength = getArrayLength(saberCardUIs);
            if(saberCardUIs[0] == null){
                Label infoLabel = new Label("You have no saber cards!");
                infoLabel.setFontSize(20f);
                infoLabel.setAlpha(2f);
                centralPart.detachAllChildren();
                pagesContainer.detachAllChildren();
                
                centralPart.addChild(infoLabel);
            }else{
                centralPart.detachAllChildren();
                pagesContainer.detachAllChildren();
                int pageNumber = (saberLength - saberLength % 12) / 12 + 1;

                if(saberLength < 12){
                    for(int i=0; i<saberLength; i++){
                        int j = i % 4;
                        int z = (i - j) / 4;
                        saberCardUIs[i].addButtonToContainer(centralPart, 2*z, j);
                    }
                }else{
                    for(int i=0; i<12; i++){
                        int j = i % 4;
                        int z = (i - j)/4;
                        saberCardUIs[i].addButtonToContainer(centralPart, 2*z, j);
                    }

                    for(int i=1; i<=pageNumber; i++){
                        Button pageButton = pagesContainer.addChild(new Button(String.valueOf(i)));
                        pageButton.addClickCommands(new PageButtonClick(OCCUPATION.SABER));
                    }
                }
            }
        }
    }

    private class ShowNeutral implements Command<Button>{                   //show neutral type cards
        public void execute(Button button){
            int neutralLength = getArrayLength(neutralCardUIs);
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
                int pageNumber = (neutralLength - neutralLength % 12) / 12 + 1;

                if(neutralLength < 12){
                    for(int i=0; i<neutralLength; i++){
                        int j = i % 4;
                        int z = (i - j) / 4;
                        neutralCardUIs[i].addButtonToContainer(centralPart, 2*z, j);
                    }
                }else{
                    for(int i=0; i<12; i++){
                        int j = i % 4;
                        int z = (i - j) / 4;
                        neutralCardUIs[i].addButtonToContainer(centralPart, 2*z, j);
                    }

                    for(int i=1; i<=pageNumber; i++){
                        Button pageButton = pagesContainer.addChild(new Button(String.valueOf(i)));
                        pageButton.addClickCommands(new PageButtonClick(OCCUPATION.NEUTRAL));
                    }
                }
            }
        }
    }

    private class ShowCommon implements Command<Button>{                           //show common type equipments
        public void execute(Button button){
            int commonLength = getArrayLength(commonEquipmentUIs);
            if(commonEquipmentUIs[0] == null){
                Label infoLabel = new Label("You have no common equipments!");
                infoLabel.setAlpha(2f);
                infoLabel.setFontSize(20f);
                centralPart.detachAllChildren();
                pagesContainer.detachAllChildren();
                centralPart.addChild(infoLabel);
            }else{
                centralPart.detachAllChildren();
                pagesContainer.detachAllChildren();

                int pageNumber = (commonLength - commonLength % 12) / 12 + 1;

                if(commonLength < 12){
                    for(int i=0; i<commonLength; i++){
                        int j = i % 4;
                        int z = (i - j) / 4;
                        commonEquipmentUIs[i].addToButtonContainer(centralPart, 2*z, j);
                    }
                }else{
                    for(int i=0; i<12; i++){
                        int j = i % 4;
                        int z = (i - j) / 4;
                        commonEquipmentUIs[i].addToButtonContainer(centralPart, 2*z, j);
                    }

                    for(int i=1; i<=pageNumber; i++){
                        Button pageButton = pagesContainer.addChild(new Button(String.valueOf(i)));
                        pageButton.addClickCommands(new PageButtonClickForEquip(EquipmentDegree.COMMON));
                    }
                }
            }
        }
    }

    private class ShowEpic implements Command<Button>{                          //show epic type cards
        public void execute(Button button){
            int epicLength = getArrayLength(epicEquipmentUIs);
            if(epicEquipmentUIs[0] == null){
                Label infoLabel = new Label("You have no epic equipments!");
                infoLabel.setAlpha(2f);
                infoLabel.setFontSize(20f);
                centralPart.detachAllChildren();
                pagesContainer.detachAllChildren();
                centralPart.addChild(infoLabel);
            }else{
                centralPart.detachAllChildren();
                pagesContainer.detachAllChildren();

                int pageNumber = (epicLength - epicLength % 12) / 12 + 1;

                if(epicLength < 12){
                    for(int i=0; i<epicLength; i++){
                        int j = i % 4;
                        int z = (i - j) / 4;
                        epicEquipmentUIs[i].addToButtonContainer(centralPart, 2*z, j);
                    }
                }else{
                    for(int i=0; i<12; i++){
                        int j = i % 4;
                        int z = (i - j) / 4;
                        epicEquipmentUIs[i].addToButtonContainer(centralPart, 2*z, j);
                    }

                    for(int i=1; i<=pageNumber; i++){
                        Button pageButton = pagesContainer.addChild(new Button(String.valueOf(i)));
                        pageButton.addClickCommands(new PageButtonClickForEquip(EquipmentDegree.EPIC));
                    }
                }
            }
        }
    }

    private class ShowRare implements Command<Button>{                          //show rare type equipments
        public void execute(Button button){
            int rareLength = getArrayLength(rareEquipmentUIs);
            if(rareEquipmentUIs[0] == null){
                Label infoLabel = new Label("You have no rare equipments!");
                infoLabel.setAlpha(2f);
                infoLabel.setFontSize(20f);
                centralPart.detachAllChildren();
                pagesContainer.detachAllChildren();
                centralPart.addChild(infoLabel);
            }else{
                centralPart.detachAllChildren();
                pagesContainer.detachAllChildren();

                int pageNumber = (rareLength - rareLength % 12) / 12 + 1;

                if(rareLength < 12){
                    for(int i=0; i<rareLength; i++){
                        int j = i % 4;
                        int z = (i - j) / 4;
                        rareEquipmentUIs[i].addToButtonContainer(centralPart, 2*z, j);
                    }
                }else{
                    for(int i=0; i<12; i++){
                        int j = i % 4;
                        int z = (i - j) / 4;
                        rareEquipmentUIs[i].addToButtonContainer(centralPart, 2*z, j);
                    }

                    for(int i=1; i<=pageNumber; i++){
                        Button pageButton = pagesContainer.addChild(new Button(String.valueOf(i)));
                        pageButton.addClickCommands(new PageButtonClickForEquip(EquipmentDegree.RARE));
                    }
                }
            }
        }
    }

    private class ShowLege implements Command<Button>{                      //show legendary equipments
        public void execute(Button button){
            int legeLength = getArrayLength(legeEquipmentUIs);
            if(epicEquipmentUIs[0] == null){
                Label infoLabel = new Label("You have no legendary equipments!");
                infoLabel.setAlpha(2f);
                infoLabel.setFontSize(20f);
                centralPart.detachAllChildren();
                pagesContainer.detachAllChildren();
                centralPart.addChild(infoLabel);
            }else{
                centralPart.detachAllChildren();
                pagesContainer.detachAllChildren();

                int pageNumber = (legeLength - legeLength % 12) / 12 + 1;

                if(legeLength < 12){
                    for(int i=0; i<legeLength; i++){
                        int j = i % 4;
                        int z = (i - j) / 4;
                        legeEquipmentUIs[i].addToButtonContainer(centralPart, 2*z, j);
                    }
                }else{
                    for(int i=0; i<12; i++){
                        int j = i % 4;
                        int z = (i - j) / 4;
                        legeEquipmentUIs[i].addToButtonContainer(centralPart, 2*z, j);
                    }

                    for(int i=1; i<=pageNumber; i++){
                        Button pageButton = pagesContainer.addChild(new Button(String.valueOf(i)));
                        pageButton.addClickCommands(new PageButtonClickForEquip(EquipmentDegree.LEGENDARY));
                    }
                }
            }
        }
    }

    private class BackToStart implements Command<Button>{                   ////use to back to cards and equipments choosen
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
            equipments.addClickCommands(new EquipmentClick());
        }
    }

    private class PageButtonClick implements Command<Button>{                   //add pageButton events
        private OCCUPATION type;

        public PageButtonClick(OCCUPATION type){
            this.type = type;
        }

        public void execute(Button button){
            int pageIndex = Integer.parseInt(button.getText());

            centralPart.detachAllChildren();
            if(cardUIs.length < 12 * pageIndex){
                for(int i = 12*(pageIndex - 1); i<cardUIs.length; i++){
                    int index = i - 12*(pageIndex-1);
                    int j = index % 4;
                    int z = (index - j)/4;
                    if(type == OCCUPATION.NEUTRAL){
                        neutralCardUIs[i].addButtonToContainer(centralPart, 2*z, j);
                    }else if(type == OCCUPATION.SABER){
                        saberCardUIs[i].addButtonToContainer(centralPart, 2*z, j);
                    }else{
                        cardUIs[i].addButtonToContainer(centralPart, 2*z, j);
                    }
                }
            }else{
                for(int i = 12*(pageIndex - 1); i<12*pageIndex; i++){
                    int index = i - 12*(pageIndex - 1);
                    int j = index % 4;
                    int z = (index - j)/4;
                    if(type == OCCUPATION.NEUTRAL){
                        neutralCardUIs[i].addButtonToContainer(centralPart, 2*z, j);
                    }else if(type == OCCUPATION.SABER){
                        saberCardUIs[i].addButtonToContainer(centralPart, 2*z, j);
                    }else{
                        cardUIs[i].addButtonToContainer(centralPart, 2*z, j);
                    }
                }
            }
        }
    }

    private class EquipmentClick implements Command<Button>{            //Equipments events
        public void execute(Button button){
            detachBag();
            leftPart.setBackground(new QuadBackgroundComponent(new ColorRGBA(0, 0.5f, 0.5f, 0.5f), 5, 5, 0.02f, false));
            leftPart.addChild(new Label("Bag", new ElementId("header"), "glass"));
            leftPart.addChild(new Panel(2, 2, ColorRGBA.Cyan, "glass")).setUserData(LayerComparator.LAYER, 2);
            
            Button common = new Button("Common");
            Button epic = new Button("Epic");
            Button lege = new Button("Legendary");
            Button rare = new Button("Rare");
            Button backToStart = new Button("Back");

            leftPart.addChild(common);
            leftPart.addChild(epic);
            leftPart.addChild(rare);
            leftPart.addChild(lege);
            leftPart.addChild(backToStart);

            common.addClickCommands(new ShowCommon());
            epic.addClickCommands(new ShowEpic());
            rare.addClickCommands(new ShowRare());
            lege.addClickCommands(new ShowLege());
            backToStart.addClickCommands(new BackToStart());

            showEquipments(centralPart);
        }
    }

    private class PageButtonClickForEquip implements Command<Button>{       //page buttons for equipments
        private EquipmentDegree type;
        public PageButtonClickForEquip(EquipmentDegree type){
            this.type = type;
        }
        
        public void execute(Button button){
            int pageIndex = Integer.parseInt(button.getText());

            centralPart.detachAllChildren();
            if(getEquipmentUIsLength() <= 12*pageIndex){
                for(int i = 12*(pageIndex -1); i<getEquipmentUIsLength(); i++){
                    int index = i-12*(pageIndex-1);
                    int j = index % 4;
                    int z = (index - j)/4;
                    if(type == EquipmentDegree.COMMON){
                        commonEquipmentUIs[i].addToButtonContainer(centralPart, 2*z, j);
                    }else if(type == EquipmentDegree.EPIC){
                        epicEquipmentUIs[i].addToButtonContainer(centralPart, 2*z, j);
                    }else if(type == EquipmentDegree.RARE){
                        rareEquipmentUIs[i].addToButtonContainer(centralPart, 2*z, j);
                    }else if(type == EquipmentDegree.LEGENDARY){
                        legeEquipmentUIs[i].addToButtonContainer(centralPart, 2*z, j);
                    }
                }
            }else{
                for(int i = 12*(pageIndex - 1); i<12*pageIndex; i++){
                    int index = i - 12*(pageIndex - 1);
                    int j = index % 4;
                    int z = (index - j)/4;
                    if(type == EquipmentDegree.COMMON){
                        commonEquipmentUIs[i].addToButtonContainer(centralPart, 2*z, j);
                    }else if(type == EquipmentDegree.EPIC){
                        epicEquipmentUIs[i].addToButtonContainer(centralPart, 2*z, j);
                    }else if(type == EquipmentDegree.RARE){
                        rareEquipmentUIs[i].addToButtonContainer(centralPart, 2*z, j);
                    }else if(type == EquipmentDegree.LEGENDARY){
                        legeEquipmentUIs[i].addToButtonContainer(centralPart, 2*z, j);
                    }
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
    
    class BagListener implements ActionListener{                        //bag show events listener
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

    private class CardsDirectoryClick implements Command<Button>{           //show cards directory
        public void execute(Button button){
            if(button.isPressed()){
                detachBag();
                showCards(centralPart);
                showCardsType();
                isShowCards = true;
            }
        }
    }

    private class CardsButtonClick implements Command<Button>{          //cards Button events
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

    public void appendToRareEquipmentUIs(EquipmentUI equipmentUI){
        for(int i=0; i<rareEquipmentUIs.length; i++){
            if(rareEquipmentUIs[i] == null){
                rareEquipmentUIs[i] = equipmentUI;
                break;
            }
        }
    }

    public void appendToCommonEquipmentUIs(EquipmentUI equipmentUI){
        for(int i=0; i<commonEquipmentUIs.length; i++){
            if(commonEquipmentUIs[i] == null){
                commonEquipmentUIs[i] = equipmentUI;
                break;
            }
        }
    }

    public void appendToEpicEquipmentUIs(EquipmentUI equipmentUI){
        for(int i=0; i<epicEquipmentUIs.length; i++){
            if(epicEquipmentUIs[i] == null){
                epicEquipmentUIs[i] = equipmentUI;
                break;
            }
        }
    }

    public void appendToLegeEquipmentUIs(EquipmentUI equipmentUI){
        for(int i=0; i < legeEquipmentUIs.length; i++){
            if(legeEquipmentUIs[i] == null){
                legeEquipmentUIs[i] = equipmentUI;
                break;
            }
        }
    }

    public void cleanArray(EquipmentUI[] eUis){
        for(int i=0; i<eUis.length; i++){
            eUis[i] = null;
        }
        eUis = new EquipmentUI[20];
    }

    public void cleanArray(CardUI[] cUis){
        for(int i=0; i<cUis.length; i++){
            cUis[i] = null;
        }
        cUis = new CardUI[30];
    }

    public void onFight(){
        cleanup();
        app.getInputManager().deleteMapping(bagString);
    }

    public Container getWindow(){
        return generalBorder;
    }

    public void exitFight(){
        app.getInputManager().addMapping(bagString, new KeyTrigger(KeyInput.KEY_B));
        app.getInputManager().addListener(new BagListener(), bagString);
    }
    
    public void update(float tpf){                                  //update money and health situation
        if(isOpenBag){
            totalMoney.setMoney(MainRole.getInstance().getGold());
            moneyLabel.setText("Money: " + totalMoney.getMoney());
            progressBar.setProgressPercent(MainRole.getInstance().getHP() / MainRole.getInstance().getTotalHP());
        }
    }
}