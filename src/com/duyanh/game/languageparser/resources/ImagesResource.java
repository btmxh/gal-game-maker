/*
 * bla bla
 */
package com.duyanh.game.languageparser.resources;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import org.apache.commons.io.FilenameUtils;
import res.ImageLoader;

/**
 *
 * @author Welcome
 */
public class ImagesResource extends ResourceObject{
    private String path;
    private String extension;
    private List<BufferedImage> images;
    private int pointer = 1;
    private int listSize = 0;

    public ImagesResource(String path, String extension) {
        this.path = path;
        this.extension = extension;
    }
    
    public BufferedImage nextImage(){
        return images.get(pointer++ - 1);
    }

    @Override
    public String toString() {
        return path; //To change body of generated methods, choose Tools | Templates.
    }
    
    public BufferedImage getImage(int index){
        return images.get(index);
    }

    @Override
    public void load() throws IOException {
        if(extension.equals("AUTO EXTENSION")){
            this.extension = FilenameUtils.getExtension(new File(path).list()[0]);
        }
        System.out.println(path + File.separator + (listSize + 1) + "." + extension);
        File f = null;
        images = new ArrayList<>();
        while ((f = new File(path + File.separator + (listSize + 1) + "." + extension)).exists()){
            images.add(ImageIO.read(f));
            listSize++;
        }
    }
    
    
}
