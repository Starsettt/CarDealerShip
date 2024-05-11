import java.sql.*;
public class DBConnection
{
    public DBConnection() throws SQLException {}
    private final String url = "jdbc:mysql://localhost/cards?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=Europe/Moscow"; // строка подключения к БД
    private final String user = "root"; // имя пользователя, за которого будет осуществляться вход
    private final String pass = "Whoiam89%who"; // пароль для входа в качестве указанного пользователя
    protected Connection connection = DriverManager.getConnection(url,user,pass); // создание объекта класса Connection с помощью метода getConnection и установление соединения с БД
    public void Connection()
    {
        try
        {
            Class.forName("сom.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance(); // вот это сам хз
            try
            {
                Connection connection = DriverManager.getConnection(url,user,pass); // создание объекта класса Connection с помощью метода getConnection и установление соединения с БД
            }
            catch (Exception e)
            {
                System.out.println("Ошибка установления соединения с БД:" + e);
            }
        }
        catch (Exception e)
        {
            System.out.println("Ошибка взаимодействия с драйвером:" + e);
        }
    }
}