/**
 * MemManager.java
 *
 * @author Cecelia Wisniewska
 * 
 * Represents the current state of memory and methods to grant access to it
 * 
 */

package CO2017.exercise2.cw374;

import java.util.Arrays;

public abstract class MemManager {
    
    volatile boolean _changed; //records if the state of the memory has changed since the last time toString was invoked
    volatile int _largestSpace; //the size of the largest currently free block of memory
    char[] _memory; //array representation of the memory
    
    //Constructor
    public MemManager(int s){
        //all spaces in memory are initially empty, '.' denotes an empty space
        _memory = new char[s];
        for (int i=0; i<s; i++){
            _memory[i] = '.';
        }
        _largestSpace = s;
        _changed = true;
    }
    
    public synchronized void allocate(Process p) throws java.lang.InterruptedException{
        while(_largestSpace<p.size){ wait();} //block until space is available
        int freeAddress = findSpace(p.size);
        p.setAddress(freeAddress);
        for (int i=freeAddress;i<freeAddress+p.size;i++){
           _memory[i] = p.id; //writes the process id into the space
        }
        int[] spaces = new int[_memory.length];
        for (int i=0;i<_memory.length;i++){
           spaces[i] = countFreeSpacesAt(i);
         }
         Arrays.sort(spaces);
         _largestSpace = spaces[spaces.length-1];
          _changed = true;
          notifyAll(); 
    }
    
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
    
    //findSpace is overridden in BFMM, FFMM and WFMM
    protected abstract int findSpace(int s);
    
    public synchronized void free(Process p){
        if(p.getAddress()>-1){
            for (int i=p.getAddress(); i<(p.getAddress()+p.getSize()); i++){
                _memory[i] = '.'; //set the contents of _memory to '.' which was occupied by the process
            }
           int[] spaces = new int[_memory.length];
           for (int i=0;i<_memory.length;i++){
               spaces[i] = countFreeSpacesAt(i);
           }
           Arrays.sort(spaces);
            _largestSpace = spaces[spaces.length-1];
            p.setAddress(-1);
            _changed = true;
            notifyAll();           
        }
    }
    
    //return whether the state of memory has changed since the last invocation of toString
    public boolean isChanged(){
        if(_changed == true){
            return true;
        }
        else{
            return false;  
        }
    }
    
    //generate a string representing the state of the memory
    @Override
    public String toString(){
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
