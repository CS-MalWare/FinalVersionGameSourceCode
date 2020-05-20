package gamesource.uiState.shopstate;

import java.util.regex.Pattern;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.simsilica.lemur.ActionButton;
import com.simsilica.lemur.Axis;
import com.simsilica.lemur.CallMethodAction;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.FillMode;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.TextField;
import com.simsilica.lemur.component.SpringGridLayout;
import com.simsilica.lemur.core.VersionedReference;
import com.simsilica.lemur.event.PopupState;
import com.simsilica.lemur.style.ElementId;
import com.simsilica.lemur.text.DocumentModel;
import com.simsilica.lemur.text.DocumentModelFilter;
import com.simsilica.lemur.text.TextFilters;

import gamesource.uiState.bagstate.CardUI;

public class FormattedTextForShop extends BaseAppState{
    private Container window;
    private int numberOfBuy;
    private int cost;
    private Label restNumber;
    private Label totalCost;
    private int totalMoney;
    private CardUI cardUI;
    private CloseCommand closeCommand = new CloseCommand();
    private VersionedReference<DocumentModel> reference;

    public FormattedTextForShop(int number, int cost, int totalMoney, CardUI cardUI){
        numberOfBuy = number;
        this.cost = cost;
        this.totalMoney = totalMoney;
        this.cardUI = cardUI;
    }

    @Override
    protected void initialize(Application application){
        numberOfBuy = 10;
    }

    @Override
    protected void cleanup(Application application){

    }

    @Override
    protected void onEnable(){
        window = new Container();
        window.addChild(new Label("Buy", new ElementId("window.title.label")));

        window.addChild(new Label("Number you want"));
        Container examples = window.addChild(new Container(new SpringGridLayout(Axis.Y, Axis.Y, FillMode.Even, FillMode.Last)));
        
        TextField textField;
        DocumentModelFilter doc;

        doc = new DocumentModelFilter();
        doc.setInputTransform(TextFilters.numeric());
        examples.addChild(new Label("Number Only:"));
        textField = examples.addChild(new TextField(doc), 1);
        textField.setPreferredWidth(300);
        reference = doc.createReference();

        restNumber = window.addChild(new Label("Rest Number: " + numberOfBuy + "/10"));
        totalCost = window.addChild(new Label("Total Cost: " + numberOfBuy*cost));

        window.addChild(new ActionButton(new CallMethodAction(this, "Buy")));
        window.addChild(new ActionButton(new CallMethodAction("Close", window, "removeFromParent")));
        window.setLocalTranslation(700, 600, 100);
        window.setAlpha(10f);
        getState(PopupState.class).showPopup(window, closeCommand);
    }

    @Override
    protected void onDisable(){
        window.removeFromParent();
    }

    private class CloseCommand implements Command<Object>{
        public void execute(Object src){
            getState(ShopAppState.class).getStateManager().detach(FormattedTextForShop.this);
        }
    }

    protected void Buy(){
        String[] costs = totalCost.getText().split(" ");
        getStateManager().attach(new DragCheck(Integer.parseInt(costs[2]), totalMoney, cardUI, numberOfBuy));
    }

    @Override
    public void update(float tpf){
        if(reference.update()){
            if(isInteger(reference.get().getText())){
            numberOfBuy = Integer.parseInt(reference.get().getText());
            restNumber.setText("Rest Number: " + (10 - numberOfBuy) + "/10");
            totalCost.setText("Total Cost: " + numberOfBuy * cost);
            }
        }
    }

    public static boolean isInteger(String string){
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(string).matches();
    }
}