import java.util.*;

//todo
class CourseScheduleII {

    public static void main(String[] args) {
        int numCourses = 2;
//        int [][] prerequisites = new int[][]{new int[]{0, 1}};
//        int[][] prerequisites = new int[][]{new int[]{1, 0}};
        int[][] prerequisites = new int[][]{new int[]{1, 0}, new int[]{0, 1}};

        /*int numCourses = 4;
        int[][] prerequisites = new int[][]{
                new int[] {1,0},new int[] {2,0},
                new int[] {3,1},new int[] {3,2},
        };*/

//        int numCourses = 3;
      /*
        int[][] prerequisites = new int[][]{
                new int[]{0, 1},
                new int[]{2, 1},
        };*/


        System.out.println("Output is " + Arrays.stream(findOrder(numCourses, prerequisites)).boxed().toList());
//        System.out.println("Output is "+ Arrays.stream(findOrder(1, new int[][]{})).boxed().toList());
    }

    public static int[] findOrder(int numCourses, int[][] prerequisites) {
        return topologicalSortMama(numCourses, prerequisites);
    }

    public static int[] topologicalSortMama(int numCourses, int[][] prerequisites) {
        //if(prerequisites.length == 0) return new int[]{};

        HashMap<Integer, List<Integer>> courses = new HashMap<>();

        int[] inDegree = new int[numCourses];
        int[] result = new int[numCourses];

        for (int[] prerequisite : prerequisites) {

            int pre = prerequisite[1];
            int cor = prerequisite[0];

            List<Integer> cl = courses.getOrDefault(pre, new ArrayList<>());
            cl.add(cor);
            courses.put(pre, cl);

            inDegree[cor]++;
        }

        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) if (inDegree[i] == 0) q.offer(i);

        int count = 0;
        while (!q.isEmpty()) {
            Integer vertex = q.poll();
            result[count++] = vertex;

            if (courses.containsKey(vertex))
                for (int e : courses.get(vertex)) {
                    inDegree[e]--;
                    if (inDegree[e] == 0) q.add(e);
                }

        }

        if (count == numCourses) return result;
        return new int[]{};
    }

    public static int[] topologicalSortInsider(int numCourses, int[][] prerequisites) {
        int[] result = new int[numCourses];

        if (prerequisites.length == 0) {

            int count = 0;
            while (numCourses > 0) result[count++] = --numCourses;

            return result;
        }

        HashMap<Integer, List<Integer>> courses = new HashMap<>();
        HashSet<Integer> visited = new HashSet<>();
        Stack<Integer> ordering = new Stack<>();

        for (int[] prerequisite : prerequisites) {

            int pre = prerequisite[1];
            int cor = prerequisite[0];

            List<Integer> cl = courses.getOrDefault(cor, new ArrayList<>());
            cl.add(pre);
            courses.put(cor, cl);


            if (!courses.containsKey(pre)) courses.put(pre, new ArrayList<>());

        }

        try {
            for (Integer vertex : courses.keySet())
                dfs(courses, vertex, ordering, visited, new HashSet<>());

        } catch (CycleEx e) {
            return new int[]{};
        }

        while (!ordering.isEmpty() || numCourses > 0)
            result[--numCourses] = !ordering.isEmpty() ? ordering.pop() : (courses.size() - numCourses);

        return result;
    }

    public static void dfs(HashMap<Integer, List<Integer>> graph, Integer vertex, Stack<Integer> ordering, HashSet<Integer> visited, HashSet<Integer> cycle) {
        if (cycle.contains(vertex)) throw new CycleEx();
        if (visited.contains(vertex)) return;

        visited.add(vertex);
        cycle.add(vertex);

        for (int neighbor : graph.get(vertex))
            dfs(graph, neighbor, ordering, visited, cycle);
        ordering.push(vertex);

    }

    static class CycleEx extends RuntimeException {

    }

}
