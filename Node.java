package hw4;

import java.util.*;

public class Node<T1, T2>{

	private T1 name;
	private HashSet<Edge<T1, T2>> edges;

	public Node(T1 n){
		name=n;
		edges=new HashSet<Edge<T1, T2>>();
	}
	
	public T1 getName(){
		return name;
	}

	public HashSet<Edge<T1, T2>> getEdges(){
		return edges;
	}

	public void addEdge(Edge<T1, T2> e){
		edges.add(e);
	}
	
	public Edge<T1, T2> getEdge(T1 dest, T2 cost){
		for(Edge<T1, T2> e:edges){
			if(e.getDest()==dest&&e.getCost()==cost){
				return e;
			}
		}
		return null;
	}
	
	public void clearEdges(){
		edges.clear();
	}

	@Override
	public boolean equals(Object obj){
		//if(obj instanceof Node<?, ?>){
			Node<T1, T2> n=(Node<T1, T2>) obj;
			if(this.getName()==n.getName()){
				return true;
			}
		//}
		return false;
		//return super.equals(obj);
	}
}