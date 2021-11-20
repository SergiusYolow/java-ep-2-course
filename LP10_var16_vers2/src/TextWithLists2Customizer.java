import java.awt.*;
import java.awt.event.*;
import java.beans.*;

/**
 * This class is a customizer for the YesNoPanel bean.  It displays a
 * TextArea and three TextFields where the user can enter the main message
 * and the labels for each of the three buttons.  It does not allow the
 * alignment property to be set.
 **/
public class TextWithLists2Customizer extends Panel
        implements Customizer, TextListener
{
    protected TextWithLists2 bean;    // The bean being customized
    private java.awt.Label label1;
    private java.awt.Label label2;
    private java.awt.Label label3;
    private java.awt.TextArea textArea1;
    private java.awt.TextArea textArea2;
    private java.awt.TextField textField1;

    // The bean box calls this method to tell us what object to customize.
    // This method will always be called before the customizer is displayed,
    // so it is safe to create the customizer GUI here.
    public void setObject(Object o)
    {
        bean = (TextWithLists2) o;   // save the object we're customizing

        StringBuilder list1 = new StringBuilder();
        StringBuilder list2 = new StringBuilder();
        String[] temp = {"empty"};
        temp = bean.getList1();
        for (String str : temp)
            list1.append(str).append('\n');
        temp = bean.getList2();
        for (String str : temp)
            list2.append(str).append('\n');


        textArea1 = new java.awt.TextArea(list1.toString());
        textArea2 = new java.awt.TextArea(list2.toString());
        label1 = new java.awt.Label("List 1");
        label2 = new java.awt.Label("List 2");
        label3 = new java.awt.Label("Static text");
        textField1 = new java.awt.TextField(bean.getStaticText());

        textArea1.addTextListener(this);
        textArea2.addTextListener(this);
        textField1.addTextListener(this);


        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(textArea1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(textArea2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(textField1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(20, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(textArea2, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                                        .addComponent(textArea1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(textField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );
    }

    // Add some space around the outside of the panel.
    public Insets getInsets()
    {
        return new Insets(10, 10, 10, 10);
    }

    // This is the method defined by the TextListener interface.  Whenever the
    // user types a character in the TextArea or TextFields, this will get
    // called.  It updates the appropriate property of the bean and fires a
    // property changed event, as all customizers are required to do.
    // Note that we are not required to fire an event for every keystroke.
    // Instead we could include an "Apply" button that would make all the
    // changes at once, with a single property changed event.
    public void textValueChanged(TextEvent e)
    {
        TextComponent t = (TextComponent) e.getSource();
        String s = t.getText();
        if (t == textField1) bean.setStaticText(s);
        else if (t == textArea1) bean.setList1(s.split("\n"));
        else if (t == textArea2) bean.setList2(s.split("\n"));
        listeners.firePropertyChange(null, null, null);
    }

    // This code uses the PropertyChangeSupport class to maintain a list of
    // listeners interested in the edits we make to the bean.
    protected PropertyChangeSupport listeners = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener l)
    {
        listeners.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l)
    {
        listeners.removePropertyChangeListener(l);
    }
}
