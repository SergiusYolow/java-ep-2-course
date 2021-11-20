import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.Serializable;

public class MyShape implements Shape, Serializable, Cloneable, Transferable {
    private static final long serialVersionUID = 1L;
    private float a = 150;
    public float x0 = 300, y0 = 200;
    private float minX, maxX;
    private float scale = 1;
    private float step = 1;

    public static DataFlavor qDataFlavor = new DataFlavor(MyShape.class, "Versiera");
    public static DataFlavor[] supportedFlavors = { qDataFlavor };

    private float graphFunc(float x) {
        return  (float) (Math.pow(a, 3) / (x*x + a * a));
    }

    public MyShape() {
        this.minX = -a;
        this.maxX = a;
    }

    public MyShape(float a, float x0, float y0, float scale, float step) {
        this.a = a;
        this.x0 = x0;
        this.y0 = y0;
        this.minX = -a;
        this.maxX = a;
        this.scale = scale;
        this.step = step;
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
    public void move(float dx, float dy) {
        x0 += dx;
        y0 += dy;
    }
    @Override
    public Rectangle getBounds() {
        float maxY = graphFunc(0);
        float minY = 0;
        return new Rectangle((int) (x0 + minX * scale), (int) (y0 - maxY * scale),
                (int) ((maxX - minX) * scale), (int) ((maxY - minY) * scale));
    }

    @Override
    public Rectangle2D getBounds2D() {
        float maxY = graphFunc(0);
        float minY = 0;
        return new Rectangle2D.Float(x0 + minX * scale, y0 - maxY * scale,
                (maxX - minX) * scale, (maxY - minY) * scale);
    }

    @Override
    public boolean contains(double x, double y) {
        return false;
    }

    @Override
    public boolean contains(Point2D p) {
        return false;
    }

    @Override
    public boolean intersects(double x, double y, double w, double h) {
        return intersects(new Rectangle2D.Double(x, y, w, h));
    }

    @Override
    public boolean intersects(Rectangle2D r) {
        for (float valX = minX; valX <= maxX; valX += step) {
            if (r.contains(x0 + valX, y0 - graphFunc(valX))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(double x, double y, double w, double h) {
        return false;
    }

    @Override
    public boolean contains(Rectangle2D r) {
        return false;
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at) {
        return new ShapeIterator(at);
    }
    public String toString() {
        return a + " " + x0 + " " + y0;
    }


    static MyShape getFromString(String line) {
        String[] arr = line.split(" ");
        return new MyShape(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]),
                Integer.parseInt(arr[2]),Integer.parseInt(arr[3]), Integer.parseInt(arr[4]));
    }

    void translate(int x, int y) {
        x0 += x;
        y0 += y;
    }
    @Override
    public PathIterator getPathIterator(AffineTransform at, double flatness) {
        return new ShapeIterator(at);
    }

    class ShapeIterator implements PathIterator {
        AffineTransform aff;
        boolean done = false;
        double h = Math.PI / 150;
        boolean start = true;
        double t = Math.PI/2-h;

        ShapeIterator(AffineTransform aff) {
            this.aff = aff;
        }
        @Override
        public int getWindingRule() {
            return WIND_NON_ZERO;
        }
        @Override
        public boolean isDone() {
            return done;
        }
        @Override
        public void next() {
            t -= h;
        }
        @Override
        public int currentSegment(float[] xy) {
            if (start) {
                xy[0] = (float)(-(a * Math.tan(t))+x0);
                xy[1] = (float)(-(a * Math.pow(Math.cos(t), 2))+y0);
                start = false;
                if (aff != null)
                    aff.transform(xy, 0, xy, 0, 1);

                return SEG_MOVETO;
            }
            if (t <= -Math.PI/2+h) {
                done = true;
                return SEG_CLOSE;
            }
            xy[0] = (float)(-(a * Math.tan(t))+x0);
            xy[1] = (float)(-(a * Math.pow(Math.cos(t), 2))+y0);
            return SEG_LINETO;
        }

        @Override
        public int currentSegment(double[] xy)
        {
            if (start) {
                xy[0] = -(a * Math.tan(t))+x0;
                xy[1] = -(a * Math.pow(Math.cos(t), 2))+y0;
                start = false;
                return SEG_MOVETO;
            }
            if (t <= -Math.PI/2+h) {
                done = true;
                return SEG_CLOSE;
            }
            xy[0] = -(a * Math.tan(t))+x0;
            xy[1] = -(a * Math.pow(Math.cos(t), 2))+y0;
            return SEG_LINETO;
        }
    }

    @Override
    public Object getTransferData(DataFlavor flavor)
            throws UnsupportedFlavorException, IOException {
        if (flavor.equals(qDataFlavor)) {
            return this;
        } else
            throw new UnsupportedFlavorException(flavor);
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return (DataFlavor[]) supportedFlavors.clone();
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return (flavor.equals(qDataFlavor));
    }
}