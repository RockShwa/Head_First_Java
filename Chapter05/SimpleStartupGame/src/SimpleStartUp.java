public class SimpleStartUp 
{
    private int[] locationCells;
    private int numOfHits = 0;
    
    public String checkYourself(int guess)
    {

        String result = "miss";
        
        for (int cell : locationCells)
        {
            if (guess == cell)
            {
                result = "hit";
                numOfHits ++;
                break;
            }
            
        }
        
        if (numOfHits == locationCells.length)
            {
                result = "kill";
            }
        System.out.println(result);
        return result;
    }
    
    // Afterwards, what am I doing with this setter?
    // Is this something I could do inside the class 
    // intead of accessing a private variable?
    public void setLocationCells(int[] loc)
    {
        locationCells = loc;
    }
    
}