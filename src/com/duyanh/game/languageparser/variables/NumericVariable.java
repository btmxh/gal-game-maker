/*
 * bla bla
 */
package com.duyanh.game.languageparser.variables;

/**
 *
 * @author Welcome
 */
public class NumericVariable extends Variable{
    public double number;

    public NumericVariable() {
    }
    
    public NumericVariable(double number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return number + ""; //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
