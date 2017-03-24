import java.net.*;
import java.io.*;

public class SquareHandlerServer {
  public static void main (String[] args) {
    int port = 21341;
    try (ServerSocket server = new ServerSocket(port)) {

      while (true) {
        System.out.println("Waiting for client...");
        Socket client = server.accept();

        // get and display client's IP address
        InetAddress clientAddress = client.getInetAddress();
        System.out.printf("Client from %s connected%n",
                          clientAddress);

        SquareHandler sh = new SquareHandler(client);
        sh.start();
      }
    } catch (IOException e) {
      System.err.println(e);
    }
  }
}

class SquareHandler extends Thread {
  Socket client;
  Writer out;
  BufferedReader in;

  public SquareHandler (Socket cl) {
    client = cl;
    try {
      out =
        new OutputStreamWriter(client.getOutputStream());
      in = new BufferedReader
        (new InputStreamReader(client.getInputStream(),
                               "UTF-8"));
    } catch (IOException e) {
      System.err.printf("Failed to create Data streams to %s%n",
                        cl.getInetAddress());
      System.err.println(e);
      System.exit(1);
    }
  }

  public static int square(int n) {
    return n*n;
  }

  public void run() {
    try {
      int num = 999;
      do {
        num = Integer.parseInt(in.readLine());

        if (num != 999) {
          System.out.printf("Client requests square of %d%n",
                            num);

          int ans = square(num);

          out.write(String.format("%d%n",
                                  ans));
          out.flush();
        }
      } while (num != 999);
      System.out.printf("Service to %s completed%n",
                         client.getInetAddress());
      client.close();
    } catch (IOException e) {
      System.err.println(e);
    }
  }
}
