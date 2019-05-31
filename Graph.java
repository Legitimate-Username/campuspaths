package hw4;

import java.util.*;

public class Graph<T1, T2>{

	private HashMap<T1, HashSet<Edge<T1, T2>>> graph;

	public Graph(){
		graph=new HashMap<T1, HashSet<Edge<T1, T2>>>();
	}
	
	/*
	 * The graph is stored as an ArrayList of Nodes, and each Node consists of a name 
	 * corresponding to an ArrayList of Edges, and each Edge stores the name of a destination 
	 * Node as well as a label. 
	 * 
	 * Representation invariant: no two nodes with the same name can exist in the graph.  
	 */

	/*private void checkRep(){
		for(int i=0;i<nodes.size()-1;i++){
			for(int j=i+1;j<nodes.size();j++){
				if(nodes.get(i).getName().equals(nodes.get(j).getName())){
					throw new RuntimeException("Duplicate node found.");
				}
			}
		}
	}*/
	
	public void addNode(T1 nodeData){
		if(!graph.containsKey(nodeData)){
			HashSet<Edge<T1, T2>> empty=new HashSet<Edge<T1, T2>>();
			graph.put(nodeData, empty);
		}
		//checkRep();
	}

	public void addEdge(T1 parentNode, T1 childNode, T2 edgeLabel){
		if(graph.containsKey(parentNode)&&graph.containsKey(childNode)){
			HashSet<Edge<T1, T2>> edges=graph.get(parentNode);
			Edge<T1, T2> e=new Edge<T1, T2>(childNode, edgeLabel);
			edges.add(e);
		}
		//checkRep();
	}

	public Iterator<T1> listNodes(){
		TreeSet<T1> strings=new TreeSet<T1>();
		HashSet<T1> keys=new HashSet<T1>(graph.keySet());
		Iterator<T1> kitr=keys.iterator();
		while(kitr.hasNext()){
			strings.add(kitr.next());
		}
		Iterator<T1> sitr=strings.iterator();
		return sitr;
	}

	public Iterator<String> listChildren(T1 parentNode){
		TreeSet<String> strings=new TreeSet<String>();
		if(graph.containsKey(parentNode)){
			HashSet<Edge<T1, T2>> children=new HashSet<Edge<T1, T2>>(graph.get(parentNode));
			Iterator<Edge<T1, T2>> itr=children.iterator();
			while(itr.hasNext()){
				Edge<T1, T2> e=itr.next();
				String s=new String(e.getDest()+"("+e.getCost()+")");
				strings.add(s);
			}
		}
		Iterator<String> sitr=strings.iterator();
		return sitr;
	}

	/*public boolean edgeExists(T1 start, T1 dest){
		HashSet<Edge<T1, T2>> edges=new HashSet<Edge<T1, T2>>(graph.get(start));
		for(Edge<T1, T2> e:edges){
			if(e.getDest().equals(dest)){
				return true;
			}
		}
		return false;
	}*/

	public T2 getEdge(T1 start, T1 dest){
		HashSet<Edge<T1, T2>> edges=new HashSet<Edge<T1, T2>>(graph.get(start));
		if(edges.size()==0){
			return null;
		}
		TreeSet<T2> labels=new TreeSet<T2>();
		for(Edge<T1, T2> e:edges){
			if(e.getDest().equals(dest)){
				labels.add(e.getCost());
			}
		}
		return labels.first();
	}
	
	public HashSet<Edge<T1, T2>> getEdges(T1 n){
		return graph.get(n);
	}
	
	public boolean nodeIn(T1 node){
		return graph.containsKey(node);
	}

	/*public void clearEdges(T1 start, T1 dest){
		HashSet<Edge<T1, T2>> edges=new HashSet<Edge<T1, T2>>(graph.get(start));
		for(Edge<T1, T2> e:graph.get(start)){
			if(e.getDest().equals(dest)){
				edges.remove(e);
			}
		}
		graph.put(start, edges);
	}*/
}