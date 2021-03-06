/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Vector;
import javax.swing.JLabel;

public class Message extends Panel {
    
    protected String label;
    protected String button;
    protected char in;

    protected JLabel jL;
    protected Button Result;

    public Message(String label) {
    }

    public Message(String label, String button) {
        // Create the components for this panel
        setLayout(new BorderLayout(15, 15));
        Panel fields = new Panel();
        jL = new JLabel();
        fields.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 15));
        add(fields, BorderLayout.NORTH);
        fields.add(jL);
        Result = new Button();
        Result.setSize(50, 20);
        add(Result, BorderLayout.SOUTH);

        Result.addActionListener((ActionEvent e) -> {
            fireEvent(new EventListener(Message.this,
                    EventListener.MESS1));
        });
        setLabel(label);
        setButton(button);
    }

    public String getLabel() {
        return label;
    }

    public String getButton() {
        return button;
    }

    public void setLabel(String label) {
        this.label = label;
        jL.setText(label);
        validate();
    }

    public void setButton(String button) {
        this.button = button;
        Result.setLabel(button);
        Result.setVisible((button != null) && (button.length() > 0));
        validate();
    }

    
    protected Vector<AnswerListener> listeners = new Vector<AnswerListener>();

    public void addAnswerListener(AnswerListener l) {
        listeners.addElement(l);
    }

    public void removeAnswerListener(AnswerListener l) {
        listeners.removeElement(l);
    }
    

    public void fireEvent(EventListener e) {
        Vector<AnswerListener> list  = new Vector<AnswerListener>();
         list = (Vector) listeners.clone();
        for (int i = 0; i < list.size(); i++) {
            AnswerListener listener = (AnswerListener)list.elementAt(i);
            switch (e.getID()) {
                case EventListener.MESS1:
                    listener.Result(e);
                    break;
            }
        }
    }
}
