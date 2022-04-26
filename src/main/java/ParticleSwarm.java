
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
import java.util.List;
import java.util.Random;

public class ParticleSwarm {

    private List<Particle> particles = new ArrayList<>();
    private Random rand = new Random(100);
    private final Function f;
    private final double rp;
    private int currentIterations = 0;
    private final int vars; 
    
    public ParticleSwarm(int nparticles, double max_velocity, Function f){
        this(nparticles, max_velocity, f, new double[] {0.7, 2.0, 2.0}, 0);
    }
    
    public ParticleSwarm(int nparticles, double max_velocity, Function f, double[] coefficients){
        this(nparticles, max_velocity, f, coefficients, 0);
    }
         
    public ParticleSwarm(int nparticles, double max_velocity, Function f, double rp){
        this(nparticles, max_velocity, f, new double[] {0.7, 2.0, 2.0}, rp);
    }
            
    public ParticleSwarm(int nparticles, double max_velocity, Function f, double[] coefficients, double rp) {
        this.f = f;
        this.rp = rp;
        this.vars = f.getVars();
        for (int i = 0; i < nparticles; i++) {
            
            double[] position = new double[vars];
            double[] velocity = new double[vars];
            for (int j = 0; j < vars; j++){
                position[j] = randomDouble(-1000.0, 1000.0);
                velocity[j] = randomDouble(-1000.0, 1000.0);
            }
            double[] r = {rand.nextDouble(), rand.nextDouble()};
            particles.add(new Particle(position, velocity, f.value(position), max_velocity, r, coefficients));
        }
    }

    private double[] update() {
        double fBest = f.value(particles.get(0).getPoint());
        double[] gBest = particles.get(0).getPoint();

        for (Particle p : particles) {
            double[] getpoint = p.getPoint();
            double pValue = f.value(getpoint);
            
            //System.out.println(p);
            
            for (double d : f.constraint(getpoint)){
                pValue += rp*d;
            }

            if (pValue < fBest) {
                fBest = pValue;
                gBest = p.getPoint();
            }
        }

        final double[] g = gBest;
        particles.parallelStream().forEach(x -> x.updatePosition(g, f, rp));

        return gBest;
    }

    public double[] run(double minTolerance, int maxIterations) {
        double currentTolerance = minTolerance + 1.0;
        currentIterations = 0;
        double[] previousgBest = update();
        double[] currentgBest = {0, 0};

        while (currentTolerance > minTolerance && currentIterations < maxIterations - 1) {
            currentgBest = update();
            currentTolerance = 0.0;
            
            //System.out.printf("Current Best Point: %.2f %.2f Value: %.2f\n",currentgBest[0], currentgBest[1], f.value(currentgBest));
            
            for (int i = 0; i < this.vars; i++) {
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
        System.out.printf("Best Point: %f %f Value: %f\n", currentgBest[0], currentgBest[1], f.value(currentgBest));
        //System.out.printf("Constraint: %f\n", f.constraint(currentgBest));
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
