package addcninblue.mancala.controller;

import addcninblue.mancala.AI;
import addcninblue.mancala.Game;
import addcninblue.mancala.Human;
import addcninblue.mancala.Player;
import addcninblue.mancala.model.State;
import addcninblue.mancala.view.View;

/**
 *
 * @author Darian
 */
public class CommandLineController implements Controller {
    private Game game;
    private View view;

    public CommandLineController(Game game, View view) {
        this.game = game;
        this.view = view;
    }

    @Override
    public void startGame() {
        addPlayers();
        game.start();

        view.displayBoard(game.getBoard());

        while (game.getState() == State.LISTENING) {
            Player currentPlayer = game.getActivePlayer();
            while (currentPlayer == game.getActivePlayer()) {
                game.commenceTurn(processMove(currentPlayer));
                view.displayBoard(game.getBoard());
            }
        }

        view.displayWinner(game.getWinner());
    }

    private void addPlayers() {
        for (int i = 1; i <= 2; i++) {
            String playerTypePrompt = String.format("Enter player type of Player %d:\n1. Human\n2. AI\n>", i);
            int playerType = view.promptInt(playerTypePrompt);
            Player player;
            switch (playerType) {
                case 1:
                    System.out.println("ASDF");
                    player = new Human(view.promptString("Name: "));
                    break;
                case 2:
                    player = new AI(game.getBoard(), i-1);
                    break;
                default:
                    view.displayError("Invalid type of player");
                    i--;
                    continue;
            }
            game.addPlayer(player);
        }
    }

    private int processMove(Player player) {
        if (player instanceof Human) {
            int moveId = view.promptInt("Move a slot: ");
            if (!game.checkValidMove(moveId)) {
                view.displayError("Invalid move");
            }
            return moveId;
        } else if (player instanceof AI) {
            return ((AI)player).calculateMove();
        } else {
            throw new IllegalArgumentException("Player is invalid");
        }
    }
}
