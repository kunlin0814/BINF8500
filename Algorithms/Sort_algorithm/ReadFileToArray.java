import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadFileToArray {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String[] text = readArray("G:\\UGA\\二上\\8500Algorithms\\sample1k.fastq");
		//for (int i=0; i<text.length; i++ ) {
		//	System.out.println(text[i]);
		// System.out.println(Array.toString(text); this method make print array easy
		//}
		String text = readString("G:\\\\UGA\\\\二上\\\\8500Algorithms\\\\sample1k.fastq");
		System.out.println(text);
	}
	public static String readString(String file){
		//@parm String file
				String text = "";
				try {
				File f = new File(file);
				Scanner s = new Scanner(f);
				while (s.hasNextLine()) {
					text = text + s.nextLine()+'\n';}
				return text ;
				}
				catch(FileNotFoundException e) {
				System.out.println("File not found");	
				
		}
				return null;	
		}
	
	/*
	 * This is method will take a string file name. It will copy the contents into an array of strings
	 * 
	 */
	public static String[] readArray(String file) {
	// step 1 : count  how many lines are in the file
	// step 2: create an array and copy the file into array
		int ctr = 0;
		try{
		Scanner s1 = new Scanner(new File(file));
		while (s1.hasNextLine()) {
			ctr +=1;
			s1.nextLine();}
		String[]words = new String[ctr];
		Scanner s2 = new Scanner(new File(file));
		for (int i=0; i<ctr; i++) {
			words[i] = s2.nextLine(); 
		}
		return words ;
		}

	catch(FileNotFoundException e){
		System.out.println("File not found");
	}
	return null;
}
}