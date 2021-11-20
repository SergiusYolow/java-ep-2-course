import java.awt.*;

interface GraphSample {
    public String getName();
    public int getWidth();
    public int getHeight();
    public void draw(Graphics2D g, Component c);
}