/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.duyanh.game.states;

import com.duyanh.game.Handler;
import java.awt.Graphics;

/**
 *
 * @author Welcome
 */
public abstract class State {
    protected Handler handler;
    public State(Handler handler){
        this.handler = handler;
    }

    public Handler getHandler() {
        return handler;
    }
    
    
    
    public abstract void tick();
    public abstract void render(Graphics g);
}
