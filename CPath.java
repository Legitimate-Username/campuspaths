package hw7;

import java.util.*;
import hw4.Edge;
import hw7.Building;

public class CPath{

	private Building start;
	private Building end;
	private ArrayList<Edge<Building, Double>> edges;
	private Double cost;

	public CPath(){
		start=new Building();
		end=new Building();
		edges=new ArrayList<Edge<Building, Double>>();
		cost=0.0;
	}

	public CPath(Building s, Building e, ArrayList<Edge<Building, Double>> al){
		start=s;
		end=e;
		edges=al;
		cost=0.0;
		for(Edge<Building, Double> edge:al){
			double temp=(double)edge.getCost();
			cost+=temp;
		}
	}
	
	public CPath(CPath p){
		start=p.getStart();
		end=p.getEnd();
		edges=p.getEdges();
		cost=p.getCost();
	}

	public Building getStart(){
		return start;
	}
	
	public Building getEnd(){
		return end;
	}

	public ArrayList<Edge<Building, Double>> getEdges(){
		return edges;
	}

	public Double getCost(){
		return cost;
	}
	
	public void addEdge(Edge<Building, Double> e){
		edges.add(e);
		cost+=e.getCost();
		end=e.getDest();
	}
}

class PathComparator implements Comparator<CPath>{
	@Override
	public int compare(CPath x, CPath y){
		double c1=x.getCost();
		double c2=y.getCost();
		if(c1==c2){
			/*Building e1=x.getEnd();
			Building e2=y.getEnd();
			if(e1.compareTo(e2)>0){
				return 1;
			}
			if(e2.compareTo(e2)<0){
				return -1;
			}*/
			return 0;
		}
		if(c1>c2){
			return 1;
		}
		if(c1<c2){
			return -1;
		}
		return 0;
	}
}