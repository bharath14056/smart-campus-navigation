package com.smartcampus.navigation.controller;

import com.smartcampus.navigation.model.Node;
import com.smartcampus.navigation.model.ShortestPathRequest;
import com.smartcampus.navigation.model.ShortestPathResponse;
import com.smartcampus.navigation.repository.NodeRepository;
import com.smartcampus.navigation.service.DijkstraService;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class NavigationController {
    private final NodeRepository nodeRepository;
    private final DijkstraService dijkstraService;

    public NavigationController(NodeRepository nodeRepository, DijkstraService dijkstraService) {
        this.nodeRepository = nodeRepository;
        this.dijkstraService = dijkstraService;
    }

    @GetMapping("/nodes")
    public List<Node> getNodes() {
        return nodeRepository.findAll();
    }

    @PostMapping("/shortest-path")
    public ShortestPathResponse findShortestPath(@RequestBody ShortestPathRequest request) {
        return dijkstraService.calculateShortestPath(request.getStart(), request.getEnd());
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", exception.getMessage()));
    }
}
