/*
 * bla bla
 */
package com.duyanh.game.languageparser;

import com.duyanh.game.languageparser.conversation.Background;
import com.duyanh.game.languageparser.conversation.Choice;
import com.duyanh.game.languageparser.conversation.Conversation;
import com.duyanh.game.languageparser.conversation.GameCommand;
import com.duyanh.game.languageparser.conversation.IfStatement;
import com.duyanh.game.languageparser.conversation.ImagesBackground;
import com.duyanh.game.languageparser.conversation.Input;
import com.duyanh.game.languageparser.conversation.NextImage;
import com.duyanh.game.languageparser.conversation.PlaySound;
import com.duyanh.game.languageparser.conversation.Question;
import com.duyanh.game.languageparser.conversation.StopSound;
import com.duyanh.game.languageparser.conversation.Wait;
import com.duyanh.game.languageparser.resources.ImageResource;
import com.duyanh.game.languageparser.resources.ImagesResource;
import com.duyanh.game.languageparser.resources.MusicResource;
import com.duyanh.game.languageparser.resources.ResourceObject;
import com.duyanh.game.languageparser.variables.BooleanVariable;
import com.duyanh.game.languageparser.variables.NumericVariable;
import com.duyanh.game.languageparser.variables.PersonVariable;
import com.duyanh.game.languageparser.variables.QuestionVariable;
import com.duyanh.game.languageparser.variables.StringVariable;
import com.duyanh.game.languageparser.variables.Variable;
import com.duyanh.game.states.GameState;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.commons.lang3.StringUtils;
import res.ImageLoader;

/**
 *
 * @author Welcome
 */
public class Parser {
    public static GalGame parse(File file) throws FileNotFoundException, ParseException{
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<String> lines = reader.lines().collect(Collectors.toList());
        return parse(lines);
    }
    
    public static GalGame parseResource(String resourcePath) throws IOException, ParseException{
        InputStream inputStream = ImageLoader.class.getResourceAsStream(resourcePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        List<String> lines = reader.lines().collect(Collectors.toList());
        return parse(lines);
    }
    
    public static GalGame parse(String content) throws ParseException{
        String[] lineArray = content.split("\n");
        return parse(Arrays.asList(lineArray));
    }
    
    public static GalGame parse(List<String> lines) throws ParseException{
        GalGame galGame = new GalGame();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if(StringUtils.isAllBlank(line))
                continue;
            if(!line.startsWith("[") && !line.startsWith("//"))
                throw new ParseException("Command in line " + i + " is error.");
            List<String> commands = getCommands(line);
            config(galGame, line, commands, i);
            resource(galGame, line, commands, i);
            variable(galGame, line, commands, i);
        }
        gameplay(lines, galGame);
        return galGame;
    }
    
    private static List<String> getCommands(String line){
        int bracketLevel = 1;
        String command = "";
        List<String> commands = new ArrayList<>();
        for(int j = 1; j < line.length(); j++){
            char c = line.charAt(j);
            System.out.println("Checking character " + c + ", bracketLevel = "+bracketLevel + ", command = "+command);
            if(c == ']'){
                bracketLevel--;
                if(bracketLevel == 0){
                    commands.add(command);
                    command = "";
                } else {
                    command += c;
                }
            } else if(c == '['){
                bracketLevel++;
                if(bracketLevel != 1){
                    command += c;
                }
            } else if(bracketLevel <= 0 & !Character.isWhitespace(c)){
                break;
            } else if(bracketLevel > 0){
                command += c;
            }
        }
        return commands;
    }
    
    public static void main(String[] args) {
        String line = "[input] [[player] name]";
        System.out.println(getCommands(line));
    }
    
    private static void config(GalGame game, String line, List<String> commands, int lineIndex) throws ParseException{
        if(commands.get(0).equals("config")){
            String varName = commands.get(1);
            String value = line.replaceAll("\\[.*?\\]", "").trim();
            double doubleValue;
            if(value.equals("false")){
                doubleValue = -1;
            } else if(value.equals("true")){
                doubleValue = Double.MAX_VALUE;
            } else if(StringUtils.isNumeric(value.replace(".", ""))){
                doubleValue = Double.parseDouble(value);
            } else throw new ParseException("Expecting numeric value at line " + lineIndex + " instead of "+ value);
            game.getConfigs().put(varName, doubleValue);
        }
    }
    
    private static void resource(GalGame game, String line, List<String> commands, int lineIndex) throws ParseException{
        if(commands.get(0).equals("resource")){
            String varName = value(line);
            ResourceObject value = null;
            String path = null;
            for (int i = 2; i < commands.size(); i++) {
                String command = commands.get(i);
                if(command.matches("path\\s+=\\s+\".*?")){
                    path = command.replaceAll("path\\s+=\\s+\".*?", "").trim();
                }
            }
            
            if(path == null){
                throw new ParseException("Resource without path at line " + lineIndex);
            }
            path = path.substring(0, path.length() - 1);
            if(commands.get(1).equals("music")){
                boolean repeat = true;
                for (int i = 2; i < commands.size(); i++) {
                    String command = commands.get(i);
                    if(command.matches("repeat\\s+=\\s+\".*?")){
                        repeat = Boolean.parseBoolean(command.replaceAll("repeat\\s+=\\s+\".*?", "").trim());
                    }
                }
                
                value = new MusicResource(path, repeat);
            }
            if(commands.get(1).equals("image")){
                value = new ImageResource(path);
            }
            if(commands.get(1).equals("images")){
                String ext = "AUTO EXTENSION";
                for (int i = 2; i < commands.size(); i++) {
                    String command = commands.get(i);
                    if(command.matches("ext\\s+=\\s+\".*?")){
                        ext = command.replaceAll("ext\\s+=\\s+\".*?", "").trim();
                    }
                }
                value = new ImagesResource(path, ext.substring(0, ext.length() - 1));
            }
            game.getResources().put(varName, value);
        }
    }
    
    private static void variable(GalGame game, String line, List<String> commands, int lineIndex) throws ParseException{
        if(commands.get(0).equals("variable")){
            String varName = value(line);
            if(commands.get(1).equals("string")){
                StringVariable var = new StringVariable();
                if(varName.matches(".*?=\".*?\"")){
                    String value = varName.split("\\s+=\\s+")[1].trim();
                    varName = varName.split("\\s+=\\s+")[0].trim();
                    var.string = varName;
                }
                game.getVariables().put(varName, var);
            } else if(commands.get(1).equals("number")){
                NumericVariable var = new NumericVariable();
                if(varName.matches(".*?=.*?")){
                    String value = varName.split("\\s+=\\s+")[1].trim();
                    varName = varName.split("\\s+=\\s+")[0].trim();
                    var.number = Double.parseDouble(value);
                }
                game.getVariables().put(varName, var);
            } else if(commands.get(1).equals("person")){
                StringVariable name = new StringVariable();
                for (int i = 2; i < commands.size(); i++) {
                    String command = commands.get(i);
                    if(command.matches("name\\s+=\\s+\".*?")){
                        name = new StringVariable(command.replaceAll("name\\s+=\\s+\".*?", "").trim());
                        name.string = name.string.substring(0, name.string.length() - 1);
                    }
                }
                game.getVariables().put(varName, new PersonVariable(name));
            }
        }
    }
    
    private static void gameplay(List<String> allLines, GalGame game) throws ParseException{
        boolean started = false;
        boolean inQuestion = false;
        Question question = null;
        int conditionLevel = -1;
        List<IfStatement> ifStatements = new ArrayList<>();
        for (int i = 0; i < allLines.size(); i++) {
            String line = allLines.get(i);
            try{
                List<String> commands = getCommands(line);
                List<GameCommand> gameCommands = new ArrayList<>();
                if(commands.isEmpty()){
                    continue;
                }
                if(commands.get(0).equals("startGame")){
                    started = true;
                    continue;
                }
                if(commands.get(0).equals("endGame")){
                    started = false;
                    break;
                }
                if(commands.get(0).equals("conversation")){
                    String personName = commands.get(1);
                    PersonVariable person = (PersonVariable) game.getVariables().get(personName);
                    gameCommands.add(new Conversation(person, value(line)));
                    gameCommands.add(new Wait(game.getConfig("autoComplete")));
                }
                if(commands.get(0).equals("title")){
                    String title = value(line);
                    System.out.println("com.duyanh.game.states.GameState.tick323212323132()");
                    gameCommands.add(new GameCommand() {
                        @Override
                        public void invoke(GameState gameState) {
                            gameState.getHandler().getFrame().setTitle(title);
                        }
                    });
                }
                if(commands.get(0).equals("size")){
                    String[] size = value(line).split("\\s+");
                    int w = Integer.parseInt(size[0]);
                    int h = Integer.parseInt(size[1]);
                    Dimension d = new Dimension(w, h);
                    gameCommands.add(new GameCommand() {
                        @Override
                        public void invoke(GameState gameState) {
                            gameState.getHandler().getGame().getDisplay().size(w, h);
                        }
                    });
                }
                if(commands.get(0).equals("question")){
                    continue;
                }
                if(commands.get(0).equals("input")){
                    String varName = commands.get(1);
                    Variable var = variable(varName, game);
                    System.err.println(commands);
                    String questionLine = allLines.get(i - 1);
                    String questionVarName = null;
                    String askerName = null;
                    List<String> questionCommands = getCommands(questionLine);
                    for (int j = 1; j < questionCommands.size(); j++) {
                        String command = questionCommands.get(j);
                        if(command.matches("variableName\\s+=\\s+\".*?")){
                            questionVarName = command.replaceAll("variableName\\s+=\\s+\".*?", "").trim();
                            continue;
                        }
                        if(game.getVariables().containsKey(command)){
                            askerName = command;
                            continue;
                        }
                    }
                    question = new Question((PersonVariable) game.getVariables().get(askerName), value(questionLine));
                    game.getVariables().put(questionVarName, new QuestionVariable(question));
                    gameCommands.add(question);
                    question.input = new Input(var);
                    
                }
                if(commands.get(0).equals("choice")){
                    int value = Integer.parseInt(commands.get(1));
                    String choiceString = value(line);
                    Choice choice = new Choice(value, choiceString);
                    if(!inQuestion){
                        //First Input
                        String questionLine = allLines.get(i - 1);
                        String questionVarName = null;
                        String askerName = null;
                        List<String> questionCommands = getCommands(questionLine);
                        for (int j = 1; j < questionCommands.size(); j++) {
                            String command = questionCommands.get(j);
                            if(command.matches("variableName\\s+=\\s+\".*?")){
                                questionVarName = command.replaceAll("variableName\\s+=\\s+\".*?", "").trim();
                                continue;
                            }
                            if(game.getVariables().containsKey(command)){
                                askerName = command;
                            }
                        }
                        if(questionVarName != null){
                            questionVarName = questionVarName.substring(0, questionVarName.length() - 1);
                        }
                        question = new Question((PersonVariable) game.getVariables().get(askerName), value(questionLine));
                        game.getVariables().put(questionVarName, new QuestionVariable(question));
                        gameCommands.add(question);
                        inQuestion = true;
                    } 
                    question.choices.add(choice);
                } else inQuestion = false;
                if(commands.get(0).equals("endCondition")){
                    ifStatements.remove(conditionLevel--);
                }
                if(isConditionLine(line)){
                    String temp;
                    temp = line.replace("=>", "");
                    String[] vars = temp.split(">|<|==|!=|>=|<=");
                    for(int j = 0; j < vars.length; j++)
                        vars[j] = vars[j].trim();
                    Variable v1 = variable(vars[0].substring(1), game);
                    Variable v2 = variable(vars[1].substring(0, vars[1].length() - 1), game);
                    temp = temp.replaceAll(Pattern.quote(vars[0]) + "|" + Pattern.quote(vars[1]), "");
                    BooleanVariable condition = new BooleanVariable(v1, v2, BooleanVariable.Compare.signValueOf(temp));
                    IfStatement ifStatement = new IfStatement(condition, new ArrayList<>());
                    ifStatements.add(ifStatement);
                    gameCommands.add(ifStatement);
                    if(conditionLevel >= 0){
                        ifStatements.get(conditionLevel).commands.addAll(gameCommands);
                    }else game.getGamePlay().addAll(gameCommands);
                    conditionLevel++;
                    continue;
                }
                if(commands.get(0).equals("nextImage")){
                    gameCommands.add(new NextImage((ImagesResource) game.getResources().get(value(line))));
                }
                if(commands.get(0).equals("image")){
                    if(commands.size() == 1){
                        gameCommands.add(new Background((ImageResource) game.getResources().get(value(line))));
//                        System.out.println(game.getResources().get(value(line)));
                    } else {
                        NumericVariable index = (NumericVariable) variable(commands.get(1), game);
                        gameCommands.add(new ImagesBackground(((ImagesResource) game.getResources().get(value(line))), index));
                    }
                }
                if(commands.get(0).equals("playSound")){
                    gameCommands.add(new PlaySound((MusicResource) game.getResources().get(value(line))));
                }
                if(commands.get(0).equals("stopSound")){
                    gameCommands.add(new StopSound((MusicResource) game.getResources().get(value(line))));
                }
                if(conditionLevel >= 0){
                    ifStatements.get(conditionLevel).commands.addAll(gameCommands);
                }else game.getGamePlay().addAll(gameCommands);
            } catch (Exception ex){
                ex.printStackTrace();
                throw new ParseException("Error at line "+i);
            }
        }
    }
    
    private static String value(String line){
        int bracketLevel = 1;
        boolean isCommand = true;
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 1; i < line.length(); i++){
            char c = line.charAt(i);
            if(isCommand){
                if(c == '['){
                    bracketLevel++;
                    continue;
                } else if(c == ']'){
                    bracketLevel--;
                    continue;
                }
                if(bracketLevel > 0){
                    continue;
                } else {
                    if(!Character.isWhitespace(c)){
                        stringBuilder.append(c);
                        isCommand = false;
                    }
                }
            } else {
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }
    
    private static String regexForBoolean(){
        return ".*?[>|<|==|!=|>=|<=].*?";
    }
    
    public static Variable variable(String varName, GalGame game){
        Variable var;
        if(varName.startsWith("[")){
            varName = varName.substring(1, varName.length() - 1);
            String field = value(varName);
            String fieldOwner = getCommands(varName).get(0);
            Variable v = game.getVariables().get(fieldOwner);
            var = Variable.getVariable(v, field);
        } else if(StringUtils.isNumeric(varName.replace(".", ""))){
            var = new NumericVariable(Double.parseDouble(varName));
        } else if(varName.startsWith("\"") && varName.endsWith("\"")){
            var = new StringVariable(varName.substring(1, varName.length() - 1));
        } else var = game.getVariables().get(varName);
        return var;
    }

    private static boolean isConditionLine(String line) {
        
        
        boolean isCondition = line.indexOf("=>") > indexOfSign(line) && line.contains("=>") && indexOfSign(line) != -1;
        return isCondition;
    }
    
    private static int indexOfSign(String line) {
        String[] signs = {">", "<", "==", "!=", ">=", "<="};
        int[] indices = new int[signs.length];
        for (int i = 0; i < signs.length; i++) {
            String sign = signs[i];
            indices[i] = line.indexOf(sign);
        }
        return min(indices);
    }
    
    private static int min(int... ints){
        OptionalInt a = IntStream.of(ints).filter((i) -> i != -1).min();
        if(a.isPresent()){
            return a.getAsInt();
        } else {
            return -1;
        }
    }
}
