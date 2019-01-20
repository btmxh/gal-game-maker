/*
 * bla bla
 */
package com.duyanh.game.languageparser.conversation;

import com.duyanh.game.languageparser.variables.NumericVariable;
import com.duyanh.game.languageparser.variables.PersonVariable;
import com.duyanh.game.states.GameState;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Welcome
 */
public class Question extends GameCommand{
    public PersonVariable asker;
    public String question;
    public List<Choice> choices;
    public NumericVariable choice = new NumericVariable(-20);
    public Input input;

    public Question(PersonVariable asker, String question) {
        this.asker = asker;
        this.question = question;
        choices = new ArrayList<>();
    }

    @Override
    public String toString() {
        if(!choices.isEmpty()){
            return asker + ": "+question+"\n" + choices + "\n";
        } else {
            return asker + ": "+question+"\n" + input + "\n"; 
        }
    }

    @Override
    public void invoke(GameState gameState) {
        if(choices.isEmpty())
            gameState.inputQuestion(this);
        else gameState.choiceQuestion(this);
        
    }
    
    
}
