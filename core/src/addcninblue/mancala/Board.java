package addcninblue.mancala;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by addison on 5/6/17.
 */
public class Board {
    int[][] board;
    int rows;
    int cols;
    int stones;

    ArrayList<int[][]> history;
    ArrayList<Boolean> continues;

    public Board(int rows, int cols, int stones){
        this.board = new int[rows][cols];
        for(int[] row : board){
            for(int i = 0; i < row.length - 1; i++){
                row[i] = stones;
            }
        }
        this.rows = rows;
        this.cols = cols;
        this.stones = stones;
        this.history = new ArrayList<int[][]>(); //changed diamond operators for compatibility
        this.continues = new ArrayList<Boolean>();
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
            if(row != initialRow && position == cols - 1) // if ends up in enemy home
                position++;
            row = (row + position / cols) % 2;
            position %= cols;
            board[row][position]++;
            stones--;
        }
        if(row == initialRow){
            if(position == 6) { // if ends up in own space
                return false;
            } else if(board[row][position] > 1){
                if (saveState) {
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

    public boolean checkValidMove(int slotId) {
        return (slotId > 0 && slotId < board[0].length);
    }

    public boolean checkWin(){
        return(board[0][cols - 1] + board[1][cols - 1] == stones * (cols - 1) * 2);
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

    public int getWinnerId(){
        if (!checkWin()) return -1;

        return (board[0][cols - 1] > board[1][cols - 1]) ? 0 : 1;
    }
}
