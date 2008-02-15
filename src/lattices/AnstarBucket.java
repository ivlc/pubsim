/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Implementation of the O(n) Anstar bucket sorting algorithm
 * with a specialised list implementation.  This has fixed 
 * memory (no allocation/deallocation) and also O(n) memory
 * requirements.  This implementation should be the fastest.
 * It is also an implementation that lends itself to the C and
 * C++ language rather that java.  It would be good to do
 * performance tests in C.
 * @author Robby McKilliam
 */
public class AnstarBucket extends AnstarBucketSlowAllocation
        implements LatticeNearestPointAlgorithm {
    
    private IntList[] buckets;
    private ListElem[] ints;
    private double[] z;

    @Override
    public void setDimension(int n) {
        this.n = n;
        // Allocate some space for arrays
        u = new double[n + 1];
        v = new double[n + 1];
        z = new double[n + 1];
        ints = new ListElem[n + 1];
        buckets = new IntList[n+1];
        for(int i = 0; i < n + 1; i++){
            buckets[i] = new IntList();
            ints[i] = new ListElem();
            ints[i].value = i;
        }
    }

    @Override
    public void nearestPoint(double[] y) {
        if (n != y.length-1)
	    setDimension(y.length-1);
        
        //make sure that the buckets are empty!
        for(int i = 0; i < n + 1; i++)
            buckets[i].clear();
        
        double a = 0, b = 0;
        for(int i = 0; i < n + 1; i++){
            z[i] = y[i] - Math.round(y[i]);
            int bi = n - (int)(Math.floor((n+1)*(z[i]+0.5)));
            buckets[bi].add(ints[i]);
            a += z[i];
            b += z[i] * z[i];
        }
        
        double D = b - a*a/(n+1);
        int m = 0;
        for(int i = 0; i < n+1; i++){
            IntListIterator itr = buckets[i].iterator();
            while(itr.hasNext()){
                int ind = itr.next().value;
                a -= 1;
                b += -2*z[ind] + 1;                
            }
            double dist = b - a*a/(n+1);
            if(dist < D){
                D = dist;
                m = i+1;
            }
        }
        
        for(int i = 0; i < n + 1; i++)
            u[i] = Math.round(y[i]);
        
        for(int i = 0; i < m; i++){
            IntListIterator itr = buckets[i].iterator();
            while(itr.hasNext()){
                int ind = itr.next().value;
                u[ind] += 1;
            }
        }
        
        project(u, v);
        
    }
    
    /** 
     * Specialised list implementation for the bucket
     * sorting algorithm.  This should be significantly
     * faster than java's list implementations.  It
     * allows a fixed memory implementation.
     */
    public class IntList{
        protected int size;
        protected ListElem current, first;
        protected IntListIterator itr;
        
        public IntList(){
            first = new ListElem();
            current = first;
            current.next = null;
            size = 0;
            itr = new IntListIterator(this);
        }
        
        public boolean add(ListElem e){
            current.next = e;
            current = e;
            current.next = null;
            size++;
            return true;
        }
        
        public void clear(){
            current = first;
            current.next = null;
            size = 0;
        }
        
        public IntListIterator iterator(){
            itr.reset(this);
            return itr;
        }
        
    }
    
    /** List element for IntList */
    public class ListElem{
        protected ListElem next;
        protected int value;
    }
    
    /** An iterator for IntList */
    public class IntListIterator implements Iterator{
        protected ListElem current;
        
        public IntListIterator(IntList list){
            current = list.first;
        }
        
        public void reset(IntList list){
            current = list.first;
        }
        
        @Override
        public boolean hasNext(){
            if(current.next == null) return false;
            else return true;
        }
        
        @Override
        public ListElem next(){
            current = current.next;
            return current;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

}