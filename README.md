# MaxFlow – Network Flow Analyser

> A self-hosted “what-if” sandbox that computes maximum throughput between two nodes in any directed graph using the Edmonds–Karp algorithm.

---

## Description

MaxFlow lets network engineers and architects model any directed network, run a battle-tested max-flow algorithm, and instantly see both the total achievable throughput and the exact links that become bottlenecks. All graphs and run statistics are stored for historical comparison.

---

## Features

- **JSON Graph Ingestion**  
  Upload or paste any directed graph (nodes, edges with capacities, source & sink).
- **Edmonds–Karp Max-Flow**  
  Computes the exact maximum flow in O(V E²) time, guaranteeing the globally optimal throughput.
- **Real-Time Visualization**  
  Next.js + Vis.js front end highlights saturated (bottleneck) edges in red and animates flow values.
- **History & Metrics**  
  Persists every run in PostgreSQL. Browse past scenarios, compare throughput, and track algorithm runtime.
- **Sample Topologies Included**  
  Ready-to-use toy and real-world ISP backbone graphs under `data/sample-graphs/`.

---

## Architecture

### High-Level Data Flow

┌──────────────┐   POST /max-flow   ┌──────────────┐
│  React App   │ ─────────────────▶ │ Spring Boot  │
│  (Vis.js UI) │                   │  API         │
└───┬──────────┘                   └───┬──────────┘
    │    SSE stream (status)            │
    ▼                                   ▼
┌──────────────┐                 ┌──────────────┐
│  WebSocket   │ optional live   │ Edmonds-Karp │
│  progress    │ progress msgs   │  Solver      │
└──────────────┘                 └──────────────┘
         ▲                               │
         │   INSERT run + results        ▼
         │                         ┌──────────────┐
         └────────────────────────▶│ PostgreSQL   │
                                   │ (graphs &   │
                                   │  run_stats) │
                                   └──────────────┘


### Component Breakdown

- **Front-end (Next.js + Vis.js)**  
  - **GraphEditor**: Drag/drop nodes, set edge capacities, define source & sink.  
  - **FlowViewer**: Animates saturated edges and displays total flow in sidebar.  
  - **HistoryPage**: SSR-rendered list of past runs with filters and comparison links.

- **Back-end (Spring Boot)**  
  - **FlowController** (`/max-flow`, `/graphs`, `/runs`): Handles JSON input, triggers solver, returns structured results.  
  - **EdmondsKarpService**: Pure-Java implementation of Edmonds–Karp on in-memory graph objects.  
  - **Repositories**: JPA/Hibernate entities for `Graph`, `Edge`, `FlowRun`, and `Bottleneck` models.

- **Database (PostgreSQL)**  
  - **graphs**: Stores raw JSON, metadata (name, uploaded_at).  
  - **flow_runs**: References `graph_id`, stores `max_flow`, `runtime_ms`, `timestamp`.  
  - **bottlenecks**: Lists `edge_id` for each saturated edge per run.

- **Deployment**  
  - **Docker & docker-compose**: One-command launch for Spring Boot, PostgreSQL, and Next.js.

---

## Edmonds–Karp Algorithm

The Edmonds–Karp algorithm is a specialization of the Ford–Fulkerson method that uses **BFS** to find the shortest augmenting path (in terms of edge count) on each iteration.  

1. **Initialize** all flows to 0.  
2. **While** a path exists from source to sink in the **residual graph** (edges with remaining capacity):  
   - Run **BFS** to find the shortest augmenting path.  
   - Determine the **bottleneck capacity** (minimum residual capacity along that path).  
   - **Augment** the flow along the path by that capacity (increase flow on forward edges, decrease on reverse edges).  
3. **Repeat** until no augmenting path remains.  

This guarantees termination in **O(V E²)** time and produces the **maximum possible flow**. Key data structures:  
- **Residual graph**: tracks remaining capacities and allows “undo” via reverse edges.  
- **Queue** for BFS to ensure shortest-path discovery.  

---

## Dependencies

- **Java** 17+ & **Maven** (or Gradle)  
- **Spring Boot** 2.7.x (Web, Data JPA)  
- **Node.js** 18+ & **npm** or **Yarn**  
- **Next.js** 13.x & **React** 18.x  
- **Vis.js** 4.x for network rendering  
- **PostgreSQL** 13+  
- **Docker** & **docker-compose** (v1.29+)
