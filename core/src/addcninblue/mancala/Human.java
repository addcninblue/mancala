package addcninblue.mancala;

/**
 * Created by addison on 5/1/17.
 */
public class Human implements Player{
    private String name;

    public Human(String name) {
        this.name = name;
    }

    @Override
    public int calculateMove(){
        return -1;
    }

    @Override
    public String getName(){
        return name;
    }
}
