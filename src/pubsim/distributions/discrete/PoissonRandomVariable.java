/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.distributions.discrete;

/**
 * Class representing the discrete Poisson distribution.
 * @author Robby McKilliam
 */
public class PoissonRandomVariable extends AbstractDiscreteRandomVariable {
    private final double l;

    /**
     * Constructor sets the parameter for this Poisson distribution.
     * p must be between 0 and 1.
     */
    public PoissonRandomVariable(double l){
        if(l <= 0)
            throw new RuntimeException("l must be > 0.");
        this.l = l;
    }

    /** Knuth's simple method for generating a Poisson r.v. */
    @Override
    public Integer getNoise() {
        double L = Math.exp(-l);
        double p = 1.0;
        Integer k = 0;
        while(p > L){
            k++;
            double u = random.raw();
            p*=u;
        }
        return k - 1;
    }

    @Override
    public double pmf(Integer x) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double getMean() {
        return l;
    }

    @Override
    public double getVariance() {
        return l;
    }

    @Override
    public double cmf(Integer k) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Integer icmf(double x) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
