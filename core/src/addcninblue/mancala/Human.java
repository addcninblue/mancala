package addcninblue.mancala;

import java.util.Scanner;

/**
 * Created by addison on 5/1/17.
 */
public class Human implements Player{
    private String name;

    public Human(String name) {
        this.name = name;
    }

    @Override
    public int getMove(){
        return -1;
    }

    @Override
    public String getName(){
        return name;
    }
}
