/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.psk.decoder;

import robbysim.psk.decoder.PSKSignal;
import robbysim.psk.decoder.KnownCSIReciever;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import robbysim.distributions.RandomVariable;
import robbysim.VectorFunctions;
import static org.junit.Assert.*;

/**
 *
 * @author Robby
 */
public class KnownCSIRecieverTest {

    public KnownCSIRecieverTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of decode method, of class KnownCSIReciever.
     */
    @Test
    public void decode() {
        System.out.println("decode");
        
        int iters = 1000000;
        int M = 3;
        int T = 8;
        
        PSKSignal signal = new PSKSignal();
        signal.setM(M);
        signal.setLength(T);
        //signal.setChannel(-1.0,-1.0);
        signal.generateChannel();
        
        RandomVariable noise = new robbysim.distributions.UniformNoise(0.0, 0.01);
        signal.setNoiseGenerator(noise);  
               
        signal.generateReceivedSignal();
        
        KnownCSIReciever instance = new KnownCSIReciever(M);
        //instance.setM(M);
        instance.setT(T);
        instance.setChannel(signal.getChannel());
        
        for(int i = 0; i < iters; i++){
            signal.generateChannel();
            signal.generatePSKSignal();
            signal.generateReceivedSignal();
            
            instance.setChannel(signal.getChannel());
            double[] res = instance.decode(signal.getReceivedSignal());
            double[] exp = signal.getPSKSignal();
            
            //System.out.println("res = " + VectorFunctions.print(res));
            //System.out.println("exp = " + VectorFunctions.print(exp));
        
            assertFalse(instance.codewordError(exp));
            
        }
    }

}