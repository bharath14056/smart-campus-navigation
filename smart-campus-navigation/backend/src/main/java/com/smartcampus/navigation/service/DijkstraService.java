package com.smartcampus.navigation.service;

import com.smartcampus.navigation.model.Edge;
import com.smartcampus.navigation.model.ShortestPathResponse;
import com.smartcampus.navigation.repository.EdgeRepository;
import com.smartcampus.navigation.repository.NodeRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class DijkstraService {
    private final EdgeRepository edgeRepository;
    private final NodeRepository nodeRepository;

    public DijkstraService(EdgeRepository edgeRepository, NodeRepository nodeRepository) {
        this.edgeRepository = edgeRepository;
        this.nodeRepository = nodeRepository;
    }

    public Map<String, List<Edge>> buildGraphFromDatabase() {
        Map<String, List<Edge>> graph = new HashMap<>();
        for (Edge edge : edgeRepository.findAll()) {
            graph.computeIfAbsent(edge.getFromNode(), key -> new ArrayList<>()).add(edge);
            graph.computeIfAbsent(edge.getToNode(), key -> new ArrayList<>());
        }
        return graph;
    }

    public ShortestPathResponse calculateShortestPath(String start, String end) {
        validateNodes(start, end);

        if (start.equals(end)) {
            return new ShortestPathResponse(List.of(start), List.of("You are already at the destination."), 0);
        }

        Map<String, List<Edge>> graph = buildGraphFromDatabase();
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previousNode = new HashMap<>();
        Map<String, Edge> previousEdge = new HashMap<>();
        Set<String> visited = new HashSet<>();
        PriorityQueue<NodeDistance> queue = new PriorityQueue<>(Comparator.comparingInt(NodeDistance::distance));

        for (String nodeId : graph.keySet()) {
            distances.put(nodeId, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        queue.offer(new NodeDistance(start, 0));

        while (!queue.isEmpty()) {
            NodeDistance current = queue.poll();
            if (!visited.add(current.nodeId())) {
                continue;
            }
            if (current.nodeId().equals(end)) {
                break;
            }

            for (Edge edge : graph.getOrDefault(current.nodeId(), List.of())) {
                if (visited.contains(edge.getToNode())) {
                    continue;
                }

                int newDistance = distances.get(current.nodeId()) + edge.getWeight();
                if (newDistance < distances.getOrDefault(edge.getToNode(), Integer.MAX_VALUE)) {
                    distances.put(edge.getToNode(), newDistance);
                    previousNode.put(edge.getToNode(), current.nodeId());
                    previousEdge.put(edge.getToNode(), edge);
                    queue.offer(new NodeDistance(edge.getToNode(), newDistance));
                }
            }
        }

        if (!previousNode.containsKey(end)) {
            throw new IllegalArgumentException("No path found between " + start + " and " + end + ".");
        }

        List<String> path = new ArrayList<>();
        List<String> directions = new ArrayList<>();
        String currentNode = end;

        path.add(currentNode);
        while (previousNode.containsKey(currentNode)) {
            Edge edge = previousEdge.get(currentNode);
            directions.add(formatDirection(edge));
            currentNode = previousNode.get(currentNode);
            path.add(currentNode);
        }

        Collections.reverse(path);
        Collections.reverse(directions);

        return new ShortestPathResponse(path, directions, distances.get(end));
    }

    private void validateNodes(String start, String end) {
        if (start == null || start.isBlank() || end == null || end.isBlank()) {
            throw new IllegalArgumentException("Start and end locations are required.");
        }
        if (!nodeRepository.existsById(start) || !nodeRepository.existsById(end)) {
            throw new IllegalArgumentException("One or both locations do not exist in the campus map.");
        }
    }

    private String formatDirection(Edge edge) {
        if (edge.getHint() == null || edge.getHint().isBlank()) {
            return edge.getDirection();
        }
        return edge.getDirection() + " (" + edge.getHint() + ")";
    }

    private record NodeDistance(String nodeId, int distance) {
    }
}
