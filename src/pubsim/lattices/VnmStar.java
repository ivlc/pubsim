/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices;

import Jama.Matrix;
import static pubsim.Util.binom;
import static pubsim.Util.factorial;
import static pubsim.VectorFunctions.dot;

/**
 *
 * @author Robby McKilliam
 */
public abstract class VnmStar extends AbstractLattice implements LatticeAndNearestPointAlgorithm {

    /** dimension of this lattice*/
    final protected int n;

    /** polynomial order */
    final protected int m;
    
    final protected int N;
    
    //store all the legendre polynomials so that we can make projection fast.
    final double[][] legendre;
    
    public VnmStar(int n, int m){
        this.n = n;
        this.m = m;
        N = n+m+1;
         //compute all the Legendre polynomials
        legendre = new double[m+1][];
        for(int k = 0; k <= m; k++)
            legendre[k] = discreteLegendrePolynomialVector(n+m+1, k);
    }

    /** {@inheritDoc} */
    @Override
    public double volume(){
        Matrix M = getGeneratorMatrix();
        return Math.sqrt(M.transpose().times(M).det());
    }

    @Override
    public Matrix getGeneratorMatrix() {
        return getGeneratorMatrix(m, n);
    }

    public static Matrix getGeneratorMatrix(int m, int n) {
        Matrix M = getMMatrix(m, n);
        Matrix Mt = M.transpose();
        Matrix K = (Mt.times(M)).inverse().times(Mt);
        Matrix G = Matrix.identity(n+m+1, n+m+1).minus(M.times(K));

        return G.getMatrix(0, n+m, 0, n-1);
    }

    /**
     * This is the matrix M in most of my papers
     * M = [1, n, n^2, ..., n^m]
     */
    public Matrix getMMatrix(){
        return getMMatrix(m, n);
    }

    /**
     * This is the Vandermonde matrix N in my thesis.
     * N = [1, n, n^2, ..., n^m]
     */
    public static Matrix getMMatrix(int m, int n){
        int N = m + n + 1;
        Matrix M = new Matrix(N, m+1);

        for(int i = 0; i < N; i++){
            for(int j = 0; j < m+1; j++){
                M.set(i, j, Math.pow(i+1, j));
            }
        }
        return M;
    }

    /**
     * Project x into the space of the lattice VnmStarSampled and return
     * the projection into y.  Requires recursion, runs in O(n m) time
     * PRE: x.length = y.length
     */
    public static void project(double[] x, double[] y, int m){
        int n = x.length;
        System.arraycopy(x, 0, y, 0, n);
        double[] p = new double[n];
        for(int k = 0; k <= m; k++){
            discreteLegendrePolynomialVector(n, k, p);
            double ytp = dot(y,p);
            double ptp = dot(p,p);
            double scale = ytp/ptp;
            for(int s = 0; s < n; s++) y[s] -= p[s]*scale;
        }
    }

    /** Project into the space this lattice lies in. */
    public void project(double[] x, double[] y){
        System.arraycopy(x, 0, y, 0, N);
        for(int k = 0; k <= m; k++){
            double[] ell = legendre[k];
            double ytp = dot(y,ell);
            double ptp = dot(ell,ell);
            double scale = ytp/ptp;
            for(int s = 0; s < N; s++) y[s] -= ell[s]*scale;
        }
    }

    /**
     * Return the mth monic discrete Legendre polynomial of length n.
     */
    public static double[] discreteLegendrePolynomialVector(int n, int m){
        double[] p = new double[n];
        discreteLegendrePolynomialVector(n, m, p);
        return p;
    }

    /**
     * Return the mth monic discrete Legendre polynomial of length n.
     * Does not allocate memory.
     * PRE: p.length = n;
     */
    public static void discreteLegendrePolynomialVector(int n, int m, double[] p){
        if(m < 0 || m > n)
            throw new ArrayIndexOutOfBoundsException("m is out of range");
        if(p.length < n)
            throw new ArrayIndexOutOfBoundsException("p is too short");
        //zero the elements in p
        for(int x = 0; x < n; x++) p[x] = 0.0;
        double scale = factorial(m)/((double)binom(2*m, m));
        for(int s = 0; s <= m; s++){
            for(int x = 0; x < n; x++)
                p[x] += scale*Math.pow(-1, s+m)*binom(s+m, s)*binom(n-s-1, n-m-1)*binom(x,s);
        }
    }

    @Override
    public double coveringRadius() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getDimension() {
        return n;
    }
    
    @Override
    public double distance() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

     private double[] yDoubletoy;
    @Override
    public void nearestPoint(Double[] y) {
        if(yDoubletoy == null || yDoubletoy.length != y.length)
            for(int i = 0; i < y.length; i++) yDoubletoy[i] = y[i];
        this.nearestPoint(y);
    }
    
    @Override
    public String name() { 
        return "Vn" + n + "m" + m + "star";
    }


}
