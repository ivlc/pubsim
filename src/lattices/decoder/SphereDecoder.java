/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.decoder;

import lattices.Lattice;
import lattices.reduction.LLL;
import simulator.VectorFunctions;

/**
 * Sphere decoder that uses the Babai point
 * as the initial point
 * @author Robby McKilliam
 */
public class SphereDecoder extends Babai
        implements GeneralNearestPointAlgorithm {

    /** Current sphere radius squared */
    protected double D;
    
    //temporary variable for ut
    protected double[] ut, ubest;
    
    protected double[] xr;
    
    //1 + small number to avoid numerical errors in branches.
    double DELTA = 1.00001;
    
    @Override
    public void setLattice(Lattice L) {
        G = L.getGeneratorMatrix().copy();
        m = G.getRowDimension();
        n = G.getColumnDimension();
        u = new double[n];
        uh = new double[n];
        x = new double[m];
        yr = new double[n];
        ut = new double[n];
        ubest = new double[n];
        xr = new double[m];
        
        lll = new LLL();
        B = lll.reduce(G);
        U = lll.getUnimodularMatrix();
        
        //CAREFULL!  This version of the sphere decoder requires R to
        //have positive diagonal entries.
        simulator.QRDecomposition QR = new simulator.QRDecomposition(B);
        R = QR.getR();
        Q = QR.getQ();
        
    }
       
    @Override
    public void nearestPoint(double[] y) {
        if(m != y.length)
            throw new RuntimeException("Point y and Generator matrix are of different dimension!");
        
        computeBabaiPoint(y);
        
        //compute the Babai point in the triangular frame
        VectorFunctions.matrixMultVector(R, uh, xr);
        
        //compute the radius squared of the sphere we are decoding in
        D = VectorFunctions.distance_between2(yr, xr) * DELTA;
        
        //current element being decoded
        int k = n-1;
        
        decode(k, 0);
        
        //compute index u = Uuh so that Gu is nearest point
        VectorFunctions.matrixMultVector(U, ubest, u);
        
        //compute nearest point
        VectorFunctions.matrixMultVector(G, u, x);
        
    }
    
    /** 
     * Recursive decode function to test nearest plane
     * for a particular dimension/elenent
     */
    protected void decode(int k, double d){
        //return if this is already not the closest point 
        if(d > D){
            return;
        }
        
        //compute the sum of R[k][k+i]*uh[k+i]'s
        //and the distance so far
        double rsum = 0.0;
        for(int i = k+1; i < n; i++ ){
            rsum += ut[i]*R.get(k, i);
        }
        
        //set least possible ut[k]
        ut[k] = Math.ceil((-Math.sqrt(D - d) + yr[k] - rsum)/R.get(k,k));
        
        while(ut[k] <= (Math.sqrt(D - d) + yr[k] - rsum)/R.get(k,k) ){
            double kd = R.get(k, k)*ut[k] + rsum - yr[k];
            double sumd = d + kd*kd;
            
            //if this is not the first element then recurse
            if( k > 0)
                decode(k-1, sumd);
            //otherwise check if this is the best point so far encounted
            //an update
            else{
                if(sumd <= D){
                    System.arraycopy(ut, 0, ubest, 0, n);
                    D = sumd;
                }
            }
            ut[k]++;
        }
        
    }


}
