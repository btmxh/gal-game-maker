/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.duyanh.game.gfx;

import com.duyanh.game.Handler;
import res.ImageLoader;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

/**
 *
 * @author Welcome
 */
public class Assets {
    /*
    public static BufferedImage playerDown, playerRight;
    public static BufferedImage air, dirt, dirt_item, stone, stone_item, inventoryScreen, wood, wood_item, sapling, leaves;
    public static BufferedImage[] pickaxes;
    */
    public static HashMap<String, BufferedImage[]> images;
    public static Font font36, font12;
    public static void init(Handler handler){
        images = new HashMap<>();
        /*
        addImage("air", true);
        addImage("dirt", true);
        addImage("dirt_item", true);
        addImage("stone", true);
        addImage("stone_item", true);
        addImage("inventoryScreen", true);
        addImage("wood", true);
        addImage("wood_item", true);
        addImage("sapling", true);
        addImage("leaves", true);
        
        images.put("pickaxe", new BufferedImage[]{ImageLoader.loadImg("wooden_pickaxe.png"),
                                        ImageLoader.loadImg("stone_pickaxe.png"),
                                        ImageLoader.loadImg("iron_pickaxe.png"),
                                        ImageLoader.loadImg("diamond_pickaxe.png")}); 
        */
        File[] files = new File(Assets.class.getResource("/res/").getFile()).listFiles();
        for (File file : files) {
            String name = file.getName();
            if(name.endsWith(".png"))
                addImage(name.substring(0, name.length() - 4));
        }
        font36 = new Font("Segoe UI", Font.PLAIN, 36);
    }
    
    public static void addImage(String name){
        try{
            images.put(name, new BufferedImage[]{ImageLoader.loadImg(name+".png")});
        } catch (Exception ex){
            System.out.println("Cannot found image "+name+".png");
        }
    }
    public static BufferedImage getImage(String name){
        return images.get(name)[0];
    }
    
}
