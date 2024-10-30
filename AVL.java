class AVL {
    public static void main(String[] args) {
        MyTree avl = new MyTree(new TreeNode(50), true);
        avl.root.right = new TreeNode(60);
        avl.root.left = new TreeNode(40);
        avl.root.left.left = new TreeNode(30);

        avl.insert(10);
        System.out.println(avl.isBalanced());
    }
}
