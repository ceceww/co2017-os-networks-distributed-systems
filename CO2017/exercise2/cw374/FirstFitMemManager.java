/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CO2017.exercise2.cw374;

/**
 *
 * @author cecew
 */
public class FirstFitMemManager extends MemManager{
   
    //Constructor
    public FirstFitMemManager(int s){
     super(s);
    }

    
    //int s is the amount of memory needed for the process
    //returns the address of a space of at least size s.
    @Override
    public int findSpace(int s){
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
