package addcninblue.mancala.view;

import addcninblue.mancala.Board;
import addcninblue.mancala.Player;

/**
 *
 * @author Darian
 */
public interface View {
    void startGame();
    void displayBoard(Board board);
    void displayWinner(Player winner);
}
