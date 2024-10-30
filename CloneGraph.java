import java.util.*;

class CloneGraph {

    private static class Node {
        public int val;
        public List<Node> neighbors;

        public Node() {
            val = 0;
            neighbors = new ArrayList<Node>();
        }

        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<Node>();
        }

        public Node(int _val, ArrayList<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }

    public Node cloneGraph(Node node) {
        if (node == null) return null;

        HashMap<Integer, List<Integer>> all = new HashMap<>();

        Stack<Node> stack = new Stack<>();
        HashSet<Integer> seen = new HashSet<>();

        stack.push(node);

        while (!stack.isEmpty()) {
            Node n = stack.pop();

            if (!seen.contains(n.val)) {
                all.put(n.val, n.neighbors.stream().map(i -> i.val).toList());
                seen.add(n.val);
            }

            for (Node child : n.neighbors) {
                if (!seen.contains(child.val)) {
                    stack.push(child);
                }
            }

        }

        return getClone(all, seen);
    }

    public Node getClone(HashMap<Integer, List<Integer>> all, HashSet<Integer> make) {
        Node clone = null;

        HashMap<Integer, Node> nodeHashMap = new HashMap<>();
        for (Integer n : make) nodeHashMap.put(n, new Node(n));

        for (Map.Entry<Integer, Node> nodeEntry : nodeHashMap.entrySet()) {
            Node node = nodeEntry.getValue();
            if (clone == null) clone = node;
            for (Integer n : all.get(node.val)) addNeighbour(node, nodeHashMap.get(n));
        }

        return clone;
    }

    public void addNeighbour(Node source, Node destination) {
        boolean src = true;
        boolean dst = true;

        for (Node s : source.neighbors)
            if (s.val == destination.val) {
                src = false;
                break;
            }
        for (Node s : destination.neighbors)
            if (s.val == source.val) {
                dst = false;
                break;
            }

        if (src) source.neighbors.add(destination);
        if (dst) destination.neighbors.add(source);
    }

    public Node cloneGraph2(Node node) {
        if (node == null) return null;


        HashMap<Node, Node> newNodes = new HashMap<>();


        Stack<Node> stack = new Stack<>();
        HashSet<Integer> seen = new HashSet<>();
        stack.push(node);

        while (!stack.isEmpty()) {
            Node n = stack.pop();

            if (!seen.contains(n.val)) {
                seen.add(n.val);
                newNodes.put(n, new Node(n.val));
            }

            for (Node child : n.neighbors) {
                if (!seen.contains(child.val)) {
                    stack.push(child);
                }
            }

        }

        for (Node old : newNodes.keySet()) {
            Node clone = newNodes.get(old);

            for (Node n : old.neighbors) {
                clone.neighbors.add(newNodes.get(n));
            }
        }

        return newNodes.values().stream().findFirst().orElse(null);
    }

}
