import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DijkstrasAlgorithm {

    public static void main(String[] args) {
        shortestPathFromSource(exampleGraph(), "A");
    }

    private static class Graph {
        public Map<String, Vertex> vertices;

        public Graph(Map<String, Vertex> vertices) {
            this.vertices = vertices;
        }
    }

    private static class Vertex {
        public String id;
        public List<Edge> edges;

        public Vertex(String id, List<Edge> edges) {
            this.id = id;
            this.edges = edges;
        }
    }

    private static class Edge {
        public int weight;
        public String from;
        public String to;

        public Edge(int weight, String from, String to) {
            this.weight = weight;
            this.from = from;
            this.to = to;
        }
    }

    private static Graph exampleGraph() {
        final List<Vertex> vertices = new ArrayList<>();
        vertices.add(
                new Vertex(
                        "A",
                        List.of(
                                new Edge(2, "A", "B"),
                                new Edge(8, "A", "D")
                        )
                )
        );
        vertices.add(
                new Vertex(
                        "B",
                        List.of(
                                new Edge(2, "B", "A"),
                                new Edge(6, "B", "E"),
                                new Edge(5, "B", "D")
                        )
                )
        );
        vertices.add(
                new Vertex(
                        "C",
                        List.of(
                                new Edge(3, "C", "F"),
                                new Edge(9, "C", "E")
                        )
                )
        );
        vertices.add(
                new Vertex(
                        "D",
                        List.of(
                                new Edge(8, "D", "A"),
                                new Edge(5, "D", "B"),
                                new Edge(3, "D", "E"),
                                new Edge(2, "D", "F")
                        )
                )
        );
        vertices.add(
                new Vertex(
                        "E",
                        List.of(
                                new Edge(6, "E", "B"),
                                new Edge(3, "E", "D"),
                                new Edge(1, "E", "F"),
                                new Edge(9, "E", "C")
                        )
                )
        );
        vertices.add(
                new Vertex(
                        "F",
                        List.of(
                                new Edge(2, "F", "D"),
                                new Edge(1, "F", "E"),
                                new Edge(3, "F", "C")
                        )
                )
        );

        Map<String, Vertex> vertexMap = vertices.stream().collect(Collectors.toMap((a) -> a.id, Function.identity()));

        return new Graph(vertexMap);
    }

    private static void shortestPathFromSource(Graph graph, String source) {

        class Path {
            public String destinationVertexId;
            public String sourceVertexId;
            public int totalWeightToDestination;

            public Path(String destinationVertexId, String sourceVertexId, int totalWeightToDestination) {
                this.destinationVertexId = destinationVertexId;
                this.sourceVertexId = sourceVertexId;
                this.totalWeightToDestination = totalWeightToDestination;
            }
        }

        //Use a PQ of weights from vertices with the priority of lowest weight
        PriorityQueue<Vertex> queue = new PriorityQueue<>(
                Comparator.comparingInt(
                        a -> a.edges.stream().map(b -> b.weight).min(Comparator.naturalOrder()).orElse(1)
                )
        );

        //Record lowest known distance to that vertex
        HashMap<String, Path> distances = new HashMap<>();
        //initialize distances
        graph.vertices.forEach((k, v) -> distances.put(k, new Path(k, null, Integer.MAX_VALUE)));

        //Do not repeat finished vertices
        HashSet<String> visited = new HashSet<>();

        //Get source
        Vertex sourceVertex = graph.vertices.get(source);
        queue.add(sourceVertex);

        //BFS
        while (!queue.isEmpty()) {
            Vertex currentVertex = queue.poll();

            Path path = distances.get(currentVertex.id);
            if(path.totalWeightToDestination == Integer.MAX_VALUE) {
                visited.add(currentVertex.id);
            }


        }


    }

}
