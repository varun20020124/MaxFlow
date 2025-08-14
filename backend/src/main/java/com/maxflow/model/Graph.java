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

    public List<String> getNodes() {
        return nodes;
    }

    public void setNodes(List<String> nodes) {
        this.nodes = nodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSink() {
        return sink;
    }

    public void setSink(String sink) {
        this.sink = sink;
    }

    public void addEdge(String from, String to, int capacity) {
        int fromIndex = nodes.indexOf(from);
        int toIndex = nodes.indexOf(to);
        if (fromIndex == -1 || toIndex == -1) {
            throw new IllegalArgumentException("Invalid node name: " + from + " or " + to);
        }
        this.edges.add(new Edge(fromIndex, toIndex, capacity));
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
