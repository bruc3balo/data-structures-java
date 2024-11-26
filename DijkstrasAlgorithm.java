import java.util.*;

public class DijkstrasAlgorithm {



    public static void main(String[] args) {

    }


    public static void printGraph(Graph g, Graph.Vertex start) {
        ///GOAL: From start point, find the shortest distances to every other vertex

        final HashSet<Graph.Vertex> completedVertices = new HashSet<>();
        final Map<Graph.Vertex, Graph.Vertex> cheapestPath = new HashMap<>();
        final Map<Graph.Vertex, Integer> pathCost = new HashMap<>();

        ///Step 1: Initialize distances, start will be 0, all others will be infinity
        for (Graph.Vertex v : g.getVertices().values()) {

            //Cheapest path is unknown
            cheapestPath.put(v, null);

            boolean isStart = v.equals(start);

            //Best cost to same point is 0. Best cost to other point is unknown
            Integer bestCost = isStart ? 0 : null;
            pathCost.put(v, bestCost);
        }

        final Queue<Graph.Vertex> toProcessQueue = new PriorityQueue<>(
                Comparator.comparingInt(v -> v.getEdgeList().values().stream().mapToInt(Graph.Edge::getWeight).min().orElse(Integer.MAX_VALUE))
        );

        ///Step 2: From each vertex not completed, starting from the start, visit all neighbours (BFS) with priority of the least weight
        ///and record shortest distances to the vertices and record how to get there i.e. previous vertex

        toProcessQueue.add(start);
        while (!toProcessQueue.isEmpty()) {
            final Graph.Vertex sourceVertex = toProcessQueue.poll();

            for (Graph.Edge neighbour : sourceVertex.getEdgeList().values()) {

                //Find destination
                Optional<Graph.Vertex> destinationVertexOptional = g.getVertex(neighbour.getDestinationVertexId());
                if (destinationVertexOptional.isEmpty()) continue;
                Graph.Vertex destinationVertex = destinationVertexOptional.get();

                //Record shortest distance to destination
                Integer edgeWeight = neighbour.getWeight();
                Integer knownDistance = currentDistance(sourceVertex, destinationVertex, cheapestPath);
                if(knownDistance == null) {
                    cheapestPath.put(destinationVertex, sourceVertex);
                    pathCost.put(destinationVertex, edgeWeight);
                }

                if(knownDistance != null) {
                    Integer knownShortestPath = pathCost.get(destinationVertex);
                    if(knownShortestPath == null) {
                        pathCost.put(destinationVertex, edgeWeight);
                    } else {
                        pathCost.put(destinationVertex, Math.min(knownShortestPath, knownDistance) + edgeWeight);
                    }
                }
                Integer tentativeDistance = knownDistance == null ? 0 : knownDistance + edgeWeight;
                


                //Ensure not completed
                boolean completed = completedVertices.contains(destinationVertex);
                if (!completed) toProcessQueue.offer(destinationVertex);
            }

            completedVertices.add(sourceVertex);
        }


        // N/B: A vertex is completed when all it's neighbours has been visited

        ///Step 3: Draw a graph of all edges and nodes from start to all destinations

    }


    // A -> B -> C -> D
    public static Integer currentDistance(Graph.Vertex origin, Graph.Vertex current, Map<Graph.Vertex, Graph.Vertex> cheapestPath) {
        int distance = 0;
        Graph.Vertex source = current;
        while (source != origin) {
            Graph.Vertex destination = cheapestPath.get(current);
            if(source == null) return null;

            Optional<Graph.Edge> optionalEdge = source.getEdge(destination.getId());
            if (optionalEdge.isEmpty()) return null;

            Graph.Edge edge = optionalEdge.get();
            distance += edge.getWeight();

            source = destination;
        }

        return distance;
    }
}
