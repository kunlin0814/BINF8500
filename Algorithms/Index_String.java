import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Index_String {

    int index;
	String string;

public Index_String(int index, String string){
        this.index = index;
        this.string = string;
    }

    private int CompareTo(Index_String element) {
    	int result = this.string.compareTo(element.string);
        return result;
    }

 public static void main(String[] args) {
	 long start_time=System.currentTimeMillis();
	 ArrayList <String> text = readString("G:\\UGA\\二上\\8500Algorithms\\sample100k.fastq");
		ArrayList <String> aList =  new ArrayList <String>();
		ArrayList <Integer> index_List = new ArrayList <Integer>();
		List<Index_String> elements = new ArrayList<Index_String>();
		//List <List> final_List = new ArrayList <List>();
		//PrintWriter output = new PrintWriter("zzz-final.text");
		for (int i=1; i<text.size();i+=4) {
			aList.add(text.get(i));
	
		}		
		
		for (int i = 1; i < aList.size(); i+=4) {
		    elements.add(new Index_String(i, aList.get(i)));}	
		quicksort(elements);

	// Init the element list
	for (Index_String element : elements) {
	    System.out.println(text.get(element.index-1));
	    System.out.println(text.get(element.index));
	    System.out.println(text.get(element.index+1));
	    System.out.println(text.get(element.index+2));
	}
	/*for (Element elemnt : elements) {
		System.out.println(elements.in);
	}*/	
	//System.out.println(elements);
		
		
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
				quicksort(elements, right, p-1); // apply to left 
				quicksort(elements, p+1, left); // apply to right 
			}		
		}
		public static void swap(List<Index_String> elements, int index1, int index2) {
			Index_String temp = elements.get(index1);
			elements.set(index1, elements.get(index2));
			elements.set(index2, temp);

		}
		private  static int getPivot(int right, int left) { // get the index from random method
			Random rand = new Random();
			int index_value = rand.nextInt(left-right+1)+right; // random index
				return index_value ; // I made wrong here 
		}
		
		//move all n < pivot to left of pivot and all n> pivot to right of pivot, then return pivot index
		public static int partition( List<Index_String> elements, int right, int left) {
			swap(elements, right, getPivot(right, left)); // get the pivot value from random and get to the most left position
			int border = right +1 ; //left pointer
			for (int i=border; i<=left; i++) {
				int compare = elements.get(i).CompareTo( elements.get(right) );
				if (compare < 0) { //pivot is A[right]
					swap(elements, i,border); // swap with border value
					border+=1;
					}
				}
				swap(elements,right, border-1); // swap the pivot value to the proper position
				return border-1; // return the index of the pivot value
			}
}