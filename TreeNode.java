//Trees
public class TreeNode {

    private int value;
    TreeNode left;
    TreeNode right;

    public TreeNode() {

    }

    public TreeNode(Integer value) {
        this.value = value;
    }

    boolean hasNoChild() {
        return !hasLeftChild() && !hasRightChild();
    }

    boolean hasLeftChild() {
        return left != null;
    }

    boolean hasRightChild() {
        return right != null;
    }

    public int getValue() {
        return value;
    }

    @Override

    public String toString() {
        return "TreeNode {" +
                "value=" + value +
                ", left=" + left +
                ", right=" + right +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) return false;
        return same(this, ((TreeNode) obj));
    }

    private boolean same(TreeNode pNode, TreeNode qNode) {

        if (pNode == null && qNode == null) return true;
        if (pNode == null || qNode == null) return false;

        if (pNode.getValue() != qNode.getValue()) return false;

        boolean pqLeft = same(pNode.left, qNode.left);
        boolean pqRight = same(pNode.right, qNode.right);

        return pqLeft && pqRight;
    }
}
