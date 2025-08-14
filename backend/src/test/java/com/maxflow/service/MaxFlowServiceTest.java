package com.maxflow.service;

import com.maxflow.model.Graph;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MaxFlowServiceTest {

    @Test
    public void testMaxFlowSimple() {
        Graph graph = new Graph();
        graph.getNodes().add("A");
        graph.getNodes().add("B");
        graph.getNodes().add("C");
        graph.getNodes().add("D");

        graph.addEdge("A", "B", 10);
        graph.addEdge("A", "C", 5);
        graph.addEdge("B", "C", 15);
        graph.addEdge("B", "D", 10);
        graph.addEdge("C", "D", 10);

        MaxFlowService maxFlowService = new MaxFlowService();
        int maxFlow = maxFlowService.calculateMaxFlow(graph, 0, 3);

        assertEquals(15, maxFlow, "Max flow should be 15");
    }
}
