import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Frame extends JFrame
{

    private static final long serialVersionUID = 1L;
    public static final int CANVAS_WIDTH = 500;
    public static final int CANVAS_HEIGHT = 500;

    private DrawCanvas canvas;

    private static int border = 8;

    static byte[] brightenTable = new byte[256];

    static
    {
        for (int i = 0; i < 256; i++)
            brightenTable[i] = (byte) (Math.sqrt(i / 255.0) * 255);
    }

    static BufferedImageOp filter = new ConvolveOp(new Kernel(3, 3, new float[]{
            0.0f, -0.75f, 0.0f,
            -0.75f, 4.0f, -0.75f,
            0.0f, -0.75f, 0.0f}));

    public Frame()
    {
        canvas = new DrawCanvas();
        canvas.setPreferredSize(new Dimension(2 * CANVAS_WIDTH, CANVAS_HEIGHT));

        Container cp = getContentPane();
        cp.add(canvas);

        Dimension screenSize = getToolkit().getScreenSize();
        setLocation((screenSize.width - 2 * CANVAS_WIDTH) / 2, (screenSize.height - CANVAS_HEIGHT) / 2);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setTitle("Дорожный знак");
        setVisible(true);
    }

    private class DrawCanvas extends JPanel
    {

        private static final long serialVersionUID = 1L;

        @Override
        public void paintComponent(Graphics graphics)
        {
            super.paintComponent(graphics);

            Graphics2D g = (Graphics2D) graphics;

            setBackground(Color.WHITE);

            g.setPaint(new GradientPaint(70, 200, Color.LIGHT_GRAY, 70, 80, Color.white));
            g.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);


            draw(g);


            BufferedImage bimage = new BufferedImage(CANVAS_WIDTH, CANVAS_HEIGHT, BufferedImage.TYPE_INT_ARGB);
            Graphics2D image = bimage.createGraphics();
            draw(image);
            drawStrings(g, "Without filter", 10, 20);

            g.translate(CANVAS_WIDTH, 0);

            g.drawImage(filter.filter(bimage, null), 0, 0, null);
            drawStrings(g, "With filter", 10, 20);
        }
    }

    public static void draw(Graphics2D g)
    {
        g.setStroke(new BasicStroke(border));
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        MyShape roadsign = new MyShape();
        roadsign.paint(g);
    }

    public static void drawStrings(Graphics2D g, String str, int x, int y)
    {
        g.setStroke(new BasicStroke(border));
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Font font = new Font("Arial", Font.BOLD, 15);
        g.setFont(font);
        g.setColor(Color.BLACK);
        g.drawString(str, x, y);
    }

    public static void main(String[] args)
    {

        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new Frame();
            }
        });
    }
}