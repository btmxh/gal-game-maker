/*
 * bla bla
 */
package com.duyanh.game.languageparser.conversation;

import com.duyanh.game.languageparser.variables.BooleanVariable;
import com.duyanh.game.states.GameState;
import java.util.List;

/**
 *
 * @author Welcome
 */
public class IfStatement extends GameCommand{
    public BooleanVariable condition;
    public List<GameCommand> commands;

    public IfStatement(BooleanVariable condition, List<GameCommand> commands) {
        this.condition = condition;
        this.commands = commands;
    }

    @Override
    public String toString() {
        return "if("+condition+"){\n"+commands+"}\n";
    }

    @Override
    public void invoke(GameState gameState) {
        System.out.println("com.duyanh.game.languageparser.conversation.IfStatement.invoke()");
        System.out.println(condition);
        if(condition.get()){
            
            commands.forEach((command) -> command.invoke(gameState));
        }
    }
    
    
}
