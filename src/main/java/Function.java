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
        this.vars = 2;
    }

    public double q(double c[]) {
        if (question.equals("a")) {
            return c[0] * c[0] + c[1] * c[1] - c[0] * c[1] - 4 * c[0] - c[1];
        } else if (question.equals("b")) {
            return (1 - c[0]) * (1 - c[0]) + (-(c[0] * c[0]) + c[1]) * (-(c[0] * c[0]) + c[1]);
        }
        return 1.0;
    }

    public double penalty(double c[]) {
        if (question.equals("b")) {
            double penalty = -10.0 * c[0] - 3.0 * c[1] + 25.0;
            if (penalty > 0) {
                return penalty * penalty;
            }
        }
        return 0.0;
    }

}
