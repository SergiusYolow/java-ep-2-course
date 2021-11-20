import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

public class LibCatalog extends JFrame
{
    //public static Laptop addResult = null;
    private JButton addBut = new JButton("Добавить");
    private JButton saveBut = new JButton("Сохранить");
    private JButton loadBut = new JButton("Загрузить");
    private JButton delBut = new JButton("Удалить");
    private JTree laptopTree;
    private JTable laptopTable;
    private MyTableModel myTableModel;
    private MyTreeModel myTreeModel;
    private static RandomAccessFile raf;
    private static String fileName = "Library.info";
    private static HashMap<String, Long> idx;


    public LibCatalog() throws HeadlessException
    {
        addBut.addActionListener(e -> addLaptop());
        delBut.addActionListener(e -> removeLaptop());
        saveBut.addActionListener(e ->
        {
            try
            {
                saveData();
            } catch (IOException ioException)
            {
                ioException.printStackTrace();
            }
        });
        loadBut.addActionListener(e ->
        {
            try
            {
                loadData();
            } catch (IOException ioException)
            {
                ioException.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException)
            {
                classNotFoundException.printStackTrace();
            }
        });

        idx = new HashMap<>();

        myTableModel = new MyTableModel();
        laptopTable = new JTable(myTableModel);
        myTreeModel = new MyTreeModel(new TreeNode("Каталог"));
        laptopTree = new JTree(myTreeModel);
        laptopTree.addTreeSelectionListener(new addTreeNode());
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, new JScrollPane(laptopTree), new JScrollPane(laptopTable));
        splitPane.setDividerLocation(300);

        getContentPane().add(splitPane);
        getContentPane().add("North", addBut);
        getContentPane().add("South", delBut);
        getContentPane().add("West", loadBut);
        getContentPane().add("East", saveBut);
        setBounds(100, 100, 600, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args)
    {
        LibCatalog mainClass = new LibCatalog();
        mainClass.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainClass.setVisible(true);
    }

    public void addNewItem(LibItem addResult)
    {
        TreeNode temp, where,insert, root = myTreeModel.getRoot();
        try
        {
            if (findNode(root, addResult.getModel()) != null)
            {
                where = findNode(root, addResult.getModel());
                if (findNode(where, addResult.getProc()) != null)
                {
                    where = findNode(root, addResult.getProc());
                    if (findNode(where, Integer.toString(addResult.getYear())) != null)
                    {
                        where = findNode(where, Integer.toString(addResult.getYear()));
                        if (findNode(where, Integer.toString(addResult.getNumber())) == null)
                        {
                            myTreeModel.insertNodeInto(new TreeNode(Integer.toString(addResult.getNumber()), addResult), where, where.getChildCount(), false);
                        }
                    }
                    else
                    {
                        myTreeModel.insertNodeInto(new TreeNode(Integer.toString(addResult.getYear())), where, where.getChildCount(), false);
                        myTreeModel.insertNodeInto(new TreeNode(Integer.toString(addResult.getNumber()), addResult), (temp = findNode(where, Integer.toString(addResult.getYear()))), temp.getChildCount(), false);
                    }

                }
                else
                {
                    myTreeModel.insertNodeInto(new TreeNode(addResult.getProc()), where, where.getChildCount(), false);
                    myTreeModel.insertNodeInto(new TreeNode(Integer.toString(addResult.getYear())), (temp = findNode(where, addResult.getProc())), temp.getChildCount(), false);
                    myTreeModel.insertNodeInto(new TreeNode(Integer.toString(addResult.getNumber()), addResult), (temp = findNode(temp, Integer.toString(addResult.getYear()))), temp.getChildCount(), false);
                }
            }
            else
            {
                myTreeModel.insertNodeInto(new TreeNode(addResult.getModel()), root, root.getChildCount(), false);
                myTreeModel.insertNodeInto(new TreeNode(addResult.getProc()), (temp = findNode(root, addResult.getModel())), temp.getChildCount(), false);
                myTreeModel.insertNodeInto(new TreeNode(Integer.toString(addResult.getYear())), (temp = findNode(temp, addResult.getProc())), temp.getChildCount(), false);
                myTreeModel.insertNodeInto(new TreeNode(Integer.toString(addResult.getNumber()), addResult), (temp = findNode(temp, Integer.toString(addResult.getYear()))), temp.getChildCount(), false);
            }


        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public TreeNode findNode(TreeNode root, String s)
    {
        Enumeration<javax.swing.tree.TreeNode> e = root.depthFirstEnumeration();
        while (e.hasMoreElements())
        {
            TreeNode node = (TreeNode) e.nextElement();
            if (node.toString().equalsIgnoreCase(s))
            {
                return node;
            }
        }
        return null;
    }

    private class addTreeNode implements TreeSelectionListener
    {
        @Override
        public void valueChanged(TreeSelectionEvent e)
        {
            TreeNode node = (TreeNode) laptopTree.getLastSelectedPathComponent();
            if (node == null)
            {
                return;
            }
            ArrayList<LibItem> array = node.getAllNodes();
            myTableModel = new MyTableModel(array);
            laptopTable.setModel(myTableModel);
        }
    }

    private void addLaptop()
    {
        Add addLaptop = new Add(this);
        addLaptop.setVisible(true);
    }

    private void removeLaptop()
    {
        TreePath currentSelection = laptopTree.getSelectionPath();
        if (currentSelection != null)
        {
            TreeNode currentNode = (TreeNode) (currentSelection.getLastPathComponent());
            TreeNode parent = (TreeNode) (currentNode.getParent());
            if (parent != null)
            {
                myTreeModel.removeNodeFromParent(currentNode);
                parent.deleteNode(currentNode);
                ArrayList<LibItem> array = parent.getAllNodes();
                myTableModel = new MyTableModel(array);
                laptopTable.setModel(myTableModel);
            }
        }
    }

    private void saveData() throws IOException
    {
        TreeNode root = myTreeModel.getRoot();
        new File(fileName).delete();
        raf = new RandomAccessFile(fileName, "rw");
        Enumeration<javax.swing.tree.TreeNode> e = root.depthFirstEnumeration();
        while (e.hasMoreElements())
        {
            TreeNode node = (TreeNode) e.nextElement();
            long pos = Buffer.writeObject(raf, node);
            idx.put(node.name, pos);
        }
        raf.close();
    }

    private void loadData() throws IOException, ClassNotFoundException
    {
        long pos;
        raf = new RandomAccessFile(fileName, "rw");
        while ((pos = raf.getFilePointer()) < raf.length())
        {
            TreeNode node = (TreeNode) Buffer.readObject(raf, pos);
            addNewItem(node.ifNode);
        }
        raf.close();
    }
}

