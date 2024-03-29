import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class GameMap
{
    CopyOnWriteArrayList<CopyOnWriteArrayList<Animal>> map;
    Integer turn;

    public GameMap()
    {
        map = newMap();
        turn = 0;
    }

    private static CopyOnWriteArrayList<CopyOnWriteArrayList<Animal>> newMap()
    {
        CopyOnWriteArrayList<CopyOnWriteArrayList<Animal>> map = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 100; i++)
        {
            map.add(new CopyOnWriteArrayList<>());
        }
        return map;
    }


    public void addAnimal(AnimalType animalType)
    {
        Random rand = new Random();
        this.map.get(rand.nextInt(100)).add(new Animal(animalType));
    }

    public void addAnimal(Animal animal)
    {
        Random rand = new Random();
        this.map.get(rand.nextInt(100)).add(animal);
    }

    public void shuffle()
    {
        ArrayList<Animal> farm = this.map.stream().flatMap(List::stream).collect(Collectors.toCollection(ArrayList::new));
        this.map = newMap();
        farm.forEach(this::addAnimal);
    }

    public void eat()
    {
        this.map.forEach(
            animals -> {
                AtomicInteger wolfCount = new AtomicInteger();
                animals.forEach(animal -> {
                    if (animal.animalType==AnimalType.WOLF)
                        wolfCount.getAndIncrement();
                });
                if (!wolfCount.compareAndSet(0, 0) && (animals.size() - wolfCount.get() > 0))
                {
                    animals.clear();
                    for (int i = 0; i < wolfCount.get(); i++)
                    {
                        animals.add(new Animal(AnimalType.WOLF));
                    }
                }
            }
        );
    }

    public void reproduce()
    {
        this.map.forEach(
                animals -> {
                    int size = animals.size();
                    for (int i = 0; i < size/2; i++)
                    {
                        animals.add(new Animal(animals.get(0).animalType));
                    }
                }
        );
    }

    public void starvate()
    {
        this.map.forEach(
                animals -> animals.forEach(
                        animal -> {
                            if (animal.getAge() > 5 && animal.animalType.equals(AnimalType.WOLF))
                                animals.remove(animal);
                        }
                )
        );
    }

    long getCount(AnimalType type)
    {
        int count = 0;

        for(CopyOnWriteArrayList<Animal> animals : this.map)
        {
            for(Animal animal : animals)
            {
                if (animal.animalType.equals(type))
                    count++;
            }
        }
        return count;
    }


    @Override
    public String toString() {
        var toDisplay =  new StringBuilder();
        for (int i = 0; i < 100; i++)
        {
            if (i%10==0)
                toDisplay.append("\n");

            if (!this.map.get(i).isEmpty())
            {
                String color = this.map.get(i).get(0).animalType == AnimalType.RABBIT ? Color.ANSI_GREEN : Color.ANSI_RED;
                this.map.get(i).forEach(animal ->
                        toDisplay.append(color)
                                .append(animal.getShortenedName()));

                toDisplay.append(Color.ANSI_RESET).append(" ");
            }
            else
                toDisplay.append("0 ");
        }
        toDisplay.append("\n Turn: ").append(this.turn).append(" :  ").append("Rabbits: ").append(getCount(AnimalType.RABBIT)).append(" :  Wolves: ").append(getCount(AnimalType.WOLF)).append("\n\n\n");

        return toDisplay.toString();
    }
}
