
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Benny
 */

//ParticleSwarm.java
import java.lang.Math;
import java.util.ArrayList;
import java.util.Random;

public class ParticleSwarm {

    private ArrayList<Particle> particles = new ArrayList<>();
    private Random rand = new Random();
    private Function f;
    private double rp;
    private int currentIterations = 0;

    public ParticleSwarm(double max_velocity, Function f, double rp) {
        this.f = f;
        this.rp = rp;
        for (int i = 0; i < 10; i++) {
            double[] position = {randomDouble(-100.0, 100.0), randomDouble(-100.0, 100.0)};
            double[] velocity = {randomDouble(-100.0, 100.0), randomDouble(-100.0, 100.0)};
            double[] r = {rand.nextDouble(), rand.nextDouble()};
            particles.add(new Particle(position, velocity, f.q(position), max_velocity, r));
        }

    }

    private double[] update() {
        double fBest = f.q(particles.get(0).getPoint());
        double[] gBest = particles.get(0).getPoint();

        for (Particle p : particles) {
            double[] getpoint = p.getPoint();
            double pValue = f.q(getpoint) + rp * f.penalty(getpoint);

            if (pValue < fBest) {
                fBest = pValue;
                gBest = p.getPoint();
            }
        }

        for (Particle p : particles) {
            p.updatePosition(gBest, f, rp);
        }
        return gBest;
    }

    public double[] run(double minTolerance, int maxIterations) {
        double currentTolerance = minTolerance + 1.0;
        currentIterations = 0;
        double[] previousgBest = update();
        double[] currentgBest = {0, 0};

        while (currentTolerance > minTolerance && currentIterations < maxIterations - 1) {
            //System.out.printf("Current Best Point: %.2f %.2f Value: %.2f\n",currentgBest[0], currentgBest[1], f.q(currentgBest));

            currentgBest = update();

            currentTolerance = 0.0;

            for (int i = 0; i < 2; i++) {
                currentTolerance += (previousgBest[i] - currentgBest[i]) * (previousgBest[i] - currentgBest[i]);
                //System.out.printf("current tol: %f\n", currentTolerance);
            }
            currentTolerance = Math.sqrt(currentTolerance);

            if (currentTolerance == 0.0) {
                currentTolerance = 0.1;
            }

            currentIterations++;
        }

        System.out.printf("Iterations: %d\n", currentIterations);
        System.out.printf("Best Point: %f %f Value: %f\n", currentgBest[0], currentgBest[1], f.q(currentgBest));
        System.out.printf("Constraint: %f\n", f.penalty(currentgBest));
        return currentgBest;

    }

    public int getCurrentIterations() {
        return currentIterations;
    }

    private double randomDouble(double upper, double lower) {
        return (this.rand.nextDouble() * (upper - lower)) + lower;
    }

    public void printValues() {
        for (Particle p : particles) {
            System.out.println(p);
        }
    }

}
