import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

import static java.util.Comparator.comparingInt;

class CarFleet {
    public static void main(String[] args) {
//        int target = 12; int[] position = new int[]{10,8,0,5,3}; int[] speed = new int[]{2,4,1,1,3};
        int target = 10;
        int[] position = new int[]{3};
        int[] speed = new int[]{3};
//        int target = 100; int[] position = new int[]{0, 2, 4}; int[] speed = new int[]{4, 2, 1};

        System.out.println("Output is " + carFleet(target, position, speed));
        System.out.println("Expected output is " + carFleetNeet(target, position, speed));
    }

    public static int carFleet(int target, int[] p, int[] s) {
        int result = 0;

        int maxHours = 1;

        int[] position = Arrays.copyOf(p, p.length);
        int[] speed = Arrays.copyOf(s, s.length);

        //maxHours
        for (int i = 0; i < position.length; i++) {
            int car = position[i];
            int pace = speed[i];

            int distance = target - car;
            int time = distance / pace;
            maxHours = Math.max(time, maxHours);
            //System.out.println("Car at "+car + " will take "+time + " hours to get to "+target);
        }

        for (int hour = 1; hour <= maxHours; hour++) {
            System.out.println("=================================================");

            //car & distance
            HashMap<Integer, Integer> carPositionAtHourMap = new HashMap<>();

            for (int i = 0; i < position.length; i++) {
                int car = position[i];
                int pace = speed[i];
                int distanceToBeCovered = pace * hour;
                int distanceCovered = distanceToBeCovered + car;
                int newPositionAtHour = Math.min(distanceCovered, target);
                carPositionAtHourMap.put(i, newPositionAtHour);
                //System.out.println("Cat at "+car + " will be at "+newPositionAtHour + " in hour "+hour);
            }

            for (int car = 0; car < carPositionAtHourMap.size(); car++) {
                int distanceTravelled = carPositionAtHourMap.get(car);

                for (int car2 = car + 1; car2 < carPositionAtHourMap.size(); car2++) {
                    int car2DistanceTravelled = carPositionAtHourMap.get(car2);
                    if (distanceTravelled == car2DistanceTravelled) {

                        if (speed[car2] != speed[car]) {
                            System.out.println("Speed before " + Arrays.toString(speed));
                            speed[car2] = speed[car];
                            position[car2] = position[car];
                            System.out.println("Speed after " + Arrays.toString(speed));
                            //result++;
                        }
                    }
                }
            }

            HashSet<Integer> distances = new HashSet<>(carPositionAtHourMap.values());
            result = distances.size();
            carPositionAtHourMap.clear();
        }


        return result;
    }

    public static int carFleetNeet(int target, int[] position, int[] speed) {
        if (position.length == 1) return 1;
        Stack<Double> stack = new Stack<>();

        //combine both arrays with speed and distance
        int[][] combine = new int[position.length][2];
        for (int i = 0; i < position.length; i++) {
            combine[i][0] = position[i];
            combine[i][1] = speed[i];
        }

        //sort by position of car from closest to farthest
        Arrays.sort(combine, comparingInt(o -> o[0]));

        //do from reverse order from the car closest to the finish
        for (int i = combine.length - 1; i >= 0; i--) {

            //calculate time to finish from closest to finish to farthest
            double currentTime = (double) (target - combine[i][0]) / combine[i][1];

            //check if you have seen a car before
            //check for collision i.e. currentTime of current car less / equal to previous car
            if (!(!stack.isEmpty() && currentTime <= stack.peek())) {

                //add current car time to the stack i.e. Fleet
                stack.push(currentTime);
            }
        }

        return stack.size();
    }

}
