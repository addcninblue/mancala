import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by addison on 5/1/17.
 */
public class Game {
    private Player[] players; // only going to have 2 players max
    private boolean win;
    private Board board;
    // row 0: 6 is "home", 0 - 5 is pits
    // row 1: 6 is "home", 0 - 5 is pits
    // layout:
    // 6 5 4 3 2 1 0
    //   0 1 2 3 4 5 6

    public Game(){
        this(null, null);
    }

    public Game(Player p1, Player p2){
        players = new Player[]{p1, p2};
        this.win = false;
        this.board = new Board(2, 7, 4);
    }

    public void start(){
        int playerTurn = 0;
        printBoard();
        System.out.println();
        while(!this.win){
            boolean turnEnded = false;
            while(!turnEnded) {
                int move = players[playerTurn % 2].getMove();
                turnEnded = board.move(move, playerTurn % 2, false);
                printBoard();
            }
            playerTurn++;
            this.win = board.checkWin();
        }
        System.out.format("%s won!", players[board.getWinner()].getFirstName());
    }

    public void addPlayer(Player player){
        for(int i = 0; i < players.length; i++){
            if(players[i] == null){
                players[i] = player;
                break;
            }
        }
    }

    public void printBoard(){
        // I apologize for this ugliness
        // Built with Vim and a lot of patience
        int[] row0 = board.getRow(0);
        int[] row1 = board.getRow(1);
        String str = "     5  4  3  2  1  0\n┌──┬──┬──┬──┬──┬──┬──┬──┐\n│  │%2d│%2d│%2d│%2d│%2d│%2d│  │\n│%2d├──┼──┼──┼──┼──┼──┤%2d│\n│  │%2d│%2d│%2d│%2d│%2d│%2d│  │\n└──┴──┴──┴──┴──┴──┴──┴──┘\n     0  1  2  3  4  5\n";
        System.out.format(str, row0[5], row0[4], row0[3], row0[2], row0[1], row0[0], row0[6],
                row1[6], row1[0], row1[1], row1[2], row1[3], row1[4], row1[5]);
    }

    public Board getBoard(){
        return board;
    }

    public static void main(String[] args) {
        Game game = new Game();
        Scanner in = new Scanner(System.in);
        System.out.println("1. Play against another person.\n2. Play against Computer");
        int choice = 0;
        while(true){
            try {
                choice = in.nextInt();
                if(choice > 2 || choice < 1)
                    throw new Exception("Not a valid number.");
                break;
            } catch(Exception e){
                System.out.println("Sorry, that wasn't a valid choice.");
            }
        }
        if(choice == 1) {
            game.addPlayer(new Human());
            game.addPlayer(new Human());
        } else {
            System.out.println("Would you like to be player one or player two?");
            while(true){
                try {
                    choice = in.nextInt();
                    if(choice > 2 || choice < 1)
                        throw new Exception("Not a valid number.");
                    break;
                } catch(Exception e){
                    System.out.println("Sorry, that wasn't a valid choice.");
                }
            }
            if(choice == 1) {
                game.addPlayer(new Human());
                game.addPlayer(new AI(game.getBoard(), 1));
            } else {
                game.addPlayer(new AI(game.getBoard(), 0));
                game.addPlayer(new Human());

            }
        }
        game.start();
    }
}
