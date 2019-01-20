/*
 * bla bla
 */
package com.duyanh.game.languageparser.variables;

/**
 *
 * @author Welcome
 */
public class BooleanVariable {
    public Variable v1, v2;
    public Compare compare;

    public BooleanVariable(Variable v1, Variable v2, Compare compare) {
        this.v1 = v1;
        this.v2 = v2;
        this.compare = compare;
    }
    
    public boolean get(){
        if(v1.getClass().equals(v2.getClass())){
            if(v1.getClass().equals(StringVariable.class)){
                String s1 = ((StringVariable) v1).string;
                String s2 = ((StringVariable) v2).string;
                
                return compare.compare(s1, s2);
            } else if(v1.getClass().equals(NumericVariable.class)){
                double s1 = ((NumericVariable) v1).number;
                double s2 = ((NumericVariable) v2).number;
                
                return compare.compare(s1, s2);
            }
        } else {
            if(compare.equals(Compare.NOT_EQUAL)){
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return v1 + compare.toString() + v2;
    }
    
    
    
    public enum Compare{
        GREATER, LESS_THAN, EQUAL, NOT_EQUAL, GREATER_OR_EQUAL, LESS_THAN_OR_EQUAL;
        
        public boolean compare(String s1, String s2){
            if(this == GREATER){
                return s1.compareTo(s2) == 1;
            } else if(this == LESS_THAN){
                return s1.compareTo(s2) == -1;
            } else if(this == EQUAL){
                return s1.equals(s2);
            } else if(this == NOT_EQUAL){
                return !s1.equals(s2);
            } else if(this == GREATER_OR_EQUAL){
                return s1.compareTo(s2) >= 0;
            } else if(this == LESS_THAN_OR_EQUAL){
                return s1.compareTo(s2) <= 0;
            } else return false;
        }
        
        public boolean compare(Double s1, Double s2){
            if(this == GREATER){
                return s1.compareTo(s2) == 1;
            } else if(this == LESS_THAN){
                return s1.compareTo(s2) == -1;
            } else if(this == EQUAL){
                return s1.equals(s2);
            } else if(this == NOT_EQUAL){
                return !s1.equals(s2);
            } else if(this == GREATER_OR_EQUAL){
                return s1.compareTo(s2) >= 0;
            } else if(this == LESS_THAN_OR_EQUAL){
                return s1.compareTo(s2) <= 0;
            } else return false;
        }
        
        public static Compare signValueOf(String s){
            s = s.replace(" ", "");
            switch(s){
                case ">": return GREATER;
                case "==": return EQUAL;
                case "<": return LESS_THAN;
                case "!=": return NOT_EQUAL;
                case ">=": return GREATER_OR_EQUAL;
                case "<=": return LESS_THAN_OR_EQUAL;
                default: return null;
            }
        }
    }
}
