/*
 * bla bla
 */
package com.duyanh.game.languageparser.conversation;

import com.duyanh.game.languageparser.resources.ImagesResource;
import com.duyanh.game.states.GameState;

/**
 *
 * @author Welcome
 */
public class NextImage extends GameCommand{
    public ImagesResource images;

    public NextImage(ImagesResource images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "nextImage();\n";
    }
    
    @Override
    public void invoke(GameState gameState){
        gameState.addImage(images.nextImage());
    }
}
