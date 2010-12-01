/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.Anstar;

import pubsim.lattices.Anstar.AnstarBucketWithArray;
import pubsim.lattices.Anstar.AnstarVaughan;
import java.util.Random;
import junit.framework.TestCase;
import pubsim.VectorFunctions;

/**
 *
 * @author Robby
 */
public class AnstarBucketWithArrayTest extends TestCase {
    
    public AnstarBucketWithArrayTest(String testName) {
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
     * Test of nearestPoint method, of class AnstarBucketWithArray.
     */
    public void testNearestPoint() {
        System.out.println("nearestPoint");
        
        int n = 1000;
        Random rand = new Random();
        double[] y = new double[n];
        double[] v_instance = null;
        double[] v_tester = null;
        double[] x = new double[n];
        AnstarBucketWithArray instance = new AnstarBucketWithArray();
        AnstarVaughan tester = new AnstarVaughan();
        
        instance.setDimension(n - 1);
        tester.setDimension(n - 1);
        for(int i=0; i<50; i++){
            for(int k = 0; k < n; k++){
                y[k] = ( rand.nextGaussian() - 0.5 )*10.0;
            }
            instance.nearestPoint(y);
            tester.nearestPoint(y);
            v_instance = instance.getLatticePoint();
            v_tester = tester.getLatticePoint();
            AnstarVaughan.project(y,x);
            System.out.println(VectorFunctions.distance_between(v_instance, v_tester));
            assertEquals(VectorFunctions.distance_between(v_instance, v_tester) < 0.00001, true);
        }
        
        //this is actually a test for the matlab code sent to Warren Smith
        double[] yMTest = {1.1393, 10.6677, 0.5928, -0.9565, -8.3235};
        double[] Mres = {0.2000, 10.2000, 0.2000, -1.8000, -8.8000};
        instance.nearestPoint(yMTest);
        assertEquals(VectorFunctions.distance_between(Mres,  instance.getLatticePoint()) < 0.00001, true);
    }

}
