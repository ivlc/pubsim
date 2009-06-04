/*
 * VectorFunctions.java
 *
 * Created on 29 April 2007, 18:47
 */
package simulator;

import Jama.Matrix;
import java.util.Random;

/**
 * Miscelaneous functions to run on double arrays
 * @author Robby McKilliam
 */
public class VectorFunctions {

    /**
     * A slow Fourier Tranform.  It will work on
     * vectors of any length.
     */
    public static void slowFT(double[] x, double[] Xi, double[] Xr) {

        int N = x.length;

        if (Xi.length != N) {
            Xi = new double[N];
        }
        if (Xi.length != N) {
            Xr = new double[N];
        }

        for (int k = 0; k < N; k++) {
            for (int m = 0; m < N; m++) {
                Xr[k] += x[m] * Math.cos(k * m * 2 * Math.PI / N);
                Xi[k] -= x[m] * Math.sin(k * m * 2 * Math.PI / N);
            }
        }
    }

    /**
     * Zero padded Fourier Transform. Pads so that the resulting transform is p times
     * the length of x.
     * This is not a FFT and requires O(N^2) operations.
     * UNTESTED!
     */
    public static Complex[] PaddedFT(Complex[] x, double p) {

        int N = (int) (x.length*p);
        Complex[] X = new Complex[N];
        int t = (x.length - N)/2;

        for (int k = 0; k < N; k++) {
            X[k] = new Complex(0,0);
            for (int m = 0; m < N; m++) {
                double re = Math.cos(k * m * 2 * Math.PI / N);
                double im = Math.sin(k * m * 2 * Math.PI / N);
                Complex ejw = new Complex(re, im);
                if(m - t >= 0)
                    X[k] = X[k].plus(x[m-t].times(ejw));
            }
        }

        return X;
    }

    /**
     * Return the magnitude squared of the Fourier
     * tranform of @param x.
     */
    public static double[] abs2FT(double[] x) {
        double[] Xi = new double[x.length],
                Xr = new double[x.length];
        slowFT(x, Xi, Xr);
        double[] out = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            out[i] = Xi[i] * Xi[i] + Xr[i] * Xr[i];
        }
        return out;
    }

    /**
     * Euclidean distance between two vectors
     */
    public static double distance_between(double[] x, double[] s) {
        double out = 0.0;
        for (int i = 0; i < x.length; i++) {
            out += Math.pow(x[i] - s[i], 2.0);
        }
        return Math.sqrt(out);
    }

    /**
     * Squared Euclidean distance between two vectors
     */
    public static double distance_between2(double[] x, double[] s) {
        double out = 0.0;
        for (int i = 0; i < x.length; i++) {
            out += Math.pow(x[i] - s[i], 2.0);
        }
        return out;
    }

    /**
     * Squared Euclidean distance between two vectors
     */
    public static double distance_between2(Complex[] x, Complex[] s) {
        double out = 0.0;
        for (int i = 0; i < x.length; i++) {
            out += x[i].minus(s[i]).abs2();
        }
        return out;
    }

    /**
     * angle between two vectors
     */
    public static double angle_between(double[] x, double[] y) {
        return Math.acos(dot(x, y) / (magnitude(x) * magnitude(y)));
    }

    /**
     * vector subtraction x - y
     */
    public static double[] subtract(double[] x, double[] y) {
        double[] out = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            out[i] = x[i] - y[i];
        }
        return out;
    }

    /**
     * vector addition x + y
     */
    public static double[] add(double[] x, double[] y) {
        double[] out = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            out[i] = x[i] + y[i];
        }
        return out;
    }

    /**
     * Return the sum of a vector
     */
    public static double sum(double[] x) {
        double out = 0.0;
        for (int i = 0; i < x.length; i++) {
            out += x[i];
        }
        return out;
    }

    /**
     * Return the sum of a vector
     */
    public static Complex sum(Complex[] x) {
        double outr = 0.0, outi = 0.0;
        for (int i = 0; i < x.length; i++) {
            outr += x[i].re();
            outi += x[i].im();
        }
        return new Complex(outr, outi);
    }

    /**
     * Return the vector with each element rounded to
     * the nearest integer.
     * Pre: x.length = y.length
     */
    public static void round(double[] x, double[] y) {
        for (int i = 0; i < x.length; i++) {
            y[i] = Math.round(x[i]);
        }
    }

    /**
     * Return a vector of zero mean var = 1 gaussian
     * noise.
     */
    public static double[] randomGaussian(int length) {
        double[] x = new double[length];
        Random r = new Random();
        for (int i = 0; i < length; i++) {
            x[i] = r.nextGaussian();
        }
        return x;
    }

    /**
     * Return a vector of zero mean var = 1 complex
     * noise i.e. Raylieh noise.
     */
    public static Complex[] randomComplex(int length) {
        Complex[] x = new Complex[length];
        Random r = new Random();
        for (int i = 0; i < length; i++) {
            x[i] = new Complex(r.nextGaussian(), r.nextGaussian());
        }
        return x;
    }
    
    /**
     * Return a vector of  gaussian noise.
     */
    public static double[] randomGaussian   (int length, double mean, double var) {
        double[] x = new double[length];
        Random r = new Random();
        double std = Math.sqrt(var);
        for (int i = 0; i < length; i++) {
            x[i] = r.nextGaussian()*std + mean;
        }
        return x;
    }
    
    /**
     * Apply a Given's rotation to matrix M.  The rotation will
     * make M[m2,n] = 0.  It affects the rows m1 and m2 in M.
     * In this implementation m2 > m1 so it's only applicable to
     * lower triangular matrixes.
     */
    public static void givensRotate(Matrix M, int m1, int m2, int n){
        if(n > M.getColumnDimension()-1 || m2 > M.getRowDimension())
            throw new RuntimeException("Given's rotation parameters outside matrix size.");
        if(m1 >= m2)
            throw new RuntimeException("This Given's implementation requires m1 < m2");
        
        double a = M.get(m1, n);
        double b = M.get(m2, n);
        double d = 1.0 / Math.sqrt(a*a + b*b);
        double c = a*d;
        double s = b*d;
        
        for(int i = 0; i < M.getColumnDimension(); i++){
            double v1 = M.get(m1, i);
            double v2 = M.get(m2, i);
            M.set(m1, i, c*v1 + s*v2);
            M.set(m2, i, -s*v1 + c*v2);
        }
    }

    /**
     * Compute the mth difference of a vector.
     * i.e. the mth psuedoderivative.
     */
    public static double[] mthDifference(double[] y, int m){
        double[] d = y;
        for(int i = 1; i<=m; i++){
            d = firstDifference(d);
        }
        return d;
    }

    /**
     * Compute the first difference of a vector.
     * i.e. psuedoderivative.
     */
    public static double[] firstDifference(double[] y){
        double[] d = new double[y.length - 1];
        for(int i = 0; i < d.length; i++){
            d[i] = y[i+1] - y[i];
        }
        return d;
    }

    /**
     * Return the squared sum of a vector
     */
    public static double sum2(double[] x) {
        double out = 0.0;
        for (int i = 0; i < x.length; i++) {
            out += x[i] * x[i];
        }
        return out;
    }

    /**
     * Return a vector of length n with zeros everywhere
     * excepect the e[i] = 1.0
     */
    public static double[] eVector(int i, int n) {
        double[] e = new double[n];
        e[i] = 1.0;
        return e;
    }

    /**
     * Return the mean value of a vector
     */
    public static double mean(double[] x) {
        return sum(x) / x.length;
    }

    /**
     * Return the magnitude of the vector
     */
    public static double magnitude(double[] x) {
        return Math.sqrt(sum2(x));
    }

    /**
     * Return the maximum value of a vector
     */
    public static double max(double[] x) {
        double out = 0;
        for (int i = 0; i < x.length; i++) {
            if (x[i] > out) {
                out = x[i];
            }
        }
        return out;
    }

    /**
     * Return true if the vector is increasing
     */
    public static boolean increasing(double[] x) {
        for (int i = 0; i < x.length - 1; i++) {
            if (x[i] > x[i + 1]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Return a string for the vector
     */
    public static String print(double[] x) {
        String st = new String();
        for (int i = 0; i < x.length; i++) {
            st = st.concat(" " + x[i]);
        }
        return st;
    }

    /**
     * Return a string for the complex vector
     */
    public static String print(Complex[] x) {
        String st = new String();
        for (int i = 0; i < x.length; i++) {
            st = st.concat(" " + x[i]);
        }
        return st;
    }
    
    /**
     * Return a string for the int[]
     */
    public static String print(int[] x) {
        String st = new String();
        for (int i = 0; i < x.length; i++) {
            st = st.concat(" " + x[i]);
        }
        return st;
    }
    
    /**
     * Return a string for the complex vector
     */
    public static String print(Object[] x) {
        String st = new String();
        for (int i = 0; i < x.length; i++) {
            st = st.concat(" " + x[i].toString());
        }
        return st;
    }

    /**
     * Return a string for the vector
     */
    public static String print(double[][] M) {
        String st = new String();
        for (int m = 0; m < M.length; m++) {
            for (int n = 0; n < M[0].length; n++) {
                st = st.concat("\t" + M[m][n]);
            }
            st = st.concat("\n");
        }
        return st;
    }

    /**
     * Return a string for the vector
     */
    public static String print(Matrix mat) {
        return print(mat.getArray());
    }

    /**
     * Packs the vector y rowise into a double[M][N]
     * where N = y.length/numrows;  Zero pads if numrows does
     * not divide y.length;
     */
    public static double[][] packRowiseToMatrix(double[] y, int numrows){
        int M = numrows;
        int N = y.length/M;
        double[][] u = new double[M][N];
        int i = 0;
        for(int m = 0; m < M; m++){
            for(int n = 0; n < N; n++){
                if(i < y.length)
                    u[m][n] = y[i];
                i++;
            }
        }
        return u;
    }

    /**
     * Packs the matrix rowise into a double[]
     */
    public static double[] unpackRowise(Matrix B){
        int M = B.getRowDimension();
        int N = B.getColumnDimension();
        double[] y = new double[M*N];
        int i = 0;
        for(int m = 0; m < M; m++){
            for(int n = 0; n < N; n++){
                y[i] = B.get(m,n);
                i++;
            }
        }
        return y;
    }

    /**
     * Packs the matrix columnwise into a double[]
     */
    public static double[] unpackColumnwise(Matrix B){
        int M = B.getRowDimension();
        int N = B.getColumnDimension();
        double[] y = new double[M*N];
        int i = 0;
        for(int n = 0; n < N; n++){
            for(int m = 0; m < M; m++){
                y[i] = B.get(m,n);
                i++;
            }
        }
        return y;
    }

    /**
     * Convolution of two vectors.  This
     * allocates the required memory
     */
    public static double[] conv(double[] x, double[] y) {
        double[] r = new double[x.length + y.length - 1];
        for (int t = 0; t < r.length; t++) {
            double csum = 0.0;
            for (int i = 0; i < x.length; i++) {
                if (t - i >= 0 && t - i < y.length) {
                    csum += x[i] * y[t - i];
                }
            }
            r[t] = csum;
        }
        return r;
    }

    /**
     * Vector dot/inner product
     */
    public static double dot(double[] x, double[] y) {
        double sum = 0.0;
        for (int i = 0; i < x.length; i++) {
            sum += x[i] * y[i];
        }
        return sum;
    }

    /**
     * Return the min value of a vector
     */
    public static double min(double[] x) {
        double out = 0.0;
        for (int i = 0; i < x.length; i++) {
            if (x[i] < out) {
                out = x[i];
            }
        }
        return out;
    }

    /**
     * Return true if every element in the vectors is equal, esle false.
     */
    public static boolean equal(double[] x, double[] y) {
        for (int i = 0; i < x.length; i++) {
            if (x[i] != y[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Return the distance between the two elements in
     * x that are the fathest apart.
     */
    public static double maxDistance(double[] x) {
        return max(x) - min(x);
    }

    /** 
     * Complex dot product using Hermition transpose of x. 
     * <p>
     * Pre: x.length = y.length
     * <p>
     * It would be interesting to know what is faster out
     * of the two ways of calculating this bellow.  One
     * of them is commented out.
     */
    public static Complex dot(Complex[] x, Complex[] y) {
        //double re = 0.0, im = 0.0;
        Complex ret = new Complex(0, 0);
        for (int i = 0; i < x.length; i++) {
            ret = ret.plus(x[i].conjugate().times(y[i]));
        //re += x[i].re()*y[i].re() + x[i].im()*y[i].im();
        //im += x[i].re()*y[i].im() - x[i].im()*y[i].re();
        }
        //return new Complex(re, im);
        return ret;
    }

    /** 
     * y and x and vector, M is a matrix.
     * Performs y = M*x.  
     * PRE: x.length = i, y.length = j, M is j by i matrix
     */
    public static void matrixMultVector(double[][] M, double[] x, double[] y) {
        for (int n = 0; n < y.length; n++) {
            y[n] = 0;
            for (int m = 0; m < x.length; m++) {
                y[n] += M[n][m] * x[m];
            }
        }
    }
    
    /**
     * y and x and vector, M is a matrix.
     * Performs y = M*x.  
     * PRE: x.length = j, y.length = i, M is i by j matrix
     */
    public static void matrixMultVector(Matrix M, double[] x, double[] y) {
        int m = M.getRowDimension();
        int n = M.getColumnDimension();
        for (int i = 0; i < m; i++) {
            y[i] = 0;
            for (int j = 0; j < n; j++) {
                y[i] += M.get(i,j) * x[j];
            }
        }
    }

    /**
     * y and x and vector, M is a matrix.
     * Performs y = M*x.
     * PRE: x.length = j, y.length = i, M is i by j matrix
     */
    public static double[] matrixMultVector(Matrix M, double[] x) {
        double[] y = new double[M.getRowDimension()];
        matrixMultVector(M, x, y);
        return y;
    }

   /**
     * returns the vector  x - round(x)
     */
    public static double[] wrap(double[] x) {
        double[] y = new double[x.length];
        for(int i = 0; i < y.length; i++){
            y[i] = x[i] - Math.round(x[i]);
        }
        return y;
    }

    /**
     * sets y = x - round(x)
     */
    public static void wrap(double[] x, double[] y) {
        int len = Math.min(y.length, x.length);
        for(int i = 0; i < len; i++){
            y[i] = x[i] - Math.round(x[i]);
        }
    }

    /** 
     * Project y parallel to x and return in yp.
     * PRE: y.length == x.length == yp.length
     */
    public static void projectParallel(double[] x, double[] y, double[] yp) {
        double xty = dot(x, y);
        double xtx = dot(x, x);
        for (int i = 0; i < x.length; i++) {
            yp[i] = xty / xtx * x[i];
        }
    }

    /** 
     * Project y orthogonal to x and return in yp.
     * PRE: y.length == x.length == yp.length
     */
    public static void projectOrthogonal(double[] x, double[] y, double[] yp) {
        double xty = dot(x, y);
        double xtx = dot(x, x);
        for (int i = 0; i < x.length; i++) {
            yp[i] = y[i] - xty / xtx * x[i];
        }
    }

    /** 
     * Scalar divide.  Result is returned in y
     * PRE: x.length == y.length
     */
    public static void divide(double[] x, double d, double[] y) {
        for (int i = 0; i < x.length; i++) {
            y[i] = x[i] / d;
        }
    }

    /** 
     * Returns the normalised vector
     * PRE: x.length == y.length
     */
    public static void normalise(double[] x, double[] y) {
        double d = magnitude(x);
        for (int i = 0; i < x.length; i++) {
            y[i] = x[i] / d;
        }
    }

    /** 
     * Returns the transpose of a matrix.  Allocates memory.
     * PRE: x.length == y.length
     */
    public static double[][] transpose(double[][] M1) {
        double[][] mat = new double[M1[0].length][M1.length];
        for (int m = 0; m < M1.length; m++) {
            for (int n = 0; n < M1[0].length; n++) {
                mat[n][m] = M1[m][n];
            }
        }
        return mat;
    }

    /** 
     * O(j^3) determinant algorithm that is 'fraction free'
     * and more stable than the LU and trace algorithm in the
     * Jama library.
     * <p>
     * Erwin H. Bareiss, �Sylvester's Identity and Multistep 
     * Integer-Preserving Gaussian Elimination,� 
     * Mathematical Computation 22, 103, pp. 565 � 578, 1968.
     */
    public static double stableDet(Matrix mat) {

        //handle exceptional cases
        if (mat.getColumnDimension() != mat.getRowDimension()) {
            throw new IllegalArgumentException("Matrix must be square.");
        }
        if (mat.getRowDimension() == 1) {
            return mat.get(0, 0);
        }

        return stableDet(mat, 0);

    }

    /** Recursive function used by stableDet */
    protected static double stableDet(Matrix mat, int index) {

        if (mat.getRowDimension() - index == 2) {
            return mat.get(index, index) * mat.get(index + 1, index + 1) - mat.get(index, index + 1) * mat.get(index + 1, index);
        }

        for (int i = index + 1; i < mat.getRowDimension(); i++) {
            for (int j = index + 1; j < mat.getRowDimension(); j++) {
                double sub = mat.get(i, index) * mat.get(index, j);
                mat.set(i, j, mat.get(i, j) * mat.get(index, index) - sub);
            }
        }
        double det = stableDet(mat, index + 1);
        for (int i = index; i < mat.getRowDimension() - 2; i++) {
            det /= mat.get(index, index);
        }

        return det;
    }
    
    /** Returns vector of length j of randomGaussian integer in the range -M to M-1 */ 
    public static double[] randomIntegerVector(int n, int M) {
        double[] u = new double[n];
        Random r = new Random();
        for (int t = 0; t < n; t++) {
            u[t] = r.nextInt(2*M) - M;
        }
        return u;
    }

    /** Swap columns i and j in a matrix inplace */
    public static void swapColumns(Matrix B, int i, int j) {
        int n = B.getRowDimension();
        for (int t = 0; t < n; t++) {
            double temp = B.get(t, i);
            B.set(t, i, B.get(t, j));
            B.set(t, j, temp);
        }
    }

    /** Swap columns i and j in a matrix inplace */
    public static void swapRows(Matrix B, int i, int j) {
        int n = B.getColumnDimension();
        for (int t = 0; t < n; t++) {
            double temp = B.get(i, t);
            B.set(i, t, B.get(j, t));
            B.set(j, t, temp);
        }
    }

    /** Construct a column matrix (vector) from a double[] */
    public static Matrix columnMatrix(double[] x){
        Matrix M = new Matrix(x.length, 1);
        for(int n = 0; n < x.length; n++){
            M.set(n, 0, x[n]);
        }
        return M;
    }

     /** Construct a row matrix (vector) from a double[] */
    public static Matrix rowMatrix(double[] x){
        Matrix M = new Matrix(1, x.length);
        for(int n = 0; n < x.length; n++){
            M.set(0, n, x[n]);
        }
        return M;
    }

}
