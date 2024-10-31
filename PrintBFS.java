import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class PrintBFS {
    public static void main(String[] args) {
        MyTree tree = new MyTree(new TreeNode(3));
        tree.insert(9);
        tree.insert(20);
        tree.insert(15);
        tree.insert(7);

        HashMap<Integer, List<Integer>> result = new HashMap<>();

//        result.values().stream().toList()

        levelOrder(tree.root);

        tree.printLevelOrder(MyTree.TraversalMethods.BFS);

        System.out.println(result);
    }

    private static final List<List<Integer>> result = new ArrayList<>();

    private static List<List<Integer>> levelOrder(TreeNode root) {
        if (root != null) {
            result.add(List.of(root.getValue()));
            bfs(root);
        }

        return result;
    }

    private static void bfs(TreeNode node) {
        List<Integer> level = new ArrayList<>();
        if (node.left != null) level.add(node.left.getValue());
        if (node.right != null) level.add(node.right.getValue());

        if (node.left != null) bfs(node.left);
        if (node.right != null) bfs(node.right);

        result.add(level);
    }

}
