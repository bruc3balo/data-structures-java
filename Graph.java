import java.util.*;

//graphs
public class Graph {

    //Adjacency list implementation
    //undirected graph
    private final HashMap<Integer, Vertex> vertices = new HashMap<>();

    public Graph() {
    }

    public HashMap<Integer, Vertex> getVertices() {
        return vertices;
    }

    public boolean addVertex(String name, int id) {
        if (doesVertexIdExist(id)) return false;
        return addVertex(new Vertex(id, name));
    }


    public boolean addVertex(Vertex vertex) {
        if (doesVertexIdExist(vertex.id)) return false;
        vertices.put(vertex.id, vertex);
        return true;
    }

    public boolean areVerticesConnected(int sourceVertexId, int destinationVertexId) {
        Vertex sourceVertex = getVertex(sourceVertexId).orElse(null);
        if (sourceVertex == null) return false;

        Vertex destinationVertex = getVertex(destinationVertexId).orElse(null);
        if (destinationVertex == null) return false;

        return sourceVertex.getEdgeList().containsKey(destinationVertex.id) && destinationVertex.getEdgeList().containsKey(sourceVertex.id);
    }

    public boolean deleteVertex(int vertexId) {
        Vertex vertex = getVertex(vertexId).orElse(null);
        if (vertex == null) return false;

        for (Map.Entry<Integer, Edge> e : vertex.getEdgeList().entrySet())
            deleteEdge(e.getValue().destinationVertexId, vertexId);

        vertices.remove(vertexId);

        return true;
    }

    public boolean updateVertex(int vertexId, String name) {
        Vertex vertex = getVertex(vertexId).orElse(null);
        if (vertex == null) return false;
        vertex.setName(name);
        return true;
    }

    public boolean addEdgeByVertexId(int sourceVertexId, int destinationVertexId, int weight) {
        //do source & destination already exists

        Vertex sourceVertex = getVertex(sourceVertexId).orElse(null);
        if (sourceVertex == null) return false;

        Vertex destinationVertex = getVertex(destinationVertexId).orElse(null);
        if (destinationVertex == null) return false;

        //do they already have connections
        Edge sourceEdge = new Edge(destinationVertexId, weight);
        Edge destinationEdge = new Edge(sourceVertexId, weight);

        //now add source to destination && destination to source
        if (!doesEdgeExistInVertex(destinationEdge, destinationVertex) && !doesEdgeExistInVertex(sourceEdge, sourceVertex))
            return sourceVertex.addEdge(sourceEdge) && destinationVertex.addEdge(destinationEdge);

        return false;
    }

    public boolean updateEdge(int sourceVertexId, int destinationVertexId, int newWeight) {
        Vertex sourceVertex = getVertex(sourceVertexId).orElse(null);
        if (sourceVertex == null) return false;

        Vertex destinationVertex = getVertex(destinationVertexId).orElse(null);
        if (destinationVertex == null) return false;


        return sourceVertex.updateEdge(newWeight, destinationVertexId) && destinationVertex.updateEdge(newWeight, sourceVertexId);
    }

    public boolean deleteEdge(int sourceVertexId, int destinationVertexId) {
        Vertex sourceVertex = getVertex(sourceVertexId).orElse(null);
        if (sourceVertex == null) return false;

        Vertex destinationVertex = getVertex(destinationVertexId).orElse(null);
        if (destinationVertex == null) return false;

        return sourceVertex.removeEdge(destinationVertexId) && destinationVertex.removeEdge(sourceVertexId);
    }


    public void bfsProcess(Vertex v) {
        if (vertices.isEmpty()) return;
        Queue<Vertex> toProcess = new LinkedList<>();
        HashSet<Integer> processed = new HashSet<>();

        toProcess.offer(v);

        while (!toProcess.isEmpty()) {

            v = toProcess.poll();

            if (!processed.contains(v.id)) {

                //Process
                System.out.println(v.id);

                //Processed
                processed.add(v.id);


            }

            for (Map.Entry<Integer, Edge> edgeEntry : v.getEdgeList().entrySet()) {
                Edge edge = edgeEntry.getValue();

                //Skip already processed
                if (processed.contains(edge.destinationVertexId)) return;

                //Add Neighbor to process queue
                getVertex(edge.destinationVertexId).ifPresent(toProcess::offer);
            }

        }
    }

    public void bfs(Vertex v) {
        HashSet<Integer> seen = new HashSet<>();
        bfsRecursion(v, seen);
    }

    public void bfsRecursion(Vertex v, HashSet<Integer> processed) {
        if (v == null || processed.contains(v.id)) return; //base case

        //Process
        System.out.println(v.id);

        //Processed
        processed.add(v.id);

        for (Map.Entry<Integer, Edge> edgeEntry : v.getEdgeList().entrySet()) {

            //Find neighbour
            Vertex neighbour = getVertex(edgeEntry.getValue().getDestinationVertexId()).orElse(null);

            //Add to stack
            bfsRecursion(neighbour, processed);
        }

    }

    public void dfsProcess(Vertex v) {
        if (v == null) return;
        if (vertices.isEmpty()) return;

        Stack<Vertex> toProcess = new Stack<>();
        HashSet<Integer> processed = new HashSet<>();


        toProcess.push(v);

        while (!toProcess.isEmpty()) {
            v = toProcess.pop();

            if (!processed.contains(v.id)) {

                //It's processed when all neighbours have been processed
                processed.add(v.id);

                System.out.println(v.id);
            }

            for (Map.Entry<Integer, Edge> edgeEntry : v.getEdgeList().entrySet()) {
                Edge edge = edgeEntry.getValue();

                //Only unvisited vertices
                if (processed.contains(edge.destinationVertexId)) continue;

                //Add neighbour to process stack
                getVertex(edge.destinationVertexId).ifPresent(toProcess::push);
            }
        }

    }

    public void dfs(Vertex v) {
        HashSet<Integer> seen = new HashSet<>();
        dfsRecursion(v, seen);
    }

    public void dfsRecursion(Vertex v, HashSet<Integer> processed) {
        if (v == null || processed.contains(v.id)) return; //base case

        //Process after all neighbours are visited
        processed.add(v.id);

        //Follow all neighbour's of vertex v
        for (Map.Entry<Integer, Edge> edgeEntry : v.getEdgeList().entrySet()) {

            //Find neighbour
            Vertex neighbour = getVertex(edgeEntry.getValue().getDestinationVertexId()).orElse(null);

            //Add to stack
            dfsRecursion(neighbour, processed);
        }

        //Process
        System.out.println(v.id);
    }

    public Optional<Vertex> getVertex(int id) {
        return Optional.of(vertices.get(id));
    }

    private boolean doesEdgeExistInVertex(Edge edge, Vertex vertex) {
        return vertex.doesEdgeIdExists(edge);
    }

    private boolean doesVertexIdExist(int id) {
        return vertices.containsKey(id);
    }


    @Override
    public String toString() {
        return "Graph {" +
                "vertices=" + vertices +
                '}';
    }

    public void printGraph() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, Vertex> v : vertices.entrySet()) {
            sb.setLength(0);

            Vertex vertex = v.getValue();
            sb.append(vertex.name).append(" (").append(vertex.id).append(") --> ");

            vertex.printEdgeList(sb);
            System.out.println(sb);
        }
    }

    public static class Edge {

        private int destinationVertexId;
        private int weight;

        public Edge(int destinationVertexId, int weight) {
            this.destinationVertexId = destinationVertexId;
            this.weight = weight;
        }

        public int getDestinationVertexId() {
            return destinationVertexId;
        }

        public void setDestinationVertexId(int destinationVertexId) {
            this.destinationVertexId = destinationVertexId;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "destinationVertexId=" + destinationVertexId +
                    ", weight=" + weight +
                    '}';
        }
    }

    public static class Vertex {
        private int id;
        private String name;
        private final HashMap<Integer, Edge> edgeList = new HashMap<>();

        public Vertex(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public HashMap<Integer, Edge> getEdgeList() {
            return edgeList;
        }

        public boolean addEdge(Edge edge) {
            if (doesEdgeIdExists(edge)) return false;
            edgeList.put(edge.destinationVertexId, edge);
            return true;
        }

        public boolean removeEdge(int destinationId) {
            Edge edge = getEdge(destinationId).orElse(null);
            if (edge == null) return false;
            edgeList.remove(edge.destinationVertexId);
            return true;
        }

        public boolean addEdge(int destinationVertexId, int weight) {
            Edge edge = new Edge(destinationVertexId, weight);
            if (doesEdgeIdExists(edge)) return false;
            edgeList.put(destinationVertexId, edge);
            return true;
        }

        public boolean updateEdge(int newWeight, int destinationId) {
            Edge edge = getEdge(destinationId).orElse(null);
            if (edge == null) return false;
            edge.setWeight(newWeight);
            edgeList.put(edge.destinationVertexId, edge);
            return true;
        }

        public Optional<Edge> getEdge(int destinationId) {
            return Optional.of(edgeList.get(destinationId));
        }

        public boolean doesEdgeIdExists(Edge edge) {
            return edgeList.containsKey(edge.destinationVertexId);
        }

        @Override
        public String toString() {
            return "Vertex{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", edgeList=" + edgeList +
                    '}';
        }

        public void printEdgeList(StringBuilder sb) {
            sb.append("[");
            for (Map.Entry<Integer, Edge> e : edgeList.entrySet()) {
                Edge edge = e.getValue();
                sb.append(edge.destinationVertexId).append("(").append(edge.weight).append(")");
            }
            sb.append("]");
        }
    }

}
