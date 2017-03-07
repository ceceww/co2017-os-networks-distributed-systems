package CO2017.exercise2.cw374;

import java.util.Scanner;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *
 * @author cecew
 */
public class QueueHandler implements Runnable {
    ThreadPoolExecutor e;
    MemManager m;
    String f;
  
    public QueueHandler(ThreadPoolExecutor e, MemManager m, String f){
        this.e=e;
        this.m=m;
        this.f=f;
    }
            
    @Override
      public void run(){
      try (Scanner file = new Scanner(f)) {
      int delay, size, rt;
      char pid;
      System.out.println("In QueueHandler");
          }
      }
}
