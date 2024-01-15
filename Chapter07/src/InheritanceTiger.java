public class InheritanceTiger extends InheritanceCat
{
    private String roar;

    public InheritanceTiger(String tigerRoar, String catName, int catAge, boolean isHungry)
    {
        super(catName, catAge, isHungry);
        roar = tigerRoar;
    }

    public void roarLoud()
    {
        System.out.println(roar + " " + roar);
    }

}