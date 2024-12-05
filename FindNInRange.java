import java.util.*;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ConcurrentSkipListMap;

public class FindNInRange {

    final TreeMap<Integer, Integer> map = new TreeMap<>();

    public static void main(String[] args) {
        FindNInRange findNInRange = new FindNInRange();

        findNInRange.insert(0, 3);

        findNInRange.insert(4, 7);

        findNInRange.insert(8, 11);

        findNInRange.insert(12, 15);

        findNInRange.insert(16, 18);

        findNInRange.insert(19, 22);


        int n = 21;
        Map.Entry<Integer, Integer> range = findNInRange.findRange(n);
        System.out.println("Range of " + n + " is " + range);
    }


    // n (log n)
    public Integer insert(int key, int value) {
        return map.put(key, value);
    }

    // n(log n)
    public Map.Entry<Integer, Integer> findRange(int n) {
        Integer floorKey = map.floorKey(n);
        return Optional.ofNullable(floorKey)
                .map(map::get)
                .map(v -> Map.entry(floorKey, v))
                .orElse(null);
    }





}