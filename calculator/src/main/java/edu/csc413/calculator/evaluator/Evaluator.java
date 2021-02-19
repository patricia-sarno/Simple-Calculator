package edu.csc413.calculator.evaluator;



import edu.csc413.calculator.exceptions.InvalidTokenException;
import edu.csc413.calculator.operators.*;

import java.util.Stack;
import java.util.StringTokenizer;

public class Evaluator {

  private Stack<Operand> operandStack;
  private Stack<Operator> operatorStack;
  private StringTokenizer expressionTokenizer;
  private final String delimiters = " +/*-^()";

  public Evaluator() {
    operandStack = new Stack<>();
    operatorStack = new Stack<>();
  }

  private void useOperator() { //Method to perform operator on two operands and push back into operand stack.
    Operator operator1 = operatorStack.pop();
    Operand operand2 = operandStack.pop();
    Operand operand1 = operandStack.pop();
    operandStack.push(operator1.execute(operand1, operand2));
  }

  public int evaluateExpression(String expression ) throws InvalidTokenException {
    String expressionToken;

    // The 3rd argument is true to indicate that the delimiters should be used
    // as tokens, too. But, we'll need to remember to filter out spaces.
    this.expressionTokenizer = new StringTokenizer( expression, this.delimiters, true );

    // initialize operator stack - necessary with operator priority schema
    // the priority of any operator in the operator stack other than
    // the usual mathematical operators - "+-*/" - should be less than the priority
    // of the usual operators



    while ( this.expressionTokenizer.hasMoreTokens() ) {
      // filter out spaces
      if ( !( expressionToken = this.expressionTokenizer.nextToken() ).equals( " " )) {
        // check if token is an operand
        if ( Operand.check( expressionToken )) {
          operandStack.push( new Operand( expressionToken ));//push in operand stack if operand
        } else {
          if ( ! Operator.check( expressionToken )) {
            throw new InvalidTokenException(expressionToken);
          } else { //check if token is an operator
            Operator operator = Operator.getOperator(expressionToken); //get operator from operator class
            if (operator.equals(Operator.getOperator("("))) { //if open parenthesis push in operator stack
              operatorStack.push(operator);
            } else if (operator.equals(Operator.getOperator(")"))) { //if closed parenthesis then must do operation of expression within the parenthesis
              while ( ! operatorStack.peek().equals(Operator.getOperator("("))) { //will do expression if the top of stack isn't "("
                useOperator();
              }
              operatorStack.pop(); //Takes out left parentheses
            } else {
              //Sorry, I didn't realize you did this part already...I just comment out the one you did :)
              while ( ! operatorStack.empty() && (operatorStack.peek().priority() >= operator.priority())) {
                useOperator();
              }
              operatorStack.push(operator);
            }
          }



          // TODO Operator is abstract - these two lines will need to be fixed:
          // The Operator class should contain an instance of a HashMap,
          // and values will be instances of the Operators.  See Operator class
          // skeleton for an example.
          //Operator newOperator = new Operator();
//          while (operatorStack.peek().priority() >= newOperator.priority() ) {
//            // note that when we eval the expression 1 - 2 we will
//            // push the 1 then the 2 and then do the subtraction operation
//            // This means that the first number to be popped is the
//            // second operand, not the first operand - see the following code
//            Operator operatorFromStack = operatorStack.pop();
//            Operand operandTwo = operandStack.pop();
//            Operand operandOne = operandStack.pop();
//            Operand result = operatorFromStack.execute( operandOne, operandTwo );
//            operandStack.push( result );
//          }
//
//          operatorStack.push( newOperator );
        }
      }
    }

    while ( ! operatorStack.empty() ) {
      useOperator();
    }


    // Control gets here when we've picked up all of the tokens; you must add
    // code to complete the evaluation - consider how the code given here
    // will evaluate the expression 1+2*3
    // When we have no more tokens to scan, the operand stack will contain 1 2
    // and the operator stack will have + * with 2 and * on the top;
    // In order to complete the evaluation we must empty the stacks,
    // that is, we should keep evaluating the operator stack until it is empty;
    // Suggestion: create a method that processes the operator stack until empty.

    return operandStack.pop().getValue(); //need to return final number on value stack.
  }
}
