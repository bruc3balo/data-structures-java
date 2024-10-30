import java.util.*;

/// has sorting algorithms///
class Sort {
    public static void main(String[] args) {

//        int[] nums = new int[]{64, 25, 12, 22, 11, 64, 7, 3, 100};
        int[] nums = new int[]{8, 2, 5, 3, 4, 7, 6, 1};

        System.out.println("\n sorted array is: ");

        //bucketSort(new int[]{1,4,1,2,7,5,2,9,10});
        Sort.quickSort(nums);

        System.out.println("\n v : " + Arrays.stream(nums).boxed().toList());

    }


    //O(n2) time
    //O(1) space
    //comparison
    public static int[] bubbleSort(int[] nums) {


        for (int i = 0; i < nums.length; i++)
            for (int j = 0; j < nums.length; j++) {
                //can check for swap
                //current bigger than next
                if (nums[j] > nums[j + 1]) {
                    //swap
                    int temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;
                }

            }


        return nums;
    }

    //O(n2) timeS
    //O(1) space
    //comparison
    public static int[] selectionSort(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {

            ///Step 1: Set current element as minimum
            int min = i;

            //check element to be minimum amongst others
            ///Step 2: Find smallest element in the array from the current element going up
            for (int j = i + 1; j < nums.length; j++) {
                int c = nums[j];

                //if find another min change pointer
                if (c < nums[min]) {
                    min = j;
                }
            }

            //if min has changed swap elements
            ///Step 3: Swap it with left most unsorted element and move sub-list boundaries +1 to right
            if (min != i) {
                //System.out.print("\n Swapping "+nums[i] + " for "+nums[min] +" ,,, ");
                int temp = nums[i];
                nums[i] = nums[min];
                nums[min] = temp;

                // System.out.print("\n Swapped "+nums[i] + " for "+nums[min] + " \n");
            }

            //for (int b : nums) {
            //System.out.print("Step "+i + ":  "+b +" \n");
            // }

        }
        return nums;
    }

    //O(n2) time
    //O(1) space
    //comparison
    public static int[] insertionSort(int[] nums) {

        //Step 5: Repeat until list is sorted
        //start from second element
        for (int i = 1; i < nums.length; i++) {
            //set the key / current element
            //Step 1: Pick an element
            int temp = nums[i];
            int j = i - 1;

            //if left is bigger than right shift
            //current  with previous elements
            ///Step 2: Compare with all elements (in sorted sub-list) on left
            while (j >= 0 && nums[j] > temp) {
                //shift to the right
                //Step 3: Shift all elements in sorted sub-list that is greater (ASC) / lesser (DSCE) than the value to be sorted
                nums[j + 1] = nums[j];
                j--;
            }


            //set left to be the key
            //Step 4: Insert a value
            nums[j + 1] = temp;
        }


        return nums;
    }

    //0(n log n) time
    // O(n2) time worst case
    //O(log n) space
    public static void quickSort(int[] nums) {
        if (nums.length == 0) return;
        int startIndex = 0;
        int endIndex = nums.length - 1;

        quickSort(nums, startIndex, endIndex);
    }

    private static void quickSort(int[] subArray, int startIndex, int endIndex) {

        //array has values i.e. start index has to be less than end index
        if (startIndex >= endIndex) return; //base case

        //find partition index and sort values
        //find a final position of pivot
        int pivot = quickSortPartition(subArray, startIndex, endIndex);

        //leftArray //smaller elements
        quickSort(subArray, startIndex, pivot - 1);

        //RightArray //larger elements
        quickSort(subArray, pivot + 1, endIndex);

    }


    private static int quickSortPartition(int[] nums, int startIndex, int endIndex) {
        //'pivotIndex' is to remember the last position that element was placed in less than pivot

        // first // **last** // random
        //Bad pivot is the largest item / the smallest item in partitioning space i.e. should be middle / median of set of data
        //the value within the partitioning space that I want to find a position for
        int pivotIndex = new Random().nextInt(endIndex - startIndex) + startIndex;
        int pivot = nums[pivotIndex];
        swap(nums, pivotIndex, endIndex);

        int left = startIndex;
        int right = endIndex;

        while (left < right) {
            //smaller than pivot -> left
            while (nums[left] <= pivot && left < right) left++;

            //larger than pivot -> right
            while (nums[right] >= pivot && left < right) right--;

            //swap right with left value
            swap(nums, left, right);
        }

        //swap pivot (last element) with new pivot Index value i.e. larger to right of pivot
        swap(nums, left, endIndex);

        return left;
    }


    private static void swap(int[] nums, int indexA, int indexB) {
        int temp = nums[indexA];
        nums[indexA] = nums[indexB];
        nums[indexB] = temp;
    }


    //0(n log n) time
    //O(n) space
    //O(1) space rarely
    public static int[] mergeSort(int[] nums) {


        //split the arrays in halves recursively
        //Step 1: Divide array by half till arrays have size of 1
        int length = nums.length;

        //Base case
        if (length <= 1) return nums;

        int middle = length / 2;
        int[] leftArray = new int[middle];
        int[] rightArray = new int[length - leftArray.length];

        //split the arrays
        int right = 0;
        for (int left = 0; left < length; left++) {
            if (left < middle) {
                leftArray[left] = nums[left];
                continue;
            }

            rightArray[right] = nums[left];
            right++;
        }

        //recurse left
        //Divide left
        mergeSort(leftArray);

        //recurse right
        //Divide right
        mergeSort(rightArray);

        //Step 2: merge & sort left and right arrays
        merge(leftArray, rightArray, nums);
        return nums;
    }

    private static void merge(int[] leftArray, int[] rightArray, int[] resultArray) {

        //cache left & right lengths
        int leftLength = leftArray.length;
        int rightLength = rightArray.length;
        int originalIndex = 0, leftIndex = 0, rightIndex = 0;

        //conditions for merging
        //while we have elements to merge
        //** compare left & right values in arrays and populate / replace original array **
        //Do till array is full
        while (leftIndex < leftLength && rightIndex < rightLength) {

            //add the smaller value into original array
            //Step 2: Sort the arrays while merging into result array
            //left is smaller than right
            if (leftArray[leftIndex] < rightArray[rightIndex]) {
                resultArray[originalIndex] = leftArray[leftIndex];
                leftIndex++;
            } else {
                //compare right with left
                //right is smaller than left
                resultArray[originalIndex] = rightArray[rightIndex];
                rightIndex++;
            }

            originalIndex++;
        }


        //make sure corner case for no values to compare are dealt with
        //The element that you cannot compare with another
        //smaller first lol
        while (leftIndex < leftLength) {
            resultArray[originalIndex] = leftArray[leftIndex];
            originalIndex++;
            leftIndex++;
        }

        //larger second
        while (rightIndex < rightLength) {
            resultArray[originalIndex] = rightArray[rightIndex];
            originalIndex++;
            rightIndex++;
        }

    }

    //O(n) time
    //O(n) space
    //+ve int
    //when range of value is small
    public static int[] countingSort(int[] nums) {
        int[] result = new int[nums.length];
        int min = 0;
        int max = 0;

        //largest and smallest
        for (int i : nums) {
            max = Math.max(max, i);
            min = Math.min(min, i);
        }

        //Step 1: find k -> AMOUNT OF UNIQUE VALUES
        int k = (max + 1) - min;

        //Step 2: Make array to track occurrences of each value in input array
        int[] counting = new int[k];

        //Step 3: Increment occurrences of array elements i.e. Counting
        for (int i : nums) counting[i] = ++counting[i];

        //Step 4: Running sums of each index i.e. as many occurrences less than and including the value of i
        //At position i, there are counting[i] occurrences of values less than / equals to 'i'
        //counting[i] is the last position i (index [i - 1]) can occur in the output array 'i.e' placements
        int sum = 0;
        for (int i = 0; i < counting.length; i++) {
            sum += counting[i];
            counting[i] = sum;
        }

        //Step 5: Placements , from last of input array
        for (int i = nums.length - 1; i >= 0; i--) {

            //find element to be sorted first
            int elementInserted = nums[i];

            //last position element inserted
            int lastPositionOfElementInserted = counting[elementInserted];

            //Running sums represent the last position that item n can occur in the sorted array
            result[lastPositionOfElementInserted - 1] = elementInserted;

            //decrement occurrence count of value
            counting[elementInserted] = --counting[elementInserted];

        }

        return result;
    }

    //O(n2) / O(n log n) time
    //O(n) space
    public static int[] bucketSort(int[] nums) {
        int[] result = new int[nums.length];
        int min = 0;
        int max = 0;
        HashMap<Integer, List<Integer>> buckets = new HashMap<>();

        //largest and smallest
        for (int i : nums) {
            max = Math.max(max, i);
            min = Math.min(min, i);
        }

        List<Integer> l;
        for (int i : nums) {
            int key = bucketCreationAlgo(i, max);
            l = buckets.getOrDefault(key, new ArrayList<>());
            l.add(i);
            buckets.put(key, l);
        }

        System.out.println(buckets);

        for (Map.Entry<Integer, List<Integer>> e : buckets.entrySet()) {
            int[] countingSort = countingSort(e.getValue().stream().mapToInt(i -> i).toArray());
            e.setValue(Arrays.stream(countingSort).boxed().toList());
        }

        int lastIndex = 0;
        for (int i = 0; i <= 10; i++) {
            List<Integer> list = buckets.getOrDefault(i * 10, new ArrayList<>());

            for (Integer val : list) {
                System.out.print("\n value " + lastIndex + " is " + val);
                result[lastIndex] = val;
                lastIndex++;
            }
        }

        return result;
    }

    private static int bucketCreationAlgo(int n, int max) {
        if (n > max) {
            throw new IllegalStateException("n cannot be greater than max");
        }

        //edge case
        if (n == max) return 100;

        Integer p = (n * 100) / max;
        String s = String.valueOf(p);
        s = s.length() == 1 ? "0".concat(s) : s;
        int f = Integer.parseInt(String.valueOf(s.charAt(0)));

        return f * 10;
    }

    //O d(n) / O(n) time ... where d is max element input has
    // O(n) space
    public static int[] radixSort(int[] nums) {
        int[] result = new int[nums.length];
        HashMap<Integer, Queue<Integer>> buckets = new HashMap<>();

        //step 1: Find max in input array
        int max = 0;

        //largest and smallest
        for (int i : nums) {
            max = Math.max(max, i);
        }


        //step 2: Define 10 queues representing a bucket for each digit from 0 - 9
        for (int i = 0; i < 10; i++) {
            buckets.put(i, new LinkedList<>());
        }

        int leastSignificantSteps = String.valueOf(max).length();
        result = nums;

        //Step 4: Repeat from step 3 till significant digits are over
        for (int step = 0; step < leastSignificantSteps; step++) {
            //Step 3: Consider least significant digit of each no in the input array & insert into respective bucket

            for (int i : result) {
                String s = String.valueOf(i);
                s = s.length() != leastSignificantSteps ? "0".repeat(leastSignificantSteps - s.length()).concat(s) : s;
                int b = Integer.parseInt(String.valueOf(s.charAt(leastSignificantSteps - step - 1)));
                buckets.get(b).offer(i);
            }

            System.out.println("At step " + step + " bucket is " + buckets.toString());

            int lastIndex = 0;
            for (int i = 0; i < 10; i++) {
                Queue<Integer> q = buckets.get(i);
                while (!q.isEmpty()) {
                    result[lastIndex] = q.poll();
                    lastIndex++;
                }
            }

        }


        return result;
    }

    public static int[] heapSort(int[] nums) {
        //heapify array
        MyMinHeap myMinHeap = new MyMinHeap(nums);
        int i = 0;
        int[] result = new int[nums.length];


        Integer val = myMinHeap.extractMin();
        while (val != null) {
            System.out.println("val is " + val);
            result[i] = val;
            i++;
            val = myMinHeap.extractMin();
        }

        return result;
    }

}
