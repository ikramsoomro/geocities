package com.geocities.util.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;


public class GraphTest {

	@Test
	public void testUsingDFS() {

		Set<Pair<String>> pairs = new HashSet<>();
		pairs.add(new Pair<String>("Boston","New York"));
		pairs.add(new Pair<String>("Philadelphia","Newark"));
		pairs.add(new Pair<String>("Newark","Boston"));
		pairs.add(new Pair<String>("Trenton","Albany"));

		Set<String> uniqueElements = new HashSet<>();
		pairs.stream().forEach(pair -> { uniqueElements.add(pair.getLeft()); uniqueElements.add(pair.getRight()); });
		Graph<String> graph = new Graph<String>(new ArrayList<>(uniqueElements), pairs, GraphSearchAlgorithm.DEPTH_FIRST_SEARCH);
		
		Assert.assertTrue(graph.isPathPresent("Boston", "Newark"));
		Assert.assertTrue(graph.isPathPresent("Newark", "Boston"));

		Assert.assertTrue(graph.isPathPresent("Boston", "Philadelphia"));
		Assert.assertTrue(graph.isPathPresent("Philadelphia", "Boston"));
		
		Assert.assertTrue(graph.isPathPresent("New York", "Newark"));
		Assert.assertTrue(graph.isPathPresent("Newark", "New York"));

		Assert.assertTrue(graph.isPathPresent("Boston", "New York"));
		Assert.assertTrue(graph.isPathPresent("New York", "Boston"));

		Assert.assertTrue(graph.isPathPresent("Newark", "Philadelphia"));
		Assert.assertTrue(graph.isPathPresent("Philadelphia", "Newark"));

		Assert.assertFalse(graph.isPathPresent("Philadelphia", "Albany"));
		Assert.assertFalse(graph.isPathPresent("Albany", "Philadelphia"));		
	}
	
	@Test
	public void testUsingBFS() {

		Set<Pair<String>> pairs = new HashSet<>();
		pairs.add(new Pair<String>("Boston","New York"));
		pairs.add(new Pair<String>("Philadelphia","Newark"));
		pairs.add(new Pair<String>("Newark","Boston"));
		pairs.add(new Pair<String>("Trenton","Albany"));

		Set<String> uniqueElements = new HashSet<>();
		pairs.stream().forEach(pair -> { uniqueElements.add(pair.getLeft()); uniqueElements.add(pair.getRight()); });
		Graph<String> graph = new Graph<String>(new ArrayList<>(uniqueElements), pairs, GraphSearchAlgorithm.BREADTH_FIRST_SEARCH);
		
		Assert.assertTrue(graph.isPathPresent("Boston", "Newark"));
		Assert.assertTrue(graph.isPathPresent("Newark", "Boston"));

		Assert.assertTrue(graph.isPathPresent("Boston", "Philadelphia"));
		Assert.assertTrue(graph.isPathPresent("Philadelphia", "Boston"));
		
		Assert.assertTrue(graph.isPathPresent("New York", "Newark"));
		Assert.assertTrue(graph.isPathPresent("Newark", "New York"));

		Assert.assertTrue(graph.isPathPresent("Boston", "New York"));
		Assert.assertTrue(graph.isPathPresent("New York", "Boston"));

		Assert.assertTrue(graph.isPathPresent("Newark", "Philadelphia"));
		Assert.assertTrue(graph.isPathPresent("Philadelphia", "Newark"));

		Assert.assertFalse(graph.isPathPresent("Philadelphia", "Albany"));
		Assert.assertFalse(graph.isPathPresent("Albany", "Philadelphia"));		
	}

	@Test
	public void testUsingDFSLazySearch() {

		Set<Pair<String>> pairs = new HashSet<>();
		pairs.add(new Pair<String>("Boston","New York"));
		pairs.add(new Pair<String>("Philadelphia","Newark"));
		pairs.add(new Pair<String>("Newark","Boston"));
		pairs.add(new Pair<String>("Trenton","Albany"));

		Set<String> uniqueElements = new HashSet<>();
		pairs.stream().forEach(pair -> { uniqueElements.add(pair.getLeft()); uniqueElements.add(pair.getRight()); });
		Graph<String> graph = new Graph<String>(new ArrayList<>(uniqueElements), pairs, GraphSearchAlgorithm.DEPTH_FIRST_SEARCH, true);
		
		Assert.assertTrue(graph.isPathPresent("Boston", "Newark"));
		Assert.assertTrue(graph.isPathPresent("Newark", "Boston"));

		Assert.assertTrue(graph.isPathPresent("Boston", "Philadelphia"));
		Assert.assertTrue(graph.isPathPresent("Philadelphia", "Boston"));
		
		Assert.assertTrue(graph.isPathPresent("New York", "Newark"));
		Assert.assertTrue(graph.isPathPresent("Newark", "New York"));

		Assert.assertTrue(graph.isPathPresent("Boston", "New York"));
		Assert.assertTrue(graph.isPathPresent("New York", "Boston"));

		Assert.assertTrue(graph.isPathPresent("Newark", "Philadelphia"));
		Assert.assertTrue(graph.isPathPresent("Philadelphia", "Newark"));

		Assert.assertFalse(graph.isPathPresent("Philadelphia", "Albany"));
		Assert.assertFalse(graph.isPathPresent("Albany", "Philadelphia"));		
	}
	
	@Test
	public void testUsingBFSLazySearch() {

		Set<Pair<String>> pairs = new HashSet<>();
		pairs.add(new Pair<String>("Boston","New York"));
		pairs.add(new Pair<String>("Philadelphia","Newark"));
		pairs.add(new Pair<String>("Newark","Boston"));
		pairs.add(new Pair<String>("Trenton","Albany"));

		Set<String> uniqueElements = new HashSet<>();
		pairs.stream().forEach(pair -> { uniqueElements.add(pair.getLeft()); uniqueElements.add(pair.getRight()); });
		Graph<String> graph = new Graph<String>(new ArrayList<>(uniqueElements), pairs, GraphSearchAlgorithm.BREADTH_FIRST_SEARCH, true);
		
		Assert.assertTrue(graph.isPathPresent("Boston", "Newark"));
		Assert.assertTrue(graph.isPathPresent("Newark", "Boston"));

		Assert.assertTrue(graph.isPathPresent("Boston", "Philadelphia"));
		Assert.assertTrue(graph.isPathPresent("Philadelphia", "Boston"));
		
		Assert.assertTrue(graph.isPathPresent("New York", "Newark"));
		Assert.assertTrue(graph.isPathPresent("Newark", "New York"));

		Assert.assertTrue(graph.isPathPresent("Boston", "New York"));
		Assert.assertTrue(graph.isPathPresent("New York", "Boston"));

		Assert.assertTrue(graph.isPathPresent("Newark", "Philadelphia"));
		Assert.assertTrue(graph.isPathPresent("Philadelphia", "Newark"));

		Assert.assertFalse(graph.isPathPresent("Philadelphia", "Albany"));
		Assert.assertFalse(graph.isPathPresent("Albany", "Philadelphia"));		
	}	
}
