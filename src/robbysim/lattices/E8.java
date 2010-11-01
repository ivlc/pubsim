/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.lattices;

import Jama.Matrix;

/**
 *
 * @author Robby McKilliam
 */
public class E8 extends AbstractLattice{

    public double coveringRadius() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setDimension(int n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getDimension() {
        return 8;
    }

    public Matrix getGeneratorMatrix() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double volume(){
        return 1.0;
    }

    @Override
    public double inradius(){
        return Math.sqrt(2.0)/2.0;
    }

    @Override
    public long kissingNumber(){
        return 240;
    }


}