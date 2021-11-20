
import java.sql.*;
import java.util.ArrayList;


public class DBController {
    static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    static String connect;
    private String name;
    private String directory;

    private String selectBooks="select * from Books";
    private String selectAuthors = "select * from Authors";
    public DBController(String name, String directory, boolean toCreate) {
        connect = "jdbc:derby:" + name + ";create="+(toCreate?"true":"false");
        //System.setProperty("derby.system.home", directory);
        this.name = name;
        this.directory=directory;
    }

    public void create() throws SQLException, ClassNotFoundException {
             executeSQL("CREATE TABLE Authors " +
                    "(" +
                    "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY, " +
                    "name VARCHAR(32)," +
                     "birthdate VARCHAR(32)"+
                    ")",true);

           executeSQL("CREATE TABLE Books " +
                    "(" +
                    "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY, " +
                    "name VARCHAR(64), " +
                    "genre VARCHAR(64)," +
                    "language VARCHAR(64)," +
                    "createDate VARCHAR(64)," +
                    "publishDate VARCHAR(64)," +
                    "authorId INTEGER NOT NULL REFERENCES Authors(id) " +
                    ")",true);
    }

    public void AddBook(Book book) throws SQLException, ClassNotFoundException {
        String sql =
                String.format("INSERT INTO Books(name,genre, language, createDate, publishDate, authorId) VALUES('%s','%s','%s','%s','%s',%d )", book.getName(), book.getGenre(), book.getLanguage(), book.getCreateDate(), book.getPublishDate(), book.getAuthorId());
        executeSQL(sql, true);
    }

    public void AddAuthor(Author author) throws SQLException, ClassNotFoundException {
        String sql = String.format("INSERT INTO Authors(name, birthdate) VALUES('%s', '%s')",
                author.getName(), author.getBirthdate());
        executeSQL(sql, true);
    }

    private ResultSet executeSQL(String query, boolean toUpdate) throws ClassNotFoundException, SQLException {

       ResultSet resultSet=null;
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(connect);
            Statement statement = conn.createStatement();
            System.out.println(query);
            if(toUpdate)
                statement.executeUpdate(query);
            else
                resultSet = statement.executeQuery(query);
           // statement.close();

        return resultSet;
    }


    public ArrayList<Book> readBooks() throws SQLException, ClassNotFoundException {
        ArrayList<Book> data=new ArrayList<Book>();
        ResultSet resultSet = executeSQL(selectBooks, false);
        if(resultSet!=null)
        while(resultSet.next())
        {
            data.add(new Book(resultSet.getInt("id"), resultSet.getString("name"),
                    resultSet.getString("genre"), resultSet.getString("language"),
                    resultSet.getString("createDate"), resultSet.getString("publishDate"),
                    resultSet.getInt("authorId")
            ));
        }
        return data;
    }
    public ArrayList<Author> readAuthors() throws SQLException, ClassNotFoundException {
        ArrayList<Author> data=new ArrayList<Author>();

        ResultSet resultSet = executeSQL(selectAuthors, false);
        if(resultSet!=null)
        while(resultSet.next())
        {
            data.add(new Author(resultSet.getInt("id"),
                    resultSet.getString("name"), resultSet.getString("birthdate")));

        }
        return data;
    }
}

