import java.util.HashMap;
import java.util.List;

public class HashTablesIceCreamParlor {

    public static void main(String[] args) {
        List<Integer> cost = List.of(1, 4, 5, 3, 2);
        whatFlavors(cost, 4);
    }

    public static void whatFlavors(List<Integer> cost, int money) {
        // Write your code here
        HashMap<Integer, Integer> costIndexMap = new HashMap<>();

        //Store complement of money & cost[i]
        for(int i = 0; i < cost.size(); i++) {
            int costA = cost.get(i);
            int complement = money - costA;

            if(costIndexMap.containsKey(complement)) {
                int costBIndex = costIndexMap.get(complement);
                System.out.println((costBIndex + 1) + " "+(i + 1));
                continue;
            }

            costIndexMap.put(costA, i);
        }

    }



}
