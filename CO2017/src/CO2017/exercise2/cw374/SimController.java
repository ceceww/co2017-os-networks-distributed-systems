/**
 * SimController.java
 *
 * @author Cecelia Wisniewska
 * 
 * Controller class for the simulation
 * 
 * To compile (from inside src folder): javac *.java
 * To run use: java CO2017.exercise2.cw374.SimController arg1 arg2 arg3
 * 
 */

package CO2017.exercise2.cw374;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimController implements Runnable {
    
    static MemManager m;
    public SimController(){}
    static ThreadPoolExecutor ex;
    
    @Override
    public void run(){
        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(SimController.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(m.isChanged()){
                System.out.println(m.toString());
                m._changed=false;
            }
            if(ex.isTerminated()){ //while loop will break when ex has terminated
              break;
            }
        } 
    }
    
    public static void main (String[] args) throws InterruptedException {
 
        char mode = args[0].charAt(0);
        int memorySize = Integer.valueOf(args[1]);
        String fileName = args[2];
        switch(mode){
            case ('f') :
            System.out.println("Policy: FIRST fit");
            m = new FirstFitMemManager(memorySize);
            ex = (ThreadPoolExecutor) Executors.newCachedThreadPool();
            SimController scf = new SimController();
            Thread watcherf = new Thread(scf);
            watcherf.start();
            QueueHandler queuehandlerf = new QueueHandler(ex, m, fileName);
            Thread qf = new Thread(queuehandlerf);
            qf.start();
            watcherf.join();
            qf.join();
            System.out.println("All threads have terminated");
            break;
           
            case ('b') :
            System.out.println("Policy: BEST fit");
            m = new BestFitMemManager(memorySize);
            ex = (ThreadPoolExecutor) Executors.newCachedThreadPool();
            SimController scb = new SimController();
            Thread watcherb = new Thread(scb);
            watcherb.start();
            QueueHandler queuehandlerb = new QueueHandler(ex, m, fileName);
            Thread qb = new Thread(queuehandlerb);
            qb.start();
            watcherb.join();
            qb.join();
            System.out.println("All threads have terminated");
            break;

            case ('w') :
            System.out.println("Policy: WORST fit");
            m = new WorstFitMemManager(memorySize);
            ex = (ThreadPoolExecutor) Executors.newCachedThreadPool();
            SimController scw = new SimController();
            Thread watcherw = new Thread(scw);
            watcherw.start();
            QueueHandler queuehandlerw = new QueueHandler(ex, m, fileName);
            Thread qw = new Thread(queuehandlerw);
            qw.start();
            watcherw.join();
            qw.join();
            System.out.println("All threads have terminated");
            break;
        }            
    }
}
