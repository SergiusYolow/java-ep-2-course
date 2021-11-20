import javax.swing.tree.TreeNode;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Enumeration;

public class Author implements Serializable
{
    private String isbn;
    private String[] author = null;


    public Author(String isbn, String[] author)
    {
        this.isbn = isbn;
        this.author = author;
    }

    public Author(String[] author)
    {
        this.author = author;
    }

    public String getAuthor(int index)
    {
        return author[index];
    }


    public String getISBN()
    {
        return isbn;
    }

    @Override
    public String toString()
    {
        return "Author{" +
                "isbn='" + isbn + '\'' +
                ", author=" + Arrays.toString(author) +
                '}';
    }
}
