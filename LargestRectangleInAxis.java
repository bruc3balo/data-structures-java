import java.util.*;

public class LargestRectangleInAxis {
    public static void main(String[] args) {
        Point[] points = new Point[]{
                new Point(2, 1), //A
                new Point(8, 1), //B
                new Point(2, 4), //C
                new Point(8, 4), //D
                new Point(-8, 1), //E
                new Point(2, -2), //F
                new Point(-8, -2), //G
                new Point(-2, 8), //H
                new Point(3, 3), //I
                new Point(-7, 3), //J
                new Point(-2, -2), //K
                new Point(2, 6), //L
                new Point(-6, 0), //M
        };

        int result = findRectWithMaxArea(points);
        System.out.println(result);
    }

    static int findRectWithMaxArea(Point[] points) {

        int maxArea = 0;

        //Rules
        HashMap<String, Map<Integer, List<Point>>> map = new HashMap<>();

        //2 points on same y, 2 points on same x
        for (Point p : points) {

            Map<Integer, List<Point>> xPoints = map.getOrDefault("x", new HashMap<>());
            List<Point> pXPoints = xPoints.getOrDefault(p.x, new ArrayList<>());
            pXPoints.add(p);
            xPoints.put(p.x, pXPoints);
            map.put("x", xPoints);

            Map<Integer, List<Point>> yPoints = map.getOrDefault("y", new HashMap<>());
            List<Point> pYPoints = yPoints.getOrDefault(p.y, new ArrayList<>());
            pYPoints.add(p);
            yPoints.put(p.y, pYPoints);
            map.put("y", yPoints);

        }

        for (Point a : points) {

            List<Point> potentialF = map.get("x").get(a.x);
            List<Point> potentialE = map.get("y").get(a.y);

            //G?
            for (Point f : potentialF) {
                if (f == a) continue;
                for (Point e : potentialE) {
                    if (e == a || e == f) continue;
                    for (Point g : points) {
                        if (g == a || g == f || g == e) continue;

                        List<Point> rectangle = List.of(a, e, f, g);

                        if (isRectangle(rectangle)) {
                            int area = calculateArea(rectangle);
                            maxArea = Math.max(maxArea, area);
                        }
                    }
                }
            }


        }

        return maxArea;
    }

    static boolean isRectangle(List<Point> randomPoints) {
        if (randomPoints.size() != 4) return false;

        // Extract x and y coordinates
        Set<Integer> xSet = new HashSet<>();
        Set<Integer> ySet = new HashSet<>();

        for (Point p : randomPoints) {
            xSet.add(p.x);
            ySet.add(p.y);
        }

        // A valid rectangle should have exactly 2 distinct x-values and 2 distinct y-values
        return xSet.size() == 2 && ySet.size() == 2;
    }

    static int calculateArea(List<Point> rectangle) {
        if (rectangle.size() != 4) return 0;

        Point a = rectangle.get(0);
        Point e = rectangle.get(1);
        Point f = rectangle.get(2);

        int length = Math.abs(a.x - e.x);
        int width = Math.abs(a.y - f.y);
        return length * width;
    }

    static int findRectWithMaxArea_(Point[] points) {

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

    static int findRectWithMaxArea__(Point[] points) {

        //Find matching points
        int maxArea = 0;

        // Iterate over all point groups
        for (Point a : points) {

            for (Point f : points) {
                if (f == a) continue;

                for (Point e : points) {
                    if (e == f || e == a) continue;

                    for (Point g : points) {
                        if (g == f || g == a || g == e) continue;

                        List<Point> rectangle = List.of(a, e, f, g);
                        if (!isRectangle(rectangle)) continue;

                        int area = calculateArea(rectangle);
                        maxArea = Math.max(maxArea, area);
                    }
                }
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
