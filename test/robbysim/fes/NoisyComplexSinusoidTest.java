/*
 * NoisyComplexSinusoidTest.java
 * JUnit based test
 *
 * Created on 9 August 2007, 13:27
 */

package robbysim.fes;

import robbysim.fes.NoisyComplexSinusoid;
import junit.framework.*;
import robbysim.SignalGenerator;
import robbysim.distributions.UniformNoise;
import robbysim.distributions.RandomVariable;
import java.util.Random;

/**
 *
 * @author Robby
 */
public class NoisyComplexSinusoidTest extends TestCase {
    
    public NoisyComplexSinusoidTest(String testName) {
        super(testName);
    }

    /**
     * Test of generateReceivedSignal method, of class simulator.fes.NoisyComplexSinusoid.
     */
    public void testGenerateReceivedSignal() {
        System.out.println("generateReceivedSignal");
        
        double f = 1.0;
        double rate = 6.0;
        double p = 0.0;
        int n = 20;
        
        NoisyComplexSinusoid instance = new NoisyComplexSinusoid(f, rate, p, n);
        UniformNoise noise = new UniformNoise(0.0, 1.0/24.0);
        instance.setNoiseGenerator(noise);
        
        instance.generateReceivedSignal();
        
        for(int i = 0; i < n; i++){
            boolean less = (Math.abs( Math.cos(i*2.0*Math.PI*f/rate + p) - instance.getReal()[i] ) <= 0.5)
                            && ( Math.abs( Math.sin(i*2.0*Math.PI*f/rate + p) - instance.getImag()[i] ) <= 0.5);      
            assertEquals(less, true);
            
        }
        
    }
    
}