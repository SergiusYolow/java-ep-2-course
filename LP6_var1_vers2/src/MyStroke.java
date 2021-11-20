import java.awt.BasicStroke;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;

public class MyStroke implements Stroke {
    BasicStroke stroke;

    public MyStroke(float width) {
        this.stroke = new BasicStroke(width);
    }

    public Shape createStrokedShape(Shape shape) {
        GeneralPath shape1 = new GeneralPath();

        float[] xy = new float[2];
        float[] prexy = new float[2];
        double t = -5;
        for (PathIterator i = shape.getPathIterator(null); !i.isDone(); i
                .next()) {
            int type = i.currentSegment(xy);
            switch (type) {
                case PathIterator.SEG_MOVETO:
                    shape1.moveTo(xy[0], xy[1]);
                    break;

                case PathIterator.SEG_LINETO:
                    double x1 = prexy[0];
                    double y1 = prexy[1];
                    double x2 = xy[0];
                    double y2 = xy[1];

                    double dx = x2 - x1;
                    double dy = y2 - y1;

                    double length = Math.sqrt(dx*dx + dy*dy);
                    dx /= length;
                    dy /= length;
                    x1 += dx * t;
                    y1 += dy * t;
                    length -= t;
                    t = 0;

                    double step = 7;
                    if (!Double.isInfinite(length)) {

                        while (t <= length) {

                            x1 += -dx * step + dy * step;
                            y1 += dy * step + dx * step;
                            shape1.lineTo(x1, y1);

                            x1 += +dy * step;
                            y1 += -dx * step;
                            shape1.lineTo(x1, y1);

                            x1 += dx * step;
                            y1 += dy * step;
                            shape1.lineTo(x1, y1);

                            x1 += -dy * step;
                            y1 += +dx * step;
                            shape1.lineTo(x1, y1);

                            x1 += -dy * step + dx * step;
                            y1 += dx * step + dy * step;
                            shape1.lineTo(x1, y1);

                            t += 3 * step;
                        }
                        t -= length;
                    }
                    break;

            }

            prexy[0] = xy[0];
            prexy[1] = xy[1];

        }
        return stroke.createStrokedShape(shape1);
    }
}