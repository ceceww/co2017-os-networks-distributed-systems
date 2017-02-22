package CO2017.exercise2.cw374;

import java.util.Arrays;

/**
 *
 * @author cecew
 */
public abstract class MemManager {
    
    volatile boolean _changed; //boolean that records if the state of the memory has changed since the last time toString was invoked
    volatile int _largestSpace; //The size of the largest currently free block of memory
    char[] _memory; //Array representation of the memory
    int[] spaceSize; //to store the sizes of free spaces
    
    //Constructor
    public MemManager(int s){
        //All data in array _memory is set to '.' indicating it is empty
        _memory = new char[s];
        for (int i=0; i<s; i++){
            _memory[i] = '.';
        }
        _largestSpace = s;
        _changed = false;
    }
    
    //Allocate memory for a process; block until space is available
    public void allocate(Process p){
        int freeAddress = findSpace(p.size);
        for (int i=freeAddress;i<freeAddress+p.size;i++){
            p.memManager._memory[i] = p.id;
        }
        for (int i=0; i<_memory.length; i++){
            spaceSize[i] = countFreeSpacesAt(i);
        }
        Arrays.sort(spaceSize); //sort spaceSize into ascending order
        _largestSpace = spaceSize[spaceSize.length-1]; //_largestSpace = max value in spaceSize
        _changed = true;
    }
    
    //Start at address pos and calculate the size of the contiguous empty space begining there.
    int countFreeSpacesAt(int pos){
        int count = 0;
        for(int i=pos; i<_memory.length-pos; i++){
            if(_memory[i]=='.'){
                     count++;
            }
        }
        return count;
    }
    
    //Find an address in memory where s amount of space is available.
    abstract int findSpace(int s);
    
    //Free memory used by a process.
    public void free(Process p){
        for (int i=p.getAddress(); i<(p.getAddress()+p.getSize()); i++){
            _memory[i] = '.';
        }
        for (int i=0; i<_memory.length; i++){
            spaceSize[i] = countFreeSpacesAt(i);
        }
        Arrays.sort(spaceSize); //sort spaceSize into ascending order
        _largestSpace = spaceSize[spaceSize.length-1]; //_largestSpace = max value in spaceSize
        _changed = true;
    }
    
    //Return whether the state of memory has changed since the last invocation of toString
    public boolean isChanged(){
        if(_changed==true){
            return true;
        }
        return false;   
    }
    
    //Generate a string representing the state of the Memory.
    @Override
    public String toString(){
        return 
    }
    
}
