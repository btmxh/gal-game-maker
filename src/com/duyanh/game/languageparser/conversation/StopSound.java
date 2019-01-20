/*
 * bla bla
 */
package com.duyanh.game.languageparser.conversation;

import com.duyanh.game.languageparser.resources.MusicResource;
import com.duyanh.game.states.GameState;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;

/**
 *
 * @author Welcome
 */
public class StopSound extends GameCommand{
    public MusicResource music;

    public StopSound(MusicResource music) {
        this.music = music;
    }

    @Override
    public String toString() {
        return "playSound("+music+")\n";
    }

    @Override
    public void invoke(GameState gameState) {
        music.stop();
    }
    
    
}
