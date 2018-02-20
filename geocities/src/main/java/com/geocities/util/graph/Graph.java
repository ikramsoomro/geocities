package com.geocities.util.graph;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * 
 * @author Ikram Soomro
 *
 */
public final class Graph {

	private final Vertex[] adjacencyList;
	private final Set<Set<Integer>> connectedNodesGroup;

	public Graph(List<String> uniqueElements, Set<Pair<String>> pairs) {

		adjacencyList = new Vertex[uniqueElements.size()];

		// construct vertices
		for (int v = 0; v < adjacencyList.length; v++) {
			adjacencyList[v] = new Vertex(uniqueElements.get(v), null);
		}

		// form edges
		Iterator<Pair<String>> iterator = pairs.iterator();
		while (iterator.hasNext()) {
			Pair<String> pair = iterator.next();
			// read vertex names and translate to vertex numbers
			int vertexIndex1 = indexForName(pair.getLeft());
			int vertexIndex2 = indexForName(pair.getRight());

			// add v2 to front of v1's adjacency list and
			adjacencyList[vertexIndex1]
					.setAdjacencyList(new Edge(vertexIndex2, adjacencyList[vertexIndex1].getAdjacencyList()));
			// add v1 to front of v2's adjacency list
			adjacencyList[vertexIndex2]
					.setAdjacencyList(new Edge(vertexIndex1, adjacencyList[vertexIndex2].getAdjacencyList()));
		}
		this.connectedNodesGroup = getConnectedNodes();
	}

	public Set<Set<Integer>> getConnectedNodesGroup() {
		return connectedNodesGroup;
	}

	private int indexForName(String name) {
		for (int v = 0; v < adjacencyList.length; v++) {
			if (adjacencyList[v].getName().equals(name)) {
				return v;
			}
		}
		return -1;
	}

	public boolean isPathPresent(String element1, String element2) {
		int indexVertex1 = indexForName(element1);
		int indexVertex2 = indexForName(element2);
		if (indexVertex1 == -1 || indexVertex2 == -1)
			return false;

		Optional<Set<Integer>> findFirst = getConnectedNodesGroup().stream()
				.filter(set -> set.contains(indexVertex1) && set.contains(indexVertex2)).findFirst();
		return findFirst.isPresent();
	}

	private void dfs(int vertexIndex, Set<Integer> vistedNodes, Set<Integer> connectedNodes) {
		vistedNodes.add(vertexIndex);
		connectedNodes.add(vertexIndex);
		int v = vertexIndex;
		for (Edge edge = adjacencyList[v].getAdjacencyList(); edge != null; edge = edge.getNext()) {
			if (!vistedNodes.contains(edge.getVertexIndex())) {
				dfs(edge.getVertexIndex(), vistedNodes, connectedNodes);
			}
		}
	}

	private Set<Set<Integer>> getConnectedNodes() {
		Set<Integer> visitedNodes = new HashSet<>();
		Set<Set<Integer>> connectedNodesGroup = new HashSet<>();
		for (int v = 0; v < adjacencyList.length; v++) {
			if (!visitedNodes.contains(v)) {
				Set<Integer> connectedNodes = new HashSet<>();
				connectedNodesGroup.add(connectedNodes);
				dfs(v, visitedNodes, connectedNodes);
			}
		}
		return connectedNodesGroup;
	}
}
