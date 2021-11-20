import javax.swing.tree.TreeNode;
import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;

public class Book implements Serializable {
    private int id;
    private String name;
    private String genre;
    private String language;
    private String createDate;
    private String publishDate;
    private int authorId;


    public Book(String name, String genre, String language, String createDate, String publishDate, int authorId) {
        this.name = name;
        this.genre = genre;
        this.language=language;
        this.createDate=createDate;
        this.publishDate=publishDate;
        this.authorId=authorId;
    }
    public Book(int id,String name, String genre, String language, String createDate, String publishDate, int authorId) {
        this(name, genre, language, createDate, publishDate, authorId);
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }
    public String getLanguage()
    {
        return language;
    }

    public String getGenre() {
        return genre;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public int getAuthorId() {
        return authorId;
    }
}
