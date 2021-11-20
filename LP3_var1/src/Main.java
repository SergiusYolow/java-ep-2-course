import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;


public class Main implements GraphSample
{
    static final int WIDTH = 600, HEIGHT = 600;

    public String getName()
    {
        return "Example";
    }

    public int getWidth()
    {
        return WIDTH;
    }

    public int getHeight()
    {
        return HEIGHT;
    }


    @Override
    public void draw(Graphics2D g, Component c)
    {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(Color.WHITE);
        g2D.fillRect(0, 0, getWidth(), getHeight());

        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.setStroke(new BasicStroke(2));
        g2D.setColor(Color.BLACK);
        double centerX = getWidth()/2 + 120;
        double centerY = getHeight() / 2;
        g2D.draw(new Line2D.Double(0, centerY, 2 * centerX, centerY));
        g2D.draw(new Line2D.Double(centerX, 0, centerX,  2 * centerY));
        g2D.translate(centerX, centerY);
        g2D.setColor(Color.red);
        g2D.setStroke(new MyStroke(3,4));

        g2D.draw(new MyLine(0, 0, 180));


    }
}

class GraphSampleFrame extends JFrame
{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    static final String classname = "Main";

    public GraphSampleFrame(final GraphSample[] examples)
    {
        super("GraphSampleFrame");

        Container cpane = getContentPane();
        cpane.setLayout(new BorderLayout());
        final JTabbedPane tpane = new JTabbedPane();
        cpane.add(tpane, BorderLayout.CENTER);

        this.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });

        for (int i = 0; i < examples.length; i++)
        {
            GraphSample e = examples[i];
            tpane.addTab(e.getName(), new GraphSamplePane(e));
        }
    }

    public class GraphSamplePane extends JComponent
    {
        /**
         *
         */
        private static final long serialVersionUID = 1L;
        GraphSample example;
        Dimension size;

        public GraphSamplePane(GraphSample example)
        {
            this.example = example;
            size = new Dimension(example.getWidth(), example.getHeight());
            setMaximumSize(size);
        }

        public void paintComponent(Graphics g)
        {
            g.setColor(Color.white);
            g.fillRect(0, 0, size.width, size.height);
            g.setColor(Color.black);
            example.draw((Graphics2D) g, this);
        }

        public Dimension getPreferredSize()
        {
            return size;
        }

        public Dimension getMinimumSize()
        {
            return size;
        }
    }

    public static void main(String[] args)
    {
        GraphSample[] examples = new GraphSample[1];

        try
        {
            Class<?> exampleClass = Class.forName(classname);
            examples[0] = (GraphSample) exampleClass.newInstance();
        } catch (ClassNotFoundException e)
        {
            System.err.println("Couldn't find example: " + classname);
            System.exit(1);
        } catch (ClassCastException e)
        {
            System.err.println("Class " + classname + " is not a GraphSample");
            System.exit(1);
        } catch (Exception e)
        {
            System.err.println("Couldn't instantiate example: " + classname);
            System.exit(1);
        }

        GraphSampleFrame f = new GraphSampleFrame(examples);
        f.pack();
        f.setVisible(true);
    }
}

interface GraphSample
{
    public String getName();

    public int getWidth();

    public int getHeight();

    public void draw(Graphics2D g, Component c);
}
