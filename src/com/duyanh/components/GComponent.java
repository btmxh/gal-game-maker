/*
 * bla bla
 */
package com.duyanh.components;

import com.duyanh.game.Handler;
import java.awt.Graphics;

/**
 *
 * @author Welcome
 */
public class GComponent {
    protected Handler handler;
    protected int x;
    protected int y;
    protected int w;
    protected int h;

    public GComponent(Handler handler, int x, int y, int w, int h) {
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    
    public void tick(){
        
    }
    
    public void render(Graphics g){
        //g.drawRect(x, y, w, h);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return w;
    }

    public void setWidth(int w) {
        this.w = w;
    }

    public int getHeight() {
        return h;
    }

    public void setHeight(int h) {
        this.h = h;
    }
    
    
}
