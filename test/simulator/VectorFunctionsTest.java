/*
 * VectorFunctionsTest.java
 * JUnit based test
 *
 * Created on 28 June 2007, 14:41
 */

package simulator;

import Jama.Matrix;
import junit.framework.*;
import javax.vecmath.GVector;
import java.util.Random;
import static org.junit.Assert.*;

/**
 *
 * @author Robby McKilliam
 */
public class VectorFunctionsTest extends TestCase {
    
    public VectorFunctionsTest(String testName) {
        super(testName);
    }

    /**
     * Asserts that two vector have equal elements.
     * @param tol tolerance for how close doubles must be to each other
     * to be considered equal
     */
    private void assertVectorsEqual(double[] x, double[] y, double tol){
        if(x.length != y.length)
            fail("Vectors are not the same length!");
        for(int i = 0; i < x.length; i++){
            assertEquals(x[i], y[i], tol);
        }
    }

    /** 
     * Asserts that two vector have equal elements. Sets a
     * default tolerance of  0.0000001.
     */
    private void assertVectorsEqual(double[] x, double[] y){
        assertVectorsEqual(x, y, 0.0000001);
    }

    /**
     * Test of slowFT method, of class simulator.VectorFunctions.
     */
    public void testSlowFT() {
        System.out.println("slowFT");
        
        double[] x = {2, 3, 1, 4};
        double[] Xi = new double[x.length];
        double[] Xr = new double[x.length];
        double[] expXi = {0, 1, 0, -1};
        double[] expXr = {10,1,-4 ,1};
        
        VectorFunctions.slowFT(x, Xi, Xr);   
        
        for(int i = 0; i < x.length; i++){
            assertEquals(true, Math.abs(expXi[i] - Xi[i]) < 0.00001);
            assertEquals(true, Math.abs(expXr[i] - Xr[i]) < 0.00001);
        }
       
    }

    /**
     * Test of abs2FT method, of class simulator.VectorFunctions.
     */
    public void testAbs2FT() {
        System.out.println("abs2FT");
        
        double[] x = {2, 3, 1, 4};
        
        double[] expResult = {100.0000, 2.0000, 16.0000, 2.0000};
        double[] result = VectorFunctions.abs2FT(x);
        assertEquals(true, VectorFunctions.distance_between(expResult, result) < 0.00001);
       
    }

    /**
     * Test of distance_between method, of class simulator.VectorFunctions.
     */
    public void testDistance_between() {
        System.out.println("distance_between");
        
        double[] x = {0, 1, 3};
        double[] s = {0, 4, 1};
        
        double expResult = Math.sqrt(13.0);
        double result = VectorFunctions.distance_between(x, s);
        assertEquals(expResult,result);
  
    }

    /**
     * Test of distance_between method, of class simulator.VectorFunctions.
     */
    public void testFirstDifference() {
        System.out.println("testFirstDifference");

        double[] x = {0, 1, 3};

        double[] expResult = {1, 2};
        double[] result = VectorFunctions.firstDifference(x);
        assertTrue(VectorFunctions.distance_between(result, expResult) < 0.00000001);

    }

    /**
     * Test of distance_between method, of class simulator.VectorFunctions.
     */
    public void testmthDifference() {
        System.out.println("testmthDifference");

        //test the second difference
        double[] x = {0, 1, 3, 2};
        //double[] expResult = {1, 2, -1};
        double[] expResult = {1, -3};
        
        double[] result = VectorFunctions.mthDifference(x, 2);
        assertTrue(VectorFunctions.distance_between(result, expResult) < 0.00000001);

    }

    /**
     * Test of angle_between method, of class simulator.VectorFunctions.
     */
    public void testAngle_between() {
        System.out.println("angle_between");
        
        double[] x = {0,1};
        double[] y = {1,1};
        
        double expResult = Math.PI/4;
        double result = VectorFunctions.angle_between(x, y);
        assertEquals(true, Math.abs(expResult - result) < 0.000001);

    }

    /**
     * Test of subtract method, of class simulator.VectorFunctions.
     */
    public void testSubtract() {
        System.out.println("subtract");
        
        double[] x = {1,2,3};
        double[] y = {1,-1,-1};
        
        double[] expResult = {0,3,4};
        double[] result = VectorFunctions.subtract(x, y);
        assertEquals(0.0, VectorFunctions.distance_between(expResult,result));
    }

    /**
     * Test of add method, of class simulator.VectorFunctions.
     */
    public void testAdd() {
        System.out.println("add");
        
        double[] x = {1,2,3};
        double[] y = {-1,1,1};
        
        double[] expResult = {0,3,4};
        double[] result = VectorFunctions.add(x, y);
        assertEquals(0.0, VectorFunctions.distance_between(expResult,result));
       
    }

    /**
     * Test of sum method, of class simulator.VectorFunctions.
     */
    public void testSum() {
        System.out.println("sum");
        
        double[] x = {1,1,4};
        
        double expResult = 6;
        double result = VectorFunctions.sum(x);
        assertEquals(expResult, result);
       
    }

    /**
     * Test of sum2 method, of class simulator.VectorFunctions.
     */
    public void testSum2() {
        System.out.println("sum2");
        
        double[] x = {1,2,3};
        
        double expResult = 14;
        double result = VectorFunctions.sum2(x);
        assertEquals(expResult, result);

    }

    /**
     * Test of mean method, of class simulator.VectorFunctions.
     */
    public void testMean() {
        System.out.println("mean");
        
        double[] x = {1,2,3};
        
        double expResult = 2;
        double result = VectorFunctions.mean(x);
        assertEquals(expResult, result);

    }

    /**
     * Test of magnitude method, of class simulator.VectorFunctions.
     */
    public void testMagnitude() {
        System.out.println("magnitude");
        
        double[] x = {1,2,3};
        
        double expResult = Math.sqrt(14.0);
        double result = VectorFunctions.magnitude(x);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of max method, of class simulator.VectorFunctions.
     */
    public void testMax() {
        System.out.println("max");
        
        double[] x = {1,4,2,3,-5};
        
        double expResult = 4.0;
        double result = VectorFunctions.max(x);
        assertEquals(expResult, result);
    }

    /**
     * Test of increasing method, of class simulator.VectorFunctions.
     */
    public void testIncreasing() {
        System.out.println("increasing");
        
        double[] x1 = {1,4,2,3,-5};
        double[] x2 = {1,2,4,7};
        
        assertEquals(false, VectorFunctions.increasing(x1));
        assertEquals(true, VectorFunctions.increasing(x2));
       
    }

    /**
     * Test of dot method, of class simulator.VectorFunctions.
     */
    public void testDot() {
        System.out.println("dot");
        
        double[] x = {1,2,3};
        double[] y = {1,-1,4};
        
        double expResult = 11.0;
        double result = VectorFunctions.dot(x, y);
        assertEquals(expResult, result);
       
    }

    /**
     * Test of min method, of class simulator.VectorFunctions.
     */
    public void testMin() {
        System.out.println("min");
        
        double[] x = {-1.0, -11.0, -11.0, 1.0, 2.0, 9.0, 1.0};
        
        double expResult = -11.0;
        double result = VectorFunctions.min(x);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of maxDistance method, of class simulator.VectorFunctions.
     */
    public void testMax_distance() {
        System.out.println("max_distance");
        
        double[] x = {-1.0, -11.0, -11.0, 1.0, 2.0, 9.0, 1.0};
        
        double expResult = 20.0;
        double result = VectorFunctions.maxDistance(x);
        assertEquals(expResult, result);
    }

    /**
     * Test of min method, of class simulator.VectorFunctions.
     */
    public void testgetSubVector() {
        System.out.println("subVector");

        int start = 1;
        int end = 4;
        double[] x = {-1.0, -11.0, -11.0, 1.0, 2.0, 9.0, 1.0};
        double[] exp = {-11.0, -11.0, 1.0, 2.0 };

        double[] res = VectorFunctions.getSubVector(x, start, end);
        System.out.println(VectorFunctions.print(res));
        assertVectorsEqual(exp, res);

    }

    /**
     * Test of min method, of class simulator.VectorFunctions.
     */
    public void testFillVector() {
        System.out.println("fillVector");

        int start = 1;
        double[] x = {-1.0, -11.0, -11.0, 1.0, 2.0, 9.0, 1.0};
        double[] f = {3.0, 3.0, 3.0 };
        double[] exp = {-1.0, 3.0, 3.0, 3.0, 2.0, 9.0, 1.0};

        VectorFunctions.fillVector(x, f, start);
        //System.out.println(VectorFunctions.print(x));
        assertVectorsEqual(exp, x);

        int start1 = 4;
        double[] x1 = {-1.0, -11.0, -11.0, 1.0, 2.0, 9.0, 1.0};
        double[] f1 = {3.0, 3.0, 3.0, 3.0 };
        double[] exp1 = {-1.0, -11.0, -11.0, 1.0, 3.0, 3.0, 3.0};

        VectorFunctions.fillVector(x1, f1, start1);
        //System.out.println(VectorFunctions.print(x1));
        assertVectorsEqual(exp1, x1);

        int start2 = 3;
        Double[] x2 = new Double[20];
        Double[] exp2 = new Double[20];
        Double[] f2 = new Double[3];
        for(int i = 0; i < f.length; i++){
            f2[i] = new Double(1.0);
            exp2[i+start2] = new Double(1.0);
        }
        VectorFunctions.fillVector(x2, f2, start2);
        assertArrayEquals(exp2, x2);

    }

        /**
     * Test of min method, of class simulator.VectorFunctions.
     */
    public void testFillEnd() {
        System.out.println("fillVector");

        int start = 1;
        double[] x = {-1.0, -11.0, -11.0, 1.0, 2.0, 9.0, 1.0};
        double[] f = {3.0, 3.0, 3.0 };
        double[] exp =  {-1.0, -11.0, -11.0, 1.0, 3.0, 3.0, 3.0};

        VectorFunctions.fillEnd(x, f);
        System.out.println(VectorFunctions.print(x));
        assertVectorsEqual(exp, x);

    }
    
    /**
     * Test of increasing method, of class simulator.VectorFunctions.
     */
    public void testConv() {
        System.out.println("conv");
        
        double[] x1 = {1, -1};
        double[] x2 = {1, -1};
        double[] exp = {1, -2, 1};
        
        double[] res = VectorFunctions.conv(x1, x2);
        System.out.println(VectorFunctions.print(res));
        assertEquals(true, VectorFunctions.distance_between2(exp, res) < 0.00001);
        
        double[] x12 = {1,-1, 2};
        double[] x22 = {1,-1, 3, 4};
        double[] exp2 = {1, -2, 6, -1, 2, 8};
        
        double[] res2 = VectorFunctions.conv(x12, x22);
        System.out.println(VectorFunctions.print(res2));
        assertEquals(true, VectorFunctions.distance_between2(exp2, res2) < 0.00001);
       
    }
    
    /**
     * Test of maxDistance method, of class simulator.VectorFunctions.
     */
    public void testMatrixMultVector() {
        System.out.println("matrixMultVector");
        
        double[] x = {1, 2, 3};
        double[][] M = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}};
        double[] y = new double[M.length];
        
        double[] expResult =  {14, 32, 50, 68};
        VectorFunctions.matrixMultVector(M, x, y);
        assertEquals(true, 
                VectorFunctions.distance_between(expResult,y)<0.00001);
    }
    
    /**
     * This tests some of the Gvectorfunctions.
     */
    public void testGVector() {
        System.out.println("testGVector");
        
        Random rand = new Random();
        
        double[] x = new double[1000];
        double[] y = new double[1000];
        
        for (int i = 0; i < x.length; i++){
            x[i] = rand.nextGaussian();
            y[i] = rand.nextGaussian();
        }
        
        GVector gx = new GVector(x);
	GVector gy = new GVector(y);
        
        assertEquals(VectorFunctions.sum2(x), gx.normSquared());
        assertEquals(VectorFunctions.sum2(y), gy.normSquared());
        assertEquals(VectorFunctions.dot(x,y), gx.dot(gy));
        
    }
    
    /**
     * Test of min method, of class simulator.VectorFunctions.
     */
    public void testStableDet() {
        System.out.println("stableDet");
        
        int m = 10;
        
        Matrix mat = Matrix.identity(m,m);
        assertEquals(true, Math.abs(mat.det() - VectorFunctions.stableDet(mat)) < 0.00001);
        
        mat = Matrix.random(m,m);
        assertEquals(true, Math.abs(mat.det() - VectorFunctions.stableDet(mat)) < 0.00001);

    }
    
        /**
     * Test of min method, of class simulator.VectorFunctions.
     */
    public void testSwapColumns() {
        System.out.println("testSwapColumns");
        
        Matrix mat = Matrix.identity(4, 3);
        Matrix matCopy = mat.copy();
        VectorFunctions.swapColumns(mat, 0,1);
        
        //System.out.println(VectorFunctions.print(mat));
        //System.out.println(VectorFunctions.print(matCopy));
        
        int j0 = 0;
        int j1 = 1;
        for(int i = 0; i < mat.getRowDimension(); i++){
            assertEquals(mat.get(i, j0), matCopy.get(i, j1));
            assertEquals(mat.get(i, j1), matCopy.get(i, j0));
        }
        
    }
    
    /**
     * Test of min method, of class simulator.VectorFunctions.
     */
    public void testSwapRows() {
        System.out.println("testSwapRows");
        
        Matrix mat = Matrix.identity(3, 4);
        Matrix matCopy = mat.copy();
        VectorFunctions.swapRows(mat, 0,1);
        
        System.out.println(VectorFunctions.print(mat));
        System.out.println(VectorFunctions.print(matCopy));
        
        int j0 = 0;
        int j1 = 1;
        for(int i = 0; i < mat.getColumnDimension(); i++){
            assertEquals(mat.get(j0, i), matCopy.get(j1, i));
            assertEquals(mat.get(j1, i), matCopy.get(j0, i));
        }
        
    }
    
    /**
     * Test of min method, of class simulator.VectorFunctions.
     */
    public void testGivensRotation() {
        System.out.println("testGivensRotation");
        
        Matrix M = Matrix.random(5, 5);
        double pdet = M.det();
        
        System.out.println(VectorFunctions.print(M));
        
        
        int m1 = 0, m2 = 1;
        int n = 0;
        VectorFunctions.givensRotate(M, m1, m2, n);
        
         System.out.println(VectorFunctions.print(M));
        
        assertTrue(Math.abs(M.get(m2, n)) < 0.00000001);
        assertTrue(Math.abs(M.det() - pdet)< 0.00000001);
        
    }

    public void testPackRowiseToMatrix(){
        System.out.println("packRowiseToMatrix");
        double[] y = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        double[][] testu = { {1, 2, 3, 4, 5}, {6, 7, 8, 9, 10}};
        double[][] u = VectorFunctions.packRowiseToMatrix(y, 2);

        for(int m = 0; m < 2; m++){
            for(int n = 0; n < 5; n++){
                assertEquals(testu[m][n], u[m][n], 0.0000001);
            }
        }

    }

    public void testunpackRowise(){
        System.out.println("testunpackRowise");
        double[][] testB = { {1, 2, 3, 4, 5}, {6, 7, 8, 9, 10}};
        double[] expres = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Matrix B = Matrix.constructWithCopy(testB);
        double[] res = VectorFunctions.unpackRowise(B);
        for(int n = 0; n < res.length; n++){
            assertEquals(res[n],expres[n] , 0.0000001);
        }

    }

    public void testunpackColumnwise(){
        System.out.println("testunpackColumnwise");
        double[][] testB = { {1, 2, 3, 4, 5}, {6, 7, 8, 9, 10}};
        double[] expres = { 1, 6, 2, 7, 3, 8, 4, 9, 5, 10};
        Matrix B = Matrix.constructWithCopy(testB);
        double[] res = VectorFunctions.unpackColumnwise(B);
        for(int n = 0; n < res.length; n++){
            assertEquals(res[n],expres[n] , 0.0000001);
        }

    }

    public void testrandomBandedMatrix(){
        System.out.println("testrandomBandedMatrix");
        int m = 10; int n = 8;
        int rb = 2; int cb = 2;
        Matrix M = VectorFunctions.randomBandedMatrix(m, n, rb, cb);
        System.out.println(VectorFunctions.print(M));

        //QRDecomposition QR = new QRDecomposition(M);
        //System.out.println(VectorFunctions.print(QR.getR()));
        //System.out.println(VectorFunctions.print(QR.getQ()));

        M = VectorFunctions.randomBandedMatrix(n, cb);
        System.out.println(VectorFunctions.print(M));

    }

   
    
}
