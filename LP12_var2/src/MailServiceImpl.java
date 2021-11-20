import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;


public class MailServiceImpl extends UnicastRemoteObject
        implements MailService
{
    private static final long serialVersionUID = 1L;

    private List<Client> userOnline;

    private String[] getOnlineUsers(String user)
    {
        LinkedList<String> result = new LinkedList<>();
        for (Client client : userOnline)
        {
            if (!client.user.equals(user))
                result.add(client.user);
        }
        if (result.size() == 0)
            return new String[]{};
        else
        {
            String[] res = new String[result.size()];
            int i =0;
            for (String str:result)
                res[i++] = str;
            return res;
        }
    }

    // инициализация сервера
    public MailServiceImpl() throws RemoteException
    {
        super();
        userOnline = new ArrayList<Client>();
    }

    @Override
    public String[] register(String user) throws RemoteException
    {
        System.out.println("register: " + user + " online & sent online users");
        userOnline.add(new Client(user));
        return getOnlineUsers(user);
    }

    @Override
    public String sendMessage(String source, String user, String message) throws RemoteException
    {
        System.out.println();
        boolean flag = false;
        for (Client client : userOnline)
        {
            if (client.user.equals(user))
            {
                client.addMessage(source, message);
                flag = true;
                break;
            }
        }
        if (flag)
        {
            System.out.println(source + " sent message: " + message + " from " + user);
            return "Message sent";
        }
        else
        {
            System.out.println(source + " not sent message: " + message + " from " + user + "because: " + user + " is offline");
            return "Message not sent: " + user + "is offline";
        }
    }

    @Override
    public String[] getMessage(String user) throws RemoteException
    {
        for (Client client : userOnline)
        {
            if (client.user.equals(user))
            {
                System.out.println(user + " get " + client.messages.size() + " messages");
                return client.getAllMessages();
            }
        }
        System.out.println(user + " get 0 messages");
        return new String[]{};
    }

    @Override
    public String unregister(String user) throws RemoteException
    {
        System.out.println("unregister: " + user + " offline");
        Client temp = null;
        for (Client client : userOnline)
        {
            if (client.user.equals(user))
            {
                temp = client;
                break;
            }
        }
        if (temp != null)
        {
            userOnline.remove(temp);
            return "Correct unregister";
        }
        else
            return "Bad unregister";

    }


    public static void main(String[] args) throws Exception
    {
        String localhost = "127.0.0.1";
        String RMI_HOSTNAME = "java.rmi.server.hostname";
        try
        {
            System.setProperty(RMI_HOSTNAME, localhost);
            // Создание удаленного RMI объекта
            MailService service = new MailServiceImpl();

            // Определение имени удаленного RMI объекта
            String serviceName = "MailService";

            System.out.println("Initializing " + serviceName);

            /*
             * Регистрация удаленного RMI объекта MailService
             * в реестре rmiregistry
             */
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind(serviceName, service);
            System.out.println("Start " + serviceName + '\n');
        } catch (RemoteException e)
        {
            System.err.println("RemoteException : " + e.getMessage());
            System.exit(1);
        } catch (Exception e)
        {
            System.err.println("Exception : " + e.getMessage());
            System.exit(2);
        }
    }

}