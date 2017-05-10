package addcninblue.mancala.view;

import addcninblue.mancala.Board;
import addcninblue.mancala.Player;

/**
 *
 * @author Darian
 */
public interface View {
    String promptString(String prompt);
    int promptInt(String prompt);
    void displayError(String errorMsg);
    void displayBoard(Board board);
    void displayWinner(Player winner);
}
