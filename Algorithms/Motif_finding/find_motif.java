import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.lang.*;
public class find_motif {
	
	public static void main (String[] args) throws IOException { 	
		long strat_time =System.currentTimeMillis();
		double A_number  ;
		double C_number  ;
		double G_number  ;
		double T_number  ;
		double other ;
		
	//Output:
	//1. Frequency and/or probability matrix
	//2. PSSM (use log-ratio scores in the log
	//3. Scores for all motif sequences in the input alignment
	//4. List of all positions in the analyzed sequence with score ≥S0.
	//- For each motif occurrence, list position (start-end), actual sequence, and score.
	ArrayList <String> text = readString("G:\\UGA\\2fall\\8500Algorithms\\sigma54.fasta");
	int motif_size = text.get(1).length();
	double cut_off = 21;
	String freqMatrix [][]= new String [(text.get(1).length())+1][6];
	double PSSMMatrix [][]= new double [(text.get(1).length())+1][6];
	String strPSSMMatrix [][]= new String [(text.get(1).length())+1][6];
	String rvsfreqMatrix [][]= new String [(text.get(1).length())+1][6];
	double rvsPSSMMatrix [][]= new double [(text.get(1).length())+1][6];
	
	double Genome_A_number = 0  ;
	double Genome_C_number =0  ;
	double Genome_G_number =0 ;
	double Genome_T_number =0;
	double Genome_other =0 ;
	
	ArrayList <String> sequence = readString("G:\\UGA\\2fall\\8500Algorithms\\Scel-So0157-2.fasta");
	
	StringBuilder gene = new StringBuilder(""); 
	
	for (int i=1; i< sequence.size();i++) {
		for (int j= 0 ; j < sequence.get(i).length(); j++) {
			char nucleotide = Character.toUpperCase(sequence.get(i).charAt(j));
			if(nucleotide =='A') {
				Genome_A_number+=1;}
			
			else if(nucleotide =='G') {
				Genome_G_number+=1;
			}
			else if(nucleotide =='T') {
				Genome_T_number+=1;
			}
			else if(nucleotide =='C') {
				Genome_C_number +=1;
			}
			else {
				Genome_other+=1;
			}
			gene.append(nucleotide); }
	}
	int gene_length = gene.length();
	char gene_array[] = new char [gene_length];
	double GC = (Genome_C_number+Genome_G_number)/gene_length ;
// fill up the four matrices
	freqMatrix[0][0] = "Pos";
	freqMatrix[0][1] = "A";
	freqMatrix[0][2] = "G";
	freqMatrix[0][3] = "T";
	freqMatrix[0][4] = "C";
	freqMatrix[0][5] = "other";
	rvsfreqMatrix[0][0] = "Pos";
	rvsfreqMatrix[0][1] = "A";
	rvsfreqMatrix[0][2] = "G";
	rvsfreqMatrix[0][3] = "T";
	rvsfreqMatrix[0][4] = "C";
	rvsfreqMatrix[0][5] = "other";
/*
	rvsPSSMMatrix[0][0] = "Pos";
	rvsPSSMMatrix[0][1] = "A";
	rvsPSSMMatrix[0][2] = "G";
	rvsPSSMMatrix[0][3] = "T";
	rvsPSSMMatrix[0][4] = "C";
	rvsPSSMMatrix[0][5] = "other";
	*/
	strPSSMMatrix[0][0] = "Pos";
	strPSSMMatrix[0][1] = "A";
	strPSSMMatrix[0][2] = "G";
	strPSSMMatrix[0][3] = "T";
	strPSSMMatrix[0][4] = "C";
	strPSSMMatrix[0][5] = "other";
	
	for (int i = 0 ; i < (text.get(1).length()) ; i++) {
		
		A_number = 0 ;
		C_number = 0 ;
		G_number = 0 ;
		T_number = 0 ;
		other = 0 ;
		for (int j = 1 ; j <text.size()/2+1;j++) {
			if (Character.toUpperCase(text.get(2*j-1).charAt(i)) == 'A') {
				A_number+=1; }
			else if(Character.toUpperCase(text.get(2*j-1).charAt(i))== 'G') {
				G_number+=1;
			}
			else if(Character.toUpperCase(text.get(2*j-1).charAt(i))== 'T') {
				T_number+=1;
			}
			else if(Character.toUpperCase(text.get(2*j-1).charAt(i))== 'C') {
				C_number+=1; }
				else {
					other+=1;
				}
			}	
		double totalNumber =  A_number+G_number+T_number+C_number+other;
		//double GC = (C_number+G_number)/totalNumber ;
		//System.out.println(GC_content);
		freqMatrix[i+1][0] = String.valueOf(i+1) ;
		freqMatrix[i+1][1] = String.valueOf(A_number+0.25);
		freqMatrix[i+1][2] = String.valueOf(G_number+0.25);
		freqMatrix[i+1][3] = String.valueOf(T_number+0.25);
		freqMatrix[i+1][4] = String.valueOf(C_number+0.25);
		freqMatrix[i+1][5] = String.valueOf(other+0.25);

		rvsfreqMatrix[i+1][0] = String.valueOf(i+1);
		rvsfreqMatrix[motif_size-i][1] = String.valueOf(T_number+0.25);
		rvsfreqMatrix[motif_size-i][2] = String.valueOf(C_number+0.25);
		rvsfreqMatrix[motif_size-i][3] = String.valueOf(A_number+0.25);
		rvsfreqMatrix[motif_size-i][4] = String.valueOf(G_number+0.25);
		rvsfreqMatrix[motif_size-i][5] = String.valueOf(other+0.25);
		
		PSSMMatrix[i+1][0] = i+1 ;
		PSSMMatrix[i+1][1] = log2((A_number+0.25)/((G_number+0.25+A_number+0.25+T_number+0.25+C_number+0.25)*((1-GC)/2)));
		PSSMMatrix[i+1][2] = log2((G_number+0.25)/((G_number+0.25+A_number+0.25+T_number+0.25+C_number+0.25)*(GC/2)));
		PSSMMatrix[i+1][3] = log2((T_number+0.25)/((G_number+0.25+A_number+0.25+T_number+0.25+C_number+0.25)*(((1-GC)/2))));
		PSSMMatrix[i+1][4] = log2((C_number+0.25)/((G_number+0.25+A_number+0.25+T_number+0.25+C_number+0.25)*((GC/2))));
		PSSMMatrix[i+1][5] = log2((other+0.25)/((G_number+0.25+A_number+0.25+T_number+0.25+C_number+0.25)*((1-GC)/2)));;
	
		rvsPSSMMatrix[i+1][0] = i+1 ;
		// T's reverse
		rvsPSSMMatrix[motif_size-i][1] = log2((T_number+0.25)/((G_number+0.25+A_number+0.25+T_number+0.25+C_number+0.25)*(((1-GC)/2))));
		// C's reverse
		rvsPSSMMatrix[motif_size-i][2] = log2((C_number+0.25)/((G_number+0.25+A_number+0.25+T_number+0.25+C_number+0.25)*((GC/2)))); 
		// A's reverse
		rvsPSSMMatrix[motif_size-i][3] = log2((A_number+0.25)/((G_number+0.25+A_number+0.25+T_number+0.25+C_number+0.25)*(((1-GC)/2))));
		// G's reverse
		rvsPSSMMatrix[motif_size-i][4] = log2((G_number+0.25)/((G_number+0.25+A_number+0.25+T_number+0.25+C_number+0.25)*((GC/2))));
		rvsPSSMMatrix[motif_size-i][5] = log2((other+0.25)/((G_number+0.25+A_number+0.25+T_number+0.25+C_number+0.25)*(((1-GC)/2))));

	
	}
	for (int i=1; i< PSSMMatrix.length; i++) {
		for (int j=1; j< PSSMMatrix[i].length;j++) {
			strPSSMMatrix[i][0] = String.valueOf(i);
			strPSSMMatrix[i][j] = Double.toString(PSSMMatrix[i][j]);
		}
	}
	
	System.out.println("frequence Matrix is ");
	printMatrix(freqMatrix);
	System.out.println("PSSM Matrix is ");
	printMatrix(strPSSMMatrix);
	//printMatrix(rvsPSSMMatrix);
	System.out.println("cut off score is "+ cut_off);
	
		for (int i=0; i < gene_length-motif_size; i++) {
			double sum = 0 ;
			double rvssum = 0; 
			for (int j=i; j< i+ motif_size; j++) {
				if (gene.charAt(j)=='A') {
					sum+= PSSMMatrix[j-i+1][1];
					rvssum+= rvsPSSMMatrix[j-i+1][1];
				}
				else if(gene.charAt(j)=='G') {
					sum+= PSSMMatrix[j-i+1][2];
					rvssum+= rvsPSSMMatrix[j-i+1][2];
				}
				else if(gene.charAt(j)=='T') {
					sum+= PSSMMatrix[j-i+1][3];	
					rvssum+= rvsPSSMMatrix[j-i+1][3];}
				
				else if(gene.charAt(j)=='C') {
					sum+= PSSMMatrix[j-i+1][4];
					rvssum+= rvsPSSMMatrix[j-i+1][4];
				}
				
				else {
					sum+= PSSMMatrix[j-i+1][5];
					rvssum+= rvsPSSMMatrix[j-i+1][5];}
			}
			
			if (sum >= cut_off) {
				System.out.print("+ strand \t");
				System.out.print("5' ");
				for (int k=i; k< i+motif_size; k++) {
					
					System.out.print(gene.charAt(k));
					
				}
				System.out.print(" 3'\t");
				
				System.out.print("scores "+ sum);
				System.out.println();
			
		}
			if ( rvssum >=cut_off) {
				System.out.print("- strand \t");
				System.out.print("3' ");
				for (int k=i; k< i+motif_size; k++) {
					char seq = gene.charAt(k);
					if (seq == 'A') {
					System.out.print('T');	
					}
					if (seq == 'T') {
					System.out.print('A');					
										}
					if (seq == 'G') {
					System.out.print('C');
					}
					if (seq == 'C') {
					System.out.print('G');
					}
				
				}
				System.out.print(" 5'\t");
				System.out.print("scores "+ rvssum);
				System.out.println();
				
				
			
		}	
		}
		long finish =System.currentTimeMillis();
		System.out.println("The Whole Program Takes "+ (finish-strat_time )+ " milli seconds" );	
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
	
	public static double log2(double x) {
		return (double) (Math.log(x)/Math.log(2));
	}

}

