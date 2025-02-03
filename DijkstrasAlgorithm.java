import java.util.*;

public class DijkstrasAlgorithm {

    public static void main(String[] args) {
        int n = 5; // Number of vertices
        Map<Integer, List<Node>> graph = new HashMap<>();

        // Adding edges
        graph.computeIfAbsent(1, k -> new ArrayList<>()).add(new Node(2, 2));
        graph.computeIfAbsent(1, k -> new ArrayList<>()).add(new Node(3, 4));
        graph.computeIfAbsent(2, k -> new ArrayList<>()).add(new Node(3, 1));
        graph.computeIfAbsent(2, k -> new ArrayList<>()).add(new Node(4, 7));
        graph.computeIfAbsent(3, k -> new ArrayList<>()).add(new Node(5, 3));
        graph.computeIfAbsent(4, k -> new ArrayList<>()).add(new Node(5, 1));

        int startNode = 1;
        int[] shortestDistances = dijkstra(n, graph, startNode);

        // Print distances
        for (int i = 1; i <= n; i++) {
            System.out.println(
                    "Shortest distance from " + startNode + " to " + i + " is " +
                            (shortestDistances[i] == Integer.MAX_VALUE ? "∞" : shortestDistances[i])
            );
        }
    }

    private static class Node implements Comparable<Node> {
        int vertexId, weight;

        Node(int vertexId, int weight) {
            this.vertexId = vertexId;
            this.weight = weight;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.weight, other.weight);
        }

    }

    private static int[] dijkstra(int n, Map<Integer, List<Node>> graph, int startVertexId) {

        //Create an array with distances
        int[] dist = new int[n + 1];

        //Fill array with +infinity
        Arrays.fill(dist, Integer.MAX_VALUE);

        //Initial distance from source is always 0
        dist[startVertexId] = 0;

        //Use a minHeap to order by least distance
        //Use a queue to traverse Breath First Search
        //i.e. Best First Search
        PriorityQueue<Node> minHeap = new PriorityQueue<>();

        //Start traversing from the source
        minHeap.add(new Node(startVertexId, 0));

        while (!minHeap.isEmpty()) {
            Node current = minHeap.poll();
            int currentVertexId = current.vertexId;

            // Traverse neighbors to find unexplored distances
            for (Node neighbor : graph.getOrDefault(currentVertexId, Collections.emptyList())) {

                int neighbourVertexId = neighbor.vertexId;
                int neighbourWeight = neighbor.weight;

                int distanceToCurrent = dist[currentVertexId];
                int distanceToNeighborFromCurrent = distanceToCurrent + neighbourWeight;
                int currentDistanceToNeighbour = dist[neighbourVertexId];

                //If found a cheaper path / distance to current, update distance table
                if (distanceToNeighborFromCurrent < currentDistanceToNeighbour) {
                    dist[neighbourVertexId] = distanceToNeighborFromCurrent;
                    minHeap.add(new Node(neighbourVertexId, distanceToNeighborFromCurrent));
                }

            }
        }

        return dist;
    }

}
