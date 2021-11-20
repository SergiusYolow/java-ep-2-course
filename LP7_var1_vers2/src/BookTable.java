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
                "ISBN", "Name", "genre", "producer", "date of publishing"
        };

        static final Class<?>[] columnTypes = new Class[]{
                String.class, String.class, String.class,String.class, String.class
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
                    return book.getISBN();
                case 1:
                    return book.getName();
                case 2:
                    return book.getGenre();
                case 3:
                    return book.getProducer();
                case 4:
                    return book.getPublishDate();
                default:
                    return null;
            }
        }

    }
}