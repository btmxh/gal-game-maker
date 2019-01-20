/*
 * bla bla
 */
package com.duyanh.game.languageparser.variables;

import com.duyanh.game.languageparser.conversation.Question;

/**
 *
 * @author Welcome
 */
public class QuestionVariable extends Variable{
    public Question question;

    public QuestionVariable(Question question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return question.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
