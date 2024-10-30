import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class GoodNodes {
    public static void main(String[] args) {
        MyTree tree = new MyTree(new TreeNode(3));
        tree.insert(2);
        tree.insert(4);
        tree.insert(5);
        tree.insert(6);
        tree.insert(7);

        System.out.println("Output is " + goodNodes(tree.root));

        HashMap<Integer, Boolean> v = new HashMap<>();

    }


    private static HashMap<TreeNode, List<Integer>> path = new HashMap<>();
    private static List<Integer> previous = new ArrayList<>();

    private static int goodNodes(TreeNode root) {
        int count = 0;
        if (root == null) return count;
        //root is always good
        //if(root.left == null && root.right == null) return ++count;

        dsf(root);

        for (Map.Entry<TreeNode, List<Integer>> e : path.entrySet()) {
            if (isGoodNode(e.getKey(), e.getValue())) count++;
        }

        return count;
    }

    private static boolean isGoodNode(TreeNode node, List<Integer> nPath) {
        for (int n : nPath) {
            if (n > node.getValue()) {
                return false;
            }
        }

        return true;
    }

    private static void dsf(TreeNode node) {
        if (node == null) return;

        path.put(node, new ArrayList<>(previous));
        previous.add(node.getValue());

        dsf(node.left);
        dsf(node.right);

        if (!previous.isEmpty()) previous.removeIf(i -> i.equals(node.getValue()));
    }


}
