/*
 * bla bla
 */
package com.duyanh.components;

import com.duyanh.game.Handler;
import com.duyanh.game.gfx.Text;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author Welcome
 */
public class GRoundButton extends GButton{

    private int arcHeight;
    private int arcWidth;
    
    public GRoundButton(Handler handler, int x, int y, int w, int h, int arcWidth, int arcHeight) {
        super(handler, x, y, w, h);
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
    }
    
    @Override
    public void render(Graphics g) {
        g.setColor(outlineColor);
        g.drawRoundRect(x, y, w, h, arcWidth, arcHeight);
        if(clicked()){
            ((Graphics2D) g).setPaint(clicked);
        } else if(hovering()){
            ((Graphics2D) g).setPaint(hovering);
        } else {
            ((Graphics2D) g).setPaint(normal);
        }
        g.fillRoundRect(x, y, w, h, arcWidth, arcHeight);
        g.setFont(font);
        Text.drawString(g, text, x + w / 2, y + h / 2, true, stringColor, font);
    }
}
