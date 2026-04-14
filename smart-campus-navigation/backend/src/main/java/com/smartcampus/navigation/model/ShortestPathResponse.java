package com.smartcampus.navigation.model;

import java.util.List;

public class ShortestPathResponse {
    private List<String> path;
    private List<String> directions;
    private int totalWeight;

    public ShortestPathResponse(List<String> path, List<String> directions, int totalWeight) {
        this.path = path;
        this.directions = directions;
        this.totalWeight = totalWeight;
    }

    public List<String> getPath() {
        return path;
    }

    public void setPath(List<String> path) {
        this.path = path;
    }

    public List<String> getDirections() {
        return directions;
    }

    public void setDirections(List<String> directions) {
        this.directions = directions;
    }

    public int getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(int totalWeight) {
        this.totalWeight = totalWeight;
    }
}
