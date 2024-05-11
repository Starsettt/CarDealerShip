public class Main
{
    public static void main(String[] args)
    {
        try
        {
            Options options = new Options(); // создание объекта класса Options, использующий пустой конструктор класса
            options.SalonOptions(); // вызов метода SalonOptions на объекте options
        }
        catch (Exception e)
        {
            System.out.println("Какая-то ошибка:" + e); // е - объект класса Exception, выводит в консоль сообщение с текстом исключения
        }
    }
}