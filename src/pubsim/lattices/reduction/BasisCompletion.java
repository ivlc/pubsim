/*
 * Schnorr's 'trick' to complete a basis from a shortest vector,
 * see Step (5) of Algorithm C, p. 213, in
 *   C. P. Schnorr, 'A hierarchy of polynomial time lattice basis reduction
 *     algorithms', Theoretical Computer Science, vol. 53, pp. 201-224, 1987.
 *   doi: 10.1016/0304-3975(87)90064-8
 * @author Vaughan Clarkson
 */

package pubsim.lattices.reduction;

import Jama.Matrix;
import pubsim.VectorFunctions;
import pubsim.lattices.decoder.ShortVectorSphereDecoded;
import pubsim.lattices.reduction.LLL;
import pubsim.lattices.GeneralLattice;

public class BasisCompletion extends LLL {
    protected double shortest;

    @Override
    protected boolean notDone() {
	return R.getMatrix(0, m-1, 0, 0).normF() >= shortest / 2;
    }

    @Override
    protected void finishUp() {
	B.setMatrix(0, m-1, n-m, n-1,
		    hermite.reduce(B.getMatrix(0, m-1, n-m, n-1)));
	R = hermite.getR();
	Matrix sub = M.getMatrix(0, n-1, n-m, n-1);
	sub = sub.times(hermite.getUnimodularMatrix());
	M.setMatrix(0, n-1, n-m, n-1, sub);
    }

    // Pre: v is a column vector containing the shortest vector of
    // the lattice basis in B
    public Matrix completeBasis(Matrix v, Matrix B) {
	shortest = v.normF();
	m = v.getRowDimension();
	n = B.getColumnDimension()+1;
	Matrix Bnew = new Matrix(m, n);
	Bnew.setMatrix(0, m-1, 0, 0, v);
	Bnew.setMatrix(0, m-1, 1, n-1, B);
	Matrix Bred = reduce(Bnew);
	return Bred.getMatrix(0, m-1, 1, n-1);
    }

    @Override
    public Matrix getUnimodularMatrix() {
	Matrix X = M.getMatrix(1, n-1, 0, 0).times(M.getMatrix(0, 0, 1, n-1))
	    .times(M.get(0, 0));
	return M.getMatrix(1, n-1, 1, n-1).minus(X);
    }

    @Override
    public Matrix getR() {
	return R;
    }

    // Test harness
    public static void main(String args[]) {
	int dim = 5;
	Matrix B = Matrix.random(dim, dim);
	ShortVectorSphereDecoded svsd
	    = new ShortVectorSphereDecoded(new GeneralLattice(B));
	BasisCompletion cb = new BasisCompletion();
	Matrix sv = VectorFunctions.columnMatrix(svsd.getShortestVector());
	System.out.println("final B = ");
	cb.completeBasis(sv, B).print(8, 2);
	System.out.println("final R = ");
	cb.getR().print(8, 2);
	System.out.println("final M = ");
	cb.getUnimodularMatrix().print(8, 2);
	System.out.println("det M = " + cb.getUnimodularMatrix().det());
	System.out.println("error = "
			   + B.times(cb.getUnimodularMatrix()
				     .getMatrix(0, dim-1, 0, 0))
			   .minus(sv).norm2());
    }
}

