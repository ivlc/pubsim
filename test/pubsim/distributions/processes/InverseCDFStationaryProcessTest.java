/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.distributions.processes;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pubsim.VectorFunctions;
import static org.junit.Assert.*;
import pubsim.distributions.RandomVariable;
import pubsim.distributions.UniformNoise;
import pubsim.distributions.circular.WrappedUniform;

/**
 *
 * @author mckillrg
 */
public class InverseCDFStationaryProcessTest {
    
    public InverseCDFStationaryProcessTest() {
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
     * Test of getNoise method, of class InverseCDFStationaryProcess.
     */
    @Test
    public void testGetNoiseWithUniform() {
        System.out.println("getNoise");
        double[] filter = {1.0, 0.25, 0.25};
        InverseCDFStationaryProcess instance = new InverseCDFStationaryProcess(new UniformNoise(0,1.0/20), filter);
        
        int N = 100000;
        double[] X = new double[N];
        for(int i = 0; i < N; i++) X[i] = instance.getNoise();
        
        System.out.println("mean = " + VectorFunctions.mean(X));
        
        for(int k = 0; k < filter.length + 4; k++){
            double Ak = 0;
            for(int i = 0; i < N-k; i++) Ak += X[i]*X[i+k];
            Ak/=(N-k);
            System.out.println("A"+k+" = " + Ak);
        }
        
    }
    
        /**
     * Test of getNoise method, of class InverseCDFStationaryProcess.
     */
    @Test
    public void testGetNoiseWithWrappedUniform() {
        System.out.println("getNoise");
        double[] filter = {1.0, 0.25, 0.25};
        InverseCDFStationaryProcess instance = new InverseCDFStationaryProcess(new WrappedUniform(0,1.0/20.0), filter);
        
        int N = 100000;
        double[] X = new double[N];
        for(int i = 0; i < N; i++) X[i] = instance.getNoise();
        
        System.out.println("mean = " + VectorFunctions.mean(X));
        
        for(int k = 0; k < filter.length + 4; k++){
            double Ak = 0;
            for(int i = 0; i < N-k; i++) Ak += X[i]*X[i+k];
            Ak/=(N-k);
            System.out.println("A"+k+" = " + Ak);
        }
        
    }
    
}