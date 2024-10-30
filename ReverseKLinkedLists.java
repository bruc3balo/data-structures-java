import java.util.Stack;

class ReverseKLinkedLists {
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
