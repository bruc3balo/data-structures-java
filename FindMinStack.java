import java.util.Stack;

class FindMinStack {
    public static void main(String[] args) {

        MinStack minStack = new MinStack(); //MinStack //null
        minStack.push(2147483646); //push //null
        minStack.push(2147483646); // push //null
        minStack.push(2147483647); // push // null
        System.out.println(minStack.top() + " top" + " :: expected 2147483647"); //top //2147483647
        minStack.pop(); //pop //null
        System.out.println(minStack.getMin() + " min :: expected 2147483646"); //getMin //2147483646
        minStack.pop(); // pop //null
        System.out.println(minStack.getMin() + " min :: expected 2147483646"); //getMin //2147483646
        minStack.pop(); // pop //null
        minStack.push(2147483647); //push //null
        System.out.println(minStack.top() + " top :: expected 2147483647"); //top //2147483647
        System.out.println(minStack.getMin() + " min :: expected 2147483647"); //getMin //2147483647
        minStack.push(-2147483648); //push //null
        System.out.println(minStack.top() + " top :: expected -2147483648"); //top //-2147483648
        System.out.println(minStack.getMin() + " min :: expected -2147483648"); //getMin //-2147483648
        minStack.pop(); // pop // null
        System.out.println(minStack.getMin() + " min expected 2147483647"); //getMin // 2147483647

    }

    static class MinStack {

        private Node top;
        private final Stack<Integer> min;

        public MinStack() {
            min = new Stack<>();
        }

        public void push(int val) {
            Node newTop = new Node(val);
            newTop.setNext(top);
            this.top = newTop;
            min.push(min.isEmpty() ? val : Math.min(min.peek(), val));
        }

        public void pop() {
            this.top = top.hasNext() ? top.getNext() : null;
            this.min.pop();
        }

        public int top() {
            return top.getValue();
        }

        public int getMin() {
            return min.peek();
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            Node n = top;
            sb.append("[");
            appendChildToPrint(sb, n);
            sb.append("]");
            return sb.toString();
        }

        private void appendChildToPrint(StringBuilder sb, Node n) {
            sb.append(" ").append(n.getValue()).append(",");
            if (n.hasNext()) {
                appendChildToPrint(sb, n.getNext());
            }
        }

        private static class Node {
            private Node next;
            private final int value;

            Node(int value) {
                this.value = value;
            }

            public int getValue() {
                return value;
            }

            public boolean hasNext() {
                return next != null;
            }

            public Node getNext() {
                return next;
            }

            public void setNext(Node next) {
                this.next = next;
            }

            public void removeNext() {
                this.next = null;
            }

            @Override
            public String toString() {
                return "Node{" +
                        "next=" + next +
                        ", value=" + value +
                        '}';
            }
        }
    }
}
