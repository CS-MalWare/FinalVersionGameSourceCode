package gamesource.testState;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Node;

public class SceneState extends AbstractAppState{
    private SimpleApplication application;
    private HashMap<String, Node> nodes = new HashMap<>();
    
    public void addNode(String name, Node node){
        nodes.put(name, node);
    }

    public Node getNode(String name){
        return nodes.get(name);
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app){
        super.initialize(stateManager, app);
        this.application = (SimpleApplication) app;

       Collection<Node> collection = nodes.values();
       Iterator<Node> iter = collection.iterator();
       while(iter.hasNext()){
           this.application.getRootNode().attachChild(iter.next());
       }
    }

    @Override
    public void cleanup(){
        super.cleanup();
        this.application.getRootNode().detachAllChildren();
    }

    public void clean(String name){
        this.application.getRootNode().detachChild(nodes.get(name));
    }

    @Override
    public void setEnabled(boolean enabled){
        super.setEnabled(enabled);
        if(enabled){
            Collection<Node> collection = nodes.values();
            Iterator<Node> iterator = collection.iterator();
            while(iterator.hasNext()){
                this.application.getRootNode().attachChild(iterator.next());
            }
        }
    }

    @Override
    public void update(float tpf){

    }
}