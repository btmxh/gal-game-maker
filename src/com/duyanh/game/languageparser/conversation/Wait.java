/*
 * bla bla
 */
package com.duyanh.game.languageparser.conversation;

import com.duyanh.game.states.GameState;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.Timer;

/**
 *
 * @author Welcome
 */
public class Wait extends GameCommand{
    public double seconds;
    private long time;

    public Wait(double seconds) {
        this.seconds = seconds;
    }

    @Override
    public String toString() {
        return "waitFor("+seconds+");\n";
    }

    @Override
    public void invoke(GameState gameState) {
        System.out.println("com.duyanh.game.languageparser.conversation.Wait.invoke()");
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                gameState.stopGame();
                while(!gameState.getHandler().getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)&& time != seconds * 1000 * 1000){
                    System.out.print("");
                }
                gameState.continueGame();
            }
        });
        t.start();
    }
    
    
}
