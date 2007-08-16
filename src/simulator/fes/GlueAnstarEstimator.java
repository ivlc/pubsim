/*
 * GlueAnstarEstimator.java
 *
 * Created on 12 August 2007, 12:28
 */

package simulator.fes;

import simulator.PnoneGlued;
import simulator.VectorFunctions;

/**
 * Frequency estimator that uses Pn1 glue vector algorithm to solve the nearest
 * point problem for the frequency estimation lattice Pn1.  O(n^4log(n)).
 * @author Robby
 */
public class GlueAnstarEstimator extends PnoneGlued implements FrequencyEstimator {
    
    protected double[] x, y;
    protected int N;
    
    /** Set the number of samples */
    public void setSize(int n){
        setDimension(n-2);  
        x = new double[n];
        y = new double[n];
        N = n;
    }
    
    /** Run the estimator on recieved data, @param y */
    public double estimateFreq(double[] real, double[] imag){
        if(n+2 != real.length)
            setSize(real.length);
        
        for(int i = 0; i < real.length; i++)
            y[i] = Math.atan(imag[i]/real[i])/Math.PI;
        
        project(y, x);
        nearestPoint(x);
        
        
        System.out.println("y antan = " + VectorFunctions.print(y));
        System.out.println("v = " + VectorFunctions.print(v));
        System.out.println("u = " + VectorFunctions.print(u));
        
        
        //calculate f from the nearest point
        double f = 0;
        double sumn = N*(N+1)/2;
        double sumn2 = N*(N+1)*(2*N+1)/6;
        for(int i = 0; i < N; i++)
            f += (N*(i+1) - sumn)*(y[i]-u[i]);
        
        f /= (sumn2*N - sumn*sumn);
        f /= 2;
        
        System.out.println("f = " + f);
        
        return f;
        
    }
    
}
