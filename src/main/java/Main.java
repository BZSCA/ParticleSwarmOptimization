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
        
        final long startTime = System.currentTimeMillis();
        ParticleSwarm particleswarm = new ParticleSwarm(10000, 100.0, fb, 0);
        particleswarm.run(0.000001, 400);
        final long endTime = System.currentTimeMillis();

        System.out.println("Total execution time: " + (endTime - startTime));

    }

}
