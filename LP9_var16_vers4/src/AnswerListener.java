/**
 * Classes that want to be notified when the user clicks a button in a
 * YesNoPanel should implement this interface.  The method invoked depends
 * on which button the user clicked.
 **/
public interface AnswerListener extends java.util.EventListener {
    public void myKeyPressed(AnswerEvent e);
}
