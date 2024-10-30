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
