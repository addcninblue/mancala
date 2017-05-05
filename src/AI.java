import java.util.ArrayList;
import java.util.List;

/**
 * Created by addison on 5/1/17.
 */
public class AI implements Player {
    int[][] board;
    int playerNumber;
    int weight;
    int depth;
    ArrayList<Integer> stonesUsed;
    ArrayList<Boolean> chained; // when one pit ends with another one

    public AI(int[][] board, int playerNumber){
        this(board, playerNumber, 8, 5);
    }

    public AI(int[][] board, int playerNumber, int weight, int depth){
        this.board = board;
        this.playerNumber = playerNumber;
        this.weight = weight;
        this.depth = depth;
        this.stonesUsed = new ArrayList<>();
        this.chained = new ArrayList<>();
    }

    /**
     * Moves given the position and row
     * @param position of pit
     * @param row playerNumber
     * @return number of stones removed
     */
    public boolean simulateMove(int position, int row){
        int pitsEach = this.board[0].length - 1;
        int initialRow = row;
        int stones = board[row][position];
        this.stonesUsed.add(0, stones);
        board[row][position] = 0;
        while(stones > 0){
            position++;
            if(row != initialRow && position == pitsEach) // if ends up in enemy home
                position++;
            row = (row + position / (pitsEach + 1)) % 2;
            position %= pitsEach + 1;
            board[row][position]++;
            stones--;
        }
        if(row == initialRow){
            if(position == 6) { // if ends up in own space
                chained.add(0, false);
                return false;
            } else if(board[row][position] > 1){
                boolean result = simulateMove(position, row);
                chained.add(0, result);
                return result;
            }
        }
        chained.add(0, true);
        return true;
    }

    public void undoMoveNew(int position, int row){
        int numberOfStones = this.stonesUsed.remove(0);
        int pitsEach = this.board[0].length;
        while(numberOfStones > 0){
            System.out.println(row + " " + position);
            board[row][position]--;
            position--;
            if(row != playerNumber && position == pitsEach)
                position--;
            row = (row + position / pitsEach) % 2;
            position = (position + pitsEach) % pitsEach;
            numberOfStones--;
        }
        if(this.chained.remove(0)){
            undoMoveNew(position, row);
        }
    }

    /**
     * Moves given the position and row
     * @param position original position of pit
     * @param row playerNumber
     * @param numberOfStones number of stones originally there
     */
    public void undoMove(int position, int row, int numberOfStones){
        int pitsEach = this.board[0].length;
        int initialRow = row;
        int initialStones = numberOfStones;
        int initialPos = position;
        while(numberOfStones > 0){
            position++;
            if(row != initialRow && position == pitsEach) // if ends up in enemy home
                position++;
            row = (row + position / (pitsEach)) % 2;
            position %= pitsEach;
            board[row][position]--;
            numberOfStones--;
        }
        board[initialRow][initialPos] = initialStones;
    }

    /**
     * http://www3.ntu.edu.sg/home/ehchua/programming/java/javagame_tictactoe_ai.html
     * @param depth
     * @param maximize
     * @return
     */
    public int[] miniMax(int depth, boolean maximize){
        List<Integer> nextMoves = generateMoves(maximize ? playerNumber : (playerNumber + 1) % 2);

        int bestScore = maximize ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int currentScore;
        int best = -1;

        if(depth == 0 || nextMoves.isEmpty()){
            bestScore = evaluate();
        } else {
            for(int i : nextMoves){
                // do move
                boolean goAgain = simulateMove(i, maximize ? playerNumber : (playerNumber + 1) % 2);
                if(maximize ^ !goAgain){
                    currentScore = miniMax(depth - 1, false)[0];
                    if(currentScore > bestScore){
                        bestScore = currentScore;
                        best = i;
                    }
                } else { //minimizing
                    currentScore = miniMax(depth - 1, true)[0];
                    if(currentScore < bestScore){
                        bestScore = currentScore;
                        best = i;
                    }
                }
                // undo move
                undoMoveNew(i, maximize ? playerNumber : (playerNumber + 1) % 2);
            }
        }
        return new int[]{bestScore, best};
    }

    private ArrayList<Integer> generateMoves(int playerNumber){
        ArrayList<Integer> moves = new ArrayList<>();
        for(int i = 0; i < board[0].length - 1; i++){
            if(board[playerNumber][i] != 0){
                moves.add(i);
            }
        }
        return moves;
    }

    private int evaluate(){
        int score = 0;
        for(int i = 0; i < board[0].length - 1; i++){
            score += board[playerNumber][i]; // points weighted at 1
            score -= board[(playerNumber + 1) % 2][i]; // points in opponent
        }
        score += board[playerNumber][6] * weight; // points in personal, weighted at 4
        score -= board[(playerNumber + 1) % 2][6] * weight; // points in opponent
        return score;
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

    @Override
    public int getMove(){
        int choice = miniMax(depth, true)[1];
        System.out.println("Computer chose " + choice);
        return (choice >= 0) ? choice : 0;
    }

    @Override
    public String getName(){
        return "AI";
    }

    @Override
    public String getFirstName(){
        return "AI";
    }

    @Override
    public String getLastName(){
        return "AI";
    }
}
