package addcninblue.mancala.controller;

import addcninblue.mancala.Game;
import addcninblue.mancala.view.View;

/**
 *
 * @author Darian
 */
public class CommandLineController implements Controller {
    private Game game;
    private View view;

    @Override
    public void startGame() {
        game.start();
        view.displayBoard(game.getBoard());

        while (!game.isWon()) {
            game.commenceTurn();
        }

        players[board.getWinner()].getName()

    }

}
