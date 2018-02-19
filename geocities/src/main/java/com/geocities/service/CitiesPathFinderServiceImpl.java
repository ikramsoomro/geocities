package com.geocities.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.geocities.util.graph.Graph;
import com.geocities.util.graph.Pair;

/**
 * 
 * @author Ikram Soomro
 *
 */
@Component
public class CitiesPathFinderServiceImpl implements ICitiesPathFinderService {

	private volatile Graph graph;

	@PostConstruct
	public void init() throws IOException {
		Set<String> cities = new HashSet<>();
		Set<Pair<String>> directCities = new HashSet<>();

		Resource resource = new ClassPathResource("/cities.txt");
		InputStream resourceAsStream = resource.getInputStream();
		Scanner scanner = new Scanner(resourceAsStream);

		while (scanner.hasNext()) {
			String line = scanner.nextLine();
			String[] split = line.split(",");
			Pair<String> directlyConnectedCities = new Pair<>(split[0].trim(), split[1].trim());
			directCities.add(directlyConnectedCities);
			cities.add(directlyConnectedCities.getLeft());
			cities.add(directlyConnectedCities.getRight());
		}
		scanner.close();
		graph = new Graph(new ArrayList<>(cities), directCities);
	}

	@Override
	public boolean isPathPresent(String origin, String destination) {
		return graph.isPathPresent(origin, destination);
	}
}
