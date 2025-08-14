package com.maxflow.dto;

import java.util.List;

public class GraphRequest {
    private List<String> nodes;
    private List<EdgeRequest> edges;
    private String source;
    private String sink;

    public GraphRequest() {}

    public List<String> getNodes() { return nodes; }
    public void setNodes(List<String> nodes) { this.nodes = nodes; }

    public List<EdgeRequest> getEdges() { return edges; }
    public void setEdges(List<EdgeRequest> edges) { this.edges = edges; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getSink() { return sink; }
    public void setSink(String sink) { this.sink = sink; }
}
