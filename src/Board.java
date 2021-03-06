import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by addison on 5/6/17.
 */
public class Board {
    int[][] board;
    int numRows;
    int numCols;
    int numStones;

    ArrayList<int[][]> history;
    ArrayList<Boolean> continues;

    public Board(int numRows, int numCols, int numStones){
        this.board = new int[numRows][numCols];
        for(int[] row : board){
            for(int i = 0; i < row.length - 1; i++){
                row[i] = numStones;
            }
        }
        this.numRows = numRows;
        this.numCols = numCols;
        this.numStones = numStones;
        this.history = new ArrayList<>();
        this.continues = new ArrayList<>();
    }

    public boolean move(int position, int row, boolean saveState){
        return move(position, row, row, saveState);
    }

    /**
     * Moves given the position and row
     * @param position position of pit
     * @param row playerNumber
     * @return whether game is over or not
     */
    public boolean move(int position, int row, int initialRow, boolean saveState){
        int stones = board[row][position];
        board[row][position] = 0;
        while(stones > 0){
            position++;
            if(row != initialRow && position == numCols - 1) // if ends up in enemy home
                position++;
            row = (row + position / numCols) % 2;
            position %= numCols;
            board[row][position]++;
            stones--;
        }
        if(row == initialRow){
            if(position == 6) { // if ends up in own space
                return false;
            } else if(board[row][position] > 1){
                if(saveState){
                    saveState(false);
                    this.continues.add(0, true);
                }
                return move(position, row, initialRow, saveState);
            }
        }
        return true;
    }

    public void saveState(boolean parent){
        if(parent)
            this.continues.add(0, false);
        int[][] boardCopy = new int[][]{Arrays.copyOf(this.board[0], this.board[0].length),
                                        Arrays.copyOf(this.board[1], this.board[1].length),};
        this.history.add(0, boardCopy);
    }

    public void undoState(){
        boolean undo = true;
        while(undo) {
            this.board = this.history.remove(0);
            undo = this.continues.remove(0);
        }
    }

    public boolean checkWin(){
        return(board[0][numCols - 1] + board[1][numCols - 1] == numStones * (numCols - 1) * 2);
    }

    public int[] getRow(int row){
        return this.board[row];
    }

    public int[][] getBoard(){
        return this.board;
    }

    private int numOfPieces(){
        int sum = 0;
        for(int[] row : board){
            for(int i : row){
                sum += i;
            }
        }
        return sum;
    }

    public void printBoard(){
        // I apologize for this ugliness
        // Built with Vim and a lot of patience
        int[] row0 = board[0];
        int[] row1 = board[1];
        String str = "     5  4  3  2  1  0\n┌──┬──┬──┬──┬──┬──┬──┬──┐\n│  │%2d│%2d│%2d│%2d│%2d│%2d│  │\n│%2d├──┼──┼──┼──┼──┼──┤%2d│\n│  │%2d│%2d│%2d│%2d│%2d│%2d│  │\n└──┴──┴──┴──┴──┴──┴──┴──┘\n     0  1  2  3  4  5\n";
        System.out.format(str, row0[5], row0[4], row0[3], row0[2], row0[1], row0[0], row0[6],
                row1[6], row1[0], row1[1], row1[2], row1[3], row1[4], row1[5]);
    }

    public int getWinner(){
        if(checkWin())
            return (board[0][numCols - 1] > board[1][numCols - 1]) ? 0 : 1;
        return -1;
    }

}
