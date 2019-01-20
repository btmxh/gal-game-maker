/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.duyanh.game;

import com.duyanh.game.input.KeyManager;
import com.duyanh.game.input.MouseManager;
import javax.swing.JFrame;

/**
 *
 * @author Welcome
 */
public class Handler {
    private Game g;

    public Handler(Game g) {
        this.g = g;
    }

    public Game getGame() {
        return g;
    }

    
    public MouseManager getMouseManager(){
        return g.getMouseManager();
    }
    
    public KeyManager getKeyManager(){
        return g.getKeyManager();
    }
    
    public int getWidth(){
        return g.getDisplay().getWidth();
    }
    
    public int getHeight(){
        return g.getDisplay().getHeight();
    }
    

    public float getFPS() {
        return g.getFPS();
    }
    
    public JFrame getFrame(){
        return g.getDisplay().getFrame();
    }
}
