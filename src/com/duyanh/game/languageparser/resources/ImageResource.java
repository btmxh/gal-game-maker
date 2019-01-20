/*
 * bla bla
 */
package com.duyanh.game.languageparser.resources;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import res.ImageLoader;

/**
 *
 * @author Welcome
 */
public class ImageResource extends ResourceObject{
    private String path;
    private BufferedImage value;

    public ImageResource(String path) {
        this.path = path;
    }
    
    public ImageResource(BufferedImage image){
        this.value = image;
    }
    
    @Override
    public void load() throws IOException{
        value = ImageIO.read(new FileInputStream(path));
    }
    
    public BufferedImage get(){
        return value;
    }
    
    
}
