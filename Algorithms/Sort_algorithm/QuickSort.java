import java.util.*;

public class QuickSort {
	public void quickSort(int[]A) {
		quicksort(A, 0, A.length-1); // first and last index
	}
	public void quicksort(int[] A, int low, int high) {
		if (low < high+1) { // if distance between low and high gt than 1, means more than 1 item between two
			int p = partition(A, low, high ); //then we get new pivot value for partition
			quicksort(A, low, p-1); // apply to right 
			quicksort(A, p+1, high); // apply to left 
		}
		
	}
	public void swap(int[]A, int index1, int index2) {
		int temp = A[index1];
		A[index1]= A[index2];
		A[index2]= temp;
	}
	public int getPivot( int low, int high) { // get the index from random method
		Random rand = new Random();
		int index_value = rand.nextInt((high-low )+1)+ low ; //start from low value index to high index
		return index_value ; // I made wrong here 
	}
	public int partition( int[] A, int low, int high) {
		swap(A, low, getPivot(low, high)); // get the pivot value and get to the most left position
		int border = low +1 ;
		for (int i=border; i<=high; i++) {
			if (A[i]< A[low]) { //pivot is A[low]
				swap(A, i, border++); 
				}
			}
			swap(A,low, border-1); // swap the pivot value to the proper position
			return border-1; // retun the index of the pivot value
		}
public static void main(String[]args) {
	QuickSort qs = new QuickSort();
	// TODO Auto-generated method stub
	int[] A = {10, 9, 1, 2, 5, 3, 8, 7, 12, 11};
	System.out.println(Arrays.toString(A));
	qs.quickSort(A);
	System.out.println(Arrays.toString(A));
}

}