package com.maxflow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxflow.model.Edge;
import com.maxflow.model.Graph;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MaxFlowControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Graph createSampleGraph() {
        Graph graph = new Graph();
        graph.setNodes(Arrays.asList("A", "B", "C", "D"));
        graph.setSource("A");
        graph.setSink("D");

        Edge e1 = new Edge(); e1.setFrom("A"); e1.setTo("B"); e1.setCapacity(10);
        Edge e2 = new Edge(); e2.setFrom("A"); e2.setTo("C"); e2.setCapacity(5);
        Edge e3 = new Edge(); e3.setFrom("B"); e3.setTo("C"); e3.setCapacity(15);
        Edge e4 = new Edge(); e4.setFrom("B"); e4.setTo("D"); e4.setCapacity(10);
        Edge e5 = new Edge(); e5.setFrom("C"); e5.setTo("D"); e5.setCapacity(10);

        graph.setEdges(Arrays.asList(e1, e2, e3, e4, e5));
        return graph;
    }

    @Test
    public void testMaxFlowDetailsEndpoint() throws Exception {
        Graph graph = createSampleGraph();

        mockMvc.perform(post("/api/maxflow/details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(graph)))
                .andDo(print()) // <-- prints full request/response to console
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalAugmentations", is(2)))
                .andExpect(jsonPath("$.steps", hasSize(2)))
                .andExpect(jsonPath("$.steps[0].path", contains(0, 1, 3)))
                .andExpect(jsonPath("$.steps[0].bottleneck", is(10)))
                .andExpect(jsonPath("$.steps[1].path", contains(0, 2, 3)))
                .andExpect(jsonPath("$.steps[1].bottleneck", is(5)));
    }

    @Test
    public void testMaxFlowEndpoint() throws Exception {
        Graph graph = createSampleGraph();

        mockMvc.perform(post("/api/maxflow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(graph)))
                .andDo(print()) // <-- prints full request/response to console
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maxFlowValue", is(15)))
                .andExpect(jsonPath("$.sourceIndex", is(0)))
                .andExpect(jsonPath("$.sinkIndex", is(3)));
    }
}
