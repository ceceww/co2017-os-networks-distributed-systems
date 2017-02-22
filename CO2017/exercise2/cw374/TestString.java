/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CO2017.exercise2.cw374;

import java.util.Arrays;

/**
 *
 * @author cecew
 */
public class TestString {
    char[] _memory;
    
    TestString(int s){
        _memory = new char[s];
         for (int i=0; i<s; i++){
            _memory[i] = '.';
        }
    }
    
     @Override
     public String toString(){
     int noOfRows = _memory.length/20; //no of rows of the 2D array
     char[][] mem = new char[noOfRows][20]; //mem is the 2D array (in this case [5][20], 5*20=100 is size of memory)
     int k=0; //incremented in for loop to point to data stored in _memory[k]
      
        for(int i=0; i<noOfRows; i++){ //iterate over rows
            for (int j=0; j<20; j++){ //iterate over columns
                        mem[i][j] = _memory[k];
                        k++;
        }
      } 
      
     //convert mem[][] to string representation
     String str = " 0|"; 
     for (int i=0; i<noOfRows; i++){
         for (int j=0; j<20; j++){
            str += String.valueOf(mem[i][j]);
            if(j==19){ str+="|";}
         }
         if(i<noOfRows-1){str += "\n" + (i+1)*20 + "|";} //update str to contain the scale of memory going up by 20
     }
        return str;
    }
     
    public static void main (String[] args){
        TestString ts = new TestString(100);
        System.out.println(ts.toString());
    }
}
