import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class test2{
public static void main(String[] args) {
  ArrayList <Integer> l1 = new ArrayList <Integer>();
  ArrayList <Integer> l2 = new ArrayList <Integer>();
  l1.add(1);
  l1.add(2);
  l2 = null; 
   System.out.println(l1.equals(l2)); 

}
}