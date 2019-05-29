package hw7;
 
import java.util.*;
import java.io.*;
 
public class CampusParser {
 
	/** @param: filename The path to the "CSV" file that contains the <hero, book> pairs                                                                                                
		@param: charsInBooks The Map that stores parsed <book, Set-of-heros-in-book> pairs;
				usually an empty Map
		@param: chars The Set that stores parsed characters; usually an empty Set.
		@effects: adds parsed <book, Set-of-heros-in-book> pairs to Map charsInBooks;
				  adds parsed characters to Set chars
		@throws: IOException if file cannot be read of file not a CSV file                                                                                    
	 */
	public static void readNodes(String filename, ArrayList<Building> buildings)
			throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String line = null;

		while ((line = reader.readLine()) != null) {
			String[] building=line.split(",");
			Building b=new Building(building[0], Integer.parseInt(building[1]), Integer.parseInt(building[2]), Integer.parseInt(building[3]));
			buildings.add(b);
		}
	}
	
	public static void readEdges(String filename, ArrayList<ArrayList<Integer>> edges)
			throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String line = null;

		while ((line = reader.readLine()) != null) {
			String[] edge=line.split(",");
			ArrayList<Integer> e=new ArrayList<Integer>();
			e.add(Integer.parseInt(edge[0]));
			e.add(Integer.parseInt(edge[1]));
			edges.add(e);
		}
	}
}