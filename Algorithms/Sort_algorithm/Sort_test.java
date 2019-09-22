import java.util.*;

public class Sort_test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			char [] my_list = {'f','d','c','g'};
			
			char temp;
			for (int i =0; i < my_list.length; i++) {
				for (int j =1; j < my_list.length-1;j++)
				if (my_list[j-1]>my_list[j]) {
					temp = my_list[j];
					my_list[j] = my_list[j-1];
					my_list[j-1]=temp;
						
					
				}
				System.out.print(Arrays.toString(my_list));
			}
			
			
			// TODO Auto-generated method stub
	//my_list.length-1 (means the final elements)
		
	

	}
}


