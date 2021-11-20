import javax.swing.tree.DefaultMutableTreeNode;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

class TreeNode extends DefaultMutableTreeNode implements Serializable
{
    String name;
    LibItem ifNode = null;
    ArrayList<TreeNode> nodes;
    boolean isThisTheEnd = false;

    public TreeNode()
    {
        name = "-";
        nodes = new ArrayList<>();
        ifNode = null;
        isThisTheEnd = false;
    }

    public TreeNode(String str)
    {
        name = str;
        nodes = new ArrayList<TreeNode>();
        ifNode = null;
        isThisTheEnd = false;
    }

    public TreeNode(String str, LibItem nbNode)
    {
        name = str;
        nodes = new ArrayList<>();
        ifNode = nbNode;
        isThisTheEnd = true;
    }

    public ArrayList<LibItem> getAllNodes()
    {
        ArrayList<LibItem> ret = new ArrayList<>();
        Deque<TreeNode> deque = new ArrayDeque<>();
        TreeNode temp;
        deque.push(this);
        while (!deque.isEmpty())
        {
            temp = deque.removeFirst();
            if (temp.isThisTheEnd)
            {
                ret.add(temp.getIfNode());
            }
            else
            {
                for (int i = 0; i < temp.nodes.size(); i++)
                {
                    deque.push(temp.nodes.get(i));
                }
            }
        }
        return ret;
    }

    public void addNode(TreeNode tn)
    {
        nodes.add(tn);
    }

    public void deleteNode(TreeNode tn)
    {
        for (int i = 0; i < nodes.size(); i++)
        {
            if (nodes.get(i).toString().compareToIgnoreCase(tn.toString()) == 0)
            {
                nodes.remove(i);
            }
        }
    }

    public LibItem getIfNode()
    {
        return ifNode;
    }

    public ArrayList<TreeNode> getNodes()
    {
        return nodes;
    }

    public String toString()
    {
        return name;
    }
}
