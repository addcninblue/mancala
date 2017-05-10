package addcninblue.mancala.desktop;

import addcninblue.mancala.Game;
import addcninblue.mancala.controller.CommandLineController;
import addcninblue.mancala.controller.Controller;
import addcninblue.mancala.view.CommandLineInterface;
import addcninblue.mancala.view.View;

/**
 *
 * @author Darian
 */
public class MancalaTester {
    public static void main(String[] args) {
        Game model = new Game();
        View view = new CommandLineInterface();
        Controller controller = new CommandLineController(model, view);

        controller.startGame();
    }
}
