/*
 * UniformNoise.java
 *
 * Created on 7 May 2007, 12:40
 */

package simulator;

import java.util.Random;

/**
 *
 * @author Robby
 */
public class UniformNoise implements NoiseGenerator {
    
    private double mean;
    private double variance;
    private double stdDeviation;
    private double range;
    private Random random;
    
    /** Creates a new instance of UniformNoise with specific variance and mean */
    public UniformNoise(double mean , double variance) {
        this.mean = mean;
        this.variance = variance;
        stdDeviation = Math.sqrt(variance);
        range = 2.0 * Math.sqrt( 3.0 * variance );
        random = new Random();
    }
    
    /** Creates UniformNoise with mean = 0.0 and variance = 1.0 */
    public UniformNoise() {
        mean = 0.0;
        variance = 1.0;
        stdDeviation = 1.0;
        range = 2.0 * Math.sqrt( 3.0 * variance );
        random = new Random();
    }
    
    public void setMean(double mean){ this.mean = mean; }
    public void setVariance(double variance){
        this.variance = variance;
        stdDeviation = Math.sqrt(variance);
    }
    
    /** Returns a uniformly distributed value */
    public double getNoise(){
        return mean + range * (random.nextDouble() - 0.5);
    }
    
}