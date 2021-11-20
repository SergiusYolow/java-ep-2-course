
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class MyLine implements Shape
{

    private int centerX;
    private int centerY;

    private int a;

    public MyLine(int centerX, int centerY, int param)
    {
        this.centerX = centerX;
        this.centerY = centerY;
        this.a = param;
    }

    @Override public Rectangle getBounds()
    {
        return null;
    }

    @Override public Rectangle2D getBounds2D()
    {
        return null;
    }

    @Override public boolean contains(double x, double y)
    {
        return false;
    }

    @Override public boolean contains(Point2D p)
    {
        return false;
    }

    @Override public boolean intersects(double x, double y, double w, double h)
    {
        return false;
    }

    @Override public boolean intersects(Rectangle2D r)
    {
        return false;
    }

    @Override public boolean contains(double x, double y, double w, double h)
    {
        return false;
    }

    @Override public boolean contains(Rectangle2D r)
    {
        return false;
    }

    @Override public PathIterator getPathIterator(AffineTransform at)
    {
        return new ShapeIterator(at);
    }

    @Override public PathIterator getPathIterator(AffineTransform at, double flatness)
    {
        return new ShapeIterator(at);
    }

    class ShapeIterator implements PathIterator
    {
        AffineTransform aff;
        boolean done = false;
        boolean start = true;
        double step = Math.PI / 360;
        double t = -Math.PI / 2 + step;

        ShapeIterator(AffineTransform aff)
        {
            this.aff = aff;
        }

        @Override public int getWindingRule()
        {
            return WIND_NON_ZERO;
        }

        @Override public boolean isDone()
        {
            return done;
        }

        @Override public void next()
        {
            t += step;
        }

        @Override public int currentSegment(float[] xy)
        {
            if (start)
            {
                xy[0] = (float) ((3 * a / Math.sqrt(2)) * (Math.pow(Math.tan(t), 2) - 1) / (3 * Math.pow(Math.tan(t), 2) + 1)) + centerX;
                xy[1] = (float) ((3 * a / Math.sqrt(2)) * Math.tan(t) * (Math.pow(Math.tan(t), 2) - 1) / (3 * Math.pow(Math.tan(t), 2) + 1)) + centerY;
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
            xy[0] = (float) ((3 * a / Math.sqrt(2)) * (Math.pow(Math.tan(t), 2) - 1) / (3 * Math.pow(Math.tan(t), 2) + 1)) + centerX;
            xy[1] = (float) ((3 * a / Math.sqrt(2)) * Math.tan(t) * (Math.pow(Math.tan(t), 2) - 1) / (3 * Math.pow(Math.tan(t), 2) + 1)) + centerY;
            return SEG_LINETO;
        }

        @Override public int currentSegment(double[] xy)
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