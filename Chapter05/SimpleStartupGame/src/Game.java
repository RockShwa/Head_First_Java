import java.util.*;

// OKAY HOMIE: we got this issue, you enter a correct
// cell multiple times, and it ocunts it as a kill. I
// need to find a way to check and make sure this is
// a unique guess... but how do I do that?
// Could make a new array that hold previous guesses
// and then iterate through that each time and check
// if the new guess is unique or not...

public class Game 
{
    public static void main(String[] args) 
    {
        // Scanner scan = new Scanner(System.in);
        boolean isAlive = true;
        int numOfGuesses = 0;
        GameHelper helper = new GameHelper();
        
        SimpleStartUp dot = new SimpleStartUp();
        
        // Compute a random num between 0 and
        // 4 that will be the starting cell position
        int startCell = (int) (Math.random() * 5);
        
        int[] dotLoc = {startCell, startCell + 1, startCell + 2};
        
        // set object location to this array with cell locs
        dot.setLocationCells(dotLoc);
        
        while (isAlive)
        {
            // System.out.println("Enter a guess: ");
            // String userGuess = scan.nextLine();
            int userGuess = helper.getUserInput("Enter a number");
            
            String result = dot.checkYourself(userGuess);
            numOfGuesses ++;
            
            if (result.equals("kill"))
            {
                isAlive = false;
                System.out.println("You took " + numOfGuesses + " guesses.");
            }
        }
    } 
}