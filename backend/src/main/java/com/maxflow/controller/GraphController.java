package com.maxflow.controller;

import com.maxflow.dto.GraphUploadResponse;
import com.maxflow.service.GraphService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/graphs")
public class GraphController {

    private final GraphService graphService;

    public GraphController(GraphService graphService) {
        this.graphService = graphService;
    }

    @PostMapping("/upload")
    public ResponseEntity<GraphUploadResponse> uploadGraph(@RequestParam("file") MultipartFile file) throws IOException {
        GraphUploadResponse response = graphService.processGraphUpload(file);
        return ResponseEntity.ok(response);
    }
}
