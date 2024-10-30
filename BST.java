class BST {
    public static void main(String[] args) {
        //MyTree myTree = new MyTree(new TreeNode(30));
        //myTree.findByValue(30).value = null;

        //myTree.insert(18);
        //myTree.insert(45);

        //myTree.insert(10);
        // myTree.insert(42);
        //myTree.insert(67);

        //myTree.deleteByValue(45);

        //myTree.printLevelOrder(MyTree.TraversalMethods.BFS);

        //System.out.println("Find by value "+myTree.findByValue(48));

        // System.out.println("Height of tree is "+myTree.getTreeHeight());

        // System.out.println("is balanced "+myTree.isBalanced());

        //  System.out.println("depth of 10 "+myTree.getNodeDepth(myTree.findByValue(10)));


        // System.out.println(myTree);

        MyTree myTree = new MyTree(new TreeNode(6));
        myTree.insert(2);
        myTree.insert(8);
        myTree.insert(0);
        myTree.insert(4);
        myTree.insert(7);
        myTree.insert(9);
        myTree.insert(3);
        myTree.insert(5);

        System.out.println("Diameter is " + myTree.getDiameter());

        System.out.println("Balanced is " + myTree.isBalanced());

        TreeNode p = myTree.findByValue(2);
        TreeNode q = myTree.findByValue(4);

        System.out.println("LCA is " + myTree.getLowestCommonAncestor(p, q));
    }

}
