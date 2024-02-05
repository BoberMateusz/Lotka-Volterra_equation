import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class Game
{


    public static void startGame(int sRabbits, int sWolves, int turns) {
        JTextArea textArea = Display.initiateDisplay();

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


        while (gameMap.turn <= turns)
        {
            try
            {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
            nextTurn(gameMap);
            System.out.println(gameMap);
            textArea.setText(gameMap.toString());


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


    private static boolean endGame(GameMap gameMap)
    {
        long rabbitCount = gameMap.getCount(AnimalType.RABBIT);

        return rabbitCount == 0 || rabbitCount > 500 || gameMap.getCount(AnimalType.WOLF) == 0;
    }




}
