package com.smartcampus.navigation.model;

public class Node {
    private String id;
    private String name;
    private String floor;
    private String type;

    public Node() {
    }

    public Node(String id, String name, String floor, String type) {
        this.id = id;
        this.name = name;
        this.floor = floor;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
