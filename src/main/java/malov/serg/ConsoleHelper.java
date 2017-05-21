package malov.serg;



import malov.serg.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class ConsoleHelper {

    private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message){

        System.out.println(message);
    }

    public static String readString() throws IOException  {
        String s = "";

        try
        {
            return s = bufferedReader.readLine();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return s;
    }

    public static List<Dish> getAllDishesForOrder() throws IOException{
        writeMessage(Dish.allDishesToString());
        List<Dish> list = new ArrayList<>();
        writeMessage("Сделайте Ваш заказ. Введите название блюда: (по завершению заказа введите \"exit\")");
        String select = readString();

        while (!select.equals("exit")){

            try {

                list.add(Dish.valueOf(select));
            }catch (IllegalArgumentException e){
                writeMessage("Wrong choise!");

            }
            writeMessage("Select dish");
            select = readString();

        }
        return list;
    }
}
