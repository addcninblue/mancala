/**
 * Created by addison on 5/1/17.
 */
public class Game {
    private Player[] players; // only going to have 2 players max
    private boolean win;
    private int[][] board;
    private int stonesEach = 4;
    private int pitsEach = 6;
    // row 0: 6 is "home", 0 - 5 is pits
    // row 1: 6 is "home", 0 - 5 is pits
    // layout:
    // 6 5 4 3 2 1 0
    //   0 1 2 3 4 5 6

    public Game(){
        players = new Player[]{null, null};
        this.win = false;
        board = new int[2][pitsEach + 1];
        board = new int[2][pitsEach + 1];
        for(int[] row : board){
            for(int i = 0; i < row.length - 1; i++){
                row[i] = stonesEach;
            }
        }
    }

    public Game(Human p1, Human p2){
        players = new Player[]{p1, p2};
        this.win = false;
        board = new int[2][pitsEach + 1];
        for(int[] row : board){
            for(int i = 0; i < row.length - 1; i++){
                row[i] = stonesEach;
            }
        }
    }

    public void start(){
        int playerTurn = 0;
        printBoard();
        System.out.println();
        while(!this.win){
            boolean turnEnded = false;
            while(!turnEnded) {
                int move = players[playerTurn % 2].getMove();
                turnEnded = move(move, playerTurn % 2);
                printBoard();
            }
            playerTurn++;
            this.win = checkWin();
        }
    }

    public void addPlayer(Player player){
        for(int i = 0; i < players.length; i++){
            if(players[i] == null){
                players[i] = player;
                break;
            }
        }
    }

    /**
     * Moves given the position and row
     * @param position position of pit
     * @param row playerNumber
     * @return whether game is over or not
     */
    public boolean move(int position, int row){
        int initialRow = row;
        int stones = board[row][position];
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
                return false;
            } else if(board[row][position] > 1){
                return move(position, row);
            }
        }
        return true;
    }

    public boolean checkWin(){
        if(board[0][pitsEach] + board[1][pitsEach] == stonesEach * pitsEach * 2)
            return true;
        return false;
    }

    public void printBoard(){
        // I apologize for this ugliness
        // Built with Vim and a lot of patience
        String str = "     5  4  3  2  1  0\n┌──┬──┬──┬──┬──┬──┬──┬──┐\n│  │%2d│%2d│%2d│%2d│%2d│%2d│  │\n│%2d├──┼──┼──┼──┼──┼──┤%2d│\n│  │%2d│%2d│%2d│%2d│%2d│%2d│  │\n└──┴──┴──┴──┴──┴──┴──┴──┘\n     0  1  2  3  4  5\n";
        System.out.format(str, board[0][5], board[0][4], board[0][3], board[0][2], board[0][1], board[0][0], board[0][6],
                board[1][6], board[1][0], board[1][1], board[1][2], board[1][3], board[1][4], board[1][5]);
    }

    public boolean getResults(){
        return(board[1][6] > board[0][6]);
    }

    public int[][] getBoard(){
        return board;
    }

    public static void main(String args[]){
        Game game = new Game();
//        game.addPlayer(new Human("Addison", "Chan"));
//        game.addPlayer(new AI(game.getBoard(), 0));
        game.addPlayer(new Human());
        game.addPlayer(new AI(game.getBoard(), 1));
        game.start();
        // training
//        int bestWeight = 8;
//        int bestDepth = 5;
//        for(int k = 0; k < 3; k++){
//            for(int i = 1; i < 10; i++) {
//                for (int j = 1; j < 6; j++) {
//                    Game game = new Game();
//                    game.addPlayer(new AI(game.getBoard(), 0, bestWeight, bestDepth));
//                    game.addPlayer(new AI(game.getBoard(), 1, i, j));
//                    game.start();
//                    boolean playerTwoWon = game.getResults();
//                    if (playerTwoWon) {
//                        bestWeight = i;
//                        bestDepth = j;
//                    }
//                }
//            }
//        }
//        System.out.print("Best Weight: " + bestWeight);
//        System.out.print("Best Depth: " + bestDepth);
    }
}
