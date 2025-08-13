package com.maxflow.model;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private int numVertices;
    private List<Edge> edges;

    public Graph(int numVertices) {
        this.numVertices = numVertices;
        this.edges = new ArrayList<>();
    }

    public void addEdge(int from, int to, int capacity) {
        edges.add(new Edge(from, to, capacity));
    }

    // Getters
    public int getNumVertices() { return numVertices; }
    public List<Edge> getEdges() { return edges; }
    }