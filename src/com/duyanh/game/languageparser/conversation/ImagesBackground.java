/*
 * bla bla
 */
package com.duyanh.game.languageparser.conversation;

import com.duyanh.game.languageparser.resources.ImageResource;
import com.duyanh.game.languageparser.resources.ImagesResource;
import com.duyanh.game.languageparser.variables.NumericVariable;
import com.duyanh.game.states.GameState;
import java.awt.image.BufferedImage;

/**
 *
 * @author Welcome
 */
public class ImagesBackground extends GameCommand{
    public ImagesResource res;
    public NumericVariable index;
    
    public ImagesBackground(ImagesResource images, int index) {
        this.res = images;
        this.index = new NumericVariable(index);
    }
    
    public ImagesBackground(ImagesResource image, NumericVariable index){
        this.res = image;
        this.index = index;
    }

    @Override
    public String toString() {
        return "background("+res+"["+index+"]);\n";
    }

    @Override
    public void invoke(GameState gameState) {
        gameState.addImage(res.getImage((int) index.number));
    }
    
    
    
    
}
