import javax.swing.*;
import javax.swing.table.*;
import java.util.List;

public class AuthorTable extends JTable {
    final AuthorTableModel model;
    public AuthorTable(List<Author> authors) {
        model = new AuthorTableModel(authors);
        setModel(model);
    }

    public void setAuthors(List<Author> authors) {
        model.setAuthors(authors);
    }

    static class AuthorTableModel extends AbstractTableModel {
        private List<Author> authors;

        public AuthorTableModel(List<Author> authors) {
            this.authors = authors;
        }

        static final String[] columnNames = new String[]{
                "Id", "Name", "Birthdate"
        };

        static final Class<?>[] columnTypes = new Class[]{
                String.class, String.class, String.class
        };

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return authors == null ? 0 : authors.size();
        }

        public String getColumnName(int column) {
            return columnNames[column];
        }

        public Class<?> getColumnClass(int column) {
            return columnTypes[column];
        }

        public Object getValueAt(int row, int column) {
            Author author = authors.get(row);
            switch (column) {
                case 0:
                    return author.getId();
                case 1:
                    return author.getName();
                case 2:
                    return author.getBirthdate();
                default:
                    return null;
            }
        }

        public void setAuthors(List<Author> authors) {
            this.authors = authors;
            this.fireTableDataChanged();
        }
    }
}