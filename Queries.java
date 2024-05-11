import java.sql.*;
public class Queries extends DBConnection
{
    Statement statement = connection.createStatement();
    int summ;
    public Queries() throws SQLException {}
    public void ExQueryForCars(String query) throws SQLException // если вначале Ех, то запрос типа Select, в противном случае все остальные
    {
        ResultSet resultSet = statement.executeQuery(query); // сделалали к БД запрос, получили массив данных, ниже развертываем его и вытаскиваем, что нужно, попутно показывая юзеру
        while (resultSet.next())
        {
            int id = resultSet.getInt("id"); // getInt для столбцов БД с типом int
            String mark = resultSet.getString("title"); // getString для столбцов БД с типом varchar, text и другие
            String model = resultSet.getString("model");
            int realese_year = resultSet.getInt("realese_year");
            int price = resultSet.getInt("price");
            int available = resultSet.getInt("available");
            String heated_seats = resultSet.getString("heated_seats");
            String heated_windshield = resultSet.getString("heated_windshield");
            String heated_steering_wheel = resultSet.getString("heated_steering_wheel");
            String climate_control = resultSet.getString("climate_control");
            int fuel_tank_capacity = resultSet.getInt("fuel_tank_capacity");
            int weight = resultSet.getInt("weight");
            int max_speed = resultSet.getInt("max_speed");
            String fuel_consumption = resultSet.getString("fuel_consumption");
            String fuel_mark = resultSet.getString("fuel_mark");
            float engine_volume = resultSet.getFloat("engine_volume");
            int max_power = resultSet.getInt("max_power");
            String engine_type = resultSet.getString("engine_type");
            System.out.println("Номер: "+id);
            System.out.println("Марка: "+mark);
            System.out.println("Модель: "+model);
            System.out.println("Год выпуска: "+realese_year);
            System.out.println("Подогрев сидений: "+heated_seats);
            System.out.println("Подогрев лобового стекла: "+heated_windshield);
            System.out.println("Подогрев руля: "+heated_steering_wheel);
            System.out.println("Климат-контроль: "+climate_control);
            System.out.println("Объем топливного бака: "+fuel_tank_capacity);
            System.out.println("Вес: "+weight);
            System.out.println("Макс. скорость: "+max_speed);
            System.out.println("Расход топлива: "+fuel_consumption);
            System.out.println("Вид топлива: "+fuel_mark);
            System.out.println("Объем двигателя: "+engine_volume);
            System.out.println("Мощность: "+max_power);
            System.out.println("Тип двигателя: "+engine_type);
            System.out.println("Цена: "+price);
            System.out.println("В наличии: "+available+"\n");
        }
    }
    public void ExQueryForInsurance(String query) throws SQLException
    {
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next())
        {
            int price = resultSet.getInt("price");
            String duration = resultSet.getString("duration");
            System.out.println("Цена: "+price);
            System.out.println("Длительность: "+duration);
            summ=price;
        }
    }
    public void ExecuteQueryForClients(String query) throws SQLException
    {
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next())
        {
            String service = resultSet.getString("service");
            int price = resultSet.getInt("summ");
            System.out.println("Вид услуги: "+service);
            System.out.println("Цена: "+price);
        }
    }
    public void UpdateQuery(String query) throws SQLException // это хз зачем я так сделал
    {
        statement.executeUpdate(query);
    }
    public void UpQueryForInsurance(String query) throws SQLException // аналогично хз, код говно, надо переписывать
    {
        statement.executeUpdate(query);
    }
}
