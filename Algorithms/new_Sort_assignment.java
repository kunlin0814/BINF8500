import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.*;
// use the System.currentTimeMillis()
public class new_Sort_assignment {

	public static void main(String[] args) throws FileNotFoundException {
		long start_time=System.currentTimeMillis();
		// TODO Auto-generated method stub
		//ArrayList <String> text = readString(args[0]);
		ArrayList <String> text = readString("G:\\UGA\\二上\\8500Algorithms\\sample1M.fastq");
		ArrayList <String> aList =  new ArrayList <String>();
		ArrayList <Integer> index_List = new ArrayList <Integer>();
		//List <List> final_List = new ArrayList <List>();
		//PrintWriter output = new PrintWriter("zzz-final.text");
		for (int i=1; i<text.size();i+=4) {
			aList.add(text.get(i)+"_"+i);
	
		}		
		//final_List.add(aList);
		//final_List.add(index_List);
			
		/*for (int i=0; i <aList.size();i++) {
			System.out.println(aList.get(i));
		}*/
		
		
		quicksort(aList);
		
		for (int i=0;i<aList.size()-1;i++ ) {
			int real_index = Integer.valueOf(aList.get(i).split("_")[1]);
			System.out.println(text.get(real_index-1));
			System.out.println(text.get(real_index));
			System.out.println(text.get(real_index+1));
			System.out.println(text.get(real_index+2));
			
			}
		long endtime=System.currentTimeMillis();
		long file_difference=(endtime-start_time);
		System.out.println("all program takes "+ file_difference);
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
		if (A.size()<2) {
			System.out.println("your array contain only 1 item");
		}
		else {
		quicksort(A, 0, A.size()-1); }// first and last index
		
	}
	public static void quicksort(ArrayList<String> A, int right, int left) {
			if (right < left) { // right and left pointer are not the same or not cross.
				int p = partition(A, right, left ); //then we get new pivot value for partition
				quicksort(A, right, p-1); // apply to left 
				quicksort(A, p+1, left); // apply to right 
			}		
		}
		public static void swap(ArrayList<String>A, int index1, int index2) {
			String temp = A.get(index1);
			A.set(index1, A.get(index2));
			A.set(index2, temp);

		}
		private  static int getPivot(int right, int left) { // get the index from random method
			Random rand = new Random();
			int index_value = rand.nextInt(left-right+1)+right; // random index
				return index_value ; // I made wrong here 
		}
		
		//move all n < pivot to left of pivot and all n> pivot to right of pivot, then return pivot index
		public static int partition( ArrayList<String> A, int right, int left) {
			swap(A, right, getPivot(right, left)); // get the pivot value from random and get to the most left position
			int border = right +1 ; //left pointer
			for (int i=border; i<=left; i++) {
				int compare = A.get(i).compareTo( A.get(right) );
				if (compare < 0) { //pivot is A[right]
					swap(A, i,border); // swap with border value
					border+=1;
					}
				}
				swap(A,right, border-1); // swap the pivot value to the proper position
				return border-1; // return the index of the pivot value
			}
}




	

