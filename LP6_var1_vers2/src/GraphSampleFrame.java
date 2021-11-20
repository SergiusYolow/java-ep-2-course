import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class GraphSampleFrame extends JFrame
{
    static final String classname = "Shapes";

    private static final long serialVersionUID = 1L;

    public GraphSampleFrame(final GraphSample[] examples) {
        super("GraphSampleFrame");

        Container cpane = getContentPane();
        cpane.setLayout(new BorderLayout());
        final JTabbedPane tpane = new JTabbedPane();
        cpane.add(tpane, BorderLayout.CENTER);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        for (int i = 0; i < examples.length; i++) {
            GraphSample e = examples[i];
            tpane.addTab(e.getName(), new GraphSamplePane(e));
        }
    }

    public class GraphSamplePane extends JComponent {
        /**
         *
         */
        private static final long serialVersionUID = 1L;
        GraphSample example;
        Dimension size;

        public GraphSamplePane(GraphSample example) {
            this.example = example;
            size = new Dimension(example.getWidth(), example.getHeight());
            setMaximumSize( size );
        }

        public void paintComponent(Graphics g) {
            g.setColor(Color.white);
            g.fillRect(0, 0, size.width, size.height);
            g.setColor(Color.black);
            example.draw((Graphics2D) g, this);
        }

        public Dimension getPreferredSize() { return size; }
        public Dimension getMinimumSize() { return size; }
    }

}
