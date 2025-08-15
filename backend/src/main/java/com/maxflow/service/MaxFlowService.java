package com.maxflow.service;

import com.maxflow.model.Edge;
import com.maxflow.model.Graph;
import org.springframework.stereotype.Service;
import com.maxflow.dto.AugmentationStep;
import java.util.*;

@Service
public class MaxFlowService {

    public static class MaxFlowResult {
        private final int maxFlowValue;
        private final int sourceIndex;
        private final int sinkIndex;

        public MaxFlowResult(int maxFlowValue, int sourceIndex, int sinkIndex) {
            this.maxFlowValue = maxFlowValue;
            this.sourceIndex = sourceIndex;
            this.sinkIndex = sinkIndex;
        }

        public int getMaxFlowValue() { return maxFlowValue; }
        public int getSourceIndex() { return sourceIndex; }
        public int getSinkIndex() { return sinkIndex; }
    }

    /** Accepts a Graph with names; converts to indices internally and runs Edmonds–Karp. */
    public MaxFlowResult calculateMaxFlow(Graph graph) {
        int sourceIndex = graph.getNodes().indexOf(graph.getSource());
        int sinkIndex = graph.getNodes().indexOf(graph.getSink());
        if (sourceIndex == -1 || sinkIndex == -1) {
            throw new IllegalArgumentException("Source or sink not found in node list");
        }

        int n = graph.getNodes().size();

        // Build capacity matrix, accepting either name-based or index-based edges
        int[][] capacity = new int[n][n];
        for (Edge e : graph.getEdges()) {
            int u = (e.getFrom() != null)
                    ? graph.getNodes().indexOf(e.getFrom())
                    : (e.getFromIndex() != null ? e.getFromIndex() : -1);
            int v = (e.getTo() != null)
                    ? graph.getNodes().indexOf(e.getTo())
                    : (e.getToIndex() != null ? e.getToIndex() : -1);

            if (u < 0 || v < 0) {
                throw new IllegalArgumentException("Invalid edge endpoints: " + e);
            }

            capacity[u][v] += e.getCapacity();
        }

        // Residual graph
        int[][] residual = new int[n][n];
        for (int i = 0; i < n; i++) {
            residual[i] = Arrays.copyOf(capacity[i], n);
        }

        int maxFlow = 0;
        int[] parent = new int[n];

        while (bfs(residual, sourceIndex, sinkIndex, parent)) {
            int pathFlow = Integer.MAX_VALUE;

            int v = sinkIndex;
            while (v != sourceIndex) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, residual[u][v]);
                v = u;
            }

            v = sinkIndex;
            while (v != sourceIndex) {
                int u = parent[v];
                residual[u][v] -= pathFlow;
                residual[v][u] += pathFlow;
                v = u;
            }

            maxFlow += pathFlow;
        }

        return new MaxFlowResult(maxFlow, sourceIndex, sinkIndex);
    }

    /** New method that returns step-by-step augmentation details */
    public List<AugmentationStep> calculateMaxFlowWithDetails(Graph graph) {
        int sourceIndex = graph.getNodes().indexOf(graph.getSource());
        int sinkIndex = graph.getNodes().indexOf(graph.getSink());
        if (sourceIndex == -1 || sinkIndex == -1) {
            throw new IllegalArgumentException("Source or sink not found in node list");
        }

        int n = graph.getNodes().size();

        int[][] capacity = new int[n][n];
        for (Edge e : graph.getEdges()) {
            int u = (e.getFrom() != null)
                    ? graph.getNodes().indexOf(e.getFrom())
                    : (e.getFromIndex() != null ? e.getFromIndex() : -1);
            int v = (e.getTo() != null)
                    ? graph.getNodes().indexOf(e.getTo())
                    : (e.getToIndex() != null ? e.getToIndex() : -1);

            if (u < 0 || v < 0) {
                throw new IllegalArgumentException("Invalid edge endpoints: " + e);
            }
            capacity[u][v] += e.getCapacity();
        }

        int[][] residual = new int[n][n];
        for (int i = 0; i < n; i++) {
            residual[i] = Arrays.copyOf(capacity[i], n);
        }

        int[] parent = new int[n];
        List<AugmentationStep> steps = new ArrayList<>();

        while (bfs(residual, sourceIndex, sinkIndex, parent)) {
            List<Integer> path = new ArrayList<>();
            int bottleneck = Integer.MAX_VALUE;
            int v = sinkIndex;

            while (v != sourceIndex) {
                int u = parent[v];
                bottleneck = Math.min(bottleneck, residual[u][v]);
                path.add(0, v);
                v = u;
            }
            path.add(0, sourceIndex);

            v = sinkIndex;
            while (v != sourceIndex) {
                int u = parent[v];
                residual[u][v] -= bottleneck;
                residual[v][u] += bottleneck;
                v = u;
            }

            int[][] snapshot = new int[n][n];
            for (int i = 0; i < n; i++) {
                snapshot[i] = Arrays.copyOf(residual[i], n);
            }

            steps.add(new AugmentationStep(path, bottleneck, snapshot));
        }

        return steps;
    }

    private boolean bfs(int[][] residual, int source, int sink, int[] parent) {
        Arrays.fill(parent, -1);
        parent[source] = -2;

        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{source, Integer.MAX_VALUE});

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int u = cur[0];
            int flow = cur[1];

            for (int v = 0; v < residual.length; v++) {
                if (parent[v] == -1 && residual[u][v] > 0) {
                    parent[v] = u;
                    int newFlow = Math.min(flow, residual[u][v]);
                    if (v == sink) return true;
                    q.offer(new int[]{v, newFlow});
                }
            }
        }
        return false;
    }
}
