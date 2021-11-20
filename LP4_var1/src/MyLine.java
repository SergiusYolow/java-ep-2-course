import java.awt.BasicStroke;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;

public class MyLine implements Stroke
{
    BasicStroke stroke;

    public MyLine(float width)
    {
        this.stroke = new BasicStroke(width);
    }

    public Shape createStrokedShape(Shape shape)
    {
        GeneralPath newshape = new GeneralPath(); // Start with an empty shape
        double[] coords = new double[2];
        double[] prevCoord = new double[2];
        for (PathIterator i = shape.getPathIterator(null); !i.isDone(); i.next())
        {
            //comment////////////////////////////////////////////////////////////////////////////////////////////// //////////////////////////////////////////////////////////////////////////
            int type = i.currentSegment(coords);
            switch (type)
            {
                case PathIterator.SEG_MOVETO:
                    newshape.moveTo(coords[0], coords[1]);
                    break;
                case PathIterator.SEG_LINETO:
                    double x = coords[0] - prevCoord[0];
                    double y = coords[1] - prevCoord[1];
                    double len = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
                    int n = (int) (len / (3 * stroke.getLineWidth()));
                    if (n == 0)
                    {
                        double x1 = prevCoord[0];
                        double y1 = prevCoord[1];
                        double x2 = coords[0];
                        double y2 = coords[1];

                        double dx = x2 - x1;
                        double dy = y2 - y1;

                        newshape.lineTo(x1 + 2 * dy, y1 - 2 * dx);
                        newshape.lineTo(x2, y2);

                        break;
                    }
                    x /= n;
                    y /= n;
                    for (int j = 0; j < n; j++)
                    {
                        double x1 = prevCoord[0] + x * j;
                        double y1 = prevCoord[1] + y * j;
                        double x2 = prevCoord[0] + x * (j + 1);
                        double y2 = prevCoord[1] + y * (j + 1);

                        double dx = x2 - x1;
                        double dy = y2 - y1;

                        newshape.lineTo(x1 + 2 * dy, y1 - 2 * dx);
                        newshape.lineTo(x2, y2);
                    }
                    break;
            }
            prevCoord[0] = coords[0];
            prevCoord[1] = coords[1];
        }
        return stroke.createStrokedShape(newshape);
    }
}
