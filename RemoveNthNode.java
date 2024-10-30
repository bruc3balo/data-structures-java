import java.util.ArrayList;

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
            if (current == toDelete) {
                if (prev == null) {
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
