/*
 * bla bla
 */
package com.duyanh.game.languageparser.resources;

import com.duyanh.game.languageparser.ParseException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import res.ImageLoader;

/**
 *
 * @author Welcome
 */
public class MusicResource extends ResourceObject{
    private String audioPath;
    private InputStream audio;
    private Player player;
    private boolean repeat;
    private Thread audioThread;

    public MusicResource(String audioPath, boolean repeat) throws ParseException {
        this.audioPath = audioPath;
        this.repeat = repeat;
    }
    
    public void play() {
        audioThread.start();
    }
    
    public void stop(){
        try {
            audioThread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(MusicResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String toString() {
        return audioPath; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void load() throws IOException {
        try {
            this.audio = new FileInputStream(audioPath);
            player = new Player(audio);
            audioThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        player.play();
                    } catch (JavaLayerException ex) {
                        Logger.getLogger(MusicResource.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        } catch (JavaLayerException ex) {
            throw new IOException("An IOException occured");
        }
        
    }
    
    public static void main(String[] args) throws ParseException, IOException {
        MusicResource mr = new MusicResource("background.mp3", false);
        mr.load();
        mr.play();
        System.out.println("com.duyanh.game.languageparser.resources.MusicResource.main()");
    }
    
    
}
