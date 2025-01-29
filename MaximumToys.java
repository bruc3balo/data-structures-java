import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

public class MaximumToys {
    public static void main(String[] args) {
        List<Integer> toys = Arrays.asList(1,12,5,111,200,1000,10);
        int k = 50;
        System.out.println("Maximum Toys is "+maximumToys(toys, k));
    }

    public static int maximumToys(List<Integer> prices, int k) {
        // Write your code here
        AtomicInteger amount = new AtomicInteger(0);
        AtomicInteger noOfToys = new AtomicInteger(0);
        prices.stream().sorted().forEach((price) ->{
            int newAmount = amount.get() + price;
            if(newAmount > k) return;
            noOfToys.incrementAndGet();
            amount.set(newAmount);
        });

        return noOfToys.get();
    }
}
