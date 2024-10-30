class Streets {
    public static void main(String[] args) {
        Graph hood = new Graph();

        //add vertices
        hood.addVertex("Kingara", 1);
        hood.addVertex("Jamuhuri", 2);
        hood.addVertex("Kilimani", 3);
        hood.addVertex("DayStar", 4);
        hood.addVertex("Tao", 5);

        //add edges
        System.out.println("\n Insert");
        hood.addEdgeByVertexId(1, 2, 3);
        hood.addEdgeByVertexId(2, 3, 3);
        hood.addEdgeByVertexId(3, 4, 3);
        hood.addEdgeByVertexId(4, 5, 3);
        hood.addEdgeByVertexId(2, 4, 3);
        hood.addEdgeByVertexId(3, 5, 3);
        //hood.printGraph();

        System.out.println("\n Update");
        hood.updateEdge(1, 2, 500);
        // hood.printGraph();

        //System.out.println("\n Delete Edge");
        //hood.deleteEdge(1,2);
        //hood.printGraph();

        //System.out.println("\n Update Vertex");
        //hood.updateVertex(1, "To Be deleted");
        //hood.printGraph();

        // System.out.println("\n Delete Vertex");
        // hood.deleteVertex(1);
        hood.printGraph();

        hood.dfsProcess(hood.getVertex(2).orElse(null));
        System.out.println("=");
        hood.dfs(hood.getVertex(2).orElse(null));
//        hood.bfsProcess(hood.getVertex(2).orElse(null));
//        hood.bfs(hood.getVertex(2).orElse(null));


    }
}
