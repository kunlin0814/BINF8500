import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Alignment {

	public static void main(String[] args) throws IOException{
		// read two fastq file into array list 
		// define the scores for match, mismatch and gap
		 ArrayList <String> text = readString("C:\\Users\\abc73_000\\Desktop\\Fasta1.txt");
		 int arraySize = 0;
		 for (int i=1; i< text.size();i++) {
		 arraySize+= text.get(i).length();
		}
		ArrayList <Character> seq = new ArrayList <Character>();
		 
		 for (int i=1; i< text.size();i++) {
			 for (int j= 0 ; j < text.get(i).length(); j++) {
				 char sequence =text.get(i).charAt(j);
				 	seq.add(sequence);
				
				}
				 }
		 for (int i=0; i< seq.size();i++) {
			 System.out.println(seq.get(i));
		 }
		
		 }
	
	
	
	public static ArrayList <String> readString(String file) throws IOException{ // a function to read the data into an arraylist
		
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