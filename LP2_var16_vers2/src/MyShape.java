import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class MyShape implements Shape
{
    MyShape()
    {
    }

    public void paint(Graphics graphics)
    {

        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        graphics2D.setPaint(new GradientPaint(0, 0, new Color(255, 255, 255), WIDTH, 0, new Color(24, 24, 24)));
        graphics2D.fillRect(0, 0, WIDTH, HEIGHT);


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
        graphics2D.translate(-200, -275);
        graphics2D.drawPolygon(x, y, 3);
        graphics2D.setColor(Color.white);
        graphics2D.fillPolygon(x, y, 3);

        graphics2D.translate(190, 390);
        graphics2D.setPaint(new Color(0, 0, 200));
        graphics2D.fill(shapeD);
        graphics2D.translate(-190, -390);
    }

    @Override
    public boolean contains(Point2D arg0)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean contains(Rectangle2D arg0)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean contains(double arg0, double arg1)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean contains(double arg0, double arg1, double arg2, double arg3)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Rectangle getBounds()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Rectangle2D getBounds2D()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PathIterator getPathIterator(AffineTransform arg0)
    {
        // TODO Auto-generated method stub
        return null;
    }

    static final int WIDTH = 500, HEIGHT = 500;

    @Override
    public PathIterator getPathIterator(AffineTransform arg0, double arg1)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean intersects(Rectangle2D arg0)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean intersects(double arg0, double arg1, double arg2, double arg3)
    {
        // TODO Auto-generated method stub
        return false;
    }

}