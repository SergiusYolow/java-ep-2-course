
import java.sql.*;
import java.util.ArrayList;


public class DBController
{
    static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    static String connect;

    private String name;
    private String directory;


    public DBController(String name, String directory, boolean toCreate)
    {
        connect = "jdbc:derby:" + name + ";create=" + (toCreate ? "true" : "false");
        System.setProperty("derby.system.home", directory);
        this.name = name;
        this.directory = directory;
    }

    public void create() throws SQLException, ClassNotFoundException
    {
        executeSQL("CREATE TABLE Authors " +
                "(" +
                "isbn VARCHAR(64) NOT NULL PRIMARY KEY, " +
                "author1 VARCHAR(32)," +
                "author2 VARCHAR(32)," +
                "author3 VARCHAR(32)," +
                "author4 VARCHAR(32)," +
                "author5 VARCHAR(32)" +
                ")", true);


        executeSQL("CREATE TABLE Annotations " +
                "(" +
                "isbn VARCHAR(64) NOT NULL PRIMARY KEY, " +
                "annotation VARCHAR(64)" +
                ")", true);

        executeSQL("CREATE TABLE Books " +
                "(" +
                "isbn VARCHAR(64) NOT NULL PRIMARY KEY, " +
                "name VARCHAR(64)," +
                "genre VARCHAR(64)," +
                "producer VARCHAR(64)," +
                "publishDate VARCHAR(64)" +
                ")", true);
    }

    public void AddBook(Book book) throws SQLException, ClassNotFoundException
    {
        String sql =
                String.format("INSERT INTO Books(isbn, name, genre, producer, publishDate) VALUES('%s','%s','%s','%s','%s')", book.getISBN(), book.getName(), book.getGenre(), book.getProducer(), book.getPublishDate());
        executeSQL(sql, true);
    }

    public void AddAuthor(Author author) throws SQLException, ClassNotFoundException
    {
        String sql = String.format("INSERT INTO Authors(isbn, author1, author2, author3, author4, author5) VALUES('%s','%s', '%s', '%s', '%s', '%s')",
                author.getISBN(), author.getAuthor(0), author.getAuthor(1), author.getAuthor(2), author.getAuthor(3), author.getAuthor(4));
        executeSQL(sql, true);
    }

    public void AddAnnotation(Annotation annotation) throws SQLException, ClassNotFoundException
    {
        String sql = String.format("INSERT INTO Annotations(isbn, annotation) VALUES('%s','%s')", annotation.getISBN(), annotation.getText());
        executeSQL(sql, true);
    }

    private ResultSet executeSQL(String query, boolean toUpdate) throws ClassNotFoundException, SQLException
    {

        ResultSet resultSet = null;
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(connect);
        Statement statement = conn.createStatement();
        System.out.println(query);
        if (toUpdate)
            statement.executeUpdate(query);
        else
            resultSet = statement.executeQuery(query);
        // statement.close();

        return resultSet;
    }


    public ArrayList<Book> readBooks() throws SQLException, ClassNotFoundException
    {
        ArrayList<Book> data = new ArrayList<Book>();
        String selectBooks = "select * from Books";
        ResultSet resultSet = executeSQL(selectBooks, false);
        if (resultSet != null)
            while (resultSet.next())
            {
                data.add(new Book(resultSet.getString("isbn"), resultSet.getString("name"),
                        resultSet.getString("genre"), resultSet.getString("producer"),
                        resultSet.getString("publishDate")
                ));
            }
        return data;
    }

    public ArrayList<Author> readAuthors() throws SQLException, ClassNotFoundException
    {
        ArrayList<Author> data = new ArrayList<Author>();

        String selectAuthors = "select * from Authors";
        ResultSet resultSet = executeSQL(selectAuthors, false);
        if (resultSet != null)
            while (resultSet.next())
            {
                String[] authors = {resultSet.getString("author1"), resultSet.getString("author2"), resultSet.getString("author3"), resultSet.getString("author4"), resultSet.getString("author5")};
                data.add(new Author(resultSet.getString("isbn"), authors));
            }
        return data;
    }

    public ArrayList<Annotation> readAnnotations() throws SQLException, ClassNotFoundException
    {
        ArrayList<Annotation> data = new ArrayList<Annotation>();

        String selectAnnotations = "select * from Annotations";
        ResultSet resultSet = executeSQL(selectAnnotations, false);
        if (resultSet != null)
            while (resultSet.next())
            {
                data.add(new Annotation(resultSet.getString("isbn"), resultSet.getString("annotation")));
            }
        return data;
    }
}

