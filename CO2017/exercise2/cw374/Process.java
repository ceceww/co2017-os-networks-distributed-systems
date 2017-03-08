
package CO2017.exercise2.cw374;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cecew
 */
public class Process implements Runnable{
    MemManager memManager;
    char id;
    int size;
    int runtime;
    int address=-1;
    
    //m - memmanager that will be used by this process i - ID of this process 
    //s - size of process r - runtime this process will use
    public Process(MemManager m, char i, int s, int r){     
        memManager = m;
        id = i;
        size = s;
        runtime = r;
    }    
    public int getAddress(){ 
        return address;
    }
    
    public char getID(){ 
        return id;
    }
    
    public int getSize(){
        return size;
    }
    
    public void setAddress(int a){
        address = a;
    }
    
    //String representation of the process
    @Override
    public String toString(){
        //pad the address to 3 characters, if undefined (AKA -1) paddedAddress=U
        String paddedAddress= String.format("%3d", address);
        if(address==-1){
            paddedAddress= String.format("%3c", 'U');
        }
        //pad the size to 2 characters
        String paddedSize = String.format("%2d", size);
        return String.valueOf(id) + ":" + paddedAddress + "+" + paddedSize;
    }
    
    @Override
    public void run(){
       System.out.println(this.toString() + " is waiting to run.");
        try {
            memManager.allocate(this); //allocate memory to process
        } catch (InterruptedException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
       System.out.println(this.toString() + " is running.");
       try{ Thread.sleep(100*runtime); } catch (InterruptedException e){}
       memManager.free(this);
       System.out.println(this.toString() + " has finished.");
    }
    
}
