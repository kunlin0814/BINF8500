import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.regex.Matcher; 
import java.lang.Math;
public class gibbs_sampler {


public static void main(String[] args) throws IOException {
	long time_start=System.currentTimeMillis();
	
	ArrayList<String> file = readString(args[0]);
	int Motif_size = Integer.valueOf(args[1]) ;
	int MotifLowerBound = Integer.valueOf(args[2]) ;
	int iterationNumber = Integer.valueOf(args[3]) ;
	int adjustmentNumber = Integer.valueOf(args[4]) ;
	int different_try = Integer.valueOf(args[5]);
	int seq_number = 0;
	

	StringBuilder seq_base = new StringBuilder();
	for (int i=0; i< file.size(); i++) {
		if (file.get(i).matches("(^[>].*)")) {
			seq_number+=1;
			seq_base = new StringBuilder();
		}
		else {
			seq_base.append(file.get(i));
		}
	}
	int seq_line = (file.size()/seq_number);
	
	// each sequence length
	int seq_len = seq_base.length();
	char [][] seq = new char [seq_number][seq_len];
	StringBuilder each_seq = new StringBuilder();
	int cnt2 = 0;
	
	
	// put the sequence information into a char matrix
	for (int i=0 ; i< file.size(); i++) {
		if (file.get(i).matches("(^[>].*)")) {
			continue;}
		else {
		if( (i+1)%seq_line==0) {
			each_seq.append(file.get(i));
			char[]char_arr = each_seq.toString().toCharArray();
			seq[cnt2] = char_arr;
			cnt2++;
			
			each_seq = new StringBuilder();
		
		}
		else {
				each_seq.append(file.get(i));}
		}
	}
	
	double pseudocount = 0.25 ;
	
	double GC_summary [] = new double [seq_number];

// Calculate the GC content for each sequence and put the result into an array
	for (int i = 0; i< seq.length; i++) { //  number of sequence
		double C_number = 0 ;
		double G_number = 0  ;
		
		for (int j = 0; j<seq[i].length; j++) { // number of columns
			char nucleotide= seq[i][j];
			if(nucleotide =='G' || nucleotide == 'g') {
				G_number+=1;
			}
			else if(nucleotide =='C' || nucleotide == 'c') {
				C_number+=1;
			}
			
		}
	GC_summary[i] = (G_number+C_number+0.5)/(seq[i].length+2);
}
	
// iteration through all seq, but ignore the ith sequence to create PSSM

// put the each iteration result into an object array	
	

	resultStr [] total_summary = new resultStr[different_try];
	System.out.println("Initial Motif Size is "+ Motif_size);
	
	
	
	for (int m=0; m< different_try; m++) {

	resultStr [] final_summary = new resultStr[iterationNumber];
	Random rand = new Random();
	int [] rand_arr = new int[seq_number];
	//int [] temp_arr = new int[seq_number];

	
// initiate the first rand_arr and start the whole process	
	for (int i =0 ; i < seq_number;i++) {
		rand_arr[i]= rand.nextInt(seq[i].length-Motif_size+1); 
	}
	
	
	int iterationCunter = 0 ;
	for (int l =0; l <iterationNumber ; l++) {
	
		++iterationCunter  ;
	
		/// iterate through all of the sequence 
	for (int k = 0 ; k < seq_number ; k++) { 
		double PSSMMatrix [][]= new double [Motif_size+1][6];	
	// create a PSSM matrix 
	
	for (int  j = 0; j <Motif_size; j++) { // column position in the char matrix
		double A_number = 0  ;
		double C_number = 0 ;
		double G_number = 0  ;
		double T_number = 0 ;
		double other = 0;		
	for ( int i=0 ; i < seq_number; i ++) { // row position of the char matrix
			if (!(i==k)) {
			
			int pos = rand_arr[i];
			char nucleotide= seq[i][pos+j];
			if(nucleotide =='A' || nucleotide =='a'){
				A_number+=1;}
			
			else if(nucleotide =='G' || nucleotide == 'g') {
				G_number+=1;
			}
			else if(nucleotide =='T' || nucleotide == 't') {
				T_number+=1;
			}
			else if(nucleotide =='C' || nucleotide == 'c') {
				C_number+=1;
			}
			else {
				other+=1;
			}

			}
		}
	
// use the sequence except the sequence i to make a PSSM matrix and subtract the background 
		PSSMMatrix[j+1][0] = j+1 ;
		PSSMMatrix[j+1][1] = log2((A_number+pseudocount)/((G_number+pseudocount+A_number+pseudocount+T_number+pseudocount+C_number+pseudocount)*((1-GC_summary[k])/2)));
		PSSMMatrix[j+1][2] = log2((G_number+pseudocount)/((G_number+pseudocount+A_number+pseudocount+T_number+pseudocount+C_number+pseudocount)*(GC_summary[k]/2)));
		PSSMMatrix[j+1][3] = log2((T_number+pseudocount)/((G_number+pseudocount+A_number+pseudocount+T_number+pseudocount+C_number+pseudocount)*(((1-GC_summary[k])/2))));
		PSSMMatrix[j+1][4] = log2((C_number+pseudocount)/((G_number+pseudocount+A_number+pseudocount+T_number+pseudocount+C_number+pseudocount)*((GC_summary[k]/2))));


}
	
	
	double total_prob_score = 0;
	double [] prob_score_strs = new double [seq_len-Motif_size+1];	
	
	
// calculate the interval of the kth sequence, k = 0,1,2,3,4,5......to the number of the sequence
	double interval [] = new double [seq[k].length-Motif_size+1];
	
	for (int i=0; i<seq[k].length-Motif_size+1; i++) {
		double prob_sum = 0 ;
		double score = 0 ;
		for (int j = i; j<i+Motif_size;j++) {
			char each_char = seq[k][j];
			if (each_char == 'A'||each_char =='a') {
				int number= j-i+1;
				prob_sum+= PSSMMatrix[number][1];
			}
			else if (each_char == 'G'||each_char =='g') {
				int number= j-i+1;
				prob_sum+= PSSMMatrix[number][2];
				
			}
			else if (each_char == 'T'||each_char =='t') {
				int number= j-i+1;
				prob_sum+= PSSMMatrix[number][3];
				
			}
			else if (each_char == 'C'||each_char =='c') {
				int number= j-i+1;
				prob_sum+= PSSMMatrix[number][4];
				
			}
			score += Math.exp(prob_sum) ;
			total_prob_score+= score;
			
			
		}
		
		prob_score_strs[i] = total_prob_score ;
	
	}
	
	for (int i =0; i < prob_score_strs.length; i++) {
		interval[i]= prob_score_strs[i]/total_prob_score ;
		//System.out.println(interval[i]);
	}
	

	Random rnd_select = new Random();
	double rand_number = rnd_select.nextDouble() ;
	int pos = 0 ;

	
	// random choose the position with the probability between 0~1
	for (int i =0 ; i<interval.length;i++ ) {		
		if (interval[i] > rand_number) {
			if(i==0) {
				pos = 0;
			}
			else{
				pos = i;
			
			rand_arr[k]= pos;	
			break;}
		}
	}	
		
		}
		
		if (iterationCunter%adjustmentNumber ==0 && !(l==0)) {
			boolean expandCheck = true;
	        
	    for(int i = 0; i < rand_arr.length;i++) {
	 	    if(rand_arr[i] == 0 || rand_arr[i]>= (seq_len - Motif_size)){
	 	       expandCheck = false;}
	        }
	    
	        if(expandCheck == true && Motif_size > MotifLowerBound+2) {
	        resultStr current_Summary = calulateScore(seq, rand_arr, Motif_size, GC_summary);
	        resultStr[] each_round_summary= new resultStr[9];
        		int[] left_substract_temp_arr = rand_arr.clone();
        		int[] right_substract_temp_arr = rand_arr.clone();
        		int[] left_substract_rigt_temp_arr = rand_arr.clone();
        		int[] left_shift_temp_arr = rand_arr.clone();
        		 for(int i =0;i<seq_number;i++) {
        			 left_substract_temp_arr[i] = rand_arr[i]+1;
        			 left_substract_rigt_temp_arr[i]=rand_arr[i]+1;
        		 	left_shift_temp_arr[i]= rand_arr[i]-1;}
            
        		 resultStr leftSubstractMatrixSummary = calulateScore(seq, left_substract_temp_arr, Motif_size-1, GC_summary);
        		 resultStr rightSubstractMatrixSummary = calulateScore(seq, right_substract_temp_arr, Motif_size-1, GC_summary);
        		 resultStr rightSubstractLeftMatrixSummary = calulateScore(seq, left_substract_rigt_temp_arr, Motif_size-2, GC_summary);
        		 resultStr rightShiftMatrixSummary = calulateScore(seq, left_substract_temp_arr, Motif_size, GC_summary);
         		 resultStr leftShiftMatrixSummary = calulateScore(seq, left_shift_temp_arr, Motif_size, GC_summary);
        	
        		int [] left_plus_temp_arr = rand_arr.clone();
        		int [] right_plus_temp_arr = rand_arr.clone();
        		int [] left_plus_rigt_temp_arr = rand_arr.clone();
        	  
              
              for(int i =0;i<seq_number;i++) {
            	  left_plus_temp_arr[i] = rand_arr[i]-1;
            	  left_plus_rigt_temp_arr[i]=rand_arr[i]-1; }
           
              resultStr leftPlusMatrixSummary = calulateScore(seq, left_plus_temp_arr, Motif_size+1, GC_summary);
              resultStr rightPlusMatrixSummary = calulateScore(seq, right_plus_temp_arr, Motif_size+1, GC_summary);
              resultStr rightPlusLeftMatrixSummary = calulateScore(seq, left_plus_rigt_temp_arr, Motif_size+2, GC_summary);
             
            
			each_round_summary[0]= current_Summary;
        	each_round_summary[1]=leftSubstractMatrixSummary;
        	each_round_summary[2]=rightSubstractMatrixSummary;
        	each_round_summary[3]=rightSubstractLeftMatrixSummary;
        	each_round_summary[4]=leftPlusMatrixSummary;
        	each_round_summary[5]=rightPlusMatrixSummary;
        	each_round_summary[6]=rightPlusLeftMatrixSummary;
        	each_round_summary[7]=rightShiftMatrixSummary;
        	each_round_summary[8]=leftShiftMatrixSummary;
        	
        	resultStr summary_result = find_max(each_round_summary);
        	rand_arr = summary_result.location.clone();
        	Motif_size = summary_result.seqLength;
        	
        	} 
          	
		} 
		// calculate the total score based on the update position and store the score and position and the motif size
		// each iteration, store the update position for each sequence and the length of the motif
		resultStr currentIterationSummary = calulateScore(seq, rand_arr, Motif_size, GC_summary);
		final_summary[l]= currentIterationSummary;
  } 
	
	// based on different random initiated point, after the iteration, it will store the result 
		resultStr each_best = find_max(final_summary);
		total_summary[m] = each_best;
	
	}	


 //After the different random initiate points and iteration number, 
 //choose the highest score and it's update location.
		resultStr best = find_max(total_summary);
		System.out.println("Iteration "+ iterationNumber);
		System.out.println("Final Motif Size is " + Motif_size);
		System.out.println("Motif at least is greater than "+ MotifLowerBound+ " Bases");
		System.out.println("Adjusting frequency is : every "+ adjustmentNumber + " iterations") ;
		System.out.println("Best score is " + best.score);
		System.out.println("Motif for each sequence is ");
		for (int i=0 ; i<seq.length;i++) {
			int start = best.location[i];
			System.out.print("Seq "+ (i+1)+'\t'+(start+1)+'-'+(start+Motif_size)+'\t') ;
			for (int j=start; j<start+Motif_size-1;j++) {
				
				System.out.print(seq[i][j]);
			}
			System.out.println();
		}
		long time_end=System.currentTimeMillis();
		System.out.println("whole program takes "+ (time_end-time_start)+ " Millisecond");

}

	
// a function will create a PSSM based on the sequence except one unchoosen sequence and then use this PSSM to calculate
// the scores for the remaining sequence and sum them up.
// the function will return the total scores based on the current location and the motif_size
public static resultStr calulateScore (char [][] seq, int[] rand_arr, int motif_size, double[]GC_summary) {
	
	double pseudocount = 0.25 ;
	int total_sequ_number = rand_arr.length;
	double sum = 0;
for (int k = 0 ; k < total_sequ_number ; k++) {
	double [][]PSSMMatrix= new double [motif_size+1][6];	
	for (int  j = 0; j <motif_size; j++) { // column position in the char matrix
			double A_number = 0  ;
			double C_number = 0 ;
			double G_number = 0  ;
			double T_number = 0 ;
			double other = 0;		
		for ( int i=0 ; i < total_sequ_number; i ++) { // row position
				if (!(i==k)) {
				//int pos = rand_arr[i];
				char nucleotide= seq[i][rand_arr[i]+j];
				if(nucleotide =='A' || nucleotide =='a'){
					A_number+=1;}
				
				else if(nucleotide =='G' || nucleotide == 'g') {
					G_number+=1;
				}
				else if(nucleotide =='T' || nucleotide == 't') {
					T_number+=1;
				}
				else if(nucleotide =='C' || nucleotide == 'c') {
					C_number+=1;
				}
				else {
					other+=1;}

				}
		
		}
		PSSMMatrix[j+1][0] = j+1 ;
		PSSMMatrix[j+1][1] = log2((A_number+pseudocount)/((G_number+pseudocount+A_number+pseudocount+T_number+pseudocount+C_number+pseudocount)*((1-GC_summary[k])/2)));
		PSSMMatrix[j+1][2] = log2((G_number+pseudocount)/((G_number+pseudocount+A_number+pseudocount+T_number+pseudocount+C_number+pseudocount)*(GC_summary[k]/2)));
		PSSMMatrix[j+1][3] = log2((T_number+pseudocount)/((G_number+pseudocount+A_number+pseudocount+T_number+pseudocount+C_number+pseudocount)*(((1-GC_summary[k])/2))));
		PSSMMatrix[j+1][4] = log2((C_number+pseudocount)/((G_number+pseudocount+A_number+pseudocount+T_number+pseudocount+C_number+pseudocount)*((GC_summary[k]/2))));
		PSSMMatrix[j+1][5] = log2((other+pseudocount)/((G_number+pseudocount+A_number+pseudocount+T_number+pseudocount+C_number+pseudocount)*((1-GC_summary[k])/2)));;
			
				
	} 
	
		// calculate each sequence score based on previous PSSM
		for(int o = rand_arr[k]; o < rand_arr[k]+motif_size; o++) {
			char each_sequ = seq[k][o];
			if (each_sequ=='A'|| each_sequ =='a') {
				sum+=PSSMMatrix[(o-rand_arr[k])+1][1];
					
				}
				else if (each_sequ=='G'|| each_sequ =='g') {
					sum+=PSSMMatrix[(o-rand_arr[k])+1][2];
					
					
				}
				else if (each_sequ=='T'|| each_sequ =='t'){
					
					sum+=PSSMMatrix[(o-rand_arr[k])+1][3];
					
				}
				else if (each_sequ=='C'|| each_sequ =='c') {
					
					sum+=PSSMMatrix[(o-rand_arr[k])+1][4];
					
				}
				
				
		}
}
	resultStr resultSummary = new resultStr(rand_arr, sum, motif_size);
	return resultSummary;

}

	
public static ArrayList<String> readString(String file) throws IOException{ 
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
				Pattern patt = Pattern.compile("^[>]");
				if (!strCurrentLine.matches("(^[>])")){
				lists.add(strCurrentLine);
				}
			}
			}
			catch(FileNotFoundException e) {
			System.out.println("File not found");
}
			return lists;
	}
public static void printMatrix(char[][] m) {
	
	for(int i=0; i<m.length;i++) {
		for (int j=0; j<m[i].length;j++) {
			System.out.print(m[i][j]+"\t");
		}
		System.out.println();
	}
}
public static void printDoubleMatrix(double[][] m) {
	
	for(int i=0; i<m.length;i++) {
		for (int j=0; j<m[i].length;j++) {
			System.out.print(m[i][j]+"\t");
		}
		System.out.println();
	}
}
public static resultStr find_max (resultStr[] m) {
	double max = (double) m[0].score;
	int max_index = 0 ;
	for (int i=0; i< m.length; i++) {
		if (max < m[i].score) {
			max = m[i].score ;
			max_index = i;
		
		}
	}
	
	return m[max_index] ;
}


public static double log2(double x) {
	return (double) (Math.log(x)/Math.log(2));
}

}