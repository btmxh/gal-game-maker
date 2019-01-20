/*
 * bla bla
 */
package com.duyanh.game.languageparser.variables;

/**
 *
 * @author Welcome
 */
public class Variable {

    public static Variable getVariable(Variable v, String field) {
        switch (field){
            case "name" : return ((PersonVariable) v).name;
            case "choice" : return ((QuestionVariable) v).question.choice;
            default: return null;
        }
    }
    
    
}
