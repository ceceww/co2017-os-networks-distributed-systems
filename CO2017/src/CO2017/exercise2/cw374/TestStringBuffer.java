package CO2017.exercise2.cw374;

public class TestStringBuffer {
 
    char[] _memory;
    
    TestStringBuffer(int s){
        _memory = new char[s];
         for (int i=0; i<s; i++){
            _memory[i] = 'x';
        }
         
         _memory[19] = 'E';
         _memory[20] = 'A';
         
         _memory[39] = 'E';
         _memory[40] = 'A';
    }
    
      @Override
     public String toString(){
        StringBuffer sb = new StringBuffer(String.valueOf(_memory));
       
        int k=0;
        for(int i=0;i<_memory.length;i++){
           if(i%20==0){
               String padded = String.format("%3d|",i);
               sb.insert(i+k, padded);
               k+=4;
           }   
        }
        
        for(int i=24;i<=sb.length();i+=26){
            sb.insert(i, "|\n");
        }
        
        
         return sb.toString();
     }
     
     
    public static void main (String[] args){
        TestStringBuffer tsb = new TestStringBuffer(100);
        System.out.println(tsb.toString());
    }
}
