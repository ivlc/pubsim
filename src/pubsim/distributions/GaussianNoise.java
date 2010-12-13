

/*
 * GaussianNoise.java
 *
 * Created on 13 April 2007, 15:40
 */

package pubsim.distributions;

import rngpack.Ranlux;

/**
 * Creates single gaussian variables
 * @author Robby McKilliam
 */
public class GaussianNoise extends AbstractRandomVariable implements RandomVariable {

    protected final double mean;
    protected final double stdDeviation;
    protected final double variance;
    
    /** Creates a new instance of GaussianNoise with specific variance and mean */
    public GaussianNoise(double mean, double variance){
        this.mean = mean;
        this.variance = variance;
        this.stdDeviation = Math.sqrt(variance);
    }

    public double getMean(){ return mean; }

    public double getVariance(){ return variance; }
    
    /** Returns an instance of gaussian noise */
    @Override
    public double getNoise(){
        return stdDeviation * random.gaussian() + mean;
    }

    /** Return the Gaussian pdf */
    public double pdf(double x) {
        double s = 1.0/Math.sqrt(2*Math.PI*getVariance());
        double d = x - getMean();
        return s * Math.exp( -(d*d)/(2*getVariance()) );
    }

    /** Return the cdf of the Gaussian evaluated at x */
    @Override
    public double cdf(double x){
        //just using the Q function from util.
        return 0.5*(1 + pubsim.Util.erf((x - mean)/stdDeviation/Math.sqrt(2)));
    }


}
