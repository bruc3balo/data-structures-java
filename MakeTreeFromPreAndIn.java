import java.util.Arrays;

class MakeTreeFromPreAndIn {
    public static void main(String[] args) {

        int[] preorder = new int[]{3, 9, 20, 15, 7};
        int[] inorder = new int[]{9, 3, 15, 20, 7};

        System.out.println("Output is " + buildTree(preorder, inorder));

    }

    public static TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder.length == 0 || inorder.length == 0) return null;

        //1st preorder is the root of current subtree
        int r = preorder[0];

        //root of subtree divides subtree into left and right in inorder
        int mid = findMid(r, inorder);

        //create root of subtree
        TreeNode root = new TreeNode(r);

        //give left of inorder to (LNR) to left subtree
        //preorder exclude root and till
        //inorder from first to before root
        root.left = inPre(Arrays.copyOfRange(preorder, 1, mid + 1), Arrays.copyOfRange(inorder, 0, mid));

        //give right of inorder to (LNR) to right subtree
        //preorder from mid and till end
        //inorder from after mid to end i.e exclude done val
        root.right = inPre(Arrays.copyOfRange(preorder, mid + 1, preorder.length), Arrays.copyOfRange(inorder, mid + 1, inorder.length));
        return root;
    }

    public static TreeNode inPre(int[] preorder, int[] inorder) {
        if (preorder.length == 0 || inorder.length == 0) return null;

        //1st preorder is the root of current subtree

        int root = preorder[0];

        //root of subtree divides subtree into left and right in inorder
        int mid = findMid(root, inorder);

        //create root of subtree
        TreeNode parent = new TreeNode(root);

        //give left of inorder to (LNR) to left subtree
        //preorder exclude root and go till mid i.e. 1 -> mid left subtree
        //inorder from first to before root i.e. exclude mid
        parent.left = inPre(Arrays.copyOfRange(preorder, 1, mid + 1), Arrays.copyOfRange(inorder, 0, mid));

        //give right of inorder to (LNR) to right subtree
        //preorder from mid and till end i.e. mid + 1 > end right subtree
        //inorder from after mid to end i.e exclude mid
        parent.right = inPre(Arrays.copyOfRange(preorder, mid + 1, preorder.length), Arrays.copyOfRange(inorder, mid + 1, inorder.length));

        return parent;
    }

    public static int findMid(int mid, int[] arr) {

        for (int i = 0; i < arr.length; i++) {
            if (mid == arr[i]) return i;
        }

        return 0;
    }
}
