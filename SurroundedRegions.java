class SurroundedRegions {

    static char x = 'X';
    static char o = 'O';
    static char t = 'T';

    public static void main(String[] args) {
      /*  char[][] board = new char[][]{
                new char[]{'X', 'X', 'X', 'X'},
                new char[]{'X', 'O', 'O', 'X'},
                new char[]{'X', 'X', 'O', 'X'},
                new char[]{'X', 'O', 'X', 'X'}
        };*/
        /*char[][] board = new char[][]{
                new char[]{'O', 'O', 'O'},
                new char[]{'O', 'O', 'O'},
                new char[]{'O', 'O', 'O'},
        };*/

        char[][] board = new char[][]{
                new char[]{'O', 'O', 'O', 'O'},
                new char[]{'O', 'O', 'O', 'O'},
                new char[]{'O', 'O', 'O', 'O'},
                new char[]{'O', 'O', 'O', 'O'}
        };

        solve(board);
    }


    public static void solve(char[][] board) {
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[0].length; j++)
                if (board[i][j] == o && (i == 0 || j == 0 || i == board.length - 1 || j == board[0].length - 1))
                    capture(i, j, board);

        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[0].length; j++)
                if (board[i][j] == o) board[i][j] = x;
                else if (board[i][j] == t) board[i][j] = o;


    }

    public static void capture(int i, int j, char[][] b) {
        // base case
        if (j < 0 || j == b[0].length) return;
        if (i < 0 || i == b.length) return;
        if (b[i][j] != o) return;

        b[i][j] = t;

        //left
//        if (j - 1 >= 0)
        capture(i, j - 1, b);

        //right
//        if (j + 1 < b[0].length)
        capture(i, j + 1, b);

        //top
//        if (i - 1 >= 0)
        capture(i - 1, j, b);

        //bottom
//        if (i + 1 < b.length)
        capture(i + 1, j, b);
    }
}
