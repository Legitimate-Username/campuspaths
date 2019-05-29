package hw7;
 
import hw4.Edge;
import hw4.Graph;
import hw7.CPath;

import java.util.*;
import java.io.*;

public class CampusPaths{

	public static Graph<Building, Double> graph;
	public static HashMap<String, Building> names;
	public static HashMap<Integer, Building> ids;
	
	public CampusPaths(){
		graph=new Graph<Building, Double>();
		names=new HashMap<String, Building>();
		ids=new HashMap<Integer, Building>();
	}

	public CampusPaths(String nodefile, String edgefile){
		graph=new Graph<Building, Double>();
		names=new HashMap<String, Building>();
		ids=new HashMap<Integer, Building>();
		createNewGraph(nodefile, edgefile);
	}
	
	public void createNewGraph(String nodefile, String edgefile){
		try{
			ArrayList<Building> buildings=new ArrayList<Building>();
			CampusParser.readNodes(nodefile, buildings);
			for(Building b:buildings){
				graph.addNode(b);
				if(b.getName().length()>0){
					names.put(b.getName(), b);
				}
				ids.put(b.getID(), b);
			}
			ArrayList<ArrayList<Integer>> edges=new ArrayList<ArrayList<Integer>>();
			CampusParser.readEdges(edgefile, edges);
			for(ArrayList<Integer> e:edges){
				int startInt=new Integer(e.get(0));
				int endInt=new Integer(e.get(1));
				Building start=new Building();
				Building end=new Building();
				for(Building b:buildings){
					if(b.getID()==startInt){
						start.setName(b.getName());
						start.setID(b.getID());
						start.setX(b.getX());
						start.setY(b.getY());
					}
					if(b.getID()==endInt){
						end.setName(b.getName());
						end.setID(b.getID());
						end.setX(b.getX());
						end.setY(b.getY());
					}
				}
				double x=Math.abs(end.getX()-start.getX());
				double y=Math.abs(end.getY()-start.getY());
				double length=(Math.sqrt(x*x+y*y));
				graph.addEdge(start, end, length);
				graph.addEdge(end, start, length);
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	private static double distance(int x1, int y1, int x2, int y2){
		double x=Math.abs(x2-x1);
		double y=Math.abs(y2-y1);
		return (Math.sqrt(x*x+y*y));
	}

	private static String circleSector2(Building n1, Building n2) {
        double angle; // = Math.toDegrees(Math.acos((n1.y-n2.y)/distance(n1.x,n1.y,n2.x,n2.y)));
        if (n1.getX() < n2.getX()) { // We are in the East semicircle
            angle = Math.toDegrees(Math.acos((n1.getY()-n2.getY())/distance(n1.getX(),n1.getY(),n2.getX(),n2.getY())));
            if (0 <= angle && angle < 22.5) 
                return "North";
            else if (22.5 <= angle && angle < 67.5)
                return "NorthEast";
            else if (67.5 <= angle && angle < 112.5)
                return "East";
            else if (112.5 <= angle && angle < 157.5)
                return "SouthEast";
            else 
                return "South";
        }
        else {
            angle = Math.toDegrees(Math.acos(-(n1.getY()-n2.getY())/distance(n1.getX(),n1.getY(),n2.getX(),n2.getY())));
            if (0 <= angle && angle < 22.5) 
                return "South";
            else if (22.5 <= angle && angle < 67.5)
                return "SouthWest";
            else if (67.5 <= angle && angle < 112.5)
                return "West";
            else if (112.5 <= angle && angle < 157.5)
                return "NorthWest";
            else 
                return "North";
        }
    }

	public static String findPath(String start, String dest){
		String stringbuilder=new String();
		boolean exists1=new Boolean(true);
		boolean exists2=new Boolean(true);
		//Iterator<Building> buildings=graph.listNodes();
		if(!names.containsKey(start)){
			stringbuilder+="Unknown building: ["+start+"]\n";
			exists1=false;
		}
		if(!names.containsKey(dest)){
			if(!start.equals(dest)){
				stringbuilder+="Unknown building: ["+dest+"]\n";
			}
			exists2=false;
		}
		if(exists1&&exists2){
			Building startb=new Building(names.get(start));
			Building destb=new Building(names.get(dest));
			if(!start.equals(dest)){
				PriorityQueue<CPath> active=new PriorityQueue<CPath>(new PathComparator());
				ArrayList<Building> finished=new ArrayList<Building>();
				ArrayList<Edge<Building, Double>> blank=new ArrayList<Edge<Building, Double>>();
				CPath initial=new CPath(names.get(start), names.get(start), blank);
				CPath solution=new CPath();
				active.add(initial);
				while(active.size()>0){
					CPath minpath=new CPath(active.poll());
					Building mindest=new Building(minpath.getEnd());
					if(mindest.equals(destb)){
						solution=minpath;
						break;
					}
					if(finished.contains(mindest)){
						continue;
					}
					//Edge<Building, Double> child=new Edge<Building, Double>();
					HashSet<Edge<Building, Double>> children=new HashSet<Edge<Building, Double>>();
					children=graph.getEdges(mindest);
					for(Edge<Building, Double> e:children){
						if(!finished.contains(e.getDest())){
							Building newStart=new Building(minpath.getStart());
							Building newEnd=new Building(minpath.getEnd());
							ArrayList<Edge<Building, Double>> newEdges=new ArrayList<Edge<Building, Double>>(minpath.getEdges());
							CPath newPath=new CPath(newStart, newEnd, newEdges);
							newPath.addEdge(e);
							active.add(newPath);
						}
					}
					finished.add(mindest);
				}
				if(solution.getEdges().size()==0){
					stringbuilder+="There is no path from "+start+" to "+dest+".\n";
				}
				else{
					stringbuilder+="Path from "+start+" to "+dest+":\n";
					Iterator<Edge<Building, Double>> itr1=solution.getEdges().listIterator();
					Iterator<Edge<Building, Double>> itr2=solution.getEdges().listIterator();
					Edge<Building, Double> e=new Edge<Building, Double>();
					e=itr2.next();
					if(e.getDest().getName().equals("")){
						stringbuilder+="\tWalk "+circleSector2(startb, e.getDest())+" to (Intersection "+e.getDest().getID()+")\n";
					}
					else{
						stringbuilder+="\tWalk "+circleSector2(startb, e.getDest())+" to ("+e.getDest().getName()+")\n";
					}
					while(itr2.hasNext()){
						Edge<Building, Double> e1=new Edge<Building, Double>();
						Edge<Building, Double> e2=new Edge<Building, Double>();
						e1=itr1.next();
						e2=itr2.next();
						if(e2.getDest().getName().equals("")){
							stringbuilder+="\tWalk "+circleSector2(e1.getDest(), e2.getDest())+" to (Intersection "+e2.getDest().getID()+")\n";
						}
						else{
							stringbuilder+="\tWalk "+circleSector2(e1.getDest(), e2.getDest())+" to ("+e2.getDest().getName()+")\n";
						}
					}
					stringbuilder+="Total distance: "+String.format("%.3f", solution.getCost())+" pixel units.\n";
				}
			}
			else{
				stringbuilder+="Path from "+start+" to "+dest+":\n";
				stringbuilder+="Total distance: 0.000 pixel units.\n";
			}
		}
		return stringbuilder.toString();
	}

	public static String listBuildings(){
		String stringbuilder=new String();
		TreeSet<String> buildings=new TreeSet<String>();
		for(String s:names.keySet()){
			buildings.add(s);
		}
		Iterator<String> itr=buildings.iterator();
		while(itr.hasNext()){
			//System.out.println(itr.next());
			String string=new String(itr.next());
			stringbuilder+=string+","+names.get(string).getID()+"\n";
		}
		return stringbuilder.toString();
	}
	
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    return true;
	}
	
	public static void main(String[] arg) {
		String nodefile="hw7/data/RPI_map_data_Nodes.csv";
		String edgefile="hw7/data/RPI_map_data_Edges.csv";
		CampusPaths campus=new CampusPaths(nodefile, edgefile);
		Scanner scanner=new Scanner(System.in);
		while(scanner.hasNextLine()){
			String s=scanner.nextLine();
			if(s.equals("b")){
				System.out.print(listBuildings());
			}
			else if(s.equals("r")){
				System.out.print("First building id/name, followed by Enter: ");
				String b1=scanner.nextLine();
				System.out.print("Second building id/name, followed by Enter: ");
				String b2=scanner.nextLine();
				if(!names.containsKey(b1)&&isInteger(b1)){
					Integer i1=Integer.parseInt(b1);
					if(ids.containsKey(i1)){
						if(ids.get(i1).getName().length()>0){
							b1=ids.get(i1).getName();
						}
					}
				}
				if(!names.containsKey(b2)&&isInteger(b2)){
					Integer i2=Integer.parseInt(b2);
					if(ids.containsKey(i2)){
						if(ids.get(i2).getName().length()>0){
							b2=ids.get(i2).getName();
						}
					}
				}
				System.out.print(findPath(b1, b2));
			}
			else if(s.equals("m")){
				System.out.println("b lists all buildings (only buildings) in the form \"name, id\" in lexicographic (alphabetical) order of name.");
				System.out.println("r prompts the user for the ids or names of two buildings (only buildings!) and prints directions for the shortest route between them.");
				System.out.println("q quits the program.");
				System.out.println("m prints a menu of all commands.");
			}
			else if(s.equals("q")){
				return;
			}
			else{
				System.out.println("Unknown option");
			}
		}
		scanner.close();
	}
}