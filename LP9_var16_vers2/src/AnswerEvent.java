/**
 * The YesNoPanel class fires an event of this type when the user clicks one
 * of its buttons.  The id field specifies which button the user pressed.
 **/
public class AnswerEvent extends java.util.EventObject
{
    protected int first, second;

    public AnswerEvent(Object source, int first,int second)
    {
        super(source);
        this.first = first;
        this.second = second;
    }

    public int[] getIDS()
    {
        int [] arr = {first,second};
        return arr;
    }
}
