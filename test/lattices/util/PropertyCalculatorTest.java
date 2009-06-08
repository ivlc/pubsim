/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.util;

import lattices.AnFastSelect;
import lattices.Dn;
import lattices.Phin2StarZnLLS;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Robby McKilliam
 */
public class PropertyCalculatorTest {

    public PropertyCalculatorTest() {
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

    int samples = 10000000;


    /**
     * Test of properties for Zn lattice
     */
//    @Test
//    public void testZn() {
//        System.out.println("testZn");
//        int N = 3;
//        PropertyCalculator prop = new PropertyCalculator(new Zn(N), samples);
//        assertEquals(N*0.5*0.5, prop.outRadius()*prop.outRadius(), 0.0001);
//        assertEquals(1.0/12.0, prop.dimensionalessSecondMoment(), 0.001);
//
//    }
//
    /**
     * Test of properties for Zn lattice
     */
//    @Test
//    public void testAn() {
//        System.out.println("testAn");
//        int N =8;
//        PropertyCalculator prop = new PropertyCalculator(new AnFastSelect(N), samples);
//
//
//        double I =  (N/12.0 + N/(6.0*(N+1)))/Math.pow(N+1, 1.0/N);
//        double G = I/N;
//        //assertEquals(G, prop.dimensionalessSecondMoment(), 0.001);
//        System.out.println(G);
//
//         System.out.println(prop.dimensionalessSecondMoment());
//        System.out.println(prop.coveringRadius());
//
//    }
////
//        /**
//     * Test of properties for Zn lattice
//     */
//    @Test
//    public void testDn() {
//        System.out.println("testDn");
//        int N = 8;
//        PropertyCalculator prop = new PropertyCalculator(new Dn(N), samples);
//
//        double I =  N/12.0 + 1/(2.0*(N+1));
//        double G = I/Math.pow(2, 2.0/N)/N;
//        //assertEquals(G, prop.dimensionalessSecondMoment(), 0.001);
//        System.out.println(G);
//
//         System.out.println(prop.dimensionalessSecondMoment());
//        System.out.println(prop.coveringRadius());
//
//    }
//
//
//            /**
//     * Test of properties for Zn lattice
//     */
//    @Test
//    public void test2Dn() {
//        System.out.println("test2Dn");
//        int N = 4;
//        //construct a lattice 2Dn
//        Matrix B = (new Dn(N)).getGeneratorMatrix().times(0.5);
//        LatticeAndNearestPointAlgorithm L
//                = new GeneralLatticeAndNearestPointAlgorithm(B);
//
//        PropertyCalculator prop = new PropertyCalculator(L, samples);
//        //assertEquals(prop.outRadius()*prop.outRadius(), 5*0.5*0.5, 0.00001);
//
//        double I =  N/12.0 + 1/(2.0*(N+1));
//        //assertEquals(I, prop.normalisedSecondMoment(), 0.001);
//
//        double G = I/Math.pow(2, 2.0/N)/N;
//        assertEquals(G, prop.dimensionalessSecondMoment(), 0.001);
//
//    }

    /**
     * Test of properties for Zn lattice
     */
    @Test
    public void testPhi2Star() {
        System.out.println("testPhi2Star");
        int N = 12;
        //construct a lattice 2Dn

        PropertyCalculator prop = new PropertyCalculator(new Phin2StarZnLLS(N), samples, true);
        //assertEquals(prop.outRadius()*prop.outRadius(), 5*0.5*0.5, 0.00001);

        System.out.println(prop.dimensionalessSecondMoment());
        System.out.println(prop.coveringRadius());

    }

}