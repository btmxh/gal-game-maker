/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.duyanh.game.states;

import com.duyanh.components.GComponent;
import com.duyanh.components.GRoundButton;
import com.duyanh.components.GTextField;
import com.duyanh.game.Handler;
import com.duyanh.game.gfx.Assets;
import com.duyanh.game.gfx.Text;
import com.duyanh.game.languageparser.GalGame;
import com.duyanh.game.languageparser.ParseException;
import com.duyanh.game.languageparser.Parser;
import com.duyanh.game.languageparser.conversation.Conversation;
import com.duyanh.game.languageparser.conversation.GameCommand;
import com.duyanh.game.languageparser.conversation.Question;
import com.duyanh.game.languageparser.variables.NumericVariable;
import com.duyanh.game.languageparser.variables.StringVariable;
import com.duyanh.game.languageparser.variables.Variable;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Welcome
 */
public class GameState extends State{
    private GalGame game;
    private List<BufferedImage> images;
    private int commandIndex = 0;
    private Graphics g;
    
    private boolean waitAccept = true;
    private boolean stop;
    private GameCommand rendering;
    private List<String> lineOfConversation;
    private List<GComponent> components = new ArrayList<>();
    private boolean enter = false;
 	
    public GameState(Handler handler, String galGamePath){
        super(handler);
        g = handler.getGame().getGraphics();
        
        try {
            game = Parser.parseResource(galGamePath);
            game.loadResources();
            System.out.println("-------------------------");
            System.out.println("Game Parsing Result:");
            System.out.println(game);
            System.out.println("-------------------------");
        } catch (IOException | ParseException ex) {
            Logger.getLogger(GameState.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
        images = new ArrayList<>();
        
        
        
    }

    @Override
    public void tick() {
        CONVERSATION_BOX_WIDTH = handler.getWidth() - CONVERSATION_BOX_X * 2;
        CONVERSATION_BOX_HEIGHT = 220;
        MAX_TEXT_WIDTH =  CONVERSATION_BOX_WIDTH - ARC_WIDTH * 2;
        if(enter && handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)){
            continueGame();
            enter = false;
            String value = ((GTextField) components.get(0)).getValue();
            Variable var = ((Question) rendering).input.var;
            System.out.println(var.getClass());
            if(var instanceof StringVariable){
                
                ((StringVariable) var).string = value;
            } else if(var instanceof NumericVariable){
                ((NumericVariable) var).number = Double.parseDouble(value);
            }
        }
        for (GComponent component : components) {
            component.tick();
        }
        if(!waitAccept){
                if(getHandler().getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)){
                    lineOfConversation.remove(1);
                    lineOfConversation.remove(1);
                    lineOfConversation.remove(1);

                    if(lineOfConversation.size() < 4){
                        waitAccept = true;
                        stop = false;
                    }
                }    
        }
        if(stop)
            return;
        if(commandIndex == game.getGamePlay().size()){
            return;
        }
        GameCommand command = game.getGamePlay().get(commandIndex++);
        command.invoke(this);
        System.out.println("invoking command " + commandIndex);
//        if(command instanceof Question){
//            GRoundButton b1 = new GRoundButton(handler, CONVERSATION_BOX_X + BUTTON_X_OFFSET, CONVERSATION_BOX_Y + TEXT_Y_OFFSET_LINE1 + SPACING * 2, CONVERSATION_BOX_WIDTH - 2 * BUTTON_X_OFFSET, FONT_SIZE + SPACING, ARC_WIDTH / 2, ARC_HEIGHT / 2);
//            GRoundButton b2 = new GRoundButton(handler, CONVERSATION_BOX_X + BUTTON_X_OFFSET, CONVERSATION_BOX_Y + TEXT_Y_OFFSET_LINE2 + SPACING * 4, CONVERSATION_BOX_WIDTH - 2 * BUTTON_X_OFFSET, FONT_SIZE + SPACING, ARC_WIDTH / 2, ARC_HEIGHT / 2);
//            GRoundButton b3 = new GRoundButton(handler, CONVERSATION_BOX_X + BUTTON_X_OFFSET, CONVERSATION_BOX_Y + TEXT_Y_OFFSET_LINE3 + SPACING * 6, CONVERSATION_BOX_WIDTH - 2 * BUTTON_X_OFFSET, FONT_SIZE + SPACING, ARC_WIDTH / 2, ARC_HEIGHT / 2);
//
//            b1.setClickedEvent((e) -> {
//                System.out.println(1);
//            });
//            b2.setClickedEvent((e) -> {
//                System.out.println(2);
//            });
//            b3.setClickedEvent((e) -> {
//                System.out.println(3);
//            });
//            if(((Question) command).choices.size() >= 1){
//                b1.setText(((Question) command).choices.get(0).choiceString.string);
//                components.add(b1);
//            }
//            
//            if(((Question) command).choices.size() >= 2){
//                b2.setText(((Question) command).choices.get(1).choiceString.string);
//                components.add(b2);
//            }
//            
//            if(((Question) command).choices.size() >= 3){
//                b3.setText(((Question) command).choices.get(2).choiceString.string);
//                components.add(b3);
//            }
//        }
    }

    @Override
    public void render(Graphics g) {
        this.g = g;
        
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);

        // You can also enable antialiasing for text:

        g2d.setRenderingHint(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        images.forEach((image) -> {
            g.drawImage(image, 0, 0, getHandler().getWidth(), getHandler().getHeight(), null);
        });
        if(rendering instanceof Conversation){
            renderConversationBox(g);
        }
        if (rendering instanceof Question) {
            Question question = (Question) rendering;
            if(question.input == null){
                renderChoiceQuestion(g, question);
            } else {
                renderInputQuestion(g, question);
            }
        }
        for (GComponent component : components) {
            component.render(g);
        }
    }
    
    private void renderConversationBox(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        final int STROKE_SIZE = 5;
        g2.setColor(new Color(0,0,182,155));
        g2.fillRoundRect(CONVERSATION_BOX_X, CONVERSATION_BOX_Y, CONVERSATION_BOX_WIDTH,
                CONVERSATION_BOX_HEIGHT, ARC_WIDTH, ARC_HEIGHT);
        g2.setStroke(new BasicStroke(STROKE_SIZE));
        g2.setColor(Color.WHITE);
        g2.drawRoundRect(CONVERSATION_BOX_X, CONVERSATION_BOX_Y, CONVERSATION_BOX_WIDTH,
                CONVERSATION_BOX_HEIGHT, ARC_WIDTH, ARC_HEIGHT);
        g2.setColor(Color.BLACK);
        g2.drawRoundRect(CONVERSATION_BOX_X+STROKE_SIZE, CONVERSATION_BOX_Y+STROKE_SIZE, CONVERSATION_BOX_WIDTH-STROKE_SIZE*2,
                CONVERSATION_BOX_HEIGHT-STROKE_SIZE*2, ARC_WIDTH-STROKE_SIZE, ARC_HEIGHT-STROKE_SIZE);
        g2.setColor(Color.BLUE);
        g2.drawRoundRect(CONVERSATION_BOX_X+STROKE_SIZE*3/2, CONVERSATION_BOX_Y+STROKE_SIZE*3/2, CONVERSATION_BOX_WIDTH-STROKE_SIZE*3,
                CONVERSATION_BOX_HEIGHT-STROKE_SIZE*3, ARC_WIDTH-STROKE_SIZE*3/2, ARC_HEIGHT-STROKE_SIZE*3/2);
        if(rendering == null)
            return;
        
        try{
            Text.drawString(g, lineOfConversation.get(0), CONVERSATION_BOX_X + TEXT_X_OFFSET, CONVERSATION_BOX_Y + TEXT_Y_OFFSET_LINE1,
                    false, Color.WHITE, Assets.font36);
            Text.drawString(g, lineOfConversation.get(1), CONVERSATION_BOX_X + TEXT_X_OFFSET, CONVERSATION_BOX_Y + TEXT_Y_OFFSET_LINE2,
                    false, Color.WHITE, Assets.font36);
            Text.drawString(g, lineOfConversation.get(2), CONVERSATION_BOX_X + TEXT_X_OFFSET, CONVERSATION_BOX_Y + TEXT_Y_OFFSET_LINE3,
                    false, Color.WHITE, Assets.font36);
            Text.drawString(g, lineOfConversation.get(3), CONVERSATION_BOX_X + TEXT_X_OFFSET, CONVERSATION_BOX_Y + TEXT_Y_OFFSET_LINE4,
                    false, Color.WHITE, Assets.font36);
        } catch (IndexOutOfBoundsException e){
            
        }
    }
    
    private void renderChoiceQuestion(Graphics g, Question question) {
        Graphics2D g2 = (Graphics2D) g;
//        Text.drawString(g, question.question, CONVERSATION_BOX_X + TEXT_X_OFFSET, CONVERSATION_BOX_Y + TEXT_Y_OFFSET_LINE1,
//                    false, Color.WHITE, Assets.font36);
        final int STROKE_SIZE = 5;
        g2.setColor(new Color(0,0,182,155));
        g2.fillRoundRect(CONVERSATION_BOX_X, CONVERSATION_BOX_Y, CONVERSATION_BOX_WIDTH,
                CONVERSATION_BOX_HEIGHT, ARC_WIDTH, ARC_HEIGHT);
        g2.setStroke(new BasicStroke(STROKE_SIZE));
        g2.setColor(Color.WHITE);
        g2.drawRoundRect(CONVERSATION_BOX_X, CONVERSATION_BOX_Y, CONVERSATION_BOX_WIDTH,
                CONVERSATION_BOX_HEIGHT, ARC_WIDTH, ARC_HEIGHT);
        g2.setColor(Color.BLACK);
        g2.drawRoundRect(CONVERSATION_BOX_X+STROKE_SIZE, CONVERSATION_BOX_Y+STROKE_SIZE, CONVERSATION_BOX_WIDTH-STROKE_SIZE*2,
                CONVERSATION_BOX_HEIGHT-STROKE_SIZE*2, ARC_WIDTH-STROKE_SIZE, ARC_HEIGHT-STROKE_SIZE);
        g2.setColor(Color.BLUE);
        g2.drawRoundRect(CONVERSATION_BOX_X+STROKE_SIZE*3/2, CONVERSATION_BOX_Y+STROKE_SIZE*3/2, CONVERSATION_BOX_WIDTH-STROKE_SIZE*3,
                CONVERSATION_BOX_HEIGHT-STROKE_SIZE*3, ARC_WIDTH-STROKE_SIZE*3/2, ARC_HEIGHT-STROKE_SIZE*3/2);
        
        
        
        Text.drawString(g, question.asker + ": " +question.question, CONVERSATION_BOX_X + TEXT_X_OFFSET, 
                CONVERSATION_BOX_Y + TEXT_Y_OFFSET_LINE1, false, Color.WHITE, Assets.font36);
    }

    private void renderInputQuestion(Graphics g, Question question) {
        Graphics2D g2 = (Graphics2D) g;
        final int STROKE_SIZE = 5;
        g2.setColor(new Color(0,0,182,155));
        g2.fillRoundRect(CONVERSATION_BOX_X, CONVERSATION_BOX_Y, CONVERSATION_BOX_WIDTH,
                CONVERSATION_BOX_HEIGHT, ARC_WIDTH, ARC_HEIGHT);
        g2.setStroke(new BasicStroke(STROKE_SIZE));
        g2.setColor(Color.WHITE);
        g2.drawRoundRect(CONVERSATION_BOX_X, CONVERSATION_BOX_Y, CONVERSATION_BOX_WIDTH,
                CONVERSATION_BOX_HEIGHT, ARC_WIDTH, ARC_HEIGHT);
        g2.setColor(Color.BLACK);
        g2.drawRoundRect(CONVERSATION_BOX_X+STROKE_SIZE, CONVERSATION_BOX_Y+STROKE_SIZE, CONVERSATION_BOX_WIDTH-STROKE_SIZE*2,
                CONVERSATION_BOX_HEIGHT-STROKE_SIZE*2, ARC_WIDTH-STROKE_SIZE, ARC_HEIGHT-STROKE_SIZE);
        g2.setColor(Color.BLUE);
        g2.drawRoundRect(CONVERSATION_BOX_X+STROKE_SIZE*3/2, CONVERSATION_BOX_Y+STROKE_SIZE*3/2, CONVERSATION_BOX_WIDTH-STROKE_SIZE*3,
                CONVERSATION_BOX_HEIGHT-STROKE_SIZE*3, ARC_WIDTH-STROKE_SIZE*3/2, ARC_HEIGHT-STROKE_SIZE*3/2);
    }
    
    public void addImage(BufferedImage image){
        images.remove(image);
        images.add(image);
    }
    
    public void continueGame(){
            stop = false;
    }
    
    public void stopGame(){
            stop = true;
    }
    
    public void reverseStatus(){
        if(waitAccept)
            stop = !stop;
    }
    
    public void conversation(Conversation conversation){
        components.clear();
        this.rendering = conversation;
        String personName = conversation.speaker.name.string;
        lineOfConversation = new ArrayList<>();
        lineOfConversation.add(personName);
        chop(conversation.sentence, lineOfConversation, Assets.font36, g);
        
        System.out.println(lineOfConversation);
        if(lineOfConversation.size() > 4){
            waitAccept = false;
        }
    }
    
    public void choiceQuestion(Question question){
        components.clear();
        this.rendering = question;
        GRoundButton b1 = new GRoundButton(handler, CONVERSATION_BOX_X + BUTTON_X_OFFSET, CONVERSATION_BOX_Y + TEXT_Y_OFFSET_LINE1 + SPACING * 2, CONVERSATION_BOX_WIDTH - 2 * BUTTON_X_OFFSET, FONT_SIZE + SPACING, ARC_WIDTH / 2, ARC_HEIGHT / 2);
        GRoundButton b2 = new GRoundButton(handler, CONVERSATION_BOX_X + BUTTON_X_OFFSET, CONVERSATION_BOX_Y + TEXT_Y_OFFSET_LINE2 + SPACING * 4, CONVERSATION_BOX_WIDTH - 2 * BUTTON_X_OFFSET, FONT_SIZE + SPACING, ARC_WIDTH / 2, ARC_HEIGHT / 2);
        GRoundButton b3 = new GRoundButton(handler, CONVERSATION_BOX_X + BUTTON_X_OFFSET, CONVERSATION_BOX_Y + TEXT_Y_OFFSET_LINE3 + SPACING * 6, CONVERSATION_BOX_WIDTH - 2 * BUTTON_X_OFFSET, FONT_SIZE + SPACING, ARC_WIDTH / 2, ARC_HEIGHT / 2);

        b1.setClickedEvent((e) -> {
            question.choice.number = 1;
            System.out.println(question.choice);
            continueGame();
        });
        b2.setClickedEvent((e) -> {
            question.choice.number = 2;
            continueGame();
        });
        b3.setClickedEvent((e) -> {
            question.choice.number = 3;
            continueGame();
        });
        if(question.choices.size() >= 1){
            b1.setText(question.choices.get(0).choiceString.string);
            components.add(b1);
        }

        if(question.choices.size() >= 2){
            b2.setText(question.choices.get(1).choiceString.string);
            components.add(b2);
        }

        if(question.choices.size() >= 3){
            b3.setText(question.choices.get(2).choiceString.string);
            components.add(b3);
        }
        stop = true;
    }
    
    public void inputQuestion(Question question){
        components.clear();
        this.rendering = question;
        GTextField inputField = new GTextField(handler, CONVERSATION_BOX_X + BUTTON_X_OFFSET, CONVERSATION_BOX_Y + TEXT_Y_OFFSET_LINE1 + SPACING * 2, CONVERSATION_BOX_WIDTH - 2 * BUTTON_X_OFFSET, FONT_SIZE + SPACING);
        components.add(inputField);
        stop = true;
        enter = true;
    }
    
    private void chop(String sentence, List<String> toRender, Font font, Graphics g) {
        FontMetrics fm = g.getFontMetrics(font);
        String[] words = sentence.split("\\s+");
        List<StringBuilder> lines = new ArrayList<>();
        int start = 0;
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            boolean added = false;
            for (int j = start; j < lines.size(); j++) {
                StringBuilder builder = lines.get(j);
                String s = builder.toString() + " " + word;
                System.out.println(fm.stringWidth(s));
                if(fm.stringWidth(s) <= MAX_TEXT_WIDTH){
                    builder.append(" ").append(word);
                    added = true;
                } else {
                    start++;
                }
            }
            if(!added){
                lines.add(new StringBuilder(word));
            }
        }
        for (StringBuilder line : lines) {
            toRender.add(line.toString());
        }
    }
    
    private static String get(List<String> list, int index){
        if(list.size() > index){
            return list.get(index);
        } else {
            list.add("");
            return get(list, index);
        }
    }
    
    public GalGame getGame(){
        return game;
    }
    
    //Constants
    private int
            CONVERSATION_BOX_X = 236, CONVERSATION_BOX_Y = 455,
            CONVERSATION_BOX_WIDTH, CONVERSATION_BOX_HEIGHT,
            ARC_WIDTH = 50, ARC_HEIGHT = 50;
    private int FONT_SIZE = 36, TEXT_X_OFFSET = 20,
            TEXT_Y_OFFSET_LINE1 = 50, SPACING = 4,
            TEXT_Y_OFFSET_LINE2 = FONT_SIZE + TEXT_Y_OFFSET_LINE1 + SPACING,
            TEXT_Y_OFFSET_LINE3 = FONT_SIZE + TEXT_Y_OFFSET_LINE2 + SPACING,
            TEXT_Y_OFFSET_LINE4 = FONT_SIZE + TEXT_Y_OFFSET_LINE3 + SPACING;
    private int MAX_TEXT_WIDTH;
    
    private int BUTTON_X_OFFSET = 20;

    
    public static void main(String[] args) {
        String sentence = "Hello. I am giap. Yesterday I saw my new waifu. She is cosi";
        List<String> toRender = new ArrayList<>();
        String[] words = sentence.split("\\s+");
        List<StringBuilder> lines = new ArrayList<>();
        int start = 0;
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            boolean added = false;
            for (int j = start; j < lines.size(); j++) {
                StringBuilder builder = lines.get(j);
                String s = builder.toString() + " " + word;
                if(s.length() <= 10){
                    builder.append(" ").append(word);
                    added = true;
                    start++;
                }
            }
            if(!added){
                lines.add(new StringBuilder(word));
            }
        }
        for (StringBuilder line : lines) {
            toRender.add(line.toString());
        }
        System.out.println(toRender);
    }
    
    
    
    
    
}
