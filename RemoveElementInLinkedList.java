public class RemoveElementInLinkedList {
    /**
     * Definition for singly-linked list.
     * public class ListNode {
     * int val;
     * ListNode next;
     * ListNode() {}
     * ListNode(int val) { this.val = val; }
     * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     * }
     */

    public ReverseLinkedList.ListNode removeElements(ReverseLinkedList.ListNode head, int val) {

        //Early Exit
        if (head == null) return null;

        ReverseLinkedList.ListNode previous = null;
        ReverseLinkedList.ListNode result = null;

        //Traverse the list
        while (head != null) {

            //Find new head
            if (previous == null) {

                //Found new head
                if (head.val != val) {
                    previous = head;

                    //Result is new head i.e. modify previous while result points to head
                    result = previous;
                }

                //Move to next element
                head = head.next;
                continue;
            }

            //assert (previous != null)

            if (head.val == val) {
                //Skip current node i.e. previous is the working list
                previous.next = head.next;
            } else {

                //Move pointer forward i.e. don't update next
                previous = head;
            }

            //Move to next element
            head = head.next;
        }

        return result;
    }

}
