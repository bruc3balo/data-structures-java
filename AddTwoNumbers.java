import java.math.BigInteger;

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
