package addcninblue.mancala;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by addison on 5/1/17.
 */
public class AI implements Player {
    Board board;
    int playerNumber;
    int weight;
    int depth;

    public AI(Board board, int playerNumber){
        this(board, playerNumber, 8, 5);
    }

    public AI(Board board, int playerNumber, int weight, int depth){
        this.board = board;
        this.playerNumber = playerNumber;
        this.weight = weight;
        this.depth = depth;
    }

    public int calculateMove(){
//        int choice = miniMax(5, true)[1]; // debug
        int[] row = board.getRow(playerNumber);
        for(int i = row.length - 2; i >= 0; i--){
            if(row[i] == row.length - i - 1) {
                System.out.println("Computer chose " + i);
                return i;
            }
        }
        int[] results = miniMax(depth, true);
        System.out.println("Computer chose " + results[1]);
        return (results[1] >= 0) ? results[1] : 0;
    }

    /**
     * http://www3.ntu.edu.sg/home/ehchua/programming/java/javagame_tictactoe_ai.html
     * @param depth
     * @param maximize
     * @return
     */
    private int[] miniMax(int depth, boolean maximize){
        List<Integer> nextMoves = generateMoves(maximize ? playerNumber : (playerNumber + 1) % 2);

        int bestScore = maximize ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int currentScore;
        int best = -1;

        if(depth == 0 || nextMoves.isEmpty()){
            bestScore = evaluate();
        } else {
            for(int i : nextMoves){
                // do move
                board.saveState(true);
                boolean goAgain = board.move(i, maximize ? playerNumber : (playerNumber + 1) % 2, true);
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
                board.undoState();
            }
        }
        return new int[]{bestScore, best};
    }

    private ArrayList<Integer> generateMoves(int playerNumber){
        ArrayList<Integer> moves = new ArrayList<Integer>(); //changed diamond operators for compatibility
        int[] row = board.getRow(playerNumber);
        for(int i = 0; i < row.length - 1; i++){
            if(row[i] != 0){
                moves.add(i);
            }
        }
        return moves;
    }

    private int evaluate(){
        int score = 0;
        int[] mySide = this.board.getRow(playerNumber);
        int[] opponentSide = this.board.getRow((playerNumber + 1) % 2);
        for(int i = 0; i < mySide.length - 1; i++){
            score += (mySide[i] > i + 1) ? -(mySide[i] - i - 1) : mySide[i]; // points weighted at 1
            score -= (opponentSide[i] > i + 1) ? -(opponentSide[i] - i - 1) : opponentSide[i]; // points in opponent
        }
        score += mySide[6] * weight; // points in personal
        score -= opponentSide[6] * weight; // points in opponent
        return score;
    }



    @Override
    public String getName(){
        return "Computer";
    }
}
