import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Math;
import java.util.*;
import java.util.Scanner ;
public class Kmeans_algorithm{	
	
	// The program takes args[0] as an input file name to do the kmeans and take args[1] as the argument for the k groups
	// ex : "java Kmeans_algorithm filepath/filename 5" , means it will choose 5 groups.
	// when you executed the file, it will ask you " Do your want to see the Cluster Summary value (yes/no) ? "
	// if you choose yes, then it will show the WCSS AIC and BIc values
	// if you choose no, then it will show the clustering result
	
	
	
	// the method that chooses the random k points from the all species as our start centroids
	public static double[][] get_start_centorid (ArrayList<Cluster_AA >each_arr, int k){

		double [][]startPoint = new double[k][each_arr.get(0).points.size()];
		HashSet <Integer> rand_start = new HashSet <Integer>();
		Random rand = new Random();
		while (rand_start.size()!= k) {
		int index_value = rand.nextInt(each_arr.size());
		rand_start.add(index_value);}
		
		Integer[] KPoints= rand_start.toArray(new Integer[rand_start.size()]);
		//System.out.println(Arrays.toString(KPoints));
		for ( int row=0; row <KPoints.length; row++) {
			int row_choose = KPoints[row];	
			List <Double> L1 = each_arr.get(row_choose).points;  
			//System.out.println(L1);
			for (int col=0; col <startPoint[row].length;col ++) {
				startPoint[row][col]= L1.get(col);
					}
		}
		return startPoint;	
	}

	public double Calculate_distance(double[]A,double[]B ) {
		double distance = 0;
		double temp = 0 ;
		for (int i=0; i< A.length;i++) {
			temp += Math.pow((A[i]-B[i]),2);
		}
		distance = Math.pow(temp, 0.5);
		
		return distance;
	}
	
	// Kmeans algorithm
	public void get_group(ArrayList<Cluster_AA >each_arr, int k, boolean Cluster_summary_value ) {
		if (k ==0) {
			System.out.println("Kmeans group cannot be 0");
		} 
		else {
		int[] Cluster_number = new int[each_arr.size()];
		int[] temp_Cluster_number = new int[each_arr.size()];
		String[] eachSpecies = new String[each_arr.size()];
		double[][]dataPointsEachSpecies = new double[each_arr.size()][each_arr.get(0).points.size()];
		double[][] new_centorid = new double[k][each_arr.get(k).points.size()];
		double[][] old_centorid = get_start_centorid(each_arr, k);
	do {
	
		temp_Cluster_number = Cluster_number ;
		Cluster_number = new int[each_arr.size()];
		//ArrayList <ArrayList<Double>> mid_centorid = new ArrayList<ArrayList<Double>>();
			//ArrayList<group> cluster_all = new ArrayList<group>();
		for (int i=0; i< each_arr.size(); i++) {
		double result[]= new double[old_centorid.length];
		for (int j=0; j<old_centorid.length;j++) {
			double sum = 0 ;	
		for ( int k1=0; k1< each_arr.get(i).points.size();k1++) {
		double each_point = Math.pow(each_arr.get(i).points.get(k1)- old_centorid[j][k1],2);
		dataPointsEachSpecies[i][k1] = each_arr.get(i).points.get(k1);	
		sum+= Math.pow(each_point,0.5) ;}
		
		result[j]= sum ; } 
		// find minimal value
		double min=result[0];
		int min_index = 0; 
		for(int i1=0;i1<result.length;i1++){
			if(result[i1]<=min) {
				min=result[i1];
			 	min_index = i1;} 
			}
		Cluster_number[i] = min_index;
		eachSpecies[i] = each_arr.get(i).ID;}
		new_centorid = upDataPoint(Cluster_number,dataPointsEachSpecies,k);
		old_centorid = new_centorid ;
		
	}while( !Arrays.equals(temp_Cluster_number,Cluster_number));
	
	if (Cluster_summary_value) {
	
	print_Summary(k,Cluster_number,dataPointsEachSpecies,old_centorid,each_arr);}
	else {
	print_cluster(k,Cluster_number,old_centorid,eachSpecies );}
	}	
	}
	
	// the method that prints out the Kmeans WCSS AIC BIC values
	protected void print_Summary (int k, int[]Cluster_number, double[][]dataPointsEachSpecies, double[][]old_centorid, ArrayList<Cluster_AA >each_arr ) {	
	double WCSS= 0 ;
	double AIC = 0;
	double BIC =0 ;
	for (int i = 0 ; i < k ; i++ ) {
		double each_cluster = 0 ; 
		for (int j = 0 ; j< Cluster_number.length; j++) {
			double temp =0 ;
			if (Cluster_number[j]== i) {
			int row = j ;
			double[] arr = dataPointsEachSpecies[row];
			temp = Calculate_distance(arr,old_centorid[i]);
			//System.out.println(eachSpecies[row]);
			}
			each_cluster += Math.pow(temp, 2) ;
		}
		WCSS += each_cluster ;
		}
	AIC = (2* k *each_arr.get(1).points.size())+WCSS;
	BIC = Math.log(each_arr.size())*k*each_arr.get(1).points.size()+WCSS;
	System.out.printf("Kmeans groups into \t%d\tWCSS\t%f\tAIC\t%f\tBIC\t%f\n", k, WCSS, AIC,BIC);

	}
	// the method that prints out the Kmeans clustering result
	protected void print_cluster (int k,int[]Cluster_number, double[][]old_centorid, String[] eachSpecies ) {
		for (int i = 0 ; i < k ; i++ ) {
			System.out.println("Cluster "+ i );
			for (int j = 0 ; j< Cluster_number.length; j++) {
				if (Cluster_number[j]== i) {
				int row = j ;
				System.out.println(eachSpecies[row]);}		
			}
		}
	}
	
	// the method that updates the centorids
	private static double[][] upDataPoint(int[]Cluster_number, double[][]dataPointsEachSpecies, int k)  {
		double[][] new_centorid = new double[k][dataPointsEachSpecies[k].length];
		
	for (int i=0; i< k ; i++) {
		ArrayList <Integer> eachCluster = new ArrayList<Integer>();
	for (int col=0; col<dataPointsEachSpecies[0].length; col++) {
		double average = 0;
		double sum = 0;
	for (int j =0; j< Cluster_number.length; j++) {
		if (Cluster_number[j]== i) {
			int row = j ;
			eachCluster.add(row);
			double data = dataPointsEachSpecies[row][col];
			sum += data ;}
			}
			average = sum /eachCluster.size();
		new_centorid[i][col]= average;
		}
		}
	return new_centorid;	
	}
	
	
	

	public static void main(String[] args) throws IOException {
		//ArrayList<String> text = readString("G:\\UGA\\2fall\\8500Algorithms\\Kmeans\\Archaea.txt");
		ArrayList<String> text = readString(args[0]);
		int row = text.size();
		int col = text.get(0).split("\t").length;
		String [][] matrix = new String [row][col];
		for(int i=0; i<row; i++) {
			for (int j=0; j< matrix[i].length;j++) {
				matrix[i][j]=text.get(i).split("\t")[j];
			}
		}

		double avg_arr[]=colMean(matrix);
		double std_arr[]=colStd(matrix);
		
	// Here is what we Normalize the each amino acid values 
		for (int column=1; column<matrix[1].length;column++) {
			for(int row_1=1;row_1<matrix.length;row_1++) {
				double each = (Double.parseDouble(matrix[row_1][column])-avg_arr[column])/std_arr[column];
				matrix[row_1][column]=String.valueOf(each);
			}
		}
		
		
		ArrayList<Cluster_AA> each_arr= new ArrayList<Cluster_AA>();
		for ( int row_index=1; row_index<matrix.length;row_index++) {
		each_arr.add(new Cluster_AA(matrix[row_index][0],get_datapoints(matrix,row_index), row_index ));
		}
			
		Kmeans_algorithm cnt_list= new Kmeans_algorithm();
		
	
		Scanner input = new Scanner(System.in);
		System.out.println("Do you want to see the Cluster Summary value (yes/no) ?");
		String s = input.nextLine().toLowerCase();
		boolean Cluster_summary_value ;
		if (s.equalsIgnoreCase("yes")) {
		Cluster_summary_value = true; }
		else {
		Cluster_summary_value = false;	
		}
		
		String number = args[1];
		int i = Integer.parseInt(number);
		// Kmean_algorithms implementation
		cnt_list.get_group(each_arr, i, Cluster_summary_value);
		
	}
		// the function that read the files into a string arraylist
		public static ArrayList<String> readString(String file) throws IOException{
		
		ArrayList <String> lists = new ArrayList<String>();
		BufferedReader objReader = null;
		try {
		String strCurrentLine;
		objReader = new BufferedReader(new FileReader(file));
		while ((strCurrentLine = objReader.readLine()) != null) {
			lists.add(strCurrentLine);}
		}
		catch(FileNotFoundException e) {
		System.out.println("File not found");
		
}
		return lists;
		}
		
		// the function that calculates the mean from the columns
		public static double[] colMean(String[][]m) {
			double avg []= new double[m[1].length];
			for (int col=1;col<m[1].length; col++) {
				double sum=0;
				for (int row=1;row<m.length;row++) {
				 double value =Double.parseDouble(m[row][col]);
				 sum += value;
				}	
				double average = sum/(m.length-1);
				avg[col]= average;
			}
			return avg; 
		}
		
		// the function that calculates the standard deviation from the columns
		public static double[]colStd(String[][]m){
			double std_arr []= new double[m[1].length];
			double []avg = colMean(m);
			for (int col=1;col<m[1].length; col++) {
				double sum = 0;
				for (int row=1;row<m.length;row++) {
					double value =Double.parseDouble(m[row][col]);
					 double each = (Math.pow((value - avg[col]),2))/(m.length-1-1);
					 sum+=each;
				}	
			double std = Math.pow(sum, 0.5);
			std_arr[col]=std;
		}
			return std_arr;
		}
		// the function that prints out the matrix
		public static void printMatrix(double[][] m) {
			
			for(int i=0; i<m.length;i++) {
				for (int j=0; j<m[i].length;j++) {
					System.out.print(m[i][j]+"\t");
				}
				System.out.println();
			}
		}
		// the function that change string arraylist into double arraylist
		public static ArrayList<Double> String2Double(ArrayList<String>L1){
			ArrayList <Double> doubleArray = new ArrayList<Double>();
			for (int i=0; i<L1.size();i++) {
				doubleArray.add(Double.parseDouble(L1.get(i)));
			}
			return doubleArray;
			
		}
		
		// the function that extracts the data point from each row of the matrix
	public static ArrayList <Double> get_datapoints (String[][]m1, int row_index) {
			ArrayList<Double> data = new ArrayList<Double>();
			for ( int col=1; col <m1[1].length; col ++ ) {
				data.add(Double.parseDouble(m1[row_index][col])); 
				}	
			return data;	
		}
	}