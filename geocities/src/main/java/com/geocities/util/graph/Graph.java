package com.geocities.util.graph;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

/**
 * 
 * @author Ikram Soomro
 *
 */
public final class Graph<E> {

	private final Vertex<E>[] adjacencyList;
	private final Set<Set<Integer>> connectedNodesGroup;

	public static final String DFS = "DFS";
	public static final String BFS = "BFS";

	/**
	 * 
	 * @param uniqueElements
	 * @param pairs
	 */
	public Graph(List<E> uniqueElements, Set<Pair<E>> pairs) {
		this(uniqueElements, pairs, DFS);
	}

	/**
	 * 
	 * @param uniqueElements
	 * @param pairs
	 * @param algorithm
	 */
	@SuppressWarnings("unchecked")
	public Graph(List<E> uniqueElements, Set<Pair<E>> pairs, String algorithm) {

		adjacencyList = new Vertex[uniqueElements.size()];

		// construct vertices
		for (int vertexIndex = 0; vertexIndex < adjacencyList.length; vertexIndex++) {
			adjacencyList[vertexIndex] = new Vertex<E>(uniqueElements.get(vertexIndex), null);
		}

		// form edges
		Iterator<Pair<E>> iterator = pairs.iterator();
		while (iterator.hasNext()) {
			Pair<E> pair = iterator.next();
			// read vertex names and translate to vertex numbers
			int vertexIndex1 = indexForElement(pair.getLeft());
			int vertexIndex2 = indexForElement(pair.getRight());

			// add vertexIndex2 to front of vertexIndex1's adjacency list and
			adjacencyList[vertexIndex1]
					.setAdjacencyList(new Edge(vertexIndex2, adjacencyList[vertexIndex1].getAdjacencyList()));
			// add vertexIndex1 to front of vertexIndex2's adjacency list
			adjacencyList[vertexIndex2]
					.setAdjacencyList(new Edge(vertexIndex1, adjacencyList[vertexIndex2].getAdjacencyList()));
		}
		if (algorithm == DFS) {
			this.connectedNodesGroup = getConnectedNodesUsingDFS();
		} else {
			this.connectedNodesGroup = getConnectedNodesUsingBFS();
		}
	}

	private Set<Set<Integer>> getConnectedNodesGroup() {
		return connectedNodesGroup;
	}

	private int indexForElement(E element) {
		for (int vertexIndex = 0; vertexIndex < adjacencyList.length; vertexIndex++) {
			if (adjacencyList[vertexIndex].getElement().equals(element)) {
				return vertexIndex;
			}
		}
		return -1;
	}

	/**
	 * 
	 * @param element1
	 * @param element2
	 * @return boolean
	 */
	public boolean isPathPresent(E element1, E element2) {
		int indexVertex1 = indexForElement(element1);
		int indexVertex2 = indexForElement(element2);
		if (indexVertex1 == -1 || indexVertex2 == -1)
			return false;

		Optional<Set<Integer>> findFirst = getConnectedNodesGroup().stream()
				.filter(set -> set.contains(indexVertex1) && set.contains(indexVertex2)).findFirst();
		return findFirst.isPresent();
	}

	private void dfs(int vertexIndex, Set<Integer> vistedNodes, Set<Integer> connectedNodes) {
		vistedNodes.add(vertexIndex);
		connectedNodes.add(vertexIndex);
		for (Edge edge = adjacencyList[vertexIndex].getAdjacencyList(); edge != null; edge = edge.getNext()) {
			if (!vistedNodes.contains(edge.getVertexIndex())) {
				dfs(edge.getVertexIndex(), vistedNodes, connectedNodes);
			}
		}
	}

	private Set<Set<Integer>> getConnectedNodesUsingDFS() {
		Set<Integer> visitedNodes = new HashSet<>();
		Set<Set<Integer>> connectedNodesGroup = new HashSet<>();
		for (int vertexIndex = 0; vertexIndex < adjacencyList.length; vertexIndex++) {
			if (!visitedNodes.contains(vertexIndex)) {
				Set<Integer> connectedNodes = new HashSet<>();
				connectedNodesGroup.add(connectedNodes);
				dfs(vertexIndex, visitedNodes, connectedNodes);
			}
		}
		return connectedNodesGroup;
	}

	private void bfs(int vertexIndex, Set<Integer> connectedNodes) {
		Set<Integer> visitedNodes = new HashSet<>();
		LinkedList<Integer> queue = new LinkedList<Integer>();
		visitedNodes.add(vertexIndex);
		queue.add(vertexIndex);

		while (queue.size() != 0) {
			vertexIndex = queue.poll();
			connectedNodes.add(vertexIndex);
			for (Edge edge = adjacencyList[vertexIndex].getAdjacencyList(); edge != null; edge = edge.getNext()) {
				int vIndex = edge.getVertexIndex();
				if (!visitedNodes.contains(vIndex)) {
					visitedNodes.add(vIndex);
					queue.add(vIndex);
				}
			}
		}
	}

	private Set<Set<Integer>> getConnectedNodesUsingBFS() {
		Set<Set<Integer>> connectedNodesGroup = new HashSet<>();
		for (int vertexIndex = 0; vertexIndex < adjacencyList.length; vertexIndex++) {
			Set<Integer> connectedNodes = new TreeSet<>();
			bfs(vertexIndex, connectedNodes);
			connectedNodesGroup.add(connectedNodes);
		}
		return connectedNodesGroup;
	}
}
