package hw4;

import java.util.*;

public class GraphWrapper<T1, T2>{

	private Graph<T1, T2> graph;

	public GraphWrapper(){
		graph=new Graph<T1, T2>();
	}

	public void addNode(T1 nodeData){
		graph.addNode(nodeData);
	}

	public void addEdge(T1 parentNode, T1 childNode, T2 edgeLabel){
		graph.addEdge(parentNode, childNode, edgeLabel);
	}

	public Iterator<T1> listNodes(){
		return graph.listNodes();
	}

	public Iterator<String> listChildren(T1 parentNode){
		return graph.listChildren(parentNode);
	}

}