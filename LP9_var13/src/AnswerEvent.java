/**
 * The YesNoPanel class fires an event of this type when the user clicks one
 * of its buttons.  The id field specifies which button the user pressed.
 **/
public class AnswerEvent extends java.util.EventObject
{
    protected int index;

    public AnswerEvent(Object source, int index)
    {
        super(source);
        this.index = index;
    }
}
