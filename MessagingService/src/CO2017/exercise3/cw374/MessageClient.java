package CO2017.exercise3.cw374;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;

/**
 * Class for the message client.
 * 
 * @author Cecelia Wisniewska
 */

public class MessageClient {
    
    /**
     * Create a new MessageClient
     */
    public MessageClient(){}
    
    /**
     * Main client behaviour
     */
    public static void main (String[] args){
        
        String servername = args[0];
        int port = Integer.parseInt(args[1]);
        
        try {
            Socket server = new Socket(servername,port);
            
            BufferedReader in = new BufferedReader (new InputStreamReader(server.getInputStream(),"UTF-8"));
            Writer out = new OutputStreamWriter(server.getOutputStream());
        
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
            
            
            String command;
            String result;
            int msgid = 0;
           
            /**
             * Execute this code while command is not "BYE"
             */
            do{
                 
                System.out.print("? ");
                 
                command = stdin.readLine();      
                
                /**
                 *  A new unique integer identifier is assigned to the message when it is sent over to the server
                 *  msgid is incremented each time this command is used
                 */
                if(command.contains("SEND:")){
                    msgid++;
                    out.write(String.format("%s", command.substring(0,5)) + String.format("%d",msgid) + 
                            String.format("%s%n",command.substring(command.indexOf(":"))));
                    out.flush();
                }
                
                if(command.contains("GET:")){
                    out.write(String.format("%s%n",command));
                    out.flush();
                }
               
                /**
                 * Continues to loop (reading in headers)
                 * Until a line with "." is read
                 */
                 if(command.contains("LIST")){
                    out.write(String.format("%s%n",command));
                    out.flush();
                    do {
                        result = in.readLine();
                        System.out.println(result);
                    }
                    while(!result.equals("."));
                }             
             
                 /**
                  * Line is read in once for all commands except for LIST which required a loop
                  */
                if(!command.equals("BYE")&&!command.contains("LIST")){
                    result = in.readLine();
                    System.out.println(result);
                }
             
            }
            
         /**
          * Terminate the client if the command was "BYE"
          * Otherwise wait for further user input
          */
         while(!command.equals("BYE"));
             out.write(String.format("%s%n",command));
             out.flush();
             server.close();
        }
      catch (IOException e) {
          System.out.println("No servers to connect to");
      }
    }
}
