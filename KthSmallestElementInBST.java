import java.util.ArrayList;

public class KthSmallestElementInBST {


    public int kthSmallest(TreeNode root, int k) {
        ArrayList<Integer> n = new ArrayList<>();
        dfs(root, n, k);
        return n.get(k - 1);
    }

    public void dfs(TreeNode root, ArrayList<Integer> n, int k) {
        if (root == null || n.size() == k) return;

        //L
        dfs(root.left, n, k);

        //N
        n.add(root.getValue());

        //R
        dfs(root.right, n, k);
    }

}
