/*
 * bla bla
 */
package com.duyanh.components;

import com.duyanh.game.Handler;
import com.duyanh.game.gfx.Assets;
import com.duyanh.game.gfx.Text;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;

/**
 *
 * @author Welcome
 */
public class GButton extends GComponent{
    protected String text = "";
    
    protected Paint normal = new Color(15248669), hovering = new Color(16246672), clicked = new Color(11621634);
    protected Color stringColor = Color.WHITE, outlineColor = Color.BLACK;
    
    protected ActionListener clickedEvent = (e) -> System.out.print("");
    protected Font font = Assets.font36;

    public GButton(Handler handler, int x, int y, int w, int h) {
        super(handler, x, y, w, h);
    }

    @Override
    public void tick() {
        super.tick();
        if(clicked()){
            clickedEvent.actionPerformed(new ActionEvent("", 0, ""));
        }
    }
    
    @Override
    public void render(Graphics g) {
        g.setColor(outlineColor);
        g.drawRect(x, y, w, h);
        if(clicked()){
            ((Graphics2D) g).setPaint(clicked);
        } else if(hovering()){
            ((Graphics2D) g).setPaint(hovering);
        } else {
            ((Graphics2D) g).setPaint(normal);
        }
        g.fillRect(x, y, w, h);
        g.setFont(font);
        Text.drawString(g, text, x + w / 2, y + h / 2, true, stringColor, font);
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public Paint getNormalPaint() {
        return normal;
    }

    public void setNormalPaint(Paint normal) {
        this.normal = normal;
    }

    public Paint getHoveringPaint() {
        return hovering;
    }

    public void setHoveringPaint(Paint hovering) {
        this.hovering = hovering;
    }

    public Paint getClickedPaint() {
        return clicked;
    }

    public void setClickedPaint(Paint clicked) {
        this.clicked = clicked;
    }

    protected boolean clicked() {
        return hovering() && handler.getMouseManager().isLeftPressed();
    }

    protected boolean hovering() {
        return new Rectangle(x, y, w, h).contains(handler.getMouseManager().getMousePoint());
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public Font getFont() {
        return font;
    }

    public Color getStringColor() {
        return stringColor;
    }

    public void setStringColor(Color stringColor) {
        this.stringColor = stringColor;
    }

    public void setClickedEvent(ActionListener clickedEvent) {
        this.clickedEvent = clickedEvent;
    }
    
    
    
}
