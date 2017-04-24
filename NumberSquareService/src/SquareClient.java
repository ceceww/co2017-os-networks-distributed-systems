/**
 *
 * @author cecew
 */
import java.net.*;
import java.io.*;
public class SquareClient {
    public static void main(String[] args) throws IOException {
        String servername = "localhost";
        int port = 21341;
        try {
            Socket server = new Socket(servername,port);
        
        System.out.println("Connected to " + server.getInetAddress());
        
        BufferedReader in = new BufferedReader (new InputStreamReader(server.getInputStream(),"UTF-8"));
        Writer out = new OutputStreamWriter(server.getOutputStream());
        
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        
        int num = 999; // sentinel
        int result;
        
        do {
        System.out.print("Enter number to square: ");
        num = Integer.parseInt(stdin.readLine());

        out.write(String.format("%d%n",num));
        out.flush();

        if (num != 999) {
          result = Integer.parseInt(in.readLine());
          System.out.printf("Server says %d x %d = %d%n",
                            num,
                            num,
                            result);
        }
      } while (num!=999);
      System.out.println("Client shutdown");
      server.close();
        }
      catch (IOException e) {
          System.out.println("No servers to connect to");
      }
      catch (NumberFormatException e) {}
    }
}

