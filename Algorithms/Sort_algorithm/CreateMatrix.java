import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
public class CreateMatrix {
    private static BufferedReader in = null;
    private static int rows = 0;
    private static int columns = 0;
    private static String [][] matrix = null;

    public static void main(String []args) throws Exception {
        try {
            String filepath = "G:\\UGA\\2fall\\8500Algorithms\\Kmeans\\Archaea.txt";
            int row = 0;
            int column=0;
            int No_Of_row = 2;
            String [][] matrix= null;
            String newData[][] = null;
            in = new BufferedReader(new FileReader(filepath));
            String line = null;
          
        
            while((line=in.readLine()) !=null) { 
                String [] tokens = line.split("\t");
                matrix = new String[No_Of_row][tokens.length];
                
               
                newData= new String[row][tokens.length];
                
                	for (int j=0; j<tokens.length;j++) {
                		newData[row][j] = tokens[j];
                		
                	}
                	row++;}
                	
            
                
                
         
          for (int i=0; i< newData.length;i++) {
        	  for (int j=0; j< newData[i].length; j++)
        	  System.out.println(newData[i][j]);
          }
        System.out.println();
        }
     catch (FileNotFoundException e) {
            System.out.println("file is not found");
            
        }
   
}

}


