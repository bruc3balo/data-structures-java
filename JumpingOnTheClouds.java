import java.util.ArrayList;
import java.util.List;

public class JumpingOnTheClouds {

    public static void main(String[] args) {
        //0 0 0 0 1 0 = 3
        //0 1 0 0 0 1 0 = 3 (0-> 2-> 4-> 6)
        //0 0 0 1 0 0 = 3
        //0 0 1 0 0 1 0 = 4
        //0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0 = 53
        List<Integer> c = List.of(0,0,0,0,1,0);
        System.out.println("Jumps are " + jumpingOnClouds(c));
    }

    public static int jumpingOnClouds(List<Integer> c) {
        // Write your code here
        int jumps = 0;

        for (int i = 0; i < c.size() - 1; i++) {

            //check to skip 2 or 1
            if (i + 2 < c.size() && c.get(i + 2) == 0) i++;

            jumps++;
        }

        return jumps;
    }
}
