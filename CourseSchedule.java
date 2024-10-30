import java.util.*;

class CourseSchedule {

    static HashSet<Integer> canBeDone = new HashSet<>();

    public static void main(String[] args) {
        //int numCourses = 2;
        //int[][] prerequisites = new int[][]{new int[]{1,0}};
        //int[][] prerequisites = new int[][]{new int[]{1, 0}, new int[]{0,1}};

        //int numCourses = 8;
       /* int[][] prerequisites = new int[][]{
                new int[]{1,0}, new int[]{2,6}, new int[]{1,7},
                new int[]{6,4},new int[]{7,0},new int[]{0,5}
        };*/

        int numCourses = 7;
        int[][] prerequisites = new int[][]{
                new int[]{1, 0}, new int[]{0, 3}, new int[]{0, 2}, new int[]{3, 2},
                new int[]{2, 5}, new int[]{4, 5}, new int[]{5, 6}, new int[]{2, 4},
        };

        System.out.println("Output is " + canFinish(numCourses, prerequisites));
        System.out.println("Expected output is " + canFinishNeet(numCourses, prerequisites));
    }

    public static boolean canFinish(int numCourses, int[][] prerequisites) {
        canBeDone.clear();
        HashMap<Integer, List<Integer>> c = new HashMap<>();

        for (int[] prerequisite : prerequisites) {

            int pre = prerequisite[1];
            int cor = prerequisite[0];

            List<Integer> cl = c.getOrDefault(cor, new ArrayList<>());
            cl.add(pre);
            c.put(cor, cl);
        }

        System.out.println(c);

        // course a
        // check for cycle
        // for each pre

        for (Map.Entry<Integer, List<Integer>> e : c.entrySet()) {
            int remaining = numCourses - 1;

            //get a course
            Integer cor = e.getKey();

            //find prerequisites
            List<Integer> pre = e.getValue();

            //if prerequisite
            // has course == cor -> rem - 1;
            // is null -> rem -1;
            // rem < 0 return false;

            //System.out.println(cor);

            if (hasCycle(cor, pre, c)) return false;
        }

        return true;
    }

    public static boolean hasCycle(int course, List<Integer> pre, HashMap<Integer, List<Integer>> map) {
        HashSet<Integer> visited = new HashSet<>();
        return hasDuplicate(course, course, pre, map, visited);
    }

    private static boolean hasDuplicate(int mainCourse, int course, List<Integer> pre, HashMap<Integer, List<Integer>> map, HashSet<Integer> visited) {
        if (canBeDone.contains(course)) return false;
        if (visited.contains(course)) return true;
        if (pre == null) return false;
        if (pre.contains(course)) return true;
        if (pre.contains(mainCourse)) return true;

        visited.add(course);

        boolean cycle = false;

        for (int c : pre) {
            if (hasDuplicate(mainCourse, c, map.get(c), map, visited)) {
                cycle = true;
            }
        }

        if (!cycle) canBeDone.add(course);

        return cycle;
    }

    public static boolean canFinishNeet(int numCourses, int[][] prerequisites) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            adj.add(new ArrayList<>());
        }

        for (int i = 0; i < prerequisites.length; i++) {
            adj.get(prerequisites[i][0]).add(prerequisites[i][1]);
        }

        int[] visited = new int[numCourses];
        for (int i = 0; i < numCourses; i++) {
            if (visited[i] == 0) {
                if (isCyclicNeet(adj, visited, i)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isCyclicNeet(List<List<Integer>> adj, int[] visited, int curr) {
        if (visited[curr] == 2) {
            return true;
        }

        visited[curr] = 2;
        for (int i = 0; i < adj.get(curr).size(); i++) {
            if (visited[adj.get(curr).get(i)] != 1) {
                if (isCyclicNeet(adj, visited, adj.get(curr).get(i))) {
                    return true;
                }
            }
        }
        visited[curr] = 1;
        return false;
    }

}
