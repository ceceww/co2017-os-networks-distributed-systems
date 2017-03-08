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
    
    //Constructor
    public MemManager(int s){
        //All data in array _memory is set to '.' indicating it is empty
        _memory = new char[s];
        for (int i=0; i<s; i++){
            _memory[i] = '.';
        }
        _largestSpace = s;
        _changed = true;
    }
    
    //Allocate memory for a process; block until space is available
    public synchronized void allocate(Process p) throws java.lang.InterruptedException{
      while(_largestSpace<p.size){ wait();}
        int freeAddress = findSpace(p.size);
        p.setAddress(freeAddress);
        for (int i=freeAddress;i<freeAddress+p.size;i++){
           _memory[i] = p.id;
       }
        _largestSpace = countFreeSpacesAt(freeAddress+p.size);
        _changed = true;
        notifyAll();
    }
    
    //Start at address pos and calculate the size of the contiguous empty space begining there.
    int countFreeSpacesAt(int pos){
        int count = 0;
       for (int i=pos;i<_memory.length;i++){
          if(_memory[i]=='.'){
              count++;
          }
          else{
              break;
          }
      }
      return count;
    }
    
    //Find an address in memory where s amount of space is available.
    protected abstract int findSpace(int s);
    
    //Free memory used by a process.
    public synchronized void free(Process p){
        if(p.getAddress()>-1){
        for (int i=p.getAddress(); i<(p.getAddress()+p.getSize()); i++){
            _memory[i] = '.';
        }
       
        _largestSpace = countFreeSpacesAt(p.getAddress());
      
        p.setAddress(-1);
        _changed = true;
        notifyAll();           
        }
    }
    
    //Return whether the state of memory has changed since the last invocation of toString
    public boolean isChanged(){
        if(_changed == true){
            return true;
        }
        else{
        return false;  }
    }
    
    //Generate a string representing the state of the Memory.
    @Override
    public String toString(){
        //Create a new StringBuffer initialized to contents of _memory
        StringBuffer sb = new StringBuffer(String.valueOf(_memory));
       
        int k=0;
        for(int i=0;i<_memory.length;i++){
           if(i%20==0){
               String padded = String.format("%3d|",i);
               sb.insert(i+k, padded);
               k+=4;
           }   
        }
        
        for(int i=24;i<=sb.length();i+=26){
            sb.insert(i, "|\n");
        }
        
        String paddedls = String.format("%3d", _largestSpace);
        sb.insert(sb.length(),"ls:" + paddedls);
        
        
         return sb.toString();
    }
    
}
