/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package res;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Welcome
 */
public class ImageLoader {
    public static BufferedImage loadImg(String name){
        try {
            return ImageIO.read(ImageLoader.class.getResourceAsStream(name));
        } catch (IOException ex) {
            System.err.println("FAILED");
            System.exit(100);
        }
        return null;
    }
}
