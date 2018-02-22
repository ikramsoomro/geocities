package com.geocities.util.graph;

public final class Vertex<E> {
	
    private final E element;
    private volatile Edge adjacencyList;
    
    public Vertex(E element, Edge adjacencyList) {
            this.element = element;
            this.setAdjacencyList(adjacencyList);
    }

	public E getElement() {
		return element;
	}

	public Edge getAdjacencyList() {
		return adjacencyList;
	}

	public void setAdjacencyList(Edge adjacencyList) {
		this.adjacencyList = adjacencyList;
	}
}
