package addcninblue.mancala;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by addison on 5/1/17.
 */
public class Game {
    private Player[] players; // only going to have 2 players max
    private boolean win;
    private Board board;
    private Scanner input;
    // row 0: 6 is "home", 0 - 5 is pits
    // row 1: 6 is "home", 0 - 5 is pits
    // layout:
    // 6 5 4 3 2 1 0
    //   0 1 2 3 4 5 6

    public Game() {
        this(null, null);
    }

    public Game(Player p1, Player p2) {
        players = new Player[]{p1, p2};
        this.win = false;
        this.board = new Board(2, 7, 4);
        this.input = new Scanner(System.in);
    }

    public void start() {
        if (Arrays.toString(players).contains("null")) {
            throw new IllegalStateException("There are not enough players to begin!");
        }
        int playerTurn = 0;
        System.out.println();


    }

    public void commenceTurn() {
        boolean turnEnded = false;
        while (!turnEnded) {
            turnEnded = board.move(inputMove(players[playerTurn % 2]), playerTurn % 2, false);
            printBoard();
        }
        playerTurn++;
    }

    private int inputMove(Player player) {
        while (true) {
            try {
                System.out.format("%s's move: ", player.getName());
                int choice = input.nextInt();
                if(choice < 0 || choice > 5)
                    throw new Exception("out of bounds");
                return choice;
            } catch (Exception e) {
                System.out.println("That was not a valid input.");
                input.nextLine();
            }
        }
    }

    public void addPlayer(Player player) {
        for (int i = 0; i < players.length; i++) {
            if (players[i] == null) {
                players[i] = player;
                break;
            }
        }
    }
    public boolean isWon() {
        return board.checkWin();
    }

    public Board getBoard() {
        return board;
    }
}
