/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.bearing.phase;

import pubsim.Complex;

/**
 *
 * @author Robby McKilliam
 */
public class ArgumentOfComplexMean implements PhaseEstimator{

    private int n;

    public void setSize(int n) {
        this.n = n;
    }

    public double estimatePhase(Complex[] y) {
        n = y.length;

        double real = 0.0, imag = 0.0;
        for(int i = 0; i < n; i++){
            Complex c = y[i].times(1.0 / y[i].abs() );
            real += c.re();
            imag += c.im();
        }

        return Math.atan2(imag, real)/(2*Math.PI);

    }

}
