/*
 * NoiseVector.java
 *
 * Created on 5 November 2007, 13:24
 */

package simulator;

import distributions.RandomVariable;

/**
 * Generates a vector of iid noise
 * @author Robby McKilliam
 */
public class NoiseVector implements SignalGenerator{
    
    protected int n;
    protected double[] iidsignal;
    protected RandomVariable noise;
    
    /** Default constructor set length of vector to 1 */ 
    public NoiseVector(){
        n = 1;
        iidsignal = new double[1];
    }

    /** Default constructor set length of vector to 1 */
    public NoiseVector(RandomVariable noise, int length){
        setNoiseGenerator(noise);
        setLength(length);
    }
    
    /** {@inheritDoc} */
    public void setLength(int n){
        this.n = n;
        iidsignal = new double[n];
    }
    
    /** {@inheritDoc} */
    public int getLength() { return n; }
    
    public void setNoiseGenerator(RandomVariable noise){
        this.noise = noise;
    }
    
    public RandomVariable getNoiseGenerator(){
        return noise;
    }
    
    /** 
     * Generate the iid noise of length n.
     */
    public double[] generateReceivedSignal(){
        if( iidsignal.length != n )
            iidsignal = new double[n];
        for(int i = 0; i < n; i++)
            iidsignal[i] = noise.getNoise();
        return iidsignal;
    }
    
}
