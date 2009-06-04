/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.util;

import Jama.Matrix;
import distributions.UniformNoise;
import java.util.Enumeration;
import lattices.Lattice;
import lattices.LatticeAndNearestPointAlgorithm;
import lattices.NearestPointAlgorithmInterface;
import lattices.decoder.SphereDecoder;
import simulator.NoiseVector;
import simulator.VectorFunctions;

/**
 * Generate points uniformly in the Vornoi region using
 * uniformly generate noise.
 * @author Robby McKilliam
 */
public class UniformInVornoi  implements PointInVoronoi{
    
    private int numsamples = 1000, count = 0;
    protected NearestPointAlgorithmInterface decoder;
    private NoiseVector nv;


    protected UniformInVornoi() {}

    /**
     * Default to using the sphere decoder to compute nearest points
     * @param L is the lattice
     * @param samples is the number of samples used per dimension
     */
    public UniformInVornoi(Lattice L, int samples){
        decoder = new SphereDecoder(L);
        numsamples = samples;
        initNoiseVector(L.getDimension());
    }

    /**
     * Using the nearest point algorithm supplied.
     * @param L is the lattice with included nearest point algorithm.
     * @param samples is the number of samples used per dimension
     */
    public UniformInVornoi(LatticeAndNearestPointAlgorithm L, int samples){
        decoder = L;
        numsamples = samples;
        initNoiseVector(L.getDimension());
    }

    public boolean hasMoreElements() {
        return count < numsamples;
    }

    public Matrix nextElement() {
        return VectorFunctions.columnMatrix(nextElementDouble());
    }

    /**
     * @return return the next element as a double[] rather than a Matrix
     */
    public double[] nextElementDouble() {
        double[]  p = nv.generateReceivedSignal();
        decoder.nearestPoint(p);
        return  VectorFunctions.subtract(p, decoder.getLatticePoint());
    }

    protected void initNoiseVector(int N) {
        UniformNoise noise = new UniformNoise();
        noise.setRange(1.0);
        nv = new NoiseVector(noise, N);
    }



}
