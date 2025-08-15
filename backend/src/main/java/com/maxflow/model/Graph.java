package com.maxflow.model;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private List<String> nodes;
    private List<Edge> edges;
    private String source;
    private String sink;

    public Graph() {
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    public Graph(List<String> nodes, List<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    public List<String> getNodes() { return nodes; }
    public void setNodes(List<String> nodes) { this.nodes = nodes; }

    public List<Edge> getEdges() { return edges; }
    public void setEdges(List<Edge> edges) { this.edges = edges; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getSink() { return sink; }
    public void setSink(String sink) { this.sink = sink; }

    // Helper for tests or programmatic building using names
    public void addEdge(String from, String to, int capacity) {
        if (!nodes.contains(from) || !nodes.contains(to)) {
            throw new IllegalArgumentException("Invalid node name: " + from + " or " + to);
        }
        Edge e = new Edge();
        e.setFrom(from);
        e.setTo(to);
        e.setCapacity(capacity);
        this.edges.add(e);
    }

    @Override
    public String toString() {
        return "Graph{" +
                "nodes=" + nodes +
                ", edges=" + edges +
                ", source='" + source + '\'' +
                ", sink='" + sink + '\'' +
                '}';
    }
}
