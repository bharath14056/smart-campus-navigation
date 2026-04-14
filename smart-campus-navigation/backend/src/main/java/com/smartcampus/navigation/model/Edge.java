package com.smartcampus.navigation.model;

public class Edge {
    private String fromNode;
    private String toNode;
    private int weight;
    private String direction;
    private String hint;

    public Edge() {
    }

    public Edge(String fromNode, String toNode, int weight, String direction, String hint) {
        this.fromNode = fromNode;
        this.toNode = toNode;
        this.weight = weight;
        this.direction = direction;
        this.hint = hint;
    }

    public String getFromNode() {
        return fromNode;
    }

    public void setFromNode(String fromNode) {
        this.fromNode = fromNode;
    }

    public String getToNode() {
        return toNode;
    }

    public void setToNode(String toNode) {
        this.toNode = toNode;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}
