import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Math;
import java.util.*;
public class find_motif {
	
	public static void main (String[] args) throws IOException { 	
		int A_number  ;
		int C_number  ;
		int G_number  ;
		int T_number  ;
	//Output:
	//1. Frequency and/or probability matrix
	//2. PSSM (use log-ratio scores in the log
	//3. Scores for all motif sequences in the input alignment
	//4. List of all positions in the analyzed sequence with score ≥S0.
	//- For each motif occurrence, list position (start-end), actual sequence, and score.
	ArrayList <String> text = readString("/Volumes/Research_Data/UGA/2Fall/8500Algorithms/Motif_finding/sigma54.fasta");
	String freqMatrix [][]= new String [(text.size()/2)+1][5];
	
	freqMatrix[0][0] = "Pos";
	freqMatrix[0][1] = "A";
	freqMatrix[0][2] = "G";
	freqMatrix[0][3] = "T";
	freqMatrix[0][4] = "C";
	for (int i = 1 ; i < (text.size()/2+1) ; i++) {
		A_number = 0 ;
		C_number = 0 ;
		G_number = 0 ;
		T_number = 0 ;
		for (int j = 0 ; j <text.get(2*i-1).length();j++) {
			if (Character.toUpperCase(text.get(2*i-1).charAt(j)) == 'A') {
				A_number+=1; }
			else if(Character.toUpperCase(text.get(2*i-1).charAt(j))== 'G') {
				G_number+=1;
			}
			else if(Character.toUpperCase(text.get(2*i-1).charAt(j))== 'T') {
				T_number+=1;
			}
			else if(Character.toUpperCase(text.get(2*i-1).charAt(j))== 'C') {
				C_number+=1;
			}	
		}
		freqMatrix[i][0] = String.valueOf(i) ;
		freqMatrix[i][1] = String.valueOf(A_number);
		freqMatrix[i][2] = String.valueOf(G_number);
		freqMatrix[i][3] = String.valueOf(T_number);
		freqMatrix[i][4] = String.valueOf(C_number);
	}
	printMatrix(freqMatrix);
	
	
	
}

	public static ArrayList <String> readString(String file) throws IOException{ 
		// a function to read the data into an arraylist

		ArrayList <String> lists = new ArrayList<String>();
		BufferedReader objReader = null;
		try {
			String strCurrentLine;
			objReader = new BufferedReader(new FileReader(file));
			//File f = new File(file);
			//Scanner s = new Scanner(f);
			while ((strCurrentLine = objReader.readLine()) != null) {
				//while (s.hasNextLine()) {
				lists.add(strCurrentLine);}
		}
		catch(FileNotFoundException e) {
			System.out.println("File not found");

		}
		return lists;
	}
	public static void printMatrix(String[][] m) {
		
		for(int i=0; i<m.length;i++) {
			for (int j=0; j<m[i].length;j++) {
				System.out.print(m[i][j]+"\t");
			}
			System.out.println();
		}
	}
	}
