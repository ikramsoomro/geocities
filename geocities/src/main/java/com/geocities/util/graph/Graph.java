package com.geocities.util.graph;

import static com.geocities.util.graph.GraphSearchAlgorithm.DEPTH_FIRST_SEARCH;

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

	private final boolean searchRequestTime;
	private final GraphSearchAlgorithm algorithm;

	/**
	 * 
	 * @param uniqueElements
	 * @param pairs
	 */
	public Graph(List<E> uniqueElements, Set<Pair<E>> pairs) {
		this(uniqueElements, pairs, DEPTH_FIRST_SEARCH, false);
	}

	/**
	 * 
	 * @param uniqueElements
	 * @param pairs
	 * @param searchRequestTime
	 */
	public Graph(List<E> uniqueElements, Set<Pair<E>> pairs, boolean searchRequestTime) {
		this(uniqueElements, pairs, DEPTH_FIRST_SEARCH, searchRequestTime);
	}

	/**
	 * 
	 * @param uniqueElements
	 * @param pairs
	 * @param algorithm
	 */
	public Graph(List<E> uniqueElements, Set<Pair<E>> pairs, GraphSearchAlgorithm algorithm) {
		this(uniqueElements, pairs, algorithm, false);
	}

	/**
	 * 
	 * @param uniqueElements
	 * @param pairs
	 * @param algorithm
	 */
	@SuppressWarnings("unchecked")
	public Graph(List<E> uniqueElements, Set<Pair<E>> pairs, GraphSearchAlgorithm algorithm,
			boolean searchRequestTime) {

		this.algorithm = algorithm;
		this.searchRequestTime = searchRequestTime;
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
		if (searchRequestTime) {
			this.connectedNodesGroup = null;
		} else if (algorithm == DEPTH_FIRST_SEARCH) {
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

		if (searchRequestTime) {
			if (algorithm == DEPTH_FIRST_SEARCH) {
				return dfsMatch(indexVertex1, indexVertex2, new HashSet<Integer>());
			} else {
				return bfsMatch(indexVertex1, indexVertex2);
			}
		}
		Optional<Set<Integer>> findFirst = getConnectedNodesGroup().stream()
				.filter(set -> set.contains(indexVertex1) && set.contains(indexVertex2)).findFirst();
		return findFirst.isPresent();
	}

	private boolean dfsMatch(int vertexIndexStart, int vertexIndexEnd, Set<Integer> vistedNodes) {
		vistedNodes.add(vertexIndexStart);
		if (vertexIndexStart == vertexIndexEnd)
			return true;
		for (Edge edge = adjacencyList[vertexIndexStart].getAdjacencyList(); edge != null; edge = edge.getNext()) {
			if (!vistedNodes.contains(edge.getVertexIndex())) {
				boolean matched = dfsMatch(edge.getVertexIndex(), vertexIndexEnd, vistedNodes);
				if (matched)
					return true;
			}
		}
		return false;
	}

	private void dfsCollect(int vertexIndexStart, Set<Integer> vistedNodes, Set<Integer> connectedNodes) {
		vistedNodes.add(vertexIndexStart);
		connectedNodes.add(vertexIndexStart);
		for (Edge edge = adjacencyList[vertexIndexStart].getAdjacencyList(); edge != null; edge = edge.getNext()) {
			if (!vistedNodes.contains(edge.getVertexIndex())) {
				dfsCollect(edge.getVertexIndex(), vistedNodes, connectedNodes);
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
				dfsCollect(vertexIndex, visitedNodes, connectedNodes);
			}
		}
		return connectedNodesGroup;
	}

	private void bfsCollect(int vertexIndex, Set<Integer> connectedNodes) {
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

	private boolean bfsMatch(int vertexIndexStart, int vertexIndexEnd) {
		Set<Integer> visitedNodes = new HashSet<>();
		LinkedList<Integer> queue = new LinkedList<Integer>();
		visitedNodes.add(vertexIndexStart);
		queue.add(vertexIndexStart);

		while (queue.size() != 0) {
			vertexIndexStart = queue.poll();
			for (Edge edge = adjacencyList[vertexIndexStart].getAdjacencyList(); edge != null; edge = edge.getNext()) {
				int vIndex = edge.getVertexIndex();
				if (vertexIndexEnd == vIndex)
					return true;
				if (!visitedNodes.contains(vIndex)) {
					visitedNodes.add(vIndex);
					queue.add(vIndex);
				}
			}
		}
		return false;
	}

	private Set<Set<Integer>> getConnectedNodesUsingBFS() {
		Set<Set<Integer>> connectedNodesGroup = new HashSet<>();
		for (int vertexIndex = 0; vertexIndex < adjacencyList.length; vertexIndex++) {
			Set<Integer> connectedNodes = new TreeSet<>();
			bfsCollect(vertexIndex, connectedNodes);
			connectedNodesGroup.add(connectedNodes);
		}
		return connectedNodesGroup;
	}
}
