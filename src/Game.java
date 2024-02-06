import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class Game
{
    public static void startGame(int startingRabbits, int startingWolves, int timeInterval)
    {
        startGame(startingRabbits, startingWolves, 200, timeInterval,
                true, true, 1000);
    }
    public static int startGame(int sRabbits, int sWolves, int turns,
                                int timeIntervals, boolean display, boolean text, int maxRabbits) {
        JTextArea textArea = new JTextArea();
        if (display)
            textArea = Display.initiateDisplay();

        GameMap gameMap = new GameMap();

        if(sRabbits+sWolves>100)
        {
            System.out.println("There may be a 100 animals maximum");
            return 0;
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
            if(timeIntervals != 0){
                try
                {
                    TimeUnit.SECONDS.sleep(timeIntervals);
                } catch (InterruptedException e)
                {
                    throw new RuntimeException(e);
                }}
            nextTurn(gameMap);
            if(text)
                System.out.println(gameMap);
            if (display)
                textArea.setText(gameMap.toString());

            int result;
            if ((result = endGame(gameMap, maxRabbits)) != 0) {
                if (display)
                    Display.endMessage(result, textArea);
                return result;
            }
        }
        if (display)
            Display.endMessage(4, textArea);
        return 4;
    }

    private static void nextTurn(GameMap gameMap)
    {
        gameMap.shuffle();
        gameMap.map.forEach(
                animals -> animals.forEach(
                        Animal::increaseAge
                )
        );
        gameMap.dieAfterNotEating();
        gameMap.eat();
        gameMap.reproduce();
        gameMap.turn++;

    }


    private static int endGame(GameMap gameMap, int maxRabbits)
    {
        long rabbitCount = gameMap.getCount(AnimalType.RABBIT);

        if(rabbitCount>maxRabbits)
            return 1;
        if(rabbitCount==0)
            return 2;
        if(gameMap.getCount(AnimalType.WOLF) == 0)
            return 3;

        return 0;
    }




}
