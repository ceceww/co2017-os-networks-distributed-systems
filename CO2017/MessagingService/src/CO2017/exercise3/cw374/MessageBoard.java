package CO2017.exercise3.cw374;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author cecew
 */
public class MessageBoard {
    
   Map<MessageHeader,String> messages = new ConcurrentHashMap<>();
    
    public MessageBoard(){}
    
    synchronized void SaveMessage(MessageHeader mh, String msg){
        if (!messages.containsKey(mh)){
            messages.put(mh, msg);
        }
    }
    
    String GetMessage(MessageHeader mh){
        return messages.get(mh);
    }
    
    Set<MessageHeader> ListHeaders(){
        return messages.keySet();
    }
    
    //TEST
   /* public static void main (String[] args){
        MessageBoard mb = new MessageBoard();
        MessageHeader mh = new MessageHeader('A', 1);
        MessageHeader mh2 = new MessageHeader('B',2);
        mb.SaveMessage(mh, "Hello first message");
        mb.SaveMessage(mh2, "Hello second message");
        System.out.println(mb.ListHeaders());
        System.out.println(mb.GetMessage(mh));
        System.out.println(mb.GetMessage(mh2));

    }*/
    
}
