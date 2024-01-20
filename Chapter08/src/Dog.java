public class Dog extends Canine implements Pet
{
    public void beFriendly()
    {
        System.out.println("I'm licking your face");
    }
    public void play()
    {
        System.out.println("I can't fetch, chase me!");
    }

    public void roam()
    {
        System.out.println("I'm on patrol now");
    }
    public void eat()
    {
        System.out.println("Nom Nom");
    }
}