import java.util.Random;

public class Animal
{
    private int age;
    AnimalType animalType;

    public int getAge()
    {
        return age;
    }

    public Animal(AnimalType animalType)
    {
        Random rand = new Random();
        this.age = 0;
        this.animalType = animalType;
    }

    public void increaseAge()
    {
        this.age++;
    }

    public Character getShortenedName()
    {
        return this.animalType.name().charAt(0);
    }


}
