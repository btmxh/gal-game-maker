/*
 * bla bla
 */
package com.duyanh.game.languageparser.conversation;

import com.duyanh.game.states.GameState;

/**
 *
 * @author Welcome
 */
public abstract class GameCommand {
    
    public abstract void invoke(GameState gameState);
}
