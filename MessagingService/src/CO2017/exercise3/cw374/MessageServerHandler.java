package CO2017.exercise3.cw374;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;

/**
 * Class to handle server side of a simple messaging client
 * 
 * @author Cecelia Wisniewska
 */

public class MessageServerHandler implements Runnable{
    char id;
    static char nextId = 'A';
    MessageBoard msgboard;
    Socket client;
    Writer out;
    BufferedReader in;
     
     /**
      * Create a new MessageServerHandler
      *
      * @param b the MessageBoard of this handler
      * @param cl the client of handler
      */
    public MessageServerHandler(MessageBoard b, Socket cl){
        id = nextId++;
        msgboard = b;
        client = cl;
        
        //Creating data streams to client
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
    
    /**
     * Main interaction with client
     */
    @Override
    public void run(){
        try{
            
            String command;
            String response;
            
            /**
             * Execute this code while command is not "BYE"
             */
              do{
                command = in.readLine();
                if(!command.isEmpty()){
                System.out.println(command);
                }
                
                /**
                 * Send back a list of the message headers (one per line) and terminated by a single "." on a line.
                 */
                if(command.equals("LIST")){
                    String msgboardstr = msgboard.ListHeaders().toString().replaceAll("\\[", "")
                           .replaceAll("\\]","").replaceAll(" ", "").replaceAll(",", "\r\n");
                    response = msgboardstr;
                    
                    if (msgboard.ListHeaders().isEmpty()){
                        out.write(String.format("%s%n","."));
                        out.flush();
                    }
                    
                    else{ 
                    out.write(String.format("%s%n",response));
                    out.write(String.format("%s%n","."));
                    out.flush();
                   }
                }
                
                /**
                 * Convert msgid into a full message header, and store the supplied message. 
                 * Ignore the message if the header is already in use.
                 */
                if(command.contains("SEND:")){
                   try{
                   String msgidstr = command.substring(command.indexOf(":")+1,command.lastIndexOf(":"));
                   int msgid = Integer.valueOf(msgidstr);
                   MessageHeader mh = new MessageHeader(this.id, msgid);
                   String msgbody = command.substring(command.lastIndexOf(":")+1);
                   msgboard.SaveMessage(mh,msgbody);
                   response = ".";
                   out.write(String.format("%s%n",response));
                   out.flush();
                   }
                   catch (NumberFormatException e){}
                 
                }
                
                /**
                 * Retrieve the specified message and return it to the client
                 * If it does not exist, send ERR.
                 */
                if(command.contains("GET:")){
                  String smh = command.substring(command.indexOf(":")+1); //smh is message header as string
                  char threadid = smh.charAt(0);
                  String msgidstr = smh.replaceAll("[^0-9]", ""); // returns msgid as string
                  int msgid = Integer.valueOf(msgidstr);
                  MessageHeader mh = new MessageHeader(threadid,msgid);
                  String msgbody = msgboard.GetMessage(mh);
                  if (msgbody.equals("No such message")){
                      System.out.println(smh + "=null");
                      System.out.println("ERR");
                  }
                  else{
                      System.out.println(smh + "=" + msgbody);
                      System.out.println("OK");
                  }
                    out.write(String.format("%s%n", msgbody));
                    out.flush();
                  
                }
          }
              /**
               * When command from client is "BYE"
               * Close the connection and terminate the handler thread.
               */
           while(!command.equals("BYE"));
            client.close(); 
        }
        catch (IOException | NullPointerException e){
        System.out.println("Connection dropped unexpectedly.");
        }
        
    }
}
