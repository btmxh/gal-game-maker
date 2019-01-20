/*
 * bla bla
 */
package com.duyanh.game.languageparser.variables;

/**
 *
 * @author Welcome
 */
public class PersonVariable extends Variable{
    public StringVariable name = new StringVariable();

    public PersonVariable() {
    }
    
    public PersonVariable(String name){
        this.name = new StringVariable(name);
    }
    
    public PersonVariable(StringVariable name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name.string; //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
