package reply_challenge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PathMaker {
	
	//implementazione dell'algoritmo dijkstra per trovare la migliore strada tra due punti del campo 
	
	List<Vertex> nodes;
	List<Edge> edges;
	
	Set<Vertex> settleNodes;
	Set<Vertex> unSettleNodes;
	Map<Vertex, Vertex> predecessors;
	Map<Vertex, Integer> distance;
	
	public PathMaker(Graph graph) {
		
		this.nodes = new ArrayList<Vertex>(graph.vertexes);
		this.edges = new ArrayList<Edge>(graph.edges);

	}
	
	public void executeDijkstra(Vertex source) {
		settleNodes = new HashSet<Vertex>();
		unSettleNodes = new HashSet<Vertex>();
		distance = new HashMap<Vertex, Integer>();
		predecessors = new HashMap<Vertex, Vertex>();
		distance.put(source, 0);
		unSettleNodes.add(source);
		while(unSettleNodes.size() > 0) {
			Vertex node = getMinimum(unSettleNodes);
			settleNodes.add(node);
			unSettleNodes.remove(node);
			findMinimalDistances(node);
		}
		
	}

	private void findMinimalDistances(Vertex node) {
		List<Vertex> adjacentNodes = getNeighbords(node);
		for(Vertex target : adjacentNodes) {
			if(getShortestDistance(target) > getShortestDistance(node) +getDistance(node, target)) {
				distance.put(target, getShortestDistance(node) + getDistance(node, target));
				predecessors.put(target, node);
				unSettleNodes.add(target);
			}
		}
	}

	private int getDistance(Vertex node, Vertex target) {
		for(Edge edge : edges) {
			if(edge.getSource().equals(node) && edge.getDestination().equals(target)) {
				return edge.getWeight();
			}
		}
		throw new RuntimeException("Should not happen");
	}

	private int getShortestDistance(Vertex destination) {
		Integer d = distance.get(destination);
		if(d == null) {
			return Integer.MAX_VALUE;
		}else {
			return d;
		}
	}

	private List<Vertex> getNeighbords(Vertex node) {
		List<Vertex> neighbors = new ArrayList<Vertex>();
		for(Edge edge: edges) {
			if(edge.getSource().equals(node) && !isSettled(edge.getDestination())) {
				neighbors.add(edge.getDestination());
			}
		}
		return neighbors;
	}

	private boolean isSettled(Vertex vertex) {
		return settleNodes.contains(vertex);

	}

	private Vertex getMinimum(Set<Vertex> vertexes) {
		Vertex minimum = null;
		for(Vertex vertex : vertexes) {
			if(minimum == null) {
				minimum = vertex;
			}else {
				if(getShortestDistance(vertex) < getShortestDistance(minimum)) {
					minimum = vertex;
				}
			}
		}
		return minimum;
	}
	
	//calculate the path drom the surce to the target
	
	public LinkedList<Vertex> getPath(Vertex target){
		
		LinkedList<Vertex> path = new LinkedList<Vertex>();
		Vertex step = target;
		
		if(predecessors.get(step) == null) {
			return null;
		}
		path.add(step);
		while(predecessors.get(step) != null) {
			step = predecessors.get(step);
			path.add(step);
		}
		
		Collections.reverse(path);
		return path;
	}
}
