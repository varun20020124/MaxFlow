package com.maxflow.controller;

import com.maxflow.model.Graph;
import com.maxflow.service.MaxFlowService;
import com.maxflow.dto.AugmentationStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MaxFlowController {

    @Autowired
    private MaxFlowService maxFlowService;

    // Existing basic max flow endpoint
    @PostMapping("/maxflow")
    public Map<String, Object> calculateMaxFlow(@RequestBody Graph graph) {
        MaxFlowService.MaxFlowResult result = maxFlowService.calculateMaxFlow(graph);
        return Map.of(
                "maxFlowValue", result.getMaxFlowValue(),
                "sourceIndex", result.getSourceIndex(),
                "sinkIndex", result.getSinkIndex()
        );
    }

    // New detailed max flow endpoint
    @PostMapping("/maxflow/details")
    public Map<String, Object> calculateMaxFlowWithDetails(@RequestBody Graph graph) {
        List<AugmentationStep> steps = maxFlowService.calculateMaxFlowWithDetails(graph);
        return Map.of(
                "steps", steps,
                "totalAugmentations", steps.size()
        );
    }
}
