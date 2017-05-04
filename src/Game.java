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
        for(int[] row : board){
            for(int i = 0; i < row.length - 1; i++){
                row[i] = stonesEach;
            }
        }
    }

    public Game(Player p1, Player p2){
        players = new Player[]{p1, p2};
        this.win = false;
        board = new int[2][pitsEach + 1];
        for(int[] row : board){
            for(int i = 0; i < row.length - 1; i++){
                row[i] = stonesEach;
            }
        }
//        printBoard();
    }

    public void start(){
        int playerTurn = 0;
        while(!this.win){
            int move = players[playerTurn % 2].getMove();
            this.win = move(move, playerTurn % 2);
            printBoard();
            playerTurn++;
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
//        System.out.println(row);
        int initialRow = row;
        int stones = board[row][position];
        int initialStones = stones;
        board[row][position] = 0;
        while(stones > 0){
            position++;
            if(row != initialRow && position == pitsEach) // if ends up in enemy home
                position++;
            row = (row + position / (pitsEach + 1)) % 2;
            position %= pitsEach + 1;
            board[row][position]++;
            stones--;
//            System.out.print(row + " ");
//            System.out.println(position);
//            printBoard();
        }
        if(board[0][pitsEach] + board[1][pitsEach] == stonesEach * pitsEach * 2)
            return true;
        return false;
    }

    public void printBoard(){
        // top row
        System.out.print("┌");
        for(int i = 1; i< board[0].length - 1; i++){
            System.out.print("──┬");
        }
        System.out.println("──┐");

        // row 0
        System.out.print("│");
        for(int i = board[0].length - 2; i >= 0; i--){
            System.out.format("%2d", board[0][i]);
            System.out.print("│");
        }
        System.out.println();

        // middle
        System.out.print("├");
        for(int i = 0; i < board[0].length - 2; i++){
            System.out.print("──┼");
        }
        System.out.print("──┤");
        System.out.println();

        // row 1
        System.out.print("│");
        for(int i = 0; i < board[1].length - 1; i++){
            System.out.format("%2d", board[1][i]);
            System.out.print("│");
        }
        System.out.println();

        System.out.print("└");
        for(int i = 1; i< board[0].length - 1; i++){
            System.out.print("──┴");
        }
        System.out.println("──┘");
        System.out.format("%s's Home: %d.\n", players[0].getFirstName(), board[0][pitsEach]);
        System.out.format("%s's Home: %d.\n", players[1].getFirstName(), board[1][pitsEach]);

    }

    public boolean getResults(){
        return(board[1][6] > board[0][6]);
    }

    public int[][] getBoard(){
        return board;
    }

    public static void main(String args[]){
//        game.addPlayer(new Player("Addison", "Chan"));
        int bestWeight = 0;
        int bestDepth = 0;
        for(int i = 1; i < 10; i++){
            for(int j = 1; i < 10; j++){
                Game game = new Game();
                game.addPlayer(new AI(game.getBoard(), 0));
                game.addPlayer(new AI(game.getBoard(), 1, i, j));
                game.start();
                boolean playerTwoWon = game.getResults();
                if(playerTwoWon){
                    bestWeight = i;
                    bestDepth = j;
                }
            }
        }
        System.out.print("Best Weight: " + bestWeight);
        System.out.print("Best Depth: " + bestDepth);
    }
}
