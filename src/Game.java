import java.util.ArrayList;

public class Game
{


    public static void startGame(int sRabbits, int sWolves, int turns)
    {
        GameMap gameMap = new GameMap();

        if(sRabbits+sWolves>100)
        {
            System.out.println("There may be a 100 animals maximum");
            return;
        }



        for (int i = 0; i < sRabbits; i++)
        {
            gameMap.addAnimal(AnimalType.RABBIT);
        }
        for (int i = 0; i < sWolves; i++)
        {
            gameMap.addAnimal(AnimalType.WOLF);
        }


        for (int i = 0; i < turns; i++)
        {
            /*try
            {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }*/
            nextTurn(gameMap);
            gameMap.display();
            System.out.println("\n Turn: " + i + " :  " + "Rabbits: " + getCount(AnimalType.RABBIT, gameMap) + " :  Wolves: " + getCount(AnimalType.WOLF, gameMap) + "\n\n\n");

            if (endGame(gameMap))
                break;
        }

    }

    private static void nextTurn(GameMap gameMap)
    {
        gameMap.shuffle();
        gameMap.map.forEach(
                animals -> animals.forEach(
                        Animal::increaseAge));
        gameMap.dieAfterNotEating();
        gameMap.eat();
        gameMap.reproduce();

    }


    private static long getCount(AnimalType type, GameMap gameMap)
    {
        int count = 0;

        for(ArrayList<Animal> animals : gameMap.map)
        {
            for(Animal animal : animals)
            {
                if (animal.animalType.equals(type))
                    count++;
            }
        }
        return count;
    }


    private static boolean endGame(GameMap gameMap)
    {
        long rabbitCount = getCount(AnimalType.RABBIT, gameMap);

        return rabbitCount == 0 || rabbitCount > 500 || getCount(AnimalType.WOLF, gameMap) == 0;
    }




}
