package com.maxflow.dto;

import java.util.List;

public class AugmentationStep {
    private List<Integer> path; // indices of nodes in augmenting path
    private int bottleneck;
    private int[][] residualCapacity; // snapshot after this step

    public AugmentationStep(List<Integer> path, int bottleneck, int[][] residualCapacity) {
        this.path = path;
        this.bottleneck = bottleneck;
        this.residualCapacity = residualCapacity;
    }

    public List<Integer> getPath() {
        return path;
    }

    public int getBottleneck() {
        return bottleneck;
    }

    public int[][] getResidualCapacity() {
        return residualCapacity;
    }
}
