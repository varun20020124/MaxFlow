package com.maxflow.dto;

public class GraphUploadResponse {
    private String message;
    private int nodeCount;
    private int edgeCount;

    public GraphUploadResponse() {
    }

    public GraphUploadResponse(String message) {
        this.message = message;
    }

    public GraphUploadResponse(String message, int nodeCount, int edgeCount) {
        this.message = message;
        this.nodeCount = nodeCount;
        this.edgeCount = edgeCount;
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public int getNodeCount() { return nodeCount; }
    public void setNodeCount(int nodeCount) { this.nodeCount = nodeCount; }

    public int getEdgeCount() { return edgeCount; }
    public void setEdgeCount(int edgeCount) { this.edgeCount = edgeCount; }
}
