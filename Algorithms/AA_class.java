import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;
public class AA_class {

	String species;
	double A ; double R; double N; double D; double C;
	double Q; double E; double G; double H; double I;
	double L; double K; double M; double F; double P;
	double S; double T; double W; double Y; double V;
	

public AA_class(String species,double A ,double R,double N, double D, double C, 
double Q, double E, double G, double H, double I,
double L, double K, double M, double F, double P,
double S, double T, double W, double Y, double V) {
this.species =species; 
this.A=A;
this.C=C;
}	
	public static void main(String[] args) throws IOException {
		ArrayList <String> text = readString("G://UGA//2fall//8500Algorithms//Kmeans//Archaea.txt");
		for (int i=0; i<text.size();i++) {
		int length = text.get(i).split("\t").length;
		//split("\t")[20]);
		}
		
		// A	R	N	D	C	Q	E	G	H	I	L	K	M	F	P	S	T	W	Y	V
		// input K, sets of points X1 ~ Xn, and place centroids at random locations. Repeat until converge
		// For each point xi: find the nearest centroid and assign the point xi to the cluster j
		// For each cluster j = 1...k, new centroid cj = mean of all points xi and assign to cluster j in previous steps
		// elbow ; calculate the sum of the square error values and then pick the point right at the tipping points
		//(that is the optimal k value)
		// kmeans minimize the within cluster variance, and variance is the sum of the Euclidean distance
	

	}
	// steps
	// 1.select random k points
	// 2.compute the distance between every point on the set  and those center and store the information
	// 3.assign each point to the nearest cluster center, we get min distance calculated from each point and we add that point to specific partition set
	// 4.update the cluster centroid point use mean
	// 5.if the center change, repeat from 2, if not, then stop. 
	
	//function
	// find the minimal 
	// find the distance ((X1-X2)^2 + (Y1-Y2)^2)^1/2
	// mean
	//4. sum of the square errors (for elbow)
	//5. minimize the distance from centroid to every data point in that cluster
	//6. random k points
	public static double Euc_Distance (ArrayList<Double> A , ArrayList<Double> B) { 
		double sum = 0 ;
		for (int i=0; i<A.size();i++) {
			double each_point = A.get(i)-B.get(i);
			double total = Math.pow(each_point,2);
			sum += total ;
			}
		return Math.pow(sum, 0.5) ;
		}
	
	public static double mean (ArrayList<Double> A) {
		
		return 0 ;
	}
	 
	
	
	public static ArrayList<String> readString(String file) throws IOException{ // a function to read the data into an arraylist
		
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
}
