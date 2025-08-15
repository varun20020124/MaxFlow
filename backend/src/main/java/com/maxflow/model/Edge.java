package com.maxflow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Edge {
    // Name-based endpoints from JSON
    private String from;
    private String to;
    private int capacity;

    // Internal index endpoints (not exposed in JSON)
    @JsonIgnore
    private Integer fromIndex;
    @JsonIgnore
    private Integer toIndex;

    public Edge() {
    }

    // Helper for building index-based edges internally
    public Edge(int fromIndex, int toIndex, int capacity) {
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
        this.capacity = capacity;
    }

    // --- JSON (name-based) getters/setters ---
    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }

    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    // --- Internal index getters/setters ---
    public Integer getFromIndex() { return fromIndex; }
    public void setFromIndex(Integer fromIndex) { this.fromIndex = fromIndex; }

    public Integer getToIndex() { return toIndex; }
    public void setToIndex(Integer toIndex) { this.toIndex = toIndex; }

    @Override
    public String toString() {
        return "Edge{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", capacity=" + capacity +
                ", fromIndex=" + fromIndex +
                ", toIndex=" + toIndex +
                '}';
    }
}
