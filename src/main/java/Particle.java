/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Benny
 */
//Particle.java
import java.lang.Math;

public class Particle implements Point {

    private int dim;
    private double[] position;
    private double[] previousVelocity;
    private double[] bestPosition;
    private double maxVelocity;
    private double bestValue;
    private double[] r;

    public Particle(double[] position, double[] previousVelocity, double best_value, double max_velocity, double[] r) {
        this.position = position;
        dim = this.position.length;
        this.bestPosition = position;
        this.bestValue = best_value;
        this.previousVelocity = previousVelocity;
        this.maxVelocity = max_velocity;
        this.r = r;
    }

    public void updatePosition(double[] gbest, Function f, double rp) {
        double[] velocity = {0, 0};

        for (int i = 0; i < dim; i++) {
            velocity[i] = 0.7 * this.previousVelocity[i] + 2 * r[0] * (bestPosition[i] - position[i]) + 2 * r[1] * (gbest[i] - position[i]);
        }

        double norm = 0;
        for (double v : velocity) {
            norm += v * v;
        }
        norm = Math.sqrt(norm);

        if (norm > 100.0) {
            for (double v : velocity) {
                v = (v / norm) * 100.0;
            }
        }

        for (int i = 0; i < dim; i++) {
            position[i] += velocity[i];
            this.previousVelocity = velocity;
        }

        double fValue = f.q(position) + rp * f.penalty(position);
        if (fValue < bestValue) {
            bestValue = fValue;
            bestPosition = position;
        }

    }

    public double getBestValue() {
        return bestValue;
    }

    @Override
    public double[] getPoint() {
        return position;
    }

    @Override
    public String toString() {
        return String.format("pos x: %f y: %f prev_velocity x: %f y: %f", position[0], position[1], previousVelocity[0], previousVelocity[1]);
    }

}
