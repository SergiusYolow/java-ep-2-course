import javax.swing.*;
import javax.swing.table.*;
import java.util.List;

public class BookTable extends JTable {
    final BookTableModel model;
    public BookTable(List<Book> books) {
        model = new BookTableModel(books);
        setModel(model);
    }


    static class BookTableModel extends AbstractTableModel {
        private List<Book> books=null;
        public BookTableModel(List<Book> books) {
            this.books = books;
        }

        static final String[] columnNames = new String[]{
                "Id", "Name", "genre", "language", "date of creation", "date of publishing", "authors id"
        };

        static final Class<?>[] columnTypes = new Class[]{
                Integer.class, String.class, String.class,String.class, String.class,String.class,Integer.class
        };

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return books == null ? 0 : books.size();
        }

        public String getColumnName(int column) {
            return columnNames[column];
        }

        public Class<?> getColumnClass(int column) {
            return columnTypes[column];
        }

        public Object getValueAt(int row, int column) {
            Book book = books.get(row);
            switch (column) {
                case 0:
                    return book.getId();
                case 1:
                    return book.getName();
                case 2:
                    return book.getGenre();
                case 3:
                    return book.getLanguage();
                case 4:
                    return book.getCreateDate();
                case 5:
                    return book.getPublishDate();
                case 6:
                    return book.getAuthorId();
                default:
                    return null;
            }
        }

    }
}