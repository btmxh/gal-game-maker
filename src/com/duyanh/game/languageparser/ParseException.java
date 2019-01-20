/*
 * bla bla
 */
package com.duyanh.game.languageparser;

/**
 *
 * @author Welcome
 */
public class ParseException extends Exception {
    private Exception ex;
    /**
     * Creates a new instance of <code>ParseException</code> without detail
     * message.
     */
    public ParseException() {
    }

    /**
     * Constructs an instance of <code>ParseException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public ParseException(String msg) {
        super(msg);
    }

    public ParseException(Exception ex, String message) {
        super(message);
        this.ex = ex;
    }
    
    

    @Override
    public void printStackTrace() {
        super.printStackTrace();
        if(ex != null)
            ex.printStackTrace();
    }
    
    
}
