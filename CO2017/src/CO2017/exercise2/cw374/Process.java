
package CO2017.exercise2.cw374;

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
    public Process(MemManager m, char i, int s, int r){}
    
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
        //return "X:A+S";
        return String.valueOf(id) + ":" + String.valueOf(address) + "+" + String.valueOf(size);
    }
    
    public void run(){
       System.out.println(this + " is waiting to run.");
       memManager.allocate(this); //allocate memory to process
       System.out.println(this + " is running.");
       try{ Thread.sleep(100*runtime); } catch (InterruptedException e){}
       memManager.free(this);
       System.out.println(this + " has finished.");
    }
}
