package addcninblue.mancala;

import addcninblue.mancala.model.State;
import java.util.Arrays;
import java.util.Observable;

/**
 * Created by addison on 5/1/17.
 */
public class Game extends Observable {
    private Player[] players; // only going to have 2 players max
    private int playerTurn;
    private Board board;

    private State state;
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
        playerTurn = 0;
        board = new Board(2, 7, 4);
    }

    public void start() {
        if (Arrays.toString(players).contains("null")) {
            throw new IllegalStateException("There are not enough players to begin!");
        }
        state = State.LISTENING;
    }

    public void commenceTurn(int slotId) {
        if (state != State.LISTENING) {
            throw new IllegalStateException("Game has not started!");
        }
        if (!checkValidMove(slotId)) return;
        playerTurn += board.move(slotId, playerTurn % 2, false) ? 1 : 0;
    }



    public void addPlayer(Player player) {
        for (int i = 0; i < players.length; i++) {
            if (players[i] == null) {
                players[i] = player;
                break;
            }
        }
    }

    public boolean checkValidMove(int slotId) {
        return board.checkValidMove(slotId);
    }

    public Player getWinner() {
        if (board.getWinnerId() == -1) return null;

        state = State.STOPPED;
        return players[board.getWinnerId()];
    }

    public Player getActivePlayer() {
        return players[playerTurn % 2];
    }

    public Board getBoard() {
        return board;
    }

    public State getState() {
        return state;
    }
}
