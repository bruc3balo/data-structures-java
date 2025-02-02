import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public class RoadsAndLibraries {
    public static void main(String[] args) {

        /*int n = 3, c_lib = 2, c_road = 1;
        List<List<Integer>> cities = Arrays.asList(Arrays.asList(1, 2), Arrays.asList(3, 1), Arrays.asList(2, 3));
        //Min cost = 4
        */


        /*int n = 6, c_lib = 2, c_road = 5;
        List<List<Integer>> cities = Arrays.asList(
                Arrays.asList(1, 3),
                Arrays.asList(3, 4),
                Arrays.asList(2, 4),
                Arrays.asList(1, 2),
                Arrays.asList(2, 3),
                Arrays.asList(5, 6)
        ); //Min cost is 12
        */


        int n = 5, c_lib = 6, c_road = 1;
        List<List<Integer>> cities = Arrays.asList(
                Arrays.asList(1, 2),
                Arrays.asList(1, 3),
                Arrays.asList(1, 4)
        ); //Min cost is 15

        System.out.println("Minimum cost is " + roadsAndLibraries(n, c_lib, c_road, cities));

    }

    public static long roadsAndLibraries(int n, int c_lib, int c_road, List<List<Integer>> cities) {

        if (c_road >= c_lib) return (long) n * c_lib;

        //Use graph map
        final Map<Integer, List<Integer>> possibleRoads = new HashMap<>();

        //Build graph
        for (List<Integer> c : cities) {
            Integer source = c.get(0);
            Integer destination = c.get(1);

            possibleRoads.computeIfAbsent(source, key -> new ArrayList<>()).add(destination);
            possibleRoads.computeIfAbsent(destination, key -> new ArrayList<>()).add(source);
        }

        final boolean[] visited = new boolean[n + 1];
        long totalCost = 0;

        //Traverse all cities
        //if city is not connected, build lib
        for (int city = 1; city <= n; city++) {
            if (visited[city]) continue;

            //Connect city to other cities with single lib
            Queue<Integer> queue = new LinkedList<>();
            queue.add(city);
            visited[city] = true;

            //Build lib for first city in connected component
            long cityCost = c_lib;

            while (!queue.isEmpty()) {
                int nextCity = queue.poll();

                for (int nextCityNeighbour : possibleRoads.getOrDefault(nextCity, Collections.emptyList())) {
                    if (visited[nextCityNeighbour]) continue;
                    visited[nextCityNeighbour] = true;

                    //Connect to neighbour city
                    queue.add(nextCityNeighbour);
                    cityCost += c_road;
                }
            }

            totalCost += cityCost;

        }

        return totalCost;
    }


}