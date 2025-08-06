# MaxFlow – Network Flow Analyser

> A self-hosted “what-if” sandbox that computes maximum throughput between two nodes in any directed graph using the Edmonds–Karp algorithm.

---

## 🚀 Features

- **Upload & Visualise**  
  Paste or upload a JSON network graph; instantly see your topology rendered with Vis.js.  
- **Max-Flow Compute**  
  Spring Boot runs the Edmonds–Karp algorithm, returning total flow and saturated edges.  
- **Real-Time UI**  
  Next.js front end animates bottleneck edges in red and displays throughput in a sidebar.  
- **History & Metrics**  
  Persist all graphs and runs in PostgreSQL. Browse past scenarios, compare throughputs, and track algorithm runtimes.  
- **Sample Graphs**  
  Ready-to-use toy and real-world topologies included under `data/sample-graphs/`.

---

## 🏗️ Architecture

```text
      [ Next.js UI ]
             │
       POST /max-flow
             ▼
     [ Spring Boot API ]
       (Edmonds–Karp)
             │
    INSERT run + results
             ▼
       [ PostgreSQL ]
  (graphs, flow_runs, bottlenecks)
