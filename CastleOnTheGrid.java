import java.util.*;

public class CastleOnTheGrid {
    public static void main(String[] args) {
        List<String> grid = List.of(
                ".X.",
                ".X.",
                "..."
        );
//        int startX = 0, startY = 0, goalX = 1, goalY = 2;
        int startX = 0, startY = 0, goalX = 0, goalY = 2;//0 0 0 2 => 3
        System.out.println(minimumMoves(grid, startX, startY, goalX, goalY));
    }


    private static class Node {
        int x, y, cost;

        public Node(int x, int y, int cost) {
            this.x = x;
            this.y = y;
            this.cost = cost;
        }
    }

    public static int minimumMoves(List<String> grid, int startX, int startY, int goalX, int goalY) {

        int columnSize = grid.size();
        int rowSize = grid.get(0).length();

        //Track visited nodes
        final HashSet<String> visited = new HashSet<>();

        //Use queue to traverse
        final Queue<Node> q = new LinkedList<>();

        //Add source
        visited.add(startX + ":" + startY);
        q.offer(new Node(startX, startY, 0));


        while (!q.isEmpty()) {
            final Node current = q.poll();

            // If we reach the goal, return the number of moves
            if (current.x == goalX && current.y == goalY) return current.cost;

            //Explore all 4 directions
            //left
            explore(grid, -1, 0, visited, q, columnSize, rowSize, current);

            //Right
            explore(grid, 1, 0, visited, q, columnSize, rowSize, current);

            //Bottom
            explore(grid, 0, 1, visited, q, columnSize, rowSize, current);

            //Top
            explore(grid, 0, -1, visited, q, columnSize, rowSize, current);

        }

        return -1;
    }

    private static void explore(
            List<String> grid,
            int dx, int dy,
            HashSet<String> visited,
            Queue<Node> q,
            int columnSize, int rowSize,
            Node current
    ) {

        int x = current.x;
        int y = current.y;

        while (true) {

            x += dx;
            y += dy;

            if (x < 0 || x >= columnSize || y < 0 || y >= rowSize) break;
            if (grid.get(x).charAt(y) == 'X') break;

            String visitedPosition = x + ":" + y;
            if (!visited.add(visitedPosition)) break;

            q.add(new Node(x, y, current.cost + 1));
        }
    }


}
