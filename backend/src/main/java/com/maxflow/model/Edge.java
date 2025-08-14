package com.maxflow.model;

public class Edge {
    private String from;
    private String to;
    private int capacity;

    // Default constructor for JSON
    public Edge() {}

    public Edge(String from, String to, int capacity) {
        this.from = from;
        this.to = to;
        this.capacity = capacity;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", capacity=" + capacity +
                '}';
    }
}
