import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


/* This script takes args[0] as an input file and will print out the sorting result
 * The script reads the args[0] and put into an arraylist.
 * I also created an Index_String class so each Index_String class have an index and a string
 * Once we sort the Index_String, we can know where is the string after sorting.
 * this sort uses Lomuto’s partition method
 */ 
public class Index_String { 

    int index;
	String string;

public Index_String(int index, String string){
        this.index = index;
        this.string = string;
    }

    private int CompareTo(Index_String object) { // create a method that can compare the index_string with other index string class but only compare the sequence
    	int result = this.string.compareTo(object.string);
        return result;
    }

 public static void main(String[] args) throws IOException {
	 long start_time=System.currentTimeMillis();
	 ArrayList <String> text = readString("G:/UGA/2fall/8500Algorithms/sample1M.fastq");
	 ArrayList <String> aList =  new ArrayList <String>();
	 List <Index_String> elements = new ArrayList <Index_String >(); // create an arraylist with index_string class
		for (int i=1; i<text.size();i+=4) {
			aList.add(text.get(i));// create an arraylist only contains sequence data
	
		}		
		
		for (int i = 0; i < aList.size(); i++) {
		    elements.add(new Index_String(i, aList.get(i)));} // the element arraylist contains the sequence data and the index for that sequence 
		
		quicksort(elements);

	// After sorting, we look the sorting index and use that index to go back to the original data to get the correct order of each header and sequence.
	for (Index_String element : elements) {
		int header_index = element.index*4;
		int read_index = header_index+1;
		int second_index = header_index+2;
		int quality_index = header_index+3;
		//System.out.println(text.get(header_index));
	    //System.out.println(text.get(read_index));
	    //System.out.println(text.get(second_index));
	    //System.out.println(text.get(quality_index));
	    //System.out.printf("%s",text.get(header_index));
	    //System.out.printf("%n");
	    //System.out.printf("%s",text.get(read_index));
	    //System.out.printf("%n");
	    //System.out.printf("%s",text.get(second_index));
	    //System.out.printf("%n");
	    //System.out.printf("%s",text.get(quality_index));
	}
	long time_end=System.currentTimeMillis();
	long program_time = (time_end-start_time);
	System.out.println("Rearrange new list takes "+ program_time);
	System.out.println(elements.size());
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
	public static void quicksort(List<Index_String> elements) {
		if (elements.size()<2) {
			System.out.println("your array contains only 1 item");
		}
		else {
			quicksort(elements, 0, elements.size()-1); }// first and last index
	}
	public static void quicksort(List<Index_String> elements, int right, int left) {
			if (right < left) { // right and left pointer are not the same or not cross.
				int p = partition(elements, right, left ); //then we get new pivot value for partition
				quicksort(elements, right, p-1); // apply to left part
				quicksort(elements, p+1, left); // apply to right part
			}		
		}
		public static void swap(List<Index_String> elements, int index1, int index2) {
			Index_String temp = elements.get(index1);
			elements.set(index1, elements.get(index2));
			elements.set(index2, temp);

		}
		// get the index from random method
		private  static int getPivot(int right, int left) {
			Random rand = new Random();
			int index_value = rand.nextInt(left-right+1)+right;
				return index_value ; 
		}
		
		//move all n < pivot to left, and all n > pivot to right of pivot, then return pivot index
		public static int partition( List<Index_String> elements, int right, int left) {
			swap(elements, right, getPivot(right, left)); // get the pivot value from random and get to the most left position
			int boundary = right +1 ; //left pointer
			for (int i=boundary; i<=left; i++) {
				int compare = elements.get(i).CompareTo( elements.get(right) );
				if (compare < 0) { //pivot is A[right]
					swap(elements, i,boundary); // swap with boundary value
					boundary+=1;
					}
				}
				swap(elements,right, boundary-1); // swap the pivot value to the proper position
				return boundary-1; // return the index of the pivot value
			}
}