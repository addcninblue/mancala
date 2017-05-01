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
        System.out.format("%s's move: ", this.firstName);
        int choice = in.nextInt();
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
