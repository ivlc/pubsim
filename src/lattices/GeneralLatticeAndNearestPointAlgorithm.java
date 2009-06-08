/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices;

import Jama.Matrix;
import lattices.decoder.GeneralNearestPointAlgorithm;
import lattices.decoder.SphereDecoder;

/**
 * General lattice with a nearest point algorithm included with it.
 * By default the sphere decoder is used but you can use other
 * algorithms by using the appropriate constructor
 * @author Robby McKilliam
 */
public class GeneralLatticeAndNearestPointAlgorithm extends GeneralLattice
    implements LatticeAndNearestPointAlgorithm {

    private NearestPointAlgorithmInterface decoder;

    public GeneralLatticeAndNearestPointAlgorithm(Matrix B){
        this.B = B;
        decoder = new SphereDecoder(this);
    }

    public GeneralLatticeAndNearestPointAlgorithm(double[][] B){
        this.B = new Matrix(B);
        decoder = new SphereDecoder(this);
    }

     public GeneralLatticeAndNearestPointAlgorithm(Matrix B, GeneralNearestPointAlgorithm np){
        this.B = B;
        decoder = np;
    }

    public void nearestPoint(double[] y) {
        decoder.nearestPoint(y);
    }

    public double[] getLatticePoint() {
        return decoder.getLatticePoint();
    }

    public double[] getIndex() {
        return decoder.getIndex();
    }

}