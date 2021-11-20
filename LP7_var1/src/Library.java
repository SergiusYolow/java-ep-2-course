import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.*;
public class Library extends Application {
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;
    private TableView<Book> BookTable = new TableView<>();
    private TableView<Genre> GenreTable = new TableView<>();
    private static ObservableList<Book> BookData = FXCollections.observableArrayList();
    private static ObservableList<Genre> GenreData = FXCollections.observableArrayList();
    private final HBox addBox = new HBox(15);

    public static void main(String[] args) {
        connectionDB();
        createDB();
        launch(args);
        closeConnectionDB();
    }
    private static void connectionDB() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            connection = DriverManager.getConnection("jdbc:derby:Library;create=true");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Book");
            while (resultSet.next()) {
                Book Book = new Book(resultSet.getString("Название"), resultSet.getString("Номер книги"), resultSet.getString("Год издания"), resultSet.getString("Состояние"), resultSet.getString("Автор"), resultSet.getString("Жанр"));
                BookData.add(Book);
            }
            resultSet = statement.executeQuery("SELECT * FROM genreTypes");
            while (resultSet.next()) {
                Genre GenreType = new Genre(resultSet.getString("Жанр"), resultSet.getString("ID"));
                GenreData.add(GenreType);
            }
        } catch (Exception e) { System.err.println(e.getMessage()); }
    }
    private static void createDB() {
        try {
            statement.execute("CREATE TABLE IF NOT EXISTS 'GenreTypes' " + "('id' INTEGER PRIMARY KEY AUTOINCREMENT,  " + "'genre' TEXT); ");
            statement.execute("CREATE TABLE IF NOT EXISTS 'Book' " + "('title' TEXT," + " 'numUDK' TEXT," + " 'year' TEXT," + " 'condition' TEXT," + " 'author' TEXT,"+ " 'genre' TEXT REFERENCES genreTypes(Genre) DEFERRABLE INITIALLY DEFERRED);");
        } catch (Exception e) { System.err.println(e.getMessage()); }
    }
    private static void closeConnectionDB() {
        try {
            resultSet.close();
            connection.close();
        } catch (Exception e) { System.out.println(e.getMessage()); }
    }
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(new Group());
        primaryStage.setTitle("Картотека библиотеки");
        createBookTable();
        createGenreTable();
        createAddBox();
        VBox allBox = new VBox(10);
        allBox.setPadding(new Insets(10, 10, 10, 10));
        allBox.getChildren().addAll(new Label("Картотека библиотеки"), BookTable,GenreTable,addBox);
        ((Group) scene.getRoot()).getChildren().addAll(allBox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void createAddBox() {
        final TextField addtTitle = new TextField();
        addtTitle.setMaxWidth(100);
        addtTitle.setPromptText("Название");
        final TextField addNumUDK = new TextField();
        addNumUDK.setMaxWidth(300);
        addNumUDK.setPromptText("Номер книги");
        final TextField addYear = new TextField();
        addYear.setMaxWidth(300);
        addYear.setPromptText("Год издания");
        final TextField addCondition = new TextField();
        addCondition.setMaxWidth(100);
        addCondition.setPromptText("Состояние");
        final TextField addName = new TextField();
        addName.setMaxWidth(300);
        addName.setPromptText("Автор");
        final TextField addGenre = new TextField();
        addGenre.setMaxWidth(300);
        addGenre.setPromptText("Жанр");
        final Button addBookButton = new Button("Добавить в картотеку");
        addBookButton.setOnAction((ActionEvent e) -> {
            String address = addtTitle.getText();
            String numUDK = addNumUDK.getText();
            String year = addYear.getText();
            String condition = addCondition.getText();
            String name = addName.getText();
            String genre = addGenre.getText();
            try {
                Book Book = new Book(address, numUDK, year, condition, name, genre);
                if (!GenreExists(genre)) {
                    Genre newGenre = new Genre(genre, "?");
                    addGenreToDataBase(newGenre);
                    GenreData.add(newGenre);
                }
                BookData.add(Book);
                addBookToDatabase(address, numUDK, year, condition, name, genre);
                addtTitle.clear();
                addNumUDK.clear();
                addYear.clear();
                addCondition.clear();
                addName.clear();
                addGenre.clear();
            } catch (Exception exc) { showAddingError(exc.getMessage()); }
        });
        addBox.getChildren().addAll(addtTitle, addNumUDK, addYear, addCondition, addName, addGenre, addBookButton);
    }
    private void showAddingError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка добавления");
        alert.setHeaderText("Ошибка");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void addGenreToDataBase(Genre newGenre) {
        String sql = "INSERT INTO GenreTypes(Genre/*, description*/) VALUES(?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newGenre.getGenre());
            statement.executeUpdate();
        } catch (Exception e) { System.out.println(e.getMessage()); }
    }
    private boolean GenreExists(String newGenre) {
        for (Genre Genre : GenreData) if (Genre.getGenre().equals(newGenre)) return true;
        return false;
    }
    private void createGenreTable() {
        TableColumn<Genre, String> GenreColumn = new TableColumn<>("Жанр");
        setGenreColumnValues(GenreColumn, 1020, "Жанр");
        GenreTable.setItems(GenreData);
        GenreTable.getColumns().addAll(GenreColumn);
        GenreTable.setMaxHeight(200);
    }
    private void createBookTable() {
        TableColumn<Book, String> titleColumn = new TableColumn<>("Название");
        TableColumn<Book, String> numUDKColumn = new TableColumn<>("Номер книги");
        TableColumn<Book, String> yearColumn = new TableColumn<>("Год издания");
        TableColumn<Book, String> conditionColumn = new TableColumn<>("Состояние");
        TableColumn<Book, String> nameColumn = new TableColumn<>("Автор");
        setBookColumnValues(titleColumn, 180, "Название");
        setBookColumnValues(numUDKColumn, 150, "Номер книги");
        setBookColumnValues(yearColumn, 150, "Год издания");
        setBookColumnValues(conditionColumn, 180, "Состояние");
        setBookColumnValues(nameColumn, 180, "Автор");
        TableColumn<Book, String> GenreColumn = new TableColumn<>("Жанр");
        setBookColumnValues(GenreColumn, 180, "Жанр");
        BookTable.setItems(BookData);
        BookTable.getColumns().addAll(titleColumn, numUDKColumn, yearColumn, conditionColumn, nameColumn, GenreColumn);
        BookTable.setMaxHeight(200);
    }
    private void setGenreColumnValues(TableColumn<Genre, String> column, int width, String sqlName) {
        column.setPrefWidth(width);
        column.setCellValueFactory(param -> param.getValue().getFieldBySQL(sqlName));
    }
    private void setBookColumnValues(TableColumn<Book, String> column, int width, String sqlName) {
        column.setPrefWidth(width);
        column.setCellValueFactory(param -> param.getValue().getFieldBySQL(sqlName));
    }
    private void addBookToDatabase(String title, String numUDK, String year, String condition, String name, String genre) {
        String sql = "INSERT INTO Book(Название, номер книги, год издания, состояние, автор, жанр) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, title);
            statement.setString(2, numUDK);
            statement.setString(3, year);
            statement.setString(4, condition);
            statement.setString(5, name);
            statement.setString(6, genre);
            statement.executeUpdate();
        } catch (Exception e) { System.out.println(e.getMessage()); }
    }

}