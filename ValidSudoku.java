import java.util.HashSet;

class ValidSudoku {

    public static void main(String[] args) {

        char[][] board = new char[][]
                {
                        new char[]{'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                        new char[]{'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                        new char[]{'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                        new char[]{'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                        new char[]{'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                        new char[]{'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                        new char[]{'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                        new char[]{'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                        new char[]{'.', '.', '.', '.', '8', '.', '.', '7', '9'},
                };

        System.out.println("is valid " + isValidSudoku(board));
    }

    public static boolean isValidSudoku(char[][] board) {

        HashSet<Character> rowColumnSet = new HashSet<>();
        char empty = '.';

        //validate rows
        for (char[] row : board) {
            rowColumnSet.clear();

            for (char c : row) {
                if (c == empty) continue;

                if (rowColumnSet.contains(c)) {
                    return false;
                }

                rowColumnSet.add(c);
            }

        }

        //validate columns
        for (char[] row : board) {

            //go through each row
            for (int r = 0; r < row.length; r++) {
                rowColumnSet.clear();

                //go through each column in each row
                for (int col = 0; col < row.length; col++) {

                    char c = board[col][r];

                    if (c == empty) continue;

                    if (rowColumnSet.contains(c)) {
                        return false;
                    }

                    rowColumnSet.add(c);
                }
            }

        }

        //validate grid
        //divide into 3 cols
        rowColumnSet.clear();

        //Check boxes
        //Keep track of current row in overall 9x9
        for (int i = 0; i < 9; i += 3) {

            //Keep track of current col in overall 9x9
            for (int j = 0; j < 9; j += 3) {

                //traverse through rows in 3x3 box
                for (int k = i; k < (i + 3); k++) {
                    for (int l = j; l < (j + 3); l++) {

                        char c = board[k][l];

                        if (c == empty) continue;

                        if (rowColumnSet.contains(c)) {
                            return false;
                        }

                        rowColumnSet.add(c);
                    }
                }

                rowColumnSet.clear();
            }

        }

        return true;
    }

    public static boolean isValidSudokuBalo(char[][] board) {

        HashSet<Character> rowColumnSet = new HashSet<>();
        char empty = '.';

        //validate rows
        for (char[] row : board) {
            rowColumnSet.clear();

            for (char c : row) {
                if (c == empty) continue;

                if (rowColumnSet.contains(c)) {
                    return false;
                }

                rowColumnSet.add(c);
            }

        }

        //validate columns
        for (char[] row : board) {

            //go through each row
            for (int r = 0; r < row.length; r++) {
                rowColumnSet.clear();

                //go through each column in each row
                for (int col = 0; col < row.length; col++) {

                    char c = board[col][r];

                    if (c == empty) continue;

                    if (rowColumnSet.contains(c)) {
                        return false;
                    }

                    rowColumnSet.add(c);
                }
            }

        }

        //validate grid
        //divide into 3 cols
        rowColumnSet.clear();

        //Check boxes
        //traverse whole board horizontally


        char[] box1 = new char[]{}, box2 = new char[]{}, box3 = new char[]{};

        for (char[] row : board) {

            //go through each row
            for (int r = 0; r < row.length; r++) {

                //valdate 3 columns

                if (r == 2 || r == 5 || r == 8) rowColumnSet.clear();

                //go through each column in each row
                for (int col = 0; col < row.length; col++) {

                    char c = board[col][r];

                    if (c == empty) continue;

                    if (rowColumnSet.contains(c)) {
                        return false;
                    }

                    rowColumnSet.add(c);
                }
            }


        }
        return false;
    }

}
