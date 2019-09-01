import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Hello {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList <String> text = readString(args[0]);
		//ArrayList <String> text = readString("G:\\UGA\\二上\\8500Algorithms\\sample1k.fastq");
		ArrayList <String> aList =  new ArrayList <String>();
		ArrayList <String> final_List = new ArrayList <String>();
		for (int i=1; i<text.size();i+=4) {
			aList.add(text.get(i));
		}
		quicksort(aList);
		//for (int i=0; i< aList.size();i++) {
		//System.out.println(aList.get(i));
		//}
		for (int i=0; i<aList.size();i++) {
			int index = text.indexOf(aList.get(i));
			int first = index -1;
			int third = index +1;
			int fourth = index +2;
			final_List.add(text.get(first));
			final_List.add(text.get(index));
			final_List.add(text.get(third));
			final_List.add(text.get(fourth));
				
		}
		for (int i=0; i<final_List.size();i++) {
			System.out.println(final_List.get(i));
		}
		//System.out.println(final_List.size());
		//System.out.println(text.get(5));
	}

	public static ArrayList<String> readString(String file){
		//@parm String file
				String text = "";
				ArrayList <String> lists = new ArrayList<String>();
				try {
				File f = new File(file);
				Scanner s = new Scanner(f);
				while (s.hasNextLine()) {
					lists.add(s.nextLine());}
				}
				catch(FileNotFoundException e) {
				System.out.println("File not found");
				
}
				return lists;
	}
	public static void quicksort(ArrayList<String>A) {
		quicksort(A, 0, A.size()-1); // first and last index
	}
	public static void quicksort(ArrayList<String> A, int low, int high) {
			if (low < high) { // low and high pointer are not the same or not cross.
				int p = partition(A, low, high ); //then we get new pivot value for partition
				quicksort(A, low, p-1); // apply to left 
				quicksort(A, p+1, high); // apply to right 
			}		
		}
		public static void swap(ArrayList<String>A, int index1, int index2) {
			String temp = A.get(index1);
			A.set(index1, A.get(index2));
			A.set(index2, temp);

		}
		private  static int getPivot(int low, int high) { // get the index from random method
			Random rand = new Random();
			int index_value = rand.nextInt(high-low+1)+low; // random index
				return index_value ; // I made wrong here 
		}
		
		//move all n < pivot to left of pivot and all n> pivot to right of pivot, then return pivot index
		public static int partition( ArrayList<String> A, int low, int high) {
			swap(A, low, getPivot(low, high)); // get the pivot value from random and get to the most left position
			int border = low +1 ; //left pointer
			for (int i=border; i<=high; i++) {
				int compare = A.get(i).compareTo( A.get(low) );
				if (compare < 0) { //pivot is A[low]
					swap(A, i,border); // swap with border value
					border+=1;
					}
				}
				swap(A,low, border-1); // swap the pivot value to the proper position
				return border-1; // return the index of the pivot value
			}
}


	

