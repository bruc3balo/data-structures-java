class BestStock {
    public static void main(String[] args) {

        int[] prices = new int[]{7, 1, 5, 3, 6, 4};
        System.out.println("Output is " + maxProfit(prices));
    }


    public static int maxProfit(int[] prices) {
        int profit = 0;
        if (prices.length == 1) return 0;

        int buy = 0;

        for (int sell = 1; sell < prices.length; sell++) {
            int s = prices[sell];
            int p = prices[sell] - prices[buy];

            if (prices[buy] < prices[sell]) profit = Math.max(profit, p);
            else buy = sell;
        }

        return profit;
    }
}
