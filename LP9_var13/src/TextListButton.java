import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * This JavaBean displays a multi-line message and up to three buttons.  It
 * fires an AnswerEvent when the user clicks on one of the buttons
 **/
public class TextListButton extends Panel
{

    protected String StaticText1 = "STATIC TEXT";
    protected String StaticText2 = "Sergey";
    protected String StaticText3 = "Rudman";
    protected String StaticText4 = "Sergey Rudman Prod.";

    // Properties of the bean.
    protected String StaticText = "Static Text";  // The message to display
    protected String ButtonText = "Button";
    protected String[] list = {"0 item", "1 item", "2 item", "3 item", "4 item", "5 item"};     // Text for the yes, no, & cancel buttons
    protected char commandKey = 'z';

    // Internal components of the panel
    protected javax.swing.JButton jButton1;
    protected javax.swing.JLabel jLabel1;
    protected javax.swing.JList<String> jList1;
    protected javax.swing.JScrollPane jScrollPane1;


    /**
     * The no-argument bean constructor, with default property values
     */
    public TextListButton()
    {
        initComponents();
    }


    /**
     * A constructor for programmers using this class "by hand"
     */
    public TextListButton(String StaticText, char key, String Button, String[] list1)
    {
        this.StaticText = StaticText;
        this.ButtonText = Button;
        this.commandKey = key;
        this.list = list1;
        initComponents();
    }

    private void initComponents()
    {
        jLabel1 = new javax.swing.JLabel(StaticText);
        jButton1 = new javax.swing.JButton(ButtonText);
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>(list);
        jScrollPane1.setViewportView(jList1);
        jButton1.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                fireEvent(new AnswerEvent(e, jList1.getSelectedIndex()));
            }
        });
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
                    fireEvent(new AnswerEvent(e, jList1.getSelectedIndex()));
            }

            @Override
            public void keyReleased(KeyEvent e)
            {

            }
        });

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
                    fireEvent(new AnswerEvent(e, jList1.getSelectedIndex()));
            }

            @Override
            public void keyReleased(KeyEvent e)
            {

            }
        });

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
                    fireEvent(new AnswerEvent(e, jList1.getSelectedIndex()));
            }

            @Override
            public void keyReleased(KeyEvent e)
            {

            }
        });

        jButton1.addKeyListener(new KeyListener()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {

            }

            @Override
            public void keyPressed(KeyEvent e)
            {
                if (e.getKeyChar() == commandKey)
                    fireEvent(new AnswerEvent(e, jList1.getSelectedIndex()));
            }

            @Override
            public void keyReleased(KeyEvent e)
            {

            }
        });


        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE))
                                .addContainerGap(144, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(118, Short.MAX_VALUE))
        );

    }// </editor-fold>//GEN-END:initComponents

    // Methods to query all of the bean properties.

    public String getStaticText()
    {
        return StaticText;
    }

    public String getButtonText()
    {
        return ButtonText;
    }

    public String[] getList()
    {
        return list;
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

    public void setList(String[] list)
    {
        this.list = list;
        jList1.setListData(list);
        jList1.setVisible((list != null));
        jList1.repaint();
    }

    public void setButtonText(String buttonText)
    {
        this.ButtonText = buttonText;
        jButton1.setText(buttonText);
        jButton1.setVisible((buttonText != null));
        jButton1.repaint();
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
        jButton1.setFont(f);
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
            listener.myEventAction(e);
        }
    }

    /**
     * A main method that demonstrates the class
     */
    public static void main(String[] args)
    {
        // Create an instance of InfoPanel, with title and message specified:
        TextListButton p = new TextListButton();
        p.setStaticText("Sergey Rudman Prod.");
        p.setCommandKey('q');



        // Register an action listener for the Panel.  This one just prints
        // the results out to the console.
        p.addAnswerListener(new AnswerListener()
        {

            @Override
            public void myEventAction(AnswerEvent e)
            {
                System.out.println("Selected index: "+ e.index);
            }
        });

        JFrame f = new JFrame();
        f.add(p);
        f.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
    }
}
