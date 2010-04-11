/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.bearing;

import distributions.circular.WrappedUniform;
import simulator.NoiseVector;
import simulator.SignalGenerator;
import static simulator.Util.fracpart;

/**
 * Simulates a quatised timer for delay estimation with incomplete data.
 * The assumes that the period of the signal T = 1.
 * @author Robby McKilliam
 */
public class QuantisedDelaySignal extends NoiseVector implements SignalGenerator{

    protected double delay = 0.0;
    private double P;

    public void setDelay(double delay){
        this.delay = delay;
    }

    public double getDelay(){
        return delay;
    }

    public void setClockPeriod(double P){
        this.P = P;
    }

    public double getClockPeriod(){
        return P;
    }

    /**
     * Generate the iid noise of length n.
     * Here, the noise generator is expected to be a discrete distribution
     * that generates the sparse u
     */
    @Override
    public double[] generateReceivedSignal(){
        if( iidsignal.length != n )
            iidsignal = new double[n];

        //WrappedUniform.Mod1 cnoise = new WrappedUniform.Mod1();
        //cnoise.setRange(P);

        double usum = noise.getNoise();
        //double usum = 0;
        for(int i = 0; i < n; i++){
            double yn = Math.round((usum + delay)/P) * P;
            iidsignal[i] = fracpart(yn);
            //iidsignal[i] = fracpart(delay + cnoise.getNoise());
            usum += noise.getNoise();
            //System.out.println(usum);
        }
        return iidsignal;
    }
}