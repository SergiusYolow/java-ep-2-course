import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class Triangle implements Shape
{


    public String getName()
    {
        return "Triangle";
    }

    public int getWidth()
    {
        return WIDTH;
    }

    public int getHeight()
    {
        return HEIGHT;
    }

    public void paint(Graphics2D ig)
    {
        BufferedImage bimage = new BufferedImage(WIDTH / 2, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = bimage.createGraphics();

        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        graphics2D.setPaint(new GradientPaint(0, 0, new Color(255, 255, 255), WIDTH / 2, 0, new Color(24, 24, 24)));
        graphics2D.fillRect(0, 0, WIDTH / 2, HEIGHT);


        int[] x = {60, 250, 440};
        int[] y = {420, 70, 420};


        Font font = new Font("Serif", Font.BOLD, 12);
        Font bigfont = font.deriveFont(AffineTransform.getScaleInstance(20.0, 20.0));
        GlyphVector gv = bigfont.createGlyphVector(graphics2D.getFontRenderContext(),
                "?");
        Shape shapeD = gv.getGlyphOutline(0);
        Shape myshape = new Polygon(x, y, 3);

        graphics2D.setStroke(new BasicStroke(5.0f));

        Paint shadowPaint = new Color(0, 0, 0, 100);
        AffineTransform shadowTransform = AffineTransform.getShearInstance(-1.15, 0.0);
        shadowTransform.scale(1.2, 0.5);

        graphics2D.translate(200, 275);
        graphics2D.setPaint(shadowPaint);
        graphics2D.fill(shadowTransform.createTransformedShape(myshape));

        graphics2D.setColor(new Color(0, 0, 200));
        graphics2D.setStroke(new BasicStroke(50, BasicStroke.CAP_BUTT, BasicStroke.CAP_BUTT));
        graphics2D.translate(-200, -255);
        graphics2D.drawPolygon(x, y, 3);
        graphics2D.setColor(Color.white);
        graphics2D.fillPolygon(x, y, 3);

        graphics2D.translate(190, 370);
        graphics2D.setPaint(new Color(0, 0, 200));
        graphics2D.fill(shapeD);


        ig.drawImage(bimage, 0, 0,null);
        ig.drawString("No filters", 10, 13);
        ig.drawImage(new ConvolveOp(new Kernel(3, 3, new float[]{
                0.0f, -0.75f, 0.0f,
                -0.75f, 4.0f, -0.75f,
                0.0f, -0.75f, 0.0f})).filter(bimage, null), WIDTH / 2, 0, null);
        ig.drawString("Sharpen", WIDTH / 2 + 10, 13);
        ig.drawLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT);
    }

    @Override
    public Rectangle getBounds()
    {
        return null;
    }

    @Override
    public Rectangle2D getBounds2D()
    {
        return null;
    }

    @Override
    public boolean contains(double x, double y)
    {
        return false;
    }

    @Override
    public boolean contains(Point2D p)
    {
        return false;
    }

    @Override
    public boolean intersects(double x, double y, double w, double h)
    {
        return false;
    }

    @Override
    public boolean intersects(Rectangle2D r)
    {
        return false;
    }

    @Override
    public boolean contains(double x, double y, double w, double h)
    {
        return false;
    }

    @Override
    public boolean contains(Rectangle2D r)
    {
        return false;
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at)
    {
        return null;
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at, double flatness)
    {
        return null;
    }
}

class MyFrame extends JFrame
{

    static final String classname = "Triangle";

    public MyFrame(final Triangle[] examples)
    {
        super("Triangle");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((screenSize.width - WIDTH) / 4, (screenSize.height - HEIGHT) / 4);

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
            Triangle e = examples[i];
            tpane.addTab("", new GraphSamplePane(e));
        }
    }

    public class GraphSamplePane extends JComponent
    {
        Triangle example;
        Dimension size;

        public GraphSamplePane(Triangle example)
        {
            this.example = example;
//            size = new Dimension(example.getWidth(), example.getHeight());
//            setMaximumSize(size);
        }

        public void paintComponent(Graphics g)
        {
            g.setColor(Color.white);
            g.fillRect(0, 0, size.width, size.height);
            g.setColor(Color.black);
            example.paint((Graphics2D) g);
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
        Triangle[] examples = new Triangle[1];

        try
        {
            Class exampleClass = Class.forName(classname);
            examples[0] = (Triangle) exampleClass.newInstance();
        } catch (ClassNotFoundException e)
        {
            System.err.println("Couldn't find example: " + classname);
            System.exit(1);
        } catch (ClassCastException e)
        {
            System.err.println("Class " + classname +
                    " is not a GraphSample");
            System.exit(1);
        } catch (Exception e)
        {
            System.err.println("Couldn't instantiate example: " +
                    classname);
            System.exit(1);
        }

        MyFrame f = new MyFrame(examples);
        f.pack();
        f.setVisible(true);
    }
}


