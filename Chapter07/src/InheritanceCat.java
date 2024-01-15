public class InheritanceCat 
{
    private String name;
    private int age;
    private boolean hungry;

    public InheritanceCat(String catName, int catAge, boolean isHungry)
    {
        name = catName;
        age = catAge;
        hungry = isHungry;
    }

    public void changeHunger()
    {
        hungry = !hungry;
        System.out.println("I ate some yummy food!");
    }




}