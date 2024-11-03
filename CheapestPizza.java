import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CheapestPizza {

    public static void main(String[] args) {

       /* //buy3CheapestIsFreeCost  //14
        Pizza[] menu = new Pizza[]{
                new Pizza("greek", 7, 5, 10),
                new Pizza("texas", 8, 9, 13),
                new Pizza("european", 5, 10, 13),
        };

        OrderItem[] order = new OrderItem[]{
                new OrderItem("texas", "Medium", 1),
                new OrderItem("european", "Small", 2),
        };*/

        //buy5For100 //900
        /*Pizza[] menu = new Pizza[]{
                new Pizza("margherita", 90, 80, 100),
                new Pizza("hawaii", 80, 90, 120),
                new Pizza("capricciosa", 50, 70, 130),
                new Pizza("greek", 50, 70, 130),
        };

        OrderItem[] order = new OrderItem[]{
                new OrderItem("greek", "Small", 5),
                new OrderItem("margherita", "Small", 4),
                new OrderItem("hawaii", "Large", 1),
                new OrderItem("margherita", "Medium", 2),
                new OrderItem("capricciosa", "Small", 7),
        };*/

        //forEveryLargeSmallIsFreeCost //117
        /*Pizza[] menu = new Pizza[]{
                new Pizza("margherita", 7, 8, 10),
                new Pizza("hawaii", 8, 9, 12),
                new Pizza("capricciosa", 50, 70, 13),
        };

        OrderItem[] order = new OrderItem[]{
                new OrderItem("margherita", "Small", 3),
                new OrderItem("capricciosa", "Large", 2),
                new OrderItem("hawaii", "Large", 3),
                new OrderItem("margherita", "Large", 1),
                new OrderItem("hawaii", "Medium", 1),
                new OrderItem("capricciosa", "Small", 5),
                new OrderItem("capricciosa", "Medium", 1),

        };*/

        Pizza[] menu = new Pizza[]{
                new Pizza("boston", 7, 5, 10),
                new Pizza("hawaii", 8, 9, 12),
                new Pizza("newyorker", 8, 9, 130),
                new Pizza("philadelphia", 5, 10, 13),
        };

        OrderItem[] order = new OrderItem[]{
                new OrderItem("boston", "Small", 3),
                new OrderItem("hawaii", "Large", 3),
                new OrderItem("newyorker", "Large", 1),
                new OrderItem("boston", "Large", 3),
        };


        System.out.println("Price is " + solution(menu, order));
    }

    public static int solution(Pizza[] menu, OrderItem[] order) {

        //cache pizza names
        Map<String, Pizza> pizzaMap = Arrays.stream(menu).collect(Collectors.toMap(p -> p.name, Function.identity()));
        List<OrderItem> allOrders = Arrays.stream(order)
                .flatMap(i -> Collections.nCopies(i.quantity, i).stream())
                .collect(Collectors.groupingBy(o -> o.name))
                .values().stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());


        //find total price without discount
        int noDiscountCost = noDiscountCost(pizzaMap, allOrders);

        int buy3CheapestIsFreeCost = buy3CheapestIsFree(pizzaMap, allOrders, noDiscountCost);
        int buy5For100Cost = buy5For100(pizzaMap, allOrders, noDiscountCost);
        int forEveryLargeSmallIsFreeCost = forEveryLargeSmallIsFree(pizzaMap, allOrders);
        int buy3LargePay3MediumCost = buy3LargePay3Medium(pizzaMap, allOrders, noDiscountCost);

        return Stream.of(noDiscountCost, buy3CheapestIsFreeCost, buy5For100Cost, forEveryLargeSmallIsFreeCost, buy3LargePay3MediumCost)
                .min(Integer::compareTo)
                .orElse(noDiscountCost);
    }

    public static int pizzaSizePrice(Pizza pizza, String size) {
        switch (size) {
            case "Small":
                return pizza.price_S;
            case "Medium":
                return pizza.price_M;
            case "Large":
                return pizza.price_L;
            default:
                throw new IllegalStateException("Unexpected value: " + size);
        }
    }

    public static int orderPrice(Map<String, Pizza> pizzaMap, List<OrderItem> orders) {
        return orders.stream().mapToInt(orderItem -> {
            //Get pizza
            Pizza pizza = pizzaMap.get(orderItem.name);

            //Price of pizza
            return pizzaSizePrice(pizza, orderItem.size);
        }).sum();
    }

    public static int noDiscountCost(Map<String, Pizza> pizzaMap, List<OrderItem> allOrders) {
        return orderPrice(pizzaMap, allOrders);
    }

    public static Integer buy3CheapestIsFree(Map<String, Pizza> pizzaMap, List<OrderItem> allOrders, int noDiscountCost) {
        //Not applicable
        if (allOrders.size() < 3) return noDiscountCost;

        //Order by price, cheapest first
        PriorityQueue<OrderItem> cheapestPizzaQueue = new PriorityQueue<>((a, b) -> {
            //Get pizza
            Pizza pizzaA = pizzaMap.get(a.name);
            Pizza pizzaB = pizzaMap.get(b.name);

            //Price of pizza
            int priceA = pizzaSizePrice(pizzaA, a.size);
            int priceB = pizzaSizePrice(pizzaB, b.size);

            //Order from lowest to highest
            return Integer.compare(priceA, priceB);
        });

        //Add all orders to the queue
        cheapestPizzaQueue.addAll(allOrders);

        OrderItem cheapestOrder = cheapestPizzaQueue.poll();

        // Get pizza
        assert cheapestOrder != null;
        Pizza pizza = pizzaMap.get(cheapestOrder.name);

        //Price of pizza
        int discountAmount = pizzaSizePrice(pizza, cheapestOrder.size);

        //Remove discount from total cost
        return noDiscountCost - discountAmount;
    }

    public static int buy5For100(Map<String, Pizza> pizzaMap, List<OrderItem> allOrders, int noDiscountCost) {
        final int DISCOUNT_PER_PIZZA_PRICE = 100;
        final int DISCOUNT_PER_PIZZA_COUNT = 5;

        Map<String, List<OrderItem>> orderMap = allOrders.stream().collect(Collectors.groupingBy(orderItem -> orderItem.name));
        boolean notApplicable = orderMap.values().stream().noneMatch(a -> a.size() >= DISCOUNT_PER_PIZZA_COUNT);
        if (notApplicable) return noDiscountCost;

        PriorityQueue<List<OrderItem>> minOrderQueue = new PriorityQueue<>((a, b) -> {
            //TODO: Priority on pizza size to pay 100 for

            AtomicInteger aCount = new AtomicInteger();
            List<OrderItem> aOrderStream = a.stream()
                    .sorted(Comparator.comparingInt(o -> pizzaSizePrice(pizzaMap.get(o.name), o.size)))
                    .filter(o -> {
                        int i = aCount.incrementAndGet();
                        return i > DISCOUNT_PER_PIZZA_COUNT;
                    }).toList();

            int aCost = orderPrice(pizzaMap, aOrderStream) + (DISCOUNT_PER_PIZZA_PRICE * DISCOUNT_PER_PIZZA_COUNT);

            AtomicInteger bCount = new AtomicInteger();
            List<OrderItem> bOrderStream = b.stream()
                    .sorted(Comparator.comparingInt(o -> pizzaSizePrice(pizzaMap.get(o.name), o.size)))
                    .filter(o -> {
                        int i = bCount.incrementAndGet();
                        return i > DISCOUNT_PER_PIZZA_COUNT;
                    }).toList();

            int bCost = orderPrice(pizzaMap, bOrderStream) + (DISCOUNT_PER_PIZZA_PRICE * DISCOUNT_PER_PIZZA_COUNT);
            return Integer.compare(aCost, bCost);
        });
        minOrderQueue.addAll(orderMap.values());

        PriorityQueue<OrderItem> toDiscountFromQueue = new PriorityQueue<>(Comparator.comparingInt(a -> -pizzaSizePrice(pizzaMap.get(a.name), a.size)));
        toDiscountFromQueue.addAll(minOrderQueue.poll());

        HashSet<OrderItem> alreadyCalculated = new HashSet<>();

        int totalCost = 0;

        while (!toDiscountFromQueue.isEmpty()) {
            OrderItem orderItem = toDiscountFromQueue.poll();

            boolean discount = alreadyCalculated.size() >= DISCOUNT_PER_PIZZA_COUNT;

            totalCost = discount ? DISCOUNT_PER_PIZZA_PRICE : pizzaSizePrice(pizzaMap.get(orderItem.name), orderItem.size);

            alreadyCalculated.add(orderItem);
        }

        return totalCost + orderPrice(pizzaMap, orderMap.values().stream().flatMap(List::stream).filter(o -> !alreadyCalculated.contains(o)).toList());
    }

    public static int forEveryLargeSmallIsFree(Map<String, Pizza> pizzaMap, List<OrderItem> order) {
        Map<String, List<OrderItem>> orderMap = order.stream().collect(Collectors.groupingBy(o -> o.name));


        int totalCost = 0;
        for (List<OrderItem> pizzaOrders : orderMap.values()) {

            int largeCount = 0;

            for (OrderItem o : pizzaOrders) {

                switch (o.size) {
                    case "Small":
                        if (largeCount > 0) largeCount--;
                        break;
                    case "Large":
                        totalCost += pizzaSizePrice(pizzaMap.get(o.name), o.size);
                        largeCount++;
                        break;
                    default:
                        totalCost += pizzaSizePrice(pizzaMap.get(o.name), o.size);
                        break;
                }

            }
        }
        return totalCost;

    }

    public static int buy3LargePay3Medium(Map<String, Pizza> pizzaMap, List<OrderItem> allOrders, int noDiscountCost) {
        int DISCOUNT_LARGE_COUNT = 3;
        int totalCost = 0;

        long largeCount = allOrders.stream().filter(i -> i.size.equalsIgnoreCase("Large")).count();
        if (largeCount < DISCOUNT_LARGE_COUNT) return noDiscountCost;

        //Order by most large costly to remove
        PriorityQueue<OrderItem> expensiveLargePizzas = new PriorityQueue<>(
                Comparator.comparingInt(a -> -pizzaSizePrice(pizzaMap.get(a.name), "Large"))
        );

        PriorityQueue<OrderItem> cheapMediumPizzas = new PriorityQueue<>(
                Comparator.comparingInt(a -> pizzaSizePrice(pizzaMap.get(a.name), "Medium"))
        );

        expensiveLargePizzas.addAll(allOrders);
        cheapMediumPizzas.addAll(allOrders);

        int discountsLeft = DISCOUNT_LARGE_COUNT;

        HashSet<OrderItem> processedOrders = new HashSet<>();

        while (!expensiveLargePizzas.isEmpty()) {
            OrderItem o = expensiveLargePizzas.poll();
            Pizza pizza = pizzaMap.get(o.name);
            if (o.size.equals("Large") && discountsLeft > 0) {
                discountsLeft--;
                totalCost += pizzaSizePrice(pizza, "Medium");
                continue;
            }

            totalCost += pizzaSizePrice(pizza, o.size);
            processedOrders.add(o);
        }

        return totalCost;
    }

    public static class Pizza {
        public String name;
        public int price_S;
        public int price_M;
        public int price_L;

        public Pizza(String name, int price_S, int price_M, int price_L) {
            this.name = name;
            this.price_S = price_S;
            this.price_M = price_M;
            this.price_L = price_L;
        }
    }

    public static class OrderItem {
        public String name;
        public String size;
        public int quantity;

        public OrderItem(String name, String size, int quantity) {
            this.name = name;
            this.size = size;
            this.quantity = quantity;
        }
    }

    static class PriceComparator<T> implements Comparator<T> {

        private List<Comparator<T>> listComparators;

        @SafeVarargs
        public PriceComparator(Comparator<T>... comparators) {
            listComparators = Arrays.asList(comparators);
        }

        @Override
        public int compare(T o1, T o2) {
            for (Comparator<T> comparator : listComparators) {
                int result = comparator.compare(o1, o2);
                if (result != 0) {
                    return result;
                }
            }
            return 0;
        }

    }

}