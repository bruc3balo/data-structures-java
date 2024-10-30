import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

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
