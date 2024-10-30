import java.util.HashSet;

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
