

//import org.apache.derby.client.am.DateTime;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainFrame extends JFrame
{
    private JTable table = null;
    private JScrollPane tableScrollPane = null;
    private JFrame frame = this;
    private DBController db = null;
    private DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public MainFrame()
    {
        super();
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        //setLayout(new FlowLayout());
        setMinimumSize(new Dimension(600, 800));
        setTitle("Kartoteka");
        PaintInterface();
        setVisible(true);
    }

    private void PaintInterface()
    {
        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("File");
        JMenuItem item = new JMenuItem("Create");
        item.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser fc = new JFileChooser();
                fc.setCurrentDirectory(new File("."));
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if (JFileChooser.APPROVE_OPTION == fc.showSaveDialog(frame))
                {
                    File f = fc.getSelectedFile();

                    if (f == null)
                    {
                        return;
                    }
                    db = new DBController(f.getName(), f.getAbsolutePath(), true);
                    try
                    {
                        db.create();
                    } catch (SQLException throwables)
                    {

                        throwables.printStackTrace();
                        return;
                    } catch (Exception ex)
                    {
                        JOptionPane.showMessageDialog(frame, ex.getMessage());
                        return;
                    }
                    makeTable(Table.BOOKS);
                }
            }
        });
        menu.add(item);
        item = new JMenuItem("Open");
        item.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser fc = new JFileChooser();
                fc.setCurrentDirectory(new File("."));
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if (JFileChooser.APPROVE_OPTION == fc.showOpenDialog(frame))
                {
                    File f = fc.getSelectedFile();
                    if (f == null)
                    {
                        return;
                    }
                    db = new DBController(f.getName(), f.getAbsolutePath(), false);
                    makeTable(Table.BOOKS);
                }
            }
        });
        menu.add(item);
        menuBar.add(menu);

        menu = new JMenu("Tables");

        item = new JMenuItem("Books");
        item.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (db == null)
                {
                    JOptionPane.showMessageDialog(frame, "Create or open database!");
                    return;
                }
                makeTable(Table.BOOKS);
            }
        });
        menu.add(item);

        item = new JMenuItem("Authors");
        item.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (db == null)
                {
                    JOptionPane.showMessageDialog(frame, "Create or open database!");
                    return;
                }
                makeTable(Table.AUTHORS);
            }
        });
        menu.add(item);

        item = new JMenuItem("Annotations");
        item.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (db == null)
                {
                    JOptionPane.showMessageDialog(frame, "Create or open database!");
                    return;
                }
                makeTable(Table.ANNOTATIONS);
            }
        });
        menu.add(item);


        menuBar.add(menu);


        menu = new JMenu("Edit");

        item = new JMenuItem("Add Book");
        item.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (db == null)
                {
                    JOptionPane.showMessageDialog(frame, "Create or open database!");
                    return;
                }
                String ISBN = JOptionPane.showInputDialog("Enter new book ISBN");

                String bookName = JOptionPane.showInputDialog("Enter new book name");

                String authors = JOptionPane.showInputDialog("Enter authors names");
                /*if(bookName==null)
                    bookName="";*/

                String[] auth = {"", "", "", "", ""};
                String[] temp = authors.split(";");

                for (int i = 0; i < temp.length && i < 5; i++)
                {
                    auth[i] = temp[i].trim();
                }
                String genre = JOptionPane.showInputDialog("Enter genre");

                String producer = JOptionPane.showInputDialog("Enter producer");
                /*if(genre==null)
                    genre="";*/

                String publishDate = JOptionPane.showInputDialog("Enter date of publishing book");


                String annotation = JOptionPane.showInputDialog("Enter annotation");


                try
                {
                    db.AddAuthor(new Author(ISBN,auth));
                    db.AddAnnotation(new Annotation(ISBN,annotation));
                    db.AddBook(new Book(ISBN, bookName, genre, producer, publishDate));
                } catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                    return;
                }
                makeTable(Table.BOOKS);
            }
        });
        menu.add(item);
        menuBar.add(menu);

        this.setJMenuBar(menuBar);

        tableScrollPane = new JScrollPane();
        this.getContentPane().add(tableScrollPane);
    }

    private void makeTable(Table tableMode)
    {
        if (db == null)
            return;
        switch (tableMode)
        {
            case AUTHORS:
            {
                ArrayList<Author> authors = null;
                try
                {
                    authors = db.readAuthors();
                } catch (SQLException | ClassNotFoundException throwables)
                {
                    System.err.println(throwables);
                    JOptionPane.showMessageDialog(frame, throwables.getMessage());
                    return;
                }

                table = new AuthorTable(authors);
                break;
            }
            case BOOKS:
            {
                ArrayList<Book> books = null;
                try
                {
                    books = db.readBooks();
                } catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                    return;
                }

                table = new BookTable(books);
                break;
            }
            case ANNOTATIONS:
            {
                ArrayList<Annotation> annotations = null;
                try
                {
                    annotations = db.readAnnotations();
                } catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                    return;
                }

                table = new AnnotationTable(annotations);
                break;
            }
            default:
                return;
        }
        this.getContentPane().add(tableScrollPane);
        tableScrollPane.setViewportView(table);
    }

}
