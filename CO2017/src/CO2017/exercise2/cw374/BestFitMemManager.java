/**
 * BestFitMemManager.java
 *
 * @author Cecelia Wisniewska
 * 
 * Allocates the smallest free partition which can accommodate the process
 * 
 */

package CO2017.exercise2.cw374;

public class BestFitMemManager extends MemManager {

    public BestFitMemManager(int s){
        super(s);
    }
    
    @Override
    protected int findSpace(int s){
        
        int _smallestSpace = _largestSpace;
        int startAddress=0;
          
       if(_largestSpace<_smallestSpace){
           _smallestSpace = _largestSpace;
       }

        for (int i=0; i<_memory.length;i++){
            if (countFreeSpacesAt(i) == _smallestSpace){
                startAddress=i;
                break;
            }
        }
        return startAddress;
    }
}
