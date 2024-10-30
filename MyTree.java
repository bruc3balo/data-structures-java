import java.util.Arrays;
import java.util.List;

class MyTree {

    TreeNode root;
    boolean avl = false;
    private int diameter = -1;

    public MyTree(TreeNode root) {
        this.root = root;
    }

    public MyTree(TreeNode root, boolean avl) {
        this.root = root;
        this.avl = avl;
    }

    public void insert(Integer value) {
        if (avl) avlInsert(root, new TreeNode(value));
        else insertNode(value, root);
    }

    //todo
    @SuppressWarnings("SuspiciousNameCombination")
    private TreeNode rightRotate(TreeNode topNode) {
        //T
        //M
        //B


        TreeNode midNode = topNode.left;
        TreeNode bottomNode = midNode.right;

        //right rotation
        midNode.right = topNode;
        //M
        //T
        //B


        topNode.left = bottomNode;
        return midNode;
    }

    //todo
    @SuppressWarnings("SuspiciousNameCombination")
    private TreeNode leftRotate(TreeNode topNode) {
        //T
        //M
        //B
        TreeNode midNode = topNode.right;
        TreeNode bottomNode = midNode.left;

        //left rotation
        midNode.left = topNode;
        //M
        //T
        //B


        topNode.right = bottomNode;
        return midNode;
    }

    @SuppressWarnings("SuspiciousNameCombination")
    private TreeNode avlInsert(TreeNode node, TreeNode newNode) {
        if (node == null) {
            node = newNode;
            return node;
        } //base case

        //left
        if (newNode.getValue() < node.getValue()) node.left = avlInsert(node.left, newNode);

            //right
        else if (newNode.getValue() > node.getValue()) node.right = avlInsert(node.right, newNode);
            //duplicate
        else throw new IllegalStateException("Duplicate values not allowed");

        //balancing
        Imbalance imbalance = Imbalance.whichInsert(newNode, node);

        //assign (broken link node) rotated tree to parent
        switch (imbalance.getRotation()) {
            case LEFT -> {
                return leftRotate(node);
            }
            case RIGHT -> {
                return rightRotate(node);
            }
            case LEFT_RIGHT -> {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }
            case RIGHT_LEFT -> {
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
            case BALANCED -> {
            }
        }
        return node;
    }

    private void insertNode(Integer value, TreeNode parent) {
        //by parent value
        if (parent.getValue() == value) throw new IllegalStateException("Duplicate values not allowed");
        if (value < parent.getValue()) {
            if (parent.hasLeftChild()) insertNode(value, parent.left);
            else parent.left = new TreeNode(value);
        } else {
            if (parent.hasRightChild()) insertNode(value, parent.right);
            else parent.right = new TreeNode(value);
        }
    }

    private void callPreOrderPrint(StringBuffer buffer) {
        preOrderPrint(root, buffer);
    }

    private void preOrderPrint(TreeNode subTree, StringBuffer buffer) {

        //N
        buffer.append(" ").append(subTree.getValue());

        if (subTree.hasNoChild()) return; //base case

        //L
        if (subTree.hasLeftChild()) preOrderPrint(subTree.left, buffer);

        //R
        if (subTree.hasRightChild()) preOrderPrint(subTree.right, buffer);

    }

    private void callInOrderPrint(StringBuffer buffer) {
        inOrderPrint(root, buffer);
    }

    private void inOrderPrint(TreeNode subTree, StringBuffer buffer) {
        //L
        if (subTree.hasLeftChild()) inOrderPrint(subTree.left, buffer);

        //N
        buffer.append(" ").append(subTree.getValue());

        //R
        if (subTree.hasRightChild()) inOrderPrint(subTree.right, buffer);

    }

    private void callPostOrderPrint(StringBuffer buffer) {
        postOrderPrint(root, buffer);
    }

    private void postOrderPrint(TreeNode subTree, StringBuffer buffer) {
        //L
        if (subTree.hasLeftChild()) postOrderPrint(subTree.left, buffer);

        //R
        if (subTree.hasRightChild()) postOrderPrint(subTree.right, buffer);

        //N
        buffer.append(" ").append(subTree.getValue());
    }

    private void callBFS(StringBuffer buffer) {
        if (root == null) throw new NullPointerException("Root is null");

        //myTree
        buffer.append(" ").append(root.getValue());
        bfsPrint(root, buffer);
    }

    private void bfsPrint(TreeNode subTree, StringBuffer buffer) {
        if (subTree == null) return;


        //left child
        if (subTree.hasLeftChild()) {
            buffer.append(" ").append(subTree.left.getValue());
        }

        //right child
        if (subTree.hasRightChild()) {
            buffer.append(" ").append(subTree.right.getValue());
        }

        bfsPrint(subTree.left, buffer);
        bfsPrint(subTree.right, buffer);
    }

    public TreeNode getLowestCommonAncestor(TreeNode p, TreeNode q) {
        if (root == null) throw new NullPointerException("Root is null");
        return lowestCommonAncestor(root, p, q);
    }

    private TreeNode lowestCommonAncestor(TreeNode node, TreeNode p, TreeNode q) {
        if (p.getValue() > node.getValue() && q.getValue() > node.getValue())
            return lowestCommonAncestor(node.right, p, q);
        if (p.getValue() < node.getValue() && q.getValue() < node.getValue())
            return lowestCommonAncestor(node.left, p, q);
        return node;
    }

    public void printLevelOrder(TraversalMethods traversal) {
        if (traversal == null) throw new IllegalStateException("Enter method of transversal");
        StringBuffer buffer = new StringBuffer();

        switch (traversal) {
            case BFS -> callBFS(buffer);
            case PRE_ORDER -> callPreOrderPrint(buffer);
            case IN_ORDER -> callInOrderPrint(buffer);
            case POST_ORDER -> callPostOrderPrint(buffer);
        }

        System.out.println("\n");
        System.out.println(traversal.name() + " : " + buffer);
    }

    public int getTreeHeight() {
        if (root == null) return -1;
        return heightRecursive(root);
    }

    public int getDiameter() {
        diameter = -1;
        diameterDFS(root);
        return diameter;
    }

    private int diameterDFS(TreeNode node) {
        //height of empty is -1 due to adding + 1 which will result to 0
        if (node == null) return -1;

        int left = 1 + diameterDFS(node.left);
        int right = 1 + diameterDFS(node.right);


        int sum = left + right;

        //diameter = (height of left) + (height of right);

        //find longest diameter
        diameter = Math.max(diameter, sum);

        //longest height
        return Math.max(left, right);
    }

    public int getTreeDepth() {
        //to leaf
        return getTreeHeight();
    }

    public int getNodeDepth(TreeNode node) {
        if (root == null || node == null) return -1;
        if (node == root) return 0;
        return depthRecursiveBalo(node, root, 0);
    }

    public int getNodeHeight(TreeNode node) {
        if (node == null || root == null) return -1;
        return maxDepthRecursive(node) + 1;
    }

    private static int heightRecursive(TreeNode node) {
        //height is from bottom to top
        //bottom / leaf should be 0
        //height of left is 0
        if (node == null) return -1; //base case

        int leftHeight = heightRecursive(node.left);
        int rightHeight = heightRecursive(node.right);

        //return the longest height and increment by one
        if (leftHeight > rightHeight) return ++leftHeight;
        return ++rightHeight;
    }

    private int maxDepthRecursive(TreeNode node) {
        //height is from bottom to top
        //bottom / leaf should be 0
        //height of left is 0
        // no of nodes not edged
        if (node == null) return -1; //base case

        int leftHeight = maxDepthRecursive(node.left);
        int rightHeight = maxDepthRecursive(node.right);

        //return the longest height and increment by one
        return Math.max(rightHeight, leftHeight) + 1;
    }

    private int depthRecursiveBalo(TreeNode target, TreeNode temp, int level) {
        if (temp == null) return --level;
        if (temp == target) return level;
        else {
            int left = depthRecursiveBalo(target, temp.left, level + 1);
            if (left != level) return left;
            int right = depthRecursiveBalo(target, temp.right, level + 1);
            if (right != level) return right;
        }
        return --level;
    }

    TreeNode findByValue(Integer value) {
        return search(value, root);
    }

    public void deleteByValue(Integer value) {
        if (value == null) throw new NullPointerException("Value required");
        if (root == null) throw new NullPointerException("Root is null");
        if (avl) root = deleteNodeByValueAvl(value, root);
        else root = deleteNodeByValue(value, root);
    }

    TreeNode search(Integer value, TreeNode subtree) {
        if (subtree == null) return null; //base case

        //found key
        if (subtree.getValue() == value) {
            //base case
            return subtree;
        }

        //no other case
        if (subtree.hasNoChild()) return null; // base case


        return search(value, (subtree.hasLeftChild() && (value <= subtree.left.getValue())) ? subtree.left : subtree.right);
    }

    public TreeNode minValueFromRoot() {
        if (root == null) throw new NullPointerException("Root is null");
        return findMinValRecursive(root, root);
    }

    private TreeNode findMinValRecursive(TreeNode subTree, TreeNode min) {
        //ideally go to extreme left for BTS
        //var current = subtree
        //while(current.left != null) current = current.left;
        //return current;

        //N
        if (subTree.getValue() < min.getValue()) min = subTree;

        if (subTree.hasNoChild()) return min; //base case

        //L
        if (subTree.hasLeftChild()) min = findMinValRecursive(subTree.left, min);

        //R
        if (subTree.hasRightChild()) min = findMinValRecursive(subTree.right, min);

        return min;
    }

    private TreeNode findMaxValRecursive(TreeNode subTree, TreeNode max) {

        //ideally go to extreme right for BTS
        //var current = subtree
        //while(current.right != null) current = current.right;
        //return current;

        //N
        if (subTree.getValue() > max.getValue()) {
            max = subTree;
        }

        if (subTree.hasNoChild()) return max; //base case

        //L
        if (subTree.hasLeftChild()) max = findMaxValRecursive(subTree.left, max);

        //R
        if (subTree.hasRightChild()) max = findMaxValRecursive(subTree.right, max);

        return max;
    }

    private TreeNode minValueFromNode(TreeNode treeNode) {
        if (root == null) throw new NullPointerException("Root is null");
        if (treeNode == null) throw new NullPointerException("Node root is null");
        return findMinValRecursive(treeNode, treeNode);
    }

    public TreeNode maxValueFromRoot() {
        if (root == null) throw new NullPointerException("Root is null");
        return findMaxValRecursive(root, root);
    }

    public TreeNode invertTree(TreeNode root) {
        if (root == null) return null;

        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;

        invertTree(root.left);
        invertTree(root.right);
        return root;
    }

    TreeNode maxValueFromNode(TreeNode treeNode) {
        if (root == null) throw new NullPointerException("Root is null");
        if (treeNode == null) throw new NullPointerException("Node root is null");
        return findMaxValRecursive(treeNode, treeNode);
    }

    public boolean isBalanced() {
        if (root == null) throw new NullPointerException("Root is null");

        int factor = getBalanceFactor(root);

        return Math.abs(factor) <= 1;
    }

    public static int getBalanceFactor(TreeNode n) {
        if (n == null) return -1;

        int left = heightRecursive(n.left);
        int right = heightRecursive(n.right);

        return left - right;
    }

    private TreeNode deleteNodeByValue(Integer value, TreeNode node) {
        if (node == null) return null; //base case

        //node is probably in left node
        if (value < node.getValue()) node.left = deleteNodeByValue(value, node.left);

            //node is probably in right node
        else if (value > node.getValue()) node.right = deleteNodeByValue(value, node.right);

            //found node
        else {
            //delete node by setting parent value to right value of n
            //right first because right is bigger than left
            //parent should be bigger than left
            if (!node.hasLeftChild()) return node.right;

                //delete node by setting parent value to left value of n
                //left second because left is smaller than right
                //parent should be bigger than left
            else if (!node.hasRightChild()) return node.left;

                //delete node with 2 children
            else {
                //get node bigger than left but smaller than right
                TreeNode temp = minValueFromNode(node.right);

                //update value
                var newNode = new TreeNode(temp.getValue());
                newNode.left = node.left;
                newNode.right = node.right;
                node = newNode;

                node.right = deleteNodeByValue(temp.getValue(), node.right);
            }
        }

        return node;
    }

    private TreeNode deleteNodeByValueAvl(Integer value, TreeNode node) {
        if (node == null) return null; //base case

        //node is probably in left node
        if (value < node.getValue()) node.left = deleteNodeByValueAvl(value, node.left);

            //node is probably in right node
        else if (value > node.getValue()) node.right = deleteNodeByValueAvl(value, node.right);

            //found node
        else {
            //delete node by setting parent value to right value of n
            //right first because right is bigger than left
            //parent should be bigger than left
            if (!node.hasLeftChild()) return node.right;

                //delete node by setting parent value to left value of n
                //left second because left is smaller than right
                //parent should be bigger than left
            else if (!node.hasRightChild()) return node.left;

                //delete node with 2 children
            else {
                //get node bigger than left but smaller than right
                TreeNode temp = minValueFromNode(node.right);

                //update value
                var newNode = new TreeNode(temp.getValue());
                newNode.left = node.left;
                newNode.right = node.right;
                node = newNode;

                node.right = deleteNodeByValueAvl(temp.getValue(), node.right);
            }
        }

        Imbalance imbalance = Imbalance.whichDelete(node);

        switch (imbalance.getRotation()) {

            case LEFT -> {
                return leftRotate(node);
            }
            case RIGHT -> {
                return rightRotate(node);
            }
            case LEFT_RIGHT -> {
                node.left = leftRotate(node);
                return rightRotate(node);
            }
            case RIGHT_LEFT -> {
                node.right = rightRotate(node);
                return leftRotate(node);
            }
            case BALANCED -> {
                return node;
            }
        }

        return node;
    }

    public enum TraversalMethods {
        PRE_ORDER,
        IN_ORDER,
        POST_ORDER,
        BFS,
    }

    private enum Heavy {

        LEFT(List.of(Imbalance.LEFT_LEFT, Imbalance.LEFT_RIGHT)),
        RIGHT(List.of(Imbalance.RIGHT_RIGHT, Imbalance.RIGHT_LEFT)),

        BALANCED(null);

        private final List<Imbalance> imbalances;

        Heavy(List<Imbalance> imbalances) {
            this.imbalances = imbalances;
        }

        public List<Imbalance> getImbalances() {
            return imbalances;
        }

        public static Heavy byImbalance(Imbalance imbalance) {
            return Arrays.stream(values()).filter(i -> i.imbalances.contains(imbalance)).findFirst().orElse(null);
        }

        public static Heavy byFactorInsert(Integer balanceFactor) {
            if (balanceFactor == null) throw new NullPointerException("Balance factor missing");
            if (balanceFactor < 2 && balanceFactor > -2) return BALANCED;
            return balanceFactor > 1 ? LEFT : RIGHT;
        }

        public static Heavy byFactorDelete(Integer balanceFactor) {
            if (balanceFactor == null) throw new NullPointerException("Balance factor missing");
            if (2 != Math.abs(balanceFactor)) return BALANCED;
            return balanceFactor == 2 ? LEFT : RIGHT;
        }

    }

    private enum Imbalance {
        LEFT_LEFT(Rotation.RIGHT),
        RIGHT_RIGHT(Rotation.LEFT),
        LEFT_RIGHT(Rotation.LEFT_RIGHT),
        RIGHT_LEFT(Rotation.RIGHT_LEFT),

        BALANCED(Rotation.BALANCED);

        private final Rotation rotation;

        Imbalance(Rotation rotation) {
            this.rotation = rotation;
        }

        public Rotation getRotation() {
            return rotation;
        }

        public static Imbalance whichInsert(TreeNode newNode, TreeNode parent) {
            if (parent == null) throw new NullPointerException("Parent node missing");

            Heavy heavy = Heavy.byFactorInsert(getBalanceFactor(parent));
            switch (heavy) {
                //left will have parent.left
                case LEFT -> {
                    return newNode.getValue() < parent.left.getValue() ? LEFT_LEFT : LEFT_RIGHT;
                }

                //right will have parent.right
                case RIGHT -> {
                    return newNode.getValue() > parent.right.getValue() ? RIGHT_RIGHT : RIGHT_LEFT;
                }

                case BALANCED -> {
                    return BALANCED;
                }

            }
            return BALANCED;
        }

        public static Imbalance whichDelete(TreeNode parent) {
            if (parent == null) throw new NullPointerException("Node missing");

            Heavy heavy = Heavy.byFactorDelete(getBalanceFactor(parent));
            switch (heavy) {
                case LEFT -> {
                    return getBalanceFactor(parent.left) >= 0 ? LEFT_LEFT : LEFT_RIGHT;
                }
                case RIGHT -> {
                    return getBalanceFactor(parent.right) <= -0 ? RIGHT_RIGHT : RIGHT_LEFT;
                }
                case BALANCED -> {
                    return BALANCED;
                }
            }

            return BALANCED;
        }

    }

    private enum Rotation {
        LEFT,
        RIGHT,
        LEFT_RIGHT,
        RIGHT_LEFT,

        BALANCED;
    }

    public static class NotBalancedException extends RuntimeException {
        public NotBalancedException() {
        }
    }

}
