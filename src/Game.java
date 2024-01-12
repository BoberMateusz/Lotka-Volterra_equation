import java.util.ArrayList;
import java.util.Random;

public class Game
{
    static int rabbitCount;
    static int wolfCount;
    static ArrayList<ArrayList<Animal>> map = new ArrayList<>(100);


    private static void createMap()
    {
        for (int i = 0; i < 100; i++)
        {
            map.add(new ArrayList<>());
        }
    }
    private static void fillWithAnimal(AnimalType animalType)
    {
        Random rand = new Random();
        map.get(rand.nextInt(100)).add(new Animal(animalType));
    }


    public static void startGame(int sRabbits, int sWolves)
    {
        startGame(sRabbits, sWolves, 0.4, 0.8, 1);
    }

    public static void startGame(int sRabbits, int sWolves, double rabbitMF, double wolfMF, double speed)
    {
        if(sRabbits+sWolves>100)
        {
            System.out.println("There may be a 100 animals maximum");
            return;
        }

        createMap();

        for (int i = 0; i < sRabbits; i++)
        {
            fillWithAnimal(AnimalType.RABBIT);
        }
        for (int i = 0; i < sWolves; i++)
        {
            fillWithAnimal(AnimalType.WOLF);
        }


    }

    private static void nextTurn()
    {
        ArrayList<ArrayList<Animal>> tMap = new ArrayList<>(100);

        map.forEach(
                animals -> animals.forEach(
                        animal -> {
                            animal.increaseAge();

                        }
                )
        );
    }



    public static void displayScreen()
    {
        for (int i = 0; i < 100; i++)
        {
            if (i%10==0)
                System.out.println();

            if (!map.get(i).isEmpty())
                map.get(i).forEach(animal -> System.out.print(animal.getShortenedName() + " "));
            else
                System.out.print("0 ");


        }
    }
}
