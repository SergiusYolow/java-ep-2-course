import javax.swing.tree.DefaultTreeModel;

class MyTreeModel extends DefaultTreeModel {
    private TreeNode root;

    public MyTreeModel(TreeNode r) {
        super(r);
        root = r;
    }

    @Override
    public TreeNode getRoot() {
        return root;
    }

    public void insertNodeInto(TreeNode child, TreeNode parent, int i, boolean flag) {
        this.insertNodeInto(child, parent, i);
        parent.addNode(child);
    }
}
