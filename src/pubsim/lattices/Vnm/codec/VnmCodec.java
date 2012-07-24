/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.lattices.Vnm.codec;

import Jama.Matrix;
import pubsim.VectorFunctions;
import pubsim.lattices.Vnm.Vnm;

/**
 * Class for encoding and decoding the Vnm lattice based codes.  See the paper
 * R. G McKilliam, "Convolutional lattice codes and the Prouhet-Tarry-Escott problem"
 * @author Robby McKilliam
 */
public class VnmCodec {

    
    final protected int n, m, N;
    
    public VnmCodec(int n, int m){
        this.n = n;
        this.m = m;
        this.N = n + m + 1;
    }
    
    /** 
     * Return the shaping loss with respect to the hypercube for this code.  Value returned in dB.
     */
    public static double shapingLoss(int n, int m){
        if(n==1) return 0.0;

        Matrix R = getR(n,m);  
        double scale = Math.pow(2.0, new Vnm(n, m).logVolume()/n);
        double secmom = 0.0;
        for(int i = 0; i < n; i++){
            double d = Math.abs(R.get(i,i))/scale;
            secmom +=d*d*d/12.0;
        }
        secmom /= n;
        //System.out.println(secmom + ", " + scale);
        double hypercubesecmom = 1.0/12.0;
        return 10.0 * Math.log10(secmom/hypercubesecmom);
    }
    
     /** 
     * Return the shaping loss with respect to the hypercube for this code.  Value returned in dB.
     * Modified to model the situation where rate can be sacrificed for shaping gain.
     */
    public static double modifiedShapingLoss(int n, int m, int k){
        if( k >= n) throw new RuntimeException("n must be greater than k, otherwise this code has rate zero!");
        if(n==1) return 0.0;

        Matrix R = getR(n,m);  
        double scale = Math.pow(2.0, new Vnm(n, m).logVolume()/n);
        double secmom = 0.0;
        for(int i = 0; i < k; i++){
            double d = Math.abs(R.get(i,i))/scale/2.0;
            secmom +=d*d*d/12.0;
        }
        for(int i = k; i < n; i++){
            double d = Math.abs(R.get(i,i))/scale;
            secmom +=d*d*d/12.0;
        }
        secmom /= n;
        //System.out.println(secmom + ", " + scale);
        double hypercubesecmom = 1.0/12.0;
        return 10.0 * Math.log10(secmom/hypercubesecmom);
    }
    
    /**
     * Get the R part of the QR decomposition of the generator matrix of Vnm.
     * Makes use of Given's rotations and is much faster than the usual QR decomposition.
     */
    public static Matrix  getR(int n, int m) {
        //compute the R part of the QR decomposition by Givens rotations
        Matrix R = new Vnm(n, m).getGeneratorMatrix();
        for( int k = m; k >= 0; k--){
            for( int i = 0; i < n; i++){
                VectorFunctions.givensRotate(R, k+i, k+i+1, i);
            }
        }
        return R;
    }
    
}
