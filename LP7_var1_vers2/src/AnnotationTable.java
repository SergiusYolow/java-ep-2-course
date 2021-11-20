import javax.swing.*;
import javax.swing.table.*;
import java.util.List;

public class AnnotationTable extends JTable
{
    final AnnotationTableModel model;

    public AnnotationTable(List<Annotation> annotations)
    {
        model = new AnnotationTableModel(annotations);
        setModel(model);
    }

    public void setAuthors(List<Annotation> annotations)
    {
        model.setAuthors(annotations);
    }

    static class AnnotationTableModel extends AbstractTableModel
    {
        private List<Annotation> annotations;

        public AnnotationTableModel(List<Annotation> annotations)
        {
            this.annotations = annotations;
        }

        static final String[] columnNames = new String[]{
                "ISBN", "Annotation"
        };

        static final Class<?>[] columnTypes = new Class[]{
                String.class, String.class
        };

        public int getColumnCount()
        {
            return columnNames.length;
        }

        public int getRowCount()
        {
            return annotations == null ? 0 : annotations.size();
        }

        public String getColumnName(int column)
        {
            return columnNames[column];
        }

        public Class<?> getColumnClass(int column)
        {
            return columnTypes[column];
        }

        public Object getValueAt(int row, int column)
        {

            Annotation annotation = annotations.get(row);
            switch (column)
            {
                case 0:
                    return annotation.getISBN();
                case 1:
                    return annotation.getText();
                default:
                    return null;
            }
        }

        public void setAuthors(List<Annotation> annotations)
        {
            this.annotations = annotations;
            this.fireTableDataChanged();
        }
    }
}