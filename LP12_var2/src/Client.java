import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class Client
{
    String user;
    LinkedList<String> messages;

    public Client(String user)
    {
        this.user = user;
        this.messages = new LinkedList<String>();
    }

    public void addMessage(String source, String message)
    {
        messages.add("Sender: " + source + '\n' + "Message: " + message);
    }

    public String[] getAllMessages()
    {
        if (messages.size() == 0)
            return new String[]{};
        String[] res = new String[messages.size()];
        int i =0;
        for (String str:messages)
            res[i++]=str;
        messages.clear();
        return res;
    }

}
