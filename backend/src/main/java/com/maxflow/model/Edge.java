package com.maxflow.model;
public class Edge{
    private int from; // start node
    private int to; // end node
    private int capacity; // max capacity of this edge
    private int flow; // current flow on this edge

    public Edge(int from, int to, int capacity){
        this.from = from;
        this.to = to;
        this.capacity = capacity;
        this.flow = 0; // intially no flow
    }

    // Getters & Setters
    public int getFrom() { return from; }
    public int getTo() { return to; }
    public int getCapacity() { return capacity; }
    public int getFlow() { return flow; }

    public void setFlow(int flow) { this.flow = flow; }
}