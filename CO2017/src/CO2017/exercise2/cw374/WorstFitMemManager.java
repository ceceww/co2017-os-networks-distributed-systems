/**
 * WorstFitMemManager.java
 *
 * @author Cecelia Wisniewska
 * 
 * Finds address of a space which is the largest block of unallocated memory
 * 
 */

package CO2017.exercise2.cw374;

public class WorstFitMemManager extends MemManager{
   
    public WorstFitMemManager(int s){
        super(s);
    }

    @Override
    protected int findSpace(int s){
        int startAddress=0;
        for (int i=0; i<_memory.length;i++){
            if (countFreeSpacesAt(i) == _largestSpace){
                startAddress=i;
                break;
            }
        }
        return startAddress;
    }  
}
