/**
 * FirstFitMemManager.java
 *
 * @author Cecelia Wisniewska
 * 
 * Allocates the first free partition which can accommodate the process
 * 
 */

package CO2017.exercise2.cw374;

public class FirstFitMemManager extends MemManager{
   
    public FirstFitMemManager(int s){
        super(s);
    }
    
    @Override
    protected int findSpace(int s){
        int size;  
        int startAddress=0;
        for (int i=0; i<_memory.length; i++){
            size=this.countFreeSpacesAt(i);
            if(size>=s){
                startAddress=i;
                return startAddress;
            }
        }
        return startAddress;
    }   
}
