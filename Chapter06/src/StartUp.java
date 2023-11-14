import java.util.*;

public class StartUp 
{
    private ArrayList<String> locationCells;
    private int numOfHits = 0;
    
    public String checkYourself(int guess)
    {
        String result = "miss";
        int index = locationCells.indexOf(guess);

        if (index >= 0)
        {
            locationCells.remove(index);

            if (locationCells.isEmpty())
            {
                result = "kill";
            }
            else
            {
                result = "hit";
            }
        }
        return result;
    }
    
    // Afterwards, what am I doing with this setter?
    // Is this something I could do inside the class 
    // intead of accessing a private variable?
    public void setLocationCells(ArrayList<String> loc)
    {
        locationCells = loc;
    }
    
}