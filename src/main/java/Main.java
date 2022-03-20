/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Benny
 */
// Main.java
import java.lang.Math;

public class Main {

    public static void main(String[] args) {
        // you can test how your classes work here

        Function fa = new Function("a");
        Function fb = new Function("b");

        for (int i = 0; i < 5; i++) {
            ParticleSwarm particleswarm = new ParticleSwarm(100.0, fa, 0);
            particleswarm.run(0.000001, 400);
        }

        System.out.println("");

        for (int i = 0; i < 5; i++) {
            ParticleSwarm particleswarm = new ParticleSwarm(100.0, fb, 0);
            particleswarm.run(0.000001, 400);
        }

        double[] point = {0, 0, 0};
        double[] oldPoint = null;
        double minTolerance = 0.001;
        double tolerance = 1.0;
        int rp = 1;

        while (tolerance > minTolerance && rp <= 20) {

            for (int j = 0; j < 20 && tolerance > minTolerance; j++) {
                ParticleSwarm particleswarm = new ParticleSwarm(100.0, fb, rp);

                point = particleswarm.run(0.000001, 400);
                if (fb.penalty(point) < 0.00001) {
                    if (oldPoint != null) {
                        tolerance = 0.0;
                        for (int i = 0; i < 2; i++) {
                            tolerance += (point[i] - oldPoint[i]) * (point[i] - oldPoint[i]);
                        }
                        tolerance = Math.sqrt(tolerance);

                        if (tolerance < minTolerance) {
                            System.out.printf("Iterations: %d\n", particleswarm.getCurrentIterations());
                        }
                    }
                    oldPoint = point;
                }
            }
            rp++;
        }

        System.out.printf("R value for penalty: %d\n", rp);
        System.out.printf("Best Point: %f %f Value: %f\n", point[0], point[1], fb.q(point));
    }

}
