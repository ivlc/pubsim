/*
 * SignalGenerator.java
 *
 * Created on 13 April 2007, 14:48
 */

package simulator;

import distributions.RandomVariable;
import java.io.Serializable;

/**
 * Interface for the generation recieved signals
 * @author Robby McKillam
 */
public interface SignalGenerator extends Serializable {
    public double[] generateReceivedSignal();
    public void setNoiseGenerator(RandomVariable noise);
    public RandomVariable getNoiseGenerator();
    
    /** Set the length of the signal generated */
    public void setLength(int n);
    
    /** Return the length of the signal generated */
    public int getLength();


    /** Generic version of the signal generator */
    public static interface Generic<T> extends Serializable{

        public T[] generateReceivedSignal();

        public void setNoiseGenerator(RandomVariable noise);
        public RandomVariable getNoiseGenerator();

        /** Set the length of the signal generated */
        public void setLength(int n);

        /** Return the length of the signal generated */
        public int getLength();
    }

}
