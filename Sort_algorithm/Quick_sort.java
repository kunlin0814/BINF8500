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
import java.io.BufferedWriter;
import java.io.FileWriter;


/* This script takes args[0] as an input file and will print out the sorting result
 * The script reads the args[0] and put into an arraylist.
 * This script create an output file in args[1]
 * I also created an Index_String class so each Index_String class have an index and a string
 * Once we sort the Index_String, we can know where is the string after sorting.
 * this sort uses Lomuto’s partition method
 */ 
public class Quick_sort { 

    int index;
	String string;

public Index_String(int index, String string){
        this.index = index;
        this.string = string;
    }

private int CompareTo(Index_String object) { 
// create a method that can compare the index_string with other index string class but only compare the sequence
    	int result = this.string.compareTo(object.string);
        return result;
    }

 public static void main(String[] args) throws IOException {
	 long start_time=System.currentTimeMillis();
	 ArrayList <String> text = readString("G:\\UGA\\2fall\\8500Algorithms\\Sort\\sample1M.fastq");
	 ArrayList <String> aList =  new ArrayList <String>();
	 List <Index_String> elements = new ArrayList <Index_String >();
	 BufferedWriter bw = null;
	 File file = new File("zzz.txt");
	 FileWriter fw = new FileWriter(file);
	 bw = new BufferedWriter(fw);
	
	for (int i=1; i<text.size();i+=4) {
		elements.add(new Index_String(i,text.get(i)));}
// the element ArrayList contains the sequence data and the index for that sequence 
	
	quicksort(elements);

	// After sorting, we look the sorting index and use that index to go back to the original data to get the correct order of each header and sequence.
	for (Index_String element : elements) {
		int header_index = (element.index-1);
		int read_index = header_index+1;
		int second_index = header_index+2;
		int quality_index = header_index+3;
		bw.write(text.get(header_index)+"\n");
		bw.write(text.get(read_index)+"\n");
		bw.write(text.get(second_index)+"\n");
		bw.write(text.get(quality_index)+"\n");
		
	    //System.out.printf("%s",text.get(header_index));
	    //System.out.printf("%n");
	    //System.out.printf("%s",text.get(read_index));
	    //System.out.printf("%n");
	    //System.out.printf("%s",text.get(second_index));
	    //System.out.printf("%n");
	    //System.out.printf("%s",text.get(quality_index));
	}
	bw.close();
	long time_end=System.currentTimeMillis();
	long program_time = (time_end-start_time);
	System.out.println("Rearrange new list takes "+ program_time);
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
	public static void quicksort(List<Index_String> elements, int low, int high) {
			if (low < high) { // low and high pointer are not the same or not cross.
				int p = partition(elements, low, high ); //then we get new pivot value for partition
				quicksort(elements, low, p-1);  // apply to low part
				quicksort(elements, p+1, high); // apply to high part
			}		
		}
		public static void swap(List<Index_String> elements, int index1, int index2) {
			Index_String temp = elements.get(index1);
			elements.set(index1, elements.get(index2));
			elements.set(index2, temp);
		}
		// get the index from random method
		private  static int getPivot(int low, int high) {
			Random rand = new Random();
			int index_value = rand.nextInt(high-low+1)+low;
				return index_value ; 
		}
		
	//move all n > pivot to high, and all n < pivot to low of pivot, then return pivot index
		public static int partition( List<Index_String> elements, int low, int high) {
			swap(elements, low, getPivot(low, high)); // get the pivot value from random and get to the lowest position
			int boundary = low +1 ; 
			for (int i=boundary; i<=high; i++) {
				int compare = elements.get(i).CompareTo( elements.get(low) );
				if (compare < 0) { //pivot is A[low]
					swap(elements, i,boundary); // swap with boundary value
					boundary+=1;
					}
				}
				swap(elements,low, boundary-1); // swap the pivot value to the proper position
				return boundary-1; // return the index of the pivot value
			}
}