import java.io.IOException;
import java.util.Scanner;

public class test {

	public static void main(String[] args) throws IOException {
		Scanner input = new Scanner(System.in);
		System.out.println("Do you want to see the Cluster Summary value (yes/no) ?");
		String s = input.nextLine().toLowerCase();
		boolean Cluster_summary_value ;
		if ( s.equalsIgnoreCase("yes")) {
		System.out.println("yes"); }
		else {
		System.out.println("No");;	
		}
	}
}
