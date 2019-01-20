/*
 * bla bla
 */
package com.duyanh.game.languageparser.conversation;

import com.duyanh.game.languageparser.GalGame;
import com.duyanh.game.languageparser.Parser;
import com.duyanh.game.languageparser.variables.PersonVariable;
import com.duyanh.game.languageparser.variables.Variable;
import com.duyanh.game.states.GameState;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Welcome
 */
public class Conversation extends GameCommand{
    public PersonVariable speaker;
    public String sentence;

    public Conversation(PersonVariable speaker, String sentence) {
        this.speaker = speaker;
        this.sentence = sentence;
    }
    
    public String replaceVariables(GalGame game){
        int bracketLevel = 0;
        int started = 0;
        String result = sentence;
        String inBracket = "";
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < sentence.length(); i++) {
            char c = sentence.charAt(i);
            if(c == '['){
                bracketLevel++;
                if(bracketLevel == 1)
                    started = i;
                strings.add("");
                inBracket += '[';
                
            } else if(c == ']'){
                bracketLevel--;
                inBracket += ']';
                
                if(bracketLevel == 0){
                    strings.remove(0);
                    Collections.reverse(strings);
                    Variable var = Parser.variable(strings.get(0), game);
                    for (int j = 1; j < strings.size(); j++) {
                        String field = strings.get(j);
                        var = Variable.getVariable(var, field);
                    }
                    System.err.println(inBracket);
                    
                    String temp = result.replace(inBracket, var.toString());
                    result = temp;
                    inBracket = "";
                    strings.clear();
                }
                
            } else if(bracketLevel == 0){
                
            } else if(Character.isWhitespace(c)){
                inBracket += c;
            } else if(strings.size() <= bracketLevel){
                strings.add(c + "");
                inBracket += c;
            } else {
                strings.set(bracketLevel, strings.get(bracketLevel) + c);
                inBracket += c;
            }
            
        }
        return result;
    }

    @Override
    public String toString() {
        return speaker + ": " + sentence + "\n"; 
    }

    @Override
    public void invoke(GameState gameState) {
        sentence = replaceVariables(gameState.getGame());
        gameState.conversation(this);
    }
    
    
}
