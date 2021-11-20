import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class MyLine implements Shape, Transferable, Serializable, Cloneable
{

    private double centerX;
    private double centerY;

    private int a;

    public MyLine()
    {
        a = 150;
    }

    double maxX = 600;     // The bounding box
    double maxY = 600;
    double minX = -600;
    double minY = -600;



    public double getCenterX()
    {
        return centerX;
    }

    public void setCenterX(double centerX)
    {
        this.centerX = centerX;
    }

    public double getCenterY()
    {
        return centerY;
    }

    public void setCenterY(double centerY)
    {
        this.centerY = centerY;
    }

    public void translate(double x, double y)
    {
        this.centerX = x;
        this.centerY = y;
    }

    public void transl(double x,double y)
    {
        this.centerX+=x;
        this.centerY+=y;
    }


    /**
     * Clone a Scribble object and its internal array of data
     */
    public Object clone()
    {
        try
        {
            MyLine s = (MyLine) super.clone(); // make a copy of all fields
            return s;
        } catch (CloneNotSupportedException e)
        {  // This should never happen
            return this;
        }
    }

    /**
     * Create a new Scribble object and initialize it by parsing a string of
     * coordinate data in the format produced by toString()
     */
    public static MyLine parse(String s) throws NumberFormatException
    {
        StringTokenizer st = new StringTokenizer(s);
        MyLine scribble = new MyLine();
        while (st.hasMoreTokens())
        {
            String t = st.nextToken();
        }
        return scribble;
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at, double flatness)
    {
        return new ShapeIterator(at);
    }

    @Override
    public Rectangle getBounds()
    {
        return new Rectangle((int) (centerX + minX - 0.5f), (int) (centerY + minY - 0.5f), (int) (maxX - minX + 1f),
                (int) (maxY - minY + 1f));
    }

    @Override
    public Rectangle2D getBounds2D()
    {
        return new Rectangle2D.Double(minX, minY, maxX - minX, maxY - minY);
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
        return intersects(new Rectangle2D.Double(x, y, w, h));
    }

    @Override
    public boolean intersects(Rectangle2D r)
    {
        double t = 0;

        double x1, y1, x2, y2;

        x2 = ((3 * a / Math.sqrt(2)) * (Math.pow(Math.tan(t), 2) - 1) / (3 * Math.pow(Math.tan(t), 2) + 1)) + centerX;
        y2 = ((3 * a / Math.sqrt(2)) * Math.tan(t) * (Math.pow(Math.tan(t), 2) - 1) / (3 * Math.pow(Math.tan(t), 2) + 1)) + centerY;

        while (t <= Math.PI)
        {
            x1 = x2;
            y1 = y2;
            x2 = ((3 * a / Math.sqrt(2)) * (Math.pow(Math.tan(t), 2) - 1) / (3 * Math.pow(Math.tan(t), 2) + 1)) + centerX;
            y2 = ((3 * a / Math.sqrt(2)) * Math.tan(t) * (Math.pow(Math.tan(t), 2) - 1) / (3 * Math.pow(Math.tan(t), 2) + 1)) + centerY;
            t += Math.PI / 55;
            if (r.intersectsLine(x1, y1, x2, y2))
            {
                return true;
            }
        }

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
        return new ShapeIterator(at);
    }


    //====== The following methods implement the Transferable interface =====

    // This is the custom DataFlavor for Scribble objects
    public static DataFlavor scribbleDataFlavor = new DataFlavor(MyLine.class, "Scribble");

    // This is a list of the flavors we know how to work with
    public static DataFlavor[] supportedFlavors = {scribbleDataFlavor, DataFlavor.stringFlavor};

    /**
     * Return the data formats or "flavors" we know how to transfer
     */
    public DataFlavor[] getTransferDataFlavors()
    {
        return (DataFlavor[]) supportedFlavors.clone();
    }

    /**
     * Check whether we support a given flavor
     */
    public boolean isDataFlavorSupported(DataFlavor flavor)
    {
        return (flavor.equals(scribbleDataFlavor) || flavor.equals(DataFlavor.stringFlavor));
    }

    /**
     * Return the scribble data in the requested format, or throw an exception
     * if we don't support the requested format
     */
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException
    {
        if (flavor.equals(scribbleDataFlavor))
        {
            return this;
        }
        else if (flavor.equals(DataFlavor.stringFlavor))
        {
            return toString();
        }
        else
        {
            throw new UnsupportedFlavorException(flavor);
        }
    }


    class ShapeIterator implements PathIterator
    {
        AffineTransform aff;
        boolean done = false;
        boolean start = true;
        double step = Math.PI / 55;
        double t = -Math.PI / 2 + step;

        ShapeIterator(AffineTransform aff)
        {
            this.aff = aff;
        }

        @Override
        public int getWindingRule()
        {
            return WIND_NON_ZERO;
        }

        @Override
        public boolean isDone()
        {
            return done;
        }

        @Override
        public void next()
        {
            t += step;
        }

        @Override
        public int currentSegment(float[] xy)
        {
            if (start)
            {
                xy[0] = (float) ((float) ((3 * a / Math.sqrt(2)) * (Math.pow(Math.tan(t), 2) - 1) / (3 * Math.pow(Math.tan(t), 2) + 1)) + centerX);
                xy[1] = (float) ((float) ((3 * a / Math.sqrt(2)) * Math.tan(t) * (Math.pow(Math.tan(t), 2) - 1) / (3 * Math.pow(Math.tan(t), 2) + 1)) + centerY);
                start = false;
                if (aff != null)
                    aff.transform(xy, 0, xy, 0, 1);

                return SEG_MOVETO;
            }
            if (t >= Math.PI / 2 - step)
            {
                done = true;
                return SEG_CLOSE;
            }
            xy[0] = (float) ((float) ((3 * a / Math.sqrt(2)) * (Math.pow(Math.tan(t), 2) - 1) / (3 * Math.pow(Math.tan(t), 2) + 1)) + centerX);
            xy[1] = (float) ((float) ((3 * a / Math.sqrt(2)) * Math.tan(t) * (Math.pow(Math.tan(t), 2) - 1) / (3 * Math.pow(Math.tan(t), 2) + 1)) + centerY);
            return SEG_LINETO;
        }

        @Override
        public int currentSegment(double[] xy)
        {
            if (start)
            {
                xy[0] = ((3 * a / Math.sqrt(2)) * (Math.pow(Math.tan(t), 2) - 1) / (3 * Math.pow(Math.tan(t), 2) + 1)) + centerX;
                xy[1] = ((3 * a / Math.sqrt(2)) * Math.tan(t) * (Math.pow(Math.tan(t), 2) - 1) / (3 * Math.pow(Math.tan(t), 2) + 1)) + centerY;
                start = false;
                if (aff != null)
                    aff.transform(xy, 0, xy, 0, 1);
                return SEG_MOVETO;
            }
            if (t >= Math.PI / 2 - step)
            {
                done = true;
                return SEG_CLOSE;
            }
            xy[0] = ((3 * a / Math.sqrt(2)) * (Math.pow(Math.tan(t), 2) - 1) / (3 * Math.pow(Math.tan(t), 2) + 1)) + centerX;
            xy[1] = ((3 * a / Math.sqrt(2)) * Math.tan(t) * (Math.pow(Math.tan(t), 2) - 1) / (3 * Math.pow(Math.tan(t), 2) + 1)) + centerY;
            return SEG_LINETO;
        }
    }
}

