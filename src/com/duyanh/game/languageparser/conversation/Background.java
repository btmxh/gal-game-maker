/*
 * bla bla
 */
package com.duyanh.game.languageparser.conversation;

import com.duyanh.game.languageparser.resources.ImageResource;
import com.duyanh.game.states.GameState;
import java.awt.image.BufferedImage;

/**
 *
 * @author Welcome
 */
public class Background extends GameCommand{
    public ImageResource res;
    public BufferedImage image;
    
    public Background(ImageResource image) {
        this(image.get());
        this.res = image;
    }
    
    public Background(BufferedImage image){
        this.image = image;
    }

    @Override
    public String toString() {
        return "background("+image+");\n";
    }

    @Override
    public void invoke(GameState gameState) {
        if(res != null)
            image = res.get();
        gameState.addImage(image);
    }
    
    
    
    
}
