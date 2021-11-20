import java.rmi.*;

public interface MailService extends Remote
{
    // Регистрация
    public String[] register(String user) throws RemoteException;

    // Отправка сообщения
    public String sendMessage(String source,String user, String message) throws RemoteException;

    // Получение сообщений
    public String[] getMessage(String user) throws RemoteException;

    // Отключение
    public String unregister(String user) throws RemoteException;

}