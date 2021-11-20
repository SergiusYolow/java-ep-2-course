import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Test
{
    public static void main(String[] args)
    {
        // Create an instance of InfoPanel, with title and message specified:
        MyElement p = new MyElement();

        // Register an action listener for the Panel.  This one just prints
        // the results out to the console.
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        f.add(p);
        f.pack();
        f.setVisible(true);
    }
}
