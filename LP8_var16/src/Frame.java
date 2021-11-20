import java.awt.*;
import javax.swing.JFrame;

public class Frame extends JFrame{
	private Information inf  = null;
    private final int X= 400, Y = 400;
    
	@Override
    public void paint(Graphics g){
      inf.paint(g);
    }
	public Frame(){
        super("Information");
        this.setSize(640, 480);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(X, Y);
        inf = new Information(new Color(255,255,255), new Color(0,0,255), 300);
	}
	public static void main(String [] args){
        Frame field = new Frame();
        field.setVisible(true);
    }
}
