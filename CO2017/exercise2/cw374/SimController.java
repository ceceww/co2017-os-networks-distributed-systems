package CO2017.exercise2.cw374;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cecew
 */
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
              m._changed=false;}
              if(ex.isShutdown()){
              break;
          }
          }
         
    }
    
    
    public static void main (String[] args) throws InterruptedException{
        //Change to switch 
        char mode = args[0].charAt(0);
        int memorySize = Integer.valueOf(args[1]);
        String fileName = args[2];
        switch(mode){
            case ('f') :
           System.out.println("Policy: FIRST fit");
           m = new FirstFitMemManager(memorySize);
           ex = (ThreadPoolExecutor) Executors.newCachedThreadPool();
          
           SimController sc = new SimController();
           Thread watcher = new Thread(sc);
           watcher.start(); //Start the watcher thread
           
           QueueHandler queuehandler = new QueueHandler(ex, m, fileName);
           Thread q = new Thread(queuehandler);
           q.start();
           watcher.join();
           q.join();
          
           ex.shutdown();
           System.out.println("All threads have terminated");
        }
                
    }
}
