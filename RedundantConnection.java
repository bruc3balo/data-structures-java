import java.util.*;

import static java.util.Comparator.comparingInt;

class RedundantConnection {
    public static void main(String[] args) {
        int[][] edges = new int[][]{
                new int[]{1, 2},
                new int[]{1, 3},
                new int[]{2, 3},
        };

        // int[][] edges = new int[][]{
        //         new int[]{1, 2}, new int[]{2, 3},
        //         new int[]{3, 4}, new int[]{1, 4},
        //             new int[]{1, 5}
        // };
        // System.out.println("Output is " + Arrays.stream(findRedundantConnection(edges)).boxed().toList());
        System.out.println("Expected Output is " + Arrays.stream(findRedundantConnectionNeet(edges)).boxed().toList());
    }

    public static int[] findRedundantConnection(int[][] edges) {
        // A, B, C
        // if A -> B && A -> C
        // single edge cannot be removed
        // common node is a possibility
        //
        HashMap<Integer, HashSet<Integer>> map = new HashMap<>();
        HashSet<List<Integer>> visited = new HashSet<>();
        List<List<Integer>> order = new ArrayList<>();
        List<List<Integer>> answers = new ArrayList<>();

        for (int[] edge : edges)
            for (int j = 0; j < edges[0].length; j++) {
                int node = edge[0];
                int friend = edge[1];


                if (!contains(order, List.of(node, friend))) order.add(List.of(node, friend));

                HashSet<Integer> ls = map.getOrDefault(node, new HashSet<>());
                ls.add(friend);
                map.put(node, ls);

                HashSet<Integer> rs = map.getOrDefault(friend, new HashSet<>());
                rs.add(node);
                map.put(friend, rs);
            }

        Comparator<Map.Entry<Integer, HashSet<Integer>>> size = comparingInt(a -> -a.getValue().size());
        PriorityQueue<Map.Entry<Integer, HashSet<Integer>>> q = new PriorityQueue<>(size);
        q.addAll(map.entrySet());


        while (!q.isEmpty()) {
            Map.Entry<Integer, HashSet<Integer>> entry = q.poll();
            for (Integer node : entry.getValue()) {
                if (!hasBeenDone(node, entry.getKey(), visited)) {

                    if (isConnected(deepCopy(map), edges, entry.getKey(), node)) {
                        List<Integer> list = List.of(node, entry.getKey());

                        visited.add(list);
                        answers.add(list);
                    }
                }
            }
        }

        if (answers.size() > 1) {
            int i = order.size() - 1;
            while (i >= 0) {
                List<Integer> o = order.get(i);
                for (List<Integer> a : answers) if (matches(a, o)) return new int[]{o.get(0), o.get(1)};
                i--;
            }
        } else if (answers.size() == 1) {
            List<Integer> a = answers.get(0);
            for (List<Integer> o : order) if (matches(a, o)) return new int[]{o.get(0), o.get(1)};
        }

        return new int[]{};
    }

    public static boolean isConnected(HashMap<Integer, HashSet<Integer>> map, int[][] edges, int source, int destination) {

        //try rm connection
        int n = edges.length;


        //remove from map
        map.get(source).removeIf(i -> i.equals(destination));
        map.get(destination).removeIf(i -> i.equals(source));

        //check if nodes are still n
        int count = 0;

        for (Map.Entry<Integer, HashSet<Integer>> node : map.entrySet()) {
            if (!node.getValue().isEmpty()) count++;
        }


        return n == count;
    }

    public static boolean hasBeenDone(int source, int destination, HashSet<List<Integer>> visited) {
        for (List<Integer> vs : visited) if (vs.contains(source) && vs.contains(destination)) return true;
        return false;
    }

    public static boolean contains(List<List<Integer>> list, List<Integer> val) {
        for (List<Integer> vs : list) if (vs.contains(val.get(0)) && vs.contains(val.get(1))) return true;
        return false;
    }

    public static boolean matches(List<Integer> op, List<Integer> order) {
        return order.contains(op.get(0)) && order.contains(op.get(1));
    }

    public static HashMap<Integer, HashSet<Integer>> deepCopy(HashMap<Integer, HashSet<Integer>> map) {
        HashMap<Integer, HashSet<Integer>> copy = new HashMap<>();

        for (Map.Entry<Integer, HashSet<Integer>> set : map.entrySet()) {

            HashSet<Integer> val = new HashSet<>(set.getValue());

            copy.put(set.getKey(), val);
        }

        return copy;
    }

    //Neet
    static int[] parent;

    public static int[] findRedundantConnectionNeet(int[][] edges) {
        parent = new int[edges.length];
        //represent themselves
        //i.e. groups of themselves
        for (int i = 0; i < edges.length; i++)
            parent[i] = i + 1;

        for (int[] edge : edges) {
            if (findNeet(edge[0]) == findNeet(edge[1])) return edge;

                //to merge them into same subset
            else unionNeet(edge[0], edge[1]);
        }

        return new int[2];
    }

    public static int findNeet(int x) {
        //we do minus 1 to find the parent in the array as the array starts from 0
        //i.e. in the parent array x is x - 1
        if (x == parent[x - 1])
            return x;

        //find parent of x's parent
        return findNeet(parent[x - 1]);
    }

    public static void unionNeet(int child, int par) {

        //join representatives / parents of both values by making left child of right
        parent[findNeet(par) - 1] = findNeet(child);
    }

}
