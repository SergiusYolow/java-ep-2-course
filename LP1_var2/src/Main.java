import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.lang.reflect.Field;

public class Main
{
    public static void main(String[] args)
    {
        JFrame fr = new JFrame("Приближение и отдаление шара");
        final Dimension[] frameSize = {new Dimension(600, 600)};
        fr.setPreferredSize(frameSize[0]);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        fr.setLocation((screenSize.width - frameSize[0].width) / 2, (screenSize.height - frameSize[0].height) / 2);

        fr.setVisible(true);
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.pack();

        Color c2 = null;
        Color c = null;
        {

            Field field = null;
            try
            {
                field = Class.forName("java.awt.Color").getField(args[1].toUpperCase());
            } catch (NoSuchFieldException e)
            {
                e.printStackTrace();
            } catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            }

            try
            {
                c = (Color) field.get(null);
            } catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }

            try
            {
                field = Class.forName("java.awt.Color").getField(args[2].toLowerCase());
            } catch (NoSuchFieldException e)
            {
                e.printStackTrace();
            } catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            }

            try
            {
                c2 = (Color) field.get(null);
            } catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }

        Color finalC = c;
        Color finalC1 = c2;
        Timer tm = new Timer(40, new ActionListener()
        {
            int k = 1;
            int i = 101;

            @Override
            public void actionPerformed(ActionEvent agr0)
            {
                Graphics2D gr = (Graphics2D) fr.getRootPane().getGraphics();
                fr.update(gr);
                frameSize[0] = fr.getSize();
                gr.translate(frameSize[0].width / 2, frameSize[0].height / 2 - 30);
                AffineTransform tranformation = AffineTransform.getScaleInstance(i * 0.01, i * 0.01);
                gr.transform(tranformation);
                gr.setStroke(new BasicStroke(Integer.parseInt(args[0])));
                int size = 100;
                Circle shape = new Circle(0,0,size, finalC, finalC1);
                shape.paint(gr);
                if (i == 100 || i == 200)
                    k = -k;
                i += k;
            }
        });
        tm.start();
    }
}