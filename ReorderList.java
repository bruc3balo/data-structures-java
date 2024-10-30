import java.util.ArrayList;
import java.util.List;

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
