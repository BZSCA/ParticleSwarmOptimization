/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Benny
 */
//Function.java
public class Function {

    private final String question;
    private final int vars;

    public Function(String question) {
        this.question = question;
        this.vars = 2; //defines the number of variables in the equation
    }

    public int getVars() {
        return vars;
    }
    
    public double value(double c[]) {
        if (question.equals("a")) {
            return c[0] * c[0] + c[1] * c[1] - c[0] * c[1] - 4 * c[0] - c[1];
        } else if (question.equals("b")) {
            return (1 - c[0]) * (1 - c[0]) + (-(c[0] * c[0]) + c[1]) * (-(c[0] * c[0]) + c[1]);
        }
        return 1.0;
    }
    
    //insert your constrain here,
    //constraints are in the form of g(x) < 0
    public double[] constraint(double c[]) {
        if (question.equals("b")) {
            double penalty = -10.0 * c[0] - 3.0 * c[1] + 25.0;
            if (penalty > 0) {
                double[] return_double = {penalty * penalty};
                return return_double;
            }
        }
        double[] return_double = {0.0};
        return return_double;
    }

}
