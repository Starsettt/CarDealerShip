import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Scanner;
public class Options extends Queries
{
    private Scanner input = new Scanner(System.in); // создание объекта класса Scanner для считывания пользовательского ввода
    public Options() throws SQLException {} // конструктор класса, реализующий поддержку исключений типа SQLException
    public void SalonOptions() throws SQLException
    {
        System.out.println("Добро пожаловать в наш автосалон!\nКак в кам обращаться?");
        String name = input.next(); // ввод с консоли имени пользователя и записи в переменную
        System.out.println(name + ", по какому вопросу вас проконсультировать?");
        System.out.println("1 - Покупка автомобиля\n2 - Страхование автомобиля\n3 - Тех.Обслуживание автомобиля\n4 - История посещения");
        int option = input.nextInt(); // аналогично, но уже с номером опции

        System.out.println("Пожалуйста, введите свои данные в следующем порядке: ФИО, номер телефона, серия паспорта, номер паспорта");
        String full_name = input.next(); // аналогично, см выше порядок что есть что
        String phone = input.next();
        String passport_series = input.next();
        String passport_number = input.next();
        String cl_ph=""; // создание переменной для сравнения
        ResultSet resulterSet = statement.executeQuery("SELECT phone FROM Clients where phone="+phone+";"); // получение данных из БД с помощью метода executeQuery и записи в объект класса ResultSet. В данном случае получение номера телефона
        while (resulterSet.next()) // перебор значений, хранящихся в объекте
        {
            String client_phone = resulterSet.getString("phone"); // присвоение переменной значения из объекта класса ResultSet с помощью метода getString
            cl_ph=client_phone;
        }
        if(phone.equals(cl_ph)) // если человек уже был ранее, то ничего не делаем
        {

        }
        else // в противном случае добавляем его в бд с помощью метода UpdateQuery, описанного в классе Queries
        {
            UpdateQuery("INSERT INTO clients(full_name,phone,passport_series,passport_number) values(" + "'" + full_name + "'" + "," + "'" + phone + "'" + "," + "'" + passport_series + "'" + "," + "'" + passport_number + "'" + ");");
        }
        switch (option)
        {
            case 1: // покупка автомобиля
                System.out.println("На данный момент наш автосалон имеет следующие автомобили:");
                ExQueryForCars("SELECT * FROM cars JOIN carinfo ON cars.info_id=carinfo.id JOIN specials ON cars.special_id = specials.id JOIN marks ON carinfo.mark_id=marks.id;"); // получение информации о текущих автомобилях из БД с помощью метода, описанного в классе Queries
                System.out.println("Желаете приобрести что-либо?(да/нет)");
                String response = input.next().toLowerCase(); // запись ответа пользователя в переменную и приведение к нижнему регистру
                if(response.equals("да"))
                {
                    System.out.println("Назовите номер автомобиля:");
                    String number = input.next();
                    int count = 0;
                    int stoimost=0;
                    ResultSet result = statement.executeQuery("SELECT price,available from cars where id="+number+";"); // получение данных из БД с помощью метода executeQuery и записи в объект класса ResultSet. В данном случае получение цены и количества
                    while (result.next())
                    {
                        int num = result.getInt("available"); // присвоение переменной значения из объекта класса ResultSet с помощью метода getInt
                        int price = result.getInt("price");  // присвоение переменной значения из объекта класса ResultSet с помощью метода getInt
                        count=num;
                        stoimost=price;
                    }
                    if (count==1) // если авто в количестве 1 штуки, то
                    {
                        ResultSet resultSet = statement.executeQuery("SELECT id from clients where phone="+phone+";"); // получаем айдишник пользователя по его номеру телефона
                        int id=0;
                        while (resultSet.next())
                        {
                            int client_id = resultSet.getInt("id"); // получаем значение айдишника
                            id = client_id; // приравниваем переменные, чтобы использовать нужную в другом запросе
                        }
                        UpdateQuery("DELETE FROM cars where id="+number+";"); // удаляем инфу об авто из таблицы cars
                        UpdateQuery("DELETE FROM carinfo where id="+number+";"); // удаляем инфу об авто из таблицы carsinfo
                        UpQueryForInsurance("INSERT sells(service_id,client_id,summ) values(1,"+id+","+stoimost+");"); // добавляем в таблицу sells запись о продаже авто
                    }
                    else // тут аналогично, только вместо удаления инфы просто уменьшаем количество авто на 1
                    {
                        ResultSet resultSet = statement.executeQuery("SELECT id from clients where phone="+phone+";");
                        int id=0;
                        while (resultSet.next())
                        {
                            int client_id = resultSet.getInt("id");
                            id = client_id;
                        }
                        UpdateQuery("UPDATE cars set available=available-1 where id="+number+";");
                        UpQueryForInsurance("INSERT sells(service_id,client_id,summ) values(1,"+id+","+stoimost+");");
                    }
                    break;
                }
                else
                {
                    break;
                }
            case 2:
                System.out.println("Выберите необходимый вид:\n1 - ОСАГО\n2 - КАСКО\n3 - ДСАГО");
                String choice = input.next();
                if(choice.equals("1"))
                {
                    ExQueryForInsurance("SELECT duration,price from services where id=2;"); // получаем инфу о длительности и цене услуги
                    System.out.println("Вы уверены?(да/нет)");
                    String insChoice = input.next().toLowerCase(Locale.ROOT);
                    if(insChoice.equals("да")) //все то же самое, что и с авто, только здесь просто добавляем запись о том, что была проведена услуга ОСАГО
                    {
                        ResultSet resultSet = statement.executeQuery("SELECT id from clients where phone="+phone+";");
                        int id=0;
                        while (resultSet.next())
                        {
                            int client_id = resultSet.getInt("id");
                            id = client_id;
                        }
                        UpQueryForInsurance("INSERT sells(service_id,client_id,summ) values(2,"+id+","+summ+");");
                        break;
                    }
                    else
                    {
                        break;
                    }
                }
                else if (choice.equals("2")) //все то же самое, что и выше, только здесь просто добавляем запись о том, что была проведена услуга КАСКО
                {
                    ExQueryForInsurance("SELECT duration,price from services where id=3;");
                    System.out.println("Вы уверены?(да/нет)");
                    String insChoice = input.next().toLowerCase(Locale.ROOT);
                    if(insChoice.equals("да"))
                    {
                        ResultSet resultSet = statement.executeQuery("SELECT id from clients where phone="+phone+";");
                        int id=0;
                        while (resultSet.next())
                        {
                            int client_id = resultSet.getInt("id");
                            id = client_id;
                        }
                        UpQueryForInsurance("INSERT sells(service_id,client_id,summ) values(3,"+id+","+summ+");");
                        break;
                    }
                    else
                    {
                        break;
                    }
                }
                else if (choice.equals("3")) //все то же самое, что и выше, только здесь просто добавляем запись о том, что была проведена услуга ДСАГО
                {
                    ExQueryForInsurance("SELECT duration,price from services where id=4;");
                    System.out.println("Вы уверены?(да/нет)");
                    String insChoice = input.next().toLowerCase(Locale.ROOT);
                    if(insChoice.equals("да"))
                    {
                        ResultSet resultSet = statement.executeQuery("SELECT id from clients where phone="+phone+";");
                        int id=0;
                        while (resultSet.next())
                        {
                            int client_id = resultSet.getInt("id");
                            id = client_id;
                        }
                        UpQueryForInsurance("INSERT sells(service_id,client_id,summ) values(4,"+id+","+summ+");");
                        break;
                    }
                    else
                    {
                        break;
                    }
                }
                break;
            case 3: // принцип такой же, как и со страхованием
                System.out.println("Какая конкретная услуга вас интересует?\n1 - Проф.осмотр\n2 - Покраска\n3 - Замена масла\n4 - Замена деталей");
                int number = input.nextInt()+4;
                ExQueryForInsurance("SELECT duration,price from services where id="+number+";");
                System.out.println("Вы уверены?(да/нет)");
                String insChoice = input.next().toLowerCase(Locale.ROOT);
                if(insChoice.equals("да"))
                {
                    ResultSet resultSet = statement.executeQuery("SELECT id from clients where phone="+phone+";");
                    int id=0;
                    while (resultSet.next())
                    {
                        int client_id = resultSet.getInt("id");
                        id = client_id;
                    }
                    UpQueryForInsurance("INSERT sells(service_id,client_id,summ) values("+number+","+id+","+summ+");");
                    break;
                }
                else
                {
                    break;
                }
            case 4:// история покупок
                ResultSet resultSet = statement.executeQuery("SELECT id from clients where phone="+phone+";");
                int id=0;
                while (resultSet.next())
                {
                    int client_id = resultSet.getInt("id");
                    id = client_id;
                }
                ExecuteQueryForClients("SELECT * FROM sells join services on sells.service_id=services.id where client_id="+id+";");
                break;
        }
    }
}
// везде один и тот же принцип. Спрашиваем, получаем данное о том, что юзеру надо, делаем запрос к БД, даем юзеру инфу, если что-то надо, то изменения в БД обратно вносятся действиями юзера