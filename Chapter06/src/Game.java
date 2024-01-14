import java.util.*;

public class Game 
{
    private GameHelper helper = new GameHelper();
    private ArrayList<StartUp> startups = new ArrayList<StartUp>();
    private int numOfGuesses = 0;
    
    private void setUpGame()
    {
        StartUp one = new StartUp();
        one.setName("poniez");
        StartUp two = new StartUp();
        two.setName("hacqi");
        StartUp three = new StartUp();
        three.setName("cabista");
        startups.add(one);
        startups.add(two);
        startups.add(three);

        System.out.println("Hello user! Your goal is to sink all of the Startups: poniez, hacqi, and cabista.");
        System.out.println("Try to sink all three in the least number of guesses");

        for (StartUp startup : startups)
        {
            ArrayList<String> newLocation = helper.placeStartup(3);
            startup.setLocationCells(newLocation);
        }
    }

    private void startPlaying()
    {
        while(!startups.isEmpty())
        {
            String userGuess = helper.getUserInput("Enter a guess");
            checkUserGuess(userGuess);
        }
        finishGame();
    }

    private void checkUserGuess(String userGuess)
    {
        numOfGuesses++;
        String result = "miss";

        for(StartUp startupToTest : startups)
        {
            result = startupToTest.checkYourself(userGuess);

            if(result.equals("hit"))
            {
                break;
            }
            if(result.equals("kill"))
            {
                startups.remove(startupToTest);
                break;
            }
        }

        System.out.println(result);
    }

    private void finishGame()
    {
        System.out.println("All startups are dead! Your stock is now worthless");
        if (numOfGuesses <= 18)
        {
            System.out.println("It only took you " + numOfGuesses + " guesses.");
            System.out.println("You got out before your options sank.");
        }
        else
        {
            System.out.println("Took you long enough. " + numOfGuesses + " guesses.");
            System.out.println("Fish are dancing with your options");
        }
    }

    public static void main(String[] args)
    {
        Game game = new Game();
        game.setUpGame();
        game.startPlaying();
    }
}