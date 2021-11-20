import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

class MyTableModel implements TableModel
{
    static final String[] columnNames = new String[]{"Жанр", "Номер", "Год", "Автор"};
    static final Class[] columnTypes = new Class[]{String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class};
    private Set<TableModelListener> listeners = new HashSet<TableModelListener>();
    private ArrayList<LibItem> infoNodes;

    public MyTableModel()
    {
        infoNodes = new ArrayList<LibItem>();
    }

    public MyTableModel(ArrayList<LibItem> al)
    {
        this.infoNodes = al;
    }

    public void setInfoArray(ArrayList<LibItem> al)
    {
        infoNodes = al;
    }

    public int getColumnCount()
    {
        return columnNames.length;
    }

    public int getRowCount()
    {
        return infoNodes.size();
    }

    public Class getColumnClass(int columnIndex)
    {
        return columnTypes[columnIndex];
    }

    public String getColumnName(int columnIndex)
    {
        return columnNames[columnIndex];
    }

    public Object getValueAt(int rowIndex, int columnIndex)
    {
        LibItem lp = infoNodes.get(rowIndex);
        switch (columnIndex)
        {
            case 0:
                return lp.getModel();
            case 1:
                return lp.getNumber();
            case 2:
                return lp.getProc();
            case 3:
                return lp.getYear();
        }
        return "";
    }

    @Override
    public void addTableModelListener(TableModelListener listener)
    {
        listeners.add(listener);
    }

    @Override
    public void removeTableModelListener(TableModelListener listener)
    {
        listeners.remove(listener);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return false;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex)
    {
    }
}