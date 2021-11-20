import javax.swing.tree.TreeNode;
import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;

public class Book implements Serializable
{
    private String ISBN;
    private String name;
    private String genre;
    private String producer;
    private String publishDate;

    public Book(String ISBN, String name, String genre, String producer, String publishDate)
    {
        this.ISBN = ISBN;
        this.name = name;
        this.genre = genre;
        this.producer = producer;
        this.publishDate = publishDate;
    }

    public String getISBN()
    {
        return ISBN;
    }

    public String getName()
    {
        return name;
    }

    public String getGenre()
    {
        return genre;
    }

    public String getProducer()
    {
        return producer;
    }

    public String getPublishDate()
    {
        return publishDate;
    }
}
