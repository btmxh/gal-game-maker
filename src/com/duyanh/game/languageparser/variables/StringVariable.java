/*
 * bla bla
 */
package com.duyanh.game.languageparser.variables;

/**
 *
 * @author Welcome
 */
public class StringVariable extends Variable{
    public String string;

    public StringVariable(String string) {
        this.string = string;
    }

    public StringVariable() {
    }

    @Override
    public String toString() {
        return string; //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
