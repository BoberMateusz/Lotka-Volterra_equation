import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class GameMap
{
    ArrayList<ArrayList<Animal>> map;

    public GameMap()
    {
        map = newMap();
    }

    private static ArrayList<ArrayList<Animal>> newMap()
    {
        ArrayList<ArrayList<Animal>> map = new ArrayList<>();
        for (int i = 0; i < 100; i++)
        {
            map.add(new ArrayList<>());
        }
        return map;
    }


    public void addAnimal(AnimalType animalType)
    {
        Random rand = new Random();
        this.map.get(rand.nextInt(100)).add(new Animal(animalType));
    }

    public void shuffle()
    {
        ArrayList<Animal> farm = this.map.stream().flatMap(List::stream).collect(Collectors.toCollection(ArrayList::new));
        this.map = newMap();
        farm.forEach(animal -> this.addAnimal(animal.animalType));
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
                if (!wolfCount.compareAndSet(0, 0))
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

    public void dieOfAge()
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




    public void display()
    {
        for (int i = 0; i < 100; i++)
        {
            if (i%10==0)
                System.out.println();

            if (!this.map.get(i).isEmpty())
            {
                this.map.get(i).forEach(animal -> System.out.print(animal.getShortenedName()));
                System.out.print(" ");
            }

            else
                System.out.print("0 ");


        }
    }
}
