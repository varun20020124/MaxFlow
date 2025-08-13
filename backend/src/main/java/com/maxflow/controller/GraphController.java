package com.maxflow.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/api/graphs")
public class GraphController {

    @PostMapping("/upload")
    public ResponseEntity<String> uploadGraph(@RequestParam("file") MultipartFile file) throws IOException {
        String content = new String(file.getBytes());
        System.out.println("Uploaded Graph JSON: " + content);
        return ResponseEntity.ok("Graph uploaded successfully!");
    }
}
