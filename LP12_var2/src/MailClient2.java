import java.net.MalformedURLException;
import java.rmi.*;
import java.util.Arrays;
import java.util.Scanner;


public class MailClient2
{
    String localhost = "127.0.0.1";
    String RMI_HOSTNAME = "java.rmi.server.hostname";
    String SERVICE_PATH = "rmi://localhost/MailService";

    String user = "UserName2";
    String[] message = {"message1", "message2"};
    String direct = "UserName1";


    public MailClient2()
    {
        try
        {
            System.setProperty(RMI_HOSTNAME, localhost);
            // URL удаленного объекта
            String objectName = SERVICE_PATH;

            MailService mail;
            mail = (MailService) Naming.lookup(objectName);



            int command = 0;
            Scanner scanner2 = new Scanner(System.in);

            System.out.println( "1. Register\n" +
                                "2. SedMessage\n" +
                                "3. GetMessage\n" +
                                "4. Unregister\n" +
                                "5. Stop"
            );

            while ((command = scanner2.nextInt()) != 5)
            {
                switch (command)
                {
                    case 1:
                    {
                        System.out.println("Register user ...");
                        System.out.println("\nUser Online ...");
                        register(mail);
                        break;
                    }
                    case 2:
                    {
                        System.out.println("Send messages ...\n");
                        sendMessage(mail);
                        break;
                    }
                    case 3:
                    {
                        System.out.println("Get messages ...\n");
                        getMessages(mail);
                        break;
                    }
                    case 4:
                    {
                        System.out.println("Unregister ...\n");
                        unregister(mail);
                        break;
                    }
                    default:
                        System.out.println("incorrect command");
                }
            }
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        } catch (RemoteException e)
        {
            e.printStackTrace();
        } catch (NotBoundException e)
        {
            System.err.println("NotBoundException : " +
                    e.getMessage());
        }
    }


    private void register(MailService bs)
    {
        try
        {
            String[] res = bs.register(user);
            if (res.length == 0)
                System.out.println("No users online");
            else
            {
                for (String str : res)
                    System.out.print(str + " ");
                System.out.println();
            }

        } catch (RemoteException e)
        {
            System.err.println("RemoteException : " +
                    e.getMessage());
        }
    }


    private void sendMessage(MailService bs)
    {
        for (int i = 0; i < message.length; i++)
        {
            try
            {
                System.out.println(bs.sendMessage(user, direct, message[i]));
            } catch (RemoteException e)
            {
                System.err.println("RemoteException : " +
                        e.getMessage());
            }
        }
    }


    private void getMessages(MailService bs)
    {
        try
        {
            String[] res = bs.getMessage(user);
            for (String str : res)
                System.out.println(str + '\n');
        } catch (RemoteException e)
        {
            System.err.println("RemoteException : " +
                    e.getMessage());
        }

    }

    private void unregister(MailService bs)
    {
        try
        {
            System.out.println(bs.unregister(user));
        } catch (RemoteException e)
        {
            System.err.println("RemoteException : " +
                    e.getMessage());
        }
    }


    public static void main(String[] args)
    {
        new MailClient2();
        System.exit(0);
    }
}