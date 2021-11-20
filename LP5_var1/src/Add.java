import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Add extends JFrame {
    private LibCatalog catalog;
    private JLabel genre = new JLabel("Жанр");
    private JLabel author = new JLabel("Автор");
    private JLabel year = new JLabel("Год выпуска");
    private JLabel num = new JLabel("Номер");
    private JTextField text_genre = new JTextField();
    private JTextField text_author = new JTextField();
    private JTextField text_year = new JTextField();
    private JTextField text_num = new JTextField();
    private JButton ok_Btn = new JButton("OK");

    public Add(LibCatalog ctalog) throws HeadlessException {
        Container c = getContentPane();
        this.catalog = ctalog;
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ok_Btn.addActionListener(new ok_listner());
        this.setTitle("Добавление");
        this.setBounds(400, 200, 400, 150);
        this.setSize(350, 250);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(genre);
        panel.add(text_genre);
        panel.add(author);
        panel.add(text_author);
        panel.add(year);
        panel.add(text_year);
        panel.add(num);
        panel.add(text_num);
        panel.add(ok_Btn);
        c.add(panel);
    }

    private class ok_listner implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(text_genre.getText().equals("") || text_author.getText().equals("") || text_year.getText().equals("") || text_num.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Нужно заполнить все поля!", "Внимание", JOptionPane.WARNING_MESSAGE);
            }else{
                try {
                    LibItem lp = new LibItem(Integer.parseInt(text_year.getText()), Integer.parseInt(text_num.getText()), text_genre.getText(), text_author.getText());
                    //LaptopCatalog.addResult = lp;
                    catalog.addNewItem(lp);
                } catch (NumberFormatException x){
                    JOptionPane.showMessageDialog(null, "Поля заполнены неправильно!", "Внимание", JOptionPane.WARNING_MESSAGE);
                }
            }
            dispose();
        }
    }
}
