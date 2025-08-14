package com.maxflow.service;

import com.maxflow.model.Edge;
import com.maxflow.model.Graph;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MaxFlowService {

    /**
     * Calculates the maximum flow between a source and sink in the graph
     * using the Edmonds-Karp (BFS-based Ford-Fulkerson) algorithm.
     *
     * @param graph   The input graph containing nodes and edges
     * @param source  The source vertex index
     * @param sink    The sink vertex index
     * @return The maximum flow value
     */
    public int calculateMaxFlow(Graph graph, int source, int sink) {
        // int numVertices = graph.getNumVertices();
        int numVertices = graph.getNodes().size();


        // Step 1: Build capacity matrix
        int[][] capacity = new int[numVertices][numVertices];
        for (Edge e : graph.getEdges()) {
            capacity[e.getFrom()][e.getTo()] = e.getCapacity();
        }

        // Step 2: Initialize residual capacity
        int[][] residualCapacity = new int[numVertices][numVertices];
        for (int i = 0; i < numVertices; i++) {
            residualCapacity[i] = Arrays.copyOf(capacity[i], numVertices);
        }

        int maxFlow = 0;
        int[] parent = new int[numVertices];

        // Step 3: Augment flows while there is a path from source to sink
        while (bfs(residualCapacity, source, sink, parent)) {
            // Find bottleneck (minimum capacity along the path)
            int pathFlow = Integer.MAX_VALUE;
            int v = sink;
            while (v != source) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, residualCapacity[u][v]);
                v = u;
            }

            // Update residual capacities
            v = sink;
            while (v != source) {
                int u = parent[v];
                residualCapacity[u][v] -= pathFlow;
                residualCapacity[v][u] += pathFlow;
                v = u;
            }

            // Add path flow to overall flow
            maxFlow += pathFlow;
        }

        return maxFlow;
    }

    /**
     * BFS to check if there is a path from source to sink in the residual graph
     * and stores the path in parent[].
     */
    private boolean bfs(int[][] residualCapacity, int source, int sink, int[] parent) {
        Arrays.fill(parent, -1);
        parent[source] = -2; // Mark source as visited

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{source, Integer.MAX_VALUE});

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int u = current[0];
            int flow = current[1];

            for (int v = 0; v < residualCapacity.length; v++) {
                if (parent[v] == -1 && residualCapacity[u][v] > 0) {
                    parent[v] = u;
                    int newFlow = Math.min(flow, residualCapacity[u][v]);
                    if (v == sink) {
                        return true; // Found path
                    }
                    queue.offer(new int[]{v, newFlow});
                }
            }
        }
        return false;
    }
}
