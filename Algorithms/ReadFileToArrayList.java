import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
public class ReadFileToArrayList {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList <String> text = readString("G:\\UGA\\二上\\8500Algorithms\\sample1k.fastq");
		ArrayList <String> aList =  new ArrayList <String>();
		for (int i=1; i<text.size();i+=4) {
			aList.add(text.get(i));
		}
		for (int i=0; i<aList.size();i++) {
			System.out.println(aList.get(i));
		}
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
	
}
