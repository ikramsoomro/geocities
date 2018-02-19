package com.geocities.util.graph;

public final class Vertex {
	
    private final String name;
    private Edge adjacencyList;
    
    Vertex(String name, Edge adjacencyList) {
            this.name = name;
            this.setAdjacencyList(adjacencyList);
    }

	public String getName() {
		return name;
	}

	public Edge getAdjacencyList() {
		return adjacencyList;
	}

	public void setAdjacencyList(Edge adjacencyList) {
		this.adjacencyList = adjacencyList;
	}
}
