import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * This JavaBean displays a multi-line message and up to three buttons.  It
 * fires an AnswerEvent when the user clicks on one of the buttons
 **/
public class TextWithLists extends Panel
{
    // Properties of the bean.
    protected String StaticText = "Static Text";  // The message to display
    protected String[] list1 = {"0 item", "1 item", "2 item", "3 item", "4 item", "5 item"};     // Text for the yes, no, & cancel buttons
    protected String[] list2 = {"0 item", "1 item", "2 item", "3 item", "4 item", "5 item"};
    protected char commandKey = 'z';

    // Internal components of the panel
    protected JLabel jLabel1;
    protected JList jList1;
    protected JList jList2;
    protected JScrollPane jScrollPane1;
    protected JScrollPane jScrollPane2;


    /**
     * The no-argument bean constructor, with default property values
     */
    public TextWithLists()
    {
        initComponents();
    }


    /**
     * A constructor for programmers using this class "by hand"
     */
    public TextWithLists(String StaticText, char key, String[] list1, String[] list2)
    {
        this.StaticText = StaticText;
        this.commandKey = key;
        this.list1 = list1;
        this.list2 = list2;
        initComponents();
    }

    private void initComponents()
    {
        jLabel1 = new JLabel();

        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);

        jLabel1.setText(StaticText);
        jList1 = new JList<String>(list1);
        jList2 = new JList<String>(list2);

        addKeyListener(new KeyListener()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {

            }

            @Override
            public void keyPressed(KeyEvent e)
            {
                if (e.getKeyChar() == commandKey)
                    fireEvent(new AnswerEvent(e, jList1.getSelectedIndex(), jList2.getSelectedIndex()));
            }

            @Override
            public void keyReleased(KeyEvent e)
            {

            }
        });
        setFocusable(true);
        jLabel1.addKeyListener(new KeyListener()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {

            }

            @Override
            public void keyPressed(KeyEvent e)
            {
                if (e.getKeyChar() == commandKey)
                    fireEvent(new AnswerEvent(e, jList1.getSelectedIndex(), jList2.getSelectedIndex()));
            }

            @Override
            public void keyReleased(KeyEvent e)
            {

            }
        });
        jLabel1.setFocusable(true);
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
                    fireEvent(new AnswerEvent(e, jList1.getSelectedIndex(), jList2.getSelectedIndex()));
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
                    fireEvent(new AnswerEvent(e, jList1.getSelectedIndex(), jList2.getSelectedIndex()));
            }

            @Override
            public void keyReleased(KeyEvent e)
            {

            }
        });
        jList2.setFocusable(true);


        jScrollPane1 = new JScrollPane();
        jScrollPane2 = new JScrollPane();

        jScrollPane1.setViewportView(jList1);
        jScrollPane2.setViewportView(jList2);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(62, 62, 62)
                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 140, Short.MAX_VALUE)
                                .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(116, 116, 116))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(138, 138, 138)
                                .addComponent(jLabel1)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(46, 46, 46)
                                .addComponent(jLabel1)
                                .addGap(20, 20, 20)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(72, Short.MAX_VALUE))
        );

    }// </editor-fold>//GEN-END:initComponents

    // Methods to query all of the bean properties.

    public String getStaticText()
    {
        return StaticText;
    }

    public String[] getList1()
    {
        return list1;
    }

    public String[] getList2()
    {
        return list2;
    }

    public char getCommandKey()
    {
        return commandKey;
    }


    // Methods to set all of the bean properties.

    public void setStaticText(String StaticText)
    {
        this.StaticText = StaticText;
        jLabel1.setText(StaticText);
        jLabel1.setVisible((StaticText != null) && (StaticText.length() > 0));
    }

    public void setList1(String[] list1)
    {
        this.list1 = list1;
        jList1.setListData(list1);
        jList1.setVisible((list1 != null));
        jList1.repaint();
    }

    public void setList2(String[] list2)
    {
        this.list2 = list2;
        jList2.setListData(list2);
        jList2.setVisible((list2 != null));
        jList2.repaint();
    }

    public void setCommandKey(char key)
    {
        commandKey = key;
    }

    public void setFont(Font f)
    {
        super.setFont(f);    // Invoke the superclass method
        jLabel1.setFont(f);
        jList1.setFont(f);
        jList2.setFont(f);
        validate();
    }

    /**
     * This field holds a list of registered ActionListeners.
     */
    protected Vector<AnswerListener> listeners = new Vector<AnswerListener>();

    /**
     * Register an action listener to be notified when a button is pressed
     */
    public void addAnswerListener(AnswerListener l)
    {
        listeners.addElement(l);
    }

    /**
     * Remove an Answer listener from our list of interested listeners
     */
    public void removeAnswerListener(AnswerListener l)
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
            AnswerListener listener = (AnswerListener) list.elementAt(i);
            listener.myKeyPressed(e);
        }
    }

//    /**
//     * A main method that demonstrates the class
//     */
//    public static void main(String[] args)
//    {
//        // Create an instance of InfoPanel, with title and message specified:
//        TextWithLists p = new TextWithLists();
//        p.setStaticText("Sergey Rudman Prod.");
//        p.setCommandKey('q');
//
//
//
//        // Register an action listener for the Panel.  This one just prints
//        // the results out to the console.
//        p.addAnswerListener(new AnswerListener()
//        {
//            @Override
//            public void myKeyPressed(AnswerEvent e)
//            {
//                System.out.println(Arrays.toString(e.getIDS()));
//            }
//        });
//
//        JFrame f = new JFrame();
//        f.add(p);
//        f.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
//        f.pack();
//        f.setVisible(true);
//    }
}
