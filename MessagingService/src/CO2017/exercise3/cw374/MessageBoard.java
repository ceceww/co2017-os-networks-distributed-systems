package CO2017.exercise3.cw374;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class that stores the state of the message board.
 * 
 * @author Cecelia Wisniewska
 */

public class MessageBoard {  
    Map<MessageHeader,String> messages = new ConcurrentHashMap<>();
    
    /**
     * Create a new MessageBoard
     */
    public MessageBoard(){}
    
    /**
    * Add a message to the board
    * @param mh the message header
    * @param msg the message body
    */
    synchronized void SaveMessage(MessageHeader mh, String msg){
        if (!messages.containsKey(mh)){
            messages.put(mh, msg);
        }
    }
    
     /**
      * Get the body of a message
      * @param mh the message header
      */
    String GetMessage(MessageHeader mh){
        if(messages.get(mh)==null){
            return "No such message";
        }
        else {
            return messages.get(mh);
        }
    }
    
     /**
      * The set of all message headers
      */
    Set<MessageHeader> ListHeaders(){
        return messages.keySet();
    }
}
