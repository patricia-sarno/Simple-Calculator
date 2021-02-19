package edu.csc413.calculator.evaluator;

/**
 * Operand class used to represent an operand
 * in a valid mathematical expression.
 */

public class Operand {

    private int num;

    /**
     * construct operand from string token.
     * Need to convert string into a number.
     */
    public Operand(String token) {
        this.num = Integer.parseInt(token);
    }

    /**
     * construct operand from integer
     * For inner expression evaluation.
     * Store operands as int object.
     */
    public Operand(int value) {
        this.num = value;
    }

    /**
     * return value of operand
     */
    public int getValue() {
        return this.num;
    }

    /**
     * Check to see if given token is a valid
     * operand.
     */
    public static boolean check(String token) {
        try{
            Integer.parseInt(token);
        }catch(Exception ex){
            return false;
        }
        return true;
    }
}
