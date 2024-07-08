import java.math.BigInteger;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.Comparator.*;

//arrays and hashing
class Median {
    public static void main(String[] args) {
        //todo
//        int[] nums1 = new int[] {1,2}, nums2 = new int[] {3,4};
        int[] nums1 = new int[]{1}, nums2 = new int[]{2};
//        int[] nums1 = new int[] {}, nums2 = new int[] {1};
//        int[] nums1 = new int[] {3}, nums2 = new int[] {-2,-1};

        System.out.println("finding median \n");
        System.out.println("Neat Median is " + findMedianSortedArrayNeat(nums1, nums2));
        System.out.println("\nBalo Median is " + findMedianSortedArrays(nums1, nums2));
    }

    //log(min(n, m))
    public static double findMedianSortedArrays(int[] a, int[] b) {
        //split into array A and B

        int total = a.length + b.length;

        //find half of the total array
        //make left-partition bigger
//        int half = (int) Math.ceil(total * 0.5);
        int half = (total + 1) / 2;

        //cater for array a being bigger
        // a should is smaller
        if (b.length < a.length) {
            return findMedianSortedArrays(b, a);
        }

        //binary search on smaller i.e. A
        int leftPointer = 0; //index of first value in a
        int rightPointer = a.length; //index of last value in a


        //median condition
        while (true) {
            int midAIndex = rightPointer < 0 ? -1 : (rightPointer + leftPointer) / 2; //a midpoint of array a (in terms of elements)
            int midBIndex = half - midAIndex - 2;
            midBIndex = midBIndex >= 0 ? midBIndex : 0;

            //check if arrays are partitioned correctly
            int aLeftValue = midAIndex >= 0 && a.length > 0 ? a[midAIndex] : Integer.MIN_VALUE;
            int aRightValue = (midAIndex + 1) < a.length ? a[midAIndex + 1] : Integer.MAX_VALUE;

            int bLeftValue = midBIndex >= 0 && b.length > 0 ? b[midBIndex] : Integer.MIN_VALUE;
            int bRightValue = (midBIndex + 1) < b.length ? b[midBIndex + 1] : Integer.MAX_VALUE;

            if (aLeftValue <= bRightValue && bLeftValue <= aRightValue) {

                if (total % 2 == 0) {
                    return (double) (Math.max(aLeftValue, bLeftValue) + Math.min(aRightValue, bRightValue)) / 2;
                } else {
                    return Math.max(aLeftValue, bLeftValue);
                }
            }

            if (aLeftValue > bRightValue) {
                rightPointer = midAIndex - 1;
            } else if (bLeftValue > aRightValue) {
                leftPointer = midAIndex + 1;
            }
        }
    }


    public static double findMedianSortedArrayNeat(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;

        if (m > n) {
            return findMedianSortedArrayNeat(nums2, nums1);
        }

        int total = m + n;
        int half = (total + 1) / 2;

        int left = 0;
        int right = m;

        var result = 0.0;

        while (left <= right) {
            int i = (left + right) / 2;
            int j = half - i;

            // get the four points around possible median
            int left1 = (i > 0) ? nums1[i - 1] : Integer.MIN_VALUE;
            int right1 = (i < m) ? nums1[i] : Integer.MAX_VALUE;
            int left2 = (j > 0) ? nums2[j - 1] : Integer.MIN_VALUE;
            int right2 = (j < n) ? nums2[j] : Integer.MAX_VALUE;

            // partition is correct
            if (left1 <= right2 && left2 <= right1) {
                // even
                if (total % 2 == 0) {
                    result = (Math.max(left1, left2) + Math.min(right1, right2)) / 2.0;
                    // odd
                } else {
                    result = Math.max(left1, left2);
                }
                break;
            }
            // partition is wrong (update left/right pointers)
            else if (left1 > right2) {
                right = i - 1;
            } else {
                left = i + 1;
            }
        }

        return result;
    }

    public static double findMedianSortedArraysBalo(int[] nums1, int[] nums2) {

        //merge
        int[] mergedArray = new int[nums1.length + nums2.length];

        int j = 0;

        for (int i = 0; i < mergedArray.length; i++) {
            if (i < nums1.length) {
                mergedArray[i] = nums1[i];
                continue;
            }

            mergedArray[i] = nums2[j];
            j++;
        }

        //merge sort
        Sort.mergeSort(mergedArray);

        int length = mergedArray.length;

        //find median of sorted array
        if (length % 2 == 0) {
            //even
            // ((length / 2) + ((length / 2) + 1))
            //(mergedArray[length / 2] + mergedArray[((length / 2) + 1)]) / 2;
            //mergedArray[5] + mergedArray[6] = (Answer / 2)
            //median = (middle + (middle + 1)) / 2
            int middle = length / 2;
            return (double) (mergedArray[middle] + mergedArray[middle + 1]) / 2;
        } else {

            //odd
            //median = (length + 1) / 2;
            int median = (length + 1) / 2;
            return mergedArray[median - 1];
        }
    }


}

class TwoSum {

    public static void main(String[] args) {
        int[] nums = new int[]{2, 7, 11, 15};
        int target = 13;
        int[] output = twoSum(nums, target);

        for (int j : output) {
            System.out.println("Output is " + j);
        }

    }

    // O(n)
    public static int[] twoSum(int[] nums, int target) {
        //save complements with their index
        HashMap<Integer, Integer> complementIndicesMap = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int currentVal = nums[i];
            int complement = target - currentVal;

            //check if value exists
            if (complementIndicesMap.containsKey(currentVal)) {
                //return index of complement and current value index
                return new int[]{complementIndicesMap.get(currentVal), i};
            }


            //add complement to map
            complementIndicesMap.put(complement, i);

            //i.e. Value + Complement = target;
            // if you store complement, you need to find value
            // You say if i find your buddy, i will come back to you i.e. I n1 are looking for n2 ... if you find him tell him where i am, here's my location

            //Example
            //int[] nums = new int[]{2,7,11,15};
            //int target = 13;
            //Step 1: val = 2, comp = (13 - 2) = 11, target 13 ... look for 11 ? that is answer i.e (2 + 11 = 13)  : you store 11
            //Step 2: val = 7, comp = (13 - 7) = 6, target 13 ... look for 7 ? that is answer i.e (7 + 6 = 13) : you store 6
            //Step 3: val = 11, comp = (13 - 11) = 2, target 13 ... look for 11 ? that is answer i.e. (11 + 2 = 13) : you store 2
        }

        return new int[]{};
    }
}

class FindDuplicates {
    public static void main(String[] args) {

        int[] nums = new int[]{64, 25, 12, 22, 11, 64};

        // System.out.println("\n Does it have duplicates ? "+hasDuplicates(nums));

        // System.out.println("\n Does it have duplicates hash ? "+hasDuplicatedHashSet(nums));


        System.out.println("\n sorted array is: ");

        //bucketSort(new int[]{1,4,1,2,7,5,2,9,10});
        nums = Sort.bucketSort(nums);
        for (int i : nums) {
            System.out.println("\n v : " + i);
        }
    }

    // O(n)
    private static boolean hasDuplicates(int[] nums) {
        //early exit
        if (nums.length == 1) {
            System.out.print("Early exit");
            return false;
        }

        //edge cases
        //sort
        //selection sort
        //O(n)
        Sort.countingSort(nums);


        System.out.print("\n ");


        //merge sort the array
        //O(log n)


        int previousNum = nums[0];
        //look for O(n)
        for (int i = 1; i < nums.length; i++) {
            int current = nums[i];

            if (current == previousNum) {
                System.out.print("\n Found " + current + " and " + previousNum);
                return true;
            }
            previousNum = nums[i];
        }

        System.out.print("\n Not found");
        return false;
    }

    //O(n)
    private static boolean hasDuplicatedHashSet(int[] nums) {
        HashSet<Integer> seen = new HashSet<>();
        for (int i : nums) {
            if (seen.contains(i)) {
                return true;
            }
            seen.add(i);
        }
        return false;
    }

}

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


    private static void swap (int[] nums, int indexA, int indexB) {
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

class GroupAnagram {

    public static void main(String[] args) {
        String[] strs = new String[]{"eat", "tea", "tan", "ate", "nat", "bat"};
        System.out.println(groupAnagrams(strs));
    }

    //O(mn)
    public static List<List<String>> groupAnagrams(String[] strs) {
        //return
        List<List<String>> res = new ArrayList<>();
        //early exit
        if (strs.length == 0) return res;
        //group anagrams
        HashMap<String, List<String>> map = new HashMap<>();

        // handle each entry
        //O(m)
        for (String s : strs) {
            //hold characters of string
            char[] hash = new char[26];
            //find offset of each character from a

            //O(n) * 26
            for (char c : s.toCharArray()) {
                hash[c - 'a']++;
            }

            //Construct char to string representation of chars
            String key = new String(hash);

            //find if key is absent add string to new list
            map.computeIfAbsent(key, k -> new ArrayList<>());

            //else add new group and add key
            map.get(key).add(s);
        }

        //add list of groups to main group
        res.addAll(map.values());
        return res;
    }

    public static List<List<String>> groupAnagramsAce(String[] strs) {
        HashSet<Integer> foundIndices = new HashSet<>();
        List<List<String>> result = new ArrayList<>();


        for (int i = 0; i < strs.length; i++) {
            if (foundIndices.contains(i)) continue;
            result.add(findAnagramsAce(strs, strs[i], foundIndices));
        }


        return result;
    }

    public static List<String> findAnagramsAce(String[] strs, String s, HashSet<Integer> foundIndices) {

        List<String> list = new ArrayList<>();
        list.add(s);

        String str;
        //assume no duplicates
        //loop


        for (int i = 0; i < strs.length; i++) {

            str = strs[i];

            if (str.equals(s)) continue;

            if (str.length() != s.length()) continue;

            if (foundIndices.contains(i)) continue;

            int[] offsets = new int[26];

            for (int j = 0; j < s.length(); j++) {
                offsets[s.charAt(j) - 'a']++;
                offsets[str.charAt(j) - 'a']--;
            }

            boolean isAnagram = true;

            for (int n : offsets) {
                if (n != 0) isAnagram = false;
            }

            if (!isAnagram) continue;

            //is anagram
            list.add(str);
            foundIndices.add(i);
        }

        return list;
    }

}

class TopKElements {
    public static void main(String[] args) {
        //int[] nums = new int[]{5,5,5,7,7,10};
        int k = 2;
        int[] nums = new int[]{1, 2};
        int[] topKElements = topKFrequenBalo(nums, k);

        for (int i : topKElements) {
            System.out.println(i + ", \n");
        }
    }

    public static int[] topKFrequenBalo(int[] nums, int k) {

        int[] res = new int[k];
        //fast look up time
        //store value & frequency
        HashMap<Integer, Integer> frequenciesMap = new HashMap<>();

        //early exit
        if (nums.length == 1) {
            return nums;
        }

        // O(n)
        for (int i = 0; i < nums.length; i++) {
            int n = nums[i];
            if (frequenciesMap.containsKey(n)) {
                int oldFrequency = frequenciesMap.get(n);
                int newFrequency = ++oldFrequency;

                frequenciesMap.put(n, newFrequency);
            } else {
                frequenciesMap.put(n, 1);
            }
        }

        System.out.println(frequenciesMap.toString());

        //to order frequencies
        final Comparator<Entry<Integer, Integer>> valueComparator = Map.Entry.comparingByValue();

        //to store ordered pairs
        PriorityQueue<Entry<Integer, Integer>> frequecyQueue = new PriorityQueue<>(valueComparator.reversed());

        //O(n)
        for (Entry<Integer, Integer> set : frequenciesMap.entrySet()) {
            frequecyQueue.offer(set);
        }

        int size = frequecyQueue.size();

        //O(n)
        for (int i = 0; i < size; i++) {
            if (i == k) break;
            //O(1)
            res[i] = frequecyQueue.poll().getKey();
        }

        return res;
    }


}

class ProductNotSelf {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 4};
        productExceptSelf(nums);
    }

    //O(n)
    public static int[] productExceptSelf(int[] nums) {
        int[] arr = new int[nums.length];

        //calculate initial values
        int prefix = 1, postfix = 1;

        for (int i = 0; i < nums.length; i++) {
            int n = nums[i];

            //prefix array
            arr[i] = prefix;

            //next iteration calculation
            prefix = n * prefix;
            System.out.println("Store " + arr[i] + " and left is " + prefix);
        }

        for (int i = nums.length - 1; i >= 0; i--) {
            int n = nums[i];

            //postfix array
            arr[i] = arr[i] * postfix;

            //next iteration calculation
            postfix = postfix * n;
        }
        return arr;
    }

}

class ValidSudoku {

    public static void main(String[] args) {

        char[][] board = new char[][]
                {
                        new char[]{'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                        new char[]{'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                        new char[]{'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                        new char[]{'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                        new char[]{'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                        new char[]{'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                        new char[]{'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                        new char[]{'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                        new char[]{'.', '.', '.', '.', '8', '.', '.', '7', '9'},
                };

        System.out.println("is valid " + isValidSudoku(board));
    }

    public static boolean isValidSudoku(char[][] board) {

        HashSet<Character> rowColumnSet = new HashSet<>();
        char empty = '.';

        //validate rows
        for (char[] row : board) {
            rowColumnSet.clear();

            for (char c : row) {
                if (c == empty) continue;

                if (rowColumnSet.contains(c)) {
                    return false;
                }

                rowColumnSet.add(c);
            }

        }

        //validate columns
        for (char[] row : board) {

            //go through each row
            for (int r = 0; r < row.length; r++) {
                rowColumnSet.clear();

                //go through each column in each row
                for (int col = 0; col < row.length; col++) {

                    char c = board[col][r];

                    if (c == empty) continue;

                    if (rowColumnSet.contains(c)) {
                        return false;
                    }

                    rowColumnSet.add(c);
                }
            }

        }

        //validate grid
        //divide into 3 cols
        rowColumnSet.clear();

        //Check boxes
        //Keep track of current row in overall 9x9
        for (int i = 0; i < 9; i += 3) {

            //Keep track of current col in overall 9x9
            for (int j = 0; j < 9; j += 3) {

                //traverse through rows in 3x3 box
                for (int k = i; k < (i + 3); k++) {
                    for (int l = j; l < (j + 3); l++) {

                        char c = board[k][l];

                        if (c == empty) continue;

                        if (rowColumnSet.contains(c)) {
                            return false;
                        }

                        rowColumnSet.add(c);
                    }
                }

                rowColumnSet.clear();
            }

        }

        return true;
    }

    public static boolean isValidSudokuBalo(char[][] board) {

        HashSet<Character> rowColumnSet = new HashSet<>();
        char empty = '.';

        //validate rows
        for (char[] row : board) {
            rowColumnSet.clear();

            for (char c : row) {
                if (c == empty) continue;

                if (rowColumnSet.contains(c)) {
                    return false;
                }

                rowColumnSet.add(c);
            }

        }

        //validate columns
        for (char[] row : board) {

            //go through each row
            for (int r = 0; r < row.length; r++) {
                rowColumnSet.clear();

                //go through each column in each row
                for (int col = 0; col < row.length; col++) {

                    char c = board[col][r];

                    if (c == empty) continue;

                    if (rowColumnSet.contains(c)) {
                        return false;
                    }

                    rowColumnSet.add(c);
                }
            }

        }

        //validate grid
        //divide into 3 cols
        rowColumnSet.clear();

        //Check boxes
        //traverse whole board horizontally


        char[] box1 = new char[]{}, box2 = new char[]{}, box3 = new char[]{};

        for (char[] row : board) {

            //go through each row
            for (int r = 0; r < row.length; r++) {

                //valdate 3 columns

                if (r == 2 || r == 5 || r == 8) rowColumnSet.clear();

                //go through each column in each row
                for (int col = 0; col < row.length; col++) {

                    char c = board[col][r];

                    if (c == empty) continue;

                    if (rowColumnSet.contains(c)) {
                        return false;
                    }

                    rowColumnSet.add(c);
                }
            }


        }
        return false;
    }

}

class LongestStreak {
    public static void main(String[] args) {
        // int[] nums = new int[]{0, 1, 2, 4, 8, 5, 6, 7, 9, 3, 55, 88, 77, 99, 999999999};
        //int[] nums = new int[]{0, 1, 1 ,2};
        int[] nums = new int[]{100, 4, 200, 1, 3, 2};
        System.out.println("Longest streak is " + longestConsecutive(nums));
        System.out.println("Expected streak is " + longestConsecutiveBalo(nums));
    }

    public static int longestConsecutive(int[] nums) {
        if (nums.length == 0) return 0;

        HashSet<Integer> nextValues = new HashSet<>();

        int min = 0;
        int max = 0;

        for (int i : nums) {
            max = Math.max(max, i);
            min = Math.min(min, i);
            nextValues.add(i);
        }

        int streak = 0;
        int mostStreak = 0;

        for (int i = min; i <= max; ++i) {
            if (nextValues.contains(i)) {
                streak++;
                nextValues.remove(i);
            } else {
                streak = 0;
            }

            mostStreak = Math.max(streak, mostStreak);

            nums = Arrays.stream(nums).boxed().distinct().sorted().mapToInt(b -> b).toArray();
            if (nextValues.size() <= mostStreak && streak == 0) break;
        }
        return mostStreak;
    }

    public static int longestConsecutiveBalo(int[] nums) {
        if (nums.length == 0) return 0;
        Arrays.sort(nums);

        int streak = 1;
        int longestStreak = 1;

        for (int i = 0; i < nums.length - 1; i++) {
            int current = nums[i];
            int next = nums[i + 1];

            if (current + 1 == next) {
                streak++;
            } else if (current == next) {
                //do nothing
                continue;
            } else {
                //loop broken
                streak = 1;
            }

            longestStreak = Math.max(longestStreak, streak);
        }

        return longestStreak;
    }
}

//two pointers
class ValidPalindrome {

    public static void main(String[] args) {
        //String s = "race a car";
        String s = "A man, a plan, a canal: Panama";
        System.out.println("is valid " + isPalindrome(s));
    }

    public static boolean isPalindrome(String s) {

        StringBuilder f = new StringBuilder();
        StringBuilder b = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            if (Character.isLetterOrDigit(s.charAt(i))) {
                f.append(String.valueOf(s.charAt(i)).toLowerCase());
            }
        }

        for (int i = s.length() - 1; i >= 0; i--) {
            if (Character.isLetterOrDigit(s.charAt(i))) {
                b.append(String.valueOf(s.charAt(i)).toLowerCase());
            }
        }


        return b.toString().equals(f.toString());
    }

}

class TwoSum2 {
    public static void main(String[] args) {
        int[] numbers = new int[]{2, 3, 4}; //1,3
        int target = 6;
        System.out.println("Output is " + Arrays.stream(twoSum2Pointer(numbers, target)).boxed().toList().toString());
    }

    public static int[] twoSum2(int[] numbers, int target) {

        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < numbers.length; i++) {
            if (map.containsKey(numbers[i])) {
                return new int[]{map.get(numbers[i]) + 1, i + 1};
            }

            map.put(target - numbers[i], i);
        }

        return new int[]{};
    }

    public static int[] twoSum2Pointer(int[] numbers, int target) {
        int p1 = 0;
        int p2 = numbers.length - 1;
        int total;
        for (int i = 0; i < numbers.length; i++) {
            total = numbers[p1] + numbers[p2];

            if (total > target) {
                p2--;
                continue;
            }

            if (total < target) {
                p1++;
                continue;
            }

            return new int[]{++p1, ++p2};
        }

        return new int[]{};
    }

    public static int[] twoSum2Values(int[] numbers, int target) {
        int p1 = 0;
        int p2 = numbers.length - 1;
        int total;
        for (int i = 0; i < numbers.length; i++) {
            total = numbers[p1] + numbers[p2];

            if (total > target) {
                p2--;
                continue;
            }

            if (total < target) {
                p1++;
                continue;
            }

            return new int[]{numbers[p1], numbers[p2]};
        }

        return new int[]{};
    }
}

class ThreeSum {
    public static void main(String[] args) {
        //[[-1,-1,2],[-4,1,3],[-3,1,2],[-1,0,1],[-2,0,2],[-4,0,4]]
        //[[-4, 0, 4],[-4, 1, 3],[-3, -1, 4],[-3,   0, 3],[-3, 1, 2],[-2, -1, 3],[-2, 0, 2],[-1, -1, 2],[-1, 0, 1]]
        int[] nums = new int[]{-1, 0, 1, 2, -1, -4}; //Expect [[1 ,-2,2], [-1,0,1]];
        //int[] nums = new int[]{ 1, 2, -2, -1};
//        int[] nums = new int[]{-1,0,1,2,-1,-4,-2,-3,3,0,4};
        //int[] nums = new int[]{ 0, 0, 0,};
//        int[] nums = new int[]{1,2,-2,-1};
        //int[] nums = new int[]{ 0, 1, 1,};
        //int[] nums = new int[]{82597,-9243,62390,83030,-97960,-26521,-61011,83390,-38677,12333,75987,46091,83794,19355,-71037,-6242,-28801,324,1202,-90885,-2989,-95597,-34333,35528,5680,89093,-90606,50360,-29393,-27012,53313,65213,99818,-82405,-41661,-3333,-51952,72135,-1523,26377,74685,96992,92263,15929,5467,-99555,-43348,-41689,-60383,-3990,32165,65265,-72973,-58372,12741,-48568,-46596,72419,-1859,34153,62937,81310,-61823,-96770,-54944,8845,-91184,24208,-29078,31495,65258,14198,85395,70506,-40908,56740,-12228,-40072,32429,93001,68445,-73927,25731,-91859,-24150,10093,-60271,-81683,-18126,51055,48189,-6468,25057,81194,-58628,74042,66158,-14452,-49851,-43667,11092,39189,-17025,-79173,13606,83172,92647,-59741,19343,-26644,-57607,82908,-20655,1637,80060,98994,39331,-31274,-61523,91225,-72953,13211,-75116,-98421,-41571,-69074,99587,39345,42151,-2460,98236,15690,-52507,-95803,-48935,-46492,-45606,-79254,-99851,52533,73486,39948,-7240,71815,-585,-96252,90990,-93815,93340,-71848,58733,-14859,-83082,-75794,-82082,-24871,-15206,91207,-56469,-93618,67131,-8682,75719,87429,-98757,-7535,-24890,-94160,85003,33928,75538,97456,-66424,-60074,-8527,-28697,-22308,2246,-70134,-82319,-10184,87081,-34949,-28645,-47352,-83966,-60418,-15293,-53067,-25921,55172,75064,95859,48049,34311,-86931,-38586,33686,-36714,96922,76713,-22165,-80585,-34503,-44516,39217,-28457,47227,-94036,43457,24626,-87359,26898,-70819,30528,-32397,-69486,84912,-1187,-98986,-32958,4280,-79129,-65604,9344,58964,50584,71128,-55480,24986,15086,-62360,-42977,-49482,-77256,-36895,-74818,20,3063,-49426,28152,-97329,6086,86035,-88743,35241,44249,19927,-10660,89404,24179,-26621,-6511,57745,-28750,96340,-97160,-97822,-49979,52307,79462,94273,-24808,77104,9255,-83057,77655,21361,55956,-9096,48599,-40490,-55107,2689,29608,20497,66834,-34678,23553,-81400,-66630,-96321,-34499,-12957,-20564,25610,-4322,-58462,20801,53700,71527,24669,-54534,57879,-3221,33636,3900,97832,-27688,-98715,5992,24520,-55401,-57613,-69926,57377,-77610,20123,52174,860,60429,-91994,-62403,-6218,-90610,-37263,-15052,62069,-96465,44254,89892,-3406,19121,-41842,-87783,-64125,-56120,73904,-22797,-58118,-4866,5356,75318,46119,21276,-19246,-9241,-97425,57333,-15802,93149,25689,-5532,95716,39209,-87672,-29470,-16324,-15331,27632,-39454,56530,-16000,29853,46475,78242,-46602,83192,-73440,-15816,50964,-36601,89758,38375,-40007,-36675,-94030,67576,46811,-64919,45595,76530,40398,35845,41791,67697,-30439,-82944,63115,33447,-36046,-50122,-34789,43003,-78947,-38763,-89210,32756,-20389,-31358,-90526,-81607,88741,86643,98422,47389,-75189,13091,95993,-15501,94260,-25584,-1483,-67261,-70753,25160,89614,-90620,-48542,83889,-12388,-9642,-37043,-67663,28794,-8801,13621,12241,55379,84290,21692,-95906,-85617,-17341,-63767,80183,-4942,-51478,30997,-13658,8838,17452,-82869,-39897,68449,31964,98158,-49489,62283,-62209,-92792,-59342,55146,-38533,20496,62667,62593,36095,-12470,5453,-50451,74716,-17902,3302,-16760,-71642,-34819,96459,-72860,21638,47342,-69897,-40180,44466,76496,84659,13848,-91600,-90887,-63742,-2156,-84981,-99280,94326,-33854,92029,-50811,98711,-36459,-75555,79110,-88164,-97397,-84217,97457,64387,30513,-53190,-83215,252,2344,-27177,-92945,-89010,82662,-11670,86069,53417,42702,97082,3695,-14530,-46334,17910,77999,28009,-12374,15498,-46941,97088,-35030,95040,92095,-59469,-24761,46491,67357,-66658,37446,-65130,-50416,99197,30925,27308,54122,-44719,12582,-99525,-38446,-69050,-22352,94757,-56062,33684,-40199,-46399,96842,-50881,-22380,-65021,40582,53623,-76034,77018,-97074,-84838,-22953,-74205,79715,-33920,-35794,-91369,73421,-82492,63680,-14915,-33295,37145,76852,-69442,60125,-74166,74308,-1900,-30195,-16267,-60781,-27760,5852,38917,25742,-3765,49097,-63541,98612,-92865,-30248,9612,-8798,53262,95781,-42278,-36529,7252,-27394,-5021,59178,80934,-48480,-75131,-54439,-19145,-48140,98457,-6601,-51616,-89730,78028,32083,-48904,16822,-81153,-8832,48720,-80728,-45133,-86647,-4259,-40453,2590,28613,50523,-4105,-27790,-74579,-17223,63721,33489,-47921,97628,-97691,-14782,-65644,18008,-93651,-71266,80990,-76732,-47104,35368,28632,59818,-86269,-89753,34557,-92230,-5933,-3487,-73557,-13174,-43981,-43630,-55171,30254,-83710,-99583,-13500,71787,5017,-25117,-78586,86941,-3251,-23867,-36315,75973,86272,-45575,77462,-98836,-10859,70168,-32971,-38739,-12761,93410,14014,-30706,-77356,-85965,-62316,63918,-59914,-64088,1591,-10957,38004,15129,-83602,-51791,34381,-89382,-26056,8942,5465,71458,-73805,-87445,-19921,-80784,69150,-34168,28301,-68955,18041,6059,82342,9947,39795,44047,-57313,48569,81936,-2863,-80932,32976,-86454,-84207,33033,32867,9104,-16580,-25727,80157,-70169,53741,86522,84651,68480,84018,61932,7332,-61322,-69663,76370,41206,12326,-34689,17016,82975,-23386,39417,72793,44774,-96259,3213,79952,29265,-61492,-49337,14162,65886,3342,-41622,-62659,-90402,-24751,88511,54739,-21383,-40161,-96610,-24944,-602,-76842,-21856,69964,43994,-15121,-85530,12718,13170,-13547,69222,62417,-75305,-81446,-38786,-52075,-23110,97681,-82800,-53178,11474,35857,94197,-58148,-23689,32506,92154,-64536,-73930,-77138,97446,-83459,70963,22452,68472,-3728,-25059,-49405,95129,-6167,12808,99918,30113,-12641,-26665,86362,-33505,50661,26714,33701,89012,-91540,40517,-12716,-57185,-87230,29914,-59560,13200,-72723,58272,23913,-45586,-96593,-26265,-2141,31087,81399,92511,-34049,20577,2803,26003,8940,42117,40887,-82715,38269,40969,-50022,72088,21291,-67280,-16523,90535,18669,94342,-39568,-88080,-99486,-20716,23108,-28037,63342,36863,-29420,-44016,75135,73415,16059,-4899,86893,43136,-7041,33483,-67612,25327,40830,6184,61805,4247,81119,-22854,-26104,-63466,63093,-63685,60369,51023,51644,-16350,74438,-83514,99083,10079,-58451,-79621,48471,67131,-86940,99093,11855,-22272,-67683,-44371,9541,18123,37766,-70922,80385,-57513,-76021,-47890,36154,72935,84387,-92681,-88303,-7810,59902,-90,-64704,-28396,-66403,8860,13343,33882,85680,7228,28160,-14003,54369,-58893,92606,-63492,-10101,64714,58486,29948,-44679,-22763,10151,-56695,4031,-18242,-36232,86168,-14263,9883,47124,47271,92761,-24958,-73263,-79661,-69147,-18874,29546,-92588,-85771,26451,-86650,-43306,-59094,-47492,-34821,-91763,-47670,33537,22843,67417,-759,92159,63075,94065,-26988,55276,65903,30414,-67129,-99508,-83092,-91493,-50426,14349,-83216,-76090,32742,-5306,-93310,-60750,-60620,-45484,-21108,-58341,-28048,-52803,69735,78906,81649,32565,-86804,-83202,-65688,-1760,89707,93322,-72750,84134,71900,-37720,19450,-78018,22001,-23604,26276,-21498,65892,-72117,-89834,-23867,55817,-77963,42518,93123,-83916,63260,-2243,-97108,85442,-36775,17984,-58810,99664,-19082,93075,-69329,87061,79713,16296,70996,13483,-74582,49900,-27669,-40562,1209,-20572,34660,83193,75579,7344,64925,88361,60969,3114,44611,-27445,53049,-16085,-92851,-53306,13859,-33532,86622,-75666,-18159,-98256,51875,-42251,-27977,-18080,23772,38160,41779,9147,94175,99905,-85755,62535,-88412,-52038,-68171,93255,-44684,-11242,-104,31796,62346,-54931,-55790,-70032,46221,56541,-91947,90592,93503,4071,20646,4856,-63598,15396,-50708,32138,-85164,38528,-89959,53852,57915,-42421,-88916,-75072,67030,-29066,49542,-71591,61708,-53985,-43051,28483,46991,-83216,80991,-46254,-48716,39356,-8270,-47763,-34410,874,-1186,-7049,28846,11276,21960,-13304,-11433,-4913,55754,79616,70423,-27523,64803,49277,14906,-97401,-92390,91075,70736,21971,-3303,55333,-93996,76538,54603,-75899,98801,46887,35041,48302,-52318,55439,24574,14079,-24889,83440,14961,34312,-89260,-22293,-81271,-2586,-71059,-10640,-93095,-5453,-70041,66543,74012,-11662,-52477,-37597,-70919,92971,-17452,-67306,-80418,7225,-89296,24296,86547,37154,-10696,74436,-63959,58860,33590,-88925,-97814,-83664,85484,-8385,-50879,57729,-74728,-87852,-15524,-91120,22062,28134,80917,32026,49707,-54252,-44319,-35139,13777,44660,85274,25043,58781,-89035,-76274,6364,-63625,72855,43242,-35033,12820,-27460,77372,-47578,-61162,-70758,-1343,-4159,64935,56024,-2151,43770,19758,-30186,-86040,24666,-62332,-67542,73180,-25821,-27826,-45504,-36858,-12041,20017,-24066,-56625,-52097,-47239,-90694,8959,7712,-14258,-5860,55349,61808,-4423,-93703,64681,-98641,-25222,46999,-83831,-54714,19997,-68477,66073,51801,-66491,52061,-52866,79907,-39736,-68331,68937,91464,98892,910,93501,31295,-85873,27036,-57340,50412,21,-2445,29471,71317,82093,-94823,-54458,-97410,39560,-7628,66452,39701,54029,37906,46773,58296,60370,-61090,85501,-86874,71443,-72702,-72047,14848,34102,77975,-66294,-36576,31349,52493,-70833,-80287,94435,39745,-98291,84524,-18942,10236,93448,50846,94023,-6939,47999,14740,30165,81048,84935,-19177,-13594,32289,62628,-90612,-542,-66627,64255,71199,-83841,-82943,-73885,8623,-67214,-9474,-35249,62254,-14087,-90969,21515,-83303,94377,-91619,19956,-98810,96727,-91939,29119,-85473,-82153,-69008,44850,74299,-76459,-86464,8315,-49912,-28665,59052,-69708,76024,-92738,50098,18683,-91438,18096,-19335,35659,91826,15779,-73070,67873,-12458,-71440,-46721,54856,97212,-81875,35805,36952,68498,81627,-34231,81712,27100,-9741,-82612,18766,-36392,2759,41728,69743,26825,48355,-17790,17165,56558,3295,-24375,55669,-16109,24079,73414,48990,-11931,-78214,90745,19878,35673,-15317,-89086,94675,-92513,88410,-93248,-19475,-74041,-19165,32329,-26266,-46828,-18747,45328,8990,-78219,-25874,-74801,-44956,-54577,-29756,-99822,-35731,-18348,-68915,-83518,-53451,95471,-2954,-13706,-8763,-21642,-37210,16814,-60070,-42743,27697,-36333,-42362,11576,85742,-82536,68767,-56103,-63012,71396,-78464,-68101,-15917,-11113,-3596,77626,-60191,-30585,-73584,6214,-84303,18403,23618,-15619,-89755,-59515,-59103,-74308,-63725,-29364,-52376,-96130,70894,-12609,50845,-2314,42264,-70825,64481,55752,4460,-68603,-88701,4713,-50441,-51333,-77907,97412,-66616,-49430,60489,-85262,-97621,-18980,44727,-69321,-57730,66287,-92566,-64427,-14270,11515,-92612,-87645,61557,24197,-81923,-39831,-10301,-23640,-76219,-68025,92761,-76493,68554,-77734,-95620,-11753,-51700,98234,-68544,-61838,29467,46603,-18221,-35441,74537,40327,-58293,75755,-57301,-7532,-94163,18179,-14388,-22258,-46417,-48285,18242,-77551,82620,250,-20060,-79568,-77259,82052,-98897,-75464,48773,-79040,-11293,45941,-67876,-69204,-46477,-46107,792,60546,-34573,-12879,-94562,20356,-48004,-62429,96242,40594,2099,99494,25724,-39394,-2388,-18563,-56510,-83570,-29214,3015,74454,74197,76678,-46597,60630,-76093,37578,-82045,-24077,62082,-87787,-74936,58687,12200,-98952,70155,-77370,21710,-84625,-60556,-84128,925,65474,-15741,-94619,88377,89334,44749,22002,-45750,-93081,-14600,-83447,46691,85040,-66447,-80085,56308,44310,24979,-29694,57991,4675,-71273,-44508,13615,-54710,23552,-78253,-34637,50497,68706,81543,-88408,-21405,6001,-33834,-21570,-46692,-25344,20310,71258,-97680,11721,59977,59247,-48949,98955,-50276,-80844,-27935,-76102,55858,-33492,40680,66691,-33188,8284,64893,-7528,6019,-85523,8434,-64366,-56663,26862,30008,-7611,-12179,-70076,21426,-11261,-36864,-61937,-59677,929,-21052,3848,-20888,-16065,98995,-32293,-86121,-54564,77831,68602,74977,31658,40699,29755,98424,80358,-69337,26339,13213,-46016,-18331,64713,-46883,-58451,-70024,-92393,-4088,70628,-51185,71164,-75791,-1636,-29102,-16929,-87650,-84589,-24229,-42137,-15653,94825,13042,88499,-47100,-90358,-7180,29754,-65727,-42659,-85560,-9037,-52459,20997,-47425,17318,21122,20472,-23037,65216,-63625,-7877,-91907,24100,-72516,22903,-85247,-8938,73878,54953,87480,-31466,-99524,35369,-78376,89984,-15982,94045,-7269,23319,-80456,-37653,-76756,2909,81936,54958,-12393,60560,-84664,-82413,66941,-26573,-97532,64460,18593,-85789,-38820,-92575,-43663,-89435,83272,-50585,13616,-71541,-53156,727,-27644,16538,34049,57745,34348,35009,16634,-18791,23271,-63844,95817,21781,16590,59669,15966,-6864,48050,-36143,97427,-59390,96931,78939,-1958,50777,43338,-51149,39235,-27054,-43492,67457,-83616,37179,10390,85818,2391,73635,87579,-49127,-81264,-79023,-81590,53554,-74972,-83940,-13726,-39095,29174,78072,76104,47778,25797,-29515,-6493,-92793,22481,-36197,-65560,42342,15750,97556,99634,-56048,-35688,13501,63969,-74291,50911,39225,93702,-3490,-59461,-30105,-46761,-80113,92906,-68487,50742,36152,-90240,-83631,24597,-50566,-15477,18470,77038,40223,-80364,-98676,70957,-63647,99537,13041,31679,86631,37633,-16866,13686,-71565,21652,-46053,-80578,-61382,68487,-6417,4656,20811,67013,-30868,-11219,46,74944,14627,56965,42275,-52480,52162,-84883,-52579,-90331,92792,42184,-73422,-58440,65308,-25069,5475,-57996,59557,-17561,2826,-56939,14996,-94855,-53707,99159,43645,-67719,-1331,21412,41704,31612,32622,1919,-69333,-69828,22422,-78842,57896,-17363,27979,-76897,35008,46482,-75289,65799,20057,7170,41326,-76069,90840,-81253,-50749,3649,-42315,45238,-33924,62101,96906,58884,-7617,-28689,-66578,62458,50876,-57553,6739,41014,-64040,-34916,37940,13048,-97478,-11318,-89440,-31933,-40357,-59737,-76718,-14104,-31774,28001,4103,41702,-25120,-31654,63085,-3642,84870,-83896,-76422,-61520,12900,88678,85547,33132,-88627,52820,63915,-27472,78867,-51439,33005,-23447,-3271,-39308,39726,-74260,-31874,-36893,93656,910,-98362,60450,-88048,99308,13947,83996,-90415,-35117,70858,-55332,-31721,97528,82982,-86218,6822,25227,36946,97077,-4257,-41526,56795,89870,75860,-70802,21779,14184,-16511,-89156,-31422,71470,69600,-78498,74079,-19410,40311,28501,26397,-67574,-32518,68510,38615,19355,-6088,-97159,-29255,-92523,3023,-42536,-88681,64255,41206,44119,52208,39522,-52108,91276,-70514,83436,63289,-79741,9623,99559,12642,85950,83735,-21156,-67208,98088,-7341,-27763,-30048,-44099,-14866,-45504,-91704,19369,13700,10481,-49344,-85686,33994,19672,36028,60842,66564,-24919,33950,-93616,-47430,-35391,-28279,56806,74690,39284,-96683,-7642,-75232,37657,-14531,-86870,-9274,-26173,98640,88652,64257,46457,37814,-19370,9337,-22556,-41525,39105,-28719,51611,-93252,98044,-90996,21710,-47605,-64259,-32727,53611,-31918,-3555,33316,-66472,21274,-37731,-2919,15016,48779,-88868,1897,41728,46344,-89667,37848,68092,-44011,85354,-43776,38739,-31423,-66330,65167,-22016,59405,34328,-60042,87660,-67698,-59174,-1408,-46809,-43485,-88807,-60489,13974,22319,55836,-62995,-37375,-4185,32687,-36551,-75237,58280,26942,-73756,71756,78775,-40573,14367,-71622,-77338,24112,23414,-7679,-51721,87492,85066,-21612,57045,10673,-96836,52461,-62218,-9310,65862,-22748,89906,-96987,-98698,26956,-43428,46141,47456,28095,55952,67323,-36455,-60202,-43302,-82932,42020,77036,10142,60406,70331,63836,58850,-66752,52109,21395,-10238,-98647,-41962,27778,69060,98535,-28680,-52263,-56679,66103,-42426,27203,80021,10153,58678,36398,63112,34911,20515,62082,-15659,-40785,27054,43767,-20289,65838,-6954,-60228,-72226,52236,-35464,25209,-15462,-79617,-41668,-84083,62404,-69062,18913,46545,20757,13805,24717,-18461,-47009,-25779,68834,64824,34473,39576,31570,14861,-15114,-41233,95509,68232,67846,84902,-83060,17642,-18422,73688,77671,-26930,64484,-99637,73875,6428,21034,-73471,19664,-68031,15922,-27028,48137,54955,-82793,-41144,-10218,-24921,-28299,-2288,68518,-54452,15686,-41814,66165,-72207,-61986,80020,50544,-99500,16244,78998,40989,14525,-56061,-24692,-94790,21111,37296,-90794,72100,70550,-31757,17708,-74290,61910,78039,-78629,-25033,73172,-91953,10052,64502,99585,-1741,90324,-73723,68942,28149,30218,24422,16659,10710,-62594,94249,96588,46192,34251,73500,-65995,-81168,41412,-98724,-63710,-54696,-52407,19746,45869,27821,-94866,-76705,-13417,-61995,-71560,43450,67384,-8838,-80293,-28937,23330,-89694,-40586,46918,80429,-5475,78013,25309,-34162,37236,-77577,86744,26281,-29033,-91813,35347,13033,-13631,-24459,3325,-71078,-75359,81311,19700,47678,-74680,-84113,45192,35502,37675,19553,76522,-51098,-18211,89717,4508,-82946,27749,85995,89912,-53678,-64727,-14778,32075,-63412,-40524,86440,-2707,-36821,63850,-30883,67294,-99468,-23708,34932,34386,98899,29239,-23385,5897,54882,98660,49098,70275,17718,88533,52161,63340,50061,-89457,19491,-99156,24873,-17008,64610,-55543,50495,17056,-10400,-56678,-29073,-42960,-76418,98562,-88104,-96255,10159,-90724,54011,12052,45871,-90933,-69420,67039,37202,78051,-52197,-40278,-58425,65414,-23394,-1415,6912,-53447,7352,17307,-78147,63727,98905,55412,-57658,-32884,-44878,22755,39730,3638,35111,39777,74193,38736,-11829,-61188,-92757,55946,-71232,-63032,-83947,39147,-96684,-99233,25131,-32197,24406,-55428,-61941,25874,-69453,64483,-19644,-68441,12783,87338,-48676,66451,-447,-61590,50932,-11270,29035,65698,-63544,10029,80499,-9461,86368,91365,-81810,-71914,-52056,-13782,44240,-30093,-2437,24007,67581,-17365,-69164,-8420,-69289,-29370,48010,90439,13141,69243,50668,39328,61731,78266,-81313,17921,-38196,55261,9948,-24970,75712,-72106,28696,7461,31621,61047,51476,56512,11839,-96916,-82739,28924,-99927,58449,37280,69357,11219,-32119,-62050,-48745,-83486,-52376,42668,82659,68882,38773,46269,-96005,97630,25009,-2951,-67811,99801,81587,-79793,-18547,-83086,69512,33127,-92145,-88497,47703,59527,1909,88785,-88882,69188,-46131,-5589,-15086,36255,-53238,-33009,82664,53901,35939,-42946,-25571,33298,69291,53199,74746,-40127,-39050,91033,51717,-98048,87240,36172,65453,-94425,-63694,-30027,59004,88660,3649,-20267,-52565,-67321,34037,4320,91515,-56753,60115,27134,68617,-61395,-26503,-98929,-8849,-63318,10709,-16151,61905,-95785,5262,23670,-25277,90206,-19391,45735,37208,-31992,-92450,18516,-90452,-58870,-58602,93383,14333,17994,82411,-54126,-32576,35440,-60526,-78764,-25069,-9022,-394,92186,-38057,55328,-61569,67780,77169,19546,-92664,-94948,44484,-13439,83529,27518,-48333,72998,38342,-90553,-98578,-76906,81515,-16464,78439,92529,35225,-39968,-10130,-7845,-32245,-74955,-74996,67731,-13897,-82493,33407,93619,59560,-24404,-57553,19486,-45341,34098,-24978,-33612,79058,71847,76713,-95422,6421,-96075,-59130,-28976,-16922,-62203,69970,68331,21874,40551,89650,51908,58181,66480,-68177,34323,-3046,-49656,-59758,43564,-10960,-30796,15473,-20216,46085,-85355,41515,-30669,-87498,57711,56067,63199,-83805,62042,91213,-14606,4394,-562,74913,10406,96810,-61595,32564,31640,-9732,42058,98052,-7908,-72330,1558,-80301,34878,32900,3939,-8824,88316,20937,21566,-3218,-66080,-31620,86859,54289,90476,-42889,-15016,-18838,75456,30159,-67101,42328,-92703,85850,-5475,23470,-80806,68206,17764,88235,46421,-41578,74005,-81142,80545,20868,-1560,64017,83784,68863,-97516,-13016,-72223,79630,-55692,82255,88467,28007,-34686,-69049,-41677,88535,-8217,68060,-51280,28971,49088,49235,26905,-81117,-44888,40623,74337,-24662,97476,79542,-72082,-35093,98175,-61761,-68169,59697,-62542,-72965,59883,-64026,-37656,-92392,-12113,-73495,98258,68379,-21545,64607,-70957,-92254,-97460,-63436,-8853,-19357,-51965,-76582,12687,-49712,45413,-60043,33496,31539,-57347,41837,67280,-68813,52088,-13155,-86430,-15239,-45030,96041,18749,-23992,46048,35243,-79450,85425,-58524,88781,-39454,53073,-48864,-82289,39086,82540,-11555,25014,-5431,-39585,-89526,2705,31953,-81611,36985,-56022,68684,-27101,11422,64655,-26965,-63081,-13840,-91003,-78147,-8966,41488,1988,99021,-61575,-47060,65260,-23844,-21781,-91865,-19607,44808,2890,63692,-88663,-58272,15970,-65195,-45416,-48444,-78226,-65332,-24568,42833,-1806,-71595,80002,-52250,30952,48452,-90106,31015,-22073,62339,63318,78391,28699,77900,-4026,-76870,-45943,33665,9174,-84360,-22684,-16832,-67949,-38077,-38987,-32847,51443,-53580,-13505,9344,-92337,26585,70458,-52764,-67471,-68411,-1119,-2072,-93476,67981,40887,-89304,-12235,41488,1454,5355,-34855,-72080,24514,-58305,3340,34331,8731,77451,-64983,-57876,82874,62481,-32754,-39902,22451,-79095,-23904,78409,-7418,77916};
        long start = System.currentTimeMillis();
        System.out.println("\n Output is " + threeSumAce(nums));
        System.out.println("Time taken is " + (System.currentTimeMillis() - start) + " ms");
        //String s = "[[-4, 0, 4],[-4, 1, 3],[-3, -1, 4],[-3, 0, 3],[-3, 1, 2],[-2, -1, 3],[-2, 0, 2],[-1, -1, 2],[-1, 0, 1]]";

        start = System.currentTimeMillis();
        System.out.println("\n Expected output is " + threeSumTwoSum(nums));
        System.out.println("Time taken is " + (System.currentTimeMillis() - start) + " ms");
    }

    public static List<List<Integer>> threeSumTwoSum(int[] nums) {
        HashSet<List<Integer>> tripletsFound = new HashSet<>();
        Arrays.sort(nums);
        HashSet<List<Integer>> foundForA = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            int a = nums[i];
            // 0 (sum) = a + b + c;
            // 0 - a = b + c;
            //
            int sumToLookFor = -a;

            if (i > 0 && nums[i] == nums[i - 1]) continue;

            while (true) {
                int[] remainingTwo = twoSum(nums, sumToLookFor, i + 1, foundForA);
                if (remainingTwo.length != 0) {
                    //found remaining two
                    foundForA.add(Arrays.stream(remainingTwo).boxed().sorted().toList());
                    int sum = a + remainingTwo[0] + remainingTwo[1];
                    //System.out.println("Sum of a : "+a + " b : "+remainingTwo[0] +" c : "+remainingTwo[1] + " is "+sum);
                    tripletsFound.add(Stream.of(a, remainingTwo[0], remainingTwo[1]).sorted().toList());
                } else {
                    break;
                }
            }

            foundForA.clear();
        }

        return new ArrayList<>(tripletsFound);
    }

    private static int[] twoSum(int[] numbers, int target, int startIndex, HashSet<List<Integer>> foundForA) {

        /*HashMap<Integer, Integer> map = new HashMap<>();

        for(int i = startIndex + 1 ; i < numbers.length; i++) {
            if(map.containsKey(numbers[i])) {
                int a = numbers[map.get(numbers[i])];
                int b = numbers[i];
                int[] result = {Math.min(a, b), Math.max(a, b)};
                if(foundForA.contains(List.of(result[0], result[1]))) continue;
                return result;
            }
            map.put(target - numbers[i], i);
        }*/

        int p1 = startIndex;
        int p2 = numbers.length - 1;
        int total;
        for (int i = startIndex; i < numbers.length; i++) {
            total = numbers[p1] + numbers[p2];

            if (total > target) {
                p2--;
                continue;
            }

            if (total < target) {
                p1++;
                continue;
            }

            if (p1 == p2) {
                return new int[]{};
            }

            int a = numbers[p1];
            int b = numbers[p2];

            //System.out.println("total is "+total);
            //System.out.println("a is index "+p1 +" : and value is "+a);
            //System.out.println("b is index "+p2 +" : and value is "+b);
            //System.out.println("c is index "+startIndex +" : and value is "+numbers[startIndex] + " \n");

            int[] result = {Math.min(a, b), Math.max(a, b)};
            if (foundForA.contains(List.of(result[0], result[1]))) {
                p1++;
                p2--;
                continue;
            }
            return result;
        }


        return new int[]{};
    }

    public static List<List<Integer>> threeSumAce(int[] nums) {
        Arrays.sort(nums);

        HashSet<List<Integer>> foundTriplets = new HashSet<>();
        List<List<Integer>> triplets = new ArrayList<>();
        List<Integer> triplet = new ArrayList<>();

        int complement = 0;
        int[] temp;

        for (int i = 0; i < nums.length; i++) {
            complement = -nums[i];

            temp = TwoSum2.twoSum2Values(nums, complement);

            if (temp.length != 0) {
                triplet.addAll(Stream.of(nums[i], temp[0], temp[1]).sorted().toList());

                if (foundTriplets.add(triplet)) {
                    triplets.add(new ArrayList<>(triplet));
                }

                triplet.clear();
            }
        }

        return triplets;
    }

}

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

//todo sijashika
class TrappingRainWater {
    public static void main(String[] args) {
        int[] h = new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}; // 6
//        int[] h = new int[]{4,2,0,3,2,5}; // 9
        //int[] h = new int[]{4,2,3}; // 1

        //drawContainer(h);
        System.out.println("Water trapped is " + trapO1Space(h));
        System.out.println("Expected is " + trapOnSpace(h));
    }

    public static int trapOnSpace(int[] h) {
        // Min(l,r) - currentHeight -> boundary , water will be contained by minimum height of block
        int units = 0;

        int[] maxL = new int[h.length];
        int[] maxR = new int[h.length];
        int[] min = new int[h.length];

        int maxLeft = 0;
        int maxRight = 0;

        for (int i = 0; i < h.length; i++) {
            maxLeft = Math.max(maxLeft, i == 0 ? 0 : h[i - 1]);
            maxL[i] = maxLeft;
        }

        for (int i = h.length - 1; i >= 0; i--) {
            maxRight = Math.max(maxRight, i == h.length - 1 ? 0 : h[i + 1]);
            maxR[i] = maxRight;
        }

        for (int i = 0; i < h.length; i++) {
            min[i] = Math.min(maxL[i], maxR[i]);
        }


        for (int i = 0; i < h.length; i++) {
            int n = h[i];
            int water = min[i] - n;
            water = water < 1 ? 0 : water;

            //Water unit in position i Min(l,r) - currentHeight
            units += water;
        }


        return units;
    }

    public static int trapO1Space(int[] h) {
        if (h.length == 0) return 0;

        int units = 0;
        int water;

        int left = 0;
        int right = h.length - 1;

        int maxLeft = h[left];
        int maxRight = h[right];

        while (left < right) {
            //shift smaller pointer
            if (maxRight < maxLeft) {
                right--;
                maxRight = Math.max(maxRight, h[right]);
                water = Math.min(maxLeft, maxRight) - h[right];
            } else {
                left++;
                maxLeft = Math.max(maxLeft, h[left]);
                water = Math.min(maxLeft, maxRight) - h[left];
            }
            units += water < 1 ? 0 : water;

        }


        return units;
    }

    public static int trapOld(int[] h) {
        int units = 0;
        int width = 1;
        boolean collect = false;
        Integer leftWall = null;
        Integer rightWall = null;


        for (int i = 0; i < h.length; i++) {
            if (i == h.length - 1 && rightWall == null) continue;


            //reached right wall
            if (rightWall != null && rightWall == i) {
                collect = false;
                int maxWaterHeight = Math.min(h[leftWall], h[rightWall]);
                for (int j = leftWall + 1; j < rightWall; j++) {
                    //times I need to check
                    int waterHeight = maxWaterHeight - h[j];
                    int thisArea = waterHeight * width;
                    units += thisArea;
                }
                rightWall = null;
                leftWall = null;
            }

            //look for wall boundaries
            if (!collect) {
                int[] walls = findLeftWall(i, h);
                if (walls.length == 0) {
                    continue;
                }
                collect = true;
                leftWall = walls[0];
                rightWall = walls[1];
                System.out.println("Left wall is " + leftWall);
                System.out.println("Right wall is " + rightWall);
            }

            /*
                        if(nextHeight != null && height > nextHeight && !collect) {
                            findLeftWall(i,h,leftWall,rightWall);
                            collect = leftWall != null;
                            if(collect) rightWall = null;
                            continue;
                        }

                        if(nextHeight == null || nextHeight > height) {

                            if(nextHeight != null &&  leftWall == null) {
                                findLeftWall(i, h, leftWall, rightWall);
                                collect = leftWall != null;
                                if(collect) rightWall = null;
                                continue;
                            }

                            if(h[leftWall] <= nextHeight && collect) {

                            }

                            System.out.println("==============================");
                        }*/
        }

        return units;
    }

    private static int[] findLeftWall(int current, int[] h) {
        int[] result = new int[]{};
        int possibleLeftWall = h[current];
        int leftWallIndex = current;
        if (current + 1 == h.length) return result;
        int possibleWaterStart = h[current + 1];
        LinkedList<Integer> rightWalls = new LinkedList<>();

        //early exit
        if (possibleWaterStart > possibleLeftWall || possibleLeftWall == 0) return result;
        int waterStartIndex = current + 1;

        //look for right wall
        //j is water initially
        for (int j = current + 1; j < h.length; j++) {

            //after water
            int k = j + 1;
            if (k == h.length) break;
            int possibleRightWall = h[k];
            int possibleRightWallIndex = k;

            //invalidate possible right wall
            if (possibleRightWall < possibleLeftWall && possibleWaterStart > possibleRightWall) continue;
            if (possibleRightWall == 0) continue;
            if (possibleRightWall == waterStartIndex) {
                //left wall must be equal / smaller than right
                if (!(possibleLeftWall <= possibleRightWall)) continue;
            } else if (possibleLeftWall == waterStartIndex) {
                //right wall must be equal / bigger than left
                if (!(possibleRightWall >= possibleLeftWall)) continue;
            } else {
                if (!(possibleRightWall > possibleWaterStart) || !(possibleLeftWall > possibleWaterStart)) continue;
            }

            if (possibleRightWall > 0 && possibleLeftWall > 0) {
                rightWalls.add(k);
            }
        }


        int max = 0;
        Integer index = null;
        for (int w = 0; w < rightWalls.size(); w++) {
            int i = h[w];
            //System.out.println("Options are index : "+w+ " value : "+i + " water is "+possibleWaterStart);
            System.out.println("Left Option : index " + current + " and value : " + h[current]);
            System.out.println("Right Option : index " + w + " and value : " + i);
            System.out.println("Water Level should be " + Math.min(h[current], h[w]) + "\n");
            /*if(w != rightWalls.size() - 1) {
                if(h[w + 1] < )
            }*/
            if (max < i) {
                max = i;
                index = w;
            }
        }
        return index != null ? new int[]{current, index} : result;
    }

    private static void drawContainer(int[] h) {
        HashMap<Integer, List<Integer>> heights = new HashMap<>();
        int maxHeight = 0;

        for (int i = 0; i < h.length; i++) {
            int height = h[i];
            maxHeight = Math.max(height, maxHeight);
            List<Integer> list = heights.getOrDefault(height, new ArrayList<>());
            list.add(i);
            heights.put(height, list);
        }


        String found = "*";
        String Notfound = " ";
        String ground = "_";
        StringBuffer b;
        for (int current = maxHeight; current >= 0; current--) {
            b = new StringBuffer();
            List<Integer> present = heights.getOrDefault(current, new ArrayList<>());
            for (int i = 0; i < h.length; i++) {
                if (current == 0) {
                    b.append(ground);
                    continue;
                }
                b.append(present.contains(i) ? found : Notfound);
            }
            System.out.println(b);

            //System.out.println("\n");
        }


    }

}


class LargestRectangleInHistogram {
    public static void main(String[] args) {
//        int[] heights = new int[]{2,1,5,6,2,3};
        int[] heights = new int[]{2,4};
//        int[] heights = new int[]{2, 1, 2};
        System.out.println(largestRectangleArea(heights));
    }

    public static int largestRectangleArea(int[] heights) {
        int area = 0;
        Stack<Map.Entry<Integer, Integer>> s = new Stack<>();

        //traverse
        for(int i = 0; i < heights.length; i++) {
            Integer h = heights[i];
            Integer start = i;
            while (!s.isEmpty() && s.peek().getValue() > h) {
                var p = s.pop();
                start = p.getKey();
                area = Math.max(area, findArea(i, p.getKey(), p.getValue()));
            }

            s.push(Map.entry(start, h));
        }


        int e = heights.length;
        while(!s.isEmpty()) {
            var p = s.pop();
            area = Math.max(area, findArea(p.getKey(), e, p.getValue()));
        }


        return area;
    }
    
    public static int findArea(int currentIndex, int boundaryIndex, int height) {
        int width = Math.abs(boundaryIndex - currentIndex);
        return width * height;
    }

}


//stack
class ValidSyntax {
    public enum SyntaxElements {

        b1("("),
        b2(")"),

        s1("["),
        s2("]"),

        c1("{"),
        c2("}");

        final String value;

        SyntaxElements(String value) {
            this.value = value;
        }

        static SyntaxElements getByValue(String s) {
            return Arrays.stream(values()).filter(i -> i.value.equals(s)).findFirst().orElse(null);
        }
    }

    public static void main(String[] args) {
        //String s = "(]";
        //String s = "()";
        //String s = "()[]{}";

        String s = "[](([[]]()))";
//        String s = "]";

        System.out.println(" is valid " + isValid(s));
    }

    public static boolean isValid(String s) {
        Stack<String> open = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            SyntaxElements in = SyntaxElements.getByValue(String.valueOf(s.charAt(i)));
            switch (in) {
                case b1, s1, c1 -> open.push(in.value);
                case b2 -> {
                    if (open.isEmpty() || !open.peek().equals(SyntaxElements.b1.value)) return false;
                    open.pop();
                }
                case s2 -> {
                    if (open.isEmpty() || !open.peek().equals(SyntaxElements.s1.value)) return false;
                    open.pop();
                }
                case c2 -> {
                    if (open.isEmpty() || !open.peek().equals(SyntaxElements.c1.value)) return false;
                    open.pop();
                }
            }
        }

        return open.isEmpty();
    }

}

class FindMinStack {
    public static void main(String[] args) {

        MinStack minStack = new MinStack(); //MinStack //null
        minStack.push(2147483646); //push //null
        minStack.push(2147483646); // push //null
        minStack.push(2147483647); // push // null
        System.out.println(minStack.top() + " top" + " :: expected 2147483647"); //top //2147483647
        minStack.pop(); //pop //null
        System.out.println(minStack.getMin() + " min :: expected 2147483646"); //getMin //2147483646
        minStack.pop(); // pop //null
        System.out.println(minStack.getMin() + " min :: expected 2147483646"); //getMin //2147483646
        minStack.pop(); // pop //null
        minStack.push(2147483647); //push //null
        System.out.println(minStack.top() + " top :: expected 2147483647"); //top //2147483647
        System.out.println(minStack.getMin() + " min :: expected 2147483647"); //getMin //2147483647
        minStack.push(-2147483648); //push //null
        System.out.println(minStack.top() + " top :: expected -2147483648"); //top //-2147483648
        System.out.println(minStack.getMin() + " min :: expected -2147483648"); //getMin //-2147483648
        minStack.pop(); // pop // null
        System.out.println(minStack.getMin() + " min expected 2147483647"); //getMin // 2147483647

    }

    static class MinStack {

        private Node top;
        private final Stack<Integer> min;

        public MinStack() {
            min = new Stack<>();
        }

        public void push(int val) {
            Node newTop = new Node(val);
            newTop.setNext(top);
            this.top = newTop;
            min.push(min.isEmpty() ? val : Math.min(min.peek(), val));
        }

        public void pop() {
            this.top = top.hasNext() ? top.getNext() : null;
            this.min.pop();
        }

        public int top() {
            return top.getValue();
        }

        public int getMin() {
            return min.peek();
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            Node n = top;
            sb.append("[");
            appendChildToPrint(sb, n);
            sb.append("]");
            return sb.toString();
        }

        private void appendChildToPrint(StringBuilder sb, Node n) {
            sb.append(" ").append(n.getValue()).append(",");
            if (n.hasNext()) {
                appendChildToPrint(sb, n.getNext());
            }
        }

        private static class Node {
            private Node next;
            private final int value;

            Node(int value) {
                this.value = value;
            }

            public int getValue() {
                return value;
            }

            public boolean hasNext() {
                return next != null;
            }

            public Node getNext() {
                return next;
            }

            public void setNext(Node next) {
                this.next = next;
            }

            public void removeNext() {
                this.next = null;
            }

            @Override
            public String toString() {
                return "Node{" +
                        "next=" + next +
                        ", value=" + value +
                        '}';
            }
        }
    }
}

class ReversePolishNotation {
    public static void main(String[] args) {
        //String[] tokens = new String[]{"2","1","+","3","*"}; //9
        //String[] tokens = new String[]{"4","13","5","/","+"}; //6
        String[] tokens = new String[]{"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"}; //6
        System.out.println("Value is " + evalRPN(tokens));
        System.out.println("\nExpected 9");
    }

    public enum Ops {

        add("+"),
        sub("-"),
        mul("*"),
        div("/");

        private final String op;

        Ops(String op) {
            this.op = op;
        }

        static Ops getByValue(String s) {
            return Arrays.stream(values()).filter(i -> i.op.equals(s)).findFirst().orElse(null);
        }
    }

    //O(n) time
    //O(n) space
    public static int evalRPN(String[] tokens) {
        Stack<String> stack = new Stack<>();
        String toStack = null;

        Integer a;
        Integer b;

        Ops ops;

        for (String current : tokens) {
            if (!isNumber(current)) {

                ops = Ops.getByValue(current);
                b = Integer.parseInt(stack.pop());
                a = Integer.parseInt(stack.pop());

                switch (ops) {
                    case add -> toStack = String.valueOf(a + b);
                    case sub -> toStack = String.valueOf(a - b);
                    case mul -> toStack = String.valueOf(a * b);
                    case div -> toStack = String.valueOf(a / b);
                }

            } else {
                toStack = current;
            }

            stack.push(toStack);
            System.out.println(stack);
        }

        System.out.println(stack.stream().toList());
        System.out.println(stack.peek());

        return Integer.parseInt(stack.pop());
    }

    public static boolean isNumber(String c) {
        try {
            Integer.parseInt(c);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}

class GenerateParentheses {
    public static void main(String[] args) {
        int n = 3;
        //System.out.println("Output is "+generateParenthesis(n));
        System.out.println("Expected output is " + generateParenthesisNeet(n));
    }

    public static List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        gen(0, 2 * n, result, new Stack<>());
        return result;
    }


    private static void gen(int diff, int n, List<String> result, Stack<Character> combinations) {

        if (diff < 0 || diff > n) {
            //base case
            return;
        }

        if (n == 0 && diff == 0) {
            //answer
            var it = combinations.iterator();
            StringBuilder sb = new StringBuilder();
            while(it.hasNext()) sb.append(it.next());
            result.add(sb.toString());
        } else {
            //add opening
            combinations.push('(');
            gen(diff + 1, n - 1, result, combinations);
            combinations.pop();

            //add closing
            combinations.push(')');
            gen(diff - 1, n - 1, result, combinations);
            combinations.pop();
        }

    }

    static Stack<Character> stack = new Stack<>();
    static List<String> res = new ArrayList<>();

    public static List<String> generateParenthesisNeet(int n) {
        backtrack(0, 0, n);
        return res;
    }

    private static void backtrack(int openN, int closedN, int n) {
        if (openN == closedN && closedN == n) {
            Iterator<Character> vale = stack.iterator();
            StringBuilder temp = new StringBuilder();
            while (vale.hasNext()) {
                temp.append(vale.next());
            }
            res.add(temp.toString());
        }
        if (openN < n) {
            stack.push('(');
            backtrack(openN + 1, closedN, n);
            stack.pop();
        }

        if (closedN < openN) {
            stack.push(')');
            backtrack(openN, closedN + 1, n);
            stack.pop();
        }
    }

}

class DailyTemperatures {
    public static void main(String[] args) {
        int[] temperatures = new int[]{73, 74, 75, 71, 69, 72, 76, 73};
        System.out.println("Output is " + Arrays.toString(dailyTemperatures(temperatures)));
        System.out.println("Expected Output is " + Arrays.toString(dailyTemperaturesNeet(temperatures)));
    }

    public static int[] dailyTemperatures(int[] temperatures) {

        //early exit
        if (temperatures.length == 1) return new int[]{0};


        int[] result = new int[temperatures.length];
        //in order
        //day 1

        main:
        for (int i = 0; i < temperatures.length; i++) {
            int day = temperatures[i];

            for (int next = i; next < temperatures.length; next++) {
                int nextDay = temperatures[next];
                if (nextDay > day) {
                    result[i] = next - i;
                    continue main;
                }
            }

            result[i] = 0;
        }


        return result;
    }


    public static int[] dailyTemperaturesNeet(int[] temperatures) {
        int[] ans = new int[temperatures.length];
        Stack<Integer> stack = new Stack<>();
        for (int currDay = 0; currDay < temperatures.length; currDay++) {
            //check previous indexes / days vs currentDay temp difference , if more, fill for result
            while (!stack.isEmpty() && temperatures[currDay] > temperatures[stack.peek()]) {
                int prevDay = stack.pop();
                ans[prevDay] = currDay - prevDay;
            }
            //store the index of previous day on the stack and check it in next iteration
            stack.add(currDay);
        }
        return ans;
    }

}

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


//linked list
class ReverseLinkedList {
    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        int[] arr = new int[]{2, 3, 4, 5};

        ListNode temp = head;
        for (int i : arr) {
            var node = new ListNode(i);
            temp.next = node;
            temp = node;
        }

        reverseListBalo(head);
        reverseListNeet(head);
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        @Override
        public String toString() {
            List<Integer> values = new ArrayList<>();
            var temp = this;
            while (temp != null) {
                values.add(temp.val);
                temp = temp.next;
            }
            return values.toString();
        }

        public ListNode copy() {
            ListNode head;
            ListNode temp = this;
            ListNode newNode = head = null;

            while (temp != null) {
                var node = new ListNode(temp.val);
                if (head == null) {
                    newNode = head = node;
                } else {
                    newNode.next = node;
                    newNode = node;
                }
                temp = temp.next;
            }

            return head;
        }
    }

    public static ListNode insertIntoListNode(int[] arr) {
        ReverseLinkedList.ListNode head;
        ReverseLinkedList.ListNode pointer = head = null;
        for (int i : arr) {
            var node = new ReverseLinkedList.ListNode(i);
            if (head == null) {
                pointer = head = node;
            } else {
                pointer.next = node;
                pointer = node;
            }
        }

        return head;
    }

    public static ListNode reverseListBalo(ListNode head) {

        if (head == null) return null;
        if (head.next == null) return head;

        ListNode next = head;
        Stack<ListNode> nodes = new Stack<>();

        while (next != null) {
            nodes.push(next);
            next = next.next;
        }

        ListNode newHead = new ListNode(nodes.pop().val);
        ListNode temp = newHead;
        while (!nodes.isEmpty()) {
            var node = new ListNode(nodes.pop().val);
            temp.next = node;
            temp = node;
        }

        return newHead;
    }


    //O(n) time
    //O(1) space
    public static ListNode reverseListNeet(ListNode head) {
        ListNode previousPointer = null;
        ListNode currentPointer = head;

        //Reverse linked list
        //previous -> next
        //current -> previous
        //next -> current
        //advance current
        //previous is current before advance
        while (currentPointer != null) {

            //cache next
            var next = currentPointer.next;

            //reverse next pointer to previous node
            currentPointer.next = previousPointer;

            //set previous to be the current pointer
            previousPointer = currentPointer;

            //shift current pointer to next node
            currentPointer = next;
        }

        //previous pointer is equals to head
        return previousPointer;
    }

    //O(n) time
    //O(n) space
    public static ListNode reverseListNeetRecursive(ListNode head) {

        return null;
    }


}

class MergeTwoLinkedLists {
    public static void main(String[] args) {
        //ReverseLinkedList.ListNode list1 = new ReverseLinkedList.insertIntoListNode(new int[]{2});
        ReverseLinkedList.ListNode list1 = ReverseLinkedList.insertIntoListNode(new int[]{1, 2, 4});
        //ReverseLinkedList.ListNode list2 = new ReverseLinkedList.insertIntoListNode(new int[]{1});
        ReverseLinkedList.ListNode list2 = ReverseLinkedList.insertIntoListNode(new int[]{1, 3, 4});

        System.out.println("Output is " + mergeTwoLists(list1, list2).toString());
        System.out.println("Expected Output is " + mergeTwoListsNeet(list1, list2).toString());
    }

    public static ReverseLinkedList.ListNode mergeTwoLists(ReverseLinkedList.ListNode list1, ReverseLinkedList.ListNode list2) {
        if (list1 == null && list2 == null) return null;
        if (list1 == null) return list2;
        if (list2 == null) return list1;


        ReverseLinkedList.ListNode nextList;

        if (list1.val < list2.val) {
            nextList = list1;
            list1 = list1.next;
        } else {
            nextList = list2;
            list2 = list2.next;
        }

        ReverseLinkedList.ListNode head;
        ReverseLinkedList.ListNode nextHead = head = null;


        while (nextList != null) {
            var node = new ReverseLinkedList.ListNode(nextList.val);
            if (nextHead == null) {
                nextHead = head = node;
            } else {
                nextHead.next = node;
                nextHead = node;
            }

            //get next node
            if (list1 == null && list2 == null) break;

            if (list1 == null) {
                nextList = list2;
                list2 = list2.next;
                continue;
            }

            if (list2 == null) {
                nextList = list1;
                list1 = list1.next;
                continue;
            }

            if (list1.val < list2.val) {
                nextList = list1;
                list1 = list1.next;
            } else {
                nextList = list2;
                list2 = list2.next;
            }
        }


        return head;
    }

    public static ReverseLinkedList.ListNode mergeTwoListsNeet(ReverseLinkedList.ListNode list1, ReverseLinkedList.ListNode list2) {
        ReverseLinkedList.ListNode head;

        if (list1 == null && list2 == null) return null;
        if (list1 == null) return list2;
        if (list2 == null) return list1;

        if (list1.val > list2.val) {
            head = list2;
            list2 = list2.next;
        } else {
            head = list1;
            list1 = list1.next;
        }
        head.next = mergeTwoListsNeet(list1, list2);
        return head;

    }

}

class ReorderList {
    public static void main(String[] args) {

        ReverseLinkedList.ListNode original = ReverseLinkedList.insertIntoListNode(new int[]{1, 2, 3, 4});

        System.out.println("Output is " + reorderList(original.copy()));
        System.out.println("Expected output is " + reorderListNeet(original.copy()));
    }

    public static ReverseLinkedList.ListNode reorderList(ReverseLinkedList.ListNode head) {
        if (head == null) return null;
        if (head.next == null) return head;

        //easier to work with indexes & two pointers
        List<ReverseLinkedList.ListNode> listNodes = new ArrayList<>();

        //assign indexes to nodes
        ReverseLinkedList.ListNode temp = head;

        while (temp != null) {
            listNodes.add(new ReverseLinkedList.ListNode(temp.val));
            temp = temp.next;
        }

        //pointer 1 going forward
        int left = 1;

        //pointer 2 going backwards
        int right = listNodes.size() - 1;


        temp = head = new ReverseLinkedList.ListNode(head.val);

        for (int count = 1; count < listNodes.size(); count++) {

            if (count % 2 != 0) {
                //do right
                var node = new ReverseLinkedList.ListNode(listNodes.get(right).val);
                temp.next = node;
                temp = node;
                right--;
            } else {
                //do left
                var node = new ReverseLinkedList.ListNode(listNodes.get(left).val);
                temp.next = node;
                temp = node;
                left++;
            }
        }


        return head;
    }

    public static ReverseLinkedList.ListNode reorderListNeet(ReverseLinkedList.ListNode head) {

        //slow is mid-point of list
        ReverseLinkedList.ListNode slow = head;

        //end is end point of list
        ReverseLinkedList.ListNode fast = head.next;

        //find mid-point and end of list
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }


        ReverseLinkedList.ListNode second = slow.next;

        //end of first list is null //previous = null
        ReverseLinkedList.ListNode prev = slow.next = null;

        //reverse second part of list
        //Reverse linked list
        //previous -> next
        //current -> previous
        //next -> current
        while (second != null) {

            //cache next
            ReverseLinkedList.ListNode tmp = second.next;

            //reverse next pointer to previous node
            second.next = prev;

            //set previous to be the current pointer
            prev = second;

            //shift current pointer to next node
            second = tmp;
        }

        //merge two halves of list
        //head is first node of list
        ReverseLinkedList.ListNode first = head;

        //previous is last node of 2nd list
        second = prev;

        //second may be shorter than first
        while (second != null) {
            //cache links
            ReverseLinkedList.ListNode tmp1 = first.next;
            ReverseLinkedList.ListNode tmp2 = second.next;

            //insert +ve
            first.next = second;

            //insert -ve
            second.next = tmp1;

            //shift pointers for next iteration
            first = tmp1;
            second = tmp2;
        }

        return head;
    }

}

class RemoveNthNode {
    public static void main(String[] args) {
        ReverseLinkedList.ListNode head = ReverseLinkedList.insertIntoListNode(new int[]{1, 2, 3, 4, 5});
//        ReverseLinkedList.ListNode head = ReverseLinkedList.insertIntoListNode(new int[]{1});
//        ReverseLinkedList.ListNode head = ReverseLinkedList.insertIntoListNode(new int[]{1, 2});
        int n = 2;

        ReverseLinkedList.ListNode output = removeNthFromEndBalo(head.copy(), n);
        System.out.println(output == null ? "output is " + new ArrayList<>() : "output is " + output);


        ReverseLinkedList.ListNode exOutput = removeNthFromEndNeet(head.copy(), n);
        System.out.println(exOutput == null ? "expected output is " + new ArrayList<>() : "expected output is " + exOutput);


    }

    public static ReverseLinkedList.ListNode removeNthFromEndBalo(ReverseLinkedList.ListNode head, int n) {
        if (head == null || head.next == null) return null;

        int count = 0;
        ReverseLinkedList.ListNode temp = head;

        while (temp != null) {
            count++;
            temp = temp.next;
        }

        temp = head;
        ReverseLinkedList.ListNode prev = null;
        int current = 1;
        while (temp != null) {
            int toDelete = count - (n - 1);
            if(current == toDelete) {
                if(prev == null) {
                    head = head.next;
                } else {
                    prev.next = prev.next.next;
                }
                break;
            }

            prev = temp;
            temp = temp.next;
            current++;
        }

        return head;
    }

    public static ReverseLinkedList.ListNode removeNthFromEndBalo2(ReverseLinkedList.ListNode head, int n) {
        if (head == null || head.next == null) return null;

        //reverse the linked list
        ReverseLinkedList.ListNode temp = head;
        ReverseLinkedList.ListNode nPointer = temp;
        ReverseLinkedList.ListNode endPointer = temp;
        int counter = 1;

        //offset of n and pointer
        while (counter < n) {
            counter++;
            endPointer = endPointer.next;
        }

        //find nPointer
        while (endPointer.next != null) {
            nPointer = nPointer.next;
            endPointer = endPointer.next;
        }

        nPointer.next = nPointer.next.next;

        return temp.next;
    }

    public static ReverseLinkedList.ListNode removeNthFromEndTry(ReverseLinkedList.ListNode head, int n) {
        if (head == null || head.next == null) return null;

        ArrayList<ReverseLinkedList.ListNode> list = new ArrayList<>();

        //traverse the linked list
        ReverseLinkedList.ListNode pointer = head;
        while (pointer != null) {
            list.add(new ReverseLinkedList.ListNode(pointer.val));
            pointer = pointer.next;
        }

        list.remove(list.size() - n);

        pointer = head = null;
        for (ReverseLinkedList.ListNode nod : list) {
            if (head == null) {
                pointer = head = nod;
            } else {
                pointer.next = nod;
                pointer = nod;
            }
        }

        return head;
    }

    public static ReverseLinkedList.ListNode removeNthFromEndNeet(ReverseLinkedList.ListNode head, int n) {
        if (head == null || head.next == null) return null;

        ReverseLinkedList.ListNode temp = new ReverseLinkedList.ListNode(0);
        temp.next = head;
        ReverseLinkedList.ListNode first = temp, second = temp;

        //run at least once
        while (n > 0) {
            second = second.next;
            n--;
        }

        //break when end list .i.e. last item
        //first is at nth node from last
        while (second.next != null) {
            second = second.next;
            first = first.next;
        }

        //skip next node
        first.next = first.next.next;
        return temp.next;
    }

}

class DeepCopyWithRandom {
    static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }

        public Node deepCopy() {
            HashMap<Node, Node> originalAndCopy = new HashMap<>();
            Node temp = this;


            //copy original side my side with new
            while (temp != null) {
                originalAndCopy.put(temp, new Node(temp.val));
                temp = temp.next;
            }

            temp = this;
            //populate next and random with
            System.out.println(originalAndCopy);
            while (temp != null) {
                //key is copy                    //value is a reference of copy
                originalAndCopy.get(temp).next = originalAndCopy.get(temp.next);
                originalAndCopy.get(temp).random = originalAndCopy.get(temp.random);
                temp = temp.next;
            }

            return originalAndCopy.get(this);
        }

        @Override
        public String toString() {
            List<List<Integer>> values = new ArrayList<>();
            var temp = this;
            while (temp != null) {
                values.add(Arrays.asList(temp.val, temp.random != null ? temp.random.val : null));
                temp = temp.next;
            }

            return values.toString();
        }
    }

    static Node insertListIntoNode(List<List<Integer>> valRandom) {
        Node dummy = new Node(0);
        Node temp = dummy;

        for (List<Integer> ints : valRandom) {
            Integer val = ints.get(0);
            Integer random = ints.get(1);

            var node = new Node(val);
            node.random = random == null ? null : new Node(random);
            temp.next = node;
            temp = node;
        }

        return dummy.next;
    }

    public static void main(String[] args) {
        Node head = insertListIntoNode(List.of(Arrays.asList(7, null), List.of(13, 0), List.of(11, 4), List.of(10, 2), List.of(1, 0)));
        System.out.println("Deep copy is " + head.deepCopy().toString());
    }
}

class AddTwoNumbers {
    public static void main(String[] args) {


        ReverseLinkedList.ListNode l1 = ReverseLinkedList.insertIntoListNode(new int[]{2, 4, 7});
        ReverseLinkedList.ListNode l2 = ReverseLinkedList.insertIntoListNode(new int[]{5, 6, 4});


        //ReverseLinkedList.ListNode l1 = ReverseLinkedList.insertIntoListNode(new int[]{9,9,9,9,9,9,9});
        //ReverseLinkedList.ListNode l2 = ReverseLinkedList.insertIntoListNode(new int[]{9,9,9,9});

        //ReverseLinkedList.ListNode l1 = ReverseLinkedList.insertIntoListNode(new int[]{9});
        //ReverseLinkedList.ListNode l2 = ReverseLinkedList.insertIntoListNode(new int[]{1,9,9,9,9,9,9,9,9,9});

        //ReverseLinkedList.ListNode l1 = ReverseLinkedList.insertIntoListNode(new int[]{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1});
        // ReverseLinkedList.ListNode l2 = ReverseLinkedList.insertIntoListNode(new int[]{5,6,4});


        System.out.println(addTwoNumbers(l1, l2).toString());

    }

    public static ReverseLinkedList.ListNode addTwoNumbersBalo(ReverseLinkedList.ListNode l1, ReverseLinkedList.ListNode l2) {
        BigInteger num1, num2;
        StringBuilder num1s = new StringBuilder(), num2s = new StringBuilder();

        ReverseLinkedList.ListNode temp = l1;

        while (temp != null) {
            num1s.append(temp.val);
            temp = temp.next;
        }

        temp = l2;
        while (temp != null) {
            num2s.append(temp.val);
            temp = temp.next;
        }

        String n1 = num1s.toString();
        String n2 = num2s.toString();

        num1s.setLength(0);
        num2s.setLength(0);

        for (int i = n1.length() - 1; i >= 0; i--) {
            String n = String.valueOf(n1.charAt(i));
            num1s.append(n);
        }

        for (int i = n2.length() - 1; i >= 0; i--) {
            String n = String.valueOf(n2.charAt(i));
            num2s.append(n);
        }

        num1 = new BigInteger(num1s.toString());
        System.out.println(num1s + " is 1");
        num2 = new BigInteger(num2s.toString());
        System.out.println(num2s + " is 2");

        BigInteger sum = num1.add(num2);

        num1s.setLength(0);
        n1 = String.valueOf(sum);

        ReverseLinkedList.ListNode head = temp = new ReverseLinkedList.ListNode();

        for (int i = n1.length() - 1; i >= 0; i--) {
            String n = String.valueOf(n1.charAt(i));
            var node = new ReverseLinkedList.ListNode(new BigInteger(n).intValue());
            temp.next = node;
            temp = node;
        }

        return head.next;
    }

    public static ReverseLinkedList.ListNode addTwoNumbers(ReverseLinkedList.ListNode first, ReverseLinkedList.ListNode second) {
        int carry = 0;
        int remainder = 0;
        int sum = 0;
        ReverseLinkedList.ListNode head;
        ReverseLinkedList.ListNode temp = head = null;

        //add each of the pair of linked lists
        while (first != null || second != null) {
            //extract values
            var v1 = first != null ? first.val : 0;
            var v2 = second != null ? second.val : 0;

            //perform sum
            sum = v1 + v2 + carry;

            //get remainder and carry or sum of current pair
            remainder = sum % 10;
            carry = sum / 10;

            //create result node for pair
            ReverseLinkedList.ListNode newNode = new ReverseLinkedList.ListNode(remainder);

            if (head == null) {
                temp = head = newNode;
            } else {
                //insert into result node
                while (temp.next != null) {
                    temp = temp.next;
                }

                temp.next = newNode;
                newNode.next = null;
            }

            //advance next first node
            if (first != null) {
                first = first.next;
            }

            //advance next second node
            if (second != null) {
                second = second.next;
            }

        }

        //add the carry for the nodes
        if (carry > 0) {
            ReverseLinkedList.ListNode newNode = new ReverseLinkedList.ListNode(carry);
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
            newNode.next = null;
        }
        return head;
    }

}

class LinkedListCycle {
    public static void main(String[] args) {
        ReverseLinkedList.ListNode head = ReverseLinkedList.insertIntoListNode(new int[]{3, 2, 0, -4});
        System.out.println("Output is " + hasCycle(head));
        System.out.println("Expected Output is " + hasCycleNeet(head));
    }

    public static boolean hasCycle(ReverseLinkedList.ListNode head) {
        HashSet<ReverseLinkedList.ListNode> previousNodes = new HashSet<>();
        ReverseLinkedList.ListNode temp = head;
        while (temp != null) {
            if (previousNodes.contains(temp)) {
                return true;
            }

            previousNodes.add(temp);
            temp = temp.next;
        }
        return false;
    }


    //Floyd's tortoise and hare
    public static boolean hasCycleNeet(ReverseLinkedList.ListNode head) {
        ReverseLinkedList.ListNode fast = head;
        ReverseLinkedList.ListNode slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            //fast and slow meet if there's a cycle
            if (fast == slow) return true;
        }
        return false;
    }

}

class FindTheDuplicateNumber {
    public static void main(String[] args) {
//        int[] nums = new int[]{1,3,4,2,2};
//        int[] nums = new int[]{3,1,3,4,2};
//        int[] nums = new int[]{4,3,1,4,2};
        int[] nums = new int[]{9, 4, 9, 5, 7, 2, 8, 9, 3, 9};
        //System.out.println("output is "+ findDuplicateFloydBalo(nums));
        System.out.println("Expected output is " + findDuplicate(nums));
        System.out.println("Expected output is " + findDuplicateNeet(nums));
    }

    //O(n2) time
    //O(1) space
    public static int findDuplicateBalo(int[] nums) {
        int repeated = 0;
        for (int i = 0; i < nums.length; i++) {
            int n = nums[i];

            for (int j = i + 1; j < nums.length; j++) {
                int r = nums[j];

                if (r == n) return n;
            }
        }
        return repeated;
    }

    //O(1) space
    //O(n) time
    public static int findDuplicateFloydBalo(int[] nums) {
        //like counting sort
        //each value will point to another index in the array i.e. non of values are less than 1 or more than nums.length
        //none will point at index 0
        //min number is 1;
        //max number is n = nums.length - 1;
        //n + 1 = nums.length
        int slow = 0;
        int fast = 1;

        while (nums[slow] != nums[fast] || slow == fast) {
            slow = slow + 1 >= nums.length ? 1 : slow + 1;
            fast = fast + 2 >= nums.length ? 1 : fast + 2;
        }

        return nums[slow];
    }

    public static int findDuplicate(int[] nums) {
        ///Floyd's hare and tortoise algorithm
        //Conditions:
        //1. min number is 1;
        //2. max number is n = nums.length - 1;
        //3. n + 1 = nums.length
        //Only 1 duplicate

        //x -> distance before beginning of cycle i.e. x =k3l + z  ... 3 is subscript
        //y -> beginning of cycle to meeting point
        //z -> meeting point to rest of cycle
        //l -> length of cycle i.e. y + z
        //k -> no of loops
        //hare travel distance = x + kl + y
        //tortoise travels distance = x + 2kl + y
        //hare = 2 * tortoise


        int hare = nums[0];
        int tortoise = nums[0];
        //find point of intersection
        //y = z; i.e. p is distance between intersection and start a.k.a. 0
        //l + z = cycle i.e. z remainder point of intersection to cycle, l length of cycle
        //advance tortoise by 1 and hare by 2 till they meet
        ///2.tortoise = hare  ///hare -> y + 2l - x /// i.e. y = z


        do {
            tortoise = nums[tortoise];
            hare = nums[nums[hare]];
        } while (hare != tortoise);

        //create tortoise 2 variable for both tortoise to meet
        //they meet at beginning of cycle -> y
        //keep shifting by 1 till meet
        //tortoise is m here

        hare = nums[0];
        while (hare != tortoise) {

            //one node at a time
            hare = nums[hare];
            tortoise = nums[tortoise];

            //point of intersection is always going to be result
           // if (hare == tortoise) return tortoise;
        }
        //always meet at the entry point
        return tortoise;
    }

    public static int findDuplicateNeet(int[] nums) {
        //min number is 1;
        //max number is n = nums.length - 1;
        //n + 1 = nums.length

        int fast = nums[0];
        int slow = nums[0];
        boolean first = true;
        //find point of intersection
        //p = x; i.e. p is distance between intersection and start a.k.a. 0
        //c + x = cycle i.e. x remainder point of intersection to cycle, c length of cycle
        //advance slow by 1 and fast by 2 till they meet
        ///2.slow = fast  ///fast -> p + 2c - x /// i.e. p = x


        while (first || fast != slow) {
            if (first) first = false;
            slow = nums[slow];
            fast = nums[nums[fast]];
            if (fast == slow) break;
        }

        //create slow 2 variable for both slow to meet
        //keep shifting by 1 till meet
        int slow2 = nums[0];
        while (slow2 != slow) {
            if (first) first = false;
            slow2 = nums[slow2];
            slow = nums[slow];
            //point of intersection is always going to be result
            if (slow2 == slow) return slow;
        }
        return slow;
    }
}

class LRUCacheDS {

    static ArrayList<List<Integer>> events = new ArrayList<>();
    static ArrayList<List<Integer>> expectedEvents = new ArrayList<>();

    public static void main(String[] args) {
        LRUCache lruCache = new LRUCache(2); // null
        lruCache.put(1, 1); // null
        lruCache.put(2, 2); // null
        lruCache.get(1); //1
        lruCache.put(3, 3); //3
        lruCache.get(2);
        lruCache.put(4, 4);
        lruCache.get(1);
        lruCache.get(3);
        lruCache.get(4);

        System.out.println("Events " + events);


        //["LRUCache","put","put","put","put","put","get","put","get","get","put","get","put","put","put","get","put","get","get","get","get","put","put","get","get","get","put","put","get","put","get","put","get","get","get","put","put","put","get","put","get","get","put","put","get","put","put","put","put","get","put","put","get","put","put","get","put","put","put","put","put","get","put","put","get","put","get","get","get","put","get","get","put","put","put","put","get","put","put","put","put","get","get","get","put","put","put","get","put","put","put","get","put","put","put","get","get","get","put","put","put","put","get","put","put","put","put","put","put","put"]
        //[[10],[10,13],[3,17],[6,11],[10,5],[9,10],[13],[2,19],[2],[3],[5,25],[8],[9,22],[5,5],[1,30],[11],[9,12],[7],[5],[8],[9],[4,30],[9,3],[9],[10],[10],[6,14],[3,1],[3],[10,11],[8],[2,14],[1],[5],[4],[11,4],[12,24],[5,18],[13],[7,23],[8],[12],[3,27],[2,12],[5],[2,9],[13,4],[8,18],[1,7],[6],[9,29],[8,21],[5],[6,30],[1,12],[10],[4,15],[7,22],[11,26],[8,17],[9,29],[5],[3,4],[11,30],[12],[4,29],[3],[9],[6],[3,4],[1],[10],[3,29],[10,28],[1,20],[11,13],[3],[3,12],[3,8],[10,9],[3,26],[8],[7],[5],[13,17],[2,27],[11,15],[12],[9,19],[2,15],[3,16],[1],[12,17],[9,1],[6,19],[4],[5],[5],[8,1],[11,7],[5,2],[9,28],[1],[2,2],[7,4],[4,22],[7,24],[9,26],[13,28],[11,26]]
        //Out [null,null,null,null,null,null,-1,null,19,17,null,-1,null,null,null,-1,null,-1,5,-1,12,null,null,3,5,5,null,null,1,null,-1,null,30,5,30,null,null,null,-1,null,-1,24,null,null,18,null,null,null,null,14,null,null,18,null,null,-1,null,null,null,null,null,18,null,null,24,null,4,29,30,null,12,-1,null,null,null,null,29,null,null,null,null,17,22,18,null,null,null,24,null,null,null,20,null,null,null,29,18,18,null,null,null,null,20,null,null,null,null,null,null,null]
        //Ex [null,null,null,null,null,null,-1,null,19,17,null,-1,null,null,null,-1,null,-1,5,-1,12,null,null,3,5,5,null,null,1,null,-1,null,30,5,30,null,null,null,-1,null,-1,24,null,null,18,null,null,null,null,-1,null,null,18,null,null,-1,null,null,null,null,null,18,null,null,-1,null,4,29,30,null,12,-1,null,null,null,null,29,null,null,null,null,17,22,18,null,null,null,-1,null,null,null,20,null,null,null,-1,18,18,null,null,null,null,20,null,null,null,null,null,null,null]

        LRUCacheNeet lruCacheNeet = new LRUCacheNeet(2);
        lruCacheNeet.put(1, 1); // null
        lruCacheNeet.put(2, 2); // null
        lruCacheNeet.get(1); //1
        lruCacheNeet.put(3, 3); //3
        lruCacheNeet.get(2);
        lruCacheNeet.put(4, 4);
        lruCacheNeet.get(1);
        lruCacheNeet.get(3);
        lruCacheNeet.get(4);

        System.out.println("Expected " + expectedEvents);
    }

    private static class LRUCache {

        int capacity;
        int size;
        final LinkedList<Integer> cache = new LinkedList<>();
        final HashMap<Integer, Integer> allValues = new HashMap<>();

        public LRUCache(int capacity) {
            this.capacity = capacity;
            this.size = 0;
            events.add(List.of());
        }

        public int get(int key) {
            Integer value = allValues.get(key);
            if (value == null) {
                events.add(List.of(-1));
                return -1;
            }
            registerUsage(key);
            events.add(List.of(value));
            return value;
        }

        public void put(int key, int value) {
            if (!allValues.containsKey(key)) {
                allValues.put(key, value);
                size++;
                cache.add(key);
                checkCapacity();
            } else {
                allValues.put(key, value);
                registerUsage(key);
            }
            events.add(List.of());
        }

        private void checkCapacity() {
            if (size > capacity) {
                if (!cache.isEmpty()) {
                    Node leastUsedNode = cache.leastUsed();
                    if (leastUsedNode != null) {
                        allValues.remove(leastUsedNode.key);
                        cache.removeNode(leastUsedNode);
                        size--;
                    }
                }
            }
        }

        private void registerUsage(int key) {
            Node byKey = cache.findByKey(key);
            cache.moveNodeToTop(byKey);
        }


        private static class Node {
            int key;
            Node next;

            Node(int key) {
                this.key = key;
            }

            Node() {
            }

        }

        private static class LinkedList<T> {
            private Node head;

            LinkedList() {
            }

            public boolean isEmpty() {
                return head == null;
            }

            public Node leastUsed() {
                //is tail
                if (head == null) return null;
                var temp = head;
                while (temp.next != null) {
                    temp = temp.next;
                }
                return temp;
            }

            public void add(Integer key) {
                Node node = new Node(key);
                if (head == null) {
                    head = node;
                    return;
                }

                var oldHead = head; //cache old head
                head = node;
                head.next = oldHead;
            }

            public void removeNode(Node node) {
                if (head == null) return;

                Node temp = head;
                Node previous = head;

                while (temp != null) {

                    if (node == temp) {
                        previous.next = temp.next;
                        temp = null;
                        continue;
                    }

                    previous = temp;
                    temp = temp.next;
                }
            }

            private void moveNodeToTop(Node node) {
                if (node == null) return;
                removeNode(node);
                add(node.key);
            }

            public Node findByKey(int key) {
                if (head == null) return null;
                Node temp = head;
                while (temp != null) {
                    if (temp.key == key) return temp;
                    temp = temp.next;
                }

                return null;
            }

        }

    }

    private static class LRUCacheNeet {

        private Map<Integer, NodeNeet> cache;
        private int capacity;

        private NodeNeet left;
        private NodeNeet right;

        public LRUCacheNeet(int capacity) {
            this.capacity = capacity;
            cache = new HashMap<>();

            //left = LRU , right = most recent
            this.left = new NodeNeet(0, 0);
            this.right = new NodeNeet(0, 0);
            this.left.next = this.right;
            this.right.prev = this.left;
            expectedEvents.add(List.of());
        }

        public int get(int key) {
            if (cache.containsKey(key)) {
                remove(cache.get(key));
                insert(cache.get(key));
                expectedEvents.add(List.of(cache.get(key).val));
                return cache.get(key).val;
            } else {
                expectedEvents.add(List.of(-1));
                return -1;
            }
        }

        public void put(int key, int value) {
            if (cache.containsKey(key)) {
                remove(cache.get(key));
            }
            cache.put(key, new NodeNeet(key, value));
            insert(cache.get(key));

            if (cache.size() > capacity) {
                // remove from the list and delete the LRU from the hashmap
                NodeNeet lru = this.left.next;
                remove(lru);
                cache.remove(lru.key);
            }

            expectedEvents.add(List.of());

        }

        // remove node from list
        public void remove(NodeNeet nodeNeet) {
            NodeNeet prev = nodeNeet.prev;
            NodeNeet next = nodeNeet.next;

            prev.next = next;
            next.prev = prev;
        }

        // insert node at right
        public void insert(NodeNeet nodeNeet) {
            NodeNeet prev = this.right.prev;
            NodeNeet next = this.right;

            prev.next = nodeNeet;
            next.prev = nodeNeet;

            nodeNeet.next = next;
            nodeNeet.prev = prev;
        }

        private static class NodeNeet {

            private int key;
            private int val;

            NodeNeet next;
            NodeNeet prev;

            public NodeNeet(int key, int val) {
                this.key = key;
                this.val = val;
            }
        }
    }

    class LRUCache2 {

        private final int capacity;
        private final LinkedHashMap<Integer, Integer> cache = new LinkedHashMap<>() {
            @Override
            protected boolean removeEldestEntry(Entry<Integer, Integer> eldest) {
                return size() > capacity;
            }
        };

        public LRUCache2(int capacity) {
            this.capacity = capacity;
        }

        public int get(int key) {
            Integer value = cache.get(key);
            if(value == null) return -1;
            //todo move up
            moveUp(key, value);
            return value;
        }

        public void put(int key, int value) {
            if(cache.containsKey(key)) {
                //update and move up
                moveUp(key, value);
            } else {
                cache.put(key, value);
            }

        }


        private void moveUp(int key, int value) {
            cache.remove(key);
            cache.put(key, value);
        }
    }

}

class MergeKLinkedLists {
    public static void main(String[] args) {

        ReverseLinkedList.ListNode[] lists = new ReverseLinkedList.ListNode[]{ReverseLinkedList.insertIntoListNode(new int[]{1, 4, 5}), ReverseLinkedList.insertIntoListNode(new int[]{1, 3, 4}), ReverseLinkedList.insertIntoListNode(new int[]{2, 6})};

        ReverseLinkedList.ListNode outputListNode = mergeKListsBalo(lists);
        System.out.println("Output is " + outputListNode != null ? outputListNode.toString() : null);

        ReverseLinkedList.ListNode ExListNode = mergeKListsNeet(lists);
        System.out.println("Expected output is " + ExListNode != null ? ExListNode.toString() : null);
    }

    public static ReverseLinkedList.ListNode mergeKListsBalo(ReverseLinkedList.ListNode[] lists) {
        int k = lists.length;
        if (k == 0) return null;
        if (k == 1) return lists[0];

        ArrayList<Integer> countingLists = new ArrayList<>();

        //counting sort
        for (ReverseLinkedList.ListNode list : lists) {
            ReverseLinkedList.ListNode temp = list;
            while (temp != null) {
                countingLists.add(temp.val);
                temp = temp.next;
            }
        }

        int[] sorted = Sort.mergeSort(countingLists.stream().mapToInt(i -> i).toArray());

        return ReverseLinkedList.insertIntoListNode(sorted);
    }

    public static ReverseLinkedList.ListNode mergeKListsNeet(ReverseLinkedList.ListNode[] lists) {
        Queue<Integer> minHeap = new PriorityQueue<>();

        for (ReverseLinkedList.ListNode nodes : lists) {
            ReverseLinkedList.ListNode current = nodes;
            while (current != null) {
                minHeap.add(current.val);
                current = current.next;
            }
        }

        ReverseLinkedList.ListNode dummy = new ReverseLinkedList.ListNode(0);
        ReverseLinkedList.ListNode current = dummy;

        while (!minHeap.isEmpty()) {
            current.next = new ReverseLinkedList.ListNode(minHeap.poll());
            current = current.next;
        }

        return dummy.next;
    }
}

class ReverseKLinkedLists  {
    public static void main(String[] args) {
        ReverseLinkedList.ListNode input = ReverseLinkedList.insertIntoListNode(new int[]{1, 2, 3, 4, 5});
        int k = 1;
        //reverseLinkedList(input,2);
        System.out.println("Output is " + reverseKGroupBalo(input, k));
        //System.out.println("Expected output is [2,1,4,3,5]");
    }

    public static ReverseLinkedList.ListNode reverseKGroupBalo(ReverseLinkedList.ListNode head, int k) {
        if (head == null) return null;
        if (head.next == null) return head;
        ReverseLinkedList.ListNode temp = head;

        Stack<ReverseLinkedList.ListNode> reversedPointersStack = new Stack<>();
        ReverseLinkedList.ListNode result = new ReverseLinkedList.ListNode();
        ReverseLinkedList.ListNode tempResult = result;
        int start = 0;
        int end = k;

        int count = 0;
        while (temp != null) {
            count++;
            temp = temp.next;
        }

        temp = head;
        while (temp != null) {

            if (start == end) {
                while (!reversedPointersStack.isEmpty()) {
                    tempResult.next = reversedPointersStack.pop();
                    tempResult = tempResult.next;
                }
                if (!(end + k <= count)) break;
                end += k;
                continue;
            }

            var next = temp.next;
            temp.next = null;
            reversedPointersStack.push(temp);
            temp = next;
            start++;
        }

        while (temp != null) {
            var next = temp.next;
            temp.next = null;
            tempResult.next = temp;
            tempResult = tempResult.next;
            temp = next;
        }

        while (!reversedPointersStack.isEmpty()) {
            tempResult.next = reversedPointersStack.pop();
            tempResult = tempResult.next;
        }

        return result.next;
    }


}

//Trees
class TreeNode {

    private int value;
    TreeNode left;
    TreeNode right;

    public TreeNode() {

    }

    public TreeNode(Integer value) {
        this.value = value;
    }

    boolean hasNoChild() {
        return !hasLeftChild() && !hasRightChild();
    }

    boolean hasLeftChild() {
        return left != null;
    }

    boolean hasRightChild() {
        return right != null;
    }

    public int getValue() {
        return value;
    }

    @Override

    public String toString() {
        return "TreeNode {" +
                "value=" + value +
                ", left=" + left +
                ", right=" + right +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) return false;
        return same(this, ((TreeNode) obj));
    }

    private boolean same(TreeNode pNode, TreeNode qNode) {

        if (pNode == null && qNode == null) return true;
        if (pNode == null || qNode == null) return false;

        if (pNode.getValue() != qNode.getValue()) return false;

        boolean pqLeft = same(pNode.left, qNode.left);
        boolean pqRight = same(pNode.right, qNode.right);

        return pqLeft && pqRight;
    }
}

class MyTree {

    TreeNode root;
    boolean avl = false;
    private int diameter = -1;

    public MyTree(TreeNode root) {
        this.root = root;
    }

    public MyTree(TreeNode root, boolean avl) {
        this.root = root;
        this.avl = avl;
    }

    public void insert(Integer value) {
        if (avl) avlInsert(root, new TreeNode(value));
        else insertNode(value, root);
    }

    //todo
    @SuppressWarnings("SuspiciousNameCombination")
    private TreeNode rightRotate(TreeNode topNode) {
        //T
        //M
        //B


        TreeNode midNode = topNode.left;
        TreeNode bottomNode = midNode.right;

        //right rotation
        midNode.right = topNode;
        //M
            //T
        //B


        topNode.left = bottomNode;
        return midNode;
    }

    //todo
    @SuppressWarnings("SuspiciousNameCombination")
    private TreeNode leftRotate(TreeNode topNode) {
        //T
        //M
        //B
        TreeNode midNode = topNode.right;
        TreeNode bottomNode = midNode.left;

        //left rotation
        midNode.left = topNode;
            //M
        //T
            //B


        topNode.right = bottomNode;
        return midNode;
    }

    @SuppressWarnings("SuspiciousNameCombination")
    private TreeNode avlInsert(TreeNode node, TreeNode newNode) {
        if (node == null) {
            node = newNode;
            return node;
        } //base case

        //left
        if (newNode.getValue() < node.getValue()) node.left = avlInsert(node.left, newNode);

            //right
        else if (newNode.getValue() > node.getValue()) node.right = avlInsert(node.right, newNode);
            //duplicate
        else throw new IllegalStateException("Duplicate values not allowed");

        //balancing
        Imbalance imbalance = Imbalance.whichInsert(newNode, node);

        //assign (broken link node) rotated tree to parent
        switch (imbalance.getRotation()) {
            case LEFT -> {
                return leftRotate(node);
            }
            case RIGHT -> {
                return rightRotate(node);
            }
            case LEFT_RIGHT -> {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }
            case RIGHT_LEFT -> {
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
            case BALANCED -> {
            }
        }
        return node;
    }

    private void insertNode(Integer value, TreeNode parent) {
        //by parent value
        if (parent.getValue() == value) throw new IllegalStateException("Duplicate values not allowed");
        if (value < parent.getValue()) {
            if (parent.hasLeftChild()) insertNode(value, parent.left);
            else parent.left = new TreeNode(value);
        } else {
            if (parent.hasRightChild()) insertNode(value, parent.right);
            else parent.right = new TreeNode(value);
        }
    }

    private void callPreOrderPrint(StringBuffer buffer) {
        preOrderPrint(root, buffer);
    }

    private void preOrderPrint(TreeNode subTree, StringBuffer buffer) {

        //N
        buffer.append(" ").append(subTree.getValue());

        if (subTree.hasNoChild()) return; //base case

        //L
        if (subTree.hasLeftChild()) preOrderPrint(subTree.left, buffer);

        //R
        if (subTree.hasRightChild()) preOrderPrint(subTree.right, buffer);

    }

    private void callInOrderPrint(StringBuffer buffer) {
        inOrderPrint(root, buffer);
    }

    private void inOrderPrint(TreeNode subTree, StringBuffer buffer) {
        //L
        if (subTree.hasLeftChild()) inOrderPrint(subTree.left, buffer);

        //N
        buffer.append(" ").append(subTree.getValue());

        //R
        if (subTree.hasRightChild()) inOrderPrint(subTree.right, buffer);

    }

    private void callPostOrderPrint(StringBuffer buffer) {
        postOrderPrint(root, buffer);
    }

    private void postOrderPrint(TreeNode subTree, StringBuffer buffer) {
        //L
        if (subTree.hasLeftChild()) postOrderPrint(subTree.left, buffer);

        //R
        if (subTree.hasRightChild()) postOrderPrint(subTree.right, buffer);

        //N
        buffer.append(" ").append(subTree.getValue());
    }

    private void callBFS(StringBuffer buffer) {
        if (root == null) throw new NullPointerException("Root is null");

        //myTree
        buffer.append(" ").append(root.getValue());
        bfsPrint(root, buffer);
    }

    private void bfsPrint(TreeNode subTree, StringBuffer buffer) {
        if (subTree == null) return;


        //left child
        if (subTree.hasLeftChild()) {
            buffer.append(" ").append(subTree.left.getValue());
        }

        //right child
        if (subTree.hasRightChild()) {
            buffer.append(" ").append(subTree.right.getValue());
        }

        bfsPrint(subTree.left, buffer);
        bfsPrint(subTree.right, buffer);
    }

    public TreeNode getLowestCommonAncestor(TreeNode p, TreeNode q) {
        if (root == null) throw new NullPointerException("Root is null");
        return lowestCommonAncestor(root, p, q);
    }

    private TreeNode lowestCommonAncestor(TreeNode node, TreeNode p, TreeNode q) {
        if (p.getValue() > node.getValue() && q.getValue() > node.getValue())
            return lowestCommonAncestor(node.right, p, q);
        if (p.getValue() < node.getValue() && q.getValue() < node.getValue())
            return lowestCommonAncestor(node.left, p, q);
        return node;
    }

    public void printLevelOrder(TraversalMethods traversal) {
        if (traversal == null) throw new IllegalStateException("Enter method of transversal");
        StringBuffer buffer = new StringBuffer();

        switch (traversal) {
            case BFS -> callBFS(buffer);
            case PRE_ORDER -> callPreOrderPrint(buffer);
            case IN_ORDER -> callInOrderPrint(buffer);
            case POST_ORDER -> callPostOrderPrint(buffer);
        }

        System.out.println("\n");
        System.out.println(traversal.name() + " : " + buffer);
    }

    public int getTreeHeight() {
        if (root == null) return -1;
        return heightRecursive(root);
    }

    public int getDiameter() {
        diameter = -1;
        diameterDFS(root);
        return diameter;
    }

    private int diameterDFS(TreeNode node) {
        //height of empty is -1 due to adding + 1 which will result to 0
        if (node == null) return -1;

        int left = 1 + diameterDFS(node.left);
        int right = 1 + diameterDFS(node.right);


        int sum = left + right;

        //diameter = (height of left) + (height of right);

        //find longest diameter
        diameter = Math.max(diameter, sum);

        //longest height
        return Math.max(left, right);
    }

    public int getTreeDepth() {
        //to leaf
        return getTreeHeight();
    }

    public int getNodeDepth(TreeNode node) {
        if (root == null || node == null) return -1;
        if (node == root) return 0;
        return depthRecursiveBalo(node, root, 0);
    }

    public int getNodeHeight(TreeNode node) {
        if (node == null || root == null) return -1;
        return maxDepthRecursive(node) + 1;
    }

    private static int heightRecursive(TreeNode node) {
        //height is from bottom to top
        //bottom / leaf should be 0
        //height of left is 0
        if (node == null) return -1; //base case

        int leftHeight = heightRecursive(node.left);
        int rightHeight = heightRecursive(node.right);

        //return the longest height and increment by one
        if (leftHeight > rightHeight) return ++leftHeight;
        return ++rightHeight;
    }

    private int maxDepthRecursive(TreeNode node) {
        //height is from bottom to top
        //bottom / leaf should be 0
        //height of left is 0
        // no of nodes not edged
        if (node == null) return -1; //base case

        int leftHeight = maxDepthRecursive(node.left);
        int rightHeight = maxDepthRecursive(node.right);

        //return the longest height and increment by one
        return Math.max(rightHeight, leftHeight) + 1;
    }

    private int depthRecursiveBalo(TreeNode target, TreeNode temp, int level) {
        if (temp == null) return --level;
        if (temp == target) return level;
        else {
            int left = depthRecursiveBalo(target, temp.left, level + 1);
            if (left != level) return left;
            int right = depthRecursiveBalo(target, temp.right, level + 1);
            if (right != level) return right;
        }
        return --level;
    }

    TreeNode findByValue(Integer value) {
        return search(value, root);
    }

    public void deleteByValue(Integer value) {
        if (value == null) throw new NullPointerException("Value required");
        if (root == null) throw new NullPointerException("Root is null");
        if (avl) root = deleteNodeByValueAvl(value, root);
        else root = deleteNodeByValue(value, root);
    }

    TreeNode search(Integer value, TreeNode subtree) {
        if (subtree == null) return null; //base case

        //found key
        if (subtree.getValue() == value) {
            //base case
            return subtree;
        }

        //no other case
        if (subtree.hasNoChild()) return null; // base case


        return search(value, (subtree.hasLeftChild() && (value <= subtree.left.getValue())) ? subtree.left : subtree.right);
    }

    public TreeNode minValueFromRoot() {
        if (root == null) throw new NullPointerException("Root is null");
        return findMinValRecursive(root, root);
    }

    private TreeNode findMinValRecursive(TreeNode subTree, TreeNode min) {
        //ideally go to extreme left for BTS
        //var current = subtree
        //while(current.left != null) current = current.left;
        //return current;

        //N
        if (subTree.getValue() < min.getValue()) min = subTree;

        if (subTree.hasNoChild()) return min; //base case

        //L
        if (subTree.hasLeftChild()) min = findMinValRecursive(subTree.left, min);

        //R
        if (subTree.hasRightChild()) min = findMinValRecursive(subTree.right, min);

        return min;
    }

    private TreeNode findMaxValRecursive(TreeNode subTree, TreeNode max) {

        //ideally go to extreme right for BTS
        //var current = subtree
        //while(current.right != null) current = current.right;
        //return current;

        //N
        if (subTree.getValue() > max.getValue()) {
            max = subTree;
        }

        if (subTree.hasNoChild()) return max; //base case

        //L
        if (subTree.hasLeftChild()) max = findMaxValRecursive(subTree.left, max);

        //R
        if (subTree.hasRightChild()) max = findMaxValRecursive(subTree.right, max);

        return max;
    }

    private TreeNode minValueFromNode(TreeNode treeNode) {
        if (root == null) throw new NullPointerException("Root is null");
        if (treeNode == null) throw new NullPointerException("Node root is null");
        return findMinValRecursive(treeNode, treeNode);
    }

    public TreeNode maxValueFromRoot() {
        if (root == null) throw new NullPointerException("Root is null");
        return findMaxValRecursive(root, root);
    }

    public TreeNode invertTree(TreeNode root) {
        if (root == null) return null;

        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;

        invertTree(root.left);
        invertTree(root.right);
        return root;
    }

    TreeNode maxValueFromNode(TreeNode treeNode) {
        if (root == null) throw new NullPointerException("Root is null");
        if (treeNode == null) throw new NullPointerException("Node root is null");
        return findMaxValRecursive(treeNode, treeNode);
    }

    public boolean isBalanced() {
        if (root == null) throw new NullPointerException("Root is null");

        int factor = getBalanceFactor(root);

        return Math.abs(factor) <= 1;
    }

    public static int getBalanceFactor(TreeNode n) {
        if (n == null) return -1;

        int left = heightRecursive(n.left);
        int right = heightRecursive(n.right);

        return left - right;
    }

    private TreeNode deleteNodeByValue(Integer value, TreeNode node) {
        if (node == null) return null; //base case

        //node is probably in left node
        if (value < node.getValue()) node.left = deleteNodeByValue(value, node.left);

            //node is probably in right node
        else if (value > node.getValue()) node.right = deleteNodeByValue(value, node.right);

            //found node
        else {
            //delete node by setting parent value to right value of n
            //right first because right is bigger than left
            //parent should be bigger than left
            if (!node.hasLeftChild()) return node.right;

                //delete node by setting parent value to left value of n
                //left second because left is smaller than right
                //parent should be bigger than left
            else if (!node.hasRightChild()) return node.left;

                //delete node with 2 children
            else {
                //get node bigger than left but smaller than right
                TreeNode temp = minValueFromNode(node.right);

                //update value
                var newNode = new TreeNode(temp.getValue());
                newNode.left = node.left;
                newNode.right = node.right;
                node = newNode;

                node.right = deleteNodeByValue(temp.getValue(), node.right);
            }
        }

        return node;
    }

    private TreeNode deleteNodeByValueAvl(Integer value, TreeNode node) {
        if (node == null) return null; //base case

        //node is probably in left node
        if (value < node.getValue()) node.left = deleteNodeByValueAvl(value, node.left);

            //node is probably in right node
        else if (value > node.getValue()) node.right = deleteNodeByValueAvl(value, node.right);

            //found node
        else {
            //delete node by setting parent value to right value of n
            //right first because right is bigger than left
            //parent should be bigger than left
            if (!node.hasLeftChild()) return node.right;

                //delete node by setting parent value to left value of n
                //left second because left is smaller than right
                //parent should be bigger than left
            else if (!node.hasRightChild()) return node.left;

                //delete node with 2 children
            else {
                //get node bigger than left but smaller than right
                TreeNode temp = minValueFromNode(node.right);

                //update value
                var newNode = new TreeNode(temp.getValue());
                newNode.left = node.left;
                newNode.right = node.right;
                node = newNode;

                node.right = deleteNodeByValueAvl(temp.getValue(), node.right);
            }
        }

        Imbalance imbalance = Imbalance.whichDelete(node);

        switch (imbalance.getRotation()) {

            case LEFT -> {
                return leftRotate(node);
            }
            case RIGHT -> {
                return rightRotate(node);
            }
            case LEFT_RIGHT -> {
                node.left = leftRotate(node);
                return rightRotate(node);
            }
            case RIGHT_LEFT -> {
                node.right = rightRotate(node);
                return leftRotate(node);
            }
            case BALANCED -> {
                return node;
            }
        }

        return node;
    }

    public enum TraversalMethods {
        PRE_ORDER,
        IN_ORDER,
        POST_ORDER,
        BFS,
    }

    private enum Heavy {

        LEFT(List.of(Imbalance.LEFT_LEFT, Imbalance.LEFT_RIGHT)),
        RIGHT(List.of(Imbalance.RIGHT_RIGHT, Imbalance.RIGHT_LEFT)),

        BALANCED(null);

        private final List<Imbalance> imbalances;

        Heavy(List<Imbalance> imbalances) {
            this.imbalances = imbalances;
        }

        public List<Imbalance> getImbalances() {
            return imbalances;
        }

        public static Heavy byImbalance(Imbalance imbalance) {
            return Arrays.stream(values()).filter(i -> i.imbalances.contains(imbalance)).findFirst().orElse(null);
        }

        public static Heavy byFactorInsert(Integer balanceFactor) {
            if (balanceFactor == null) throw new NullPointerException("Balance factor missing");
            if (balanceFactor < 2 && balanceFactor > -2) return BALANCED;
            return balanceFactor > 1 ? LEFT : RIGHT;
        }

        public static Heavy byFactorDelete(Integer balanceFactor) {
            if (balanceFactor == null) throw new NullPointerException("Balance factor missing");
            if (2 != Math.abs(balanceFactor)) return BALANCED;
            return balanceFactor == 2 ? LEFT : RIGHT;
        }

    }

    private enum Imbalance {
        LEFT_LEFT(Rotation.RIGHT),
        RIGHT_RIGHT(Rotation.LEFT),
        LEFT_RIGHT(Rotation.LEFT_RIGHT),
        RIGHT_LEFT(Rotation.RIGHT_LEFT),

        BALANCED(Rotation.BALANCED);

        private final Rotation rotation;

        Imbalance(Rotation rotation) {
            this.rotation = rotation;
        }

        public Rotation getRotation() {
            return rotation;
        }

        public static Imbalance whichInsert(TreeNode newNode, TreeNode parent) {
            if (parent == null) throw new NullPointerException("Parent node missing");

            Heavy heavy = Heavy.byFactorInsert(getBalanceFactor(parent));
            switch (heavy) {
                //left will have parent.left
                case LEFT -> {
                    return newNode.getValue() < parent.left.getValue() ? LEFT_LEFT : LEFT_RIGHT;
                }

                //right will have parent.right
                case RIGHT -> {
                    return newNode.getValue() > parent.right.getValue() ? RIGHT_RIGHT : RIGHT_LEFT;
                }

                case BALANCED -> {
                    return BALANCED;
                }

            }
            return BALANCED;
        }

        public static Imbalance whichDelete(TreeNode parent) {
            if (parent == null) throw new NullPointerException("Node missing");

            Heavy heavy = Heavy.byFactorDelete(getBalanceFactor(parent));
            switch (heavy) {
                case LEFT -> {
                    return getBalanceFactor(parent.left) >= 0 ? LEFT_LEFT : LEFT_RIGHT;
                }
                case RIGHT -> {
                    return getBalanceFactor(parent.right) <= -0 ? RIGHT_RIGHT : RIGHT_LEFT;
                }
                case BALANCED -> {
                    return BALANCED;
                }
            }

            return BALANCED;
        }

    }

    private enum Rotation {
        LEFT,
        RIGHT,
        LEFT_RIGHT,
        RIGHT_LEFT,

        BALANCED;
    }

    public static class NotBalancedException extends RuntimeException {
        public NotBalancedException() {
        }
    }

}

class BST {
    public static void main(String[] args) {
        //MyTree myTree = new MyTree(new TreeNode(30));
        //myTree.findByValue(30).value = null;

        //myTree.insert(18);
        //myTree.insert(45);

        //myTree.insert(10);
        // myTree.insert(42);
        //myTree.insert(67);

        //myTree.deleteByValue(45);

        //myTree.printLevelOrder(MyTree.TraversalMethods.BFS);

        //System.out.println("Find by value "+myTree.findByValue(48));

        // System.out.println("Height of tree is "+myTree.getTreeHeight());

        // System.out.println("is balanced "+myTree.isBalanced());

        //  System.out.println("depth of 10 "+myTree.getNodeDepth(myTree.findByValue(10)));


        // System.out.println(myTree);

        MyTree myTree = new MyTree(new TreeNode(6));
        myTree.insert(2);
        myTree.insert(8);
        myTree.insert(0);
        myTree.insert(4);
        myTree.insert(7);
        myTree.insert(9);
        myTree.insert(3);
        myTree.insert(5);

        System.out.println("Diameter is " + myTree.getDiameter());

        System.out.println("Balanced is " + myTree.isBalanced());

        TreeNode p = myTree.findByValue(2);
        TreeNode q = myTree.findByValue(4);

        System.out.println("LCA is " + myTree.getLowestCommonAncestor(p, q));
    }

}

class TreesNeet {

    public static class TreeNodeLeet {
        int val;
        TreeNodeLeet left;
        TreeNodeLeet right;

        TreeNodeLeet() {
        }

        TreeNodeLeet(int val) {
            this.val = val;
        }

        TreeNodeLeet(int val, TreeNodeLeet left, TreeNodeLeet right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }

    }

}

class PrintBFS {
    public static void main(String[] args) {
        MyTree tree = new MyTree(new TreeNode(3));
        tree.insert(9);
        tree.insert(20);
        tree.insert(15);
        tree.insert(7);

        HashMap<Integer, List<Integer>> result = new HashMap<>();

//        result.values().stream().toList()

        levelOrder(tree.root);

        tree.printLevelOrder(MyTree.TraversalMethods.BFS);

        System.out.println(result);
    }

    private static List<List<Integer>> result = new ArrayList<>();

    private static List<List<Integer>> levelOrder(TreeNode root) {
        if (root != null) {
            result.add(List.of(root.getValue()));
            bfs(root);
        }

        return result;
    }

    private static void bfs(TreeNode node) {
        List<Integer> level = new ArrayList<>();
        if (node.left != null) level.add(node.left.getValue());
        if (node.right != null) level.add(node.right.getValue());

        if (node.left != null) bfs(node.left);
        if (node.right != null) bfs(node.right);

        result.add(level);
    }

}

class BTORightSide {
    public static void main(String[] args) {
        result.clear();
        MyTree root = new MyTree(new TreeNode(3));
        root.insert(9);
        root.insert(20);
        root.insert(15);
        root.insert(7);


        System.out.println("output is " + levelOrder(root.root));

        System.out.println("Expected output is " + rightSideViewNeet(root.root));


    }

    private static HashMap<Integer, List<Integer>> result = new HashMap<>();

    public static List<List<Integer>> levelOrder(TreeNode root) {

        int level = 0;

        if (root != null) {
            result.put(level, List.of(root.getValue()));
            bfs(root, level + 1);
        }
        return result.values().stream().toList();
    }

    public static void bfs(TreeNode node, int level) {


        List<Integer> temp = result.get(level);

        List<Integer> list = temp != null ? temp : new ArrayList<>();

        if (node.left != null) list.add(node.left.getValue());

        if (node.right != null) list.add(node.right.getValue());

        if (!list.isEmpty()) result.put(level, list);

        if (node.left != null) bfs(node.left, level + 1);
        if (node.right != null) bfs(node.right, level + 1);
    }


    public static List<Integer> rightSideViewNeet(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        if (root == null) return list;
        bfsNeet(list, root);
        return list;
    }

    public static void bfsNeet(List<Integer> list, TreeNode root) {
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            int levelSize = q.size();
            for (int i = 0; i < levelSize; i++) {
                TreeNode cur = q.poll();
                if (i == 0) list.add(cur.getValue()); //last out
                if (cur.right != null) q.offer(cur.right);
                if (cur.left != null) q.offer(cur.left);
            }
        }
    }
}

class GoodNodes {
    public static void main(String[] args) {
        MyTree tree = new MyTree(new TreeNode(3));
        tree.insert(2);
        tree.insert(4);
        tree.insert(5);
        tree.insert(6);
        tree.insert(7);

        System.out.println("Output is " + goodNodes(tree.root));

        HashMap<Integer, Boolean> v = new HashMap<>();

    }


    private static HashMap<TreeNode, List<Integer>> path = new HashMap<>();
    private static List<Integer> previous = new ArrayList<>();

    private static int goodNodes(TreeNode root) {
        int count = 0;
        if (root == null) return count;
        //root is always good
        //if(root.left == null && root.right == null) return ++count;

        dsf(root);

        for (Map.Entry<TreeNode, List<Integer>> e : path.entrySet()) {
            if (isGoodNode(e.getKey(), e.getValue())) count++;
        }

        return count;
    }

    private static boolean isGoodNode(TreeNode node, List<Integer> nPath) {
        for (int n : nPath) {
            if (n > node.getValue()) {
                return false;
            }
        }

        return true;
    }

    private static void dsf(TreeNode node) {
        if (node == null) return;

        path.put(node, new ArrayList<>(previous));
        previous.add(node.getValue());

        dsf(node.left);
        dsf(node.right);

        if (!previous.isEmpty()) previous.removeIf(i -> i.equals(node.getValue()));
    }


}

class ValidBTS {

    private static MyTree tree = new MyTree(new TreeNode(3));

    public static void main(String[] args) {
        //[3,1,5,0,2,4,6]

        tree.insert(1);
        tree.insert(5);
        tree.insert(0);
        tree.insert(2);
        tree.insert(4);
        tree.insert(6);

        // System.out.println("Output is "+dfsNeet(tree.root,null,null));
        System.out.println("Good nodes " + goodNodes(tree.root, tree.root.getValue()));
    }


    private static boolean dfsNeet(TreeNode node, Integer min, Integer max) {
        //todo bado sijashika
        if (node == null) return true;


        //right site
        //1.Left is less than current
        //2.Right is more than current
        //3.More than root
        //4.Root is min
        if (min != null && node.getValue() <= min) return false;

        //left side
        //1.Left bound is less than current
        //2.Right is more than current
        //3.Less than root
        //4.Root is max
        if (max != null && node.getValue() >= max) return false;


        //left lower bound is
        return dfsNeet(node.left, min, node.getValue()) && dfsNeet(node.right, node.getValue(), max);
    }

    public static int goodNodes(TreeNode node, int max) {

        if (node == null) return 0;

        int x = node.getValue();

        //good node (+1) or bad node (+0)
        int newCount = x >= max ? 1 : 0;

        // count max
        max = Math.max(max, node.getValue());

        //dfs left
        newCount += goodNodes(node.left, max);

        //dfs right
        newCount += goodNodes(node.right, max);

        return newCount;
    }

    private static List<Boolean> validPaths = new ArrayList<>();
    //List<Integer> path = new ArrayList<>();
    private static HashSet<Integer> dupl = new HashSet<>();
    private static TreeNode root;

    public static boolean isValidBST(TreeNode root) {
        if (root == null) return true;
        ValidBTS.root = root;
        dsf(root, true);
        return !validPaths.contains(false);
    }

    public static void dsf(TreeNode node, boolean left) {
        //base case
        if (node == null) return;

        //duplicate
        if (!dupl.add(node.getValue())) validPaths.add(false);

            //compare previous nodes in path
            //else validPaths.add(isValid(node, path, left));
        else validPaths.add(isSubTreeValid(node, left));


        //add node to path for next
        //path.add(node.val);

        //check left child first all the way to the end
        dsf(node.left, node == root ? true : left);

        //then check right child from the end coming up
        dsf(node.right, node == root ? false : left);

        //remove current node from path i.e. latest
        //if(!path.isEmpty()) path.removeIf(i-> i.equals(node.val));
        //if(!path.isEmpty()) path.remove(path.size() - 1);
    }

    public static boolean isSubTreeValid(TreeNode node, boolean isLeft) {
        if (node == null) return true;
        boolean parent = parentTest(node, root, isLeft);
        boolean left = node.left == null || leftTest(node.right, node.left, root, node, node == root || isLeft);
        boolean right = node.right == null || rightTest(node.right, node.left, root, node, node != root && isLeft);

        System.out.print("\n Node : " + node.getValue() + " || parent -> " + parent + " || left -> " + left + " || right -> " + right + " = = = " + (left && right && parent) + " \n");

        return left && right && parent;
    }

    public static boolean parentTest(TreeNode parent, TreeNode root, boolean isLeft) {
        //isRoot test
        if (parent == root) return true;

        int p = parent.getValue();
        int rt = root.getValue();

        //root test
        boolean rootTest = isLeft ? (p < rt) : (p > rt);

        return rootTest;

    }

    public static boolean rightTest(TreeNode right, TreeNode left, TreeNode root, TreeNode parent, boolean isLeft) {
        int r = right.getValue();
        int p = parent.getValue();
        int rt = root.getValue();

        //parent test
        boolean parentTest = r > p;

        //root test
        boolean rootTest = isLeft ? (r < rt) : (r > rt);


        //left test
        boolean leftTest = left == null || (r > left.getValue());

        System.out.print("\n Right Node : " + r + " || Parent -> " + parentTest + " || root -> " + rootTest + " || left -> " + leftTest);

        return parentTest && rootTest && leftTest;
    }

    public static boolean leftTest(TreeNode right, TreeNode left, TreeNode root, TreeNode parent, boolean isLeft) {
        int l = left.getValue();
        int p = parent.getValue();
        int rt = root.getValue();

        //parent test
        boolean parentTest = l < p;

        //root test
        boolean rootTest = isLeft ? (l < rt) : (l > rt);

        //right test
        boolean rightTest = right == null || (l < right.getValue());

        System.out.print("\n Left Node : " + l + " || Parent -> " + parentTest + " || root -> " + rootTest + " || left -> " + rightTest);

        return parentTest && rootTest && rightTest;
    }

}

class KthSmallest {
    public static void main(String[] args) {
        PriorityQueue<Integer> q = new PriorityQueue<>();

    }


}

class MakeTreeFromPreAndIn {
    public static void main(String[] args) {

        int[] preorder = new int[]{3, 9, 20, 15, 7};
        int[] inorder = new int[]{9, 3, 15, 20, 7};

        System.out.println("Output is " + buildTree(preorder, inorder));

    }

    public static TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder.length == 0 || inorder.length == 0) return null;

        //1st preorder is the root of current subtree
        int r = preorder[0];

        //root of subtree divides subtree into left and right in inorder
        int mid = findMid(r, inorder);

        //create root of subtree
        TreeNode root = new TreeNode(r);

        //give left of inorder to (LNR) to left subtree
        //preorder exclude root and till
        //inorder from first to before root
        root.left = inPre(Arrays.copyOfRange(preorder, 1, mid + 1), Arrays.copyOfRange(inorder, 0, mid));

        //give right of inorder to (LNR) to right subtree
        //preorder from mid and till end
        //inorder from after mid to end i.e exclude done val
        root.right = inPre(Arrays.copyOfRange(preorder, mid + 1, preorder.length), Arrays.copyOfRange(inorder, mid + 1, inorder.length));
        return root;
    }

    public static TreeNode inPre(int[] preorder, int[] inorder) {
        if (preorder.length == 0 || inorder.length == 0) return null;

        //1st preorder is the root of current subtree

        int root = preorder[0];

        //root of subtree divides subtree into left and right in inorder
        int mid = findMid(root, inorder);

        //create root of subtree
        TreeNode parent = new TreeNode(root);

        //give left of inorder to (LNR) to left subtree
        //preorder exclude root and go till mid i.e. 1 -> mid left subtree
        //inorder from first to before root i.e. exclude mid
        parent.left = inPre(Arrays.copyOfRange(preorder, 1, mid + 1), Arrays.copyOfRange(inorder, 0, mid));

        //give right of inorder to (LNR) to right subtree
        //preorder from mid and till end i.e. mid + 1 > end right subtree
        //inorder from after mid to end i.e exclude mid
        parent.right = inPre(Arrays.copyOfRange(preorder, mid + 1, preorder.length), Arrays.copyOfRange(inorder, mid + 1, inorder.length));

        return parent;
    }

    public static int findMid(int mid, int[] arr) {

        for (int i = 0; i < arr.length; i++) {
            if (mid == arr[i]) return i;
        }

        return 0;
    }
}

class SerDesr {

    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(1);
        treeNode.left = new TreeNode(2);
        treeNode.right = new TreeNode(3);
        treeNode.right.right = new TreeNode(5);
        treeNode.right.left = new TreeNode(4);

        String serialize = serialize(treeNode);
        System.out.println("ser is " + serialize);

        TreeNode deserialize = deserialize(serialize);
        System.out.println("de is " + deserialize);

        System.out.println("equals " + treeNode.equals(deserialize));

    }

    private static HashMap<String, List<Integer>> b = new HashMap<>();
    private static String pre = "p";
    private static String in = "i";

    // Encodes a tree to a single string.
    public static String serialize(TreeNode root) {
        b.clear();
        inOrder(root);
        preOrder(root);
        return fromMap(b);
    }

    // Decodes your encoded data to tree.
    public static TreeNode deserialize(String data) {
        b = toMap(data);
        return dfs(b.get(pre), b.get(in));
    }

    private static void inOrder(TreeNode node) {
        if (node == null) return;

        //L
        inOrder(node.left);
        //N
        List<Integer> l = b.getOrDefault(in, new ArrayList<>());
        l.add(node.getValue());
        b.put(in, l);

        //R
        inOrder(node.right);

    }

    private static void preOrder(TreeNode node) {
        if (node == null) return;

        //N
        List<Integer> l = b.getOrDefault(pre, new ArrayList<>());
        l.add(node.getValue());
        b.put(pre, l);

        //L
        preOrder(node.left);

        //R
        preOrder(node.right);

    }

    private static String fromList(List<Integer> list) {

        StringBuilder sb = new StringBuilder();

        for (int i : list) sb.append(i).append(",");

        return sb.toString();
    }

    private static List<Integer> toList(String s) {
        List<Integer> list = new ArrayList<>();
        if (s.equals("")) return list;
        boolean negative = false;
        for (int i = 0; i < s.length(); i++) {
            String a = String.valueOf(s.charAt(i));
            if (a.equals(",")) continue;
            else if (a.equals("-")) negative = true;
            else if (negative) {
                Integer k = Integer.parseInt("-".concat(a));
                list.add(k);
                negative = false;
            } else list.add(Integer.parseInt(a));
        }
        return list;
    }

    private static String fromMap(HashMap<String, List<Integer>> map) {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, List<Integer>> e : map.entrySet()) {
            sb.append(e.getKey()).append("=").append(fromList(e.getValue())).append(".");
        }

        return sb.toString();
    }

    private static HashMap<String, List<Integer>> toMap(String s) {
        HashMap<String, List<Integer>> map = new HashMap<>();
        if (s.equals("")) return map;
        for (int i = 0; i < s.length(); i++) {
            String a = String.valueOf(s.charAt(i));
            List<Integer> g = map.getOrDefault(a, new ArrayList<>());
            StringBuilder sb = new StringBuilder();
            for (int j = i + 1; j < s.length(); j++) {
                String v = String.valueOf(s.charAt(j));

                if (v.equals("=")) continue;
                else if (v.equals(".")) {
                    g = toList(sb.toString());
                    i = j;
                    break;
                } else {
                    sb.append(v);
                }
            }
            map.put(a, g);
        }
        return map;
    }

    private static TreeNode dfs(List<Integer> preorder, List<Integer> inorder) {
        if (preorder.size() == 0 || inorder.size() == 0) return null;

        int r = preorder.get(0);
        int mid = findMid(r, inorder);

        TreeNode root = new TreeNode(r);


        root.left = dfs(preorder.subList(1, mid + 1), inorder.subList(0, mid));
        root.right = dfs(preorder.subList(mid + 1, preorder.size()), inorder.subList(mid + 1, inorder.size()));

        return root;
    }

    public static int findMid(int mid, List<Integer> arr) {

        for (int i = 0; i < arr.size(); i++) {
            if (mid == arr.get(i)) return i;
        }

        return 0;
    }

}

class AVL {
    public static void main(String[] args) {
        MyTree avl = new MyTree(new TreeNode(50), true);
        avl.root.right = new TreeNode(60);
        avl.root.left = new TreeNode(40);
        avl.root.left.left = new TreeNode(30);

        avl.insert(10);
        System.out.println(avl.isBalanced());
    }
}

class MockTime {
    public static void main(String[] args) {

        String time = "?7:??";

        System.out.println("Output is " + getCombinations(time));
        System.out.println("Expected is " + getTimeCombinations(time));
    }

    private static int getTimeCombinations(String time) {
        if (!time.contains("?")) return 0;
        int result = 1;
        for (int i = 0; i < time.length(); i++) {
            if (time.charAt(i) != '?') continue;

            switch (i) {
                //hr 1
                //max 4
                case 0 -> {
                    if (time.charAt(1) == '?') {
                        //??:00
                        result = 24;
                        i++;
                    } else {
                        result *= (Integer.parseInt(String.valueOf(time.charAt(1))) > 3) ? 2 : 3;
                    }
                }

                //hr 2
                case 1 -> //1 is not ?
                        result *= time.charAt(0) != '2' ? 10 : 4;

                //min 1
                case 3 -> result *= 6;

                //min 2
                case 4 -> result *= 10;
            }
        }
        return result;
    }

    public static int getCombinations(String time) {
        //time is valid
        //if no combinations return 0
        //always 5 char

        /*
        (Min) 00:00 - 23:59 (Max)

        1. “00:00” -> 0

        2. “00:??” -> 60

        3. “?0:00” -> 3
z
        4. “??:00” -> 24

        5. “17:5?” -> 10

        6. “??:??” -> 1440

        7. "?7:??" ->

        */


        //initialize variables for loop //condition to do loop //at end of the loop
        //time.length = 5;
        //{'?','?',':','2','5'}

        //Hh:Mm
        char H = time.charAt(0);
        char h = time.charAt(1);

        char M = time.charAt(3);
        char m = time.charAt(4);

        int totalHourCombinations = 0;
        int totalMinuteCombinations = 0;


        //H Conditions
        // if H != ?
        //if H == 2
        // h < 4
        // h < 10

        //h Conditions
        //if h != ?
        // h <= 3
        //H <= 2
        // h > 3
        // H <= 1

        // M Conditions
        // if M == ?
        //permutation = 6

        //m Conditions
        // if m == ?
        //permutation = 9


        //Test Hours section
        if (H != '?' && h == '?') {
            if (H == '2') {
                totalHourCombinations += 4;
            } else if (H == '0' || H == '1') {
                totalHourCombinations += 10;
            }
        } else if (H == '?' && h != '?') {
            System.out.println("Small h is: " + h);
            if (Integer.parseInt(String.valueOf(h)) > 3) {
                totalHourCombinations += 2;
            } else {
                totalHourCombinations += 3;
            }
        } else if (H == '?' && h == '?') {
            totalHourCombinations += 24;
        }

        //Test Minutes Section
        if (M != '?' && m == '?') {
            totalMinuteCombinations += 10;
        } else if (M == '?' && m != '?') {
            totalMinuteCombinations += 6;
        } else if (M == '?' && m == '?') {
            totalMinuteCombinations += 60;
        }

        //Check if a value is 0
        if (totalMinuteCombinations == 0 || totalHourCombinations == 0) {
            if (totalMinuteCombinations == 0) {
                ++totalMinuteCombinations;
            } else {
                ++totalHourCombinations;
            }
        }

        return totalHourCombinations * totalMinuteCombinations;
    }

}

//heaps
class MyMinHeap {
    int[] heapArray; // array of elements in heap
    int capacity; //maximum possible sie of min heap
    int heapSize; //current no of elements in heap

    public MyMinHeap(int capacity) {
        this.heapSize = 0;
        this.capacity = capacity;
        heapArray = new int[capacity];
    }

    public MyMinHeap(int[] heapArray) {
        this.heapArray = heapArray;
        this.capacity = heapArray.length;
        this.heapSize = heapArray.length;
        heapifyAllNodes();
    }

    public Integer getParent(int index) {
        //gets parent node
        int parent = (index - 1) / 2;
        if (heapSize == 0) return null;
        return parent;
    }

    public Integer getLeft(int index) {
        //returns left node
        int left = (2 * index) + 1;
        if (left >= heapSize) return null;
        return left;
    }

    public Integer getRight(int index) {
        //return tight node
        int right = (2 * index) + 2;
        if (right >= heapSize) return null;
        return right;
    }

    public int getMin() {
        //get minimum root value
        return 0;
    }

    public Integer extractMin() {
        //gets and removes minimum value (root)
        if (heapSize <= 0) return null;

        if (heapSize == 1) {
            heapSize--;
            return heapArray[0];
        }

        int root = heapArray[0];
        heapArray[0] = heapArray[heapSize - 1];
        heapSize--;
        minHeapify(0);
        return root;
    }

    public void insertKey(int k) {
        //insert new key / value
        if (heapSize == capacity) throw new IllegalStateException("Heap full");
        heapSize++;
        int index = heapSize - 1;

        //insertion at end of array / heap
        heapArray[index] = k;

        siftUp(index);
    }

    private boolean isLeaf(int index) {
        return index > (heapSize / 2) && index <= heapSize;
    }

    private void siftUp(int index) {
        Integer parentIndex = getParent(index);

        //i.e. move up anything less (current and parent)
        //swap till top
        while ((index != 0 && parentIndex != null) && (heapArray[index] < heapArray[parentIndex])) {
            //swap
            int temp = heapArray[index];
            heapArray[index] = heapArray[parentIndex];
            heapArray[parentIndex] = temp;


            //for the next iteration
            index = parentIndex;
            parentIndex = getParent(index);
        }

    }

    private void siftDown(int index) {
        int leftIndex = getLeft(index);
        int rightIndex = getRight(index);

        while ((leftIndex < heapSize && (heapArray[index] > heapArray[leftIndex]))
                ||
                rightIndex < heapSize && (heapArray[index] > heapArray[rightIndex])) {

            //swap with the smallest child
            int smallestIndex = rightIndex >= heapSize || heapArray[leftIndex] < heapArray[rightIndex] ? leftIndex : rightIndex;

            //swap
            int temp = heapArray[smallestIndex];
            heapArray[smallestIndex] = heapArray[index];
            heapArray[index] = temp;

            //for the next iteration
            index = smallestIndex;
            leftIndex = getLeft(index);
            rightIndex = getRight(index);
        }

    }

    public void decreaseKey(int index, int newVal) {
        //deletion
        heapArray[index] = newVal;
        Integer parent = getParent(index);
        while (index != 0 && heapArray[parent] > heapArray[index]) {
            int temp = heapArray[index];
            heapArray[index] = heapArray[parent];
            heapArray[parent] = temp;
            index = parent;
        }
    }

    public void deleteKey(int index) {
        decreaseKey(index, Integer.MIN_VALUE);
        extractMin();
    }

    public void minHeapify(int index) {
        Integer left = getLeft(index);
        Integer right = getRight(index);
        int smallest = index;

        //left is smaller?
        if ((left != null) && left < heapSize && heapArray[left] < heapArray[index]) smallest = left;

        //right is smaller
        if ((right != null) && right < heapSize && heapArray[right] < heapArray[smallest]) smallest = right;

        //smaller has changed do swap
        if (smallest != index) {
            //swap
            int temp = heapArray[index];
            heapArray[index] = heapArray[smallest];
            heapArray[smallest] = temp;
            minHeapify(smallest);
        }
    }

    public void heapifyAllNodes() {
        //from bottom level to top level exclude last level
        for (int i = (heapSize / 2) - 1; i >= 0; i--) {
            minHeapify(i);
        }
    }

    public boolean contains(int val) {
        for (int i = 0; i < heapSize; i++) if (heapArray[i] == val) return true;
        return false;
    }

    public void printArray() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < heapSize; i++) {
            sb.append(heapArray[i]).append(", ");
        }
        sb.append("]");
        System.out.println(sb);
    }

    public int getHeight() {
        return (int) (Math.ceil(Math.log(heapSize + 1)) - 1);
    }

}

class Heaps {
    public static void main(String[] args) {
        MyMinHeap myMinHeap = new MyMinHeap(7);
        myMinHeap.insertKey(5);
        myMinHeap.insertKey(8);
        myMinHeap.insertKey(7);
        myMinHeap.insertKey(3);

        myMinHeap.printArray();
    }
}

class LargestKth {
    public static void main(String[] args) {
        KthLargest kthLargest = new KthLargest(3, new int[]{4, 5, 8, 2});
        kthLargest.add(3);
    }

    static class KthLargest {

        PriorityQueue<Integer> nums = new PriorityQueue<>();
        int k;

        public KthLargest(int k, int[] nums) {
            this.k = k;
            for (int num : nums) this.nums.add(num);
        }

        public int add(int val) {
            nums.add(val);
            while (nums.size() > k) {
                nums.poll();
            }
            return nums.peek();
        }


    }
}

class LastStoneWeight {
    public static void main(String[] args) {

        int[] stones = new int[]{2, 7, 4, 1, 8, 1};

        System.out.println("Output is " + lastStoneWeight(stones));
        System.out.println("Expected output is " + lastStoneWeightNeet(stones));
    }

    public static int lastStoneWeight(int[] stones) {

        if (stones.length == 1) return 1;

        PriorityQueue<Integer> m = new PriorityQueue<>(reverseOrder());

        for (int s : stones) m.offer(s);

        System.out.println(Arrays.stream(stones).boxed().toList());

        while (m.size() > 1) {
            int big = m.poll();
            int small = m.poll();


            //default destroyed

            //one more
            if (big > small) m.offer(big - small);
        }

        return m.isEmpty() ? 0 : m.poll();
    }

    public static int lastStoneWeightNeet(int[] stones) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>();
        for (int stone : stones) maxHeap.add(-stone);
        while (maxHeap.size() > 1) {
            int stone1 = maxHeap.remove();
            int stone2 = maxHeap.remove();
            if (stone1 != stone2) maxHeap.add(stone1 - stone2);
        }
        return maxHeap.size() != 0 ? (-maxHeap.remove()) : 0;
    }
}

class KthClosesToOrigin {
    public static void main(String[] args) {
        int[][] points = new int[][]{new int[]{1, 3}, new int[]{-2, 2}};
        int k = 2;
        System.out.println("Output is ");
        Arrays.stream(kClosest(points, k)).forEach(a -> System.out.println(Arrays.stream(a).boxed().toList()));
        System.out.println("Expected output is ");
        Arrays.stream(kClosestNeet(points, k)).forEach(a -> System.out.println(Arrays.stream(a).boxed().toList()));

    }

    public static int[][] kClosest(int[][] points, int k) {
        PriorityQueue<int[]> q = new PriorityQueue<>(Comparator.comparingInt((int[] a) -> a[0]));

        for (int[] point : points) {

            int x = point[0];
            int y = point[1];

            int key = (int) getDistance(x, y);

            q.offer(new int[]{key, x, y});
        }


        int kth = 0;
        int[][] list = new int[k][2];


        while (k > 0) {
            int[] m = q.poll();
            list[kth] = new int[]{m[1], m[2]};
            k--;
            kth++;
        }

        return list;
    }

    private static double getDistance(int x1, int y1) {

        double x = Math.pow(x1, 2);
        double y = Math.pow(y1, 2);

        return x + y;
    }

    public static int[][] kClosestNeet(int[][] points, int k) {
        PriorityQueue<int[]> q = new PriorityQueue<>((a, b) -> Integer.compare((b[0] * b[0] + b[1] * b[1]), (a[0] * a[0] + a[1] * a[1]))); //only this is changed (swapped)
        for (int[] point : points) {
            q.add(point);
            //remove when size increase k
            if (q.size() > k) {
                q.remove();
            }
        }
        int[][] ans = new int[k][2];
        for (int i = 0; i < k; i++) {
            int[] cur = q.poll();
            ans[i][0] = cur[0];
            ans[i][1] = cur[1];
        }
        return ans;
    }

}

class KthLargestInArray {
    public static void main(String[] args) {
        int[] nums = new int[]{3, 2, 3, 1, 2, 4, 5, 5, 6};
        int k = 4;
        System.out.println("output is " + findKthLargest(nums, k));
    }

    public static int findKthLargest(int[] nums, int k) {
        if (k < 1) throw new AssertionError();
        if (nums.length < 1) throw new AssertionError();
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        for (int n : nums) {

            if (pq.size() < k) {
                pq.offer(n);
                continue;
            }

            if (n > pq.peek()) {
                pq.poll();
                pq.offer(n);
            }

        }

        assert !pq.isEmpty();
        return pq.poll();
    }


}

class TaskScheduler {
    public static void main(String[] args) {

    }

}

class TwitterLeet {

    public static void main(String[] args) {


        //["Twitter","postTweet","postTweet","postTweet","postTweet","postTweet","postTweet","postTweet","postTweet","postTweet","getNewsFeed"]
        //[[],[1,5],[1,3],[1,101],[1,13],[1,10],[1,2],[1,94],[1,505],[1,333],[1]]
        Twitter twitter = new Twitter();

        twitter.postTweet(1, 5);
        twitter.postTweet(1, 3);
        twitter.postTweet(1, 101);

        twitter.postTweet(1, 13);
        twitter.postTweet(1, 10);
        twitter.postTweet(1, 2);

        twitter.postTweet(1, 94);
        twitter.postTweet(1, 505);
        twitter.postTweet(1, 333);

        System.out.println("Feed is " + twitter.getNewsFeed(1));

    }

    static class Twitter {

        final HashMap<Integer, HashSet<Integer>> followers = new HashMap<>();
        final PriorityQueue<Tweet> tweets = new PriorityQueue<>(Comparator.comparingInt(a -> -a.id));

        public Twitter() {

        }

        public void postTweet(int userId, int tweetId) {
            Tweet tweet = new Tweet(tweets.size() + 1, userId, tweetId);
            tweets.add(tweet);
        }

        public List<Integer> getNewsFeed(int userId) {
            List<Integer> feed = new ArrayList<>();
            List<Tweet> lookedAtFeeds = new ArrayList<>();
            int max = 10;
            HashSet<Integer> f1 = followers.getOrDefault(userId, new HashSet<>());

            while (!tweets.isEmpty()) {
                Tweet tweet = tweets.poll();
                if (f1.contains(tweet.userId) || tweet.userId == userId) feed.add(tweet.tweetId);

                lookedAtFeeds.add(tweet);
                if (feed.size() == max) break;
            }

            tweets.addAll(lookedAtFeeds);

            return feed;
        }

        public void follow(int followerId, int followeeId) {
            //add follower to following
            HashSet<Integer> f1 = followers.getOrDefault(followerId, new HashSet<>());
            if (f1.add(followeeId)) followers.put(followerId, f1);
        }

        public void unfollow(int followerId, int followeeId) {
            HashSet<Integer> f1 = followers.get(followerId);
            if (f1 == null) return;
            if (f1.remove(followeeId)) followers.put(followerId, f1);
        }

        static class Tweet {

            int id;
            int userId;
            int tweetId;


            public Tweet(int id, int userId, int tweetId) {
                this.id = id;
                this.userId = userId;
                this.tweetId = tweetId;
            }
        }
    }

}

class MedianLeet {
    public static void main(String[] args) {
        MedianFinder2 medianFinder = new MedianFinder2();
        medianFinder.addNum(1);
        medianFinder.addNum(2);
        //medianFinder.addNum(3);
        //medianFinder.addNum(4);
        //medianFinder.addNum(5);

        System.out.println("Median is " + medianFinder.findMedian());
    }

    static class MedianFinder {

        final PriorityQueue<Integer> all = new PriorityQueue<>();

        public MedianFinder() {

        }

        public void addNum(int num) {
            all.add(num);
        }

        public double findMedian() {
            if (all.isEmpty()) return 0;
            if (all.size() == 1) return all.peek();
            int size = all.size();
            int half = size / 2;
            List<Integer> looked = new ArrayList<>();

            for (int i = 0; i < half; i++) looked.add(all.poll());


            if (size % 2 == 0) {
                //even
                //e.g. [1, 2] h = 1
                int a = looked.get(looked.size() - 1);
                int b = all.poll();

                looked.add(b);
                all.addAll(looked);

                return (double) (a + b) / 2;
            } else {
                //odd
                //e.g. [1, 2, 3] h = 1

                int b = all.poll();

                looked.add(b);
                all.addAll(looked);

                return b;
            }
        }


    }

    static class MedianFinder2 {

        final List<Integer> all = new ArrayList<>();

        public MedianFinder2() {

        }

        public void addNum(int num) {
            all.add(num);
            all.sort(Comparator.naturalOrder());
        }

        public double findMedian() {
            if (all.isEmpty()) return 0;
            if (all.size() == 1) return all.get(0);
            int size = all.size();
            int half = size / 2;


            if (size % 2 == 0) {
                //even
                //e.g. [1, 2] h = 1
                int a = all.get(half - 1);
                int b = all.get(half);

                return (double) (a + b) / 2;
            } else {
                //odd
                //e.g. [1, 2, 3] h = 1

                return all.get(half);
            }
        }


    }
}

//graphs
class Graph {

    //Adjacency list implementation
    //undirected graph
    private final HashMap<Integer, Vertex> vertices = new HashMap<>();

    public Graph() {
    }

    public HashMap<Integer, Vertex> getVertices() {
        return vertices;
    }

    public boolean addVertex(String name, int id) {
        if (doesVertexIdExist(id)) return false;
        vertices.put(id, new Vertex(id, name));
        return true;
    }


    public boolean addVertex(Vertex vertex) {
        if (doesVertexIdExist(vertex.id)) return false;
        vertices.put(vertex.id, vertex);
        return true;
    }

    public boolean areVerticesConnected(int sourceVertexId, int destinationVertexId) {
        Vertex sourceVertex = getVertex(sourceVertexId).orElse(null);
        if (sourceVertex == null) return false;

        Vertex destinationVertex = getVertex(destinationVertexId).orElse(null);
        if (destinationVertex == null) return false;

        return sourceVertex.getEdgeList().containsKey(destinationVertex.id) && destinationVertex.getEdgeList().containsKey(sourceVertex.id);
    }

    public boolean deleteVertex(int vertexId) {
        Vertex vertex = getVertex(vertexId).orElse(null);
        if (vertex == null) return false;

        for (Map.Entry<Integer, Edge> e : vertex.getEdgeList().entrySet())
            deleteEdge(e.getValue().destinationVertexId, vertexId);

        vertices.remove(vertexId);

        return true;
    }

    public boolean updateVertex(int vertexId, String name) {
        Vertex vertex = getVertex(vertexId).orElse(null);
        if (vertex == null) return false;
        vertex.setName(name);
        return true;
    }

    public boolean addEdgeByVertexId(int sourceVertexId, int destinationVertexId, int weight) {
        //do source & destination already exists

        Vertex sourceVertex = getVertex(sourceVertexId).orElse(null);
        if (sourceVertex == null) return false;

        Vertex destinationVertex = getVertex(destinationVertexId).orElse(null);
        if (destinationVertex == null) return false;

        //do they already have connections
        Edge sourceEdge = new Edge(destinationVertexId, weight);
        Edge destinationEdge = new Edge(sourceVertexId, weight);

        //now add source to destination && destination to source
        if (!doesEdgeExistInVertex(destinationEdge, destinationVertex) && !doesEdgeExistInVertex(sourceEdge, sourceVertex))
            return sourceVertex.addEdge(sourceEdge) && destinationVertex.addEdge(destinationEdge);

        return false;
    }

    public boolean updateEdge(int sourceVertexId, int destinationVertexId, int newWeight) {
        Vertex sourceVertex = getVertex(sourceVertexId).orElse(null);
        if (sourceVertex == null) return false;

        Vertex destinationVertex = getVertex(destinationVertexId).orElse(null);
        if (destinationVertex == null) return false;


        return sourceVertex.updateEdge(newWeight, destinationVertexId) && destinationVertex.updateEdge(newWeight, sourceVertexId);
    }

    public boolean deleteEdge(int sourceVertexId, int destinationVertexId) {
        Vertex sourceVertex = getVertex(sourceVertexId).orElse(null);
        if (sourceVertex == null) return false;

        Vertex destinationVertex = getVertex(destinationVertexId).orElse(null);
        if (destinationVertex == null) return false;

        return sourceVertex.removeEdge(destinationVertexId) && destinationVertex.removeEdge(sourceVertexId);
    }


    public void bfsProcess(Vertex v) {
        if (vertices.isEmpty()) return;
        Queue<Vertex> toProcess = new LinkedList<>();
        HashSet<Integer> processed = new HashSet<>();

        toProcess.offer(v);

        while (!toProcess.isEmpty()) {

            v = toProcess.poll();

            if (!processed.contains(v.id)) {
                processed.add(v.id);
                System.out.println(v.id);
            }

            for (Map.Entry<Integer, Edge> edgeEntry : v.getEdgeList().entrySet()) {
                Edge edge = edgeEntry.getValue();
                Optional<Vertex> vertex = getVertex(edge.destinationVertexId);
                if (vertex.isPresent() && !processed.contains(vertex.get().id)) {
                    toProcess.offer(vertex.get());
                }
            }

        }
    }

    public void bfs(Vertex v) {
        HashSet<Integer> seen = new HashSet<>();
        bfsRecursion(v, seen);
    }

    public void bfsRecursion(Vertex v, HashSet<Integer> seen) {
        if (v == null || seen.contains(v.id)) return; //base case

        seen.add(v.id);
        System.out.println(v.id);

        for (Map.Entry<Integer, Edge> edgeEntry : v.getEdgeList().entrySet()) {
            bfsRecursion(getVertex(edgeEntry.getValue().getDestinationVertexId()).orElse(null), seen);
        }

    }

    public void dfsProcess(Vertex v) {
        if (v == null) return;
        if (vertices.isEmpty()) return;
        Stack<Vertex> toProcess = new Stack<>();
        HashSet<Integer> visited = new HashSet<>();

        toProcess.push(v);
        System.out.println(v.id);


        while (!toProcess.isEmpty()) {
            v = toProcess.pop();

            if (!visited.contains(v.id)) {
                visited.add(v.id);
                System.out.println(v.id);
            }

            for (Map.Entry<Integer, Edge> edgeEntry : v.getEdgeList().entrySet()) {
                Edge edge = edgeEntry.getValue();
                Optional<Vertex> vertex = getVertex(edge.destinationVertexId);
                if (!visited.contains(edge.destinationVertexId) && vertex.isPresent()) {
                    toProcess.push(vertex.get());
                    //break;
                }
            }
        }

    }

    public void dfs(Vertex v) {
        HashSet<Integer> seen = new HashSet<>();
        dfsRecursion(v, seen);
    }

    public void dfsRecursion(Vertex v, HashSet<Integer> seen) {
        if (v == null || seen.contains(v.id)) return; //base case

        seen.add(v.id);


        for (Map.Entry<Integer, Edge> edgeEntry : v.getEdgeList().entrySet()) {
            dfsRecursion(getVertex(edgeEntry.getValue().getDestinationVertexId()).orElse(null), seen);
        }

        System.out.println(v.id);

    }

    public Optional<Vertex> getVertex(int id) {
        return Optional.of(vertices.get(id));
    }

    private boolean doesEdgeExistInVertex(Edge edge, Vertex vertex) {
        return vertex.doesEdgeIdExists(edge);
    }

    private boolean doesVertexIdExist(int id) {
        return vertices.containsKey(id);
    }


    @Override
    public String toString() {
        return "Graph {" +
                "vertices=" + vertices +
                '}';
    }

    public void printGraph() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, Vertex> v : vertices.entrySet()) {
            sb.setLength(0);

            Vertex vertex = v.getValue();
            sb.append(vertex.name).append(" (").append(vertex.id).append(") --> ");

            vertex.printEdgeList(sb);
            System.out.println(sb);
        }
    }

    static class Edge {

        private int destinationVertexId;
        private int weight;

        public Edge(int destinationVertexId, int weight) {
            this.destinationVertexId = destinationVertexId;
            this.weight = weight;
        }

        public int getDestinationVertexId() {
            return destinationVertexId;
        }

        public void setDestinationVertexId(int destinationVertexId) {
            this.destinationVertexId = destinationVertexId;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "destinationVertexId=" + destinationVertexId +
                    ", weight=" + weight +
                    '}';
        }
    }

    static class Vertex {
        private int id;
        private String name;
        private final HashMap<Integer, Edge> edgeList = new HashMap<>();

        public Vertex(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public HashMap<Integer, Edge> getEdgeList() {
            return edgeList;
        }

        public boolean addEdge(Edge edge) {
            if (doesEdgeIdExists(edge)) return false;
            edgeList.put(edge.destinationVertexId, edge);
            return true;
        }

        public boolean removeEdge(int destinationId) {
            Edge edge = getEdge(destinationId).orElse(null);
            if (edge == null) return false;
            edgeList.remove(edge.destinationVertexId);
            return true;
        }

        public boolean addEdge(int destinationVertexId, int weight) {
            Edge edge = new Edge(destinationVertexId, weight);
            if (doesEdgeIdExists(edge)) return false;
            edgeList.put(destinationVertexId, edge);
            return true;
        }

        public boolean updateEdge(int newWeight, int destinationId) {
            Edge edge = getEdge(destinationId).orElse(null);
            if (edge == null) return false;
            edge.setWeight(newWeight);
            edgeList.put(edge.destinationVertexId, edge);
            return true;
        }

        public Optional<Edge> getEdge(int destinationId) {
            return Optional.of(edgeList.get(destinationId));
        }

        public boolean doesEdgeIdExists(Edge edge) {
            return edgeList.containsKey(edge.destinationVertexId);
        }

        @Override
        public String toString() {
            return "Vertex{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", edgeList=" + edgeList +
                    '}';
        }

        public void printEdgeList(StringBuilder sb) {
            sb.append("[");
            for (Map.Entry<Integer, Edge> e : edgeList.entrySet()) {
                Edge edge = e.getValue();
                sb.append(edge.destinationVertexId).append("(").append(edge.weight).append(")");
            }
            sb.append("]");
        }
    }

}

class Streets {
    public static void main(String[] args) {
        Graph hood = new Graph();

        //add vertices
        hood.addVertex("Kingara", 1);
        hood.addVertex("Jamuhuri", 2);
        hood.addVertex("Kilimani", 3);
        hood.addVertex("DayStar", 4);
        hood.addVertex("Tao", 5);

        //add edges
        System.out.println("\n Insert");
        hood.addEdgeByVertexId(1, 2, 3);
        hood.addEdgeByVertexId(2, 3, 3);
        hood.addEdgeByVertexId(3, 4, 3);
        hood.addEdgeByVertexId(4, 5, 3);
        hood.addEdgeByVertexId(2, 4, 3);
        hood.addEdgeByVertexId(3, 5, 3);
        //hood.printGraph();

        System.out.println("\n Update");
        hood.updateEdge(1, 2, 500);
        // hood.printGraph();

        //System.out.println("\n Delete Edge");
        //hood.deleteEdge(1,2);
        //hood.printGraph();

        //System.out.println("\n Update Vertex");
        //hood.updateVertex(1, "To Be deleted");
        //hood.printGraph();

        // System.out.println("\n Delete Vertex");
        // hood.deleteVertex(1);
        hood.printGraph();

        hood.dfsProcess(hood.getVertex(2).orElse(null));
        System.out.println("=");
        hood.dfs(hood.getVertex(2).orElse(null));
//        hood.bfsProcess(hood.getVertex(2).orElse(null));
//        hood.bfs(hood.getVertex(2).orElse(null));


    }
}

class NoOfIslands {

    static char water = '0';
    static char land = '1';

    public static void main(String[] args) {

        char[][] grid = new char[][]{
                {'1', '1', '0', '0', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '1', '0', '0'},
                {'0', '0', '0', '1', '1'},
        };

        /* char[][] grid = new char[][]{
                {'1','1','1','1','0'},
                {'1','1','0','1','0'},
                {'1','1','0','0','0'},
                {'0','0','0','0','0'},
                };*/

        /*char[][] grid = new char[][]{
                {'1','1','1'},
                {'0','1','0'},
                {'0','1','0'},
        };*/


        System.out.println("output is " + numIslandsBalo(grid));
        System.out.println("Expected is 1");
    }

    public static int numIslandsBalo(char[][] grid) {
        int islands = 0;

        int v = grid.length;
        int e = grid[0].length;

        HashSet<String> processed = new HashSet<>();

        for (int i = 0; i < v; i++) {
            for (int j = 0; j < e; j++) {
                if (grid[i][j] == land)
                    if (!processed.contains(getPoint(i, j)))
                        if (hasIsland(i, j, grid, processed)) ++islands;
            }
        }


        return islands;
    }


    public static boolean hasIsland(int row, int col, char[][] grid, HashSet<String> processed) {
        HashSet<String> seen = new HashSet<>();
        dfs(row, col, grid, seen);
        processed.addAll(seen);
        return true;
    }

    public static void dfs(int row, int col, char[][] grid, HashSet<String> seen) {
        if (grid[row][col] == water || seen.contains(getPoint(row, col))) return; // base case

        //process
        seen.add(getPoint(row, col));

        //children
        //left
        if (col - 1 >= 0) dfs(row, col - 1, grid, seen);

        //right
        if (col + 1 < grid[0].length) dfs(row, col + 1, grid, seen);

        //top
        if (row - 1 >= 0) dfs(row - 1, col, grid, seen);

        //bottom
        if (row + 1 < grid.length) dfs(row + 1, col, grid, seen);
    }


    public static boolean isIsland(char top, char bottom, char left, char right) {
        return top == water && bottom == water && left == water && right == water;
    }

    public static String getPoint(int i, int j) {
        return String.valueOf(i).concat(String.valueOf(j));
    }
}

class CloneGraph {

    private static class Node {
        public int val;
        public List<Node> neighbors;

        public Node() {
            val = 0;
            neighbors = new ArrayList<Node>();
        }

        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<Node>();
        }

        public Node(int _val, ArrayList<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }

    public Node cloneGraph(Node node) {
        if (node == null) return null;

        HashMap<Integer, List<Integer>> all = new HashMap<>();

        Stack<Node> stack = new Stack<>();
        HashSet<Integer> seen = new HashSet<>();

        stack.push(node);

        while (!stack.isEmpty()) {
            Node n = stack.pop();

            if (!seen.contains(n.val)) {
                all.put(n.val, n.neighbors.stream().map(i -> i.val).toList());
                seen.add(n.val);
            }

            for (Node child : n.neighbors) {
                if (!seen.contains(child.val)) {
                    stack.push(child);
                }
            }

        }

        return getClone(all, seen);
    }

    public Node getClone(HashMap<Integer, List<Integer>> all, HashSet<Integer> make) {
        Node clone = null;

        HashMap<Integer, Node> nodeHashMap = new HashMap<>();
        for (Integer n : make) nodeHashMap.put(n, new Node(n));

        for (Map.Entry<Integer, Node> nodeEntry : nodeHashMap.entrySet()) {
            Node node = nodeEntry.getValue();
            if (clone == null) clone = node;
            for (Integer n : all.get(node.val)) addNeighbour(node, nodeHashMap.get(n));
        }

        return clone;
    }

    public void addNeighbour(Node source, Node destination) {
        boolean src = true;
        boolean dst = true;

        for (Node s : source.neighbors)
            if (s.val == destination.val) {
                src = false;
                break;
            }
        for (Node s : destination.neighbors)
            if (s.val == source.val) {
                dst = false;
                break;
            }

        if (src) source.neighbors.add(destination);
        if (dst) destination.neighbors.add(source);
    }

    public Node cloneGraph2(Node node) {
        if (node == null) return null;


        HashMap<Node, Node> newNodes = new HashMap<>();



        Stack<Node> stack = new Stack<>();
        HashSet<Integer> seen = new HashSet<>();
        stack.push(node);

        while (!stack.isEmpty()) {
            Node n = stack.pop();

            if (!seen.contains(n.val)) {
                seen.add(n.val);
                newNodes.put(n, new Node(n.val));
            }

            for (Node child : n.neighbors) {
                if (!seen.contains(child.val)) {
                    stack.push(child);
                }
            }

        }

        for (Node old : newNodes.keySet()) {
            Node clone = newNodes.get(old);

            for (Node n : old.neighbors) {
                clone.neighbors.add(newNodes.get(n));
            }
        }

        return newNodes.values().stream().findFirst().orElse(null);
    }

}

class MaxArea {
    public static void main(String[] args) {
        int[][] grid = new int[][]{
                {0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0},
                {0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
        };

        System.out.println("Area is " + maxAreaOfIsland(grid));
    }

    public static int maxAreaOfIsland(int[][] grid) {
        if (grid.length == 0) return 0;

        int area = 0;

        for (int i = 0; i < grid.length; i++) {

            for (int j = 0; j < grid[0].length; j++) {

                int c = grid[i][j];
                if (c == 1) {
                    area = Math.max(dfs(i, j, grid), area);
                }

            }

        }


        return area;
    }

    private static int dfs(int i, int j, int[][] grid) {
        if (i < 0 || i >= grid.length) return 0;
        if (j < 0 || j >= grid[0].length) return 0;
        if (grid[i][j] == 0) return 0;

        grid[i][j] = 0;
        int area = 1;

        //left
        int l = dfs(i, j - 1, grid);

        //right
        int r = dfs(i, j + 1, grid);

        //bottom
        int b = dfs(i + 1, j, grid);

        //top
        int t = dfs(i - 1, j, grid);

        return l + r + b + t + area;
    }

}

class PacificAtlantic {
    public static void main(String[] args) {

    /*   int[][] heights = new int[][] {
                new int[]{1, 2, 3},
                new int[]{8, 9, 4},
                new int[]{7, 6, 5},
        }; */

    /* int[][] heights = new int[][] {
                new int[]{1,2,2,3,5},
                new int[]{3,2,3,4,4},
                new int[]{2,4,5,3,1},
                new int[]{6,7,1,4,5},
                new int[]{5,1,1,2,4},
        };*/


        int[][] heights = new int[][]{
                new int[]{1, 2, 3, 4},
                new int[]{12, 13, 14, 5},
                new int[]{11, 16, 15, 6},
                new int[]{10, 9, 8, 7},
        };

        System.out.println("Output is " + pacificAtlantic(heights));
        System.out.println("Expected is [[0, 3], [1, 0], [1, 1], [1, 2], [1, 3], [2, 0], [2, 1], [2, 2], [2, 3], [3, 0], [3, 1], [3, 2], [3, 3]]");
    }


    public static List<List<Integer>> pacificAtlantic(int[][] heights) {
        if (heights.length == 0) return new ArrayList<>();

        HashSet<List<Integer>> paths = new HashSet<>();
        HashSet<List<Integer>> pacific = new HashSet<>();
        HashSet<List<Integer>> atlantic = new HashSet<>();

        for (int i = 0; i < heights.length; i++)
            for (int j = 0; j < heights[0].length; j++) {

                if (j == 0 || i == 0) {
                    pacific.add(getPoints(i, j));
                }

                if (j == heights[0].length - 1 || i == heights.length - 1) {
                    atlantic.add(getPoints(i, j));
                }

            }


        for (List<Integer> i : new ArrayList<>(pacific)) {
            dfsPacific(i.get(0), i.get(1), 0, heights, pacific);
        }


        for (List<Integer> i : new ArrayList<>(atlantic)) {
            dfsAtlantic(i.get(0), i.get(1), 0, heights, atlantic);
        }

        //System.out.print("\n atlantic");
        //System.out.print(atlantic);

        for (List<Integer> i : atlantic) {
            if (pacific.contains(i)) paths.add(i);
        }


        return new ArrayList<>(paths);
    }

    private static void dfsPacific(int i, int j, int prev, int[][] h, HashSet<List<Integer>> p) {

        if (i < 0 || i > h.length - 1) return;
        if (j < 0 || j > h[0].length - 1) return;
        if (h[i][j] < prev) return;

        p.add(getPoints(i, j));


        //left
        if (!p.contains(getPoints(i, j - 1))) {
            dfsPacific(i, j - 1, h[i][j], h, p);
        }

        //right
        if (!p.contains(getPoints(i, j + 1))) {
            dfsPacific(i, j + 1, h[i][j], h, p);
        }

        //top
        if (!p.contains(getPoints(i - 1, j))) {
            dfsPacific(i - 1, j, h[i][j], h, p);
        }

        //bottom
        if (!p.contains(getPoints(i + 1, j))) {
            dfsPacific(i + 1, j, h[i][j], h, p);
        }

    }

    private static void dfsAtlantic(int i, int j, int prev, int[][] h, HashSet<List<Integer>> a) {

        if (i < 0 || i > h.length - 1) return;
        if (j < 0 || j > h[0].length - 1) return;
        if (h[i][j] < prev) return;
        a.add(getPoints(i, j));


        //left
        if (!a.contains(getPoints(i, j - 1))) {
            dfsAtlantic(i, j - 1, h[i][j], h, a);
        }

        //right
        if (!a.contains(getPoints(i, j + 1))) {
            dfsAtlantic(i, j + 1, h[i][j], h, a);
        }

        //top
        if (!a.contains(getPoints(i - 1, j))) {
            dfsAtlantic(i - 1, j, h[i][j], h, a);
        }

        //bottom
        if (!a.contains(getPoints(i + 1, j))) {
            dfsAtlantic(i + 1, j, h[i][j], h, a);
        }

    }


    private static List<Integer> getPoints(int i, int j) {
        return List.of(i, j);
    }


}

class SurroundedRegions {

    static char x = 'X';
    static char o = 'O';
    static char t = 'T';

    public static void main(String[] args) {
      /*  char[][] board = new char[][]{
                new char[]{'X', 'X', 'X', 'X'},
                new char[]{'X', 'O', 'O', 'X'},
                new char[]{'X', 'X', 'O', 'X'},
                new char[]{'X', 'O', 'X', 'X'}
        };*/
        /*char[][] board = new char[][]{
                new char[]{'O', 'O', 'O'},
                new char[]{'O', 'O', 'O'},
                new char[]{'O', 'O', 'O'},
        };*/

        char[][] board = new char[][]{
                new char[]{'O', 'O', 'O', 'O'},
                new char[]{'O', 'O', 'O', 'O'},
                new char[]{'O', 'O', 'O', 'O'},
                new char[]{'O', 'O', 'O', 'O'}
        };

        solve(board);
    }


    public static void solve(char[][] board) {
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[0].length; j++)
                if (board[i][j] == o && (i == 0 || j == 0 || i == board.length - 1 || j == board[0].length - 1))
                    capture(i, j, board);

        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[0].length; j++)
                if (board[i][j] == o) board[i][j] = x;
                else if (board[i][j] == t) board[i][j] = o;


    }

    public static void capture(int i, int j, char[][] b) {
        // base case
        if (j < 0 || j == b[0].length) return;
        if (i < 0 || i == b.length) return;
        if (b[i][j] != o) return;

        b[i][j] = t;

        //left
//        if (j - 1 >= 0)
        capture(i, j - 1, b);

        //right
//        if (j + 1 < b[0].length)
        capture(i, j + 1, b);

        //top
//        if (i - 1 >= 0)
        capture(i - 1, j, b);

        //bottom
//        if (i + 1 < b.length)
        capture(i + 1, j, b);
    }
}

class RottingOranges {
    public static void main(String[] args) {
        /*int[][] grid = new int[][]{
                new int[]{rotten,fresh,fresh},
                new int[]{fresh,fresh,empty},
                new int[]{empty,fresh,fresh}
        };*/
        /*int[][] grid = new int[][]{
                new int[]{rotten,fresh,fresh},
                new int[]{empty,fresh,fresh},
                new int[]{fresh,empty,fresh}
        };*/

        //int[][] grid = new int[][]{new int[]{0,2}};

        int[][] grid = new int[][]{
                new int[]{2, 0, 1, 1, 1, 1, 1, 1, 1, 1}, new int[]{1, 0, 1, 0, 0, 0, 0, 0, 0, 1}, new int[]{1, 0, 1, 0, 1, 1, 1, 1, 0, 1}, new int[]{1, 0, 1, 0, 1, 0, 0, 1, 0, 1}, new int[]{1, 0, 1, 0, 1, 0, 0, 1, 0, 1},
                new int[]{1, 0, 1, 0, 1, 1, 0, 1, 0, 1}, new int[]{1, 0, 1, 0, 0, 0, 0, 1, 0, 1}, new int[]{1, 0, 1, 1, 1, 1, 1, 1, 0, 1}, new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 1}, new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };


        System.out.println("Output is " + orangesRotting(Arrays.copyOf(grid, grid.length)));
        System.out.println("Expected output is " + orangesRottingNeet(Arrays.copyOf(grid, grid.length)));
    }

    static final int rotten = 2;
    static final int fresh = 1;
    static final int empty = 0;
    static int freshF = 0;

    static Queue<List<Integer>> r = new LinkedList<>();

    public static int orangesRotting(int[][] grid) {
        int time = 0;

        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[0].length; j++)
                if (grid[i][j] == fresh) freshF++;
                else if (grid[i][j] == rotten) r.offer(List.of(i, j));


        do {
            boolean change = false;
            int size = r.size();
            for (int i = 0; i < size; i++) {
                List<Integer> poll = r.poll();
                assert poll != null;
                if (minute(poll.get(0), poll.get(1), grid)) change = true;
            }

            if (change) time++;
        } while (freshF != 0 && !r.isEmpty());

        return freshF > 0 ? -1 : time;
    }

    private static boolean minute(int i, int j, int[][] g) {
        if (i < 0 || i == g.length) return false;
        if (j < 0 || j == g[0].length) return false;
        if (g[i][j] == empty) return false;
        if (g[i][j] == fresh) {
            g[i][j] = rotten;
            --freshF;
            r.add(List.of(i, j));
            return true;
        }

        boolean left = false;
        boolean right = false;
        boolean bottom = false;
        boolean top = false;

        if (j - 1 >= 0 && g[i][j - 1] == fresh) left = minute(i, j - 1, g);
        if (j + 1 < g[0].length && g[i][j + 1] == fresh) right = minute(i, j + 1, g);
        if (i + 1 < g.length && g[i + 1][j] == fresh) bottom = minute(i + 1, j, g);
        if (i - 1 >= 0 && g[i - 1][j] == fresh) top = minute(i - 1, j, g);

        //return left || right || bottom || top;

        return right || left || bottom || top;

    }


    public static int orangesRottingNeet(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        Queue<int[]> queue = new LinkedList<>();
        int fresh = 0;

        for (int i = 0; i < m; i += 1) {
            for (int j = 0; j < n; j += 1) {
                if (grid[i][j] == 2) queue.offer(new int[]{i, j});
                else if (grid[i][j] == 1) fresh += 1;
            }
        }

        int count = 0;
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        while (!queue.isEmpty() && fresh != 0) {
            count += 1;
            int sz = queue.size();
            for (int i = 0; i < sz; i += 1) {
                int[] rotten = queue.poll();
                int r = rotten[0], c = rotten[1];
                for (int[] dir : dirs) {
                    int x = r + dir[0], y = c + dir[1];
                    if (0 <= x && x < m && 0 <= y && y < n && grid[x][y] == 1) {
                        grid[x][y] = 2;
                        queue.offer(new int[]{x, y});
                        fresh -= 1;
                    }
                }
            }
        }
        return fresh == 0 ? count : -1;
    }
}

class CourseSchedule {

    static HashSet<Integer> canBeDone = new HashSet<>();

    public static void main(String[] args) {
        //int numCourses = 2;
        //int[][] prerequisites = new int[][]{new int[]{1,0}};
        //int[][] prerequisites = new int[][]{new int[]{1, 0}, new int[]{0,1}};

        //int numCourses = 8;
       /* int[][] prerequisites = new int[][]{
                new int[]{1,0}, new int[]{2,6}, new int[]{1,7},
                new int[]{6,4},new int[]{7,0},new int[]{0,5}
        };*/

        int numCourses = 7;
        int[][] prerequisites = new int[][]{
                new int[]{1, 0}, new int[]{0, 3}, new int[]{0, 2}, new int[]{3, 2},
                new int[]{2, 5}, new int[]{4, 5}, new int[]{5, 6}, new int[]{2, 4},
        };

        System.out.println("Output is " + canFinish(numCourses, prerequisites));
        System.out.println("Expected output is " + canFinishNeet(numCourses, prerequisites));
    }

    public static boolean canFinish(int numCourses, int[][] prerequisites) {
        canBeDone.clear();
        HashMap<Integer, List<Integer>> c = new HashMap<>();

        for (int[] prerequisite : prerequisites) {

            int pre = prerequisite[1];
            int cor = prerequisite[0];

            List<Integer> cl = c.getOrDefault(cor, new ArrayList<>());
            cl.add(pre);
            c.put(cor, cl);
        }

        System.out.println(c);

        // course a
        // check for cycle
        // for each pre

        for (Map.Entry<Integer, List<Integer>> e : c.entrySet()) {
            int remaining = numCourses - 1;

            //get a course
            Integer cor = e.getKey();

            //find prerequisites
            List<Integer> pre = e.getValue();

            //if prerequisite
            // has course == cor -> rem - 1;
            // is null -> rem -1;
            // rem < 0 return false;

            //System.out.println(cor);

            if (hasCycle(cor, pre, c)) return false;
        }

        return true;
    }

    public static boolean hasCycle(int course, List<Integer> pre, HashMap<Integer, List<Integer>> map) {
        HashSet<Integer> visited = new HashSet<>();
        return hasDuplicate(course, course, pre, map, visited);
    }

    private static boolean hasDuplicate(int mainCourse, int course, List<Integer> pre, HashMap<Integer, List<Integer>> map, HashSet<Integer> visited) {
        if (canBeDone.contains(course)) return false;
        if (visited.contains(course)) return true;
        if (pre == null) return false;
        if (pre.contains(course)) return true;
        if (pre.contains(mainCourse)) return true;

        visited.add(course);

        boolean cycle = false;

        for (int c : pre) {
            if (hasDuplicate(mainCourse, c, map.get(c), map, visited)) {
                cycle = true;
            }
        }

        if (!cycle) canBeDone.add(course);

        return cycle;
    }

    public static boolean canFinishNeet(int numCourses, int[][] prerequisites) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            adj.add(new ArrayList<>());
        }

        for (int i = 0; i < prerequisites.length; i++) {
            adj.get(prerequisites[i][0]).add(prerequisites[i][1]);
        }

        int[] visited = new int[numCourses];
        for (int i = 0; i < numCourses; i++) {
            if (visited[i] == 0) {
                if (isCyclicNeet(adj, visited, i)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isCyclicNeet(List<List<Integer>> adj, int[] visited, int curr) {
        if (visited[curr] == 2) {
            return true;
        }

        visited[curr] = 2;
        for (int i = 0; i < adj.get(curr).size(); i++) {
            if (visited[adj.get(curr).get(i)] != 1) {
                if (isCyclicNeet(adj, visited, adj.get(curr).get(i))) {
                    return true;
                }
            }
        }
        visited[curr] = 1;
        return false;
    }

}

//todo
class CourseScheduleII {

    public static void main(String[] args) {
        int numCourses = 2;
//        int [][] prerequisites = new int[][]{new int[]{0, 1}};
//        int[][] prerequisites = new int[][]{new int[]{1, 0}};
        int[][] prerequisites = new int[][]{new int[]{1, 0}, new int[]{0, 1}};

        /*int numCourses = 4;
        int[][] prerequisites = new int[][]{
                new int[] {1,0},new int[] {2,0},
                new int[] {3,1},new int[] {3,2},
        };*/

//        int numCourses = 3;
      /*
        int[][] prerequisites = new int[][]{
                new int[]{0, 1},
                new int[]{2, 1},
        };*/


        System.out.println("Output is " + Arrays.stream(findOrder(numCourses, prerequisites)).boxed().toList());
//        System.out.println("Output is "+ Arrays.stream(findOrder(1, new int[][]{})).boxed().toList());
    }

    public static int[] findOrder(int numCourses, int[][] prerequisites) {
        return topologicalSortMama(numCourses, prerequisites);
    }

    public static int[] topologicalSortMama(int numCourses, int[][] prerequisites) {
        //if(prerequisites.length == 0) return new int[]{};

        HashMap<Integer, List<Integer>> courses = new HashMap<>();

        int[] inDegree = new int[numCourses];
        int[] result = new int[numCourses];

        for (int[] prerequisite : prerequisites) {

            int pre = prerequisite[1];
            int cor = prerequisite[0];

            List<Integer> cl = courses.getOrDefault(pre, new ArrayList<>());
            cl.add(cor);
            courses.put(pre, cl);

            inDegree[cor]++;
        }

        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) if (inDegree[i] == 0) q.offer(i);

        int count = 0;
        while (!q.isEmpty()) {
            Integer vertex = q.poll();
            result[count++] = vertex;

            if (courses.containsKey(vertex))
                for (int e : courses.get(vertex)) {
                    inDegree[e]--;
                    if (inDegree[e] == 0) q.add(e);
                }

        }

        if (count == numCourses) return result;
        return new int[]{};
    }

    public static int[] topologicalSortInsider(int numCourses, int[][] prerequisites) {
        int[] result = new int[numCourses];

        if (prerequisites.length == 0) {

            int count = 0;
            while (numCourses > 0) result[count++] = --numCourses;

            return result;
        }

        HashMap<Integer, List<Integer>> courses = new HashMap<>();
        HashSet<Integer> visited = new HashSet<>();
        Stack<Integer> ordering = new Stack<>();

        for (int[] prerequisite : prerequisites) {

            int pre = prerequisite[1];
            int cor = prerequisite[0];

            List<Integer> cl = courses.getOrDefault(cor, new ArrayList<>());
            cl.add(pre);
            courses.put(cor, cl);


            if (!courses.containsKey(pre)) courses.put(pre, new ArrayList<>());

        }

        try {
            for (Integer vertex : courses.keySet())
                dfs(courses, vertex, ordering, visited, new HashSet<>());

        } catch (CycleEx e) {
            return new int[]{};
        }

        while (!ordering.isEmpty() || numCourses > 0)
            result[--numCourses] = !ordering.isEmpty() ? ordering.pop() : (courses.size() - numCourses);

        return result;
    }

    public static void dfs(HashMap<Integer, List<Integer>> graph, Integer vertex, Stack<Integer> ordering, HashSet<Integer> visited, HashSet<Integer> cycle) {
        if (cycle.contains(vertex)) throw new CycleEx();
        if (visited.contains(vertex)) return;

        visited.add(vertex);
        cycle.add(vertex);

        for (int neighbor : graph.get(vertex))
            dfs(graph, neighbor, ordering, visited, cycle);
        ordering.push(vertex);

    }

    static class CycleEx extends RuntimeException {

    }

}

class RedundantConnection {
    public static void main(String[] args) {
     int[][] edges = new int[][]{
             new int[]{1,2},
             new int[]{1,3},
             new int[]{2,3},
     };

        // int[][] edges = new int[][]{
        //         new int[]{1, 2}, new int[]{2, 3},
        //         new int[]{3, 4}, new int[]{1, 4},
        //             new int[]{1, 5}
        // };
       // System.out.println("Output is " + Arrays.stream(findRedundantConnection(edges)).boxed().toList());
        System.out.println("Expected Output is " + Arrays.stream(findRedundantConnectionNeet(edges)).boxed().toList());
    }

    public static int[] findRedundantConnection(int[][] edges) {
        // A, B, C
        // if A -> B && A -> C
        // single edge cannot be removed
        // common node is a possibility
        //
        HashMap<Integer, HashSet<Integer>> map = new HashMap<>();
        HashSet<List<Integer>> visited = new HashSet<>();
        List<List<Integer>> order = new ArrayList<>();
        List<List<Integer>> answers = new ArrayList<>();

        for (int[] edge : edges)
            for (int j = 0; j < edges[0].length; j++) {
                int node = edge[0];
                int friend = edge[1];


                if (!contains(order, List.of(node, friend))) order.add(List.of(node, friend));

                HashSet<Integer> ls = map.getOrDefault(node, new HashSet<>());
                ls.add(friend);
                map.put(node, ls);

                HashSet<Integer> rs = map.getOrDefault(friend, new HashSet<>());
                rs.add(node);
                map.put(friend, rs);
            }

        Comparator<Map.Entry<Integer, HashSet<Integer>>> size = comparingInt(a -> -a.getValue().size());
        PriorityQueue<Map.Entry<Integer, HashSet<Integer>>> q = new PriorityQueue<>(size);
        q.addAll(map.entrySet());


        while (!q.isEmpty()) {
            Entry<Integer, HashSet<Integer>> entry = q.poll();
            for (Integer node : entry.getValue()) {
                if (!hasBeenDone(node, entry.getKey(), visited)) {

                    if (isConnected(deepCopy(map), edges, entry.getKey(), node)) {
                        List<Integer> list = List.of(node, entry.getKey());

                        visited.add(list);
                        answers.add(list);
                    }
                }
            }
        }

        if (answers.size() > 1) {
            int i = order.size() - 1;
            while (i >= 0) {
                List<Integer> o = order.get(i);
                for (List<Integer> a : answers) if (matches(a, o)) return new int[]{o.get(0), o.get(1)};
                i--;
            }
        } else if (answers.size() == 1) {
            List<Integer> a = answers.get(0);
            for (List<Integer> o : order) if (matches(a, o)) return new int[]{o.get(0), o.get(1)};
        }

        return new int[]{};
    }

    public static boolean isConnected(HashMap<Integer, HashSet<Integer>> map, int[][] edges, int source, int destination) {

        //try rm connection
        int n = edges.length;


        //remove from map
        map.get(source).removeIf(i -> i.equals(destination));
        map.get(destination).removeIf(i -> i.equals(source));

        //check if nodes are still n
        int count = 0;

        for (Map.Entry<Integer, HashSet<Integer>> node : map.entrySet()) {
            if (!node.getValue().isEmpty()) count++;
        }


        return n == count;
    }

    public static boolean hasBeenDone(int source, int destination, HashSet<List<Integer>> visited) {
        for (List<Integer> vs : visited) if (vs.contains(source) && vs.contains(destination)) return true;
        return false;
    }

    public static boolean contains(List<List<Integer>> list, List<Integer> val) {
        for (List<Integer> vs : list) if (vs.contains(val.get(0)) && vs.contains(val.get(1))) return true;
        return false;
    }

    public static boolean matches(List<Integer> op, List<Integer> order) {
        return order.contains(op.get(0)) && order.contains(op.get(1));
    }

    public static HashMap<Integer, HashSet<Integer>> deepCopy(HashMap<Integer, HashSet<Integer>> map) {
        HashMap<Integer, HashSet<Integer>> copy = new HashMap<>();

        for (Map.Entry<Integer, HashSet<Integer>> set : map.entrySet()) {

            HashSet<Integer> val = new HashSet<>(set.getValue());

            copy.put(set.getKey(), val);
        }

        return copy;
    }

    //Neet
    static int[] parent;

    public static int[] findRedundantConnectionNeet(int[][] edges) {
        parent = new int[edges.length];
        //represent themselves
        //i.e. groups of themselves
        for (int i = 0; i < edges.length; i++)
            parent[i] = i + 1;

        for (int[] edge : edges) {
            if (findNeet(edge[0]) == findNeet(edge[1])) return edge;

            //to merge them into same subset
            else unionNeet(edge[0], edge[1]);
        }

        return new int[2];
    }

    public static int findNeet(int x) {
        //we do minus 1 to find the parent in the array as the array starts from 0
        //i.e. in the parent array x is x - 1
        if (x == parent[x - 1])
            return x;

        //find parent of x's parent
        return findNeet(parent[x - 1]);
    }

    public static void unionNeet(int child, int par) {

        //join representatives / parents of both values by making left child of right
        parent[findNeet(par) - 1] = findNeet(child);
    }

}

class WordLadder {
    public static void main(String[] args) {
     //   String beginWord = "hit";
     //   String endWord = "cog";
     //   List<String> wordList = new ArrayList<>(List.of("hot","dot","dog","lot","log","cog"));

//        String beginWord = "hot";
//        String endWord = "dog";
//        List<String> wordList = new ArrayList<>(List.of("hot","dog","dot"));//
        String beginWord = "hit";
        String endWord = "cog";
        List<String> wordList = new ArrayList<>(List.of("hot","dot","tog","cog"));
        System.out.println("Expected Output is "+ladderLengthNeet(beginWord, endWord, wordList));

        System.out.println("Output is "+ladderLength(beginWord, endWord, wordList));
    }

    public static int ladderLength(String beginWord, String endWord, List<String> wordList) {
        if(!wordList.contains(endWord)) return 0;
        int l = beginWord.length();
        HashMap<String, HashSet<String>> streak = new HashMap<>();

        String word = null;

        for(int i = 0; i < wordList.size(); i++) {

            //begin word may not be in wordlist
            if(word == null && (!wordList.get(0).equals(beginWord))) {
                word = beginWord;
                i--;
            } else {
                word = wordList.get(i);
            }


            //calculate transform sequence
            //1. currentWord and nextWord

            for (String w : wordList) {
                if (w.equals(word)) continue;
                int t = weight(w, word, l);
                if (t == 1) {
                    HashSet<String> wordL = streak.getOrDefault(word, new HashSet<>());
                    wordL.add(w);
                    streak.put(word, wordL);
                    //word = w;
                }
            }
        }

        return dijkstrasAlgorithm(beginWord, endWord, streak).length;
    }

    public static int weight(String word1, String word2, int l) {
        //char[] char = new char[26];
        int diff = 0;

        for(int i = 0; i < l; i++) {
            diff += word2.charAt(i) != word1.charAt(i) ? 1 : 0;
        }


        return diff;
    }

    public static int ladderLengthNeet(String beginWord, String endWord, List<String> wordList) {
        Map<String, List<String>> adjlist = new HashMap<>();
        wordList.add(beginWord);
        for(String word : wordList) {
            StringBuilder string=null;
            for(int i=0;i<word.length();i++){
                string = new StringBuilder(word);
                string.setCharAt(i, '*');
                List<String> wordlist = adjlist.getOrDefault(string.toString(), new ArrayList<String>());
                wordlist.add(word);
                adjlist.put(string.toString(), wordlist);
            }
        }

        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        Set<String> visited = new HashSet<>();
        visited.add(beginWord);

        int step = 1;
        StringBuilder string = null;
        while(!queue.isEmpty()) {
            step++;
            int size = queue.size();
            for(int j=0; j<size; j++){
                String str = queue.poll();

                for(int i=0;i<str.length();i++){
                    string = new StringBuilder(str);
                    string.setCharAt(i, '*');
                    for(String pat:adjlist.get(string.toString())){
                        if(pat.equals(endWord)){
                            return step;
                        }
                        if(visited.contains(pat)){
                            continue;
                        }
                        queue.offer(pat);
                        visited.add(pat);
                    }
                }
            }
            // step++;
        }
        return 0;
    }


    private static class Edge {
        public String from;
        public String to;
        public int weight;

        public Edge(String from, String to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

    }

    public static String[] dijkstrasAlgorithm(String start, String end, HashMap<String, HashSet<String>> graph) {
        if (!graph.containsKey(start) || !graph.containsKey(end)) return new String[]{};
        System.out.println(graph);

        String orphan = "~@#$%";
        int l = start.length();
        int[] cost = new int[graph.size()];
        String[] parent = new String[graph.size()];
        boolean[] completed = new boolean[graph.size()];
        HashMap<String, Integer> labelIndex = new HashMap<>();

        int count = 0;
        for(String label : graph.keySet()) {
            labelIndex.put(label, count);
            cost[count] = Integer.MIN_VALUE;
            completed[count] = false;
            parent[count] = orphan;
            count++;
        }

        System.out.println(labelIndex);

        PriorityQueue<Map.Entry<String, Integer>> q = new PriorityQueue<>(Map.Entry.comparingByValue());


        HashMap<String, Integer> m = new HashMap<>();
        m.put(start, cost[labelIndex.get(start)]);
        q.addAll(m.entrySet());
        m.clear();

        while (!q.isEmpty()) {
            Map.Entry<String, Integer> e = q.poll();

            String currentV = e.getKey();

            Integer currentI = labelIndex.get(currentV);
            if(completed[currentI]) continue;
            System.out.println(currentV);
            HashSet<String> neighbour = graph.get(currentV);

            for(String n : neighbour) {
                Integer ni = labelIndex.get(n);

                Integer currentWeight = cost[ni];
                String currentParent = parent[ni];

                String possibleParent = currentV;
                int weightFromPossibleParent = weight(possibleParent, n, l);

                if(currentParent.equals(orphan)) {
                    //set values

                    parent[ni] = possibleParent;
                    cost[ni] = weightFromPossibleParent;
                } else {
                    //compare cost form start vs cost from current

                    //cost form start
                    int costFromStart = 0;
                    String c = currentParent;
                    Integer ci = labelIndex.get(currentParent);

                    while (!c.equals(start)) {
                        int cCost = cost[ci];
                        costFromStart += cCost;
                        c = parent[ci];
                        ci = labelIndex.get(c);
                    }

                    //cost from current
                    int costFromCurrent = 0;
                    c = currentV;
                    ci = currentI;

                    while (!c.equals(start)) {
                        int cCost = cost[ci];
                        costFromCurrent += cCost;
                        c = parent[ci];
                        ci = labelIndex.get(c);
                    }

                    if(costFromCurrent < costFromStart) {
                        parent[ni] = possibleParent;
                        cost[ni] = weightFromPossibleParent;
                    } else {
                        parent[ni] = currentParent;
                        cost[ni] = currentWeight;
                    }

                }

                m.put(n, cost[ni]);
                q.addAll(m.entrySet());
                m.clear();

            }

            completed[currentI] = true;
        }

        String c = end;
        int i = labelIndex.get(c);

        Stack<String> path = new Stack<>();
        path.push(c);



        System.out.println("key : "+labelIndex.keySet().stream().sorted().toList());
        System.out.println("cost : "+Arrays.stream(cost).boxed().toList());
        System.out.println("parent : "+Arrays.stream(cost).boxed().toList());


        while (!c.equals(start)) {
            c = parent[i];
            i = labelIndex.get(c);
            path.push(c);
        }

        String[] minPath = new String[path.size()];
        count = 0;
        while (!path.isEmpty()) {
            minPath[count++] = path.pop();
        }

        return minPath;
    }

    /*private static String[] dijkstrasAlgorithm(String start, String end, HashSet<Edge> graph) {
        if (!graph.containsKey(start) || !graph.containsKey(end)) return new String[]{};

        HashMap<String,HashMap<>>



        String[] minCost = new String[]{};
        return minCost;
    }*/

}

class Interview {
    public static void main(String[] args) {

        var it = new FilterIterator<>(Arrays.stream(new int[]{1, 2, 3, 4, 5}).iterator(), x -> x > 3);

        System.out.println(it.hasNext());
        System.out.println(it.hasNext());

        System.out.println(it.next());
        System.out.println(it.hasNext());

        System.out.println(it.next());
        System.out.println(it.hasNext());

    }

    static class FilterIterator<T> implements Iterator<T> {
        private final Iterator<T> iterator;
        private final Predicate<T> predicate;
        private T next;

        public FilterIterator(Iterator<T> iterator, Predicate<T> predicate) {
            this.iterator = iterator;
            this.predicate = predicate;
        }

        @Override
        public boolean hasNext() {
            if(next != null) return true;
            while (iterator.hasNext()) {
                var temp = iterator.next();
                if(predicate.test(temp)) {
                    next = temp;
                    return true;
                }
            }
            return false;
        }

        @Override
        public T next() {
            if(this.next == null && !hasNext()) throw new NoSuchElementException();
            var temp = next;
            this.next = null;
            return temp;
        }

        public static void sayHi(){}
    }
}

class BestStock {
    public static void main(String[] args) {

        int[] prices = new int[]{7,1,5,3,6,4};
        System.out.println("Output is "+maxProfit(prices));
    }


    public static int maxProfit(int[] prices) {
        int profit = 0;
        if(prices.length == 1) return 0;

        int buy = 0;

        for (int sell = 1; sell < prices.length; sell++) {
            int s = prices[sell];
            int p = prices[sell] - prices[buy];

            if(prices[buy] < prices[sell]) profit = Math.max(profit, p);
            else buy = sell;
        }

        return profit;
    }
}

class CharacterReplacement {
    public static void main(String[] args) {

        String s = "ABAB";
        int  k = 2;

//        String s = "AABABBA";
//       int  k = 1;

//        String s = "ABAA";
//        int k = 0;

        System.out.println("Longest character streak "+characterReplacement(s,k));
    }

    public static int characterReplacement(String s, int k) {

        if(s.length() == 1) return 1;

        int recordLeft = 0;
        int recordRight = 0;
        int record = 1  ;

        int start = 0;
        Integer streak = null;



        //find longest streak
        for(int i = 0; i < s.length() - 1; i++) {

            //save start and end of the longest streak
            char c = s.charAt(i);
            char n = s.charAt(i + 1);

            //new streak starting
            if(streak == null) {
                start = i;
                streak = 1;
            }

            //break streak
            if(c != n || (i == s.length() - 2)) {

                //is longest streak?
                if(streak >= record) {
                    if(i == s.length() - 2 && c == n) {
                        streak++;
                        i++;
                    }

                    record = streak;
                    recordLeft = start;
                }

                //reset
                streak = null;
            } else {
                //continue streak
                streak++;
            }
        }

        //replace adjacent characters with similar
        return longestStreakFromIndex(s, recordLeft,k);
    }

    private static int longestStreakFromIndex(String s, int i, int k) {
        int streak = 1;
        int repl = k;

        char c = s.charAt(i);

        int left = i - 1;
        int right = i + 1;

        //go as left as possible
        while (left >= 0) {
            char n = s.charAt(left);
            if(n != c) {
                if(repl > 0) repl--;
                else break;
            }

            streak++;
            left--;
        }

        //go as right as possible
        while (right < s.length()) {
            char n = s.charAt(right);
            if(n != c) {
                if(repl > 0) repl--;
                else break;
            }
            streak++;
            right++;
        }

        return streak;
    }

}

//slidingWindow
class Permutation {
    public static void main(String[] args) {


        String s1 = "abc";
        String s2 = "ccccbbbbaaaa";

        System.out.println(checkInclusion(s1,s2));

    }

    public static boolean checkInclusion(String s1, String s2) {
        int target = transform(s1);
        int l = s1.length();
        int left = 0;
        int right = left + l - 1;


        //same length &&
        while(right < s2.length()) {
            String test = s2.substring(left, right + 1);

            int tt = transform(test);

            if(tt == target && isAnagram(s1, test)) {
                return true;
            }
            right++;
            left++;
        }

        return false;
    }

    public static int transform(String s) {
        int val = 0;

        for(int i = 0; i < s.length(); i++)
            val += 'a' - s.charAt(i);

        return val;
    }


    public static boolean isAnagram(String original, String test) {
        if (original.length() != test.length()) return false;

        int[] store = new int[26];

        for (int i = 0; i < original.length(); i++) {
            store[original.charAt(i) - 'a']++;
            store[test.charAt(i) - 'a']--;
        }

        for (int n : store) if (n != 0) return false;

        return true;
    }
}

class MinWin {
    public static void main(String[] args) {

//        String s = "obzcopzocynyrsgsarijyxnkpnukkrvzuwdjldxndmnvevpgmxrmvfwkutwekrffnloyqnntbdohyfqndhzyoykiripdzwiojyoznbtogjyfpouuxvumtewmmnqnkadvzrvouqfbbdiqremqzgevkbhyoznacqwbhtrcjwfkzpdstpjswnpiqxjhywjanhdwavajrhwtwzlrqwmombxcaijzevbtcfsdcuovckoalcseaesmhrrizcjgxkbartdtotpsefsrjmvksqyahpijsrppdqpvmuocofuunonybjivbjviyftsyiicbzxnwnrmvlgkzticetyfcvqcbjvbufdxgcmesdqnowzpshuwcseenwjqhgsdlxatamysrohfnixfprdsljyyfhrnnjsagtuihuczilgvtfcjwgdhpbixlzmakebszxbhrdibpoxiwztshwczamwnninzmqrmpsviydkptjzpktksrortapgpxwojofxeasoyvyprjoguhqobehugwdvtzlenrcttuitsiijswpogicjolfxhiscjggzzissfcnxnvgftxvbfzkukqrtalvktdjsodmtgzqtuyaqvvrbuexgwqzwduixzrpnvegddyyywaquxjxrnuzlmyipuqotkghfkpknqinoidifnfyczzonxydtqroazxhjnrxfbmtlqcsfhshjrxwqvblovaouxwempdrrplefnxmwrwfjtebrfnfanvvmtbzjesctdgbsfnpxlwihalyiafincfcwgdfkvhebphtxukwgjgplrntsuchyjjuqozakiglangxkttsczhnswjksnuqwflmumpexxrznzwxurrysaokwxxqkrggytvsgkyfjrewrcvntomnoazmzycjrjrqemimyhriyxgrzcfuqtjhvjtuhwfzhwpljzajitrhryaqchnuawbxhxrpvyqcvhpggrpplhychyulijhkglinibedauhvdydkqszdbzfkzbvhldstocgydnbfjkcnkfxcyyfbzmmyojgzmasccaahpdnzproaxnexnkamwmkmwslksfpwirexxtymkmojztgmfhydvlqtddewjvsrmyqjrpycbmndhupmdqqabiuelacuvxnhxgtpvrtwfgzpcrbhhtikbcqpctlxszgpfbgcsbaaiapmtsucocmpecgixshrrnhyrpalralbccnxvjzjllarqhznzghswqsnfuyywmzbopyjyauknxddgdthlabjqtwxpxwljvoxkpjjpfvccyikbbrpdsyvlxscuoofkecwtnfkvcnzbxkeabtdusyhrqklhaqreupakxkfzxgawqfwsaboszvlshwzhosojjotgyagygguzntrouhiweuomqptfjjqsxlbylhwtpssdlltgubczxslqjgxuqnmpynnlwjgmebrpokxjnbiltvbebyytnnjlcwyzignmhedwqbfdepqakrelrdfesqrumptwwgifmmbepiktxavhuavlfaqxqhreznbvvlakzeoomykkzftthoemqwliednfsqcnbexbimrvkdhllcesrlhhjsspvfupxwdybablotibypmjutclgjurbmhztboqatrdwsomnxnmocvixxvfiqwmednahdqhxjkvcyhpxxdmzzuyyqdjibvmfkmonfxmohhshpkhmntnoplphqyprveyfsmsxjfosmicdsjrieeytpnbhlsziwxnpmgoxneqbnufhfwrjbqcsdfarybzwaplmxckkgclvwqdbpumsmqkswmjwnkuqbicykoisqwoootrdpdvcuiuswfqmrkctsgrevcxnyncmivsxbpbxzxpwchiwtkroqisnmrbmefbmatmdknaklpgpyqlsccgunaibsloyqpnsibwuowebomrmcegejozypjzjunjmeygozcjqbnrpakdermjcckartbcppmbtkhkmmtcngteigjnxxyzaibtdcwutkvpwezisskfaeljmxyjwykwglqlnofhycwuivdbnpintuyhtyqpwaoelgpbuwiuyeqhbvkqlsfgmeoheexbhnhutxvnvfjwlzfmvpcghiowocdsjcvqrdmkcizxnivbianfpsnzabxqecinhgfyjrjlbikrrgsbgfgyxtzzwwpayapfgueroncpxogouyrdgzdfucfrywtywjeefkvtzxlwmrniselyeodysirqflpduvibfdvedgcrzpzrunpadvawfsmmddqzaaahfxlifobffbyzqqbtlcpquedzjvykvarayfldvmkapjcfzfbmhscdwhciecsbdledspgpdtsteuafzbrjuvmsfrajtulwirzagiqjdiehefmfifocadxfuxrpsemavncdxuoaetjkavqicgndjkkfhbvbhjdcygfwcwyhpirrfjziqonbyxhibelinpllxsjzoiifscwzlyjdmwhnuovvugfhvquuleuzmehggdfubpzolgbhwyeqekzccuypaspozwuhbzbdqdtejuniuuyagackubauvriwneeqfhtwkocuipcelcfrcjcymcuktegiikyosumeioatfcxrheklookaqekljtvtdwhxsteajevpjviqzudnjnqbucnfvkybggaybebljwcstmktgnipdyrxbgewqczzkaxmeazpzbjsntltjwlmuclxirwytvxgvxscztryubtjweehapvxrguzzsatozzjytnamfyiitreyxmanhzeqwgpoikcjlokebksgkaqetverjegqgkicsyqcktmwjwakivtsxjwrgakphqincqrxqhzbcnxljzwturmsaklhnvyungjrxaonjqomdnxpnvihmwzphkyuhwqwdboabepmwgyatyrgtboiypxfavbjtrgwswyvcqhzwibpisydtmltbkydhznbsvxktyfxopwkxzbftzknnwipghuoijrbgqnzovxckvojvsqqraffwowfvqvfcmiicwitrhxdeombgesxexedlakitfovtydxunqnwqqdeeekiwjnwoshqcsljiicgobbbuqakjdonjawgjlezdnqhfdqnmsuavxdpnfzwipmspiabveaarshzwxmirgkmfncvtdrdvfxkpxlkdokxgtwcskmjryyymcthfnkasinihaunohkxaibtsqelockaefjmsuolebtnepauwmrxutspjwaxbmahsjtkfkxlnszribmeofbkyvbjscjtqjakuwvcgunvnonvqbbggfshauqsyznokqbhowjusypfnecffenojfvlblgzntqzlrgzprvhqnpfrrkzxznieiuivajivzijsqijigtatifmbplzqahuidegfoobpymkputzamzvweiyvvzlwihgmmmrcburbgbsdxrfjsbiylitghgcpqjbevvgypxcybubyoijijrhuzcdijfybqbfowlookqmlnplbxvjjosfqviygqyhgamuwzjklbyzopkrnhbywtfoqomweldmlrhjqswctubiknzzvcztyehouvnyiqnvkufaobehxhrjvtisxjlxoumipzjarwvbsaegdkpbsjmpevjbewzuqnfhoohhmdjgfpmjzdmtmykqvtucptwfidpwtwffzolffzqfdearclkyeecuzabjeqhxpmfodsvisnpxrqowdawheydfyhoexvcmihdlzavtqlshdhdgjzpozvvackebhgqppvcrvymljfvooauxcjnbejdivikcoaugxwzsulgfqdtefpehbrlhaoqxwcancuvbqutnfbuygoemditeagmcveatgaikwflozgdhkyfqmjcruyyuemwbqwxyyfiwnvlmbovlmccaoguieu";
//        String t = "cjgamyzjwxrgwedhsexosmswogckohesskteksqgrjonnrwhywxqkqmywqjlxnfrayykqotkzhxmbwvzstrcjfchvluvbaobymlrcgbbqaprwlsqglsrqvynitklvzmvlamqipryqjpmwhdcsxtkutyfoiqljfhxftnnjgmbpdplnuphuksoestuckgopnlwiyltezuwdmhsgzzajtrpnkkswsglhrjprxlvwftbtdtacvclotdcepuahcootzfkwqhtydwrgqrilwvbpadvpzwybmowluikmsfkvbebrxletigjjlealczoqnnejvowptikumnokysfjyoskvsxztnqhcwsamopfzablnrxokdxktrwqjvqfjimneenqvdxufahsshiemfofwlyiionrybfchuucxtyctixlpfrbngiltgtbwivujcyrwutwnuajcxwtfowuuefpnzqljnitpgkobfkqzkzdkwwpksjgzqvoplbzzjuqqgetlojnblslhpatjlzkbuathcuilqzdwfyhwkwxvpicgkxrxweaqevziriwhjzdqanmkljfatjifgaccefukavvsfrbqshhswtchfjkausgaukeapanswimbznstubmswqadckewemzbwdbogogcysfxhzreafwxxwczigwpuvqtathgkpkijqiqrzwugtr";

//        String s = "ADOBECODEBANC";
//        String t = "ABC";

//        String s = "a";
//        String t = "a";

        String s = "bba";
        String t = "ab";

//        String s =  "cabwefgewcwaefgcf";
//        String t = "cae";

        System.out.println(minWindowTicksOptimize(s, t));
    }

    public static String minWindow(String s, String t) {
        //min length of window = t.length();
        if(t.length() > s.length()) return "";


        //store all windows
        //order of min
        PriorityQueue<String> w = new PriorityQueue<>(comparingInt(String::length));


        //traverse s to find t
        //left & right pointer
        //skip anything t doesn't have
        int left = 0;
        int right = t.length();
        List<Character> tChar = new ArrayList<>();

        //populate char list
        for(Character c : t.toCharArray()) tChar.add(c);

        //traverse
        while (right <= s.length()) {
            String sub = s.substring(left, right);
            if(sub.length() < t.length()) break;
            if(valid(sub, new ArrayList<>(tChar))) {
                w.offer(sub);
                left++;
            } else {
               right++;
            }
        }

        //get minimum size
        return w.isEmpty() ? "" : w.poll();
    }

    public static String minWindowTicks(String s, String t) {
        //min length of window = t.length();
        if (t.length() > s.length()) return "";

        //store all windows
        //order of min
        PriorityQueue<int[]> w = new PriorityQueue<>(Comparator.comparingInt((set) -> (set[1] - set[0])));


        List<Character> tArray = new ArrayList<>();
        List<Integer> stMap = new ArrayList<>();

        //populate char list
        for (Character c : t.toCharArray()) tArray.add(c);

        for (int i = 0; i < s.length(); i++) if(tArray.contains(s.charAt(i))) stMap.add(i);
        if(stMap.isEmpty()) return "";


        for(int start = 0; start < stMap.size(); start++) {

            for(int end = start; end < stMap.size(); end++) {
                String sub = s.substring(stMap.get(start), stMap.get(end) + 1);
                if(sub.length() < t.length()) continue;
                if(valid(sub, new ArrayList<>(tArray))) {
                    w.offer(new int[]{stMap.get(start), stMap.get(end)});
                    break;
                }
            }
        }


        //get minimum size
        return w.isEmpty() ? "" : s.substring(w.peek()[0], w.peek()[1] + 1);
    }

    public static String minWindowTicksOptimize(String s, String t) {
        //min length of window = t.length();
        if (t.length() > s.length()) return "";

        //store all windows
        //order of min
        String window = "";

        HashMap<Character, Integer> need = new HashMap<>();
        HashMap<Character, Integer> has = new HashMap<>();

        //populate need list
        for (Character c : t.toCharArray()) {
            need.put(c, need.getOrDefault(c, 0) + 1);
            has.put(c, 0);
        }

        int start = 0;
        int end = 0;
        boolean expand = true;

        while (start < s.length()) {

            if(expand) {
                Character next = s.charAt(end);
                if(need.containsKey(next)) has.put(next, has.get(next) + 1);
            } else {

                //min of 0
                //reduce occurrence
                Character prev = s.charAt(start - 1);
                if(need.containsKey(prev)) has.put(prev, Math.max(0, has.get(prev) - 1));
            }


            if (valid(has, need)) {
                if (window.equals("") || (end - start) < window.length() - 1) window = s.substring(start, end + 1);

                //shrink window
                start++;
                expand = false;
            } else {

                //expand window
                if(end + 1 != s.length()) {
                    end++;
                   expand = true;
                }

                //no more expanding window
                else break;
            }
        }

        //get minimum size

        return window;
    }



    private static boolean valid(HashMap<Character, Integer> has, HashMap<Character, Integer> need) {
        for(Map.Entry<Character, Integer> e : has.entrySet()) {
            Integer have = e.getValue();
            Integer want = need.get(e.getKey());

            if(have < want) return false;
        }
        return true;
    }

    private static boolean valid(String subString, List<Character> t) {
        if (subString.length() < t.size()) return false;
        for(Character c : subString.toCharArray()) t.remove(c);
        return t.isEmpty();
    }


}


class MaxWindow {
    public static void main(String[] args) {
        int[] nums = new int[]{1,3,-1,-3,5,3,6,7};
        int k = 3;
//        int[] nums = new int[]{1};
//        int k = 1;
        System.out.println(Arrays.stream(maxSlidingWindow4(nums,k)).boxed().toList());
    }

    // prio que
    public static int[] maxSlidingWindow(int[] nums, int k) {
        List<Integer> list = new ArrayList<>();
        int end = k - 1;
        int start = 0;

        List<Integer> l = Arrays.stream(nums).boxed().toList();

        //slide window
        while(end < nums.length) {

            //max b4 slide
            PriorityQueue<Integer> q = new PriorityQueue<>(Comparator.comparingInt(a-> -a));
            q.addAll(l.subList(start, end + 1));
            list.add(q.poll());

            //slide
            start++;
            end++;
        }



        int[] res = new int[list.size()];
        for(int i = 0; i < list.size(); i++) res[i] = list.get(i);

//        list.stream().mapToInt(i->i).toArray();
        return res;
    }

    //sort
    public static int[] maxSlidingWindow2(int[] nums, int k) {
        List<Integer> list = new ArrayList<>();
        int end = k - 1;
        int start = 0;

        List<Integer> l = Arrays.stream(nums).boxed().toList();

        //slide window
        while(end < nums.length) {

            //max b4 slide
            List<Integer> integers = l.subList(start, end + 1).stream().sorted().toList();
            list.add(integers.get(integers.size() - 1));

            //slide
            start++;
            end++;
        }



        int[] res = new int[list.size()];
        for(int i = 0; i < list.size(); i++) res[i] = list.get(i);

//        list.stream().mapToInt(i->i).toArray();
        return res;
    }

    //sort 3& priority
    public static int[] maxSlidingWindow3(int[] nums, int k) {
        List<Integer> list = new ArrayList<>();
        int end = k - 1;
        int start = 0;

        List<Integer> sub = new ArrayList<>();
        LinkedHashMap<Integer, List<Integer>> map = new LinkedHashMap<>();
        LinkedHashMap<Integer, List<Integer>> orderMap = new LinkedHashMap<>();
        PriorityQueue<Map.Entry<Integer, List<Integer>>> q = new PriorityQueue<>(Map.Entry.comparingByKey(Comparator.comparingInt(a -> -a)));


        for (int i = 0; i < nums.length; i++) {
            sub.add(nums[i]);
            int finalI = i;
            map.compute(nums[i], (key, value) -> {
                if(value == null) value = new ArrayList<>();
                value.add(finalI);
                return value;
            });
        }

        q.addAll(map.entrySet());


        while (!q.isEmpty()) {
            Entry<Integer, List<Integer>> poll = q.poll();
            orderMap.put(poll.getKey(), poll.getValue());
        }

        while (end < nums.length) {
            List<Integer> subList = sub.subList(start, end + 1);
            for (Integer key : orderMap.keySet()) {
                if(subList.contains(key)) {
                    list.add(key);
                    break;
                }
            }

            end++;
            start++;
        }




        int[] res = new int[list.size()];
        for(int i = 0; i < list.size(); i++) res[i] = list.get(i);

//        list.stream().mapToInt(i->i).toArray();
        return res;
    }


    public static int[] maxSlidingWindow4(int[] nums, int k) {
        List<Integer> list = new ArrayList<>();
        int end = k - 1;
        int start = 0;

        //List<Integer> l = Arrays.stream(nums).boxed().toList();

        //slide window
        while(end < nums.length) {

            int max = Integer.MIN_VALUE;
            //max b4 slide
            for(int i = start; i <= end; i++) max = Math.max(max, nums[i]);
            list.add(max);

            //slide
            start++;
            end++;
        }

        int[] res = new int[list.size()];
        for(int i = 0; i < list.size(); i++) res[i] = list.get(i);

//        list.stream().mapToInt(i->i).toArray();
        return res;
    }

}


