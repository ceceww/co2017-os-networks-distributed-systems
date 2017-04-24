
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author cecew
 */
public class SquareServer {
    // Work out the answer
  public static int square(int n) {
    return n*n;
  }

  public static void main (String[] args) {
    int port = 21341;

    try (ServerSocket server = new ServerSocket(port)) {
      while (true) {
        System.out.println("Waiting for client...");
        Socket client = server.accept();

        // get and display client's IP address
        InetAddress clientAddress = client.getInetAddress();
        System.out.println("Client from " + clientAddress + " connected.");

        Writer out = new OutputStreamWriter(client.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream(),"UTF-8"));

        int num = 999;
        do {
          num = Integer.parseInt(in.readLine());

          if (num != 999) {
            System.out.printf("Client requests square of %d%n",num);
    
            int ans = square(num);

            out.write(String.format("%d%n",ans));
            out.flush();
          }
        } while (num != 999);
        System.out.printf("Service to %s completed%n",
                          clientAddress);
        client.close();
      }
    } catch (IOException e) {
      System.err.println(e);
    }
  }
}
