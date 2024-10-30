import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

class ValidBTS {

    private static MyTree tree = new MyTree(new TreeNode(3));

    public static void main(String[] args) {
        //[3,1,5,0,2,4,6]

        tree.insert(1);
        tree.insert(5);
        tree.insert(0);
        tree.insert(2);
        tree.insert(4);
        tree.insert(6);

        // System.out.println("Output is "+dfsNeet(tree.root,null,null));
        System.out.println("Good nodes " + goodNodes(tree.root, tree.root.getValue()));
    }


    private static boolean dfsNeet(TreeNode node, Integer min, Integer max) {
        //todo bado sijashika
        if (node == null) return true;


        //right site
        //1.Left is less than current
        //2.Right is more than current
        //3.More than root
        //4.Root is min
        if (min != null && node.getValue() <= min) return false;

        //left side
        //1.Left bound is less than current
        //2.Right is more than current
        //3.Less than root
        //4.Root is max
        if (max != null && node.getValue() >= max) return false;


        //left lower bound is
        return dfsNeet(node.left, min, node.getValue()) && dfsNeet(node.right, node.getValue(), max);
    }

    public static int goodNodes(TreeNode node, int max) {

        if (node == null) return 0;

        int x = node.getValue();

        //good node (+1) or bad node (+0)
        int newCount = x >= max ? 1 : 0;

        // count max
        max = Math.max(max, node.getValue());

        //dfs left
        newCount += goodNodes(node.left, max);

        //dfs right
        newCount += goodNodes(node.right, max);

        return newCount;
    }

    private static List<Boolean> validPaths = new ArrayList<>();
    //List<Integer> path = new ArrayList<>();
    private static HashSet<Integer> dupl = new HashSet<>();
    private static TreeNode root;

    public static boolean isValidBST(TreeNode root) {
        if (root == null) return true;
        ValidBTS.root = root;
        dsf(root, true);
        return !validPaths.contains(false);
    }

    public static void dsf(TreeNode node, boolean left) {
        //base case
        if (node == null) return;

        //duplicate
        if (!dupl.add(node.getValue())) validPaths.add(false);

            //compare previous nodes in path
            //else validPaths.add(isValid(node, path, left));
        else validPaths.add(isSubTreeValid(node, left));


        //add node to path for next
        //path.add(node.val);

        //check left child first all the way to the end
        dsf(node.left, node == root ? true : left);

        //then check right child from the end coming up
        dsf(node.right, node == root ? false : left);

        //remove current node from path i.e. latest
        //if(!path.isEmpty()) path.removeIf(i-> i.equals(node.val));
        //if(!path.isEmpty()) path.remove(path.size() - 1);
    }

    public static boolean isSubTreeValid(TreeNode node, boolean isLeft) {
        if (node == null) return true;
        boolean parent = parentTest(node, root, isLeft);
        boolean left = node.left == null || leftTest(node.right, node.left, root, node, node == root || isLeft);
        boolean right = node.right == null || rightTest(node.right, node.left, root, node, node != root && isLeft);

        System.out.print("\n Node : " + node.getValue() + " || parent -> " + parent + " || left -> " + left + " || right -> " + right + " = = = " + (left && right && parent) + " \n");

        return left && right && parent;
    }

    public static boolean parentTest(TreeNode parent, TreeNode root, boolean isLeft) {
        //isRoot test
        if (parent == root) return true;

        int p = parent.getValue();
        int rt = root.getValue();

        //root test
        boolean rootTest = isLeft ? (p < rt) : (p > rt);

        return rootTest;

    }

    public static boolean rightTest(TreeNode right, TreeNode left, TreeNode root, TreeNode parent, boolean isLeft) {
        int r = right.getValue();
        int p = parent.getValue();
        int rt = root.getValue();

        //parent test
        boolean parentTest = r > p;

        //root test
        boolean rootTest = isLeft ? (r < rt) : (r > rt);


        //left test
        boolean leftTest = left == null || (r > left.getValue());

        System.out.print("\n Right Node : " + r + " || Parent -> " + parentTest + " || root -> " + rootTest + " || left -> " + leftTest);

        return parentTest && rootTest && leftTest;
    }

    public static boolean leftTest(TreeNode right, TreeNode left, TreeNode root, TreeNode parent, boolean isLeft) {
        int l = left.getValue();
        int p = parent.getValue();
        int rt = root.getValue();

        //parent test
        boolean parentTest = l < p;

        //root test
        boolean rootTest = isLeft ? (l < rt) : (l > rt);

        //right test
        boolean rightTest = right == null || (l < right.getValue());

        System.out.print("\n Left Node : " + l + " || Parent -> " + parentTest + " || root -> " + rootTest + " || left -> " + rightTest);

        return parentTest && rootTest && rightTest;
    }

}
