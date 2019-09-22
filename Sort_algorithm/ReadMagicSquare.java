import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadMagicSquare {
    public static String[][] create2DIntMatrixFromFile(String filename) throws Exception {
        String[][] matrix = null;
        String[][] new_matrix=null;

        // If included in an Eclipse project.
        //InputStream stream = ClassLoader.getSystemResourceAsStream(filename);
        BufferedReader buffer = null;
        buffer = new BufferedReader(new FileReader(filename));

        String line;
        int row = 0;
        int size = 0;
        int cnt =0 ;
        while ((line = buffer.readLine()) != null) {
            String[] vals = line.split("\t");
            //System.out.println(vals.length);
            // Lazy instantiation.
            cnt ++ ;
            if (matrix == null) {
                size = vals.length;
                matrix = new String[40][size];
            }
           
            for (int col = 0; col < size; col++) {
            	//new_matrix = new String[cnt][size];
            	matrix[row][col] = vals[col];
            
            }
            row++;
        }

        return matrix;
    }
    public static void main(String[] args) throws Exception {
        String[][] matrix = null;

   
     matrix = create2DIntMatrixFromFile("G:\\UGA\\2fall\\8500Algorithms\\Kmeans\\Archaea.txt");
     for (int i=0;i<matrix.length;i++) {
    	 for(int j=0; j<matrix[i].length;j++) {
    		 System.out.print(matrix[i][j]+" ");
    	 }
    	 System.out.println();
     }   
    }
}