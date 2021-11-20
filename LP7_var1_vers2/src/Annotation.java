import java.io.Serializable;

public class Annotation implements Serializable
{
    public String getText()
    {
        return text;
    }

    public String getISBN()
    {
        return isbn;
    }

    String text;
    String isbn;

    public Annotation(String isbn,String text)
    {
        this.text = text;
        this.isbn = isbn;
    }



}
