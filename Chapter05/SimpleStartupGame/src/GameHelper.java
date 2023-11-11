import java.util.*; 
public class GameHelper {
    public int getUserInput(String prompt){
       System.out.print(prompt + ": ");
       Scanner scanner = new Scanner(System.in);
       return scanner.nextInt();
    }
}