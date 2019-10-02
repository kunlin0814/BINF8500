import java.io.*; 
import java.util.*; 
import java.lang.*; 
public class Alignment_example {  

public static void main(String[] args) throws IOException { 	
	String gene_1 ;
	String gene_2 ;
	String str = ""; 
	int misMatchPenalty = 1; 
	int gapPenalty = 2; 	
	ArrayList <String> text = readString("C:\\Users\\abc73_000\\Desktop\\Fasta\\HIV1a.fasta");

	for (int i=1; i< text.size();i++) {
		for (int j= 0 ; j < text.get(i).length(); j++) {
			char sequence =text.get(i).charAt(j);
			str+= sequence;
		}
	}

	ArrayList <String> text2 = readString("C:\\Users\\abc73_000\\Desktop\\Fasta\\HIV1b.fasta");
	String str2 = "" ; 
	for (int i=1; i< text2.size();i++) {
		for (int j= 0 ; j < text2.get(i).length(); j++) {
			char sequence =text2.get(i).charAt(j);
			str2+= sequence; }
	}
	

	getMinimumPenalty(str, str2, misMatchPenalty, gapPenalty);


}
public static ArrayList <String> readString(String file) throws IOException{ // a function to read the data into an arraylist

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

public static int getMinimal (int [] score) {
	int minimal = score[0];
	for (int i=0; i< score.length; i++) {
		if (score[i]<=minimal) {
			minimal = score[i];
		}	
	}
	return minimal;
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
public static void getMinimumPenalty(String x, String y, int misMatch, int gap) throws IOException {

	//1.  setting up the a score matrix
	//2.  matrix with score, filling up the matrix with scores and directions, 
	//    including score matrix and trace-back matrix
	//3.  optimal alignment, use the trace-back matrix  
	BufferedWriter bw = null;
	File file = new File("alignment.txt");
	FileWriter fw = new FileWriter(file);
	bw = new BufferedWriter(fw);
	int i, j; 
	int m = x.length(); // length of gene1 
	int n = y.length(); // length of gene2 

	// table for storing optimal 
	// substructure answers 
	int dp[][] = new int[n + m + 1][n + m + 1]; 
	System.out.println("x gene length is "+ m + " bases");
	System.out.println("y gene length is "+ m + " bases");
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
				dp[i][j] = dp[i - 1][j - 1];} // diagonal   
			else {

				each_blank[0]= dp[i - 1][j - 1] + misMatch;
				each_blank[1]= dp[i - 1][j] + gap ;
				each_blank[2]= dp[i] [j - 1] + gap ;	

				dp[i][j]= getMinimal(each_blank);
				/* dp[i][j]= Math.min(Math.min(dp[i - 1][j - 1] + misMatch, dp[i - 1][j] + gap) // go down  
			                   , dp[i][j - 1] + gap ); */ }   //go right 
		} 
	}

	// Reconstructing the solution 
	int maxLength = n + m; // maximum possible length   
	i = m; // m = gene1 length
	j = n; // n = gene2 length

	int xpos = maxLength; 
	int ypos = maxLength; 

	// Final answers for the respective strings, moving backward 
	char xgene[] = new char[maxLength + 1];  
	char ygene[] = new char[maxLength + 1]; 

	while ( !(i == 0 || j == 0)) { 
		if (x.charAt(i - 1) == y.charAt(j - 1)) { // if two bases are the same
			xgene[xpos--] = x.charAt(i - 1); 
			ygene[ypos--] = y.charAt(j - 1); 
			i--; j--; } 
		else if (dp[i - 1][j - 1] + misMatch == dp[i][j]) { // score from mismatch
			xgene[xpos--] = x.charAt(i - 1); 
			ygene[ypos--] = y.charAt(j - 1); 
			i--; j--; } 
		else if (dp[i - 1][j] + gap == dp[i][j]) { // score from horizontal
			xgene[xpos--] = x.charAt(i - 1); 
			ygene[ypos--] = '-'; 
			i--; } 
		else if (dp[i][j - 1] + gap == dp[i][j]) { // score from vertical
			xgene[xpos--] = '-'; 
			ygene[ypos--] = y.charAt(j - 1); 
			j--; } 
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

	System.out.print("Minimum Penalty in aligning the genes = "); 
	System.out.print(dp[m][n] + "\n");

	if( m >50 || n >50) {
		int block;
		if (m > n) {
			block = m/50; }
		else {
			block = n/50;}

		bw.write("The aligned genes are :\n");
		//System.out.println("The aligned genes are :"); 

		for (int blocksize = 0; blocksize<block; blocksize++) {
			for (int x_base = id; x_base <(id+50); x_base++) {
				//System.out.print((char)xgene[blocksize+x_base]);
				bw.write((char)xgene[blocksize+x_base]);
			}
			bw.write('\n');
			//System.out.println();
			for (int y_base = id; y_base <(id+50); y_base++) {
				bw.write("|");
				//System.out.print("|");
			}
			bw.write("\n");
			//System.out.println();   
			for (int y_base = id; y_base <(id+50); y_base++) {
				bw.write((char)ygene[blocksize+y_base]);
				//System.out.print((char)ygene[blocksize+y_base]);
			}
			bw.write("\n");
			//System.out.println();
		}
		for (int remaining=50*block+id; remaining < id+m; remaining++) {
			bw.write((char)xgene[remaining]);
			//System.out.print((char)xgene[remaining]);
		}
			bw.write("\n");
		//System.out.println();
		for (int remaining=(50*block+id); remaining < id+m; remaining++) {
			bw.write("|");
			//System.out.print("|");
		}
		bw.write("\n");
		//System.out.println();
		for (int remaining=(50*block+id); remaining < id+m; remaining++) {
			bw.write((char)ygene[remaining]);
			//System.out.print((char)ygene[remaining]);
		}
	
		bw.close();
	}

	else {
		
		System.out.println("The aligned genes are :"); 
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

