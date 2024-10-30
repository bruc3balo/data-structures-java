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
