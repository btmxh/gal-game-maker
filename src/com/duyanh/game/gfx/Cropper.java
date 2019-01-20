/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.duyanh.game.gfx;

import java.awt.image.BufferedImage;

/**
 *
 * @author Welcome
 */
public class Cropper {
    protected int width, height;
    protected BufferedImage spritesheet;
    public Cropper(BufferedImage spritesheet, int width, int height){
        this.height = height;
        this.spritesheet = spritesheet;
        this.width = width;
    }
    
    public BufferedImage crop(int x, int y){
        return spritesheet.getSubimage(x*width, y*width, width, height);
    }
}
