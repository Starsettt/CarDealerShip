import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Scanner;
public class Example extends Queries
{
    Scanner input = new Scanner(System.in);
    String phone_number;
    public Example() throws SQLException {}
    public void SalonOptions() throws SQLException
    {
        System.out.println("Добро пожаловать в наш автосалон!\nКак в кам обращаться?");
        String name = input.next();
        System.out.println(name + ", по какому вопросу вас проконсультировать?");

        System.out.println("1 - Покупка автомобиля\n2 - Страхование автомобиля\n3 - Тех.Обслуживание автомобиля\n4 - История посещения");
        int option = input.nextInt();

        System.out.println("Пожалуйста, введите свои данные в следующем порядке: ФИО, номер телефона, серия паспорта, номер паспорта");
        String full_name = input.next();
        String phone = input.next();
        phone_number = phone;
        String passport_series = input.next();
        String passport_number = input.next();
        String cl_ph="";
        ResultSet resulterSet = statement.executeQuery("SELECT phone FROM Clients where phone="+phone+";");
        while (resulterSet.next())
        {
            String client_phone = resulterSet.getString("phone");
            cl_ph=client_phone;
        }
        if(phone.equals(cl_ph))
        {

        }
        else
        {
            UpdateQuery("INSERT INTO clients(full_name,phone,passport_series,passport_number) values(" + "'" + full_name + "'" + "," + "'" + phone + "'" + "," + "'" + passport_series + "'" + "," + "'" + passport_number + "'" + ");");
        }
        switch (option)
        {
            case 1:
                OptionNum1();
                break;
            case 2:
                OptionNum2();
                break;
            case 3:
                OptionNum3();
                break;
            case 4:
                OptionNum4();
                break;
        }
        /*
            case 1 -> OptionNum1();
            case 2 -> OptionNum2();
            case 3 -> OptionNum3();
            case 4 -> OptionNum4();
         */
    }
    public void OptionNum1() throws SQLException
    {
        System.out.println("На данный момент наш автосалон имеет следующие автомобили:");
        ExQueryForCars("SELECT * FROM cars JOIN carinfo ON cars.info_id=carinfo.id JOIN specials ON cars.special_id = specials.id JOIN marks ON carinfo.mark_id=marks.id;");
        System.out.println("Желаете приобрести что-либо?(да/нет)");
        String response = input.next().toLowerCase(Locale.ROOT);
        if(response.equals("да"))
        {
            System.out.println("Назовите номер автомобиля:");
            String number = input.next();
            int count = 0;
            int stoimost=0;
            ResultSet result = statement.executeQuery("SELECT price,available from cars where id="+number+";");
            while (result.next())
            {
                int num = result.getInt("available");
                int price = result.getInt("price");
                count=num;
                stoimost=price;
            }
            if (count==1)
            {
                ResultSet resultSet = statement.executeQuery("SELECT id from clients where phone="+phone_number+";");
                int id=0;
                while (resultSet.next())
                {
                    int client_id = resultSet.getInt("id");
                    id = client_id;
                }
                UpdateQuery("DELETE FROM cars where id="+number+";");
                UpdateQuery("DELETE FROM carinfo where id="+number+";");
                UpdateQuery("INSERT sells(service_id,client_id,summ) values(1,"+id+","+stoimost+");");
            }
            else
            {
                ResultSet resultSet = statement.executeQuery("SELECT id from clients where phone="+phone_number+";");
                int id=0;
                while (resultSet.next())
                {
                    int client_id = resultSet.getInt("id");
                    id = client_id;
                }
                UpdateQuery("UPDATE cars set available=available-1 where id="+number+";");
                UpdateQuery("INSERT sells(service_id,client_id,summ) values(1,"+id+","+stoimost+");");
            }
        }
    }
    public void OptionNum2() throws SQLException
    {
        System.out.println("Выберите необходимый вид:\n1 - ОСАГО\n2 - КАСКО\n3 - ДСАГО");
        String choice = input.next();
        if(choice.equals("1"))
        {
            ExQueryForInsurance("SELECT duration,price from services where id=2;");
            System.out.println("Вы уверены?(да/нет)");
            String insChoice = input.next().toLowerCase(Locale.ROOT);
            if(insChoice.equals("да"))
            {
                ResultSet resultSet = statement.executeQuery("SELECT id from clients where phone="+phone_number+";");
                int id=0;
                while (resultSet.next())
                {
                    int client_id = resultSet.getInt("id");
                    id = client_id;
                }
                UpdateQuery("INSERT sells(service_id,client_id,summ) values(2,"+id+","+summ+");");
            }
        }
        else if (choice.equals("2"))
        {
            ExQueryForInsurance("SELECT duration,price from services where id=3;");
            System.out.println("Вы уверены?(да/нет)");
            String insChoice = input.next().toLowerCase(Locale.ROOT);
            if(insChoice.equals("да"))
            {
                ResultSet resultSet = statement.executeQuery("SELECT id from clients where phone="+phone_number+";");
                int id=0;
                while (resultSet.next())
                {
                    int client_id = resultSet.getInt("id");
                    id = client_id;
                }
                UpdateQuery("INSERT sells(service_id,client_id,summ) values(3,"+id+","+summ+");");
            }
        }
        else if (choice.equals("3"))
        {
            ExQueryForInsurance("SELECT duration,price from services where id=4;");
            System.out.println("Вы уверены?(да/нет)");
            String insChoice = input.next().toLowerCase(Locale.ROOT);
            if(insChoice.equals("да"))
            {
                ResultSet resultSet = statement.executeQuery("SELECT id from clients where phone="+phone_number+";");
                int id=0;
                while (resultSet.next())
                {
                    int client_id = resultSet.getInt("id");
                    id = client_id;
                }
                UpdateQuery("INSERT sells(service_id,client_id,summ) values(4,"+id+","+summ+");");
            }
        }
    }
    public void OptionNum3() throws SQLException
    {
        System.out.println("Какая конкретная услуга вас интересует?\n1 - Проф.осмотр\n2 - Покраска\n3 - Замена масла\n4 - Замена деталей");
        int number = input.nextInt()+4;
        ExQueryForInsurance("SELECT duration,price from services where id="+number+";");
        System.out.println("Вы уверены?(да/нет)");
        String insChoice = input.next().toLowerCase(Locale.ROOT);
        if(insChoice.equals("да"))
        {
            ResultSet resultSet = statement.executeQuery("SELECT id from clients where phone="+phone_number+";");
            int id=0;
            while (resultSet.next())
            {
                int client_id = resultSet.getInt("id");
                id = client_id;
            }
            UpdateQuery("INSERT sells(service_id,client_id,summ) values("+number+","+id+","+summ+");");
        }
    }
    public void OptionNum4() throws SQLException
    {
        ResultSet resultSet = statement.executeQuery("SELECT id from clients where phone="+phone_number+";");
        int id=0;
        while (resultSet.next())
        {
            int client_id = resultSet.getInt("id");
            id = client_id;
        }
        ExecuteQueryForClients("SELECT * FROM sells join services on sells.service_id=services.id where client_id="+id+";");
    }
}
