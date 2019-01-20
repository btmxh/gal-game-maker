/*
 * bla bla
 */
package com.duyanh.components;

import com.duyanh.game.Handler;
import com.duyanh.game.gfx.Assets;
import com.duyanh.game.gfx.Text;
import com.duyanh.game.input.KeyManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

/**
 *
 * @author Welcome
 */
public class GTextField extends GComponent{
    private String value = "";
    private Font font = Assets.font36;

    public GTextField(Handler handler, int x, int y, int w, int h) {
        super(handler, x, y, w, h);
        
    }

    @Override
    public void tick() {
        super.tick();
        KeyManager manager = handler.getKeyManager();
        if(manager.getKeyJustPressed() == -1)
            return;
        if(manager.keyJustPressed(KeyEvent.VK_BACK_SPACE) && value.length() >= 1){
            value = value.substring(0, value.length() - 1);
        }
        String text = KeyEvent.getKeyText(manager.getKeyJustPressed());
        if(text.length() != 1)
            return;
        if(Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK)){
            text = text.toUpperCase();
        } else {
            text = text.toLowerCase();
        }
        value += text;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawRect(x, y, w, h);
        g.setColor(Color.WHITE);
        g.fillRect(x, y, w, h);
        g.setFont(font);
        Text.drawString(g, value, x, y, false, Color.BLACK, font);
    }

    public String getValue() {
        return value;
    }
    
    

    
    
    
}
