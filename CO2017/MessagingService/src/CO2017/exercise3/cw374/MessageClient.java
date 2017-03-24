package CO2017.exercise3.cw374;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;

/**
 *
 * @author cecew
 */
public class MessageClient {
    
    public MessageClient(){}
    
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
            
             do{
                command = stdin.readLine();
                out.write(String.format("%s%n",command));
                out.flush();
                
                if(command.contains("SEND:")){
                    msgid++;
                    out.write(String.format("%s%n", command));
                    out.write(String.format("%d%n",
                                  msgid));
                }
             
                if(!command.equals("BYE")){
                    result = in.readLine();
                    System.out.println(result);
                }
          
             
           }
         while(!command.equals("BYE"));
             System.out.println("Client shutdown");
              server.close();
        }
      catch (IOException e) {
          System.out.println("No servers to connect to");
      }
    }
}
