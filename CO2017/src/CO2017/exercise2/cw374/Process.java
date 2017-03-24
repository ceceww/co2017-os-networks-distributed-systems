/**
 * Process.java
 *
 * @author Cecelia Wisniewska
 * 
 * Represents a process
 * 
 */

package CO2017.exercise2.cw374;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Process implements Runnable{
    MemManager memManager;
    char id;
    int size;
    int runtime;
    int address=-1;
 
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
    
    //string representation of the process
    @Override
    public String toString(){
        String paddedAddress= String.format("%3d", address);
        if(address==-1){
            paddedAddress= String.format("%3c", 'U');
        }
        String paddedSize = String.format("%2d", size);
        return String.valueOf(id) + ":" + paddedAddress + "+" + paddedSize;
    }
    
    @Override
    public void run(){
       System.out.println(this.toString() + " waiting to run.");
       try {
           memManager.allocate(this);
       } catch (InterruptedException ex) {
           Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
       }
       System.out.println(this.toString() + " running.");
       try{ Thread.sleep(100*runtime); } catch (InterruptedException e){} //simulates the running of the process
       memManager.free(this);
       System.out.println(this.toString() + " has finished.");
    }
    
}
