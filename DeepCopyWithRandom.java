import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

class DeepCopyWithRandom {
    static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }

        public Node deepCopy() {
            HashMap<Node, Node> originalAndCopy = new HashMap<>();
            Node temp = this;


            //copy original side my side with new
            while (temp != null) {
                originalAndCopy.put(temp, new Node(temp.val));
                temp = temp.next;
            }

            temp = this;
            //populate next and random with
            System.out.println(originalAndCopy);
            while (temp != null) {
                //key is copy                    //value is a reference of copy
                originalAndCopy.get(temp).next = originalAndCopy.get(temp.next);
                originalAndCopy.get(temp).random = originalAndCopy.get(temp.random);
                temp = temp.next;
            }

            return originalAndCopy.get(this);
        }

        @Override
        public String toString() {
            List<List<Integer>> values = new ArrayList<>();
            var temp = this;
            while (temp != null) {
                values.add(Arrays.asList(temp.val, temp.random != null ? temp.random.val : null));
                temp = temp.next;
            }

            return values.toString();
        }
    }

    static Node insertListIntoNode(List<List<Integer>> valRandom) {
        Node dummy = new Node(0);
        Node temp = dummy;

        for (List<Integer> ints : valRandom) {
            Integer val = ints.get(0);
            Integer random = ints.get(1);

            var node = new Node(val);
            node.random = random == null ? null : new Node(random);
            temp.next = node;
            temp = node;
        }

        return dummy.next;
    }

    public static void main(String[] args) {
        Node head = insertListIntoNode(List.of(Arrays.asList(7, null), List.of(13, 0), List.of(11, 4), List.of(10, 2), List.of(1, 0)));
        System.out.println("Deep copy is " + head.deepCopy().toString());
    }
}
