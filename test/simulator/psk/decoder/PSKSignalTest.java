/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.psk.decoder;

import junit.framework.TestCase;
import simulator.Complex;
import simulator.NoiseGenerator;
import simulator.VectorFunctions;

/**
 *
 * @author robertm
 */
public class PSKSignalTest extends TestCase {
    
    public PSKSignalTest(String testName) {
        super(testName);
    }            

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of generateReceivedSignal method, of class PSKSignal.
     */
    public void testGenerateReceivedSignal() {
        System.out.println("generateReceivedSignal");
      
        double[] xr ={1.0, 2.0, 3.0};
        int M = 4;
        
        PSKSignal instance = new PSKSignal();
        instance.setM(4);
        instance.setChannel(-0.4326, -1.6656);
        
        NoiseGenerator noise = new simulator.UniformNoise(0.0, 0.0);
        instance.setNoiseGenerator(noise);  
               
        instance.setPSKSignal(xr);
        
        //Output taken from Matlab
        Complex[] expr = {  new Complex(1.6656, -0.4326), 
                            new Complex(0.4326, 1.6656), 
                            new Complex(-1.6656, 0.4326)};
        instance.generateReceivedSignal();
        
        System.out.println(VectorFunctions.print(instance.getReceivedSignal()));
        
        assertEquals(true, VectorFunctions.distance_between2(expr,instance.getReceivedSignal())<0.0001);

    }

}