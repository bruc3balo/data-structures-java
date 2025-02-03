import java.util.*;

public class LargestRectangleInAxis {
    public static void main(String[] args) {
        Point[] points = new Point[]{
                new Point(2, 1),
                new Point(8, 1),
                new Point(2, 4),
                new Point(8, 4),
                new Point(-8, 1),
                new Point(-2, -2),
                new Point(-8, -2),
                new Point(-2, 8),
                new Point(3, 3),
                new Point(-7, 3),
                new Point(-2, -2),
                new Point(2, 6),
                new Point(-6, 0),
        };

        int result = findRectWithMaxArea(points);
        System.out.println(result);
    }


    static int findRectWithMaxArea(Point[] points) {

        //Find matching points
        final Map<Integer, Set<Integer>> graph = new HashMap<>();
        for (Point p : points) {
            graph.compute(p.x, (x1, pointsWithSameX) -> {
                if (pointsWithSameX == null) pointsWithSameX = new HashSet<>();
                pointsWithSameX.add(p.y);
                return pointsWithSameX;
            });
        }

        int maxArea = -1;
        int n = points.length;

        // Iterate over all point pairs
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                Point p1 = points[i];
                Point p2 = points[j];

                //Must form diagonal
                if (p1.x == p2.x || p1.y == p2.y) continue;

                Set<Integer> p1YPointsWithSameX = graph.get(p1.x);
                Set<Integer> p2YPointsWithSameX = graph.get(p2.x);

                // Check if the other two corners exist in the graph
                if (p1YPointsWithSameX.contains(p2.y)) continue;
                if (p2YPointsWithSameX.contains(p1.y)) continue;

                int area = Math.abs(p1.x - p2.x) * Math.abs(p1.y - p2.y);
                System.out.println("Rectangle: " + p1.x + ", " + p1.y + ", " + p2.x + ", " + p2.y + ": " + area);

                maxArea = Math.max(maxArea, area);
            }
        }

        //Find Max area
        return maxArea;
    }


    private static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }


}
