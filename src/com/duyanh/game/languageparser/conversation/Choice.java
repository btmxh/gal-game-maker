/*
 * bla bla
 */
package com.duyanh.game.languageparser.conversation;

import com.duyanh.game.languageparser.variables.NumericVariable;
import com.duyanh.game.languageparser.variables.StringVariable;
import com.duyanh.game.states.GameState;

/**
 *
 * @author Welcome
 */
public class Choice extends GameCommand{
    public NumericVariable value;
    public StringVariable choiceString;

    public Choice(int value, String choiceString) {
        this.value = new NumericVariable(value);
        this.choiceString = new StringVariable(choiceString);
    }

    @Override
    public String toString() {
        return "Choice "+value+" = "+choiceString+"\n";
    }

    @Override
    public void invoke(GameState gameState) {
    }
    
    
}
