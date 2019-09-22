import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
public class test4 {
	public static void main(String[] args) {
	int [][]a = new int [][] {{1,2,6},
								{2,3,4}};
	int [][]b = new int [][] {{4,6,5},
								{0,8,9}};
	int[]arr = a[0];
	System.out.println(Arrays.toString(arr));
	}
	
	public static double distance (int[]a, int[] b) {
		double temp =0 ;
		double distance =0 ;
		for (int i=0; i<a.length; i++) {
			temp += Math.pow((a[i]-b[i]),2);	
		}
		distance = Math.pow(temp, 0.5);
		return distance;
	}
	
}