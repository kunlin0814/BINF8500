import java.io.*; 
import java.util.*; 
import java.lang.*; 
public class Alignment_example {  

public static void main(String[] args) throws IOException { 	
	

	// The attachment is the script for the sequence alignment.
	//This script takes args[0] as our first sequence and args[1] as our second sequence that is fasta format, and the arg[2] is the file name you want to put on your output file.
	// The output file contains the length of the first sequence and the second sequence and the best score of this alignment and the alignment result.
	// The upper line is the first sequence, and the lower line is the second sequence
	// If there is a mismatch or a gap, it will show * sign.

	
	String gene1 = ""; 
	int misMatchPenalty = 0; 
	int gapPenalty = -1;
	int Match = 1 ;
	ArrayList <String> text = readString(args[0]);

	for (int i=1; i< text.size();i++) {
		for (int j= 0 ; j < text.get(i).length(); j++) {
			char sequence =Character.toUpperCase(text.get(i).charAt(j));
			gene1+= sequence;
		}
	}
	
	ArrayList <String> text2 = readString(args[1]);
	String gene2 = "" ; 
	for (int i=1; i< text2.size();i++) {
		for (int j= 0 ; j < text2.get(i).length(); j++) {
			char sequence =Character.toUpperCase(text2.get(i).charAt(j));
			gene2+= sequence; }
	}
		getMaximalValue(args[2], gene1, gene2, Match, misMatchPenalty, gapPenalty);


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

public static int getMaximum (int [] score) {
	int maximum = score[0];
	for (int i=0; i< score.length; i++) {
		if (score[i]> maximum) {
			maximum = score[i];
		}	
	}
	return maximum;
}	
public static void printMatrix(int[][] m) {

	for(int i=0; i<m.length;i++) {
		for (int j=0; j<m[i].length;j++) {
			System.out.print(m[i][j]+"\t");
		}
		System.out.println();
	}
}
//function to find out the minimum penalty 
public static void getMaximalValue(String output, String x, String y,int Match, int misMatch, int gap) throws IOException {

	//1.  setting up the a score matrix
	//2.  matrix with score, filling up the matrix with scores and directions, 
	//    including score matrix and trace-back matrix
	//3.  optimal alignment, use the trace-back matrix  
	BufferedWriter bw = null;
	File file = new File(output);
	FileWriter fw = new FileWriter(file);
	bw = new BufferedWriter(fw);
	int i, j; 
	int m = x.length(); // length of gene1 
	int n = y.length(); // length of gene2 

	// table for storing optimal 
	// substructure answers 
	int dp[][] = new int[n + m + 1][n + m + 1]; 
	bw.write("x length is "+ m + " bases\n");
	bw.write("y length is "+ n + " bases\n");
	//for (int[] x1 : dp) 
	//Arrays.fill(x1, 0); // fill up 0

	// initialize the table, get the maximal gaps scores for rows and columns
	// then calculate the possibility value for each blank, can start from column or start from row


	for (i = 0; i <= (n + m); i++) { 
		dp[i][0] = i * gap; // fill up the first row to calculate the scores for gap 
		dp[0][i] = i * gap; // fill up the first column to calculate the scores for gap
	}
	
	// calculate the minimum penalty 
	for (i = 1; i <= m; i++) {
		int [] each_blank = new int[3];
		for (j = 1; j <= n; j++) { 
			if (x.charAt(i - 1) == y.charAt(j - 1)) { 
				dp[i][j] = dp[i - 1][j - 1] + 1 ; } // diagonal   
			else {
				each_blank[0]= dp[i - 1][j - 1] + misMatch;
				each_blank[1]= dp[i - 1][j] + gap ;
				each_blank[2]= dp[i] [j - 1] + gap ;	

				dp[i][j]= getMaximum(each_blank);
				/* dp[i][j]= Math.min(Math.min(dp[i - 1][j - 1] + misMatch, dp[i - 1][j] + gap) // go down  
			                   , dp[i][j - 1] + gap ); */ }   //go right 
		} 
	}
	//printMatrix(dp);
	// Reconstructing the solution 
	int maxLength = n + m; // maximum possible length   
	i = m; // m = gene1 length
	j = n; // n = gene2 length

	int xpos = maxLength; 
	int ypos = maxLength; 

	// Final answers for the respective strings, moving backward 
	char xgene[] = new char[maxLength + 1];  
	char ygene[] = new char[maxLength + 1]; 
	
	
	// Reconstruct the alignment sequence from backward 
	while ( !(i == 0 || j == 0)) { // 
		int prvScore = dp[i][j];
		int diagMatch = dp[i - 1][j - 1] + Match ;
		int diagMismatch = dp[i - 1][j - 1] + misMatch;
		int horizontal = dp[i - 1][j] + gap ;
		int vertical = dp[i][j - 1] + gap ; 
		
		
		if (x.charAt(i - 1)==(y.charAt(j - 1))&& prvScore == diagMatch) { // if two bases are the same
			xgene[xpos--] = x.charAt(i - 1); 
			ygene[ypos--] = y.charAt(j - 1); 
			i--; j--; } 
		else if (x.charAt(i - 1) != y.charAt(j - 1)&& prvScore == diagMismatch ) { // score from mismatch
			xgene[xpos--] = x.charAt(i - 1); 
			ygene[ypos--] = y.charAt(j - 1); 
			i--; j--; } 
		else if (prvScore == horizontal) { // score from horizontal
			xgene[xpos--] = x.charAt(i - 1); 
			ygene[ypos--] = '-'; 
			i--; } 
		else if (prvScore == vertical) { // score from vertical
			xgene[xpos--] = '-'; 
			ygene[ypos--] = y.charAt(j - 1); 
			j--; } 
		else if (x.charAt(i - 1) == y.charAt(j - 1)&& prvScore == diagMatch && prvScore == horizontal) {
			xgene[xpos--] = x.charAt(i - 1); 
			ygene[ypos--] = y.charAt(j - 1); 
			i--; j--;
		}
		else if (x.charAt(i - 1) != y.charAt(j - 1)&& prvScore == diagMismatch && prvScore == horizontal) {
			xgene[xpos--] = x.charAt(i - 1); 
			ygene[ypos--] = y.charAt(j - 1); 
			i--; j--; }
		
		else if (x.charAt(i - 1) == y.charAt(j - 1)&& prvScore == diagMatch && prvScore == vertical) {
			xgene[xpos--] = x.charAt(i - 1); 
			ygene[ypos--] = y.charAt(j - 1); 
			i--; j--;
		}
	    else if (x.charAt(i - 1) != y.charAt(j - 1)&&prvScore == diagMismatch && prvScore == vertical) {
			xgene[xpos--] = x.charAt(i - 1); 
			ygene[ypos--] = y.charAt(j - 1); 
			i--; j--; 
		}
		else if (x.charAt(i - 1) != y.charAt(j - 1)&& prvScore == diagMismatch && prvScore == horizontal&& prvScore == vertical) {
			xgene[xpos--] = x.charAt(i - 1); 
			ygene[ypos--] = y.charAt(j - 1); 
			i--; j--; 
		}
		else if ( x.charAt(i - 1) == y.charAt(j - 1)&& prvScore == diagMatch && prvScore == horizontal && prvScore == vertical ) {
			xgene[xpos--] = x.charAt(i - 1); 
			ygene[ypos--] = y.charAt(j - 1); 
			i--; j--; 
		}
		else if ( prvScore == horizontal && prvScore == vertical ) { // if vertical and horizontal gives same score we choose horizontal
			xgene[xpos--] = x.charAt(i - 1); 
			ygene[ypos--] = '-'; 
			i--; } 
		
		
	} 

	//printMatrix(dp);
	while (xpos > 0) { 

		if (i > 0) {
			xgene[xpos--] = x.charAt(--i); }
		else {
			xgene[xpos--] = '-';} }


	while (ypos > 0) {

		if (j > 0) {

			ygene[ypos--] = y.charAt(--j);} 
		else {
			ygene[ypos--] = (int)'-'; } 
	}


	// Since we have assumed the answer to be n+m long,  
	// we need to remove the extra gaps in the starting id represents the index from which the arrays xans, yans are useful 
	int id = 1; 
	for (i = maxLength; i >= 1; i--) { 
		if ((char)ygene[i] == '-' &&  
				(char)xgene[i] == '-') { 
			id = i + 1; 
			break; } 
	} 
	//System.out.println(Arrays.toString(xgene));
	//System.out.println(Arrays.toString(ygene));
	bw.write("The highest Score in the aligning genes = "); 
	bw.write(dp[m][n] + "\n");

	if( m >50 || n >50) {
		int block, real_remain = 0;
		
		if (m >= n) {
			real_remain = m;
			block = m/50; }
		else {
			block = n/50;
			real_remain = n; }

		bw.write("The aligned species are :\n");
		//System.out.println("The aligned genes are :"); 

		for (int blocksize = 0; blocksize<block; blocksize++) {
			for (int x_base = id; x_base <(id+50); x_base++) {
				//System.out.print((char)xgene[blocksize+x_base]);
				bw.write((char)xgene[blocksize+x_base]);
			}
			bw.write('\n');
			//System.out.println();
			for (int y_base = id; y_base <(id+50); y_base++) {
				if ((char)xgene[blocksize+y_base]==(char)ygene[blocksize+y_base]) {
					bw.write(" ");
				}
				else {
					bw.write("*");
				}
				
				//bw.write("|");
				//System.out.print("|");
			}
			bw.write("\n");
			
			//System.out.println();   
			for (int y_base = id; y_base <(id+50); y_base++) {
				bw.write((char)ygene[blocksize+y_base]);
				//System.out.print((char)ygene[blocksize+y_base]);
			}
			bw.write("\n");
			bw.write("\n");
			//System.out.println();
		}
		
		for (int remaining=(50*block)+id; remaining < xgene.length; remaining++) {
			bw.write((char)xgene[remaining]);
			//System.out.print((char)xgene[remaining]);
		}
			bw.write("\n");
		//System.out.println();
		for (int remaining=(50*block+id); remaining < xgene.length; remaining++) {
			if ((char)xgene[remaining]==(char)ygene[remaining]) {
				bw.write(" ");
			}
			else {
				bw.write("*");
			}
			
			//System.out.print("|");
		}
		bw.write("\n");
		//System.out.println();
		for (int remaining=(50*block+id); remaining < ygene.length; remaining++) {
			bw.write((char)ygene[remaining]);
			//System.out.print((char)ygene[remaining]);
		}
	
		bw.close();
	}

	else {
		
		System.out.println("The aligned species are :"); 
		for (i = id; i <= maxLength; i++) { 
			System.out.print((char)xgene[i]); 
		} 
		System.out.print("\n"); 
		for (i = id; i <= maxLength; i++) 
		{ 
			System.out.print("|"); 
		} 
		System.out.print("\n"); 
			for (i = id; i <= maxLength; i++) 
			{ 

				System.out.print((char)ygene[i]); 
			} 
		}

	}
}

