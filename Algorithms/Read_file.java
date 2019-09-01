import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Read_file {

	public static void main(String[] args) { //Exception also work 
		// TODO Auto-generated method stub
		String text = readString("G:\\UGA\\二上\\8500Algorithms\\sample1k.fastq");
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
		}
		catch(FileNotFoundException e) {
		System.out.println("File not found");	
		
}
		return text;	
}
	
}