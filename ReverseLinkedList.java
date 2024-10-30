import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
        ListNode head;
        ListNode pointer = head = null;
        for (int i : arr) {
            var node = new ListNode(i);
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
