/**
 * QueueHandler.java
 *
 * @author Cecelia Wisniewska
 * 
 * Handle a file of incoming process data
 * 
 */

package CO2017.exercise2.cw374;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        String fname = f;
        Path fpath = Paths.get(fname);
        try (Scanner file = new Scanner(fpath)) {
          int delay, size, rt;
          char pid;

          while (file.hasNextLine()) {
            Scanner line = new Scanner(file.nextLine());
            line.useDelimiter(":");
            delay = line.nextInt();
            pid   = line.next().charAt(0);
            size  = line.nextInt();
            rt    = line.nextInt();
              try {
                  Thread.sleep(100*delay);
              } catch (InterruptedException ex) {
                  Logger.getLogger(QueueHandler.class.getName()).log(Level.SEVERE, null, ex);
              }
              Process p = new Process(m,pid,size,rt);
              e.execute(p);
            line.close();
          }
        e.shutdown();

        e.awaitTermination(5L, TimeUnit.SECONDS); //wait until all tasks have completed before terminating

          file.close();
        } catch (NoSuchFileException e) {
          System.err.println("File not found: "+fname);
          System.exit(1);
        } catch (IOException e) {
          System.err.println(e);
          System.exit(1);

            } catch (InterruptedException ex) {
                Logger.getLogger(QueueHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
}
