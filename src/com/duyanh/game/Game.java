/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.duyanh.game;


import com.duyanh.game.gfx.Assets;
import com.duyanh.game.gfx.Display;
import com.duyanh.game.input.KeyManager;
import com.duyanh.game.input.MouseManager;
import com.duyanh.game.states.GameState;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

/**
 *
 * @author Welcome
 */
public class Game implements Runnable{
    //Display Fields
    private int width,height;
    private String title;
    //Thread
    private boolean running;
    private Thread thread;
    private int maxFPS = 120;
    //Handler
    private Handler handler;
    //Rendering
    private BufferStrategy bs;
    private Graphics g;
    private Display display;
    //Managers
    private KeyManager key;
    private MouseManager mouse;
    //States
    private GameState gameState;
    
    public Game(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
        handler = new Handler(this);
    }
    private int FPS = 60;
    public void run(){
		init();
		
		int fps = 60;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;
		
		while(running){
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;
			
			if(delta >= 1){
				tick();
				render();
				ticks++;
				delta--;
			}
			
			if(timer >= 1000000000){
				//System.out.println("Ticks and Frames: " + ticks);
                                FPS = ticks;
				ticks = 0;
				timer = 0;
			}
		}
		
		stop();
		
	}
	
	public synchronized void start(){
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop(){
		if(!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

    private void init() {
        display = new Display(width, height, title);
        Assets.init(handler);
        gameState = new GameState(handler, "galGame1.gal");
        key = new KeyManager();
        mouse = new MouseManager(handler);
        display.getFrame().addKeyListener(key);
        display.getCanvas().addKeyListener(key);
        display.getFrame().addMouseListener(mouse);
        display.getCanvas().addMouseListener(mouse);
        display.getCanvas().addMouseMotionListener(mouse);
        display.getFrame().addMouseMotionListener(mouse);
    }

    private void tick() {
        key.tick();
        gameState.tick();
    }

    private void render() {
        bs =display.getCanvas().getBufferStrategy();
        if(bs == null){
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        g.clearRect(0, 0, width, height);
        
        //Draw Here
        
        gameState.render(g);
        
        //End Drawing
        
        g.dispose();
        bs.show();
        
    }
    
    public KeyManager getKeyManager(){
        return key;
    }
    
    public MouseManager getMouseManager(){
        return mouse;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getFPS() {
        return FPS;
    }

    public Display getDisplay() {
        return display;
    }

    public Graphics getGraphics() {
        return g;
    }
    
    
    
}
