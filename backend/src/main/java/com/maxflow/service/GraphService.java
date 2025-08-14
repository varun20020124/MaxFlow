package com.maxflow.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxflow.dto.GraphUploadResponse;
import com.maxflow.model.Edge;
import com.maxflow.model.Graph;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class GraphService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public GraphUploadResponse processGraphUpload(MultipartFile file) throws IOException {
        // 1. Read JSON from file
        Graph graph = objectMapper.readValue(file.getInputStream(), Graph.class);

        // 2. (For now) Just log graph data
        System.out.println("Parsed Graph: " + graph);

        // 3. Count edges and nodes for metrics
        int nodeCount = graph.getNodes().size();
        int edgeCount = graph.getEdges().size();

        // 4. Create response DTO
        GraphUploadResponse response = new GraphUploadResponse();
        response.setMessage("Graph uploaded and parsed successfully!");
        response.setNodeCount(nodeCount);
        response.setEdgeCount(edgeCount);

        return response;
    }
}
