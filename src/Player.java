import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by addison on 5/1/17.
 */
public class Player {
    private String firstName;
    private String lastName;
    private Scanner in;

    public Player(){
        in = new Scanner(System.in);
        this.firstName = "Player";
        this.lastName = "";
    }

    public Player(String firstName, String lastName){
        in = new Scanner(System.in);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getMove(){
        int choice = -1;
        while(true) {
            try {
                System.out.format("%s's move: ", this.firstName);
                choice = in.nextInt();
                if(choice < 0 || choice > 5)
                    throw new Exception("out of bounds");
                break;
            } catch (Exception e) {
                System.out.println("That was not a valid input.");
                in.nextLine();
                choice = -1;
            }
        }
        return choice;
    }

    public String getName(){
        return String.format("%s %s", firstName, lastName);
    }

    public String getFirstName(){
        return this.firstName;
    }

    public String getLastName(){
        return this.lastName;
    }
}
