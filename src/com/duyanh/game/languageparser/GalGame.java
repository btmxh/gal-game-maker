/*
 * bla bla
 */
package com.duyanh.game.languageparser;

import com.duyanh.game.languageparser.conversation.GameCommand;
import com.duyanh.game.languageparser.resources.ResourceObject;
import com.duyanh.game.languageparser.variables.Variable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Welcome
 */
public class GalGame {
    private Map<String, Variable> variables;
    private Map<String, Double> configs;
    private Map<String, ResourceObject> resources;
    private List<GameCommand> gamePlay;

    public GalGame() {
        variables = new HashMap<>();
        configs = new HashMap<>();
        resources = new HashMap<>();
        gamePlay = new ArrayList<>();
    }

    public Map<String, Double> getConfigs() {
        return configs;
    }

    public Map<String, Variable> getVariables() {
        return variables;
    }

    public Map<String, ResourceObject> getResources() {
        return resources;
    }

    public List<GameCommand> getGamePlay() {
        return gamePlay;
    }

    @Override
    public String toString() {
        return configs + "\n" + variables + "\n" + resources + "\n" + gamePlay;
    }
    
    public double getConfig(String key){
        if(configs.containsKey(key)){
            return configs.get(key);
        } else return defaultConfig(key);
    }

    private double defaultConfig(String key) {
        switch (key){
            case "autoComplete": return -1;
        }
        return -1;
    }

    public void loadResources() throws IOException {
        for (Map.Entry<String, ResourceObject> entry : resources.entrySet()) {
            String resourceName = entry.getKey();
            ResourceObject resource = entry.getValue();
            resource.load();
        }
    }
}

