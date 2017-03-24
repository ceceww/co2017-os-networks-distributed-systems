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
public class MessageServerHandler implements Runnable{
    
    char id;
    static char nextId = 'A';
    MessageBoard msgboard;
    Socket client;
    Writer out;
    BufferedReader in;
        
    public MessageServerHandler(MessageBoard b, Socket cl){
        id = nextId++;
        msgboard = b;
        client = cl;
        
        try {
            out = new OutputStreamWriter(client.getOutputStream());
            in = new BufferedReader(new InputStreamReader(client.getInputStream(),"UTF-8"));
        } 
        catch (IOException e) {
            System.err.printf("Failed to create Data streams to %s%n", cl.getInetAddress());
            System.err.println(e);
            System.exit(1);
        }
    }
    
    public char getId(){
        return id;
    }
    
    public void setId(char id){
        this.id = id;
    }
    
    @Override
    public void run(){
        try{
            String command;
            String response;
       
              do{
                command = in.readLine();
                System.out.println(command);
                
                if(command.equals("LIST")){
                   response = msgboard.ListHeaders().toString();
                    out.write(String.format("%s%n",response));
                    out.flush();
                }
                
                if(command.contains("SEND:")){
                //   String msgbody = command.substring(command.indexOf(":")+1);
               //    MessageHeader mh = new MessageHeader(this.id, Integer.parseInt(in.readLine()));
                   response = "";
                   out.write(String.format("%s%n",response));
                   out.flush();
                   // msgboard.SaveMessage(msgheader,msgbody);
                }
                
                if(command.contains("GET:")){
                  /*  String msgheader = command.substring(command.indexOf(":")+1);
                    char threadid = msgheader.charAt(0);
                    int msgid = msgheader.charAt(2);
                    MessageHeader mh = new MessageHeader(threadid,msgid);
                    response = msgboard.GetMessage(mh);
                    out.write(response);
                    out.flush();*/
                  response = "GET works";
                    out.write(String.format("%s%n", response));
                    out.flush();
                }
          }
           while(!command.equals("BYE"));
            client.close(); 
        }
        catch (IOException e){
        System.err.println(e);
        }
        
    }
}
