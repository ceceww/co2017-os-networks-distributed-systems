package CO2017.exercise2.cw374;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.NoSuchFileException;
import java.util.Scanner;

class DataReaderDemo {
  public static void main (String[] args) {
    if (args.length!=1){
      System.err.println("Usage: DataReaderDemo <file>");
      System.exit(1);
    }
    
    String fname = args[0];
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
        line.close();

        System.out.printf("delay: %s, ID: %c, size: %s, runtime: %s\n",
                          delay, pid, size, rt );

      }
      file.close();
    } catch (NoSuchFileException e) {
      System.err.println("File not found: "+fname);
      System.exit(1);
    } catch (IOException e) {
      System.err.println(e);
      System.exit(1);
    }
  }

}
