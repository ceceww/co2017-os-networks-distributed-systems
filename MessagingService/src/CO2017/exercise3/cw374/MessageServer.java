package CO2017.exercise3.cw374;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Class to control server side of the MessageBoard
 * 
 * @author Cecelia Wisniewska
 */

public class MessageServer {
    
    public MessageServer(){}
    
    public static void main (String[] args) throws IOException {
        int port = Integer.parseInt(args[0]);
        
        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Starting Message server on port " + port);
            
            // create a new message board
            MessageBoard b = new MessageBoard();
        
            while(true){
            Socket client = server.accept();
            // get and display client's IP address
            InetAddress clientAddress = client.getInetAddress();

            System.out.println("connection: " + clientAddress);

            // same message board used for each client that connects
            // so all messages retrieved/sent to same message board.
            MessageServerHandler msh = new MessageServerHandler(b, client);

            ThreadPoolExecutor ex = (ThreadPoolExecutor) Executors.newCachedThreadPool(); 
            ex.execute(msh);
            }
        
        } 
        catch (IOException e) {
         System.err.println(e);
        }
  }
        
}
