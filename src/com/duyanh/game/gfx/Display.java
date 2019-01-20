/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.duyanh.game.gfx;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author Welcome
 */
public class Display {
    private int width = 1080, height = 720;
    private String title = "Game";
    private JFrame frame;
    private Canvas canvas;

    public Display(int width, int height, String title) {
        this.height = height;
        this.width = width;
        this.title = title;
        createDisplay();
    }

    private void createDisplay() {
        frame = new JFrame(title);
        canvas = new Canvas();
        Dimension dim = new Dimension(width, height);
        frame.setPreferredSize(dim);
        frame.setMinimumSize(dim);
        frame.setMaximumSize(dim);
        frame.setSize(dim);
        
        canvas.setMinimumSize(dim);
        canvas.setMaximumSize(dim);
        canvas.setPreferredSize(dim);
        canvas.setSize(dim);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        
        frame.add(canvas);
        frame.setVisible(true);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public JFrame getFrame() {
        return frame;
    }
    
    public void size(int width, int height){
        Dimension dim = new Dimension(width, height);
        frame.setPreferredSize(dim);
        frame.setMinimumSize(dim);
        frame.setMaximumSize(dim);
        frame.setSize(dim);
        
        canvas.setMinimumSize(dim);
        canvas.setMaximumSize(dim);
        canvas.setPreferredSize(dim);
        canvas.setSize(dim);
    }

    public int getWidth() {
        return frame.getWidth();
    }

    public int getHeight() {
        return frame.getHeight();
    }
    
}
