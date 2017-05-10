package addcninblue.mancala.view;

import addcninblue.mancala.Board;
import addcninblue.mancala.Player;
import java.util.Scanner;

/**
 *
 * @author Darian
 */
public class CommandLineInterface implements View {
    private final Scanner input;

    public CommandLineInterface() {
        input = new Scanner(System.in);
    }

    @Override
    public String promptString(String prompt) {
        System.out.print(prompt);
        return input.nextLine();
    }

    @Override
    public int promptInt(String prompt) {
        try {
            return Integer.parseInt(promptString(prompt));
        } catch (NumberFormatException e) {
            System.out.println("Invalid input");
            return promptInt(prompt);
        }
    }

    @Override
    public void displayError(String errorMsg) {
        System.out.printf("ERROR: %s\n", errorMsg);
    }

    @Override
    public void displayBoard(Board board) {
        // I apologize for this ugliness
        // Built with Vim and a lot of patience
        int[] row0 = board.getRow(0);
        int[] row1 = board.getRow(1);
        String str = "     5  4  3  2  1  0\n┌──┬──┬──┬──┬──┬──┬──┬──┐\n│  "
                                          + "│%2d│%2d│%2d│%2d│%2d│%2d│  "
                                      + "│\n│%2d├──┼──┼──┼──┼──┼──┤%2d│\n│  "
                                          + "│%2d│%2d│%2d│%2d│%2d│%2d│  "
                                      + "│\n└──┴──┴──┴──┴──┴──┴──┴──┘\n     0  1  2  3  4  5\n";
        System.out.format(str, row0[5], row0[4], row0[3], row0[2], row0[1], row0[0], row0[6],
                row1[6], row1[0], row1[1], row1[2], row1[3], row1[4], row1[5]);
    }

    @Override
    public void displayWinner(Player winner) {
        System.out.format("%s won!", winner.getName());
    }
}
