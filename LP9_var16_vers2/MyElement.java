import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * This JavaBean displays a multi-line message and up to three buttons.  It
 * fires an AnswerEvent when the user clicks on one of the buttons
 **/
public class MyElement extends Panel
{
    // Properties of the bean.
    protected String StaticText = "Static Text";  // The message to display
    protected String[] list1 = {"1", "2", "3", "4", "5"};     // Text for the yes, no, & cancel buttons
    protected String[] list2 = {"1", "2", "3", "4", "5"};
    protected char commandKey = 'z';

    // Internal components of the panel
    protected javax.swing.JLabel jLabel1;
    protected javax.swing.JList jList1;
    protected javax.swing.JList jList2;
    protected javax.swing.JLabel jLabel2;
    protected javax.swing.JLabel jLabel3;
    protected javax.swing.JScrollPane jScrollPane1;
    protected javax.swing.JScrollPane jScrollPane2;


    /**
     * The no-argument bean constructor, with default property values
     */
    public MyElement()
    {
        initComponents();
    }


    /**
     * A constructor for programmers using this class "by hand"
     */
    public MyElement(String StaticText, char key, String[] list1, String[] list2)
    {
        this.StaticText = StaticText;
        this.commandKey = key;
        this.list1 = list1;
        this.list2 = list2;
        initComponents();
    }

    private void initComponents()
    {
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel1.setText(StaticText);
        jLabel2.setText("null");
        jLabel3.setText("null");

        jList1 = new JList<String>(list1);
        jList2 = new JList<String>(list2);

        jList1.addKeyListener(new KeyListener()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {

            }

            @Override
            public void keyPressed(KeyEvent e)
            {
                if (e.getKeyChar() == commandKey)
                    jLabel2.setText(String.valueOf(jList1.getSelectedIndex()));
            }

            @Override
            public void keyReleased(KeyEvent e)
            {

            }
        });
        jList1.setFocusable(true);
        jList2.addKeyListener(new KeyListener()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {

            }

            @Override
            public void keyPressed(KeyEvent e)
            {
                if (e.getKeyChar() == commandKey)
                    jLabel3.setText(String.valueOf(jList2.getSelectedIndex()));
            }

            @Override
            public void keyReleased(KeyEvent e)
            {

            }
        });
        jList2.setFocusable(true);
        setFocusable(true);

        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();

        jScrollPane1.setViewportView(jList1);
        jScrollPane2.setViewportView(jList2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(221, 221, 221)
                                .addComponent(jLabel1)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(62, 62, 62)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jScrollPane1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 140, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane2)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(116, 116, 116))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(48, 48, 48)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addGap(18, 18, 18)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel3))
                                .addContainerGap(38, Short.MAX_VALUE))
        );

    }// </editor-fold>//GEN-END:initComponents

    // Methods to query all of the bean properties.


    // Methods to set all of the bean properties.

    public void setStaticText(String StaticText)
    {
        this.StaticText = StaticText;
        jLabel1.setText(StaticText);
        jLabel1.setVisible((StaticText != null) && (StaticText.length() > 0));
    }

    public void setFont(Font f)
    {
        super.setFont(f);    // Invoke the superclass method
        jLabel1.setFont(f);
        jLabel2.setFont(f);
        jLabel3.setFont(f);
        jList1.setFont(f);
        jList2.setFont(f);
        validate();
    }

    /**
     * This field holds a list of registered ActionListeners.
     */
    protected Vector<MyKeyListener> listeners = new Vector<MyKeyListener>();

    /**
     * Register an action listener to be notified when a button is pressed
     */
    public void addMyKeyListener(MyKeyListener l)
    {
        listeners.addElement(l);
    }

    /**
     * Remove an Answer listener from our list of interested listeners
     */
    public void removeMyKeyListener(MyKeyListener l)
    {
        listeners.removeElement(l);
    }

    /**
     * Send an event to all registered listeners
     */
    public void fireEvent(AnswerEvent e)
    {
        // Make a copy of the list and fire the events using that copy.
        // This means that listeners can be added or removed from the original
        // list in response to this event.  We ought to be able to just use an
        // enumeration for the vector, but that doesn't actually copy the list.
        Vector list = (Vector) listeners.clone();
        for (int i = 0; i < list.size(); i++)
        {
            MyKeyListener listener = (MyKeyListener) list.elementAt(i);
            listener.event(e);
        }
    }

    /**
     * A main method that demonstrates the class
     */
    public static void main(String[] args)
    {
        // Create an instance of InfoPanel, with title and message specified:
        MyElement p = new MyElement();


        // Register an action listener for the Panel.  This one just prints
        // the results out to the console.
        p.addMyKeyListener(new MyKeyListener()
        {

            @Override
            public void event(AnswerEvent e)
            {

            }

            @Override
            public void keyTyped(KeyEvent e)
            {
                System.out.println(123);
            }

            @Override
            public void keyPressed(KeyEvent e)
            {

            }

            @Override
            public void keyReleased(KeyEvent e)
            {

            }
        });

        JFrame f = new JFrame();
        f.add(p);
        f.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
    }
}
