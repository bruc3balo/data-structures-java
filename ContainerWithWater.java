class ContainerWithWater {
    public static void main(String[] args) {
        int[] height = new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7};
        //int[] height = new int[]{1,1};
        //int[] height = new int[]{1,2};
        //int[] height = new int[]{1,2};
        System.out.println("Old Area is " + maxAreaOld(height));
        System.out.println("New Area is " + maxAreaNew(height));
        System.out.println("Expected Area is " + expectedMaxArea(height));

    }

    //O(n)
    public static int maxAreaNew(int[] height) {
        int area = 0;
        int a = 0;
        int b = height.length - 1;

        //start with biggest length i.e. most distance

        while (a < b) {
            int left = height[a];
            int right = height[b];
            int length = b - a;
            int width = Math.min(left, right);
            int newArea = length * width;
            area = Math.max(area, newArea);
            if (left < right) {
                a++;
            } else {
                b--;
            }
        }

        return area;
    }

    //O(n2)
    public static int maxAreaOld(int[] height) {
        int area = 0;
        for (int i = 0; i < height.length; i++) {
            int current = height[i];
            for (int j = i + 1; j < height.length; j++) {
                int compare = height[j];
                int length = Math.abs(i - j);
                int width = Math.min(current, compare);
                int newArea = (length * width);
                area = Math.max(area, newArea);
            }
        }

        return area;
    }

    public static int expectedMaxArea(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int res = 0;
        while (left < right) {
            int containerLength = right - left;
            int area = containerLength * Math.min(height[left], height[right]);
            res = Math.max(res, area);
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return res;
    }

}
