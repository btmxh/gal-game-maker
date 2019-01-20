/*
 * bla bla
 */
package com.duyanh.game.languageparser.conversation;

import com.duyanh.game.languageparser.variables.StringVariable;
import com.duyanh.game.languageparser.variables.Variable;
import com.duyanh.game.states.GameState;

/**
 *
 * @author Welcome
 */
public class Input extends GameCommand{
    public Variable var;

    public Input(Variable var) {
        this.var = var;
    }

    @Override
    public String toString() {
        return var + " = input();\n";
    }

    @Override
    public void invoke(GameState gameState) {
    }
    
    
}
