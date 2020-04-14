/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer;

import java.util.Comparator;
import javafx.util.Pair;

/**
 *
 * @author zoehill
 */
public class PairComparator implements Comparator<Pair> {

    @Override
    public int compare(Pair o1, Pair o2) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return Double.compare((double)o1.getKey(), (double)o2.getKey());
        
        //return (Integer)o1.getKey() - (Integer)o2.getKey();
    }
    
}
