package com.maxflow.controller;

import com.maxflow.dto.EdgeRequest;
import com.maxflow.dto.GraphRequest;
import com.maxflow.model.Edge;
import com.maxflow.model.Graph;
import com.maxflow.service.MaxFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class MaxFlowController {

    @Autowired
    private MaxFlowService maxFlowService;

    // POST /api/maxflow with human-friendly names; controller maps names -> indices
    @PostMapping("/maxflow")
    public Map<String, Object> calculateMaxFlow(@RequestBody GraphRequest req) {

        if (req.getNodes() == null || req.getEdges() == null || req.getSource() == null || req.getSink() == null) {
            return Map.of("error", "Payload must include nodes, edges, source, sink");
        }

        // Map source/sink names to indices
        int sourceIndex = req.getNodes().indexOf(req.getSource());
        int sinkIndex = req.getNodes().indexOf(req.getSink());
        if (sourceIndex == -1 || sinkIndex == -1) {
            return Map.of("error", "Source or sink not found in nodes list");
        }

        // Convert edges from names -> indices
        List<Edge> internalEdges = new ArrayList<>();
        for (EdgeRequest er : req.getEdges()) {
            int u = req.getNodes().indexOf(er.getFrom());
            int v = req.getNodes().indexOf(er.getTo());
            if (u == -1 || v == -1) {
                return Map.of("error", "Edge references unknown node: " + er.getFrom() + " -> " + er.getTo());
            }
            internalEdges.add(new Edge(u, v, er.getCapacity()));
        }

        // Build internal Graph (indices-based)
        Graph graph = new Graph();
        graph.setNodes(req.getNodes());     // keep names for reference
        graph.setEdges(internalEdges);      // indices inside Edge

        // Run Edmonds-Karp
        int maxFlow = maxFlowService.calculateMaxFlow(graph, sourceIndex, sinkIndex);

        return Map.of(
                "maxFlowValue", maxFlow,
                "sourceIndex", sourceIndex,
                "sinkIndex", sinkIndex
        );
    }
}
