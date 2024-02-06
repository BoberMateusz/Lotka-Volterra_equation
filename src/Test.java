import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<Integer> results = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            results.add(0);
        }

        for (int i = 0; i < 1; i++) {
            int gameResult = Game.startGame(50, 50, 100,
                    0, false, true, 1000);
            results.set(gameResult, results.get(gameResult)+1);
        }

        System.out.println("Rabbit population exceeded 1000." + " " + results.get(1) + "\n" +
                "There are no rabbits left." + " " + results.get(2) + "\n" +
                "All wolfs have starvated" + " " + results.get(3) + "\n" +
                "Desired amount of turns has elapsed" + " " + results.get(4) + "\n" +
                "Something unexpected occurred" + " " + results.get(0));

    }
}
