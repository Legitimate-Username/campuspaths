package hw4;

import java.util.*;

public final class Edge<T1, T2>{

	private final T1 destination;
	private T2 cost;
	
	public Edge(){
		destination=null;
		cost=null;
	}
	
	public Edge(T1 s, T2 c){
		destination=s;
		cost=c;
	}
	
	public T1 getDest(){
		return destination;
	}

	public T2 getCost(){
		return cost;
	}
}